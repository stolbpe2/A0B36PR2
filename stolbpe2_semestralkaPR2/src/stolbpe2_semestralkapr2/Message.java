/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.Serializable;
import java.net.InetAddress;

//třída zprávy, pro standardizovanou práci s veškerými zprávami
public class Message implements Serializable {
String obsah;
String odesilatel;
InetAddress IP=null;
int socket;

//vytváření jednoduché zprávy se speciálním kódem odesílatele
public Message(String m,String o){
this.obsah=m;
this.odesilatel=o;

}
//odesílání programové zprávy
public Message(String m){
this.obsah=m;
this.odesilatel="program";
//druh=Typ.values()[2];
}
//zpráva pro předávání reference spojení
public Message(InetAddress a,int ConSocket){
this.obsah=a.getHostAddress();
this.odesilatel="intIP";
IP=a;
socket = ConSocket;
}
}
