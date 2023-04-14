package com.example.graphql.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Entrenador {
    @Id
    private String entrenadorId;

    private String nombre;

    private String apellido;


}
