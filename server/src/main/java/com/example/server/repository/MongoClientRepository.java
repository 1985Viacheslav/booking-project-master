package com.example.server.repository;

import com.example.server.data.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MongoClientRepository extends CrudRepository<Client, UUID> {
    List<Client> findTop10ByOrderByCreationDateDesc();
}

