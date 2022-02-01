package service;
import com.sun.source.tree.IfTree;
import model.Customer;
import model.IRoom;
import model.Reservation;


import java.security.PublicKey;
import java.util.*;

public class ReservationService {
    public Set<IRoom> rooms = new HashSet<>(); // The set to store all the room
    public Set<Reservation> reservationSet =new HashSet<>(); // the map store all the reservation
    private static ReservationService reservationService;
    public ReservationService () {}
    public static ReservationService getInstance(){
        if (Objects.isNull(reservationService)){
            reservationService=new ReservationService();
        }
        return reservationService;
    }


    void addRoomExtent(IRoom room){
        rooms.add(room);

   }
     public void addRoom(IRoom room){


         addRoomExtent(room);
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
            System.out.println("Rooms in the hotel system:");
            for (IRoom room:rooms
                 ) {
                  System.out.println(room);

            }
        }
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

               Reservation a=new Reservation(customer,room,checkInDate,checkOutDate);
                  reservationSet.add(a);
                  System.out.println("your room has been reserved successfully");
                  return a;


    }

    public Collection<IRoom> findRooms(Date checkInDate,Date checkOutDate) {
        Set<IRoom> availableRoom = new HashSet<>();// This set store all available room
        Set<IRoom> reservationRoom = new HashSet<>();
        for (Reservation matchReservation : reservationSet) {
            reservationRoom.add(matchReservation.getRoom());
        }

        if (!(rooms.isEmpty())&&reservationSet.isEmpty()) {
            // if there is no reservation in the reservation map ,then all the rooms in the room set are available
            availableRoom.addAll(rooms);


        }else if (rooms.isEmpty()){
            System.out.println("Currently there is no  room available in the system ");



        }else
        {
            for (IRoom roms: rooms){

                for (Reservation r: reservationSet){

                    if (((r.getCheckInDate().compareTo(checkOutDate) >= 0) || (r.getCheckOutDate().compareTo(checkInDate) <= 0) )
                            &&(roms.getRoomNumber().equals(r.getRoom().getRoomNumber()))&&notCollide(roms,checkInDate,checkOutDate)){
                        availableRoom.add(roms);


                    }else if (!reservationRoom.contains(roms)){
                        availableRoom.add(roms);

                    }
                }
            }



        }



        return availableRoom;
    }
///if for a single room
    public  boolean notCollide(IRoom room,Date checkInDate,Date checkOutDate){
        boolean test;
        boolean result=true;
        for (Reservation reservation:reservationSet){
            if (room.getRoomNumber().equals(reservation.getRoom().getRoomNumber())){
            test= ((reservation.getCheckInDate().compareTo(checkOutDate) >= 0) || (reservation.getCheckOutDate().compareTo(checkInDate) <= 0));
            result=test&result;
            }
        }
        return result;
    }


    public Collection<Reservation> getCustomersReservation(Customer customer){

            Collection<Reservation> reservations = new HashSet<>();
            for (Reservation r: reservationSet){
                if (r.getCustomer().equals(customer)){
                    reservations.add(r);
                }
            }



           for (Reservation reservation: reservations) {
            System.out.println(reservation);

        }
            return reservations;


    }

    public void  printAllReservation(){
        int i=1;
        for (Reservation reservation:reservationSet) {
            System.out.println(reservation);
            System.out.println("--------------");

        }

    }
}
