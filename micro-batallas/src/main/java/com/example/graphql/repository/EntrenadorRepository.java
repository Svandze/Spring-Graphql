package com.example.graphql.repository;

import com.example.graphql.model.Entrenador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntrenadorRepository extends MongoRepository<Entrenador, String> {
	Entrenador findByEntrenadorId(String entrenadorId);
}
