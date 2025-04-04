package com.gabi.menus;

import com.gabi.api.HotelResource;
import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Reservation reservation;
        try {
            // method called if customer has an account and wants to book a room
            reservation = booking(sc, checkInDate, checkOutDate);
        }catch (IllegalArgumentException e){
            System.out.println(e.toString());
            return;
        }
        // If valid, confirm booking
        System.out.println(reservation.toString());

    }

    public void seeReservations(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter your email address");
        String email = sc.nextLine();
        // check email is valid
        if(!emailValidator(email)) {
            return;
        }
        //
        try {
            hotelResource.getCustomerReservations(email);
        } catch (NoSuchElementException e) {
            System.out.println("Customer does not exist. Please create an account to continue");
            return;
        }

        // return and display reservations
        List<Reservation> reservations = hotelResource.getCustomerReservations(email);
        reservations.forEach(System.out::println);

    }

    public void createAccount(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter your email address");
        String email = sc.nextLine();
        System.out.println("Enter your first name");
        String firstName = sc.nextLine();
        System.out.println("Enter your last name");
        String lastName = sc.nextLine();

        try{
            hotelResource.createCustomer(firstName, lastName, email);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }

        Optional<Customer> customer = hotelResource.getCustomer(email);
        customer.ifPresent(System.out::println);

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

        // check date range provided first
        List<IRoom> roomList = hotelResource.findARoom(checkInDate, checkOutDate);
        if (roomList.isEmpty()) {
            System.out.println("No rooms found, extending date range by 7 days");
            List<IRoom> extendedRooms = hotelResource.extendedFindARoom(checkInDate, checkOutDate);
            if (extendedRooms.isEmpty()) {
                System.out.println("No rooms available.");
                return;
            }
            System.out.println("Available rooms: ");
            extendedRooms.forEach(System.out::println);
            return;
        }
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

    private boolean emailValidator(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+\\.)(com|co\\.uk|org|net)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
