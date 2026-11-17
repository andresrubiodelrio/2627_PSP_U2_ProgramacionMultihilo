package basicos.sincronizacionmultiple;


public class ProblemaSincronizacionMultipleResuelto {

    public static void main(String[] args) {
        HiloSegmentoSincronizado tarea=new HiloSegmentoSincronizado();

        Thread t1=new Thread(()->{
            for(int i=0;i<10;i++)
                tarea.incrementar1();
        });

        Thread t2=new Thread(()->{
            for(int i=0;i<10;i++)
                tarea.incrementar2();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Valor de contador1: " + tarea.getContador1());
        System.out.println("Valor de contador2: " + tarea.getContador2());
        //ejemplo.creaHilos();

    }

    public void creaHilos()
    {

    }
}
