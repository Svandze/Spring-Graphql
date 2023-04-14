package com.example.graphql.resolver;

import com.example.graphql.model.BatallaPokemon;
import com.example.graphql.model.Entrenador;
import com.example.graphql.repository.BatallaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.exception.PokemonNotFoundException;
import com.example.graphql.model.Pokemon;
import com.example.graphql.repository.EntrenadorRepository;
import com.example.graphql.repository.PokemonRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MutationResolver implements GraphQLMutationResolver {

    Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(MutationResolver.class);
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private  BatallaRepository batallaRepository;
    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public boolean borrarPokemon(String id) {
        pokemonRepository.deleteById(id);
        return true;
    }

    public List<Pokemon> findRandomPokemons() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        return IntStream.range(0, 2)
                .mapToObj(i -> pokemons.get(random.nextInt(pokemons.size())))
                .collect(Collectors.toList());
    }
    public BatallaPokemon batallaPokemon() {
        List<Pokemon> pokemons = findRandomPokemons();
        logger.info("Pokemons: ");
        pokemons.forEach(pokemon -> System.out.println(pokemon.getNombre()));

        BatallaPokemon batalla = BatallaPokemon.builder()
                .pokemon1(pokemons.get(0))
                .pokemon2(pokemons.get(1))
                .build();

        Pokemon pokemon1 = pokemons.get(0);
        Pokemon pokemon2 = pokemons.get(1);

        if (pokemon1.getTipo().equals(pokemon2.getTipo())) {
            if (pokemon1.getNivel() > pokemon2.getNivel()) {
                batalla.setGanador(pokemon1);
            } else if (pokemon1.getNivel() < pokemon2.getNivel()) {
                batalla.setGanador(pokemon2);
            } else {
                // Si ambos tienen el mismo nivel, se elige uno al azar.
                batalla.setGanador(Math.random() < 0.5 ? pokemon1 : pokemon2);
            }
        } else {
            // En caso de que los tipos sean diferentes, el tipo del primer Pokémon
            // se compara con el tipo del segundo Pokémon para decidir el ganador.
            String tipo1 = pokemon1.getTipo();
            String tipo2 = pokemon2.getTipo();
            if (tipo1.equals("Fuego") && tipo2.equals("Planta")
                    || tipo1.equals("Agua") && tipo2.equals("Fuego")
                    || tipo1.equals("Planta") && tipo2.equals("Agua")) {
                logger.info(pokemon1.getNombre() + " Wins");
                batalla.setGanador(pokemon1);
            } else {
                logger.info(pokemon2.getNombre() + " Wins");
                batalla.setGanador(pokemon2);
            }
        }
        batallaRepository.save(batalla);
        return batalla;
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
