/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.net.InetAddress;

//třída spojující serverovou a klientskou část každého proudu spojení
public class TwoDSpojeni implements Comparable {

    ThreadServer sthread = null;
    ThreadClient cthread;
    InetAddress adresa;

    //několik konstruktorů spojení pro různé typy vzniku spojení
    public TwoDSpojeni(ThreadServer s) {
        this.sthread = s;
        System.err.println("TwoDSpojeni: mám server thread");
        adresa = this.sthread.Adresa();
        this.cthread = null;


    }

    public TwoDSpojeni(InetAddress IP, int conSocket) {
        adresa = IP;
        System.err.println("jdu ziskat založit klienta s portem"+conSocket);
        this.cthread = new ThreadClient(IP, conSocket);

    }

    public TwoDSpojeni(ThreadClient s) {
        this.cthread = s;
        this.cthread.start();
        adresa = cthread.Adresa();
        s.Odesli("chci se pripojit");

    }
//přiřazení serverové části k již existující klientské části
    public void priradServer(ThreadServer s) {
        this.sthread = s;
        System.err.println("TwoDS:prirazuji server");
    }
    
  public void priradKlienta(ThreadClient s) {
        this.cthread = s;
        System.err.println("TwoDS:prirazuji klienta");
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
        try{
        return sthread.getsocket();
        }catch(NullPointerException e){
            return cthread.getsocket();
    }
    }

    public boolean Stav() {
//        if (sthread.Stav()){
//        return sthread.Stav();}else{
//            return cthread.Stav();
//        }
        return true;
    }


//komparátor pro seřazení kontaktů před zobrazením.
    @Override
    public int compareTo(Object o) {
        TwoDSpojeni druhe = (TwoDSpojeni) o;
        return druhe.Adresa().getAddress().toString().compareTo(this.Adresa().getAddress().toString());
    }

  
}
