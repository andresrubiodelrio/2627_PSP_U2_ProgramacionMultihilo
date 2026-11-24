package productoresconsumidores.colacadenas;

import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Queue<String> cola=new LinkedList<>();
        int tamano=10;

        Thread productor=new Thread(new Productor(cola,tamano));
        Thread consumidor=new Thread(new Consumidor(cola,tamano));

        productor.start();
        consumidor.start();

    }
}
