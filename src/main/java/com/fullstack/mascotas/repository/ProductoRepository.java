package com.fullstack.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.mascotas.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
