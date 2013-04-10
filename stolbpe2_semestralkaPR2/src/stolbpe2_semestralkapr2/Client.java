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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
public class Client extends Thread implements Spojeni {

    String IP = "";
    InetAddress address;
    Socket s;
    OutputStream os;
    ObjectOutputStream oos;
    InputStream ois;
    ObjectInputStream oois;

    public Client(String adresa) {
        this.IP = adresa;
        this.run();

    }

    @Override
    public void run() {
        try {
            address = InetAddress.getByName(IP);
            s = new Socket(address, 5678);
            os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            ois = s.getInputStream();
            oois = new ObjectInputStream(ois);
            oos.writeObject(new Message("spojeno", "petak"));
        }catch(IOException e){
        }
        
    }
    @Override
    public void Ukonci(){
        try {
            oos.writeObject(new Message("//QUIT"));
                 oos.close();
                 os.close();
                 s.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
} 
    @Override
    public void Odesli(String s){
        try {   
            oos.writeObject(new Message(s));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
