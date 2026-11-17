package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

/*
Cálculo del importe y de los gastos de envío como tareas independientes
 */
public class EjemploThenCombine {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        // Primera tarea: calcular el importe de los artículos.
        CompletableFuture<Double> futuroImporte =
                CompletableFuture.supplyAsync(() ->
                        pedido.calcularImporte()
                );

        // Segunda tarea independiente: calcular los gastos de envío.
        //1.5€ por cada producto del pedido
        CompletableFuture<Double> futuroEnvio =
                CompletableFuture.supplyAsync(() ->
                        pedido.getCantidad() * 1.5
                );

        // Combinar los resultados cuando ambos estén disponibles.
        CompletableFuture<Double> futuroTotal =
                futuroImporte.thenCombine(
                        futuroEnvio,
                        (importe, gastosEnvio) -> {

                            System.out.println("Importe: " + importe);
                            System.out.println("Gastos de envío: " + gastosEnvio);

                            if (importe>500)
                                return importe; //No se le cobra gastos de envío.
                            else
                                return importe + gastosEnvio;
                        }
                );

        System.out.println("Total: " + futuroTotal.join());
    }
}
