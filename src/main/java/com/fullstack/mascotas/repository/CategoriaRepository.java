package com.fullstack.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.mascotas.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
