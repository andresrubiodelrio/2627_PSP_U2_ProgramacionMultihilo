package executor;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EjemploAwaitTermination {

    public static class Backup
    {
        public void realizarBackup(int identificador)
        {
            System.out.println("Backup "+ identificador + " iniciado " + " a las " + LocalTime.now() + " por "
                    + Thread.currentThread().getName());

            try {
                // Simulamos que el backup tarda de 3 segundos.
                Thread.sleep(3000);

                System.out.println("Backup " + identificador + " finalizado " + " a las " + LocalTime.now() + ".");

            } catch (InterruptedException e) {
                System.out.println("Backup " + identificador + " interrumpido " +  " a las " + LocalTime.now() + ".");
                Thread.currentThread().interrupt();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {

        Backup copiaSeguridad=new Backup();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {copiaSeguridad.realizarBackup(1);});
        executor.execute(() -> {copiaSeguridad.realizarBackup(2);});
        executor.execute(() -> {copiaSeguridad.realizarBackup(3);});

        // No acepta nuevas tareas; permite terminar la ya enviada.
        executor.shutdown();

        if (executor.isShutdown())
            System.out.println("Hilo Principal: solicitud de apagado realizada a las " + LocalTime.now() + " No se aceptan nuevas tareas.");

        System.out.println("Hilo Principal: esperamos 10 segundos a que termine el executor...");

        // Bloquea a main hasta que termine el executor
        // o transcurran 10 segundos.
        boolean terminado =executor.awaitTermination(10, TimeUnit.SECONDS);

        if (terminado) {
            System.out.println("Hilo Principal: el executor ha terminado: " + LocalTime.now());
        } else {
            System.out.println("Hilo Principal: han pasado 10 segundos y el executor aún no ha terminado: " + LocalTime.now());
        }

        System.out.println("El hilo principal continúa con el resto del programa.");
    }
}
