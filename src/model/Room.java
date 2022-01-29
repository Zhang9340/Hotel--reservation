package model;

public class Room implements IRoom {
    private String roomNumber;
    protected Double price;
    private RoomType enumeration;

    public Room(String roomNumber,Double price,RoomType enumeration){
        this.enumeration=enumeration;
        this.price=price;
        this.roomNumber=roomNumber;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }
    @Override
    public  String toString(){
        return "Room Number: "+roomNumber+" "+" Room Price :"+price +" Room type: "+ enumeration+"\n";
    }
}
