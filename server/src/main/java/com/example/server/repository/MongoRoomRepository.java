package com.example.server.repository;


import com.example.server.data.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MongoRoomRepository extends CrudRepository<Room, UUID> {
    List<Room> findTop15ByOrderByIdDesc();
}
