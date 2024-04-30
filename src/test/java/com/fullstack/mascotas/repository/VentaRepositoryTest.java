package com.fullstack.mascotas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.model.DetalleVenta;
import com.fullstack.mascotas.model.Producto;
import com.fullstack.mascotas.model.Venta;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VentaRepositoryTest {

    @Autowired
    private VentaRepository repository;

    @Test
    void testFindByDate() {
        // given
        var day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        var year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        Categoria cat = new Categoria(1L, "Categoria Prueba");
        Producto prod = new Producto(1L, "Producto Prueba", 1000, cat);
        DetalleVenta det = new DetalleVenta(0, 1L, prod, 5);
        Venta venta = new Venta(1L, LocalDateTime.now(), List.of(det), 5000);
        repository.saveAllAndFlush(List.of(venta));

        // when
        var resultDay = repository.findByDate(day, "yyyy-mm-dd");
        var resultMonth = repository.findByDate(month, "yyyy-mm");
        var resultYear = repository.findByDate(year, "yyyy");

        // then
        assertEquals(resultDay.size(), resultDay.stream()
                .filter(x -> x.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(day)).count(),
                "Todos los registros corresponden a la fecha indicada");
        assertEquals(resultMonth.size(), resultMonth.stream()
                .filter(x -> x.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM")).equals(month)).count(),
                "Todos los registros corresponden al mes indicado");
        assertEquals(resultYear.size(), resultYear.stream()
                .filter(x -> x.getFecha().format(DateTimeFormatter.ofPattern("yyyy")).equals(year)).count(),
                "Todos los registros corresponden al año indicado");

    }
}
