package completablefuture.ejemplosimples;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EjemploGet {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        boolean simularError = false;

        CompletableFuture<Double> futuroImporte =
                CompletableFuture.supplyAsync(() -> {

                    if (simularError) {
                        throw new IllegalStateException(
                                "No se pudo calcular el importe.");
                    }

                    return pedido.calcularImporte();
                });

        try {
            // main espera aquí si el cálculo todavía no ha terminado.
            double importe = futuroImporte.get();

            System.out.println("Importe recibido: " + importe);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo principal fue interrumpido mientras esperaba.");

        } catch (ExecutionException e) {
            System.out.println("ERROR: Se ha producido un error durante la ejecución de una tarea");
            System.out.println("La tarea falló: " + e.getCause().getMessage());

        } catch (CancellationException e) {
            System.out.println("El futuro fue cancelado.");
        }
    }
}
