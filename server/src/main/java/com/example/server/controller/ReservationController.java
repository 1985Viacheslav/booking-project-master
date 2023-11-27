package com.example.server.controller;

import com.example.server.data.Reservation;
import com.example.server.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping
    public List<Reservation> reservationsInfo(@RequestParam(required = false) UUID reservationId) {
        if (reservationId == null) {
            return reservationService.getAllReservations();
        } else {
            Reservation reservation = reservationService.getReserveInfo(reservationId);
            return List.of(reservation);
        }
    }

    @GetMapping("room")
    public List<Reservation> roomReservations(@RequestParam String roomId) {
        return reservationService.getRoomReservations(roomId);
    }

    @GetMapping("client")
    public List<Reservation> clientReservations(@RequestParam UUID clientId) {
        return reservationService.getClientReservations(clientId);
    }

    @PostMapping
    public Reservation reserveRoom(@RequestBody Reservation reservation) {
        return reservationService.reserveRoom(reservation);
    }

    @DeleteMapping
    public void unReserveRoom(@RequestParam UUID reservationId) {
        reservationService.unReserveRoom(reservationId);
    }

    @PostMapping("paid")
    public Reservation payForReservation(@RequestParam UUID reservationId, @RequestParam(defaultValue = "0.0") Float amount) {
        return reservationService.payForReservation(reservationId, amount);
    }
}
