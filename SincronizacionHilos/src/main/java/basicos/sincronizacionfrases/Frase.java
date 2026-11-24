package basicos.sincronizacionfrases;

public class Frase {

    public static synchronized void imprimirFrases(String frase1, String frase2)
    {

        System.out.print(frase1);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println("Hilo de la Frase interrumpido.");
            Thread.currentThread().interrupt();
        }
        System.out.println(frase2);

    }
}
