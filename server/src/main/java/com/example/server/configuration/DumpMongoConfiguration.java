package com.example.server.configuration;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class DumpMongoConfiguration {
    private String database = "";
    private int port = 8000;
    private String host = "localhost";

}

