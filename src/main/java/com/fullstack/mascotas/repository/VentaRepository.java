package com.fullstack.mascotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.mascotas.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT v FROM Venta v WHERE TO_CHAR(v.fecha, :filtro) = :fecha")
    List<Venta> findByDate(@Param("fecha") String fecha, @Param("filtro") String filtro);

}
