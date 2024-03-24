package com.fullstack.mascotas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.mascotas.data.TestingData;
import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.model.Producto;

@RestController
public class ProductosController {
    @Autowired
    TestingData data;

    @GetMapping("/productos")
    public ResponseEntity<Object> getProductos() {
        List<Producto> prod = data.productos;

        if (!prod.isEmpty())
            return ResponseEntity.ok(prod);
        else
            return ResponseEntity.ok("Aun no hay productos registradas");
    }

    @GetMapping("/productos/cat/{id}")
    public ResponseEntity<Object> getPublicacion(@PathVariable int id) {
        // Comprobar que existe la categoria
        Optional<Categoria> cat = data.categorias.stream().filter(x -> x.getId() == id).findFirst();
        if (!cat.isPresent())
            return ResponseEntity.badRequest().body("No se encontró la categoría " + id);

        // Filtrar productos de esa categoria
        List<Producto> prods = data.productos.stream().filter(x -> x.getCategoria().getId() == id).toList();

        if (!prods.isEmpty())
            return ResponseEntity.ok(prods);
        else
            return ResponseEntity.ok("No existen productos en la categoria " + id);
    }
}
