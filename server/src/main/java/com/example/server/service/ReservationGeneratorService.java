package com.example.server.service;

import com.example.server.data.Client;
import com.example.server.data.Reservation;
import com.example.server.data.Room;
import com.example.server.repository.MongoClientRepository;
import com.example.server.repository.MongoRoomRepository;
import com.example.server.repository.ReservationMongoRepository;
import com.example.server.utils.ReservationsGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ReservationGeneratorService {
    private final Logger logger = LoggerFactory.getLogger(ReservationGeneratorService.class);

    @Autowired
    private MongoClientRepository clientRepository;

    @Autowired
    private MongoRoomRepository roomRepository;

    @Autowired
    private ReservationMongoRepository reservationRepository;

    @Autowired
    private ReservationsGenerator reservationsGenerator;

    @Value("${reservations.generator.needed}")
    private boolean reservationsGeneratorShouldStart;



    @PostConstruct
    private void init() {
        if (reservationsGeneratorShouldStart) {
            logger.info("Reservations generating started...");
            List<Client> clients = (List<Client>) clientRepository.findAll();
            List<Room> rooms = (List<Room>) roomRepository.findAll();
            List<Reservation> reservations = reservationsGenerator.makeRandomReservations(clients, rooms);
            reservationRepository.saveAll(reservations);
            logger.info("Reservations generating finished...");
        }
    }
}
