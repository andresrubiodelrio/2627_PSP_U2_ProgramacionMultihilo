package completablefuture.ejemplosimples;

import java.util.concurrent.CompletableFuture;

/*
calcular los importes de tres pedidos
 */
public class EjemploAllOf {

    public static void main(String[] args) {

        Pedido pedido1 = new Pedido(1, "Ana", 5, 40.0);
        Pedido pedido2 = new Pedido(2, "Juan", 2, 30.0);
        Pedido pedido3 = new Pedido(3, "Fátima", 3, 15.0);

        CompletableFuture<Double> futuro1 =
                CompletableFuture.supplyAsync(() ->
                        pedido1.calcularImporte()
                );

        CompletableFuture<Double> futuro2 =
                CompletableFuture.supplyAsync(() ->
                        pedido2.calcularImporte()
                );

        CompletableFuture<Double> futuro3 =
                CompletableFuture.supplyAsync(() ->
                        pedido3.calcularImporte()
                );

        // Representa la finalización del conjunto de tareas.
        CompletableFuture<Void> todos =
                CompletableFuture.allOf(futuro1, futuro2, futuro3);

        System.out.println("main puede seguir haciendo otras cosas.");

        // Esperamos a que terminen todos.
        todos.join();

        System.out.println("Todos los importes están calculados.");

        // Consultamos los resultados individuales.
        System.out.println("Pedido 1: " + futuro1.join());
        System.out.println("Pedido 2: " + futuro2.join());
        System.out.println("Pedido 3: " + futuro3.join());
        System.out.println("Importe total: " + (futuro1.join() + futuro2.join() + futuro3.join()));
    }
}
