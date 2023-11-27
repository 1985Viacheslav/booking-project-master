package com.example.server.utils;

import com.example.server.dto.RoomDto;

import java.io.IOException;
import java.util.List;

public interface RoomsDataImporter {
    List<RoomDto> loadFromCsv(String path) throws IOException;
}
