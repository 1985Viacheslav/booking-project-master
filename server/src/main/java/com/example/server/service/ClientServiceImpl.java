package com.example.server.service;

import com.example.server.data.Client;
import com.example.server.dto.ClientDto;
import com.example.server.mapping.ClientMapper;
import com.example.server.repository.MongoClientRepository;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service("clientService")
public class ClientServiceImpl implements ClientService {


    private final MongoClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(MongoClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getTopClients() {
        return clientRepository.findTop10ByOrderByCreationDateDesc();
    }

    @Override
    public ClientDto addClient(ClientDto clientDto) {
        Client clientData = ClientMapper.toDataModel(clientDto);
        Client insertedClientInstance = clientRepository.save(clientData);
        return ClientMapper.toDtoModel(insertedClientInstance);
    }

    @Override
    public void addClients(List<ClientDto> clients) {
        clients.parallelStream().forEach(clientDto -> addClient(clientDto));
    }

    @Override
    public void deleteClient(UUID uuid) {
        clientRepository.deleteById(uuid);
    }
}

