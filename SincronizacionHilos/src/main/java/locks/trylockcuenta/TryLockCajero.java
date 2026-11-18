package locks.trylockcuenta;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockCajero {
    public static void main(String[] args) {
        Cajero cajero = new Cajero();

        Thread t1 = new Thread(() -> cajero.sacarDinero("María"));
        Thread t2 = new Thread(() -> cajero.sacarDinero("Fátima"));

        t1.start();
        t2.start();
    }

    public static class Cajero {

        private final Lock lock = new ReentrantLock();

        public void sacarDinero(String nombreCliente) {

            System.out.println(nombreCliente + " intenta usar el cajero...");

            try
            {
                if (lock.tryLock(2000, TimeUnit.MILLISECONDS))
                { // intenta sin esperar indefinidamente
                    try {
                        System.out.println(nombreCliente + " está usando el cajero");
                        Thread.sleep(1000); // simula operación
                        System.out.println(nombreCliente + " terminó");
                    } catch (InterruptedException e) {
                        System.out.println(nombreCliente + "ha sido interrumpido.");
                        System.out.println(e.getMessage());
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(nombreCliente + " no puedes usar el cajero porque se está accediendo a tu cuenta desde otro cajero.");
                }
            } catch (InterruptedException e) {
                System.out.println("ERROR: Operación interrumpida");
                System.out.println(e.getMessage());
            }
        }
    }
}
