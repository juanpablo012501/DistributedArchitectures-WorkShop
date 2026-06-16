package edu.eci.arsw.rooms;

public class Room {

    //attributes
    private String id;
    private int capacity;
    private boolean available;

    //methods
    public Room(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        available = true;
    }

    public String toText() {
        String isFree = (available ? "available" : "not available");
        return "The room ID is: " + this.id + ", the capacity is: " + this.capacity + " and it is " + isFree;
    }

    //getters
    public String getId() {return id;}
    public int getCapacity() {return capacity;}
    public boolean isAvailable() {return available;}

    //setters
    public void setId(String id) {this.id = id;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setAvailable(boolean available) {this.available = available;}
}
