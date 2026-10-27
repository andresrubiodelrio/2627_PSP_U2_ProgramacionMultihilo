package basicos.anonima;


public class CreacionHilosAnonimoRunnable {
    public static void main(String[] args) {


        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                System.out.println("[2]Saludos desde un hilo anónimo Runnable");

                //Para usar los métodos básicos de Thread (getName, setName, ...) debemos obtener el objeto Thread
                //asociado al hilo.
                System.out.println("[2]Mi nombre inicial es:"+Thread.currentThread().getName());

                //Modificamos el nombre del hilo
                Thread.currentThread().setName("Hilo con clase anónima");

                System.out.println("[2]Mi nombre tras la modificación realizada es: " + Thread.currentThread().getName()+"\n");
            }
        };

        Thread hilo1=new Thread(runnable);

        System.out.println("[1]Soy el hilo principal y me llamo: " + Thread.currentThread().getName());
        System.out.println("[1]Voy a ejecutar un hilo de una clase anónima que implementa la interfaz Runnable\n");

        try {
            hilo1.start();
//          hilo1.start();
        }
        catch (IllegalThreadStateException e)
        {
            System.out.println("ERROR: Se está llamando más de una vez al método start del hilo.");
        }

        System.out.println("[3]Soy el hilo principal y me llamo: " + Thread.currentThread().getName());


    }


}
