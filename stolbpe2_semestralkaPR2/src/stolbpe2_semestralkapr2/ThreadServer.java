/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
class ThreadServer extends Thread implements Spojeni{

    private Socket socket;
    private InputStream is;
    private ObjectInputStream ois;
    private String chyba;
    Message prectena = new Message("Chyba");

    public ThreadServer(Socket s){
        socket=s;
        try {
            is= s.getInputStream();
            ois = new ObjectInputStream(is);
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
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
                   Stolbpe2_semestralkaPR2.Zobraz(new Message(prectena.obsah,socket.getInetAddress().toString()));
                }else{
                   Ukonci();
                   break;
                }
                    
                
           
        }
    
}

    @Override
    public void Ukonci(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void Odesli(String s){
        throw new UnsupportedOperationException("Jsem Server");
    }
    
    @Override
    public InetAddress Adresa(){
    return socket.getInetAddress();
    }

    @Override
    public boolean Stav() {
        return socket.isConnected();
    }
        }
