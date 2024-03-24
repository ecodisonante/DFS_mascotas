package com.fullstack.mascotas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Categoria {
    @Getter private int id;
    @Getter private String nombre;
}
