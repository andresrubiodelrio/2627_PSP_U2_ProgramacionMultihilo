/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlock.NO;

/**
 *
 * @author IMCG
 */
public class Hilo1 extends Thread {
 //Declara dos objetos del tipos de cada fichero
  FicheroANO a;
  FicheroBNO b;

  /*****************************************************************************
  constructor
   *
   * @param a: ficheroA
   * @param b: ficheroB
   */
  Hilo1(FicheroANO a, FicheroBNO b) {
    this.a = a;
    this.b = b;
  }
/*******************************************************************************
 * Tarea del hilo: acceder al ficheroA
 */
  @Override
  public void run() {
    a.metodoA(b);
    //el hilo accede al ficheroA
  }
}
