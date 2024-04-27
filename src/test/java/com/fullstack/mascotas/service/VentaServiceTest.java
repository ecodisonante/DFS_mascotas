package com.fullstack.mascotas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.model.DetalleVenta;
import com.fullstack.mascotas.model.Producto;
import com.fullstack.mascotas.model.Venta;
import com.fullstack.mascotas.repository.VentaRepository;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @InjectMocks
    VentaService ventaService;

    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private DetalleVentaService detalleService;

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
    void testGetAllVentas() {
        // when
        when(ventaRepository.findAll()).thenReturn(ventas);
        var result = ventaService.getAllVentas();
        // then
        assertEquals(ventas.size(), result.size(),
                "El resultado contiene la misma cantidad de objetos");
        assertEquals(prod.getNombre(), result.getFirst().getDetalle().getFirst().getProducto().getNombre(),
                "El nombre de la categoria es el de prueba");
    }

    @Test
    void testGetVentaById() {
        // when
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        // then
        assertTrue(ventaService.getVentaById(1L).isPresent());
        assertEquals(venta, ventaService.getVentaById(1L).get(),
                "El objeto retornado es el mismo de prueba");
    }

    @Test
    void testGetVentasByDate() throws Exception {
        // given
        var day = LocalDateTime.now().getDayOfMonth();
        var month = LocalDateTime.now().getMonthValue();
        var year = LocalDateTime.now().getYear();

        // when
        when(ventaRepository.findByDate(String.format("%d-%02d-%02d", new Object[] { year, month, day }), "yyyy-mm-dd"))
                .thenReturn(ventas);
        when(ventaRepository.findByDate(String.format("%d-%02d", new Object[] { year, month }), "yyyy-mm"))
                .thenReturn(ventas);
        when(ventaRepository.findByDate(String.format("%04d", year), "yyyy")).thenReturn(ventas);

        var resultDay = ventaService.getVentasByDate(0, 0, day);
        var resultMonth = ventaService.getVentasByDate(0, month, 0);
        var resultYear = ventaService.getVentasByDate(year, 0, 0);

        // then
        assertEquals(ventas, resultDay);
        assertEquals(ventas, resultMonth);
        assertEquals(ventas, resultYear);
        assertThrows(Exception.class, () -> ventaService.getVentasByDate(0, 0, 0));
    }

    @Test
    void testCreateVenta() throws Exception {
        // when
        when(ventaRepository.save(venta)).thenReturn(venta);
        when(detalleService.createDetalleVenta(det)).thenReturn(det);

        var result = ventaService.createVenta(venta);
        // then
        assertEquals(venta, result);
        assertThrows(Exception.class, () -> ventaService.createVenta(new Venta()));
    }

    @Test
    void testUpdateVenta() throws Exception {
        // given
        // when
        when(ventaRepository.existsById(1L)).thenReturn(true);
        when(ventaRepository.save(venta)).thenReturn(venta);
        when(detalleService.updateDetalleVenta(1L, det)).thenReturn(det);

        var result = ventaService.updateVenta(1L, venta);
        // then
        assertEquals(venta, result);
        assertThrows(Exception.class, () -> ventaService.updateVenta(999L, venta));
    }

    @Test
    void testGetVasdentaById() throws Exception {
        // given
        // when
        when(ventaRepository.existsById(1L)).thenReturn(true);
        ventaService.deleteVenta(1L);

        // then
        assertThrows(Exception.class, () -> ventaService.deleteVenta(100L));
        verify(ventaRepository, times(1)).deleteById(any());

    }
}
