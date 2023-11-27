package com.example.server.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("Rooms")
@Data
public class Room {
    private String id;
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
