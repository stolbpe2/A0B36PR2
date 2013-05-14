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

//klientské vlákno
public class ThreadClient extends Thread {

    InetAddress IP = null;
    int intSocket;
    Socket s;
    OutputStream os;
    ObjectOutputStream oos;
    boolean funguje=true;

    
    //konstruktor klientského vlákna
    public ThreadClient(InetAddress adresa,int intSocket) {
        IP=adresa;
        this.intSocket=intSocket;
        try {
            s = new Socket(IP, intSocket);
        } catch (IOException ex) {
            Stolbpe2_semestralkaPR2.Zobraz(new Message("chyba, na socketu:"+intSocket+" již jedna instance programu na tomoto počítači  běží"));
        }
    }

    @Override
    public final void start() {
        try {

            os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
                    System.err.println("jsem nový klient, s IP: "+IP+" a na socketu: "+s.getPort());
            funguje=true;
           System.err.println("pripojuji se k "+IP.getHostAddress()+" a povedlo se mi to");
        } catch (IOException e) {
         System.err.println("pripojuji se k"+IP.getHostAddress()+"a nepovedlo se mi to");
        funguje=false;
        }

    }
//ukončení vlákna
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

//odesílání zpráv
    public synchronized void Odesli(String s) {
        try {
            oos.writeObject((Object) new Message(s));
        } catch (IOException | NullPointerException e) {
            Stolbpe2_semestralkaPR2.Zobraz(new Message("nepodarilo se mi odeslat"+ s+" na "+ IP.getHostAddress(),"PROGRAM"));
            funguje=false;
        }
    }
 
    public synchronized void Odesli(InetAddress a,int conSocket) {
        Stolbpe2_semestralkaPR2.Zobraz(new Message(a.getHostAddress(), "Odesílám adresu s portem"+conSocket));
        try {
            oos.writeObject((Object) new Message(a,conSocket));
        } catch (IOException | NullPointerException e) {
            System.err.println("nepovedlo se odeslat");
            funguje=false;
        }
    } 
    
   //gettery 
    public InetAddress Adresa() {
        return IP;
    }

    public boolean Stav() {
            return funguje;
        
    }
}
