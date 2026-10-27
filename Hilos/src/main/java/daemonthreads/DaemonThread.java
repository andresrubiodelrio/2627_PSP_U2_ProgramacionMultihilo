package daemonthreads;

public class DaemonThread {

    public static void main(String[] args) {

        Thread hiloDemonio=new Thread(new Hilo(10), "Hilo Demonio");
        Thread hilo=new Thread(new Hilo(3), "Hilo Usuario");

        hiloDemonio.setDaemon(true);
        hiloDemonio.start();
        hilo.start();

        try {
            hilo.join();
//            hiloDemonio.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Hilos de usuario finalizados y por tanto, termina el hilo principal tras realizar el join");

    }
    public static class Hilo implements Runnable
    {
        private int segundos=0;

        public Hilo(int segundos)
        {
            setSegundos(segundos);
        }

        private void setSegundos(int segundos)
        {
            if (segundos<=0)
                throw new IllegalArgumentException("ERROR: El número de segundos no puede ser negativo.");

            this.segundos=segundos;

        }
        @Override
        public void run() {

            for (int i=0; i<segundos;i++)
            {
                try {
                    System.out.println("Soy la hebra " + Thread.currentThread().getName() + ". Me voy a dormir durante un segundo.");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
