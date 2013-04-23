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
    static List<InetAddress> zakadresy = new ArrayList();
    ServerSocket s;

    
    
    
    

    public void pridejClienta(InetAddress adresa) {
        Pridej a = new Pridej(adresa);
    }

    public Server() {
        try {
            //zakadresy.add(InetAddress.getByName("127.0.0.1"));
            zakadresy.add(InetAddress.getByName("255.255.255.255"));
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            s = new ServerSocket(5678);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.execute();
    }

    public static void PredejSpojeni() {
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

//        for (int i = 0; i < polespojeni.size(); i++) {
//            try {
//                if (!polespojeni.get(i).Stav()) {
//                    polespojeni.remove(i);
//                    i--;
//                }
//            } catch (NullPointerException e) {
//            }
//        }

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

    public static String GetMyIP() {
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
            if(!zakadresy.contains(adresa)){
            for (int i = 0; i < polespojeni.size(); i++) {
                System.err.println("porovnavam:"+polespojeni.get(i).Adresa().getHostAddress() +" a " +adresa.getHostAddress());
                if (polespojeni.get(i).Adresa().getHostAddress().equals(adresa.getHostAddress())) {
                    obsahuje = true;
                }
            }
            if (!obsahuje) {
                polespojeni.add(new TwoDSpojeni(adresa));
            }
            }
            Stolbpe2_semestralkaPR2.Seznam();

        }
    }

    @Override
    protected Object doInBackground() {
        Socket socket = null;
        ThreadServer docasnespojeni = null;
        while (true) {
            try {


                polespojeni.add(new TwoDSpojeni( new ThreadServer(s.accept()),true));
                System.err.println("vkládaám nové spojení");

            } catch (IOException ex) {

            }

            boolean vlozeno = false;
//            for (int i = 0; i <= polespojeni.size(); i++) {
//                if (polespojeni.get(i).Adresa().equals(docasnespojeni.Adresa())) {
//                    polespojeni.get(i).priradServer(docasnespojeni);
//
//                    vlozeno = true;
//                }
//                if (!vlozeno) {
//            System.err.println("vkládaám nové spojení");
//                    TwoDSpojeni a = new TwoDSpojeni(docasnespojeni);
//                    polespojeni.add(a);

//                }
//            }
        }
    }
}
