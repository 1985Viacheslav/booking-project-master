package com.example.server.utils;

import com.example.server.data.Client;
import com.example.server.data.Reservation;
import com.example.server.data.Room;

import java.util.List;

public interface ReservationsGenerator {
    List<Reservation> makeRandomReservations(List<Client> clients, List<Room> rooms);
}

