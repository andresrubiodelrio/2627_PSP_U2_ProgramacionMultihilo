package productoresconsumidores.pintorvendedor;

public class AlmacenCuadros {
    //recurso compartido => contador de cuadros en almacen

    private int cuadros=0;

    public synchronized void guardar(){

        try
        {
            //mientras haya cuadros sin sacar hay que esperar
            while(cuadros>0)
                //el hilo invoca wait() y se pone en la cola de espera wait del objeto
                this.wait();
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
            System.out.println("El método guardar ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
        System.out.println("PINTOR: Voy a pintar un nuevo cuadro");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("ERROR: No ha sido posible pintar un nuevo cuadro. Me he quedado sin pintura.");
            System.out.println("El método guardar ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
        //incrementa en 1 simulando que guarda un cuadro
        cuadros++;

        //notifica que se ha producido el evento 'pintado y guardado un cuadro'
        System.out.println("Pintado cuadro. Total cuadros pendientes de vender: "+ cuadros);
        this.notify();
    }

    public synchronized void sacar(){

        try{
            //mientras no haya cuadros hay que esperar
            while(cuadros ==0)
                //el hilo invoca wait() y se pone en la cola de espera wait del objeto
                this.wait();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
            System.out.println("El método sacar ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }

        System.out.println("VENDEDOR: Voy a vender un nuevo cuadro");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("ERROR: No ha sido posible vender el nuevo cuadro. Buscaré más compradores.");
            System.out.println("El método sacar ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }
        //decrementa 1 simulando que vende un cuadro
        cuadros--;

        //notifica que se ha producido el evento 'vendido y sacado un cuadro'
        System.out.println("Vendido cuadro. Total cuadros pendientes de vender: "+ cuadros);
        this.notify();
    }
    
} // fin clase AlmacenCuadros
