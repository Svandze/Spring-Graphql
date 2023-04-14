package com.example.graphql.repository;

import com.example.graphql.model.BatallaPokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BatallaRepository extends MongoRepository<BatallaPokemon, String> {
    BatallaPokemon findByBatallaId(String batallaId);
}
