package deadlock;

import locks.trylockbanco.TryLockBanco;

public class SolucionDeadLockOrdenConsistente {


    public static void main(String[] args) {
            DeadlockOrdered ejemplo = new DeadlockOrdered();

            //Thread t1 = new Thread(ejemplo::metodoA, "Hilo-1");
            //Thread t2 = new Thread(ejemplo::metodoB, "Hilo-2");

            Thread t1=new Thread(()-> ejemplo.metodoA(),"Hilo-1");
            Thread t2=new Thread(()-> ejemplo.metodoB(),"Hilo-2");

            t1.start();
            t2.start();
    }

    public static class DeadlockOrdered {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        // Ambos métodos adquieren primero lock1 y después lock2
        public void metodoA() {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " bloqueó lock1");

                // Espera para aumentar la probabilidad del deadlock
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    System.out.println("ERROR: El método A ha sido interrumpido.");
                    System.out.println(e.getMessage());
                    Thread.currentThread().interrupt();
                }

                System.out.println(Thread.currentThread().getName() + " intenta bloquear lock2...");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " bloqueó lock2");
                    // trabajo crítico
                }
            }
        }

        public void metodoB() {
            synchronized (lock1) {                // <-- NOTA: cambiamos el orden (antes era lock2 primero)
                System.out.println(Thread.currentThread().getName() + " bloqueó lock1");

                // Espera para aumentar la probabilidad del deadlock
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    System.out.println("ERROR: El método B ha sido interrumpido.");
                    System.out.println(e.getMessage());
                    Thread.currentThread().interrupt();
                }

                System.out.println(Thread.currentThread().getName() + " intenta bloquear lock2...");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " bloqueó lock2");
                    // trabajo crítico
                }
            }
        }

    }
}
