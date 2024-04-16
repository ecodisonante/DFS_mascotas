package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;
import com.fullstack.mascotas.model.Venta;

public interface IVentaService {

    List<Venta> getAllVentas();

    Optional<Venta> getVentaById(Long id);

    List<Venta> getVentasByDate(int year, int month, int day) throws Exception;

    Venta createVenta(Venta venta) throws Exception;

    Venta updateVenta(Long id, Venta venta) throws Exception;

    void deleteVenta(Long id) throws Exception;

}
