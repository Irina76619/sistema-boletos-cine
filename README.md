# Sistema de Venta de Boletos - Sala de Cine

Proyecto final del curso **Taller de Programación** (Sección 51037) - Universidad Tecnológica del Perú (UTP).

**Ejercicio 6:** Plataforma interactiva para la compra de boletos en una sala de cine.

## Integrantes (Grupo 5)

- Bobadilla Chuquizuta, Flavio Sebastian
- Cortez Espinoza, Irina
- Fernandez Rivera, Felipe Sebastian Eugenio
- Martinez Paucar, Leo Xandhe

## Descripción

Aplicación de consola desarrollada en Java que permite gestionar la venta de boletos de una sala de cine mediante una matriz de asientos. El sistema permite:

- Seleccionar el tipo de sala (Regular, Xtreme o Prime).
- Visualizar la distribución de los asientos mediante una matriz.
- Registrar el estado de cada asiento (disponible, reservado o comprado).
- Realizar nuevas reservas y compras, actualizando automáticamente la matriz de la sala.
- Calcular el precio del boleto según el tipo de sala y el tipo de cliente (adulto, niño, discapacitado).
- Generar un reporte de ventas y exportarlo en formato `.txt` y `.html`.

## Estructura del proyecto

```
boletos/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── Utilitarios/
    │   │   │   ├── EntradaUtil.java
    │   │   │   └── GestorArchivoTexto.java
    │   │   └── com/mycompany/boletos/
    │   │       ├── Asiento.java
    │   │       ├── Boleto.java
    │   │       ├── CineBoletos.java
    │   │       ├── GestorVentas.java
    │   │       ├── SalaCine.java
    │   │       └── Tarifario.java
    │   └── resources/
    │       ├── reporte_ventas.html
    │       └── reporte_ventas.txt
    └── test/
        └── java/
```

## Tecnologías

- Java
- Apache Maven
- Apache NetBeans (IDE utilizado para el desarrollo)

## Cómo compilar y ejecutar

### Con NetBeans

1. Abrir Apache NetBeans.
2. `File > Open Project` y seleccionar la carpeta `boletos/`.
3. Click derecho sobre el proyecto → `Run`.

### Con Maven desde la terminal

```bash
git clone https://github.com/tu-usuario/sistema-boletos-cine.git
cd sistema-boletos-cine
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.boletos.CineBoletos"
```

> Si el proyecto no tiene configurado el plugin `exec-maven-plugin`, también puedes compilar con `mvn package` y ejecutar el `.jar` generado en la carpeta `target/`.

## Menú principal

```
===== SISTEMA DE VENTA DE BOLETOS - SALA DE CINE =====
1. Ver mapa de asientos
2. Reservar un asiento
3. Comprar un asiento (generar boleto)
4. Ver reporte de ventas
5. Guardar reporte de ventas (.txt y .html)
6. Salir
```

## Documentación del proyecto

El informe completo (aspectos generales, diseño UML, desarrollo y pruebas de escritorio) se encuentra en el documento `TALLER_PROGRAMACION_TRABAJO_FINAL.docx` de este repositorio / entregado en el aula virtual.

## Licencia

Proyecto académico desarrollado con fines educativos para el curso Taller de Programación - UTP, 2026.
