package basicos;

public class MetodosEstaticosThreads {
    public static void main(String[] args) {

        System.out.println("¡Hola mundo!");
        Thread miHiloPrincipal = Thread.currentThread();
        //obtiene el hilo donde se está ejecutando este método mediante la
        //función Thread.currentThread(), y lo almacena en la variable
        //local miHilo

        try
        {
            System.out.println("El nombre del hilo en ejecución es: " + miHiloPrincipal.getName());
            //El hilo se esperará durante 5 segundos antes de ejecutar las sentencias posteriores
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            System.out.println("El hilo " +  miHiloPrincipal.getName()
                    + " ha sido interrumpido estando en estado de waiting, sleeping o mientras se ejecutaba.");
            System.out.println(e.getMessage());
        }


        //imprime el nombre del hilo en la Salida (función getName())
        System.out.println("Por defecto, el hilo que ejecuta el método main() "
                +"de mi programa se llama " + miHiloPrincipal.getName());

        miHiloPrincipal.setName("Soy el Hilo DAM!");

        System.out.println("El nuevo nombre del hilo principal una vez usado el método setName es: " + miHiloPrincipal.getName());




    }
}
