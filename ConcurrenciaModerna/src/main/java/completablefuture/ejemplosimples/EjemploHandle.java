package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

public class EjemploHandle {

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

        CompletableFuture<String> futuroMensaje =
                futuroImporte.handle((importe, error) -> {

                    if (error != null) {
                        return "No se pudo obtener el importe del pedido.";
                    }

                    return "Importe del pedido: " + importe + " €";
                });

        System.out.println(futuroMensaje.join());
    }
}
