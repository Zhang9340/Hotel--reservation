package model;

public class Room implements IRoom {
    private final String roomNumber;
    protected Double price;
    private final RoomType enumeration;

    public Room(String roomNumber,Double price,RoomType enumeration){
        this.enumeration=enumeration;
        this.price=price;
        this.roomNumber=roomNumber;
    }
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash +  (roomNumber == null ? 0 :roomNumber.hashCode());
        hash = 31 * hash + (price == null ? 0 : price.hashCode());
        hash = 31 * hash + (enumeration== null ? 0 : enumeration.hashCode());

        return hash;
    }
    @Override
    public boolean equals(final Object o){
        //If the object is compared with itself then return true
        if (this==o){return true;}

        if (o==null){return false;}
        if(getClass()!=o.getClass()){return false;}

        // typecast o to Complex so that we can compare data members
        Room room=(Room) o;
        return roomNumber.equals(room.roomNumber)
                &&enumeration.equals(room.enumeration)
                &&price.equals(room.price);
                



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
