/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Punk
 */
public class Server {

    static List spojeni = new ArrayList(10);

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(5678);
        try {
            while (true) {
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

// Obsluha spojení, vlákna
