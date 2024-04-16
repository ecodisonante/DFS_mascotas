package com.fullstack.mascotas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.mascotas.service.ICategoriaService;
import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.model.ResponseDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    ICategoriaService _service;

    @GetMapping
    public ResponseEntity<Object> getCategoriasList() {
        List<Categoria> categorias = _service.getAllCategorias();

        if (!categorias.isEmpty())
            return ResponseEntity.ok(categorias);
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay categorias ingresadas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoria(@PathVariable Long id) {
        try {
            Optional<Categoria> categoria = _service.getCategoriaById(id);

            if (!categoria.isPresent())
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe la categoria con el id " + id));

            return ResponseEntity.ok(categoria.get());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCategoria(@RequestBody Categoria categoria) {
        //validar que exista el nombre
        if (categoria.getNombre() == null || categoria.getNombre().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Nombre de categoria no puede estar vacío"));

        try {
            return ResponseEntity.ok(_service.createCategoria(categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Categoria categoria) {
        //validar que exista el nombre
        if (categoria.getNombre() == null || categoria.getNombre().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Nombre de categoria no puede estar vacío"));

        try {
            return ResponseEntity.ok(_service.updateCategoria(id, categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
        try {
            _service.deleteCategoria(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Categoría eliminada."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
