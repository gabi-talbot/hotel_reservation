package com.gabi.api;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.service.CustomerService;
import com.gabi.service.ReservationService;

import java.util.List;
import java.util.Optional;

public class AdminResource {

    private static CustomerService customerService;
    private static ReservationService reservationService;
    private static final AdminResource reference = new AdminResource();

    private AdminResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public static AdminResource getInstance() {
        return reference;
    }

    public Optional<Customer> getCustomer(String email) {
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

