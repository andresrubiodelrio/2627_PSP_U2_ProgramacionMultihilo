package basicos.lambda;

public class SimulacionCoheteExpresionLambda {

    public static void main(String[] args) {
        final int CUENTA_ATRAS=10;

        Cohete cohete1=new Cohete(1);
        Cohete cohete2=new Cohete(2);

        Thread despegueCohete1=new Thread(()->cohete1.despegue(CUENTA_ATRAS));
        Thread despegueCohete2=new Thread(()->cohete2.despegue(CUENTA_ATRAS));

        despegueCohete1.start();
        despegueCohete2.start();
        System.out.println("Comienza la cuenta atrás!");

    }
}
