package future.callable;

import java.util.Random;
import java.util.concurrent.*;

public class TareasCallable {
    public static void main(String[] args) {
        Callable<String> tareaLimpiarCadena=()->{
            String cadena="    Hola desde Callable     ";
            System.out.println("Tarea: Limpiando cadena.");
            //Se tardan 5 segundos en limpiar la cadena
            Thread.sleep(5000);
            return cadena.trim();
        };

        Callable<Integer> tareaDobleDeNumero=()->{
            Random random=new Random();
            int numeroAleatorio=random.nextInt(10); //Número aleatorio entre 0 y 10
            System.out.println("Tarea: Generando el doble de: "+numeroAleatorio);
            //Se tardan 3 segundos en generar el número aleatorio
            Thread.sleep(3000);
            return 2*numeroAleatorio;
        };

        ExecutorService executor= Executors.newSingleThreadExecutor();

        Future<String> futureCadena=executor.submit(tareaLimpiarCadena);
        Future<Integer> futureInteger=executor.submit(tareaDobleDeNumero);

        String cadenaLimpia= null;
        Integer numeroGenerado=null;
        try {

            cadenaLimpia = futureCadena.get(); //Se bloquea hasta que el resultado esté disponible.
            numeroGenerado = futureInteger.get();

        } catch (InterruptedException e) {
            System.out.println("ERROR: La tarea ha sido interrumpida.");
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
