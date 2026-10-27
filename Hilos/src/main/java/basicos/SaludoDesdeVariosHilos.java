package basicos;

public class SaludoDesdeVariosHilos {
    public static void main(String[] args) {

        //Objeto hilo que ya tiene asociado una tarea
        ThreadSaludo saludoThread= new ThreadSaludo();

        //Objeto que contiene la tarea
        Runnable tarea=new TareaSaludo();
        //Asigno esa tarea a un hilo
        Thread saludoRunnable=new Thread(tarea);

        //Objeto que contiene una tarea en una clase anónima
        //Aquí ya no declaramos una clase llamada TareaSaludo:
        // proporcionamos su implementación directamente donde la necesitamos.
        Runnable tareaAnonima = new Runnable() {

            @Override
            public void run() {
                System.out.println("Hola desde el hilo");
            }
        };
        //Asigno esa tarea anónima a un hilo
        Thread saludoAnonimo = new Thread(tareaAnonima);

        //la lambda proporciona el mismo trabajo que antes estaba dentro de run()
        // La tarea no recibe parámetros al igual que el método run()
        Runnable tareaLambda = () -> System.out.println("Hola desde el hilo");
        Thread saludoLamdba = new Thread(tareaLambda);

        saludoThread.start();
        saludoRunnable.start();
        saludoAnonimo.start();
        saludoLamdba.start();

    }

    public static class ThreadSaludo extends Thread{

        @Override
        public void run() {
            System.out.println("Hola desde el hilo");
        }
    }

    public static class TareaSaludo implements Runnable{
        @Override
        public void run() {
            System.out.println("Hola desde el hilo");
        }
    }
}
