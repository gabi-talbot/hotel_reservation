package com.gabi.menus;

import com.gabi.api.AdminResource;
import com.gabi.api.HotelResource;
import com.gabi.models.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final AdminResource adminResource;
    private final HotelResource hotelResource;

    public AdminMenu() {
        adminResource = AdminResource.getInstance();
        hotelResource = HotelResource.getInstance();
    }

    public void display(Scanner sc) {
        sc.nextLine();
        int input = 0;
        do {
            menu();
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input must be a valid number");
                sc.nextLine();
                continue;
            }

            if (input >= 6 || input <= 0) {
                System.out.println("Input must be a valid number");
            }

            switch (input) {
                case 1:
                    seeAllCustomers();
                    continue;
                case 2:
                    seeAllRooms();
                    continue;
                case 3:
                    seeAllReservations();
                    continue;
                case 4:
                    addRoom(sc);
                    continue;
                case 5:
                    return;
            }
        } while (input != 5);
    }

    private void menu(){
            System.out.println("Admin Menu");
            System.out.println("----------");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            // just add a return statement.
            System.out.println("5. Back to Main Menu");
    }

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


    private void addRoom(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter room number");
        String roomNumber = sc.nextLine();
        try{
            Integer.parseInt(roomNumber);
        }catch (NumberFormatException e){
            System.out.println("Invalid room number");
            return;
        }

        System.out.println("Enter room price");
        double price;
        try {
            price = sc.nextDouble();
        }catch (InputMismatchException e){
            System.out.println("Invalid room price");
            return;
        }

        sc.nextLine();
        System.out.println("Enter room type: double or single");
        RoomType roomType;
        try{
            roomType = RoomType.valueOf(sc.nextLine().toUpperCase());
        }catch (IllegalArgumentException e){
            System.out.println("Invalid room type");
            return;
        }

        // Create room, save and return control
        List<IRoom> rooms = new ArrayList<>();
        IRoom room = new Room(roomNumber, price, roomType);
        rooms.add(room);
        adminResource.addRoom(rooms);
        IRoom savedRoom = hotelResource.getRoom(roomNumber);
        System.out.println(savedRoom + " added successfully");
    }

}
