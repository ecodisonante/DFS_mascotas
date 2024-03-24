package com.fullstack.mascotas.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

public class Venta {
    @Getter private int id;
    @Getter private LocalDateTime fecha;
    @Getter private List<Producto> detalle;
    @Getter private int total;

    public Venta(int id, LocalDateTime fecha, List<Producto> detalle) {
        this.id = id;
        this.fecha = fecha;
        this.detalle = detalle;
        this.total = calcularTotal(detalle);
    }

    private int calcularTotal(List<Producto> detalle) {
        int total = detalle.stream()
                .mapToInt(Producto::getValor)
                .sum();
        return total;
    }
}
