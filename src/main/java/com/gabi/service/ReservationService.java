package com.gabi.service;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;

import java.util.*;


/**
 * Service class designed as a singleton
 */
public class ReservationService {

    // class variable only accessed via getInstance method
    private static final ReservationService reference = new ReservationService();
    private static List<Reservation> reservations;
    private static Map<String, IRoom> rooms;


    private ReservationService() {
        rooms = new HashMap<>();
        reservations = new ArrayList<>();
    }
    public static ReservationService getInstance() {
        return reference;
    }

    public void addRoom(List<IRoom> roomsList) {
        roomsList.forEach(room -> rooms.put(room.getRoomNumber(), room));
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkInDate, Date checkOutDate) throws IllegalArgumentException {

        // check if there is a matching reservation
        List<Reservation> customerReservations = getCustomerReservations(customer);
        List<Reservation> filteredReservations = customerReservations.stream()
                .filter(reservation -> ((reservation.getCheckInDate().before(checkInDate) || reservation.getCheckInDate().equals(checkInDate))
                && (reservation.getCheckOutDate().after(checkInDate) || reservation.getCheckOutDate().equals(checkInDate))))
                .filter(reservation -> ((reservation.getCheckInDate().before(checkOutDate) || reservation.getCheckInDate().equals(checkOutDate))
                        && (reservation.getCheckOutDate().after(checkOutDate) || reservation.getCheckOutDate().equals(checkOutDate))))
                .toList();
        // throw if reservation exists in the date range
        if (!filteredReservations.isEmpty()) throw new IllegalArgumentException("Reservation already present for this date range");

        // Else, create reservation and add reservation to Collection for storage
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        System.out.println("reservation added");

        return reservation;
    }


    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

        // check to see if checkin/check dates causes a room to be unavailable
        for (Reservation res : reservations) {
            if((((checkInDate.before(res.getCheckOutDate())) || (checkInDate.equals(res.getCheckOutDate()))) &&
                    ((checkInDate.after(res.getCheckInDate()))) || (checkInDate.equals(res.getCheckInDate())))
            || (((checkOutDate.after(res.getCheckInDate())) || (checkOutDate.equals(res.getCheckInDate()))) &&
                    ((checkOutDate.before(res.getCheckOutDate())) || (checkOutDate.equals(res.getCheckOutDate()))))){
                String roomId = res.getRoom().getRoomNumber();
                // make room as unavailable
                rooms.get(roomId).setIsFree(false);
            }
        }

        // stream and filter room to result if it is free. Finally, collect to list
       List<IRoom> freeRooms =  rooms.values().stream()
                .filter(IRoom::isFree)
                .toList();
        // set all rooms back to free, ready for next query
        rooms.values().forEach(room -> room.setIsFree(true));

        return freeRooms;
    }

    public List<Reservation> getCustomerReservations(Customer customer) {
        return reservations.stream()
                .filter(reservation -> reservation.getCustomer().equals(customer))
                .toList();
    }

    public void printAllReservations(){
        reservations.forEach(System.out::println);
    }

    public List<IRoom> getRooms() {
        return new ArrayList<>(rooms.values());
    }
}
