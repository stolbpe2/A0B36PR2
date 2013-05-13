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

//třída pro hlavní práci se spojeními, spravující existující spojení a obsluhující přidávání nových spojení.
//zpracovává:1)spojení zadaná přes GUI, 2)spojení příchozí jako žádost o spojení, 3)příchozí reference a zkouší zda je reference platná
//4)předávání vlastních referencí dále do sítě
public final class Main extends SwingWorker {

    static List<TwoDSpojeni> polespojeni = Collections.synchronizedList(new ArrayList());
    static List<InetAddress> zakadresy = Collections.synchronizedList(new ArrayList());
    static InetAddress local = null;
    ServerSocket s;
    DistributeMyConnections distribute = new DistributeMyConnections();

    //vrací socket na kterém naní naslouchám
    public int getSocket() {
    return s.getLocalPort();
    }
   
//externí volání přidání nového klienta 
    public void Pridej(InetAddress inet,int intsocket) {
        Pridej temp = new Pridej(inet,intsocket);
    }

//vytvoření nové instance, na začátku se vytvoří seznam zakázaných adres
    public Main(int sock) throws IllegalArgumentException, IOException {
        
        try {
            local = InetAddress.getByName("127.0.0.1");

            //zakadresy.add(InetAddress.getByName("127.0.0.1"));
            zakadresy.add(InetAddress.getByName("255.255.255.255"));
            zakadresy.add(InetAddress.getLocalHost());

        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

            s = new ServerSocket(sock);
            
        
    }

    
    //spuštění main, pro spouštění pomocí GUI (a práci na pozadí) je vytvořen jako vlákno ve SwingWorkeru
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
                Pridej klienta = new Pridej(docasny.Adresa(),this.getSocket());
                Pridej prirad = new Pridej(docasny);
            } catch (IOException ex) {
            System.out.println("spadl server!");
            }
        }
    }

    //funkce předávající  současná spojení ostatním klientům v síti, pro aktualitu celé sítě
    public static void PredejSpojeni() {
        Main.UdrzSpojeni();
        List<TwoDSpojeni> bezi;

        for (int i = 0; i < polespojeni.size()-1; i++) {
            bezi = polespojeni.subList(0, i);
            bezi.addAll(polespojeni.subList(i + 1, polespojeni.size()));
            for (int j = 0; i < bezi.size()-1; i++) {
                if (!zakadresy.contains(polespojeni.get(i).Adresa())) {
                    polespojeni.get(i).Odesli(bezi.get(j).Adresa(),bezi.get(j).getSocket());

                    System.err.println("predavam" + bezi.get(j).Adresa().toString());
                }

            }
        }

    }
//funkce vracející seznam všech spojení, používaná pro výpis do GUI
    public String seznamSpojeni() {
        Main.UdrzSpojeni();
        Collections.sort(polespojeni);
        String temp, a = "";
        System.err.println("SeznamSpojeni vypisuji seznamspojeni");
        for (int i = 0; i < (polespojeni.size()); i++) {
            temp = polespojeni.get(i).Adresa().getHostAddress();
            a = a + "\n" + temp;
        }
        return a;
    }
//funkce procházející celý seznam a ověřuje zda jsou spojení aktivní, případně je vymaže
    public static void UdrzSpojeni() {

        for (int i = 0; i < polespojeni.size(); i++) {

            if (!polespojeni.get(i).Stav()) {
                System.err.println("mazu spojeni-stav" + polespojeni.get(i).adresa.getHostAddress());
                polespojeni.remove(i);
                i--;

            }
        }
        
//zde je zakomentováno mazání localhost adresy, aby bylo možno program testovat na jednom počítači        
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

    
//funkce pro odesílání zpráv
    public void Odesli(String zprava) {
        Stolbpe2_semestralkaPR2.Zobraz(new Message(zprava, "Já:  "));
        for (int i = 0; i < polespojeni.size(); i++) {
            try {
                polespojeni.get(i).Odesli(zprava);
            } catch (Error e) {
            }
        }
    }

    
   //získání vlastní IP Adresy
    public static String GetMyIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException ex) {
            return "0.0.0.0";
        }
    }

    void changePort(int port) throws IOException {
     s = new ServerSocket(port);   
    }

   //vnitřní třída Serveru, realizující přidávání do arraylistu spojení
    public class Pridej extends Thread{

        InetAddress adresa = null;
        int intsocket=5678;
        boolean obsahuje;
        boolean jenserver;
        ThreadServer server;

        public Pridej(InetAddress IP,int intSocket) {
            adresa = IP;
            this.intsocket=intSocket;
            obsahuje = false;
            jenserver = false;
            this.start();
        }

        public Pridej() {
            obsahuje = true;
            jenserver = false;
            this.start();

        }

        public Pridej(ThreadServer server) {
            this.obsahuje = false;
            this.jenserver = true;
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
                        polespojeni.add(new TwoDSpojeni(adresa,intsocket));
                        
                        System.err.println("Pridej: pridavam spojeni"+adresa+"  "+intsocket);
                        Main.PredejSpojeni();
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
                    System.err.println("Pridej: pridavam spojeni-server uz mam"+server.Adresa());
                    Main.PredejSpojeni();
                }
            }
            Stolbpe2_semestralkaPR2.Seznam();
        }
    }
}
