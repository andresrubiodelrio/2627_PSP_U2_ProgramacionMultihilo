package productoresconsumidores.colacadenas;

import java.util.Objects;
import java.util.Queue;

public class Consumidor implements Runnable {
    private Queue<String> cola;
    private int tamano;

    public Consumidor(Queue<String> cola, int tamano)
    {
        setCola(cola);
        setTamano(tamano);
    }

    public Queue<String> getCola() {
        return cola;
    }

    public void setCola(Queue<String> cola) {
        Objects.requireNonNull(cola,"ERROR: La cola no puede ser un objeto nulo.");
        this.cola = cola;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        if(tamano<=0)
            throw new IllegalArgumentException("ERROR: El tamaño de la cola no puede ser menor o igual a 0.");
        this.tamano = tamano;
    }

    @Override
    public void run() {
        while (true)
            consumeDato();
    }

    private void consumeDato()
    {
        try
        {
            synchronized (cola)
            {
                if (cola.isEmpty())
                {
                    //Si la cola está vacía, el consumimdor debe esperar a que se produzcan datos
                    System.out.println("Consumidor debe esperar...");
                    cola.wait();
                }

                Thread.sleep(700); //Simulamos el tiempo de procesamiento de consumir un dato

                String dato=cola.remove();
                System.out.println("Consumidor consume dato. Dato consumido: " + dato + ". Total datos sin consumir: " + cola.size());

                if (cola.size()==9)
                {
                    //Como ya se ha consumido un dato, el productor ya puede producir
                    cola.notify();
                }

            }


        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
            System.out.println("El hilo consumidor ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }

    }
}
