package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EjemploAllOfConError {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        boolean resultadoParcial = false;
        try {
            Pedido pedido1 = new Pedido(1, "Ana", 5, 40.0);
            Pedido pedido2 = new Pedido(2, "Luis", 3, 30.0);
            Pedido pedido3 = new Pedido(3, "Marta", 2, 30.0);

            CompletableFuture<Double> futuro1 = CompletableFuture.supplyAsync(
                    () -> pedido1.calcularImporte(), executor);

//            CompletableFuture<Double> futuro2 = CompletableFuture.supplyAsync(
//                    () -> pedido2.calcularImporte(), executor);

            CompletableFuture<Double> futuro2 = CompletableFuture.supplyAsync(() -> {
                // Simulamos un fallo DENTRO de la tarea asíncrona.
                throw new IllegalStateException(
                        "No se pudo procesar el pedido " + pedido2.getNumero());
            }, executor);

            CompletableFuture<Double> futuro3 = CompletableFuture.supplyAsync(
                    () -> pedido3.calcularImporte(), executor);

            // 1. Capturamos el error del conjunto.
            CompletableFuture<Void> todos = CompletableFuture.allOf(futuro1, futuro2, futuro3);

            try {
                todos.thenRun(() -> {
                    // No se ejecuta: futuro2 ha fallado.
                    double total = futuro1.join() + futuro2.join() + futuro3.join();
                    System.out.println("Total completo: " + total);
                }).join();

            } catch (CompletionException e) {
                System.out.println("Error del conjunto: " + e.getCause().getMessage());
                resultadoParcial=true;
            }

            if (resultadoParcial) {
                // 2. Recuperamos cada resultado:
                // el pedido que falla queda excluido del total parcial.
                CompletableFuture<Double> seguro1 = recuperar(pedido1, futuro1);
                CompletableFuture<Double> seguro2 = recuperar(pedido2, futuro2);
                CompletableFuture<Double> seguro3 = recuperar(pedido3, futuro3);

                CompletableFuture<Void> fin = CompletableFuture.allOf(seguro1, seguro2, seguro3)
                        .thenRun(() -> {
                            double totalParcial = seguro1.join()
                                    + seguro2.join() + seguro3.join();

                            System.out.println("Total parcial: " + totalParcial);
                        });

                fin.join();
            }


        } finally {
            executor.shutdown();
        }
    }

    private static CompletableFuture<Double> recuperar(Pedido pedido, CompletableFuture<Double> futuro) {

        return futuro.exceptionally(error -> {
            System.out.println("Pedido " + pedido.getNumero()
                    + " excluido del total: se usa 0.0 como alternativa.");

            return 0.0;
        });
    }
}
