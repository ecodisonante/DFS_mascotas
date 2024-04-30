package com.fullstack.mascotas.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.fullstack.mascotas.service.IVentaService;

import jakarta.servlet.http.HttpServletRequest;

import com.fullstack.mascotas.model.Venta;
import com.fullstack.mascotas.model.GananciasDto;
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
    IVentaService service;

    private static final String VENTAS = "lista-ventas";

    @GetMapping
    public ResponseEntity<Object> getVentasList() {
        List<Venta> ventas = service.getAllVentas();
        if (!ventas.isEmpty()) {

            var ventaRes = ventas.stream()
                    .map(Venta::toDto)
                    .map(cat -> EntityModel.of(cat,
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVenta(cat.getId()))
                                    .withSelfRel()))
                    .collect(Collectors.toList());

            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVentasList());

            return ResponseEntity.ok(CollectionModel.of(ventaRes, linkTo.withRel("ventas")));

        } else {
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay ventas ingresadas"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVenta(@PathVariable Long id) {
        try {
            Optional<Venta> venta = service.getVentaById(id);

            if (venta.isPresent()) {

                var ventaRes = EntityModel.of(venta.get(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVenta(id))
                                .withSelfRel(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVentasList())
                                .withRel(VENTAS));

                return ResponseEntity.ok(ventaRes);

            } else {
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe la venta con el id " + id));
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @GetMapping("/fecha")
    public ResponseEntity<Object> getVentasFecha(HttpServletRequest request) {

        // recibir parametros
        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");
        String dayParam = request.getParameter("day");

        int year, month, day;
        List<Venta> ventas = new ArrayList<>();

        try {
            year = yearParam != null ? Integer.parseInt(yearParam) : 0;
            month = monthParam != null ? Integer.parseInt(monthParam) : 0;
            day = dayParam != null ? Integer.parseInt(dayParam) : 0;

            ventas = service.getVentasByDate(year, month, day);

            if (!ventas.isEmpty()) {

                var ventaRes = ventas.stream()
                        .map(Venta::toDto)
                        .map(cat -> EntityModel.of(cat,
                                WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVenta(cat.getId()))
                                        .withSelfRel()))
                        .collect(Collectors.toList());

                var ganancia = ResponseEntity.ok(
                        new GananciasDto(
                                ventas.size(),
                                ventas.stream().mapToLong(Venta::getTotal).sum(),
                                ventaRes));

                var gananciaRes = EntityModel.of(ganancia,
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVentasList())
                                .withRel(VENTAS));

                return ResponseEntity.ok(gananciaRes);

            } else
                return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(),
                        "No hay ventas registradas en el rango solicitado"));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),
                    "Parametros incorrectos"));
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
            var nuevaVenta = service.createVenta(venta);

            var ventaRes = EntityModel.of(nuevaVenta,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVenta(nuevaVenta.getId()))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVentasList())
                            .withRel(VENTAS));

            return ResponseEntity.ok(ventaRes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        // validar datos
        if (venta.getFecha() == null)
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Fecha de venta no puede estar vacío"));
        if (venta.getDetalle() == null || venta.getDetalle().isEmpty())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Detalle de venta no puede estar vacío"));

        try {
            var actualizaVenta = service.updateVenta(id, venta);

            var ventaRes = EntityModel.of(actualizaVenta,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVenta(id))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getVentasList())
                            .withRel(VENTAS));

            return ResponseEntity.ok(ventaRes);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVenta(@PathVariable Long id) {
        try {
            service.deleteVenta(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Venta eliminada."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
