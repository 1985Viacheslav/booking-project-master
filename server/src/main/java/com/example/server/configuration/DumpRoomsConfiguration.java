package com.example.server.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "dump.rooms")
public class DumpRoomsConfiguration {
    private Boolean needed;
    private String path = "";
    private int flows = 2;
}


