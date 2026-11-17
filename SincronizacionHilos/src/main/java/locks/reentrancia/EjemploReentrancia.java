package locks.reentrancia;

import java.util.concurrent.locks.ReentrantLock;

public class EjemploReentrancia {

    //En este ejemplo hemos declarado el objeto lock como ReentrantLock, en lugar de Lock,
    // porque getHoldCount() es un método específico de esa clase.
    private final ReentrantLock lock = new ReentrantLock();

    public void metodoA() {
        lock.lock();

        try {
            System.out.println("A: " + lock.getHoldCount());

            metodoB();

            System.out.println("A, después de B: " + lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }

    public void metodoB() {
        lock.lock();

        try {
            System.out.println("B: " + lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EjemploReentrancia ejemplo =  new EjemploReentrancia();

        ejemplo.metodoA();

        System.out.println("Tras finalizar A: " + ejemplo.lock.getHoldCount());
    }
}
