/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlock.NO;

/**
 *
 * @author IMCG
 */
public class Hilo2 extends Thread {

  //Declara dos objetos del tipos de cada fichero
  FicheroANO a;
  FicheroBNO b;

  /****************************************************************************
   * constructor
   * 
   * @param a: ficheroA
   * @param b: ficheroB
   */
  Hilo2(FicheroANO a, FicheroBNO b) {
    this.a = a;
    this.b = b;
  }

  /******************************************************************************
   * Tarea del hilo: acceder al ficheroB
   */
  @Override
  public void run() {

    b.metodoB(a);
    //el hilo accede al ficheroB.
  }
}
