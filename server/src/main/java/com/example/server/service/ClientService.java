package com.example.server.service;

import com.example.server.data.Client;
import com.example.server.dto.ClientDto;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<Client> getTopClients();
    ClientDto addClient(ClientDto clientDto);
    void addClients(List<ClientDto> clients);
    void deleteClient(UUID uuid);
}

