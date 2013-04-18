/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
public class ThreadClient extends Thread {

    InetAddress IP = null;
    Socket s;
    OutputStream os;
    ObjectOutputStream oos;
    boolean funguje=true;

    public ThreadClient(InetAddress adresa) {
        IP=adresa;
        //Stolbpe2_semestralkaPR2.Zobraz(new Message(IP.getHostAddress()));
        this.start();

    }

    @Override
    public final void start() {
        try {
            s = new Socket(IP, 5678);
            os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            funguje=true;
           // System.err.println("pripojuji se k "+IP.getHostAddress()+" a povedlo se mi to");
        } catch (IOException e) {
         System.err.println("pripojuji se k"+IP.getHostAddress()+"a nepovedlo se mi to");
        funguje=false;
        }

    }

    public void Ukonci() {
        try {
            oos.writeObject(new Message("//QUIT"));
            oos.close();
            os.close();
            s.close();
            funguje=false;
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public synchronized void Odesli(String s) {
        try {
            oos.writeObject((Object) new Message(s));
        } catch (IOException | NullPointerException e) {
            System.err.println("nepovedlo se odeslat");
            funguje=false;
        }
    }

    
 
    public synchronized void Odesli(InetAddress a) {
        Stolbpe2_semestralkaPR2.Zobraz(new Message(a.getHostAddress(), "Odesílám adresu "));
        try {
            oos.writeObject((Object) new Message(a));
        } catch (IOException | NullPointerException e) {
            System.err.println("nepovedlo se odeslat");
            funguje=false;
        }
    } 
     

    public InetAddress Adresa() {
        return IP;
    }


    public boolean Stav() {
       
            return funguje;
        
    }
}
