package com.gabi.models;

public interface IRoom {

    public String getRoomNumber();

    public Double getRoomPrice();

    public RoomType getRoomType();

    public boolean isFree();

    public void setIsFree(boolean b);

}
