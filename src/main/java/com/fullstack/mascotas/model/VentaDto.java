package com.fullstack.mascotas.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VentaDto {

    private long id;
    private LocalDateTime fecha;
    private int productos;
    private long total;

}
