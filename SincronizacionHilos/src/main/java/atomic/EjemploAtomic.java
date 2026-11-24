package atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class EjemploAtomic {
    public static void main(String[] args)  {
        AtomicInteger contador = new AtomicInteger(0);

        Runnable increment = () -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementAndGet(); // incrementa de forma atómica y devuelve el valor
            }
        };

        Thread t1 = new Thread(increment);
        Thread t2 = new Thread(increment);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println("Hilo principal interrumpido.");
            Thread.currentThread().interrupt();
        }


        System.out.println("Valor final: " + contador.get());
    }
}
