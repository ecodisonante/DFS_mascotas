package com.fullstack.mascotas.model;

import java.util.List;

import org.springframework.hateoas.EntityModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GananciasDto {

    int totalVentas;
    long totalIngresos;
    List<EntityModel<VentaDto>> detalle;

}
