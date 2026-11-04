package future.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ProcesamientoPedidos {

    public static class CalcularImportePedido implements Callable<Double> {

        private int numeroPedido;
        private int unidades;
        private double precioUnitario;
        private int tiempoProceso;

        public CalcularImportePedido(int numeroPedido, int unidades,
                                     double precioUnitario, int tiempoProceso) {
            setNumeroPedido(numeroPedido);
            setUnidades(unidades);
            setPrecioUnitario(precioUnitario);
            setTiempoProceso(tiempoProceso);
        }

        private void setNumeroPedido(int numeroPedido) {
            if (numeroPedido<=0) {
                throw new IllegalArgumentException("ERROR: El número de pedido debe ser mayor a 0");
            }
            this.numeroPedido=numeroPedido;
        }

        public int getNumeroPedido()
        {
            return numeroPedido;
        }

        private void setUnidades(int unidades) {

            if (unidades<=0) {
                throw new IllegalArgumentException("ERROR: El número de unidades del pedido " + this.numeroPedido + " debe ser mayor a 0");
            }
            this.unidades = unidades;
        }

        public int getUnidades()
        {
            return unidades;
        }

        private void setPrecioUnitario(double precioUnitario) {
            if (precioUnitario<=0){
                throw new IllegalArgumentException("ERROR: El precio de las unidades el pedido " + this.numeroPedido + " debe ser mayor a 0");
            }

            this.precioUnitario = precioUnitario;
        }

        public double getPrecioUnitario()
        {
            return precioUnitario;
        }

        private void setTiempoProceso(int tiempoProceso) {
            if (tiempoProceso<=0) {
                throw new IllegalArgumentException("ERROR: El tiempo de proceso del pedido " + this.numeroPedido + " debe ser mayor a 0");
            }
            this.tiempoProceso = tiempoProceso;
        }

        @Override
        public Double call() throws InterruptedException {
            String nombreHilo = Thread.currentThread().getName();

            System.out.printf("[%s] INICIO del pedido %d%n",
                    nombreHilo, numeroPedido);

            // Fallo simulado durante el procesamiento.
            if (numeroPedido == 8) {
                throw new IllegalArgumentException(
                        "No se ha podido consultar el stock del pedido " + numeroPedido
                );
            }
            // Simula el tiempo necesario para procesar el pedido.
            Thread.sleep(tiempoProceso);

            double importe = unidades * precioUnitario;

            System.out.printf("[%s] FIN del pedido %d. Importe: %.2f euros%n",
                    nombreHilo, numeroPedido, importe);

            return importe;
        }
    }

    public static void main(String[] args) {

        // Tres hilos de trabajo para procesar ocho pedidos.
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<CalcularImportePedido> tareas = new ArrayList<>();

        // Pedido, unidades, precio unitario y tiempo en milisegundos.
        tareas.add(new CalcularImportePedido(1, 3, 20.00, 4000));
        tareas.add(new CalcularImportePedido(2, 2, 15.50, 1000));
        tareas.add(new CalcularImportePedido(3, 5,  8.00, 2000));
        tareas.add(new CalcularImportePedido(4, 1, 120.00, 1000));
        tareas.add(new CalcularImportePedido(5, 4, 12.50, 2000));
        tareas.add(new CalcularImportePedido(6, 2, 35.00, 1000));
        tareas.add(new CalcularImportePedido(7, 6,  5.00, 2000));
        tareas.add(new CalcularImportePedido(8, 3, 18.00, 1000));
        //tareas.add(new CalcularImportePedido(9, -3, 18.00, 1000));

        List<Future<Double>> listaFuturos = new ArrayList<>();

        // Primero enviamos TODAS las tareas y guardamos sus Future.
        for (CalcularImportePedido tarea : tareas) {
            Future<Double> future = executor.submit(tarea);
            //Si enviásemos una tarea y llamásemos inmediatamente a su get() antes de enviar la siguiente,
            // procesaríamos los pedidos de uno en uno y desaprovecharíamos el pool.
            //Por eso es mejor lanzar todas  las tareas e ir añadiendo en una lista los future que han sido
            //creados para guardar cada resultado.
            listaFuturos.add(future);
        }

        // Las tareas enviadas siguen ejecutándose,
        // incluidas las que están esperando en cola.
        executor.shutdown();

        // Después recogemos los resultados en el orden de envío.
        double total = 0;
        for (int i = 0; i < listaFuturos.size(); i++) {
            Future<Double> future = listaFuturos.get(i);

            double importe = 0;
            try {
                importe = future.get();
                System.out.printf("[Hilo Principal] Pedido %d: %.2f euros%n", i + 1, importe);

                total += importe;
            }
            catch (InterruptedException e)
            {
                System.out.println("ERROR: El hilo principal ha sido interrumpido.");
                executor.shutdownNow();
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
            catch (ExecutionException e)
            {
                System.out.println("ERROR: Se ha producido un error durante la ejecución del pedido número " + i+1);
                System.out.println(e.getMessage());
            }
        }

        System.out.printf("Importe total de los pedidos: %.2f euros%n",total);
    }
}
