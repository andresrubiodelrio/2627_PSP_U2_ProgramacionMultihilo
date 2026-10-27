package basicos.herencia;

public class CreacionHilosHeredando {

    public static void main(String[] args)
    {
        //se crea un objeto de la clase que hereda de Thread
        HiloconThread hilo1=new HiloconThread();
        HiloconThread hilo2=new HiloconThread();

        System.out.println("[1]Soy el hilo principal y me llamo: " + Thread.currentThread().getName());
        System.out.println("[1]Voy a ejecutar un hilo que hereda de la clase Thread.\n");
        //invoca a start() y pone en marcha el hilo hilo1
        hilo1.run();
        hilo2.run();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        }


        System.out.println("[3]Soy el hilo principal y me llamo: " + Thread.currentThread().getName());


    }

    public static class HiloconThread extends Thread {


        //clase  que extiende a Thread
        @Override
        public void run() {

            // se redefine el método run() con el código asociado al hilo
            System.out.println("[2]¡Saludos desde un hilo que hereda de la clase Thread!.");
            System.out.println("[2]Mi nombre inicial es:" + getName());

            //Modificamos el nombre del hilo
            setName("Hilo Extend Thread");

            System.out.println("[2]Mi nombre tras la modificación realizada es: " + getName()+"\n");
            //System.out.println("[2]Mi nombre tras la modificación realizada es: " + Thread.currentThread().getName()+"\n");

        }
    }
}
