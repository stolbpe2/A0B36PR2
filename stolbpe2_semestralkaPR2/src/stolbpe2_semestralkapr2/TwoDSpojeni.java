/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.net.InetAddress;

//třída spojující serverovou a klientskou část každého proudu spojení
public class TwoDSpojeni extends Thread implements Comparable {

    ThreadServer sthread = null;
    ThreadClient cthread;
    InetAddress adresa;

    //několik konstruktorů spojení pro různé typy vzniku spojení
    public TwoDSpojeni(ThreadServer s) {
        this.sthread = s;
        System.err.println("TwoDSpojeni: mám server thread, jdu spustit klienta");
        adresa = this.sthread.Adresa();
        this.cthread = new ThreadClient(adresa, sthread.getSocket());
        this.start();


    }

    public TwoDSpojeni(InetAddress IP, int conSocket) {
        adresa = IP;
        System.err.println("jdu ziskat založit klienta s portem"+conSocket);
        this.cthread = new ThreadClient(IP, conSocket);
        this.start();
    }

    public TwoDSpojeni(ThreadClient s) {
        this.cthread = s;
        adresa = cthread.Adresa();
        s.Odesli("chci se pripojit");
        this.start();

    }
//přiřazení serverové části k již existující klientské části
    public void priradServer(ThreadServer s) {
        this.sthread = s;
    }
//několik typů odesílání, standardní zpráva a zpráva pro přenos IP adresy
    public void Odesli(String s) {
        cthread.Odesli(s);
    }

    public void Odesli(InetAddress a, int conSocket) {
        String moje = Main.GetMyIP().toString();

        if ((!a.getHostAddress().equals("/127.0.0.1")) & !(a.getHostAddress().equals(moje))) {
            cthread.Odesli(a, conSocket);

        } else {
        }
    }

    public void Ukonci() {
        sthread.Ukonci();
        cthread.Ukonci();
    }
//gettery
    public InetAddress Adresa() {
        return adresa;
    }

    public int getSocket() {
        return sthread.getSocket();
    }

    public boolean Stav() {
        return cthread.Stav();
    }


    @Override
    public void run() {
        this.cthread.start();
        System.err.println("spusteny klientska i serverova vlakna");
    }
//komparátor pro seřazení kontaktů před zobrazením.
    @Override
    public int compareTo(Object o) {
        TwoDSpojeni druhe = (TwoDSpojeni) o;
        return druhe.Adresa().getAddress().toString().compareTo(this.Adresa().getAddress().toString());
    }
}
