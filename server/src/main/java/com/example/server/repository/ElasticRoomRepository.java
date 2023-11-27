package com.example.server.repository;

import com.example.server.data.RoomElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticRoomRepository extends ElasticsearchRepository<RoomElastic, String> {
    List<RoomElastic> findTop100ByName(String name);

    List<RoomElastic> findTop100ByDescriptionOrLocationOrNeighborhoodOverview(String description, String location, String neighborhoodOverview);

    List<RoomElastic> findTop100ByPriceIsLessThan(String price);
}

