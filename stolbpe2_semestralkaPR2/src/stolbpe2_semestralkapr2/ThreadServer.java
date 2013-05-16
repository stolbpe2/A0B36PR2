/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//serverové vlákno
class ThreadServer extends Thread {

    private Socket socket;
    private InputStream is;
    private ObjectInputStream ois;
    private String chyba;
    private InetAddress adresa;
    Message prectena = new Message("chyba","chyba");
    boolean funkcni = true;

    //konstruktor vlákna
    public ThreadServer(Socket s) {
        System.err.println("jsem nove serverove vlakno");
        this.socket = s;
        try {
            this.is = this.socket.getInputStream();
            this.ois = new ObjectInputStream(is);
        } catch (IOException ex) {
            System.err.println("nepovedlo se otevřít komunikaci");
        }
        this.start();

    }

    @Override
    public void run() {

        while (true) {
            prectena = new Message("chyba","chyba");
            Object precteny = null;
            try {            
                precteny = ois.readObject();
                prectena = (Message) precteny;
            } catch (ClassCastException | IOException | ClassNotFoundException ex) {
                
            }    
            try {
if (!prectena.odesilatel.equals("chyba")) {
                if (prectena.odesilatel.equals("IP")) {
             Stolbpe2_semestralkaPR2.pridejClienta(prectena.IP, prectena.socket);
                //System.err.println("mam prichozi zpravu-IP"+prectena.IP.getHostAddress()+"  "+prectena.socket);
                }else{
                Stolbpe2_semestralkaPR2.Zobraz(new Message(prectena.obsah, socket.getInetAddress().getHostAddress() + " " + socket.getPort()));
                //System.err.println("mam prichozi zpravu-text");
                }
}else{throw new NullPointerException();}
                } catch (NullPointerException e) {
                //System.err.println("spojeni uzavreno");
                Stolbpe2_semestralkaPR2.Seznam();
                Ukonci();
            }
        
        }

    }
//ukončení vlákna

    public void Ukonci() {

        funkcni = false;

    }
//ošetření neodesílání

    public void Odesli(String s) {
        throw new UnsupportedOperationException("Jsem Server");
    }

    public void Odesli(InetAddress a) {
        throw new UnsupportedOperationException("Jsem Server");
    }
    //gettery

    public final InetAddress Adresa() {
        return socket.getInetAddress();
    }

    int getSocket() {
        return socket.getPort();
    }

    boolean Stav() {
        return socket.isClosed();
    }
}
