package com.fullstack.mascotas.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.mascotas.data.TestingData;
import com.fullstack.mascotas.model.GananciasDTO;
import com.fullstack.mascotas.model.Venta;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class VentasController {

    @Autowired
    TestingData data;

    @GetMapping("/ventas")
    public ResponseEntity<Object> getVentas() {
        List<Venta> ventas = data.ventas;

        if (!ventas.isEmpty())
            return ResponseEntity.ok(ventas);
        else
            return ResponseEntity.ok("Aun no hay ventas registradas");
    }

    @GetMapping("/ventas/fecha")
    public ResponseEntity<Object> getVentasMes(HttpServletRequest request) {

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

            if (day > 0) {
                int anno = year > 0 ? year : LocalDateTime.now().getYear();
                int mes = month > 0 ? month : LocalDateTime.now().getMonthValue();

                // Si es una fecha invalida provocará una excepcion
                LocalDateTime.of(year, month, day, 0, 0, 0);

                ventas = data.ventas.stream()
                        .filter(x -> x.getFecha().getYear() == anno &&
                                x.getFecha().getMonthValue() == mes &&
                                x.getFecha().getDayOfYear() == day)
                        .toList();

            } else if (month > 0) {
                int anno = year > 0 ? year : LocalDateTime.now().getYear();

                if (year < 2020 || year > LocalDateTime.now().getYear())
                    return ResponseEntity.badRequest().body("Año no esta dentro del rango aceptado.");
                if (month < 1 || month > 12)
                    return ResponseEntity.badRequest().body("Mes no esta dentro del rango aceptado.");

                ventas = data.ventas.stream()
                        .filter(x -> x.getFecha().getYear() == anno &&
                                x.getFecha().getMonthValue() == month)
                        .toList();

            } else if (year > 0) {
                if (year < 2020 || year > LocalDateTime.now().getYear())
                    return ResponseEntity.badRequest().body("Año no esta dentro del rango aceptado.");

                ventas = data.ventas.stream().filter(x -> x.getFecha().getYear() == year).toList();
            } else {
                return ResponseEntity.badRequest().body("Parametros incorrectos");
            }

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Parametros incorrectos");
        }

        if (!ventas.isEmpty()){
            int cantidad = ventas.size();
            int ingresos = ventas.stream().mapToInt(Venta::getTotal).sum();

            GananciasDTO ganancias = new GananciasDTO(cantidad, ingresos, ventas);
            return ResponseEntity.ok(ganancias);
        }
        else
            return ResponseEntity.ok("No hay ventas registradas en el rango solicitado");
    }

}
