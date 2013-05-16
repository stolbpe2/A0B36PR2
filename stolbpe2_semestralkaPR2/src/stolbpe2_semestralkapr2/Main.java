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

    static List<ThreadClient> poleclient =new ArrayList();
    static List<ThreadServer> poleserver = new ArrayList();
    static List<InetAddress> zakadresy = Collections.synchronizedList(new ArrayList());
    static InetAddress local = null;
    static ServerSocket s;
    DistributeMyConnections distribute;

    //vrací socket na kterém naní naslouchám
    static public int getSocket() {
        return s.getLocalPort();
    }

//externí volání přidání nového klienta 
    public void Pridej(InetAddress adresa, int intsocket) {
        boolean obsahuje=false;
        for (int i = 0; i < (poleclient.size()); i++) {
                        //System.err.println("!Pridej: porovnavam:" + poleclient.get(i).Adresa().getHostAddress()  +poleclient.get(i).getSocket() + " a " + adresa.getHostAddress() + intsocket);
                        if ((poleclient.get(i).Adresa().equals(adresa)) & (poleclient.get(i).getsocket() == intsocket)) {
                            obsahuje=true;
                        }
                    }
                    if (!obsahuje) {
                        poleclient.add(new ThreadClient(adresa, intsocket));
                        //Stolbpe2_semestralkaPR2.Zobraz(new Message("!Pridej: pridavam spojeni" + adresa + "  " + intsocket));
                        Main.PredejSpojeni();
                        Stolbpe2_semestralkaPR2.Seznam();
                    //}        
        }
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
        while (true) {
            try {

                System.err.println("akceptuji nové spojení");
                    distribute = new DistributeMyConnections();
                ThreadServer docasny = new ThreadServer(s.accept());
                
                if(!poleserver.contains(docasny)){
                    poleserver.add(docasny);
                }
            } catch (IOException ex) {
                System.out.println("spadl server!");
            }
        }
    }

    //funkce předávající  současná spojení ostatním klientům v síti, pro aktualitu celé sítě
    public static void PredejSpojeni() {
        Main.UdrzSpojeni();
        int size=poleclient.size();
        System.out.println(size);
        if(size>0){
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                InetAddress adresa=poleclient.get(j).Adresa();
                int socket=poleclient.get(j).getsocket();
                    poleclient.get(i).Odesli(adresa,socket);
                    System.err.println("predavam" +adresa+" "+socket);               
         poleclient.get(i).Odesli(GetMyIP(true), getSocket());       
        }
            
            }
        }

    }
//funkce vracející seznam všech spojení, používaná pro výpis do GUI

    public String seznamSpojeni() {
        Main.UdrzSpojeni();
        String temp, a = "";
        //System.err.println("SeznamSpojeni vypisuji seznamspojeni o velikosti: "+poleclient.size());
        for (int i = 0; i < (poleclient.size()); i++) {
            temp = poleclient.get(i).Adresa().getHostAddress() + " " + poleclient.get(i).getsocket();
            a = a + "\n" + temp;
        }
        return a;
    }
//funkce procházející celý seznam a ověřuje zda jsou spojení aktivní, případně je vymaže

    public static void UdrzSpojeni() {

        for (int i = 0; i < poleclient.size(); i++) {
            if (!poleclient.get(i).Stav()) {
                System.err.println("mazu spojeni-stav" + poleclient.get(i).Adresa().getHostAddress());
                poleclient.remove(i);
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
        for (int i = 0; i < poleclient.size(); i++) {
            try {
                System.err.println("odesílám klientovi");
                poleclient.get(i).Odesli(zprava);
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

    //získání vlastní IP Adresy
    public static InetAddress GetMyIP(boolean a) {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    void changePort(int port) throws IOException {
        s = new ServerSocket(port);
    }

   
    }
