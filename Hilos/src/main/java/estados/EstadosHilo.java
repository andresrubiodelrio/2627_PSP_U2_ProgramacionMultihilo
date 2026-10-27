package estados;

import static java.lang.Thread.sleep;

public class EstadosHilo {
    public static Thread hiloPrincipal;

    public static void main(String[] args) {


        hiloPrincipal=Thread.currentThread();


        Thread hiloAuxiliar=new Thread(new TareaHilo());


        //Crea un nuevo hilo. El hilo está en estado New
        System.out.println("[1] Hilo Auxiliar: Estado=" + hiloAuxiliar.getState()
                + ",¿Vivo? isAlive()=" + hiloAuxiliar.isAlive()  + "\n");

        System.out.println("Llamamos al método start del hilo auxiliar. Por tanto, debe cambiar su estado.");
        //Inicia el thread hilo y pasa al estado Runnable
        hiloAuxiliar.start();
        System.out.println("[2] Hilo Auxiliar: Estado=" + hiloAuxiliar.getState()
                + ",¿Vivo? isAlive()=" + hiloAuxiliar.isAlive()  + "\n" );

        //Consultamos el estado del hilo un número grande de veces para comprobar que el hilo pasa por el
        //estado TIMED_WAITING.
        for(int i=0;i<500;i++)
        {
            System.out.println("[3] Hilo Auxiliar: Estado=" + hiloAuxiliar.getState()
                    + ",¿Vivo? isAlive()=" + hiloAuxiliar.isAlive() + "\n");
        }

        try
        {
            //El hilo principal espera a que el hilo auxiliar termine
            System.out.println("[4] El hilo principal se va al estado de WAITING porque va a esperar al hilo auxiliar con el método join.");
            hiloAuxiliar.join();
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }

        //Cuando se llegue a este punto el hilo auxiliar ha terminado ya que el hilo principal ha estado
        //esperando (WAINTING) a que termine su ejecución.
        System.out.println("\n[5] Hilo Auxiliar: Estado=" + hiloAuxiliar.getState()
                + ",¿Vivo? isAlive()=" + hiloAuxiliar.isAlive());
    }

    public static class TareaHilo implements  Runnable {
        @Override
        public void run() {
            for(int i=10;i>=1;i--)
            {
                try
                {
                    System.out.print(i+",");
                    sleep(100);
                    //sleep(Duration.ofMillis(100));
                    System.out.println("[6] Hilo Principal: Estado= " + hiloPrincipal.getState());
                }
                catch (InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

}
