package deadlock;

public class EjemploDeadLock {

    public static void main(String[] args) {
        DeadlockExample ejemplo = new DeadlockExample();

//        Thread t1 = new Thread(ejemplo::metodoA, "Hilo-1");
//        Thread t2 = new Thread(ejemplo::metodoB, "Hilo-2");

        Thread t1=new Thread(()-> ejemplo.metodoA(),"Hilo-1");
        Thread t2=new Thread(()-> ejemplo.metodoB(),"Hilo-2");

        t1.start();
        t2.start();
    }

    public static class DeadlockExample {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

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
                }

                System.out.println(Thread.currentThread().getName() + " intenta bloquear lock2...");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " bloqueó lock2");
                }
            }
        }

        public void metodoB() {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " bloqueó lock2");

                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    System.out.println("ERROR: El método B ha sido interrumpido.");
                    System.out.println(e.getMessage());
                }

                System.out.println(Thread.currentThread().getName() + " intenta bloquear lock1...");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + " bloqueó lock1");
                }
            }
        }
    }
}
