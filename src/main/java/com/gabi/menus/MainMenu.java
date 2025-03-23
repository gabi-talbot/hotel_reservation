package com.gabi.menus;

import com.gabi.api.HotelResource;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;
import com.gabi.models.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {


    private static final String DATE_FORMAT = "MM/dd/yyyy";
    SimpleDateFormat formatter;
    HotelResource hotelResource;

    public MainMenu() {
        hotelResource = HotelResource.getInstance();
        formatter = new SimpleDateFormat(DATE_FORMAT);
    }


    public void display() {
        System.out.println("Welcome to the Hotel Reservation Menu");
        System.out.println(" ");
        System.out.println("---------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------");
        System.out.println(" ");
        System.out.println("Please enter your choice");

    }

    public void findAndReserve(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter check in date mm/dd/yyyy eg: 12/03/2025");
        String inDate = sc.nextLine();
        // Try to convert String to Date
        Date checkInDate = checkDate(inDate);
        if (checkInDate == null) {
            return;
        }

        System.out.println("Enter check out date mm/dd/yyyy eg: 12/03/2025");
        String outDate = sc.nextLine();
        // Try to convert String to Date
        Date checkOutDate = checkDate(outDate);
        if (checkOutDate == null) {
            return;
        }

        // find available rooms
        findRooms(checkInDate, checkOutDate);
        // check if customer has an account
        System.out.println("Would you like to book a room? y/n");
        String answer1 = sc.next();
        if (answer1.equalsIgnoreCase("n")) {
            return;
        }
        System.out.println("Do you have an account? y/n");
        String answer2 = sc.next();
        if (answer2.equalsIgnoreCase("n")) {
            System.out.println("Please create an account before continuing");
            return;
        }
        sc.nextLine();
        // method called if customer has an account and wants to book a room
        Reservation reservation = booking(sc, checkInDate, checkOutDate);
        // confirm booking
        System.out.println(reservation.toString());

    }

    private Date checkDate(String date){
        Date checkedDate = null;
        try {
            checkedDate = formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("Please enter a valid date mm/dd/yyyy eg: 12/03/2025");
            return checkedDate;
        }
        return checkedDate;
    }

    private void findRooms(Date checkInDate, Date checkOutDate) {

        // check date range not accurate - should be returning room 101
        List<IRoom> roomList = hotelResource.findARoom(checkInDate, checkOutDate);
        System.out.println("Available rooms: ");
        roomList.forEach(System.out::println);
    }

    private Reservation booking(Scanner sc, Date checkInDate, Date checkOutDate) {
        System.out.println("Enter your email address:");
        String email = sc.nextLine();
        System.out.println("what room number would you like to reserve?");
        String roomNumber = sc.nextLine();
        IRoom room = hotelResource.getRoom(roomNumber);

        return hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
    }
}
