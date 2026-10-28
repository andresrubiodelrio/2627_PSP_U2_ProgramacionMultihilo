package basicos.herencia;

import java.util.Arrays;
import java.util.Random;

public class MaximoArray {

    public static final int LONGITUD_ARRAY=50;

    public static void main(String[] args) {

        int [] numeros=new int[LONGITUD_ARRAY];

        inicializaArray(numeros);
        HiloMaximo hilo1 = new HiloMaximo(numeros,0,(LONGITUD_ARRAY/2)-1,"Hilo-1");
        HiloMaximo hilo2 = new HiloMaximo(numeros,LONGITUD_ARRAY/2,LONGITUD_ARRAY-1,"Hilo-2");

        hilo1.start();
        hilo2.start();

        try {
            // El principal espera a que terminen los dos.
            hilo1.join();
            hilo2.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            System.err.println(
                    "Se interrumpió la espera del hilo principal."
            );

            return;
        }

        // Ambos trabajadores han terminado.
        int maximo1 = hilo1.getMaximo();
        int maximo2 = hilo2.getMaximo();

        // Esta comparación la realiza el hilo principal.
        int maximoTotal = Math.max(maximo1, maximo2);
        int indiceInicial=(LONGITUD_ARRAY/2)-1;

        System.out.println(
                "Máximo de las posiciones 0 a " + indiceInicial + " : " + maximo1
        );

        System.out.println(
                "Máximo de las posiciones " + (indiceInicial+1) + " a " + (LONGITUD_ARRAY-1) +" : " + maximo2
        );

        System.out.println(
                "Máximo de todo el array: " + maximoTotal
        );

    }

    private static void inicializaArray(int [] numeros)
    {
        // Semilla fija: permite reproducir el ejemplo en clase.
        Random aleatorio = new Random(42);

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = aleatorio.nextInt(1000); // Entre 0 y 999.
        }

        System.out.println("Array: " + Arrays.toString(numeros));
    }

    public static class HiloMaximo extends Thread
    {
        private int [] numeros;
        private int inicio, fin;
        private int maximo;
        private String nombre;

        public HiloMaximo(int [] numeros, int inicio, int fin, String nombre)
        {
            super(nombre);
            setNumeros(numeros);
            setInicio(inicio);
            setFin(fin);
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

        public int getMaximo()
        {
            return this.maximo;
        }

        @Override
        public void run() {

            maximo=numeros[inicio];

            for(int i=inicio+1;i<fin;i++)
            {
                if(numeros[i]>maximo)
                    maximo=numeros[i];
            }

        }
    }
}

