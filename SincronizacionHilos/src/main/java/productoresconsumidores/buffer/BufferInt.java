package productoresconsumidores.buffer;

import java.util.ArrayList;
import java.util.List;

public class BufferInt {
    private List<Integer> buffer=new ArrayList<>();
    private static final int CAPACIDAD=5;

    public synchronized void produce()
    {
        //if(buffer.size()==CAPACIDAD) {
        while(buffer.size()==CAPACIDAD) {
            System.out.println("Buffer completo, productor esperando...");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                System.out.println("El método produce ha sido interrumpido.");
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Productor añade nuevos items...");
        for(int i=0;i<CAPACIDAD;i++)
        {
            buffer.add(i);
            System.out.println("Productor añade el item " + i);
        }

        notify();
    }

    public synchronized void consume()
    {
        //if (buffer.size()<capacidad)
        while (buffer.size()<CAPACIDAD)
        {
            System.out.println("Buffer vacío, consumidor esperando...");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                System.out.println("El método consume ha sido interrumpido.");
                Thread.currentThread().interrupt();
            }
        }

        while(!buffer.isEmpty())
        {
            int item=buffer.remove(0);
            System.out.println("Consumidor consume item " + item);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                System.out.println("El método consume ha sido interrumpido.");
                Thread.currentThread().interrupt();
            }
        }

        notify();
    }

}
