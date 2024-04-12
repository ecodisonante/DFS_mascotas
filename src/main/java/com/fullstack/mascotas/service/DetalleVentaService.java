package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.mascotas.model.DetalleVenta;
import com.fullstack.mascotas.repository.DetalleVentaRepository;

@Service
public class DetalleVentaService implements IDetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleRepository;

    @Override
    public List<DetalleVenta> getAllDetalleVentas() {
        return detalleRepository.findAll();
    }

    @Override
    public Optional<DetalleVenta> getDetalleVentaById(Long id) {
        return detalleRepository.findById(id);
    }

    @Override
    public DetalleVenta createDetalleVenta(DetalleVenta detalle) throws Exception {
        validateDetalleVenta(detalle);
        return detalleRepository.save(detalle);
    }

    @Override
    public DetalleVenta updateDetalleVenta(Long id, DetalleVenta detalle) throws Exception {
        validateDetalleVenta(detalle);

        if (detalleRepository.existsById(id)) {
            detalle.setId(id);
            return detalleRepository.save(detalle);
        }

        throw new Exception("No se encontró DetalleVenta");
    }

    @Override
    public void deleteDetalleVenta(Long id) throws Exception {
        if (detalleRepository.existsById(id)) {
            detalleRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró DetalleVenta ID " + id);
        }
    }

    private void validateDetalleVenta(DetalleVenta detalle) throws Exception {

        if (detalle.getVentaId() == 0)
            throw new Exception("Id de venta no valido");
        if (detalle.getProducto() == null)
            throw new Exception("Producto no ingresado");
        if (detalle.getProducto().getId() == 0)
            throw new Exception("Id de producto no valido");
        if (detalle.getCantidad() == 0)
            throw new Exception("Cantidad de producto debe ser mayor a 0");

    }
}
