package basicos.sincronizacionmultiple;

public class HiloSegmentoSincronizado {
    private int contador1=0;
    private int contador2=0;

    private Object block1=new Object();
    private Object block2=new Object();

    public void incrementar1()
    {
        synchronized (block1)
        {
            contador1++;
        }
    }

    public void incrementar2()
    {
        synchronized (block2)
        {
            contador2++;
        }
    }

    public int getContador1() {
        return contador1;
    }

    public int getContador2() {
        return contador2;
    }
}
