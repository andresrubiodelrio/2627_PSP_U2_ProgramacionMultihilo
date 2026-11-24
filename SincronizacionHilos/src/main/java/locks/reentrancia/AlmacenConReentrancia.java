package locks.reentrancia;

import java.util.concurrent.locks.ReentrantLock;


public class AlmacenConReentrancia {

    public static class Almacen {
        private int stock = 10;

        private final ReentrantLock lock = new ReentrantLock();

        public void procesarPedido(String cliente, int unidades) {
            lock.lock(); // Primera adquisición.

            try {
                System.out.println("Procesando pedido de " + cliente + ". Adquisiciones del candado: " + lock.getHoldCount());

                descontarStock(cliente, unidades);

                System.out.println("De vuelta en procesarPedido de " + cliente + ". Adquisiciones del candado: " + lock.getHoldCount());

            } finally {
                lock.unlock(); // Libera la primera adquisición.
            }
        }

        public void descontarStock(String cliente, int unidades) {
            lock.lock(); // Segunda adquisición por el mismo hilo.

            try {
                System.out.println("descontarStock pedido de " + cliente + ". Adquisiciones del candado: " +lock.getHoldCount());

                if (unidades <= 0 || unidades > stock) {
                    throw new IllegalArgumentException("Cantidad de unidades no válida.");
                }

                stock -= unidades;

                System.out.println("Stock restante: " + stock);

            } finally {
                lock.unlock(); // Libera la segunda adquisición.
            }
        }
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

        System.out.println("Proceso finalizado. Adquisiones del candado: "+ almacen.lock.getHoldCount());
    }

}
