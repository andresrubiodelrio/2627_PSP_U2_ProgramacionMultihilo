package productoresconsumidores.libros;

public class LectorLibro implements Runnable {
    private Libro libro;

    public LectorLibro(Libro libro) {

        setLibro(libro);
    }

    public void setLibro(Libro libro) {
        if (libro==null)
            throw new NullPointerException("ERROR: Un libro no puede ser nulo.");

        this.libro = libro;
    }

    @Override
    public void run() {
        synchronized (libro) {
            System.out.println(Thread.currentThread().getName()+" está esperando a que el libro "+libro.getTitulo() + " esté completo") ;
            try {
                libro.wait();
            } catch (InterruptedException e) {
                System.out.println("ERROR: El hilo lector " + Thread.currentThread().getName() + " ha sido interrumpido.");
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName()+": El libro ya ha sido completado. Ahora sí puedes leerlo.");
            System.out.println(Thread.currentThread().getName() + ": Genial. Me lo llevo y me pongo a leerlo");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("ERROR: El hilo lector ha sido interrumpido.");
                System.out.println(e.getMessage());
                Thread.currentThread().interrupt();
            }

            System.out.println(Thread.currentThread().getName()+ ". Devuelvo el libro. Ya he terminado de leerlo.");
            libro.notify();
        }
    }
}

