/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
class ServeConnection extends Thread implements Spojeni {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String chyba;
    boolean active = false;
    Message prectena = new Message("Chyba");

    public ServeConnection(Socket s) throws IOException {
        socket=s;
        in = new ObjectInputStream(s.getInputStream());
        out = new ObjectOutputStream(s.getOutputStream());
        start();
    }

    public boolean IsActive() {
        return active;
    }

    @Override
    public void run() {
        try {
           while (true) {
                
                try {
                    Object precteny = in.readObject();
                    prectena = (Message) precteny;
                } catch (ClassNotFoundException ex) {
                    System.out.println("nelze precist");
                }
                if (!prectena.obsah.equals("//QUIT")) {
                   //zadej do listu
                }else{
                   Ukonci();
                   break;
                }
                    
                }
            }
         catch (IOException e) {
            System.err.println("!!IO Exception");
        
        }
    
}

    @Override
    public void Ukonci(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServeConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void Odesli(String s){
        try {
            out.writeObject(new Message(s));
        } catch (IOException ex) {
            Logger.getLogger(ServeConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        }
