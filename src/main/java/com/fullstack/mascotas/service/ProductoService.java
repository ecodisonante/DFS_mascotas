package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.mascotas.model.Producto;
import com.fullstack.mascotas.repository.ProductoRepository;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto createProducto(Producto producto) throws Exception {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Long id, Producto producto) throws Exception {
        if (productoRepository.existsById(id)) {
            producto.setId(id);
            return productoRepository.save(producto);
        } else {
            throw new Exception("No se encontró ID de Categoría.");
        }
    }

    @Override
    public void deleteProducto(Long id) throws Exception {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró ID de Categoría.");
        }
    }

}
