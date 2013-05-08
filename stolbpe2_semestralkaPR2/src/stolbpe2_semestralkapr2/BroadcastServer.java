/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
public final class BroadcastServer extends Thread {

    ServerSocket s;

    public BroadcastServer() {
        try {
            s = new ServerSocket(5677);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
    }

    public void Odesli(String zprava) {
    }

    @Override
    public void start() {
        Socket socket = null;
        while (true) {
            //try {
                //s.;
                
                Server.Pridej(s.getInetAddress());
                System.err.println("přijat broadcast z "+s.getInetAddress().toString());

            //} catch (IOException ex) {}
        }
    }
}
