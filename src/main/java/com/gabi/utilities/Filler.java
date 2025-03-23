package com.gabi.utilities;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.models.Room;
import com.gabi.models.RoomType;
import com.gabi.service.CustomerService;
import com.gabi.service.ReservationService;

import java.util.*;

public class Filler {

    private CustomerService customerService;
    private ReservationService reservationService;

    public Filler() {
        this.customerService = CustomerService.getInstance();
        this.reservationService = ReservationService.getInstance();
    }

    public void seed() {

        // add customers to customerService map
        Customer customer1 = new Customer("Mary", "Sue", "mary@gmail.com");
        Customer customer2 = new Customer("John", "Smith", "john@gmail.com");
        Customer customer3 = new Customer("Jane", "Doe", "jane@gmail.com");
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        customerService.addCustomer(customer3);


        // add rooms to reservationService map
        IRoom room1 = new Room("101", 50.00, RoomType.SINGLE);
        IRoom room2 = new Room("102", 50.00, RoomType.SINGLE);
        IRoom room3 = new Room("103", 75.00, RoomType.DOUBLE);
        List<IRoom> ls = new ArrayList<>();
        ls.add(room1);
        ls.add(room2);
        ls.add(room3);

        reservationService.addRoom(ls);

        // add a reservation to room 101
        Customer customer = customerService.getCustomer("mary@gmail.com").orElseThrow();
        IRoom room = reservationService.getARoom("101");
        Date checkIn = new GregorianCalendar(2025, Calendar.MAY, 1).getTime();
        Date checkOut = new GregorianCalendar(2025, Calendar.MAY, 2).getTime();

        reservationService.reserveARoom(customer, room, checkIn, checkOut);

    }


}
