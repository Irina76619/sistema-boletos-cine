package com.mycompany.boletos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Representa un boleto ya comprado. Se crea SOLO cuando el asiento pasa
// a estado COMPRADO (ver SalaCine y CineBoletos), o sea que si existe un
// objeto Boleto es porque ya hubo una compra real.
public class Boleto {

    // Contador estatico para ir generando codigos de boleto tipo B-001, B-002...
    // Es estatico porque tiene que ser compartido entre TODOS los boletos,
    // no uno por cada boleto (si no, siempre empezaria en 0).
    private static int contador = 0;

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Todos estos campos son "final" porque un boleto una vez emitido no
    // deberia cambiar de datos (no tendria sentido que un boleto cambie de
    // cliente o de precio despues de comprado).
    private final String codigo;
    private final Asiento asiento;
    private final String cliente;
    private final String tipoPersona;
    private final double precioFinal;
    private final LocalDateTime fechaHora;


    // Constructor normal: la fecha se toma automaticamente del sistema
    // (o sea, el momento exacto en que se genera el boleto).
    public Boleto(Asiento asiento, String cliente, String tipoSala, String tipoPersona) {
        this.asiento = asiento;
        this.cliente = cliente;
        this.tipoPersona = tipoPersona;
        this.precioFinal = Tarifario.calcularPrecio(tipoSala, tipoPersona);
        this.codigo = generarCodigo();
        this.fechaHora = LocalDateTime.now();
    }

    // Constructor sobrecargado: este permite pasar una fecha especifica
    // en vez de la fecha actual. Lo usamos para pruebas o casos donde no
    // queremos que la hora sea siempre "ahora mismo".
    public Boleto(Asiento asiento, String cliente, String tipoSala, String tipoPersona, LocalDateTime fechaHora) {
        this.asiento = asiento;
        this.cliente = cliente;
        this.tipoPersona = tipoPersona;
        this.precioFinal = Tarifario.calcularPrecio(tipoSala, tipoPersona);
        this.codigo = generarCodigo();
        this.fechaHora = fechaHora;
    }

    // Genera el codigo del boleto usando el contador estatico.
    // %03d hace que siempre tenga 3 digitos (001, 002, ... 099, 100).
    private static String generarCodigo() {
        contador++;
        return String.format("B-%03d", contador);
    }

    public String getCodigo() {
        return codigo;
    }
    public Asiento getAsiento() {
        return asiento;
    }
    public String getCliente() {
        return cliente;
    }
    public double getPrecioFinal() {
        return precioFinal;
    }
    public String getTipoPersona() {
        return tipoPersona;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    // Devuelve la fecha ya formateada como texto (dd/MM/yyyy HH:mm:ss)
    // para no tener que formatear la fecha cada vez que la queremos mostrar.
    public String getFechaHoraFormateada() {
        return fechaHora.format(FORMATO_FECHA);
    }

    // Sobreescribimos toString para que al imprimir el boleto salga un
    // resumen legible con todos los datos importantes de la venta.
    @Override
    public String toString() {
        return "Boleto " + codigo + " | Cliente: " + cliente
                + " | Asiento: " + asiento.obtenerEtiqueta()
                + " | Tipo: " + Tarifario.obtenerNombrePersona(tipoPersona)
                + " | Total pagado: S/ " + String.format("%.2f", precioFinal)
                + " | Fecha: " + getFechaHoraFormateada();
    }
}
