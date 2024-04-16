package com.fullstack.mascotas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.mascotas.service.IProductoService;
import com.fullstack.mascotas.model.Producto;
import com.fullstack.mascotas.model.ResponseDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    IProductoService _service;

    @GetMapping
    public ResponseEntity<Object> getProductosList() {
        List<Producto> productos = _service.getAllProductos();

        if (!productos.isEmpty())
            return ResponseEntity.ok(productos);
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay productos ingresados"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProducto(@PathVariable Long id) {
        try {
            Optional<Producto> producto = _service.getProductoById(id);

            if (!producto.isPresent())
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe el producto con el id " + id));

            return ResponseEntity.ok(producto.get());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProducto(@RequestBody Producto producto) {
        // validar datos
        if (producto.getNombre() == null || producto.getNombre().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Nombre de producto no puede estar vacío"));
        if (producto.getValor() == 0)
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Valor de producto debe ser mayor a 0"));
        if (producto.getCategoria() == null)
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Debe ingresar la categoría del producto"));

        try {
            return ResponseEntity.ok(_service.createProducto(producto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Producto producto) {
        // validar que exista el nombre
        if (producto.getNombre() == null || producto.getNombre().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Nombre de producto no puede estar vacío"));

        try {
            return ResponseEntity.ok(_service.updateProducto(id, producto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProducto(@PathVariable Long id) {
        try {
            _service.deleteProducto(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Categoría eliminada."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
