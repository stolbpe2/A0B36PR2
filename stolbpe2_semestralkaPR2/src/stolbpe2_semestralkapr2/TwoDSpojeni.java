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
public class TwoDSpojeni extends Thread{
ThreadServer sthread=null;
ThreadClient cthread;
InetAddress adresa;
public TwoDSpojeni(ThreadServer s){
this.sthread=s;
System.err.println("TwoDSpojeni: mám server thread, jdu spustit klienta");
adresa=this.sthread.Adresa();
this.cthread=new ThreadClient(adresa);
this.start();


}

public TwoDSpojeni(InetAddress IP){
adresa=IP;
this.cthread= new ThreadClient(IP);
this.start();
}

public TwoDSpojeni(ThreadClient s){
this.cthread=s;
adresa=cthread.Adresa();
s.Odesli("chci se pripojit");
this.start();
        
}

public void priradServer(ThreadServer s){
this.sthread=s;
//this.sthread.start();
}


    public void Odesli(String s) {
    cthread.Odesli(s);    
    }

    public void Odesli(InetAddress a) {
        String moje = Server.GetMyIP().toString() ;

    if (
            (!a.getHostAddress().equals("/127.0.0.1"))&!(a.getHostAddress().equals(moje)))  
            
            {cthread.Odesli(a);
     
    }else{}
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

    @Override
    public void start() {
this.cthread.start();
System.err.println("spusteny klientska i serverova vlakna");
    }
}
