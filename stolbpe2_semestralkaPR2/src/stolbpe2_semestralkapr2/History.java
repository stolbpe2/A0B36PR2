/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.util.ArrayList;

/**
 *
 * @author Punk
 */
public class History {
    ArrayList<Message> seznam = new  ArrayList<>();
    public History(){
        
    }
    
    public void add(Message x){
    seznam.add(x);    
    }
    
@Override
public String toString(){
    String text="";
    for(int i=0;i<seznam.size();i++){
    text=text+seznam.get(i).odesilatel+":  "+seznam.get(i).obsah+"\n";    
    }
    return text;
}
}
