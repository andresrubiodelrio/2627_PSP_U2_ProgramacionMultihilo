package executor;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
El apagado en dos fases consiste en dar primero una oportunidad
a las tareas para terminar y, si tardan demasiado, solicitar su
interrupción. La segunda fase solo se ejecuta si la primera no
consigue completar el cierre.
 */
public class ApagadoEnDosFases {

    public static class Tarea implements Runnable {

        private final String nombre;

        public Tarea(String nombre) {
            if (nombre == null) {
                throw new NullPointerException("ERROR: El nombre de la tarea  no puede ser nulo.");
            }

            if (nombre.isBlank())
            {
                throw new IllegalArgumentException("ERROR: El nombre de la tarea no puede ser vacío.");
            }

            this.nombre = nombre;
        }

        @Override
        public void run() {
            System.out.println(nombre + " iniciada a las " + LocalTime.now());

            try {
                // Simulamos un trabajo de 10 segundos.
                //Thread.sleep(10000);
                Thread.sleep(1000);
                //Thread.sleep(200);

                System.out.println(nombre + " finalizada correctamente a las "+ LocalTime.now());

            } catch (InterruptedException e) {
                System.out.println(nombre + " interrumpida a las " + LocalTime.now());
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }



        @Override
        public String toString() {
            return nombre;
        }
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 5; i++) {
            executor.execute(new Tarea("Tarea " + i));
        }

        // 1. Solicitar el cierre permitiendo terminar lo ya enviado.
        executor.shutdown();

        System.out.println("Shutdown solicitado a las " + LocalTime.now() + ". No se aceptan nuevas tareas.");

        try {
            // 2. Esperar un máximo de 3 segundos.
            System.out.println("awaitTermination(): esperando hasta 3 segundos...");

            boolean terminado =executor.awaitTermination(3, TimeUnit.SECONDS);

            if (terminado) {
                System.out.println("El executor ha terminado antes de los 3 segundos concedidos: " + LocalTime.now());
            }
            else
            {
                // 3. Si no ha terminado, intentar interrumpir el trabajo.
                System.out.println("Tiempo agotado. A las " + LocalTime.now() + " no han terminado todas las tareas. Llamamos a shutdownNow().");

                List<Runnable> tareasPendientes = executor.shutdownNow();

                System.out.println(" Tras la llamada a ShutdownNow hay tareas que no llegaron a iniciarse. La lista de tareas no iniciadas son:");

                for (Runnable tarea : tareasPendientes) {
                    System.out.println("- " + tarea);
                }

                System.out.println("awaitTermination: Esperamos 5 segundos a que las tareas interrumpidas terminen: " + LocalTime.now()) ;

                // 4. Dar un tiempo adiconal a que las tareas respondan a la interrupción.
                if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.out.println("El executor ha terminado tras shutdownNow() y antes de los 5 segundos extras: " + LocalTime.now());
                } else {
                    System.out.println("El executor todavía no ha terminado tras shutdownNow() y tras los 5 segundos extras: " +  LocalTime.now());
                }
            }

        } catch (InterruptedException e) {
            // Este catch corresponde a la interrupción de main
            // mientras estaba esperando en awaitTermination().
            executor.shutdownNow();
            Thread.currentThread().interrupt();

            System.out.println("El hilo principal fue interrumpido mientras esperaba.");
        }

        System.out.println("Hilo principal continúa con su ejecución.");
    }
}
