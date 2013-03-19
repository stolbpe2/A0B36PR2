/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Punk
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(5678);
        List docasny = new ArrayList();
        List spojeni = new ArrayList();
        System.out.println("Server pripraven");
        try {
            while (true) {
                for (int i = 0; i < spojeni.size(); i++) {
                    if (spojeni.get(i) == false) {
                        docasny.add(spojeni.get(i));
                    }
                    spojeni.removeAll(docasny);
                }
                Socket socket = s.accept();
                try {
                    spojeni.add(new ServeConnection(socket));
                } catch (IOException e) {
                    System.err.println("ServeConnection IO Exception");
                }

            }
        } finally {
            s.close();
        }
    }
}

class ServeConnection extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String chyba;
    boolean active = false;

    public ServeConnection(Socket s) throws IOException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    public boolean IsActive() {
        return active;
    }

    @Override
    public void run() {
        try {
            System.out.println("//Obsluhuji");

            while (true) {
                Message prectena = new Message("Chyba");
                try {
                    Object precteny = in.readObject();
                    prectena = (Message) precteny;
                } catch (ClassNotFoundException ex) {
                    System.out.println("nelze precist");
                }
                System.out.println(prectena.obsah + "   odesilatel: " + prectena.odesilatel);
                if (prectena.obsah.equals("//QUIT")) {
                    System.out.println("//Konec");

                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("!!IO Exception");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("!!Socket se neuzavrel");
            }
        }
    }
}
