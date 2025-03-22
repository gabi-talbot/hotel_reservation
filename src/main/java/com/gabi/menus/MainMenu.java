package com.gabi.menus;

import com.gabi.api.HotelResource;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {


    private static final String DATE_FORMAT = "MM/dd/yyyy";
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    HotelResource hotelResource = HotelResource.getInstance();


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
        System.out.println("Enter check in date mm/dd/yyyy eg: 12/03/2025");
        String inDate = sc.nextLine();
        // Convert string to date
        Date checkInDate = null;
        try {
            checkInDate = formatter.parse(inDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Enter check out date mm/dd/yyyy eg: 12/03/2025");
        String outDate = sc.nextLine();
        Date checkOutDate = null;
        try {
            checkOutDate = formatter.parse(outDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // call private utility methods to find and book a room
        findRooms(checkInDate, checkOutDate);
        Reservation reservation = booking(sc, checkInDate, checkOutDate);

        // confirm booking
        System.out.println(reservation.toString());




    }

    private void findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> roomList = hotelResource.findARoom(checkInDate, checkOutDate);
        roomList.forEach(room -> System.out.println(room.toString()));
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
