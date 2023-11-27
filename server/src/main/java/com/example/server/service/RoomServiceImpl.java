package com.example.server.service;

import com.example.server.data.Room;
import com.example.server.data.RoomElastic;
import com.example.server.dto.RoomDto;
import com.example.server.mapping.RoomMapper;
import com.example.server.repository.ElasticRoomRepository;
import com.example.server.repository.MongoRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    private MongoRoomRepository roomRepository;

    @Autowired
    private ElasticRoomRepository roomElasticRepository;

    @Override
    public List<RoomDto> getAllRooms() {
        var rooms = (List<Room>) roomRepository.findAll();
        return rooms.stream()
                .map(RoomMapper::toDtoModel)
                .collect(Collectors.toList());
    }


    @Override
    public void addRooms(List<RoomDto> roomsDto) {
        List<UUID> ids = IntStream.range(0, roomsDto.size())
                .mapToObj(i -> UUID.randomUUID())
                .collect(Collectors.toList());
        List<Room> rooms = roomsDto.stream()
                .map(roomDto -> RoomMapper.toDataModel(roomDto, ids.get(roomsDto.indexOf(roomDto))))
                .collect(Collectors.toList());
        List<RoomElastic> roomElasticList = roomsDto.stream()
                .map(roomDto -> RoomMapper.toElasticDataModel(roomDto, ids.get(roomsDto.indexOf(roomDto))))
                .collect(Collectors.toList());

        roomRepository.saveAll(rooms);
        roomElasticRepository.saveAll(roomElasticList);
    }

    @Override
    public RoomDto addRoom(RoomDto room) {
        UUID id = UUID.randomUUID();
        roomElasticRepository.save(RoomMapper.toElasticDataModel(room, id));
        Room savedRoom = roomRepository.save(RoomMapper.toDataModel(room, id));
        return RoomMapper.toDtoModel(savedRoom);
    }

    @Override
    public List<Room> getTop3Rooms() {
        return roomRepository.findTop15ByOrderByIdDesc();
    }

    @Override
    public List<RoomElastic> searchCommon(String text) {
        return roomElasticRepository.findTop100ByName(text);
    }

    @Override
    public List<RoomElastic> findAll(String description) {
        return roomElasticRepository.findTop100ByDescriptionOrLocationOrNeighborhoodOverview(description, description, description);
    }

    @Override
    public List<RoomElastic> findAllByPriceIsLessThan(String name) {
        return roomElasticRepository.findTop100ByPriceIsLessThan(name);
    }
}

