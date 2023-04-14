package com.example.graphql.resolver;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.graphql.model.Entrenador;
import com.example.graphql.model.Pokemon;
import com.example.graphql.repository.EntrenadorRepository;

public class PokemonResolver implements GraphQLResolver<Pokemon> {

	@Autowired
	private EntrenadorRepository entrenadorRepository;

	public Entrenador getEntrenador(Pokemon pokemon) {
		return entrenadorRepository.findByEntrenadorId(pokemon.getEntrenador().getEntrenadorId());
	}
}
