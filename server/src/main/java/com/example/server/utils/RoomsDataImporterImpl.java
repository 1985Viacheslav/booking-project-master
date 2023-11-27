package com.example.server.utils;

import com.example.server.dto.RoomDto;
import com.example.server.mapping.RoomMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomsDataImporterImpl implements RoomsDataImporter {

    @Override
    public List<RoomDto> loadFromCsv(String path) throws IOException {
        File fileWithRoomData = new File(path);
        List<RoomDto> rooms = null;

        if (!fileWithRoomData.exists()) {
            return rooms;
        }

        CSVParser csvParser = CSVParser.parse(String.valueOf(fileWithRoomData), CSVFormat.DEFAULT.withHeader());
        List<CSVRecord> records = csvParser.getRecords();

        rooms = records.parallelStream()
                .map(RoomMapper::toRoomDtoModel)
                .collect(Collectors.toList());

        return rooms;
    }
}
