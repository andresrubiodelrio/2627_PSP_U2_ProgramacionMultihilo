package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

public class EjemploThenRun {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        CompletableFuture<Void> futuro =
                CompletableFuture.supplyAsync(() ->
                                pedido.calcularImporte()
                        )
                        .thenApply(importe -> {
                            if (importe >= 100) {
                                pedido.aplicarDescuento(10);
                            }
                            else if (importe >= 50) {
                                pedido.aplicarDescuento(5);
                            }

                            return pedido.calcularDescuento();
                        })
                        .thenAccept(descuento -> {
                            System.out.println("Pedido: " + pedido.getNumero());
                            System.out.println("Cliente: " + pedido.getCliente());
                            System.out.println("Importe: " + pedido.calcularImporte());
                            System.out.println("Descuento: " + descuento);
                            System.out.println("Total: " + pedido.calcularTotal());
                        })
                        .thenRun(() -> {
                            System.out.println("Procesamiento del pedido finalizado.");
                        });

        // Esperamos a que termine toda la cadena.
        futuro.join();
    }
}
