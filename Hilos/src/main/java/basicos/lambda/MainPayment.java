package basicos.lambda;

public class MainPayment {

    public static void main(String[] args) {

        for (int i = 1; i < 20; i++) {

            Payment paymentThread = new Payment(i);

            Thread hilo = new Thread(() -> paymentThread.pagar("30280023122976342122",50.5));

            hilo.start();
        }


    }

}