package completablefuture.ejemplosimples;

import java.util.concurrent.*;

/*
Cálculo del importe del pedido y a partir de dicho importe calcular el descuento.
 */
public class EjemploThenApply {

    public static void main(String[] args) {

        System.out.println("PRIMERO CON COMPETABLE FUTURE");
        System.out.println("=============================");
        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        // Primera etapa: calcular el importe.
        CompletableFuture<Double> futuroImporte =
                CompletableFuture.supplyAsync(() ->
                        pedido.calcularImporte()
                );

        // Segunda etapa: decidir y calcular el descuento.
        CompletableFuture<Double> futuroDescuento =
                futuroImporte.thenApply(importe -> {

                    if (importe >= 100) {
                        pedido.aplicarDescuento(10);
                    }
                    else if (importe >= 50) {
                        pedido.aplicarDescuento(5);
                    }

                    return pedido.calcularDescuento();
                });

        double descuento = futuroDescuento.join();

        System.out.println("Importe: " + futuroImporte.join());
        System.out.println("Descuento: " + descuento);
        System.out.println("Total: " + pedido.calcularTotal());


        System.out.println("SEGUNDO CON FUTURE");
        System.out.println("==================");
        ejemploFuture(pedido);
    }

    public static void  ejemploFuture(Pedido pedido)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Double> futuroImporte =
                executor.submit(() -> pedido.calcularImporte());

        // main debe esperar aquí si el importe todavía no está disponible.
        double importe = 0;
        try
        {
            importe = futuroImporte.get();

            // Esta tarea solo se programa después de obtener el importe.
            double finalImporte = importe;
            Future<Double> futuroDescuento =
                executor.submit(() -> {
                    if (finalImporte >= 100) {
                        pedido.aplicarDescuento(10);
                    }
                    else if (finalImporte >= 50) {
                        pedido.aplicarDescuento(5);
                    }

                    return pedido.calcularDescuento();
                });

            double descuento = futuroDescuento.get();

            System.out.println("Importe: " + pedido.calcularImporte());
            System.out.println("Descuento: " + descuento);
            System.out.println("Total: " + pedido.calcularTotal());

            executor.shutdown();
        }
        catch (InterruptedException e) {
            System.out.println("ERROR: El hilo principal ha sido interrumpido.");
            executor.shutdown();
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException e) {
            System.out.println("ERROR: Se ha producido un error durante la ejecución de una tarea");
        }
    }
}
