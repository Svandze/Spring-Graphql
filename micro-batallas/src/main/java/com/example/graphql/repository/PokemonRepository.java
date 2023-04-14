package com.example.graphql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.graphql.model.Pokemon;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {
	Pokemon findByPokemonId(String pokemonId);
}
