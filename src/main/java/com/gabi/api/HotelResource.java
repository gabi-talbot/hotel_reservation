package com.gabi.api;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;
import com.gabi.service.CustomerService;
import com.gabi.service.ReservationService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HotelResource {

    private final CustomerService customerService;
    private final ReservationService reservationService;
    // static reference
    private static final HotelResource reference = new HotelResource();

    private HotelResource() {
        customerService = CustomerService.getInstance();
        reservationService = ReservationService.getInstance();
    }

    public static HotelResource getInstance() {
        return reference;
    }

    public Optional<Customer> getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customerService.addCustomer(customer);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate)
            throws NoSuchElementException {
        Customer customer = customerService.getCustomer(customerEmail).orElseThrow();
        return reservationService.reserveARoom(customer, room, checkInDate,checkOutDate);
    }

    public List<Reservation> getCustomerReservations(String customerEmail) throws NoSuchElementException {
        Customer customer = customerService.getCustomer(customerEmail).orElseThrow();
        return reservationService.getCustomerReservations(customer);
    }

    public List<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }




}
