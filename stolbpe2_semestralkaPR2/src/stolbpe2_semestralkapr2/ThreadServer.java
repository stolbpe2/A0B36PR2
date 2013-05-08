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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
class ThreadServer extends Thread {

    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;
    private String chyba;
    private InetAddress adresa;
    Message prectena = new Message("Chyba");

    public ThreadServer(Socket s) {
        System.err.println("jsem nove serverove vlakno");
        try {
            is = s.getInputStream();
            os = s.getOutputStream();
            ois = new ObjectInputStream(is);
            adresa = s.getInetAddress();
            System.err.println("threadserver: povedlo se otevřít komunikaci");
        } catch (IOException ex) {
            System.err.println("nepovedlo se otevřít komunikaci");
        }
    }

    public boolean IsActive() {
        return (socket.isConnected());

    }

    @Override
    public void run() {

        System.err.println("jsem spuštěné serverové vlákno");
        while (true) {

            Object precteny = null;
            try {
                precteny = ois.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(adresa.getHostAddress().toString() + "  ThreadServer: spojení uzavreno");
            }

            prectena = (Message) precteny;
            try {
                if (!prectena.obsah.equals("//QUIT")) {
                    if (prectena.odesilatel.equals("intIP")) {
                        Stolbpe2_semestralkaPR2.pridejClienta(prectena.IP);
                    } else {
                        Stolbpe2_semestralkaPR2.Zobraz(new Message(prectena.obsah, adresa.toString()));
                    }
                } else {
                    Ukonci();
                    break;
                }
            } catch (NullPointerException e) {
                System.err.println("spojeni uzavreno");
                Ukonci();
            }

        }

    }

    public void Ukonci() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Odesli(String s) {
        throw new UnsupportedOperationException("Jsem Server");
    }

    public final InetAddress Adresa() {
        return adresa;
    }

    public boolean Stav() {
        return socket.isConnected();
    }

    public void Odesli(InetAddress a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
