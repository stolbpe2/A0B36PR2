/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.net.InetAddress;

/**
 *
 * @author Punk
 */
public class TwoDSpojeni implements Spojeni {
ThreadServer sthread;
ThreadClient cthread;
InetAddress adresa;
public TwoDSpojeni(ThreadServer s){
this.sthread=s;
Stolbpe2_semestralkaPR2.Zobraz(new Message("zakladam serverove vlakno, spoustim klientske"));
this.cthread= new ThreadClient(s.Adresa()); 
adresa=s.Adresa();
}

public TwoDSpojeni(InetAddress IP){
adresa=IP;
this.cthread= new ThreadClient(IP);
}

public TwoDSpojeni(ThreadClient s){
this.cthread=s;
adresa=s.Adresa();
s.Odesli("chci se pripojit");
        
}

public void priradServer(ThreadServer s){
this.sthread=s;
Stolbpe2_semestralkaPR2.Zobraz(new Message("Přiřazuji server"));

}

    @Override
    public void Odesli(String s) {
    cthread.Odesli(s);    
    }

    @Override
    public void Ukonci() {
        sthread.Ukonci();
        cthread.Ukonci();
    }

    @Override
    public InetAddress Adresa() {
        return adresa;
    }

    @Override
    public boolean Stav() {
       return cthread.Stav();
    }
}
