package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;
import com.fullstack.mascotas.model.Categoria;

public interface ICategoriaService {

    List<Categoria> getAllCategorias();

    Optional<Categoria> getCategoriaById(Long id);

    Categoria createCategoria(Categoria categoria) throws Exception;

    Categoria updateCategoria(Long id, Categoria categoria) throws Exception;

    void deleteCategoria(Long id) throws Exception;

}
