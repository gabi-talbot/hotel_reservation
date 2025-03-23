package com.gabi.menus;

import com.gabi.api.AdminResource;
import com.gabi.models.*;

import java.util.ArrayList;
import java.util.List;

public class AdminMenu {

    private AdminResource adminResource;

    public AdminMenu() {
        adminResource = AdminResource.getInstance();
    }

    public void display(){
        System.out.println("Admin Menu");
        System.out.println("----------");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        // just add a return statement.
        System.out.println("5. Back to Main Menu");
    }

    //TODO: doWhile loop as in main menu
    private void seeAllCustomers() {
        List<Customer> customers = adminResource.getAllCustomers();
        customers.forEach(System.out::println);
    }

    private void seeAllRooms() {
        List<IRoom> rooms = adminResource.getAllRooms();
        rooms.forEach(System.out::println);
    }

    private void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private void addRoom(String roomNumber, Double price, RoomType type) {
        List<IRoom> rooms = new ArrayList<>();
        IRoom room = new Room(roomNumber, price, type);
        rooms.add(room);

        adminResource.addRoom(rooms);
    }

}
