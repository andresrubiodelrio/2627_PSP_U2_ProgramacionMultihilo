package basicos.lambda;

import java.time.Duration;

public class Payment {

    private int idTransaction;

    public Payment(int idTransaction) {
        setIdTransaction(idTransaction);
    }

    public void pagar(String numCuenta,double cantidadAPagar) {

        System.out.println("Iniciando el pago de la transacción " + idTransaction);

        //... Sentencias correspondientes a la realización del pago

        System.out.println("-------");

        try {
            Thread.sleep(Duration.ofSeconds(2));
        }
        catch (InterruptedException e)
        {
            System.out.println("ERROR: La tarea asignada al hilo ha sido interrumpida.");
        }

        System.out.println("Finalizando el pago de la transacción " + idTransaction);
    }

    private void setIdTransaction(int idTransaction) {
        if (idTransaction <= 0)
            throw new IllegalArgumentException("ERROR: El idTransaction no puede ser negativo ni cero.");

        this.idTransaction = idTransaction;
    }
}
