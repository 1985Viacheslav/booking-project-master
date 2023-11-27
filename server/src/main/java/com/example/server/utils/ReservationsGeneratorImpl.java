package com.example.server.utils;

import com.example.server.data.Client;
import com.example.server.data.Reservation;
import com.example.server.data.ReservationStatus;
import com.example.server.data.Room;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class ReservationsGeneratorImpl implements ReservationsGenerator {

    @Override
    public List<Reservation> makeRandomReservations(List<Client> clients, List<Room> rooms) {
        List<Reservation> reservations = new ArrayList<>();
        int reservationsCount = Math.min(clients.size(), rooms.size());

//        Random random = new Random();

        for (int i = 0; i < reservationsCount; i++) {
            long daysDelta = 7;
            long daysCount = 7;

            Reservation reservation = Reservation.builder()
                    .id(UUID.randomUUID())
                    .roomId(rooms.get(i).getId())
                    .clientId(UUID.fromString(clients.get(i).getId()))
                    .reservationStartDate(Instant.now().plus(daysDelta, ChronoUnit.DAYS))
                    .reservationEndDate(Instant.now().plus(daysDelta + daysCount, ChronoUnit.DAYS))
                    .reservationStatus(ReservationStatus.Paid)
                    .build();

            reservations.add(reservation);
        }

        return reservations;
    }
}
