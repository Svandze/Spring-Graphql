package com.example.graphql.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Pokemon {
    @Id
    private String pokemonId;


    private String nombre;


    private String tipo;


    private int nivel;


    private Entrenador entrenador;


}
