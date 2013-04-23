/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Punk
 */
public class TwoDSpojeni{
ThreadServer sthread;
ThreadClient cthread;
InetAddress adresa;
public TwoDSpojeni(ThreadServer s,boolean server){
this.sthread=s;
Stolbpe2_semestralkaPR2.Zobraz(new Message("zakladam serverove vlakno, spoustim klientske"));
this.cthread= new ThreadClient(s.Adresa()); 
adresa=s.Adresa();
this.cthread.start();
}

public TwoDSpojeni(InetAddress IP){
adresa=IP;
this.cthread= new ThreadClient(IP);
}

public TwoDSpojeni(ThreadClient s){
this.cthread=s;
adresa=cthread.Adresa();
s.Odesli("chci se pripojit");
        
}

public void priradServer(ThreadServer s){
this.sthread=s;

}


    public void Odesli(String s) {
    cthread.Odesli(s);    
    }

    public void Odesli(InetAddress a) {
        String moje = Server.GetMyIP().toString() ;

    if (
            (a.getHostAddress().equals("127.0.0.1"))&(a.getHostAddress().equals(moje)))  
            
            {
     
    }else{cthread.Odesli(a);}
    }

    
    public void Ukonci() {
        sthread.Ukonci();
        cthread.Ukonci();
    }


    public InetAddress Adresa() {
        return adresa;
    }


    public boolean Stav() {
       return cthread.Stav();
    }

    void PriradClient(InetAddress adresa) {
        cthread=new ThreadClient(adresa);
    }
}
