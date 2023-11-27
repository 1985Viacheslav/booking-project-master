package com.example.server.mapping;

import com.example.server.data.Room;
import com.example.server.data.RoomElastic;
import com.example.server.dto.RoomDto;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.csv.CSVRecord;

import java.util.UUID;

@Data
@Builder
public class RoomMapper {

    public static Room toDataModel(RoomDto roomDto, UUID id) {
        return Room.builder()
                .id(id.toString())
                .name(roomDto.getName())
                .description(roomDto.getDescription())
                .neighborhoodOverview(roomDto.getNeighborhoodOverview())
                .location(roomDto.getLocation())
                .about(roomDto.getAbout())
                .neighbourhood(roomDto.getNeighbourhood())
                .type(roomDto.getType())
                .price(roomDto.getPrice())
                .reviewsPerMonth(roomDto.getReviewsPerMonth())
                .build();
    }

    public static RoomElastic toElasticDataModel(RoomDto roomDto, UUID id) {
        return RoomElastic.builder()
                .id(id.toString())
                .name(roomDto.getName())
                .description(roomDto.getDescription())
                .neighborhoodOverview(roomDto.getNeighborhoodOverview())
                .location(roomDto.getLocation())
                .about(roomDto.getAbout())
                .neighbourhood(roomDto.getNeighbourhood())
                .type(roomDto.getType())
                .price(roomDto.getPrice())
                .reviewsPerMonth(roomDto.getReviewsPerMonth())
                .build();
    }

    public static RoomDto toDtoModel(Room room) {
        return RoomDto.builder()
                .name(room.getName())
                .description(room.getDescription())
                .neighborhoodOverview(room.getNeighborhoodOverview())
                .location(room.getLocation())
                .about(room.getAbout())
                .neighbourhood(room.getNeighbourhood())
                .type(room.getType())
                .price(room.getPrice())
                .reviewsPerMonth(room.getReviewsPerMonth())
                .build();
    }

    public static RoomDto toRoomDtoModel(CSVRecord csvRecord) {
        return RoomDto.builder()
                .name(csvRecord.get("name"))
                .description(csvRecord.get("description"))
                .neighborhoodOverview(csvRecord.get("neighborhood_overview"))
                .location(csvRecord.get("host_location"))
                .about(csvRecord.get("host_about"))
                .neighbourhood(csvRecord.get("host_neighbourhood"))
                .type(csvRecord.get("room_type"))
                .price(csvRecord.get("price"))
                .reviewsPerMonth(csvRecord.get("reviews_per_month"))
                .build();
    }
}
