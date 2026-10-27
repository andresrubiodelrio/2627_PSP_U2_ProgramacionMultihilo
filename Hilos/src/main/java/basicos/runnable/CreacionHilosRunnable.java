package basicos.runnable;

public class CreacionHilosRunnable {

    public static void main(String[] args) {

        //se crea un objeto Thread
        Thread hilo1= new Thread(new HiloRunnable());

        System.out.println("[1]Soy el hilo principal y me llamo: " + Thread.currentThread().getName());
        System.out.println("[1]Voy a ejecutar un hilo que implementa la interfaz Runnable\n");

        hilo1.start();
        System.out.println("[3]Soy el hilo principal y me llamo: " + Thread.currentThread().getName());

    }

    public static class HiloRunnable implements Runnable
    {

        public void run()
        {
            System.out.println("[2]Saludos desde un hilo Runnable");

            //Para usar los métodos básicos de Thread (getName, setName, ...) debemos obtener el objeto Thread
            //asociado al hilo.

            System.out.println("[2]Mi nombre inicial es:"+Thread.currentThread().getName());

            //Modificamos el nombre del hilo
            Thread.currentThread().setName("Hilo Implements Runnable");

            //System.out.println("[2]Mi nombre tras la modificación realizada es: " + getName()+"\n");
            System.out.println("[2]Mi nombre tras la modificación realizada es: " + Thread.currentThread().getName()+"\n");
        }

    }
}
