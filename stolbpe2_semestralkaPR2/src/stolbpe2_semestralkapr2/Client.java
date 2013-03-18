/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Punk
 */
public class Client {
 public static void main(String[] args) throws Exception {
    InetAddress address = InetAddress.getByName(null);
    Socket socket = new Socket();
    try {
    socket = new Socket(address, 5678);
          //BufferedReader in = new BufferedReader (new InputStreamReader( socket.getInputStream()));
        Scanner in=new Scanner(System.in);
      PrintWriter out = new PrintWriter( new OutputStreamWriter( socket.getOutputStream()), true);
      for(int i = 0; i < 10; i ++) {
        out.println(i);
       Thread.sleep(200);
      }
      out.println("//QUIT");
    } finally {
      socket.close();
    }
  }
}
