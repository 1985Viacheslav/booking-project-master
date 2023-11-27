package com.example.server.repository;


import com.example.server.data.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationMongoRepository extends CrudRepository<Reservation, UUID> {
    List<Reservation> findAllByRoomId(String roomId);
    List<Reservation> findAllByClientId(UUID clientId);
}

