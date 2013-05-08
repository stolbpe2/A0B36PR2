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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Punk
 */
public final class Server extends SwingWorker {

    static  List<TwoDSpojeni> polespojeni = Collections.synchronizedList(new ArrayList());
    static  List<InetAddress> zakadresy = Collections.synchronizedList(new ArrayList());
    static InetAddress local=null;
       

    static void Pridej(InetAddress inet) {
        Pridej temp = new Pridej(inet);
    }
    ServerSocket s;

    public Server() {
         try {
            local = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    @Override
    protected Object doInBackground() {
        Socket socket = null;
        ThreadServer docasnespojeni = null;
        while (true) {
            try {

                System.err.println("akceptuji nové spojení");
                Socket sock = s.accept();
                ThreadServer docasny = new ThreadServer(sock);
                docasny.start();
                System.err.println("mám dočasné spojení ");
                Pridej klienta = new Pridej(docasny.Adresa());
                Pridej prirad=new Pridej(docasny);
                klienta.start();
                prirad.start();

            } catch (IOException ex) {
            }
        }
    }

    public static void PredejSpojeni() {
        Server.UdrzSpojeni();
        List<TwoDSpojeni> bezi;
        
        for (int i = 0; i < polespojeni.size(); i++) {
            bezi = polespojeni.subList(0, i);
            bezi.addAll(polespojeni.subList(i + 1, polespojeni.size()));
            for (int j = 0; i < bezi.size(); i++) {
                if(!polespojeni.get(i).Adresa().equals(local)){
                polespojeni.get(i).Odesli(bezi.get(j).Adresa());

                System.err.println("predavam" + bezi.get(j).Adresa().toString());
            }
                
            }
        }

    }

    public String seznamSpojeni() {
        Server.UdrzSpojeni();
        String temp, a = "";
        System.err.println("SeznamSpojeni vypisuji seznamspojeni");
        for (int i = 0; i < (polespojeni.size()); i++) {
            temp = polespojeni.get(i).Adresa().getHostAddress();
            System.err.println(temp);
            a = a + "\n" + temp;
        }
        return a;
    }

    public static void UdrzSpojeni() {

        for (int i = 0; i < polespojeni.size(); i++) {
            
                if (!polespojeni.get(i).Stav()) {
                    System.err.println("mazu spojeni-stav" + polespojeni.get(i).adresa.getHostAddress());
                    polespojeni.remove(i);
                    i--;

                }
        }
//        boolean smazano=false;
//        for (int i = 0; i < polespojeni.size(); i++) {
//                 if ((polespojeni.get(i).Adresa().equals(local))&!smazano) {
//                 System.err.println("mazu spojeni-localhost" +polespojeni.get(i).adresa.getHostAddress());
//                    polespojeni.remove(i);
//                    smazano=true;
//                    i--;
//                 }
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

    public static class Pridej extends Thread {

        InetAddress adresa = null;
        boolean obsahuje;
        boolean jenserver;
        ThreadServer server;

        public Pridej(InetAddress IP) {
            adresa = IP;
            obsahuje = false;
            jenserver = false;
            this.start();
        }

        public Pridej() {
            obsahuje = true;
            this.start();
            jenserver = false;
        }

        public Pridej(ThreadServer server) {
            obsahuje = false;
            jenserver = true;
            this.server = server;
            this.start();
        }

        @Override
        public final void run() {
 if (!jenserver) {
            if ((!zakadresy.contains(adresa)) & (!obsahuje)) {
               
                    for (int i = 0; i < (polespojeni.size()); i++) {
                        System.err.println("Pridej: porovnavam:" + polespojeni.get(i).Adresa().getHostAddress() + " a " + adresa.getHostAddress());
                        if (polespojeni.get(i).Adresa().getHostAddress().equals(adresa.getHostAddress())) {
                            obsahuje = true;
                        }
                    }
                    if (!obsahuje) {
                        polespojeni.add(new TwoDSpojeni(adresa));
                        System.err.println("Pridej: pridavam spojeni");
                        Server.PredejSpojeni();
                    }
                }
            } else {
                for (int i = 0; i < (polespojeni.size()); i++) {
                    System.err.println("Pridej-jenserver: porovnavam:" + polespojeni.get(i).Adresa().getHostAddress() + " a " + server.Adresa().getHostAddress());
                    if (polespojeni.get(i).Adresa().equals(server.Adresa())) {
                        polespojeni.get(i).priradServer(server);
                    }
                }
                if (obsahuje) {
                    polespojeni.add(new TwoDSpojeni(server));
                    System.err.println("Pridej: pridavam spojeni-server uz mam");
                    Server.PredejSpojeni();
                }
            }


            Stolbpe2_semestralkaPR2.Seznam();


        }
    }
}
