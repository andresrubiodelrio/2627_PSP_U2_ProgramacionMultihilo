package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EjemploThenCombineRecuperacionGlobal {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Pedido pedido1 = new Pedido(1, "Ana", 5, 40.0);
            Pedido pedido2 = new Pedido(2, "Luis", 3, 30.0);

            CompletableFuture<Double> futuro1 =
                    CompletableFuture.supplyAsync(
                            () -> pedido1.calcularImporte(), executor);

//            CompletableFuture<Double> futuro1 =
//                    CompletableFuture.supplyAsync(() -> {
//
//                        throw new IllegalStateException(
//                                "No se pudo procesar el pedido "
//                                        + pedido1.getNumero());
//
//                    }, executor);

            CompletableFuture<Double> futuro2 =
                    CompletableFuture.supplyAsync(
                            () -> pedido2.calcularImporte(), executor);


//            CompletableFuture<Double> futuro2 =
//                    CompletableFuture.supplyAsync(() -> {
//
//                        throw new IllegalStateException(
//                                "No se pudo procesar el pedido "
//                                        + pedido2.getNumero());
//
//                    }, executor);

            CompletableFuture<Double> totalAlternativo = futuro1
                    .thenCombine(futuro2, (importe1, importe2) -> {
                        // No se ejecuta porque futuro2 ha fallado.
                        System.out.println("Calculando el total completo.");
                        return importe1 + importe2;
                    })
                    .exceptionally(error -> {
                        boolean fallo1 = futuro1.isCompletedExceptionally();
                        boolean fallo2 = futuro2.isCompletedExceptionally();

                        // También podría fallar la propia función de combinación.
                        // Si los futuros originales son correctos, propagamos ese error.
                        if (!fallo1 && !fallo2) {
                            throw new CompletionException(error);
                        }

                        double totalParcial = 0.0;

                        if (fallo1) {
                            System.out.println("Ha fallado el futuro del pedido "
                                            + pedido1.getNumero()+ ". Se hará el cálculo teniendo en cuenta solo el otro pedido.");
                        } else {
                            // Solo leemos el resultado si el futuro es correcto.
                            totalParcial += futuro1.join();
                        }

                        if (fallo2) {
                            System.out.println("Ha fallado el futuro del pedido "
                                            + pedido2.getNumero()+ ". Se hará el cálculo teniendo en cuenta solo el otro pedido.");
                        } else {
                            totalParcial += futuro2.join();
                        }

                        return totalParcial;

                    });

            System.out.println("Total: " + totalAlternativo.join());

        } finally {
            executor.shutdown();
        }
    }
}
