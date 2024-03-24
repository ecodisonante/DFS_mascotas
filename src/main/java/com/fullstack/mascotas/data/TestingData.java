package com.fullstack.mascotas.data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fullstack.mascotas.model.Categoria;
import com.fullstack.mascotas.model.Producto;
import com.fullstack.mascotas.model.Venta;

@Repository
public class TestingData {

    public Categoria cat1 = new Categoria(1, "Alimentos");
    public Categoria cat2 = new Categoria(2, "Utensilios");
    public Categoria cat3 = new Categoria(3, "Medicamentos");

    public List<Categoria> categorias = Arrays.asList(cat1, cat2, cat3);

    public Producto prod1 = new Producto(1, "Comida para perros 1kg", cat1, 2500);
    public Producto prod2 = new Producto(2, "Comida para gatos 1kg", cat1, 4200);
    public Producto prod3 = new Producto(3, "Comida para erizos 100g", cat1, 5000);
    public Producto prod4 = new Producto(4, "Correa para perros", cat2, 2000);
    public Producto prod5 = new Producto(5, "Collar para gatos", cat2, 3000);
    public Producto prod6 = new Producto(6, "Cama para erizos", cat2, 2700);
    public Producto prod7 = new Producto(7, "Antipulgas para perros pipeta", cat3, 7500);
    public Producto prod8 = new Producto(8, "Shampoo para gatos 250ml", cat3, 12000);
    public Producto prod9 = new Producto(9, "Vitaminas para erizos 100mg", cat3, 15000);

    public List<Producto> productos = Arrays.asList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9);

    // ventas del dia 24 marzo
    Venta v1 = new Venta(1, LocalDateTime.of(2024, 3, 24, 10, 30, 0), Arrays.asList(prod1, prod1, prod4, prod7));
    Venta v2 = new Venta(1, LocalDateTime.of(2024, 3, 24, 12, 10, 0), Arrays.asList(prod2, prod2, prod2));
    Venta v3 = new Venta(1, LocalDateTime.of(2024, 3, 24, 12, 50, 0), Arrays.asList(prod3, prod6, prod9));
    Venta v4 = new Venta(1, LocalDateTime.of(2024, 3, 24, 14, 15, 0), Arrays.asList(prod5, prod5, prod8));
    Venta v5 = new Venta(1, LocalDateTime.of(2024, 3, 24, 16, 05, 0), Arrays.asList(prod1, prod1, prod1, prod2, prod2));

    // Ventas otros días del mes
    Venta v6 = new Venta(1, LocalDateTime.of(2024, 3, 22, 10, 30, 0), Arrays.asList(prod1));
    Venta v7 = new Venta(1, LocalDateTime.of(2024, 3, 22, 15, 30, 0), Arrays.asList(prod2, prod2));
    Venta v8 = new Venta(1, LocalDateTime.of(2024, 3, 18, 10, 30, 0), Arrays.asList(prod1, prod1, prod1, prod1, prod4, prod4, prod7, prod7));
    Venta v9 = new Venta(1, LocalDateTime.of(2024, 3, 15, 10, 30, 0), Arrays.asList(prod3));
    Venta v10 = new Venta(1, LocalDateTime.of(2024, 3, 4, 10, 30, 0), Arrays.asList(prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2));

    // Ventas año 2024
    Venta v11 = new Venta(1, LocalDateTime.of(2024, 2, 24, 10, 30, 0), Arrays.asList(prod1, prod1, prod1, prod1, prod1));
    Venta v12 = new Venta(1, LocalDateTime.of(2024, 2, 19, 10, 30, 0), Arrays.asList(prod3, prod6));
    Venta v13 = new Venta(1, LocalDateTime.of(2024, 2, 14, 10, 30, 0), Arrays.asList(prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2));
    Venta v14 = new Venta(1, LocalDateTime.of(2024, 2, 8, 10, 30, 0), Arrays.asList(prod2, prod5, prod8));
    Venta v15 = new Venta(1, LocalDateTime.of(2024, 1, 30, 10, 30, 0), Arrays.asList(prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2));
    Venta v16 = new Venta(1, LocalDateTime.of(2024, 1, 18, 10, 30, 0), Arrays.asList(prod1, prod1, prod1, prod1, prod1));
    Venta v17 = new Venta(1, LocalDateTime.of(2024, 1, 8, 10, 30, 0), Arrays.asList(prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2, prod2));

    public List<Venta> ventas = Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17);

}
