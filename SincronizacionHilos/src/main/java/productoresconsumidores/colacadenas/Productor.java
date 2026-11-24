package productoresconsumidores.colacadenas;

import java.util.Objects;
import java.util.Queue;

public class Productor implements Runnable{
    private Queue<String> cola;
    private int tamano;

    public Productor(Queue<String> cola, int tamano)
    {
        setCola(cola);
        setTamano(tamano);

    }

    public void setCola(Queue<String> cola)
    {
        Objects.requireNonNull(cola,"ERROR: La cola no puede ser un objeto nulo.");
        this.cola=cola;
    }

    public Queue<String> getCola() {
        return cola;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        if (tamano<=0)
            throw new IllegalArgumentException("ERROR: El tamaño de la cola no puede ser menor o igual a 0");

        this.tamano = tamano;
    }

    @Override
    public void run()
    {
        while (true)
            produceDato();
    }

    private void produceDato()
    {
        try
        {
            synchronized (cola)
            {
                if (cola.size()==tamano)
                {
                    //Si la cola está llena, el productor debe esperar a que se consuman datos
                    System.out.println("Productor, esperando...");
                    cola.wait();
                }

                Thread.sleep(1000); //Simulamos que el tiempo de procesamiento sea 1ms

                cola.add("id " + cola.size());
                System.out.println("Productor produce nuevo dato. Total datos sin consumir: " + cola.size());

                if (cola.size()==1)
                    //Como ya se ha producido un dato, el consumidor ya puede consumir
                    cola.notify();

            }
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
            System.out.println("El hilo productor ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }


    }
}
