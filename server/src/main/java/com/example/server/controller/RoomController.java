package com.example.server.controller;

import com.example.server.data.Room;
import com.example.server.data.RoomElastic;
import com.example.server.dto.RoomDto;
import com.example.server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> top3Rooms() {
        return roomService.getTop3Rooms();
    }

    @PostMapping
    public void addRoom(@RequestBody RoomDto roomDto) {
        roomService.addRoom(roomDto);
    }

    @PostMapping("try")
    public List<RoomElastic> elasticSearch(@RequestBody String text) {
        return roomService.searchCommon(text);
    }

    @GetMapping("textSearch")
    public List<RoomElastic> allSearchFind(@RequestParam String description) {
        return roomService.findAll(description);
    }

    @GetMapping("priceSearch")
    public List<RoomElastic> priceSearchFind(@RequestParam String text) {
        return roomService.findAllByPriceIsLessThan(text);
    }
}
