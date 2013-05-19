/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

/**
 *
 * @author Punk
 */
public final class DistributeMyConnections extends Thread {
//jednoduchá třída, pro opakované odesílání seznamu spojení

    public DistributeMyConnections() {
this.start();
    }

   
    @Override
    public void run() {
        while (true) {
           Main.PredejSpojeni();
           Stolbpe2_semestralkaPR2.Seznam();
            try {
                Thread.sleep(10000);
                
            } catch (InterruptedException ex) {
               System.err.println("Distribute: automaticky predavam driv");
            }
        }
    }
}
