package locks.trylockbanco;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {

    private final Lock lock = new ReentrantLock();

    public void accederCuenta(String nombre) {

        if (lock.tryLock())
        {
            // intenta bloquear sin esperar
            try {
                System.out.println(nombre + " accedió a la cuenta.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("ERROR: Hilo " + nombre + " interrumpido");
                System.out.println(e.getMessage());
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(nombre + " no pudo acceder. Recurso ocupado.");
        }
    }
}
