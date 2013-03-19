/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Punk
 */
public class Client {
public static void main(String args[]){  
try{
InetAddress address = InetAddress.getByName(null);
Socket s = new Socket(address,5678); 
OutputStream os = s.getOutputStream();  
ObjectOutputStream oos = new ObjectOutputStream(os); 
Scanner sc=new Scanner(System.in);
String temp="";
do{
temp=sc.next(); 
oos.writeObject(new Message(temp,"petak"));
}while((!temp.equals("//QUIT")));
oos.writeObject(new Message("//QUIT"));
oos.close();  
os.close();  
s.close();  
}catch(Exception e){System.out.println(e);}  
}  
}     
