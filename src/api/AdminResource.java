package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
//static reference
public class AdminResource {
    CustomerService customerservice= CustomerService.getInstance();
    ReservationService reservationService =ReservationService.getInstance();
    private static AdminResource adminResource;
    public AdminResource(){}
    public static AdminResource getInstance(){
        if (Objects.isNull(adminResource)){
            adminResource=new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){
          return customerservice.getCustomer(email);

    }
    public  void addRoom (List<IRoom> rooms){
        for (IRoom b:rooms
             ) {reservationService.addRoom(b);

        }

    }
    public Collection<IRoom> getAllRooms(){


        reservationService.getAllRoom();

        return reservationService.rooms;

    }
    public Collection<Customer> getAllCustomer(){

        return customerservice.getAllCustomers();

    }
    public void displayAllReservation(){
          reservationService.printAllReservation();

    }
}
