package executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

    public static class Ofertas
    {
        public void ofertar()
        {
            Thread hiloEjecutor=Thread.currentThread();
            System.out.println("Hilo " +  hiloEjecutor.getName() + " actualizando productos ofertables...");
        }
    }

    public static class StockMarket implements Runnable{

        @Override
        public void run() {
            Thread hiloEjecutor=Thread.currentThread();
            System.out.println("Hilo " +  hiloEjecutor.getName() + " actualizando el stock de datos de la web...");
        }
    }

    public static void main(String[] args) {
        Ofertas oferta = new Ofertas();

        ScheduledExecutorService executor= Executors.newScheduledThreadPool(2);

        executor.scheduleAtFixedRate(new StockMarket(),1000,5000, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(()-> oferta.ofertar(),500,10000, TimeUnit.MILLISECONDS);

        Thread hiloPrincipal=Thread.currentThread();

        try
        {
            hiloPrincipal.join(20000);
        }
        catch (InterruptedException e) {
            System.out.println("ERROR: El hilo " + Thread.currentThread().getName() + " ha sido interrumpido.");
            System.out.println(e.getMessage());
            hiloPrincipal.interrupt();
        }

        solicitarApagado(executor);
    }

    public static void solicitarApagado(ScheduledExecutorService executor)
    {
        if (executor==null)
            throw new NullPointerException("ERROR: Las tareas no pueden ser terminadas porque el objeto executor es nulo.");

        executor.shutdown();
        System.out.println("\nApagado del Executor solicitado. No se aceptan nuevas tareas. ");
    }
}
