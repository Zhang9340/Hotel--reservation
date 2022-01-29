package api;



import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;


import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class HotelResource {
    private static HotelResource hotelResource;
    public HotelResource () {}
    public static HotelResource getInstance(){
        if (Objects.isNull(hotelResource)){
           hotelResource=new HotelResource();
        }
        return hotelResource;
    }

    CustomerService customerservice=CustomerService.getInstance();
    ReservationService reservationService =ReservationService.getInstance();

    public Customer getCustomer(String email){

       return  customerservice.getCustomer(email);


    }


    public void createACustomer(String email ,String firstName, String lastName){
        customerservice.addCustomer(email,firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        System.out.println(reservationService.getARoom(roomNumber));
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate,Date checkOutDate){
       Customer customer= getCustomer(customerEmail);

        reservationService.reserveARoom(customer,room,checkInDate,checkOutDate);
        return null;




    }
    public Collection<Reservation> getCustomersReservations(String customerEmail){
           return reservationService.getCustomersReservation(getCustomer(customerEmail));

    }



    public Collection<IRoom> findARoom(Date checkIn,Date checkOut){
        System.out.println(reservationService.findRooms(checkIn,checkOut));
        return   reservationService.findRooms(checkIn,checkOut);
    }


}




