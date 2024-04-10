package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.repository.CategoriaRepository;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> getCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria createCategoria(Categoria categoria) throws Exception {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria updateCategoria(Long id, Categoria categoria) throws Exception {
        if (categoriaRepository.existsById(id)) {
            categoria.setId(id);
            return categoriaRepository.save(categoria);
        } else {
            throw new Exception("No se encontró ID de Categoría.");
        }
    }

    @Override
    public void deleteCategoria(Long id) throws Exception {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró ID de Categoría.");
        }
    }

}
