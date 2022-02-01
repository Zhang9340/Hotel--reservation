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
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash +  (customer == null ? 0 :customer.hashCode());
        hash = 31 * hash + (room == null ? 0 : room.hashCode());
        hash = 31 * hash + (checkInDate== null ? 0 : checkInDate.hashCode());
        hash=  31*hash + (checkOutDate== null? 0: checkOutDate.hashCode());
        return hash;
    }
    @Override
    public boolean equals(final Object o){
        //If the object is compared with itself then return true
        if (this==o){return true;}

        if (o==null){return false;}
        if(getClass()!=o.getClass()){return false;}

        // typecast o to Complex so that we can compare data members
        Reservation reservation=(Reservation) o;
        return customer.equals(reservation.customer)
                &&room.equals(reservation.room)&&checkInDate.equals(reservation.checkInDate)
                &&checkOutDate.equals(reservation.checkOutDate);




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

    public  IRoom getRoom() {
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
        return "Customer detail: "+customer +"\n"+"Check in date: "+checkInDate+
                " "+"Check out date: "+checkOutDate+" Room Type:"+ room;
    }
}
