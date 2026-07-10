package com.mycompany.boletos;

import Utilitarios.EntradaUtil;
import Utilitarios.GestorArchivoTexto;
import java.util.Scanner;

// Clase principal del programa (Main). Aca NO va la logica del negocio,
// solo se encarga de mostrar el menu, leer lo que elige el usuario y
// llamar a los metodos de las otras clases (SalaCine, GestorVentas, etc).
// Toda la logica "de verdad" esta repartida en las demas clases.
public class CineBoletos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Antes de armar el menu principal, el usuario elige el tipo de sala
        // con la que va a trabajar durante toda la ejecucion del programa.
        String tipoSala = elegirTipoSala(scanner);
        SalaCine sala = SalaCine.crearSegunTipo("Sala 1", tipoSala);
        GestorVentas ventas = new GestorVentas();

        // Menu principal: se repite con un do-while hasta que el usuario
        // elija la opcion 6 (salir). Usamos do-while porque el menu se
        // tiene que mostrar SI o SI al menos una vez.
        int opcion;
        do {
            mostrarMenu();
            opcion = EntradaUtil.leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    System.out.println(sala.dibujarMapa());
                    break;
                case 2:
                    procesarReserva(scanner, sala);
                    break;
                case 3:
                    procesarCompra(scanner, sala, ventas);
                    break;
                case 4:
                    System.out.println(ventas.generarReporteTexto());
                    break;
                case 5:
                    procesarGuardarReporte(ventas);
                    break;
                case 6:
                    System.out.println("Gracias por usar el sistema de la sala de cine. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }

        } while (opcion != 6);

        scanner.close();
    }

    // Pide el tipo de sala al inicio del programa. Cualquier opcion que no
    // sea 2 o 3 cae en el default y arma una sala Regular, asi evitamos
    // que el programa se rompa si el usuario mete un numero raro.
    private static String elegirTipoSala(Scanner scanner) {
        System.out.println("\n===== ELIJA EL TIPO DE SALA =====");
        System.out.println("1. Regular");
        System.out.println("2. Xtreme");
        System.out.println("3. Prime");
        int opcion = EntradaUtil.leerEntero(scanner, "Seleccione una opción: ");

        switch (opcion) {
            case 2:
                return "XTREME";
            case 3:
                return "PRIME";
            default:
                return "REGULAR";
        }
    }

    // Solo imprime las opciones del menu, no hace ninguna logica.
    private static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE VENTA DE BOLETOS - SALA DE CINE =====");
        System.out.println("1. Ver mapa de asientos");
        System.out.println("2. Reservar un asiento");
        System.out.println("3. Comprar un asiento (generar boleto)");
        System.out.println("4. Ver reporte de ventas");
        System.out.println("5. Guardar reporte de ventas (.txt y .html)");
        System.out.println("6. Salir");
    }

    // Opcion 2 del menu: pide fila y columna y trata de reservar ese asiento.
    // Restamos 1 porque el usuario cuenta desde 1 (fila 1 = A) pero
    // internamente la matriz arranca en 0.
    private static void procesarReserva(Scanner scanner, SalaCine sala) {
        System.out.println(sala.dibujarMapa());
        int fila = EntradaUtil.leerEntero(scanner, "Ingrese el número de fila (1 = A, 2 = B, ...): ") - 1;
        int columna = EntradaUtil.leerEntero(scanner, "Ingrese el número de columna: ") - 1;

        // Si la posicion no existe o el asiento ya esta ocupado, SalaCine
        // lanza una excepcion con el mensaje explicando el motivo y aca
        // simplemente la mostramos, sin cortar el programa.
        try {
            sala.reservarAsiento(fila, columna);
            Asiento asiento = sala.obtenerAsiento(fila, columna);
            System.out.println("Reserva realizada con éxito: " + asiento);
        } catch (Exception ex) {
            System.out.println("No se pudo reservar el asiento: " + ex.getMessage());
        }
    }

    // Opcion 3 del menu: compra un asiento y genera el boleto correspondiente.
    // Es parecido a procesarReserva pero ademas pide los datos del cliente
    // y arma el objeto Boleto al final.
    private static void procesarCompra(Scanner scanner, SalaCine sala, GestorVentas ventas) {
        System.out.println(sala.dibujarMapa());
        int fila = EntradaUtil.leerEntero(scanner, "Ingrese el número de fila (1 = A, 2 = B, ...): ") - 1;
        int columna = EntradaUtil.leerEntero(scanner, "Ingrese el número de columna: ") - 1;

        try {
            sala.comprarAsiento(fila, columna);
            Asiento asiento = sala.obtenerAsiento(fila, columna);

            System.out.print("Ingrese el nombre del cliente: ");
            String cliente = scanner.nextLine().trim();
            if (cliente.isEmpty()) {
                cliente = "Cliente sin nombre"; // por si el usuario no escribe nada
            }

            String tipoPersona = elegirTipoPersona(scanner);

            // El precio se calcula solo dentro del constructor de Boleto
            // (usando Tarifario), aca no hacemos ningun calculo directo.
            Boleto boleto = new Boleto(asiento, cliente, sala.getTipo(), tipoPersona);
            ventas.registrarVenta(boleto);
            System.out.println("Compra realizada con éxito.");
            System.out.println(boleto);
        } catch (Exception ex) {
            System.out.println("No se pudo completar la compra: " + ex.getMessage());
        }
    }

    // Submenu para elegir el tipo de persona (niño, adulto, discapacitado)
    // que se usa para calcular el precio del boleto.
    private static String elegirTipoPersona(Scanner scanner) {
        System.out.println("\n----- TIPO DE PERSONA -----");
        System.out.println("1. Niño");
        System.out.println("2. Adulto");
        System.out.println("3. Discapacitado");
        int opcion = EntradaUtil.leerEntero(scanner, "Seleccione una opción: ");

        switch (opcion) {
            case 1:
                return Tarifario.NINO;
            case 3:
                return Tarifario.DISCAPACITADO;
            case 2:
            default:
                return Tarifario.ADULTO;
        }
    }

    // Opcion 5 del menu: guarda el reporte de ventas actual en un .txt y
    // en un .html dentro de la carpeta resources del proyecto.
    private static void procesarGuardarReporte(GestorVentas ventas) {
        String reporte = ventas.generarReporteTexto();

        String rutaTxt = "src/main/resources/reporte_ventas.txt";
        String rutaHtml = "src/main/resources/reporte_ventas.html";

        boolean okTxt = GestorArchivoTexto.guardar(rutaTxt, reporte);
        boolean okHtml = GestorArchivoTexto.guardarHtml(rutaHtml, reporte);

        if (okTxt) {
            System.out.println("Reporte guardado en: " + rutaTxt);
        }
        if (okHtml) {
            System.out.println("Reporte guardado en: " + rutaHtml);
        }
    }
}
