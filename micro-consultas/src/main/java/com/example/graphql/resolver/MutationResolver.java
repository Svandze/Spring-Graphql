package com.example.graphql.resolver;

import com.example.graphql.model.Entrenador;
import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.exception.PokemonNotFoundException;
import com.example.graphql.model.Pokemon;
import com.example.graphql.repository.EntrenadorRepository;
import com.example.graphql.repository.PokemonRepository;

public class MutationResolver implements GraphQLMutationResolver {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public Entrenador newEntrenador(String firstName, String lastName) {
        Entrenador entrenador = Entrenador.builder()
                .nombre(firstName)
                .apellido(lastName)
                .build();
        entrenadorRepository.save(entrenador);
        return entrenador;
    }

    public Pokemon newPokemon(String nombre, String tipo, Integer nivel, String entrenadorId) {
        Pokemon pokemon = Pokemon.builder()
                .entrenador(Entrenador.builder()
                        .entrenadorId(entrenadorId).build())
                .nombre(nombre)
                .tipo(tipo)
                .nivel(nivel != null ? nivel : 0).build();
        pokemonRepository.save(pokemon);

        return pokemon;
    }

    public boolean borrarPokemon(String id) {
        pokemonRepository.deleteById(id);
        return true;
    }

    public Pokemon actualizarNivel(Integer nivel, String id) {
        Pokemon pokemon = pokemonRepository.findByPokemonId(id);
        if (pokemon == null) {
            throw new PokemonNotFoundException("The pokemon not found - id: ", id);
        }
        pokemon.setNivel(nivel);

        pokemonRepository.save(pokemon);

        return pokemon;
    }
}
