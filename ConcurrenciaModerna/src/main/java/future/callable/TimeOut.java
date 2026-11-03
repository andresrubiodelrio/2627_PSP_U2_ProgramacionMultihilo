package future.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TimeOut {

    private static final long INICIO = System.nanoTime();

    public static class Tarea implements Callable<Double> {

        private int numero;
        private int unidadesVendidas;
        private double precioUnidad;
        private int duracionSegundos;

        public Tarea(int numero, int unidadesVendidas, double precioUnidad, int duracionSegundos) {
            setNumero(numero);
            setUnidadesVendidas(unidadesVendidas);
            setPrecioUnidad(precioUnidad);
            setDuracionSegundos(duracionSegundos);
        }

        public void setNumero(int numero) {
            if (numero<=0)
                throw new IllegalArgumentException("ERROR: El numero debe ser mayor que 0");
            this.numero = numero;
        }

        public void setUnidadesVendidas(int unidadesVendidas) {
            if (unidadesVendidas<=0)
                throw new IllegalArgumentException("ERROR: El número de unidades vendidas debe ser mayor que 0");

            this.unidadesVendidas = unidadesVendidas;
        }

        public void setPrecioUnidad(double precioUnidad) {
            if (precioUnidad<=0)
                throw new IllegalArgumentException("ERROR: El precio de cada unidad debe ser mayor que 0");

            this.precioUnidad = precioUnidad;
        }

        public void setDuracionSegundos(int duracionSegundos) {
            if (duracionSegundos<=0)
                throw new IllegalArgumentException("ERROR: La duración de la tarea debe ser mayor que 0");

            this.duracionSegundos = duracionSegundos;
        }

        @Override
        public Double call() throws InterruptedException {
            String nombreHilo = Thread.currentThread().getName();

            mostrar("[" + nombreHilo + "] INICIO de la tarea " + numero);

            // Simulamos una operación que tarda varios segundos.
            Thread.sleep(duracionSegundos * 1000);

            mostrar("[" + nombreHilo + "] FIN de la tarea " + numero);

            return this.unidadesVendidas*this.precioUnidad;
        }
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            List<Future<Double>> listaFuturos = new ArrayList<>();

            // Enviamos TODAS las tareas antes de esperar resultados.
            listaFuturos.add(executor.submit(new Tarea(1, 3,20.0,8)));
            listaFuturos.add(executor.submit(new Tarea(2, 4,33.50,2)));
            listaFuturos.add(executor.submit(new Tarea(3, 2,53.25,3)));

            List<Future<Double>> pendientes = new ArrayList<>();

            mostrar("PRIMERA RONDA: hasta cinco segundos por cada get().");

            double totalImporte=0;
            for (int i = 0; i < listaFuturos.size(); i++) {
                Future<Double> future = listaFuturos.get(i);

                try {
                    Double resultado = future.get(5, TimeUnit.SECONDS);

                    mostrar("Resultado de la tarea " + (i + 1) + ": " + resultado);
                    totalImporte+=resultado;
                    mostrar("Total acumulado: "+totalImporte);

                } catch (TimeoutException e) {
                    mostrar("No se ha obtenido a tiempo el resultado de la tarea " + (i + 1));

                    // Conservamos el MISMO Future para consultarlo después.
                    pendientes.add(future);

                } catch (ExecutionException e) {
                    mostrar("La tarea " + (i + 1) + " ha fallado: " + e.getCause());
                }
            }

            mostrar("Primera ronda finalizada. Resultados pendientes: " + pendientes.size());

            mostrar("SEGUNDA RONDA: recogemos los resultados pendientes.");

            for (Future<Double> future : pendientes) {
                try {
                    // Ahora decidimos esperar sin límite.
                    // La tarea NO se vuelve a enviar ni se reinicia.
                    Double resultado = future.get();

                    mostrar("Resultado pendiente recuperado: " + resultado);
                    totalImporte+=resultado;
                    mostrar("Total acumulado: "+totalImporte);

                } catch (ExecutionException e) {
                    mostrar("Una tarea pendiente ha fallado: " + e.getCause());
                }
            }

            mostrar("Fin de la recogida de resultados.");

        } catch (InterruptedException e) {
            // Si se interrumpe main durante cualquiera de las dos rondas,
            // abandonamos las esperas y solicitamos interrumpir las tareas.
            executor.shutdownNow();
            Thread.currentThread().interrupt();

            mostrar("El hilo principal ha sido interrumpido.");

        } finally {
            // Solicitamos el apagado incluso si se produce una excepción.
            executor.shutdown();
        }
    }

    private static void mostrar(String mensaje) {
        long segundos = TimeUnit.NANOSECONDS.toSeconds(
                System.nanoTime() - INICIO
        );

        System.out.printf("[%d s] %s%n", segundos, mensaje);
    }
}
