package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;
    public Reservation(Customer customer,IRoom room,Date checkInDate,Date checkOutDate){
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.room=room;
        this.customer=customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }
    @Override
    public String toString(){
        return "Customer detail: "+customer +"\n"+" Check in date: "+checkInDate+
                " "+"Check out date: "+checkOutDate+" Room Type:"+ room;
    }
}
