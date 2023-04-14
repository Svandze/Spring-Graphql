package com.example.graphql.resolver;

import com.example.graphql.model.BatallaPokemon;
import com.example.graphql.model.Entrenador;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.model.Pokemon;
import com.example.graphql.repository.EntrenadorRepository;
import com.example.graphql.repository.PokemonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
