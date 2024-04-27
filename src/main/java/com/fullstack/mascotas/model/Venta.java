package com.fullstack.mascotas.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "venta")
public class Venta extends RepresentationModel<Venta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "ventaId", cascade = CascadeType.REMOVE)
    private List<DetalleVenta> detalle;

    @Column(name = "total")
    private long total;

    public VentaDto toDto() {
        return new VentaDto(id, fecha, detalle.size(), total);
    }

}
