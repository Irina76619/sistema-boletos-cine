package com.mycompany.boletos;

// Clase encargada de calcular los precios de los boletos.
// La dejamos separada de Boleto porque el precio depende de dos cosas
// (tipo de sala y tipo de persona) y si mezclabamos esto dentro de Boleto
// el codigo se ponia mas dificil de leer. Todo aca es estatico porque
// no necesitamos guardar ningun dato, solo calcular.
public class Tarifario {

    // Tipos de persona validos para el calculo de la tarifa.
    public static final String NINO = "NINO";
    public static final String ADULTO = "ADULTO";
    public static final String DISCAPACITADO = "DISCAPACITADO";

    // Metodo principal: segun el tipo de sala, delega el calculo a un metodo
    // especifico. Esto es para no tener un solo switch gigante mezclando
    // sala y persona a la vez.
    public static double calcularPrecio(String tipoSala, String tipoPersona) {
        switch (tipoSala) {
            case "XTREME":
                return precioXtreme(tipoPersona);
            case "PRIME":
                return precioPrime(tipoPersona);
            case "REGULAR":
            default:
                return precioRegular(tipoPersona);
        }
    }

    // Precios para la sala Regular
    private static double precioRegular(String tipoPersona) {
        switch (tipoPersona) {
            case NINO:
                return 20.00;
            case DISCAPACITADO:
                return 18.00;
            case ADULTO:
            default:
                return 25.00;
        }
    }

    // Precios para la sala Xtreme (mas cara que Regular)
    private static double precioXtreme(String tipoPersona) {
        switch (tipoPersona) {
            case NINO:
                return 24.00;
            case DISCAPACITADO:
                return 22.00;
            case ADULTO:
            default:
                return 29.00;
        }
    }

    // Precios para la sala Prime. Aca niño y discapacitado pagan lo mismo,
    // por eso los dos case caen en el mismo return (no es error, es a proposito).
    private static double precioPrime(String tipoPersona) {
        switch (tipoPersona) {
            case NINO:
                return 39.00;
            case DISCAPACITADO:
                return 35.00;
            case ADULTO:
            default:
                return 46.00;
        }
    }

    // Convierte el tipo de persona (que internamente es un String tipo "NINO")
    // a algo mas presentable para mostrar en pantalla o en el boleto.
    public static String obtenerNombrePersona(String tipoPersona) {
        switch (tipoPersona) {
            case NINO:
                return "Niño";
            case DISCAPACITADO:
                return "Discapacitado";
            case ADULTO:
            default:
                return "Adulto";
        }
    }
}
