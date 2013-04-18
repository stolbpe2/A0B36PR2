/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Punk
 */
public final class Server extends SwingWorker {

    static List<TwoDSpojeni> polespojeni = new ArrayList();
    ServerSocket s;

    public void pridejClienta(InetAddress adresa) {
        Pridej a = new Pridej(adresa);
    }

    public Server() {
        try {
            s = new ServerSocket(5678);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.execute();
    }

    public void PredejSpojeni() {
        List<TwoDSpojeni> bezi;
        for (int i = 0; i < polespojeni.size(); i++) {
            bezi = polespojeni.subList(0, i);
            bezi.addAll(polespojeni.subList(i + 1, polespojeni.size()));
            for (int j = 0; i < bezi.size(); i++) {
                polespojeni.get(i).Odesli(bezi.get(j).Adresa());

                System.err.println("predavam" + bezi.get(j).Adresa().toString());
            }
        }

    }

    public String seznamSpojeni() {
        this.UdrzSpojeni();
        String temp, a = "";
        for (int i = 0; i < polespojeni.size(); i++) {
            temp = polespojeni.get(i).Adresa().getHostAddress();
            a = a + "\n" + temp;
        }
        return a;
    }

    public static void UdrzSpojeni() {
//        L
        for (int i = 0; i < polespojeni.size(); i++) {
            try {
                if (!polespojeni.get(i).Stav()) {
                    polespojeni.remove(i);
                    i--;
                }
            } catch (NullPointerException e) {
            }
        }

    }

    public void Odesli(String zprava) {
        Stolbpe2_semestralkaPR2.Zobraz(new Message(zprava, "Já:  "));
        for (int i = 0; i < polespojeni.size(); i++) {
            try {
                polespojeni.get(i).Odesli(zprava);
            } catch (Error e) {
            }
        }
    }

    public String GetMyIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException ex) {
            return "0.0.0.0";
        }
    }

    public static class Pridej implements Runnable {

        InetAddress adresa = null;

        public Pridej(InetAddress IP) {
            adresa = IP;
            this.run();
        }

        @Override
        public final void run() {
            boolean obsahuje = false;
            for (int i = 0; i < polespojeni.size(); i++) {
                if (polespojeni.get(i).Adresa().getAddress().equals(adresa.getAddress())) {
                    obsahuje = true;
                }
            }
            if (!obsahuje) {
                polespojeni.add(new TwoDSpojeni(adresa));
            }
            Stolbpe2_semestralkaPR2.Seznam();

        }
    }

    @Override
    protected Object doInBackground() {
        Socket socket = null;
        ThreadServer docasnespojeni = null;
        System.err.println("doinbackground");
        while (true) {
            try {

                System.err.println("akceptuji spojení");
                docasnespojeni = new ThreadServer(s.accept());
                System.err.println("povedlo se prijmout spojeni");
            } catch (IOException ex) {
                System.err.println("nepovedlo se prijmout spojeni");
            }
            System.err.println("jdu vkladat");
            boolean vlozeno = false;
            for (int i = 0; i <= polespojeni.size(); i++) {
                if (polespojeni.get(i).Adresa().equals(docasnespojeni.Adresa())) {
                    polespojeni.get(i).priradServer(docasnespojeni);
                    System.err.println("Prirazeno ke klientovi");
                    vlozeno = true;
                }
                if (!vlozeno) {
                    TwoDSpojeni a = new TwoDSpojeni(docasnespojeni);
                    polespojeni.add(a);
                    System.err.println("Vytvoreno nove TwoDSpojeni");
                }
            }
        }
    }
}
