package com.example.server.service;


import com.example.server.data.Reservation;
import com.example.server.data.ReservationStatus;
import com.example.server.repository.ReservationMongoRepository;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {
    private final HazelcastInstance hazelcastInstance;
    private final ReservationMongoRepository reservationRepository;


    @Override
    public Reservation getReserveInfo(UUID reservationId) {
        return reservationRepository.findById(reservationId).get();
    }

    @Override
    public List<Reservation> getRoomReservations(String roomId) {
        return reservationRepository.findAllByRoomId(roomId);
    }

    @Override
    public List<Reservation> getClientReservations(UUID clientId) {
        return reservationRepository.findAllByClientId(clientId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    @Override
    public Reservation reserveRoom(Reservation reservation) {
        Reservation reservationResult = reservation;
        boolean lockSuccess = hazelcastInstance.getMap("room").tryLock(reservation.getRoomId());
        if (!lockSuccess) {
            return reservationResult;
        }
        reservation.setReservationStatus(ReservationStatus.Reserved);
        try {
            Thread.sleep(20000);
            reservationResult = reservationRepository.save(reservation);
        } catch (InterruptedException e) {
        } finally {
            hazelcastInstance.getMap("room").unlock(reservation.getRoomId());
        }
        return reservationResult;
    }

    @Override
    public void unReserveRoom(UUID reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public void reserveRooms(List<Reservation> reservations) {
        reservations.forEach(reservation -> reservation.setReservationStatus(ReservationStatus.Reserved));
        reservationRepository.saveAll(reservations);
    }

    @Override
    public Reservation payForReservation(UUID reservationId, float amount) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        if (reservation.getReservationStatus() != ReservationStatus.Reserved) {
            return reservation;
        }

        boolean lockSuccess = hazelcastInstance.getMap("room").tryLock(reservation.getRoomId());
        if (!lockSuccess) {
            return reservation;
        }
        reservation.setReservationStatus(ReservationStatus.Paid);

        try {
            Thread.sleep(20000);
            reservation = reservationRepository.save(reservation);
        } catch (InterruptedException e) {
        } finally {
            hazelcastInstance.getMap("room").unlock(reservation.getRoomId());
        }
        return reservation;
    }
}

