package com.example.server.service;

import com.example.server.data.Room;
import com.example.server.data.RoomElastic;
import com.example.server.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRooms();

    RoomDto addRoom(RoomDto room);

    void addRooms(List<RoomDto> rooms);

    List<RoomElastic> searchCommon(String text);

    List<RoomElastic> findAll(String description);

    List<RoomElastic> findAllByPriceIsLessThan(String name);
    List<Room> getTop3Rooms();
}
