package locks.almacen;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Almacen {

    private int stock = 5;

    private final Lock lock = new ReentrantLock();

    public void procesarPedido(String cliente, int unidades) {

        if (unidades <= 0)
            throw new IllegalArgumentException("Las unidades deben ser positivas.");

        // El bloqueo comienza aquí.
        lock.lock();

        try {
            System.out.println("Procesando pedido de " + cliente);

            // Primera llamada: seguimos teniendo el lock.
            if (!comprobarStock(unidades))
            {
                System.out.println("Stock insuficiente para " + cliente);

                return;
            }

            // Segunda llamada: seguimos teniendo el lock.
            descontarStock(unidades);

            // Tercera llamada: seguimos teniendo el lock.
            registrarPedido(cliente, unidades);

        } finally {
            // El bloqueo termina aquí.
            liberarBloqueo();
        }
    }

    private boolean comprobarStock(int unidades) {

        System.out.println("Comprobando existencias: " + stock);

        return stock >= unidades;
    }

    private void descontarStock(int unidades) {
        stock -= unidades;

        System.out.println("Descontadas " + unidades + " unidades.");
    }

    private void registrarPedido(String cliente, int unidades) {
        System.out.println("Pedido confirmado: " + cliente
                        + ", unidades: " + unidades
                        + ", stock restante: " + stock
        );
    }

    private void liberarBloqueo() {
        lock.unlock(); // Liberación en otro método.
    }

    public static void main(String[] args) {

        Almacen almacen = new Almacen();

        Thread pedido1 = new Thread(() -> almacen.procesarPedido("Ana", 4),"Hilo Ana");

        Thread pedido2 = new Thread(() -> almacen.procesarPedido("Juan", 4),"Hilo Juan");

        pedido1.start();
        pedido2.start();

        try {
            pedido1.join();
            pedido2.join();
        } catch (InterruptedException e) {
            System.out.println("ERROR: El hilo principal ha sido interrumpido.");
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }

    }
}
