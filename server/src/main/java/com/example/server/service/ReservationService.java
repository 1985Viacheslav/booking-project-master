package com.example.server.service;


import com.example.server.data.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    Reservation getReserveInfo(UUID reservationId);

    List<Reservation> getRoomReservations(String roomId);

    List<Reservation> getClientReservations(UUID clientId);

    List<Reservation> getAllReservations();

    Reservation reserveRoom(Reservation reservation);

    void unReserveRoom(UUID reservationId);

    void reserveRooms(List<Reservation> reservations);

    Reservation payForReservation(UUID reservationId, float amount);
}
