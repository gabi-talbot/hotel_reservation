package com.gabi.models;

public class Room implements IRoom {
    private final String roomNumber;
    Double price;
    private final RoomType roomType;
    private boolean isFree;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
        this.isFree = true;
    }
    @Override
    public final String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public final Double getRoomPrice() {
        return this.price;
    }

    @Override
    public final RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public final boolean isFree() {
        return this.isFree;
    }

    // sets the room availability status
    @Override
    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", roomType=" + roomType +
                '}';
    }
}
