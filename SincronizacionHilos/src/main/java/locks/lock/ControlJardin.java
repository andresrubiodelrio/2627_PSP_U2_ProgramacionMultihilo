package locks.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControlJardin {

    public static class Jardin {

        private int visitas = 0;

        private final Lock lock = new ReentrantLock();

        public void registrarVisita() {
            lock.lock();

            try {
                visitas++;
            } finally {
                lock.unlock();
            }
        }

        public int getVisitas() {
            lock.lock();

            try {
                return visitas;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {

            Jardin jardin = new Jardin();

            Runnable entrada = () -> {
                for (int i = 0; i < 100; i++) {
                    jardin.registrarVisita();
                }
            };

            Thread norte = new Thread(entrada, "Entrada norte");
            Thread sur = new Thread(entrada, "Entrada sur");

            norte.start();
            sur.start();

            try {
                norte.join();
                sur.join();
            }
            catch (InterruptedException e)
            {
                System.out.println("ERROR: El hilo principal ha sido interrumpido.");
                System.out.println(e.getMessage());
            }

            System.out.println("Visitas: " + jardin.getVisitas());
        }
}
