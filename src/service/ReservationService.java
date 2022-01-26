package service;
import model.Customer;
import model.IRoom;
import model.Reservation;


import java.util.*;

public class ReservationService {
    public Collection<IRoom> rooms = new HashSet<>();
    public Map<String,Reservation> mapReservation =new HashMap<String ,Reservation>();

    public Collection<IRoom> availableRoom = new HashSet<>();

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

    }


    public IRoom getARoom(String roomId){
        for (IRoom room:rooms)
        {
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public  void reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

              Reservation a=new Reservation(customer,room,checkInDate,checkOutDate);
              if (! mapReservation.containsKey(customer.getEmail())){

                  mapReservation.put(customer.getEmail(), a);
                  System.out.println("This room has been reserved");
              }
    }

    public Collection<IRoom> findRooms(Date checkInDate,Date checkOutDate) {
        Set<Reservation> ReservationSet = new HashSet<>(mapReservation.values());
        if (mapReservation.size()==0) {
            // if there is no reservation in the reservation map ,then all the rooms in the room set are available
           availableRoom= rooms;

        }else if (rooms.size()==0){
            System.out.println("Currently there is no available room ");


        }else {

         for (Reservation matchReservation : ReservationSet) {
            for (IRoom rom:rooms) {
                if ( (rom.getRoomNumber().equals(matchReservation.getRoom().getRoomNumber()))
                    && ((matchReservation.getCheckInDate().compareTo(checkOutDate) >= 0) || (matchReservation.getCheckOutDate().compareTo(checkInDate) <= 0) ))
                    {
                        // For a room in the room set ,if the given checkin and checkout is not conflicted with current reservation  then this room is available
                        ///Add reservation
                        availableRoom.add(rom);
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
        System.out.println(mapReservation);

    }
}
