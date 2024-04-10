/**
 * CATEGORIAS
 */

INSERT INTO categoria (id, nombre) VALUES (1, 'Alimentos');
INSERT INTO categoria (id, nombre) VALUES (2, 'Utencilios');
INSERT INTO categoria (id, nombre) VALUES (3, 'Medicamentos');

/**
 * PRODUCTOS
 */

INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (1, 'Comida para perros 1kg', 1, 2500);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (2, 'Comida para gatos 1kg', 1, 4200);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (3, 'Comida para erizos 100g', 1, 5000);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (4, 'Correa para perros', 2, 2000);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (5, 'Collar para gatos', 2, 3000);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (6, 'Cama para erizos', 2, 2700);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (7, 'Antipulgas para perros pipeta', 3, 7500);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (8, 'Shampoo para gatos 250ml', 3, 12000);
INSERT INTO producto (id, nombre, categoria_id, valor) VALUES (9, 'Vitaminas para erizos 100mg', 3, 15000);

/**
 * VENTAS
 */

-- ventas del dia 24 marzo
INSERT INTO venta (id, fecha, total) VALUES (1, TO_DATE('2024/03/24 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (2, TO_DATE('2024/03/24 12:10:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (3, TO_DATE('2024/03/24 12:50:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (4, TO_DATE('2024/03/24 14:15:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (5, TO_DATE('2024/03/24 16:05:00', 'YYYY/MM/DD HH24:MI:SS'), 0);

-- Ventas otros días del mes
INSERT INTO venta (id, fecha, total) VALUES (6, TO_DATE('2024/03/22 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (7, TO_DATE('2024/03/22 15:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (8, TO_DATE('2024/03/18 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (9, TO_DATE('2024/03/15 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (10, TO_DATE('2024/03/04 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);

-- Ventas año 2024
INSERT INTO venta (id, fecha, total) VALUES (11, TO_DATE('2024/02/24 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (12, TO_DATE('2024/02/19 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (13, TO_DATE('2024/02/14 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (14, TO_DATE('2024/02/08 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (15, TO_DATE('2024/01/30 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (16, TO_DATE('2024/01/18 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);
INSERT INTO venta (id, fecha, total) VALUES (17, TO_DATE('2024/01/08 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 0);

/**
 * DETALLE
 */

-- ventas del dia 24 marzo
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (1, 1, 2);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (1, 4, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (1, 7, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (2, 2, 3);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (3, 3, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (3, 6, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (3, 9, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (4, 5, 2);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (4, 8, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (5, 1, 3);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (5, 2, 2);

-- Ventas otros días del mes
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (6, 1, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (7, 2, 2);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (8, 1, 3);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (8, 4, 2);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (8, 7, 2);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (9, 3, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (10, 2, 9);

-- Ventas año 2024
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (11, 1, 5);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (12, 3, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (12, 6, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (13, 2, 9);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (14, 2, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (14, 5, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (14, 8, 1);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (15, 2, 9);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (16, 1, 5);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad) VALUES (17, 2, 9);

