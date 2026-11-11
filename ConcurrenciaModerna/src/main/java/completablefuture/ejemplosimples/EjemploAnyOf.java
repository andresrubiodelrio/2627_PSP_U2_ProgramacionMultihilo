package completablefuture.ejemplosimples;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/*
simularemos consultar el mismo pedido en dos servidores. Utilizaremos la primera respuesta disponible:
 */
public class EjemploAnyOf {

    public static void main(String[] args) {

        Pedido pedido = new Pedido(1, "Ana", 5, 40.0);

        CompletableFuture<String> servidor1 =
                consultarAsync(pedido, "Servidor 1");

        CompletableFuture<String> servidor2 =
                consultarAsync(pedido, "Servidor 2");

        CompletableFuture<Object> primero =
                CompletableFuture.anyOf(servidor1, servidor2);

        Object resultado = primero.join();

        System.out.println("Primera respuesta: " + resultado);
    }

    private static CompletableFuture<String> consultarAsync(
            Pedido pedido, String servidor) {

        Random random=new Random();
        int tiempoAleatorio=1+ random.nextInt(1500);

        return CompletableFuture.supplyAsync(() -> {

            // Simulamos el tiempo que tarda la consulta.
            try {
                Thread.sleep(tiempoAleatorio);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }

            return servidor + ": importe " + pedido.calcularImporte();
        });
    }
}
