package completablefuture.ejemplosimples;

import java.util.concurrent.*;

public class EjemploSupplyAsync {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        // Primera etapa: calcular el importe.
        CompletableFuture<Double> futuroImporte =
                CompletableFuture.supplyAsync(() ->
                            pedido.calcularImporte()
                );

        double importe = futuroImporte.join();

        System.out.println("Importe: " + importe);

    }
}
