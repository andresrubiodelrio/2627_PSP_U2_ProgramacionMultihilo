package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EjemploThenCombineRecuperacionIndividual {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Pedido pedido1 = new Pedido(1, "Ana", 5, 40.0);
            Pedido pedido2 = new Pedido(2, "Luis", 3, 30.0);

            CompletableFuture<Double> futuro1 =
                    CompletableFuture.supplyAsync(
                            () -> pedido1.calcularImporte(), executor);

            // Conserva el importe si termina correctamente.
            // Si falla, el nuevo futuro devuelve 0.0.
            CompletableFuture<Double> seguro1 =
                    futuro1.exceptionally(error -> {
                        System.out.println(
                                "Pedido " + pedido1.getNumero()
                                        + " excluido del total parcial.");

                        return 0.0;
                    });

            CompletableFuture<Double> futuro2 =
                    CompletableFuture.supplyAsync(() -> {
                        throw new IllegalStateException(
                                "No se pudo procesar el pedido "
                                        + pedido2.getNumero());
                    }, executor);

//            CompletableFuture<Double> futuro2 =
//                    CompletableFuture.supplyAsync(
//                            () -> pedido2.calcularImporte(), executor);


            CompletableFuture<Double> seguro2 =
                    futuro2.exceptionally(error -> {
                        System.out.println(
                                "Pedido " + pedido2.getNumero()
                                        + " excluido del total parcial.");

                        return 0.0;
                    });

            // Combinamos los futuros recuperados.
            CompletableFuture<Double> totalParcial = seguro1
                    .thenCombine(seguro2, (importe1, importe2) -> {
                        // Sí se ejecuta: ambos han terminado correctamente.
                        System.out.println(
                                "Combinando: " + importe1 + " + " + importe2);

                        return importe1 + importe2;
                    });

            System.out.println("Total: " + totalParcial.join());

        } finally {
            executor.shutdown();
        }
    }
}
