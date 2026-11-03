package future.callable;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class MaximoArray {

    public static final int LONGITUD_ARRAY=50;

    public static void main(String[] args) {

        int [] numeros=new int[LONGITUD_ARRAY];

        inicializaArray(numeros);

        ExecutorService executor=Executors.newFixedThreadPool(2);

        HiloMaximo tarea1 = new HiloMaximo(numeros,0,(LONGITUD_ARRAY/2)-1,"Hilo-1");
        HiloMaximo tarea2 = new HiloMaximo(numeros,LONGITUD_ARRAY/2,LONGITUD_ARRAY-1,"Hilo-2");

        Future<Integer> maximoTramo1=executor.submit(tarea1);
        Future<Integer> maximoTramo2=executor.submit(tarea2);

        // Esta comparación la realiza el hilo principal.
        int maximoTotal = 0;
        int maximo1=0, maximo2=0;
        try {
            maximo1=maximoTramo1.get();
            maximo2=maximoTramo2.get();
            maximoTotal = Math.max(maximo1, maximo2);
        }
        catch (InterruptedException e)
        {
            System.out.println("ERROR: El hilo principal ha sido interrumpido");
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException e)
        {
            System.out.println("ERROR: Algo ha sucedido en la ejecución de la tarea del hilo.");
            System.out.println(e.getMessage());
        }

        int indiceInicial=(LONGITUD_ARRAY/2)-1;

        System.out.println("Máximo de las posiciones 0 a " + indiceInicial + " : " + maximo1);

        System.out.println("Máximo de las posiciones " + (indiceInicial+1) + " a " + (LONGITUD_ARRAY-1) +" : " + maximo2);

        System.out.println("Máximo de todo el array: " + maximoTotal);

        executor.shutdown();

    }

    private static void inicializaArray(int [] numeros)
    {
        // Semilla fija: permite reproducir el ejemplo en clase.
        Random aleatorio = new Random();

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = aleatorio.nextInt(1000); // Entre 0 y 999.
        }

        System.out.println("Array: " + Arrays.toString(numeros));
    }

    public static class HiloMaximo implements Callable<Integer>
    {
        private int [] numeros;
        private int inicio, fin;
        private String nombre;

        public HiloMaximo(int [] numeros, int inicio, int fin, String nombre)
        {
            setNombre(nombre);
            setNumeros(numeros);
            setInicio(inicio);
            setFin(fin);
        }

        public void setNombre(String nombre)
        {
            if (nombre==null)
                throw new NullPointerException("ERROR: El nombre de la tarea no puede ser nulo.");

            if (nombre.isBlank())
                throw new IllegalArgumentException("ERROR: El nombre de la tarea no puede ser vacío.");

            this.nombre = nombre;
        }

        public void setNumeros(int[] numeros) {
            if (numeros==null)
                throw new NullPointerException("ERROR: El array de números no puede ser nulo.");

            if (numeros.length==0)
                throw new IllegalArgumentException("ERROR: El array de número no puede estar vacío.");

            this.numeros = numeros;
        }

        public void setInicio(int inicio) {
            if (inicio<0)
                throw new IllegalArgumentException("ERROR: El inicio del array no puede ser inferior a 0.");

            this.inicio = inicio;
        }

        public void setFin(int fin) {
            if (fin<0)
                throw new IllegalArgumentException("ERROR: El fin del array no puede ser inferior a 0.");

            if (fin>=numeros.length)
                throw new IllegalArgumentException("ERROR: El fin del array no puede ser mayor o igual a su longitud.");

            if (inicio>fin)
                throw new IllegalArgumentException("ERROR El inicio del array no puede ser superior al fin.");

            this.fin = fin;
        }

        @Override
        public Integer call() {

            int maximo=numeros[inicio];
            Thread.currentThread().setName(this.nombre);
            for(int i=inicio+1;i<fin;i++)
            {
                if(numeros[i]>maximo)
                    maximo=numeros[i];
            }

            return maximo;
        }
    }
}

