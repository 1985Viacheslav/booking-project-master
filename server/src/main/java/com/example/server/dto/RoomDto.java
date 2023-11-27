package com.example.server.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoomDto {
    private String name;
    private String description;
    private String neighborhoodOverview;
    private String location;
    private String about;
    private String neighbourhood;
    private String type;
    private String price;
    private String reviewsPerMonth;
}
