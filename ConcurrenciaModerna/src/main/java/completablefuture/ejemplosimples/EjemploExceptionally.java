package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

public class EjemploExceptionally {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        boolean simularError = false;

        CompletableFuture<Double> futuroDescuento =
                CompletableFuture.supplyAsync(() -> {

                    if (simularError) {
                        throw new IllegalStateException("No se pudo consultar el descuento.");
                    }

                    double importe=pedido.calcularImporte();
                    if ( importe>= 100) {
                        pedido.aplicarDescuento(10);
                    }
                    else if (importe >= 50) {
                        pedido.aplicarDescuento(5);
                    }

                    return pedido.calcularDescuento();
                });

        CompletableFuture<Double> futuroRecuperado =
                futuroDescuento.exceptionally(error -> {

                    System.out.println("Tipo:" + error.getCause().getClass().getSimpleName());
                    System.out.println("Mensaje:" + error.getCause().getMessage());
                    System.out.println("La consulta falló. Se utilizará un descuento de 0 €.");

                    return 0.0;
                });

        double descuento = futuroRecuperado.join();

        double total = pedido.calcularImporte() - descuento;

        System.out.println("Descuento: " + descuento);
        System.out.println("Total: " + total);
    }
}
