package Utilitarios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Clase de apoyo para guardar y leer archivos de texto (.txt y .html).
// La usamos principalmente para exportar el reporte de ventas a un archivo
// que se pueda abrir despues fuera del programa.
public class GestorArchivoTexto {

    // Lee todo el contenido de un archivo de texto y lo devuelve como String.
    // Si el archivo no existe o hay algun problema, devolvemos null en vez
    // de dejar que el programa se detenga por la excepcion.
    public static String leer(String rutaArchivo) {
        try {
            return Files.readString(Path.of(rutaArchivo));
        } catch (IOException ex) {
            System.out.println("No se pudo leer el archivo: " + ex.getMessage());
            return null;
        }
    }

    // Guarda un texto plano en la ruta indicada. Devuelve true/false para
    // que el que llama este metodo sepa si se pudo guardar o no.
    public static boolean guardar(String rutaArchivo, String texto) {
        try {
            Files.writeString(Path.of(rutaArchivo), texto);
            return true;
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo: " + ex.getMessage());
            return false;
        }
    }

    // Genera una version en HTML del reporte de ventas, para que se vea un
    // poco mas presentable si se abre en el navegador (con tabla y estilos
    // basicos). El texto del reporte (que ya viene armado con \n) lo metemos
    // dentro de una etiqueta <pre> para que respete los saltos de linea.
    public static boolean guardarHtml(String rutaArchivo, String texto) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("<title>Reporte de Ventas</title>\n");
        html.append("</head>\n");
        html.append("<body bgcolor=\"#e8e8e8\">\n");
        html.append("<center>\n");
        html.append("<table border=\"3\" cellpadding=\"15\" cellspacing=\"0\" width=\"480\" bgcolor=\"#ffffff\">\n");
        html.append("  <tr>\n");
        html.append("    <td align=\"center\" bgcolor=\"#d0d0d0\">\n");
        html.append("      <h2>SALA DE CINE</h2>\n");
        html.append("      <b>REPORTE DE VENTAS</b>\n");
        html.append("    </td>\n");
        html.append("  </tr>\n");
        html.append("  <tr>\n");
        html.append("    <td>\n");
        html.append("      <pre>\n");
        html.append(texto);
        html.append("\n      </pre>\n");
        html.append("    </td>\n");
        html.append("  </tr>\n");
        html.append("</table>\n");
        html.append("</center>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        try {
            Files.writeString(Path.of(rutaArchivo), html.toString());
            return true;
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo HTML: " + ex.getMessage());
            return false;
        }
    }
}
