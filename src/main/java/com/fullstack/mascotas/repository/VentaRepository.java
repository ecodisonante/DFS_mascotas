package com.fullstack.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.mascotas.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

}
