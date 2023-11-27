package com.example.dataprocessor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Properties;

@Data
@NoArgsConstructor
public class TransferConfiguration {
    private String clientsPath;
    private String roomsPath;
    private String elasticHost;
    private String index;
    private int elasticPort;
    private String mongoDatabase;
    private String mongoHost;
    private int mongoPort;
    private int flows;
    private String host;

    public static TransferConfiguration toTransferConfiguration(Properties properties) {
        TransferConfiguration configuration = new TransferConfiguration();
        configuration.setClientsPath(properties.getProperty("dump.clients.path"));
        configuration.setRoomsPath(properties.getProperty("dump.rooms.path"));
        configuration.setElasticHost(properties.getProperty("elastic.host"));
        configuration.setIndex(properties.getProperty("elastic.rooms.index"));
        configuration.setElasticPort(Integer.parseInt(properties.getProperty("elastic.port")));
        configuration.setMongoDatabase(properties.getProperty("mongo.database"));
        configuration.setMongoHost(properties.getProperty("mongo.host"));
        configuration.setMongoPort(Integer.parseInt(properties.getProperty("mongo.port")));
        configuration.setFlows(Integer.parseInt(properties.getProperty("dump.flows")));
        return configuration;
    }


}



