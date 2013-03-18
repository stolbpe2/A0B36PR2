/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Punk
 */
public class Server { 
  public static void main(String[] args) throws IOException {
    int cnt = 0;
    ServerSocket s = new ServerSocket(5678);
    System.out.println("Server pripraven");
    try {
      while(true) {
        Socket socket = s.accept();
        try {
          new ServeConnection(socket);
        } catch(IOException e) {
          System.err.println("IO Exception");
        }
        if (cnt++ > 4)
          break;
      }
    } finally {
      s.close();
    }
  }
}

class ServeConnection extends Thread {
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  public ServeConnection(Socket s) throws IOException {
    socket = s;
    in = new BufferedReader (new InputStreamReader( socket.getInputStream()));
    out = new PrintWriter( new OutputStreamWriter( socket.getOutputStream()), true);
    start();
  }
  @Override
  public void run() {
    try {
      System.out.println("//Obsluhuji");
      while (true) { 
        String str = in.readLine();
        System.out.println(str);
        if (str.equals("//QUIT")) {
          System.out.println("//Konec");
          break;
        }
        out.println(str);
      }
    } catch(IOException e) {
      System.err.println("!!IO Exception");
    } finally {
      try {
        socket.close();
      } catch(IOException e) {
        System.err.println("!!Socket se neuzavrel");
      }
    }
  }
}
