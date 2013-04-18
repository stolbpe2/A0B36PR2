/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
class ThreadServer extends Thread{

    private Socket socket;
    private InputStream is;
    private ObjectInputStream ois;
    private String chyba;
    Message prectena = new Message("Chyba");

    public ThreadServer(Socket s){
        //System.err.println("jsem nove serverove vlakno");
        socket=s;
        try {
            is= s.getInputStream();
            ois = new ObjectInputStream(is);
            //System.err.println("server: povedlo se otevřít komunikaci");
        } catch (IOException ex) {
            System.err.println("nepovedlo se otevřít komunikaci");
        }
        start();
    }
    
    public ThreadServer(InetAddress so){
        try {
            socket = new Socket(so,5678);
            is= socket.getInputStream();
            ois = new ObjectInputStream(socket.getInputStream());
            start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean IsActive() {
        return (socket.isConnected());
        
    }

    @Override
    public synchronized void start() {
       
           while (true) {
                
                Object precteny = null;               
            try {
                precteny = ois.readObject();
            } catch (    IOException | ClassNotFoundException ex) {
                Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                   
                    prectena = (Message) precteny;             
                if (!prectena.obsah.equals("//QUIT")) {
                    if(prectena.odesilatel.equals("intIP")){
                        
                    }else{
                   Stolbpe2_semestralkaPR2.Zobraz(new Message(prectena.obsah,socket.getInetAddress().toString()));
                    }
                    }else{
                   Ukonci();
                   break;
                }
                    
                
           
        }
    
}

    public void Ukonci(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void Odesli(String s){
        throw new UnsupportedOperationException("Jsem Server");
    }

    public InetAddress Adresa(){
    return socket.getInetAddress();
    }


    public boolean Stav() {
        return socket.isConnected();
    }

    public void Odesli(InetAddress a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        }
