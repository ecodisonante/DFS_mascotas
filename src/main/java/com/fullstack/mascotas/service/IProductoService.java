package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;
import com.fullstack.mascotas.model.Producto;

public interface IProductoService {

    List<Producto> getAllProductos();

    Optional<Producto> getProductoById(Long id);

    Producto createProducto(Producto producto) throws Exception;

    Producto updateProducto(Long id, Producto producto) throws Exception;

    void deleteProducto(Long id) throws Exception;
}
