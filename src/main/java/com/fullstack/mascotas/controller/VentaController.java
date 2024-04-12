package com.fullstack.mascotas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.mascotas.service.IVentaService;
import com.fullstack.mascotas.model.Venta;
import com.fullstack.mascotas.model.ResponseDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    IVentaService _service;

    @GetMapping
    public ResponseEntity<Object> getVentasList() {
        List<Venta> ventas = _service.getAllVentas();
        if (!ventas.isEmpty())
            return ResponseEntity.ok(ventas);
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay ventas ingresadas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVenta(@PathVariable Long id) {
        try {
            Optional<Venta> venta = _service.getVentaById(id);

            if (!venta.isPresent())
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe la venta con el id " + id));

            return ResponseEntity.ok(venta.get());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createVenta(@RequestBody Venta venta) {
        // validar datos
        if (venta.getFecha() == null)
            venta.setFecha(LocalDateTime.now());
        if (venta.getDetalle() == null || venta.getDetalle().isEmpty())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Detalle de venta no puede estar vacío"));

        try {
            return ResponseEntity.ok(_service.createVenta(venta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Venta venta) {
        // validar datos
        if (venta.getFecha() == null)
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Fecha de venta no puede estar vacío"));
        if (venta.getDetalle() == null || venta.getDetalle().isEmpty())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Detalle de venta no puede estar vacío"));

        try {
            return ResponseEntity.ok(_service.updateVenta(id, venta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVenta(@PathVariable Long id) {
        try {
            _service.deleteVenta(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Venta eliminada."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
