package com.fullstack.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.mascotas.model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

}
