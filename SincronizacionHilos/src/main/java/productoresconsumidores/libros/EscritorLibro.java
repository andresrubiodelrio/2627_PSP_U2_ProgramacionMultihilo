package productoresconsumidores.libros;


public class EscritorLibro implements Runnable {
    Libro libro;

    public EscritorLibro(Libro libro) {
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
            System.out.println("El autor está empezando a escribir el libro: " +libro.getTitulo() );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("ERROR: El hilo escritor ha sido interrumpido");
                System.out.println(e.getMessage());
            }

            libro.setEstaCompletado(true);
            System.out.println("El libro acaba de terminar de escribirse.");

            libro.notify();
            System.out.println("notify a un lector");
        }
    }
}
