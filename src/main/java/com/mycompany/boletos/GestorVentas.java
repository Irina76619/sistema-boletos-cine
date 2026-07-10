package com.mycompany.boletos;

// Esta clase lleva el control de todas las ventas que se van haciendo.
// Guarda los boletos vendidos en un arreglo y con eso podemos sacar el
// reporte de ventas y el total recaudado.
public class GestorVentas {

    // Capacidad maxima del arreglo de boletos. Como es un arreglo comun
    // (no una lista dinamica) necesitamos definir un tamaño fijo desde el
    // inicio, por eso ponemos un numero razonablemente grande.
    private static final int CAPACIDAD_MAXIMA = 200;

    private final Boleto[] boletosVendidos;
    private int cantidadVendida; // cuantos boletos ya se guardaron realmente

    public GestorVentas() {
        this.boletosVendidos = new Boleto[CAPACIDAD_MAXIMA];
        this.cantidadVendida = 0;
    }

    // Agrega un boleto nuevo al arreglo, siempre que haya espacio. Si el
    // arreglo ya esta lleno avisamos por consola en vez de romper el programa.
    public void registrarVenta(Boleto boleto) {
        if (cantidadVendida < boletosVendidos.length) {
            boletosVendidos[cantidadVendida] = boleto;
            cantidadVendida++;
        } else {
            System.out.println("No se pueden registrar más ventas: se alcanzó la capacidad máxima.");
        }
    }

    // Devolvemos una COPIA del arreglo (del tamaño real usado, no de 200)
    // en vez de devolver el arreglo original. Esto es para que nadie desde
    // afuera pueda modificar directamente nuestro arreglo interno.
    public Boleto[] getBoletosVendidos() {
        Boleto[] copia = new Boleto[cantidadVendida];
        for (int i = 0; i < cantidadVendida; i++) {
            copia[i] = boletosVendidos[i];
        }
        return copia;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    // Suma el precio de todos los boletos vendidos hasta el momento.
    public double calcularTotalRecaudado() {
        double total = 0.0;
        for (int i = 0; i < cantidadVendida; i++) {
            total += boletosVendidos[i].getPrecioFinal();
        }
        return total;
    }

    // Arma el texto del reporte de ventas: lista cada boleto vendido y al
    // final pone el total de boletos y el total recaudado en soles.
    public String generarReporteTexto() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE VENTAS ===\n");

        if (cantidadVendida == 0) {
            reporte.append("Aún no se ha vendido ningún boleto.\n");
        } else {
            for (int i = 0; i < cantidadVendida; i++) {
                reporte.append(boletosVendidos[i].toString()).append("\n");
            }
        }

        reporte.append("--------------------------------\n");
        reporte.append("Boletos vendidos: ").append(cantidadVendida).append("\n");
        reporte.append(String.format("Total recaudado: S/ %.2f%n", calcularTotalRecaudado()));
        return reporte.toString();
    }
}
