package com.example.server.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Builder
@Document("Reservations")
@Data
public class Reservation {
    private UUID id;
    private String roomId;
    private UUID clientId;
    private Instant reservationStartDate;
    private Instant reservationEndDate;
    private ReservationStatus reservationStatus;
}
