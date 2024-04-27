package com.fullstack.mascotas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.model.DetalleVenta;
import com.fullstack.mascotas.model.GananciasDto;
import com.fullstack.mascotas.model.Producto;
import com.fullstack.mascotas.model.ResponseDTO;
import com.fullstack.mascotas.model.Venta;
import com.fullstack.mascotas.service.IVentaService;

@ExtendWith(MockitoExtension.class)
public class VentaControllerTest {

    @InjectMocks
    private VentaController controller;

    @Mock
    private IVentaService service;

    private Categoria cat;
    private Producto prod;
    private DetalleVenta det;
    private Venta venta;
    private List<Venta> ventas;

    @BeforeEach
    public void setup() {

        cat = new Categoria(1L, "Categoria Prueba");
        prod = new Producto(1L, "Producto Prueba", 1000, cat);
        det = new DetalleVenta(1L, 1L, prod, 5);
        venta = new Venta(1L, LocalDateTime.now(), List.of(det), 5000);
        ventas = List.of(venta);
    }

    @Test
    void testGetVentasList() {
        // when
        when(service.getAllVentas()).thenReturn(ventas);
        var result = controller.getVentasList();

        // Full List
        assertEquals(CollectionModel.class, result.getBody().getClass());
        assertEquals(ventas.size(), ((CollectionModel) result.getBody()).getContent().size());

        // Empty List
        when(service.getAllVentas()).thenReturn(new ArrayList<>());
        result = controller.getVentasList();
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("No hay ventas ingresadas", ((ResponseDTO) result.getBody()).getMessage());
    }

    @Test
    void testGetVenta() {
        // when
        when(service.getVentaById(1L)).thenReturn(Optional.of(venta));
        when(service.getVentaById(2L)).thenReturn(Optional.empty());

        var result = controller.getVenta(1L);
        var badResult = controller.getVenta(2L);

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Venta.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Empty
        assertEquals(ResponseDTO.class, badResult.getBody().getClass());
        assertEquals("No existe la venta con el id 2", ((ResponseDTO) badResult.getBody()).getMessage());
    }

    @Test
    void testGetVentasFecha() throws Exception {
        // given
        var fecha = LocalDateTime.now();
        var day = String.format("%02d", fecha.getDayOfMonth());
        var month = String.format("%02d", fecha.getMonthValue());
        var year = String.format("%04d", fecha.getYear());
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        when(service.getVentasByDate(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<Venta>());
        when(service.getVentasByDate(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth()))
                .thenReturn(ventas);

        // Response Empty
        request.setParameter("year", "0");
        request.setParameter("month", "0");
        request.setParameter("day", "0");
        var responseEmpty = controller.getVentasFecha(request);

        assertEquals(ResponseDTO.class, responseEmpty.getBody().getClass());
        assertEquals("No hay ventas registradas en el rango solicitado",
                ((ResponseDTO) responseEmpty.getBody()).getMessage());

        // Response OK
        request.setParameter("year", year);
        request.setParameter("month", month);
        request.setParameter("day", day);
        var responseOK = controller.getVentasFecha(request);

        assertEquals(EntityModel.class, responseOK.getBody().getClass());
        assertEquals(GananciasDto.class,
                ((ResponseEntity) ((EntityModel) responseOK.getBody()).getContent()).getBody().getClass());

        // Response Error
        request.setParameter("year", "2022");
        request.setParameter("month", "12");
        request.setParameter("day", "definitivamente no soy un dia");
        var responseError = controller.getVentasFecha(request);

        assertEquals(ResponseDTO.class, responseError.getBody().getClass());
        assertEquals("Parametros incorrectos", ((ResponseDTO) responseError.getBody()).getMessage());
    }

    @Test
    void testCreateVenta() throws Exception {
        // when
        when(service.createVenta(venta)).thenReturn(venta);

        var result = controller.createVenta(venta);
        venta.setDetalle(null);
        var badResult = controller.createVenta(venta);

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Venta.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Error
        assertEquals(ResponseDTO.class, badResult.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, badResult.getStatusCode());
    }

    @Test
    void testUpdateVenta() throws Exception {
        // when
        when(service.updateVenta(1L, venta)).thenReturn(venta);

        var result = controller.updateVenta(1L, venta);
        venta.setDetalle(null);
        var badResult = controller.createVenta(venta);

        // Result OK
        assertEquals(EntityModel.class, result.getBody().getClass());
        assertEquals(Venta.class, ((EntityModel) result.getBody()).getContent().getClass());

        // Result Error
        assertEquals(ResponseDTO.class, badResult.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, badResult.getStatusCode());
    }

    @Test
    void testDeleteVenta() {
        // when
        var result = controller.deleteVenta(1L);

        // Result OK
        assertEquals(ResponseDTO.class, result.getBody().getClass());
        assertEquals("Venta eliminada.", ((ResponseDTO) result.getBody()).getMessage());
    }
}
