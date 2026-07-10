package com.mycompany.boletos;

// Esta clase representa la sala de cine completa. Adentro guarda una matriz
// (arreglo bidimensional) de objetos Asiento, que es basicamente el "mapa"
// de la sala. Aca es donde vive toda la logica de reservar, comprar y
// dibujar el mapa de asientos.
public class SalaCine {

    private final String nombre;
    private final String tipo;
    private final int totalFilas;
    private final int totalColumnas;
    private final Asiento[][] asientos; // matriz: fila x columna

    public SalaCine(String nombre, String tipo, int totalFilas, int totalColumnas) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.totalFilas = totalFilas;
        this.totalColumnas = totalColumnas;
        this.asientos = new Asiento[totalFilas][totalColumnas];
        inicializarAsientos();
    }

    // Metodo estatico que arma la sala segun el tipo elegido por el usuario.
    // Cada tipo de sala tiene un tamaño distinto (mas o menos filas/columnas).
    // Esto evita que el que use la clase tenga que acordarse el tamaño de
    // cada sala a mano, solo dice el tipo y aca se resuelve.
   public static SalaCine crearSegunTipo(String nombre, String tipo) {
        int filas;
        int columnas;

        switch (tipo) {
            case "XTREME":
                filas = 6;
                columnas = 8;
                break;
            case "PRIME":
                filas = 5;
                columnas = 6;
                break;
            case "REGULAR":
            default:
                filas = 8;
                columnas = 10;
                break;
        }

        return new SalaCine(nombre, tipo, filas, columnas);
    }

    // Llena toda la matriz con asientos nuevos, todos disponibles al inicio.
    // Recorremos fila por fila y dentro de cada fila, columna por columna
    // (doble for, tipico para matrices).
    private void inicializarAsientos() {
        for (int f = 0; f < totalFilas; f++) {
            for (int c = 0; c < totalColumnas; c++) {
                asientos[f][c] = new Asiento(f, c);
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTotalFilas() {
        return totalFilas;
    }

    public int getTotalColumnas() {
        return totalColumnas;
    }

    // Chequea que la fila/columna que pide el usuario realmente exista en
    // la sala. Lo separamos en su propio metodo porque lo necesitan varios
    // metodos de aca abajo (obtenerAsiento, reservarAsiento, comprarAsiento)
    // y asi no repetimos el mismo if en cada uno.
    private void validarPosicion(int fila, int columna) throws Exception {
        if (fila < 0 || fila >= totalFilas || columna < 0 || columna >= totalColumnas) {
            throw new Exception("La posición fila=" + fila + ", columna=" + columna
                    + " no existe en " + nombre + " (filas 0-" + (totalFilas - 1)
                    + ", columnas 0-" + (totalColumnas - 1) + ").");
        }
    }

    public Asiento obtenerAsiento(int fila, int columna) throws Exception {
        validarPosicion(fila, columna);
        return asientos[fila][columna];
    }

    // Reserva un asiento. Primero se fija que la posicion exista (eso lo hace
    // obtenerAsiento) y despues que el asiento este disponible; si ya esta
    // reservado o comprado, no deja reservar y explica por que en el mensaje.
    public void reservarAsiento(int fila, int columna) throws Exception {
        Asiento asiento = obtenerAsiento(fila, columna);
        if (!asiento.estaDisponible()) {
            throw new Exception("El asiento " + asiento.obtenerEtiqueta()
                    + " ya se encuentra " + asiento.obtenerNombreEstado() + ".");
        }
        asiento.setEstado(Asiento.RESERVADO);
    }

    // Compra un asiento. Nota: a diferencia de reservarAsiento, aca SI se
    // permite comprar un asiento que estaba reservado (pasa de RESERVADO a
    // COMPRADO), solo bloqueamos si ya estaba comprado antes.
    public void comprarAsiento(int fila, int columna) throws Exception {
        Asiento asiento = obtenerAsiento(fila, columna);
        if (asiento.getEstado() == Asiento.COMPRADO) {
            throw new Exception("El asiento " + asiento.obtenerEtiqueta()
                    + " ya fue comprado anteriormente.");
        }
        asiento.setEstado(Asiento.COMPRADO);
    }

    // Cuenta cuantos asientos hay en un estado especifico (recorriendo toda
    // la matriz). Lo usamos por ejemplo para saber cuantos asientos quedan
    // disponibles o cuantos ya se vendieron.
    public int contarAsientosPorEstado(char estado) {
        int contador = 0;
        for (int f = 0; f < totalFilas; f++) {
            for (int c = 0; c < totalColumnas; c++) {
                if (asientos[f][c].getEstado() == estado) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Arma el "dibujo" de la sala en texto para mostrarlo en consola.
    // Usamos StringBuilder en vez de concatenar Strings con + en un loop,
    // porque es mas eficiente cuando se va armando el texto de a poquitos
    // dentro de un for.
    public String dibujarMapa() {
        StringBuilder mapa = new StringBuilder();
        mapa.append("\n=== ").append(nombre).append(" (").append(tipo).append(") ===\n");
        mapa.append("       PANTALLA\n\n");

        // Primero la fila de numeros de columna (encabezado)
        mapa.append("     ");
        for (int c = 0; c < totalColumnas; c++) {
            mapa.append(String.format("%-4d", c + 1));
        }
        mapa.append("\n");

        // Despues cada fila de asientos, con su letra al inicio (A, B, C...)
        for (int f = 0; f < totalFilas; f++) {
            char letraFila = (char) ('A' + f);
            mapa.append(" ").append(letraFila).append(" | ");
            for (int c = 0; c < totalColumnas; c++) {
                mapa.append(asientos[f][c].obtenerSimbolo()).append(" ");
            }
            mapa.append("\n");
        }
        mapa.append("\nLeyenda: [ ] Disponible   [R] Reservado   [X] Comprado\n");
        return mapa.toString();
    }
}
