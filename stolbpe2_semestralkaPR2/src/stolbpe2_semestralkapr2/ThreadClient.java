/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
public class ThreadClient extends Thread implements Spojeni {

    InetAddress IP = null;
    InetAddress address;
    Socket s;
    OutputStream os;
    ObjectOutputStream oos;

    public ThreadClient(InetAddress adresa) {
        IP=adresa;
        Stolbpe2_semestralkaPR2.Zobraz(new Message(IP.getHostAddress()));
        this.start();

    }

    @Override
    public final void start() {
        try {
            s = new Socket(IP, 5678);
            os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            System.err.println("pripojuji se k "+IP.getHostAddress()+" a povedlo se mi to");
        } catch (IOException e) {
         System.err.println("pripojuji se k"+IP.getHostAddress()+"a nepovedlo se mi to");
        }

    }

    @Override
    public void Ukonci() {
        try {
            oos.writeObject(new Message("//QUIT"));
            oos.close();
            os.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void Odesli(String s) {
        Stolbpe2_semestralkaPR2.Zobraz(new Message(s, "Odesílám:"));
        try {
            oos.writeObject((Object) new Message(s));
        } catch (IOException ex) {
            System.err.println("nepovedlo se odeslat");
        }
    }

    @Override
    public InetAddress Adresa() {
        return IP;
    }

    @Override
    public boolean Stav() {
        try {
            return s.getInetAddress().isReachable(100);
        } catch (IOException ex) {
           return false;
        }
    }
}
