package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

public class EjemploThenAccept {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        // Primera etapa: calcular el importe.
        CompletableFuture<Double> futuroImporte =
                CompletableFuture.supplyAsync(() ->
                        pedido.calcularImporte()
                );

        // Segunda etapa: calcular el descuento.
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

        // Tercera etapa: utilizar el descuento para mostrar el resumen.
        CompletableFuture<Void> futuroMostrar =
                futuroDescuento.thenAccept(descuento -> {

                    System.out.println("Pedido: " + pedido.getNumero());
                    System.out.println("Cliente: " + pedido.getCliente());
                    System.out.println("Importe: " + pedido.calcularImporte());
                    System.out.println("Descuento: " + descuento);
                    System.out.println("Total: " + pedido.calcularTotal());

                    // Esta lambda no devuelve ningún valor.
                });

        // Esperamos a que termine la última etapa.
        futuroMostrar.join();
    }
}
