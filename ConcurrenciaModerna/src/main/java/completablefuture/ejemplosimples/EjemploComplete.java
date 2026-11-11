package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

public class EjemploComplete {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        // Creamos un futuro pendiente, sin iniciar ninguna tarea.
        CompletableFuture<Double> futuroImporte =
                new CompletableFuture<>();

        // Preparamos una transformación para cuando llegue el importe.
        CompletableFuture<Double> futuroDescuento =
                futuroImporte.thenApply(importe -> {
                    if (importe >= 100) {
                        return importe * 0.10;
                    }

                    return 0.0;
                });

        // Proporcionamos manualmente el resultado.
        boolean completado =
                futuroImporte.complete(pedido.calcularImporte());

        System.out.println("Primer intento: " + completado);
        System.out.println("Importe: " + futuroImporte.join());
        System.out.println("Descuento: " + futuroDescuento.join());

        // Intentamos proporcionar otro resultado al mismo futuro.
        boolean segundoIntento = futuroImporte.complete(999.0);

        System.out.println("Segundo intento: " + segundoIntento);
        System.out.println("Importe conservado: " + futuroImporte.join());
    }
}
