/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Punk
 */
public class Message implements Serializable {
public enum Typ {Error, Message, Info};
Typ druh;
String obsah;
String odesilatel;
InetAddress IP=null;

public Message(String m,String o){
this.obsah=m;
this.odesilatel=o;
//druh=Typ.values()[2];
}

public Message(String m){
this.obsah=m;
this.odesilatel="program";
//druh=Typ.values()[2];
}

public Message(InetAddress a){
this.obsah=a.getHostAddress();
this.odesilatel="intIP";
IP=a;
}
}
