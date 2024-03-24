package com.fullstack.mascotas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Producto {

    @Getter private int id;
    @Getter private String nombre;
    @Getter private Categoria categoria;
    @Getter private int valor;
    
}
