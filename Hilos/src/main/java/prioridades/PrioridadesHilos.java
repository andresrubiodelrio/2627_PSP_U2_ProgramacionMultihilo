package prioridades;



public class PrioridadesHilos {

    public static void main(String[] args)
    {
        final int NUM_HILOS = 5;

        //vectores para hilos de distintas prioridades
        Thread[] hiloMIN = new Thread[NUM_HILOS];
        Thread[] hiloNORM = new Thread[NUM_HILOS];
        Thread[] hiloMAX = new Thread[NUM_HILOS];

        //crea los hilos de prioridad mínima
        for (int i = 0; i < NUM_HILOS; i++) {
            hiloMIN[i] = new MiHilo(Thread.MIN_PRIORITY);
        }

        //crea los hilos de prioridad normal
        for (int i = 0; i < NUM_HILOS; i++) {
            hiloNORM[i] = new MiHilo();
        }

        //crea los hilos de máxima prioridad
        for (int i = 0; i < NUM_HILOS; i++) {
            hiloMAX[i] = new MiHilo(Thread.MAX_PRIORITY);
        }

        System.out.println("Hilos en proceso, espera......\nLos de mayor "
                + "prioridad tienden a terminar antes...\n");

        //inicia los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            hiloMIN[i].start();
            hiloNORM[i].start();
            hiloMAX[i].start();
        }

    }


    public static class MiHilo extends Thread {


        public MiHilo() {
            //hereda la prioridad del hilo padre
        }

        public MiHilo(int prioridad) {
            //establece la prioridad indicada
            this.setPriority(prioridad);
        }

        @Override
        public void run() {

            //cadena
            String strCadena = "";

            //agrega 20000 caracteres a una cadena vacía
            for (int i = 0; i < 20000; ++i) {
                //imprime el valor en la Salida
                strCadena += "A";

                try
                {
                    sleep(1);
                }
                catch (InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }

            }

            System.out.println("El Hilo llamado "+ this.getName() + " y prioridad " + this.getPriority()
                    + " ha terminado.");
        }
    }
}
