package com.example.server.data;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import lombok.Data;

@Builder
@Data
@Document(indexName = "booking")
public class RoomElastic {
    @Id
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
