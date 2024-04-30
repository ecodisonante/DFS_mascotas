package com.fullstack.mascotas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    ICategoriaService service;

    private static final String CATEGORIAS = "lista-categorias";

    @GetMapping
    public ResponseEntity<Object> getCategoriasList() {
        List<Categoria> categorias = service.getAllCategorias();

        if (!categorias.isEmpty()) {

            var categoriaRes = categorias.stream()
                    .map(cat -> EntityModel.of(cat,
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoria(cat.getId()))
                                    .withSelfRel()))
                    .collect(Collectors.toList());

            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoriasList());

            return ResponseEntity.ok(CollectionModel.of(categoriaRes, linkTo.withRel("categorias")));

        } else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay categorias ingresadas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoria(@PathVariable Long id) {
        try {
            Optional<Categoria> categoria = service.getCategoriaById(id);

            if (categoria.isPresent()) {

                var categoriaRes = EntityModel.of(categoria.get(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoria(id))
                                .withSelfRel(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoriasList())
                                .withRel(CATEGORIAS));

                return ResponseEntity.ok(categoriaRes);

            } else {
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe la categoria con el id " + id));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCategoria(@RequestBody Categoria categoria) {

        // validar que exista el nombre
        if (categoria.getNombre() == null || categoria.getNombre().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Nombre de categoria no puede estar vacío"));

        try {

            var nuevaCat = service.createCategoria(categoria);

            var categoriaRes = EntityModel.of(nuevaCat,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoria(nuevaCat.getId()))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoriasList())
                            .withRel(CATEGORIAS));

            return ResponseEntity.ok(categoriaRes);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Categoria categoria) {
        // validar que exista el nombre
        if (categoria.getNombre() == null || categoria.getNombre().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Nombre de categoria no puede estar vacío"));

        try {

            var actualizaCat = service.updateCategoria(id, categoria);

            var categoriaRes = EntityModel.of(actualizaCat,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoria(id))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getCategoriasList())
                            .withRel(CATEGORIAS));

            return ResponseEntity.ok(categoriaRes);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategoria(@PathVariable Long id) {
        try {
            service.deleteCategoria(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Categoría eliminada."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
