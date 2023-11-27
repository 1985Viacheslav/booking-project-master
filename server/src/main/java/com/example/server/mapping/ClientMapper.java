package com.example.server.mapping;

import com.example.server.data.Client;
import com.example.server.dto.ClientDto;

import java.util.UUID;

public class ClientMapper {
    public static Client toDataModel(ClientDto clientDto) {
        return Client.builder()
                .id(UUID.randomUUID().toString())
                .name(clientDto.getName())
                .creationDate(clientDto.getCreationDate())
                .build();
    }

    public static ClientDto toDtoModel(Client client) {
        return ClientDto.builder()
                .name(client.getName())
                .creationDate(client.getCreationDate())
                .build();
    }
}
