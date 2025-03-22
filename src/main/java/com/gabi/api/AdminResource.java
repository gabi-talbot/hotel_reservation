package com.gabi.api;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.service.CustomerService;
import com.gabi.service.ReservationService;

import java.util.List;

public class AdminResource {

    // autowiring service instances
    private static CustomerService customerService = CustomerService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();
    private static final AdminResource reference = new AdminResource();

    private AdminResource() {
    }

    public static AdminResource getInstance() {
        return reference;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        reservationService.addRoom(rooms);
    }

    public List<IRoom> getAllRooms() {
     return reservationService.getRooms();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}

