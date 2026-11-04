package executor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class FixedExecutor {

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
            int duracionTarea = 1+rand.nextInt(10);

            System.out.println("Tarea con identificador " + identificador + " está siendo ejecutada por el hilo: " + Thread.currentThread().getName() + " y tengo una duración de " + duracionTarea + " segundos.");

            try
            {
                //Simulación de la realizacón de la tarea
                Thread.sleep(duracionTarea*1000);
            } catch (InterruptedException e) {
                System.out.println("ERROR: El hilo " + Thread.currentThread().getName() + " ha sido interrumpido.");
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) {
        // Ejecutará las tareas dos hilos.
        ExecutorService executor= Executors.newFixedThreadPool(2);

        //Creamos 10 tareas que son ejecutadas por los dos hilos
        for(int i=0;i<10;i++)
        {
            executor.execute(new Tarea(i+1));
            //executor.submit(new Tarea(i+1));
        }

        try
        {
            System.out.println("Creación de la tarea 11");
            executor.execute(new Tarea(11));
        }
        catch (RejectedExecutionException e)
        {
            System.out.println("ERROR: Se ha tratado de cargar una nueva tarea. Sin embargo, la cola de tareas ya no acepta más tareas.");
        }

        executor.shutdown();
        System.out.println("Apagado solicitado.No se aceptan nuevas tareas.");

        try
        {
            System.out.println("Creación de la tarea 12");
            executor.execute(new Tarea(12));
        }
        catch (RejectedExecutionException e)
        {
            System.out.println("ERROR: Se ha tratado de cargar una nueva tarea. Sin embargo, la cola de tareas ya no acepta más tareas.");
        }

    }
}
