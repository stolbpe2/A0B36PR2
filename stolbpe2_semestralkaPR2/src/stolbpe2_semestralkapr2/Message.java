/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

/**
 *
 * @author Punk
 */
public class Message {
public enum Typ {Error, Message, Info};
Typ druh;
String obsah;
String odesilatel;

public Message(String m,String o){
this.obsah=m;
this.odesilatel=o;
druh=Typ.values()[2];
}
}
