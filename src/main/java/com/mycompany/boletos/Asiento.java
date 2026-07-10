package com.mycompany.boletos;

// Esta clase representa un asiento dentro de la sala de cine.
// Cada asiento sabe en que fila y columna esta, y en que estado se encuentra
// (disponible, reservado o comprado). Es basicamente el "ladrillo" con el que
// despues armamos la matriz de asientos en SalaCine.
public class Asiento {

    // Usamos constantes de tipo char para los 3 posibles estados del asiento.
    // Así evitamos escribir 'D', 'R' o 'C' sueltos por todo el codigo (harcodeado)
    // y si algun dia queremos cambiar el valor solo lo cambiamos aca.
    public static final char DISPONIBLE = 'D';
    public static final char RESERVADO = 'R';
    public static final char COMPRADO = 'C';

    // fila y columna son privados porque no queremos que se puedan cambiar
    // desde afuera una vez creado el asiento (encapsulamiento).
    private int fila;
    private int columna;
    private char estado;

    // Constructor: cuando se crea un asiento nuevo siempre arranca DISPONIBLE,
    // nadie deberia poder crear un asiento ya reservado o comprado.
    public Asiento(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.estado = DISPONIBLE;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public char getEstado() {
        return estado;
    }

    // Antes de cambiar el estado validamos que sea uno de los 3 permitidos.
    // Si alguien manda un char raro (por error de programacion, no del usuario)
    // preferimos que reviente aca con una excepcion clara en vez de dejar el
    // asiento en un estado invalido silenciosamente.
    public void setEstado(char estado) {
        if (estado != DISPONIBLE && estado != RESERVADO && estado != COMPRADO) {
            throw new IllegalArgumentException("Estado inválido: '" + estado
                    + "'. Los valores permitidos son D (disponible), R (reservado) o C (comprado).");
        }
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return this.estado == DISPONIBLE;
    }

    // Convierte la posicion (fila, columna) que usamos internamente (0,0 0,1...)
    // en la etiqueta que ve el usuario, tipo "A1", "B3", etc. Sumamos 'A' + fila
    // para sacar la letra y le sumamos 1 a la columna porque para el usuario
    // las columnas empiezan en 1, no en 0.
    public String obtenerEtiqueta() {
        char letraFila = (char) ('A' + fila);
        int numeroColumna = columna + 1;
        return "" + letraFila + numeroColumna;
    }

    // Este metodo es el que usamos para dibujar el mapa de asientos en consola.
    // Cada estado tiene su simbolo para que se note rapido de un vistazo.
    public String obtenerSimbolo() {
        switch (estado) {
            case DISPONIBLE:
                return "[ ]";
            case RESERVADO:
                return "[R]";
            case COMPRADO:
                return "[X]";
            default:
                return "[?]"; // esto en teoria nunca deberia pasar por la validacion de setEstado
        }
    }

    // Igual que obtenerSimbolo pero en texto completo, lo usamos en los mensajes
    // que le mostramos al usuario (ej: "El asiento A1 ya se encuentra RESERVADO").
    public String obtenerNombreEstado() {
        switch (estado) {
            case DISPONIBLE:
                return "DISPONIBLE";
            case RESERVADO:
                return "RESERVADO";
            case COMPRADO:
                return "COMPRADO";
            default:
                return "DESCONOCIDO";
        }
    }

    // Sobreescritura de toString (viene de Object). Nos sirve para poder
    // imprimir el asiento directo con System.out.println(asiento) y que
    // salga algo entendible en vez de la direccion de memoria.
    @Override
    public String toString() {
        return "Asiento " + obtenerEtiqueta() + " | Estado: " + obtenerNombreEstado();
    }
}
