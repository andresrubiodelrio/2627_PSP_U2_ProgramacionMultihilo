package estados;

public class Viaje
{
    public static void main(String[] args) {

        Thread main =Thread.currentThread();

        Runnable viaje=()->{
            for(int i=1;i<11;i++)
            {
                System.out.println(i + " - " + Thread.currentThread().getName());
                try
                {
                    Thread.sleep((long)(Math.random()*1000));
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Finalizado viaje de: " + Thread.currentThread().getName());
            System.out.println("Estado de main: " + main.getState());
        };


        Thread v1=new Thread(viaje,"Islas Feroe");
        Thread v2=new Thread(viaje,"Malta");
        Thread v3=new Thread(viaje,"Francia");
        Thread v4=new Thread(viaje,"Islandia");

        System.out.println("Comienza el tour por diferentes países.");
        v1.start();
        v2.start();
        v3.start();
        v4.start();


        try {
            Thread.sleep(10000);
//            v1.join();
//            v2.join();
//            v3.join();
//            v4.join();
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Estado de main tras las esperas: " + main.getState());

    }

//    public static class ViajeTarea implements Runnable
//    {
//        private String nombre;
//
//        public ViajeTarea(String nombre)
//        {
//            this.nombre=nombre;
//        }
//
//        public String getNombre()
//        {
//            return nombre;
//        }
//
//        @Override
//        public void run() {
//            for(int i=0;i<10;i++)
//            {
//                System.out.println(i + " - " + nombre);
//                try
//                {
//                    Thread.sleep((long)(Math.random()*1000));
//                } catch (InterruptedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//            System.out.println("Me voy de viaje a: " + nombre);
//        }
//    }
}

