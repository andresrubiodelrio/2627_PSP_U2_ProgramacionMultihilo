package daemonthreads;

public class DaemonThread {

    public static void main(String[] args) {

        Thread hiloUsuario=new Thread(new Temporizador(3), "Hilo Usuario");
        Thread hiloDemonio=new Thread(new Temporizador(10), "Hilo Demonio");

        hiloDemonio.setDaemon(true);

        hiloDemonio.start();
        hiloUsuario.start();

        try {
            hiloUsuario.join();
            hiloDemonio.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Hilos de usuario finalizados y por tanto, termina el hilo principal tras realizar el join");

    }
    public static class Temporizador implements Runnable
    {
        private int cuentaSegundos=0;

        public Temporizador(int cuentaSegundos)
        {
            setSegundos(cuentaSegundos);
        }

        private void setSegundos(int cuentaSegundos)
        {
            if (cuentaSegundos<=0)
                throw new IllegalArgumentException("ERROR: El número de segundos no puede ser negativo.");

            this.cuentaSegundos=cuentaSegundos;

        }
        @Override
        public void run() {

            for (int i=0; i<cuentaSegundos;i++)
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
