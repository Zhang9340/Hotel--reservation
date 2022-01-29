package service;
import model.Customer;
import model.IRoom;
import model.Reservation;


import java.time.Duration;
import java.util.*;

public class ReservationService {
    public Collection<IRoom> rooms = new HashSet<>(); // The set to store all the room
    public Map<String,Reservation> mapReservation =new HashMap<String ,Reservation>(); // the map store all the reservation
    public Collection<IRoom> availableRoom = new HashSet<>();// This set store all available room

    private static ReservationService reservationService;
    public ReservationService () {}
    public static ReservationService getInstance(){
        if (Objects.isNull(reservationService)){
            reservationService=new ReservationService();
        }
        return reservationService;
    }



    public void addRoom(IRoom room){


         rooms.add(room);
         System.out.println("You have successfully add a room !");

    }


    public IRoom getARoom(String roomId){
        for (IRoom room:rooms){
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
            return  null;
    }



    public void  getAllRoom(){
        if (rooms.size()==0){
            System.out.println("There is no room in the system");
        }else {
            System.out.println("Rooms in the hotel system");
            for (IRoom room:rooms
                 ) {
                  System.out.println(room);

            }
        }
    }

    public  void reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

              Reservation a=new Reservation(customer,room,checkInDate,checkOutDate);
                  mapReservation.put(customer.getEmail(), a);
                  System.out.println("your room has been reserved successfully");

    }

    public Collection<IRoom> findRooms(Date checkInDate,Date checkOutDate) {
        Set<Reservation> ReservationSet = new HashSet<>(mapReservation.values());
        if (rooms.size()!=0&&mapReservation.size()==0) {
            // if there is no reservation in the reservation map ,then all the rooms in the room set are available
           availableRoom=rooms;

        }else if (rooms.size()==0){
            System.out.println("Currently there is no  room available in the system ");



        }else
        {


          for (Reservation matchReservation : ReservationSet) {
              for (IRoom rom:rooms) {
                    if ( (rom.getRoomNumber().equals(matchReservation.getRoom().getRoomNumber()))
                    && ((matchReservation.getCheckInDate().compareTo(checkOutDate) >= 0) || (matchReservation.getCheckOutDate().compareTo(checkInDate) <= 0) ))
                    {
                        // For a room in the room set ,if the given checkin and checkout is not conflicted with current reservation  then this room is available
                        ///Add reservation
                        availableRoom.add(rom);

                    }else {
                        availableRoom.remove(rom);

                    }
                }
          }

        }
        return availableRoom;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){

            Collection<Reservation> reservations = new HashSet<>();
            reservations.add(mapReservation.get(customer.getEmail()));
            return reservations;


    }

    public void  printAllReservation(){
        for (Reservation reservation:mapReservation.values()
             ) {
            System.out.println(reservation);

        }

    }
}
