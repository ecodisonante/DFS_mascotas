package com.fullstack.mascotas.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GananciasDTO {
    @Getter int totalVentas;
    @Getter int totalIngresos;
    @Getter List<Venta> detalle;
}
