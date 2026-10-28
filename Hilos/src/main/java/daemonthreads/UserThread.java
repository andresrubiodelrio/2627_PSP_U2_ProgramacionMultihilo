package daemonthreads;

public class UserThread {

    public static void main(String[] args) {
        int tiempoEnSegundos=3;

        Thread hiloTemporizador=new Thread(new Temporizador(tiempoEnSegundos),"Hilo Usuario");

        hiloTemporizador.start();

        try {
            hiloTemporizador.join();
            System.out.println("Han pasado unos " + tiempoEnSegundos + " segundos");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
