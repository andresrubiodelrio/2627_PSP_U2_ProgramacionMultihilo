package basicos.lambda;

public class Cohete {
    private int id;

    public Cohete(int id) {
        setId(id);
    }

    private void setId(int id) {
        if (id<0)
            throw new IllegalArgumentException("ERROR: El id no puede ser negativo.");

        this.id = id;
    }

    public void despegue(int cuentaAtras) {
        while (cuentaAtras > 0) {
            System.out.println("#" + id + " (" + cuentaAtras + ")" );
            cuentaAtras--;
        }

        System.out.println("Lanzamiento (" + id + ")");
    }


}
