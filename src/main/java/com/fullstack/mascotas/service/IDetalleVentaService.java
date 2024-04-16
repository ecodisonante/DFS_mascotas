package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;
import com.fullstack.mascotas.model.DetalleVenta;

public interface IDetalleVentaService {

    List<DetalleVenta> getAllDetalleVentas();

    Optional<DetalleVenta> getDetalleVentaById(Long id);

    DetalleVenta createDetalleVenta(DetalleVenta detalleventa) throws Exception;

    DetalleVenta updateDetalleVenta(Long id, DetalleVenta detalleventa) throws Exception;

    void deleteDetalleVenta(Long id) throws Exception;

}
