package basicos;

public class CreacionVariosHilos {
    public static void main(String[] args)
    {
        //creamos 2 hilos del tipo Hilo_Thread con 2 constructores
        //diferentes
        MiHilo hiloThread1 = new MiHilo("Hilo Uno");
        MiHilo hiloThread2 = new MiHilo();

        //creamos un hilo Runnable en un paso
        Thread hiloRunnable = new Thread(new Tarea("Hilo Tres"));

        //ponemos en marcha los 3 hilos
        hiloThread1.start();
        hiloThread2.start();
        hiloRunnable.start();

        System.out.println("Soy el hilo principal " + Thread.currentThread().getName() + " y he terminado." + " y mi prioridad es: " + Thread.currentThread().getPriority()) ;

    }

    public static class Tarea implements Runnable {

        private String nombre="";

        public Tarea(String nombre)
        {
            this.nombre=nombre;
        }

        //clase que implementa Runnable
        public void run() {

            //redefinimos run() con el código asociado al hilo
            Thread hiloAsignado=Thread.currentThread();

            hiloAsignado.setName(nombre);

            for (int i = 1; i <= 5; i++) {
                System.out.println("Me llamo: "+hiloAsignado.getName()+ " y tengo la prioridad:"+hiloAsignado.getPriority());
            }
        }
    }

    public static class MiHilo extends Thread {
        //clase que extiende a Thread con 2 constructores

        public MiHilo(String nombre) {
            //constructor 1

            if (nombre==null)
                throw new NullPointerException("ERROR: El nombre de un hilo no puede ser nulo.");

            if (nombre.isBlank())
                throw new IllegalArgumentException("ERROR: El nombre de un hilo no puede ser vacío.");

            this.setName(nombre);
        }

        public MiHilo()
        {
            //constructor 2
        }


        @Override
        public void run() {
            //redefinimos run() con el código asociado al hilo
            for (int i = 1; i <= 5; i++) {
                System.out.println("Me llamo: "+this.getName()+ " y tengo la prioridad:"+this.getPriority());
            }
        }
    }
}
