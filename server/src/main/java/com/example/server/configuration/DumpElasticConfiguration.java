package com.example.server.configuration;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "elastic.rooms")
public class DumpElasticConfiguration {
    private String index;
    private String type;
}

