package productoresconsumidores.libros;

public class Main {
    public static void main(String args[])
    {
        Libro libro=new Libro("El Alquimista");
        LectorLibro lectorJuan=new LectorLibro(libro);
        LectorLibro lectorAna=new LectorLibro(libro);

        Thread juanThread=new Thread(lectorJuan,"Juan");
        Thread anaThread=new Thread(lectorAna,"Ana");

        anaThread.start();
        juanThread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("ERROR: El hilo principal ha sido interrumpido.");
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }

        EscritorLibro escritor=new EscritorLibro(libro);
        Thread escritorThread=new Thread(escritor);
        escritorThread.start();
    }
}
