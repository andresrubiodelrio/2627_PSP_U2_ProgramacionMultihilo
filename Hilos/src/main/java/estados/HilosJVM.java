package estados;

public class HilosJVM {

    public static void main(String[] args) {
        Thread runner1=new Thread(new Runner1(),"MiHebra-1");
        Thread runner2=new Thread(new Runner2(), "MiHebra-2");

        runner1.start();
        runner2.start();

        for (Thread t:Thread.getAllStackTraces().keySet())
        {
            System.out.println("Nombre de la hebra: " + t.getName() + ". Estado de la hebra: " + t.getState() + ". Prioridad de la hebra: " + t.getPriority());
        }

        //La hebra principal espera a que las otros dos terminen (Join Point)
        try {
            runner1.join();
            runner2.join();
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Finalizadas todas las hebras.");
        for (Thread t:Thread.getAllStackTraces().keySet())
        {
            System.out.println("Nombre de la hebra: " + t.getName() + ". Estado de la hebra: " + t.getState() + ". Prioridad de la hebra: " + t.getPriority());
        }

    }
    public static class Runner1 implements Runnable
    {

        @Override
        public void run()
        {
            for(int i=0;i<10;i++)
            {
                try
                {
                    Thread.sleep(1000);
                    //Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }

                System.out.println("Runner1: " + i );
            }
        }
    }

    public static class Runner2 implements Runnable
    {

        @Override
        public void run()
        {
            for(int i=0;i<10;i++)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }

                System.out.println("Runner2: " + i );

            }
        }
    }
}


