package com.gabi.service;

import com.gabi.models.Customer;
import com.gabi.models.IRoom;
import com.gabi.models.Reservation;
import com.gabi.models.Room;

import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class designed as a singleton Reservation model
 */
public class ReservationService {

    // class variable only accessed via getInstance mathod
    private static ReservationService reference = new ReservationService();
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
                                    Date checkInDate, Date checkOutDate) {

        // Create reservation and add reservation to Collection store
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        System.out.println("reservation added");

        return reservation;
    }


    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

        // check to see if checkin/check dates cause a room to be unavailable
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

        // stream. map room to result if free, collect to list
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
