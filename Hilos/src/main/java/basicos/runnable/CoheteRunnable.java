package basicos.runnable;

public  class CoheteRunnable {

    public static void main(String[] args) {

//        Despegue cohete1=new Despegue(1,10);
//        Thread hilo1=new Thread(cohete1)
//        Despegue cohete2=new Despegue(2,10);
//        Thread hilo2=new Thread(cohete2);

        Thread hilo1=new Thread(new Despegue(1,10));
        Thread hilo2=new Thread(new Despegue(2,10));

        //hilo1.run();
        //hilo2.run();

        hilo1.start();
        hilo2.start();
        System.out.println("Comienza la cuenta atrás!");
    }

    public static class Despegue implements Runnable {
        private int cuentaAtras;
        private int id;

        public Despegue() {}

        public Despegue(int id, int cuentaAtras) {
            setCuentaAtras(cuentaAtras);
            setId(id);
        }

        private void setId(int id) {
            if (id<0)
                throw new IllegalArgumentException("ERROR: El id no puede ser negativo.");

            this.id = id;
        }

        private void setCuentaAtras(int cuentaAtras) {
            if (cuentaAtras<=0)
                throw new IllegalArgumentException("ERROR: La cuenta atrás no puede ser negativo ni comenzar en cero.");

            this.cuentaAtras = cuentaAtras;
        }

        @Override
        public void run() {

            while (cuentaAtras > 0) {
                System.out.println("#" + id + " (" + cuentaAtras + ")" );
                cuentaAtras--;

            }

//            System.out.println("Soy el hilo: "+ Thread.currentThread().getName());
            System.out.println("Lanzamiento (" + id + ")");
        }


    }
}
