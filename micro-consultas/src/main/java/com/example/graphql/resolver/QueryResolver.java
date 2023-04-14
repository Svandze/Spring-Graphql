package com.example.graphql.resolver;

import com.example.graphql.model.Entrenador;
import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.model.Pokemon;
import com.example.graphql.repository.EntrenadorRepository;
import com.example.graphql.repository.PokemonRepository;

public class QueryResolver implements GraphQLQueryResolver {

	@Autowired
	private PokemonRepository pokemonRepository;

	@Autowired
	private EntrenadorRepository entrenadorRepository;

	public Iterable<Pokemon> findAllPokemons() {
		return pokemonRepository.findAll();
	}

	public Iterable<Pokemon> findAllPokemonsById(Iterable<String> ids) {
		return pokemonRepository.findAllById(ids);
	}

	
	public Iterable<Entrenador> findAllEntrenadores() {
		return entrenadorRepository.findAll();
	}

	public long countPokemons() {
		return (int) pokemonRepository.count();
	}

	public long countEntrenadores() {
		return (int) entrenadorRepository.count();
	}
}
