package com.fullstack.mascotas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.mascotas.model.DetalleVenta;
import com.fullstack.mascotas.model.Venta;
import com.fullstack.mascotas.repository.ProductoRepository;
import com.fullstack.mascotas.repository.VentaRepository;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaService detalleService;
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> getVentaById(Long id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta createVenta(Venta venta) throws Exception {
        venta.setTotal(calcTotal(venta));
        Venta nueva = ventaRepository.save(venta);

        try {

            for (DetalleVenta det : venta.getDetalle()) {
                det.setVentaId(nueva.getId());
                detalleService.createDetalleVenta(det);
            }

            return venta;

        } catch (Exception ex) {
            deleteVenta(nueva.getId());
            throw ex;
        }
    }

    @Override
    public Venta updateVenta(Long id, Venta venta) throws Exception {
        if (ventaRepository.existsById(id)) {
            venta.setId(id);

            for (var det : venta.getDetalle()) {
                det.setVentaId(venta.getId());
                detalleService.updateDetalleVenta(det.getId(), det);
            }

            venta.setTotal(calcTotal(venta));
            return ventaRepository.save(venta);
        } else {
            throw new Exception("No se encontró ID de Venta.");
        }
    }

    @Override
    public void deleteVenta(Long id) throws Exception {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró ID de Venta.");
        }
    }

    private long calcTotal(Venta venta) {
        long total = 0;

        for (var det : venta.getDetalle()) {
            if (det.getProducto().getValor() == 0)
                det.setProducto(productoRepository.findById(det.getProducto().getId()).get());
            total += det.getProducto().getValor() * det.getCantidad();
        }

        return total;
    }
}
