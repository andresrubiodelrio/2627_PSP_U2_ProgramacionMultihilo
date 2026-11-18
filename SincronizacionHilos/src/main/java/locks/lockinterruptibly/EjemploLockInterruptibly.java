package locks.lockinterruptibly;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EjemploLockInterruptibly {

    public static void main(String[] args) {

        // Cambiar a false para probar con lock().
        boolean usarLockInterruptible = true;

        Lock lock = new ReentrantLock();

        Thread trabajador = new Thread(() -> {

            try {
                System.out.println(
                        "Trabajador: intento adquirir el lock."
                );

                if (usarLockInterruptible) {
                    lock.lockInterruptibly();
                } else {
                    lock.lock();
                }

                // Solo llegamos aquí si hemos adquirido el candado.
                try {
                    System.out.println(
                            "Trabajador: he adquirido el lock."
                    );

                    System.out.println(
                            "Trabajador: proceso el pedido."
                    );

                } finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                System.out.println(
                        "Trabajador: espera cancelada; "
                                + "no proceso el pedido."
                );

                Thread.currentThread().interrupt();
            }

        }, "Trabajador");

        // main adquiere el candado antes de arrancar al trabajador.
        lock.lock();

        try {
            System.out.println("main: tengo el lock.");

            trabajador.start();

            try {
                Thread.sleep(1000);


                System.out.println("main: interrumpo al trabajador.");

                trabajador.interrupt();

                // main conserva el candado un segundo más.
                Thread.sleep(1000);

                System.out.println("main: voy a liberar el lock.");

            } finally {
                lock.unlock();
            }

            trabajador.join();

            System.out.println("main: fin.");
        }
        catch (InterruptedException e)
        {
            System.out.println("ERROR: se ha producido una interrupción");
            System.out.println(e.getMessage());
        }
    }
}
