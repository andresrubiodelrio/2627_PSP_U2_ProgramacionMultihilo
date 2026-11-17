package completablefuture.ejemplosimples;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

public class EjemploCancel {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        CompletableFuture<Double> futuroImporte =
                CompletableFuture.supplyAsync(() ->
                    pedido.calcularImporte()
                );

        boolean cancelado = futuroImporte.cancel(true);

        System.out.println("Cancelación: " + cancelado);
        System.out.println("isCancelled(): " + futuroImporte.isCancelled());
        System.out.println("isDone(): " + futuroImporte.isDone());

        try {
            double importe = futuroImporte.join();
            System.out.println("Importe: " + importe);

        } catch (CancellationException e) {
            System.out.println("No se obtiene el importe: futuro cancelado.");
        }

        // Después de cancelarlo, complete() no puede establecer el resultado.
        boolean completado =
                futuroImporte.complete(pedido.calcularImporte());

        System.out.println("¿Se pudo completar después?: " + completado);
    }
}
