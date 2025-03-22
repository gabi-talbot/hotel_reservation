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

        return reservation;
    }

    // needs a way to specify dates in search room should only be false if booked between those dates.
    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {

        List<IRoom> result = new ArrayList<>();
        for (Reservation res : reservations) {
            if((checkInDate.equals(res.getCheckOutDate()) || checkInDate.before(res.getCheckOutDate()))
            || (checkOutDate.equals(res.getCheckInDate()) || checkOutDate.after(res.getCheckInDate()))){
                continue;
            }else {
                result.add(res.getRoom());
            }
        }
        return result;
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
