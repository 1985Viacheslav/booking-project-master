package com.example.server.data;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Builder
@Document("Clients")
@Data
public class Client {
    private String id;
    private String name;
    private String creationDate;
}





