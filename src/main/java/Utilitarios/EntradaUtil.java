package Utilitarios;

import java.util.Scanner;

// Clase de apoyo para leer numeros enteros desde el teclado sin que el
// programa se caiga si el usuario escribe letras u otra cosa que no sea
// un numero. La separamos del Main para no repetir este mismo bloque de
// codigo cada vez que necesitamos leer un entero.
public class EntradaUtil {

    // Sigue pidiendo el dato hasta que el usuario ingrese un numero valido.
    // Usamos try/catch para atrapar el error si Integer.parseInt no puede
    // convertir el texto a numero (por ejemplo si el usuario escribe "hola").
    public static int leerEntero(Scanner scanner, String mensaje) {
        int valor = -1;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Debe ingresar solo números, intente nuevamente.");
            }
        }
        return valor;
    }
}
