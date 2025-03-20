package com.gabi.models;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType roomType) {
        super(roomNumber, price, roomType);
        super.price = 0.0;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FreeRoom price=" + price +
                '}';
    }
}
