package com.example.graphql.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class BatallaPokemon {
    @Id
    private String batallaId;
    private Pokemon ganador;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
}
