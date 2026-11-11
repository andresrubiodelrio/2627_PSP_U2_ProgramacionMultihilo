package completablefuture.ejemplosimples;

/**
 * Modelo de pedido para los ejemplos de CompletableFuture de PSP.
 * Representa un pedido con varios articulos del mismo precio.
 *
 * El porcentaje se indica como 10 para un descuento del 10 %.
 * Los gastos de envio se suman despues de descontar los articulos.
 * Se usa double para mantener sencillos los ejemplos de clase.
 */
public class Pedido {

    public enum Estado {
        PENDIENTE, PAGADO, ENVIADO, CANCELADO
    }

    private int numero;
    private String cliente;
    private int cantidad;
    private double precioUnitario;

    private double porcentajeDescuento;
    private double gastosEnvio;
    private Estado estado;

    public Pedido(int numero, String cliente, int cantidad,
                  double precioUnitario) {

        setNumero(numero);
        setCliente(cliente);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        setPorcentajeDescuento(0);
        setGastosEnvio(0);
        setEstado(Estado.PENDIENTE);
    }

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public Estado getEstado() {
        return estado;
    }

    // Importe de los articulos antes de descuentos y envio.
    public double calcularImporte() {
        return cantidad * precioUnitario;
    }

    // Dinero que se descuenta, no el porcentaje ni el total a pagar.
    public double calcularDescuento() {
        return calcularImporte() * porcentajeDescuento / 100;
    }

    public double calcularTotal() {
        return calcularImporte() - calcularDescuento() + gastosEnvio;
    }

    // Establece el porcentaje actual; las llamadas no acumulan descuentos.
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
        }
        this.porcentajeDescuento = porcentaje;
    }

    public void setGastosEnvio(double gastosEnvio) {
        if (gastosEnvio < 0) {
            throw new IllegalArgumentException("Los gastos deben ser un numero no negativo.");
        }
        this.gastosEnvio = gastosEnvio;
    }

    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new NullPointerException("ERROR: El estado de un pedido es obligatorio. No puede ser nulo.");
        }

        this.estado = estado;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("ERROR: El número del pedido debe ser positivo.");
        }
                this.numero = numero;
    }

    public void setCliente(String cliente) {
        if (cliente == null )
            throw new NullPointerException("ERROR: El cliente de un pedido es obligatorio. No puede ser nulo.");

        if (cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El cliente de un pedido  es obligatorio.");
        }

        this.cliente = cliente;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("ERROR: La cantidad de un pedido debe ser positiva.");
        }

        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("ERROR: El precio debe ser un número no negativo.");
        }
        this.precioUnitario = precioUnitario;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento < 0) {
            throw new IllegalArgumentException("ERROR: El porcentaje de descuento debe ser un número no negativo.");
        }
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "número=" + numero +
                ", cliente='" + cliente + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", porcentajeDescuento=" + porcentajeDescuento +
                ", gastosEnvio=" + gastosEnvio +
                ", total=" + calcularTotal() +
                ", estado=" + estado +
                '}';
    }
}
