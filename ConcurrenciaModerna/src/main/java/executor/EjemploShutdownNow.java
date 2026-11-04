package executor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EjemploShutdownNow {

    public static class Tarea implements Runnable {

        private final String nombre;

        public Tarea(String nombre) {
            if (nombre==null) {
                throw new  NullPointerException("ERROR: El nombre de la tarea no puede ser nulo.");
            }

            if (nombre.isBlank())
                throw new IllegalArgumentException("ERROR: El nombre de la tarea no puede ser vacío.");

            this.nombre = nombre;
        }

        @Override
        public void run() {

            System.out.println(nombre + " iniciada por "
                    + Thread.currentThread().getName());

            try {
                // Simulamos un trabajo que tarda 10 segundos.
                Thread.sleep(10000);

                System.out.println(nombre + " finalizada normalmente.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("La tarea " + nombre + " ha sido interrumpida antes de terminar.");

            }
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    public static void main(String[] args)  {

        // Solo puede ejecutar una tarea a la vez.
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            // Enviamos cinco tareas.
            for (int i = 1; i <= 5; i++) {
                executor.execute(new Tarea("Tarea " + i));
            }

            // El hilo principal espera cinco segundos antes del apagado.
            Thread.sleep(5000);

            System.out.println("Solicitando shutdownNow()...");

            // Recogemos las tareas que permanecían en cola.
            List<Runnable> noEjecutadas = executor.shutdownNow();

            System.out.println("Número de tareas que no han llegado a iniciarse: "  + noEjecutadas.size());

            System.out.println("Lista de tareas no iniciadas:");

            for (Runnable tarea : noEjecutadas) {
                System.out.println("- " + tarea);
            }

        }
        catch (InterruptedException e) {
            System.out.println("El hilo principal ha sido interrumpido.");
            executor.shutdownNow();
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        finally {
            // También asegura el cierre si main se interrumpe.
            executor.shutdownNow();
        }
    }
}
