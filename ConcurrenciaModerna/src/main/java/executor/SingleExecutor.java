package executor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleExecutor {

    public static class Tarea implements Runnable{
        private int identificador;

        public Tarea(int identificador)
        {
            if (identificador<0)
                throw new IllegalArgumentException("ERROR: El identificador de una tarea no puede ser negativo.");

            this.identificador=identificador;
        }


        @Override
        public void run() {
            Random rand = new Random();
            int duracionTarea = rand.nextInt(11);

            System.out.println("Tarea con identificador " + identificador + " está siendo ejecutada por el hilo: " + Thread.currentThread().getName() + " y tengo una duración de " + duracionTarea + " segundos.");

            try
            {
                Thread.sleep(duracionTarea*1000);
            } catch (InterruptedException e) {
                System.out.println("ERROR: El hilo " + Thread.currentThread().getName() + " ha sido interrumpido.");
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) {

        // Ejecutará las tareas secuencialmente una tras otra en un solo hilo.
        ExecutorService executor= Executors.newSingleThreadExecutor();

        System.out.println("Creación de tres tareas iniciales.");
        //Creamos 3 tareas que son ejecutadas por el mismo hilo
        for(int i=0;i<3;i++)
        {
            executor.execute(new Tarea(i+1));
            //executor.submit(new Tarea(i+1));
        }
        System.out.println("Creación de 5 tareas adicionales.");
        //Creamos 5 tareas que son ejecutadas por el mismo hilo.
        for (int i = 1; i <= 5; i++) {

            int taskId = i;
            //El método submit recibe como parámetro el Runnable. En este caso como una expresión lambda.
            executor.submit(() ->{
                    Random rand = new Random();
                    int duracionTarea = 1+ rand.nextInt(10);
                    System.out.println("Tarea " + taskId + " ejecutada por " + Thread.currentThread().getName() + " y tengo una duración de " + duracionTarea + " segundos.");
                    try {
                        //Simulamos el tiempo de realización de la tarea a ejecutar
                        Thread.sleep(duracionTarea*1000);
                    }
                    catch (InterruptedException e) {
                        System.out.println("ERROR: El hilo " + Thread.currentThread().getName() + " ha sido interrumpido.");
                        System.out.println(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
            });
        }

        executor.shutdown();
        System.out.println("Shutdown solicitado. No se aceptan nuevas tareas.");
    }
}
