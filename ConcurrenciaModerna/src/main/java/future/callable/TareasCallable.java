package future.callable;

import java.util.Random;
import java.util.concurrent.*;

public class TareasCallable {
    public static void main(String[] args) {
        Callable<String> tareaLimpiarCadena=()->{
            String cadena="    Hola desde Callable     ";
            //Se tardan 5 segundos en limpiar la cadena
            Thread.sleep(5000);
            System.out.println("Tarea 1: cadena limpia.");

            return cadena.trim();
        };

        Callable<Integer> tareaDobleDeNumero=()->{
            Random random=new Random();
            int numeroAleatorio=1+ random.nextInt(10); //Número aleatorio entre 0 y 10
            //Se tardan 3 segundos en generar el número aleatorio
            Thread.sleep(3000);
            System.out.println("Tarea 2: Generado el doble de: "+numeroAleatorio);
            return 2*numeroAleatorio;
        };

        ExecutorService executor= Executors.newSingleThreadExecutor();

        System.out.println("Hilo Principal: Lanzamos tarea 1");
        Future<String> futureCadena=executor.submit(tareaLimpiarCadena);
        System.out.println("Hilo Principal: Lanzamos tarea 2");
        Future<Integer> futureInteger=executor.submit(tareaDobleDeNumero);

        String cadenaLimpia= null;
        Integer numeroGenerado=null;
        try {
            System.out.println("Hilo principal: Bloqueado hasta obtener las respuestas de las tareas lanzadas.");
            cadenaLimpia = futureCadena.get(); //Se bloquea hasta que el resultado esté disponible.
            numeroGenerado = futureInteger.get();

        } catch (InterruptedException e) {
            System.out.println("ERROR: El hilo principal ha sido interrumpido.");
            executor.shutdown();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.out.println("ERROR: Se ha producido un error durante la ejecución de una tarea");
        }

        System.out.println("Cadena limpia: " + cadenaLimpia);
        System.out.println("Número aleatorio generado: " + String.valueOf(numeroGenerado));
        System.out.println("Finalizamos pool");
        executor.shutdown();

    }
}
