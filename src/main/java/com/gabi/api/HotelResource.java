package com.gabi.api;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;
import com.gabi.service.CustomerService;
import com.gabi.service.ReservationService;

import java.util.Date;
import java.util.List;

public class HotelResource {

    // autowiring service instances
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    // static reference
    private static final HotelResource reference = new HotelResource();

    private HotelResource() {
    }

    public static HotelResource getInstance() {
        return reference;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customerService.addCustomer(customer);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate,checkOutDate);
    }

    public List<Reservation> getCustomerReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomerReservations(customer);
    }

    public List<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }




}
