package com.example.server.configuration;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "dump.clients")
public class DumpClientsConfiguration {
    private Boolean needed;
    private String path = "";
    private int flows = 2;
}

