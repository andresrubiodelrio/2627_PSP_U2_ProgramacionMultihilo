package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class EjemploWhenComplete {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        boolean simularError = true;

        CompletableFuture<Double> futuro =
                CompletableFuture.supplyAsync(() -> {

                    if (simularError) {
                        throw new IllegalStateException("No se pudo calcular el importe.");
                    }

                    return pedido.calcularImporte();

                }).whenComplete((importe, error) -> {

                    if (error == null) {
                        System.out.println("Importe calculado: " + importe);
                    } else {
                        System.out.println("Se ha producido un error en el cálculo.");
                    }
                });

        try {
            double importe = futuro.join();

            System.out.println("Resultado recibido por main: " + importe);

        } catch (CompletionException e) {
            Throwable causa = e.getCause();
            System.out.println("Tipo: " + causa.getClass().getSimpleName());
            System.out.println("Mensaje: " + causa.getMessage());
        }
    }
}
