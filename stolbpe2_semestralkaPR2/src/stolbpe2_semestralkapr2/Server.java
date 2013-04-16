/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Punk
 */
public class Server extends SwingWorker {

    static List<TwoDSpojeni> polespojeni = new ArrayList();

    public Server() {
    }

    public void pridejClienta(InetAddress IP) {
        polespojeni.add(new TwoDSpojeni(IP));
        //this.UdrzSpojeni();
        Stolbpe2_semestralkaPR2.Seznam(this.seznamSpojeni());
    }

    public String seznamSpojeni() {
        //this.UdrzSpojeni();
        String temp, a = "";
        for (int i = 0; i < polespojeni.size(); i++) {
            temp = polespojeni.get(i).Adresa().getHostAddress();
            a = a + "\n" + temp;
        }
        return a;
    }

    public void UdrzSpojeni() {
        List<TwoDSpojeni> temp = new ArrayList();
        for (int i = 0; i < polespojeni.size(); i++) {
            try {
                if (polespojeni.get(i).Stav()) {
                    temp.add(polespojeni.get(i));
                }
            } catch (NullPointerException e) {
            }
            polespojeni = temp;
        }
    }

    public void Odesli(String zprava) {
        for (int i = 0; i < polespojeni.size(); i++) {
            try {
                polespojeni.get(i).Odesli(zprava);
            } catch (Error e) {
            }
        }
    }

    @Override
    protected Object doInBackground() {
        Socket socket = null;
        ThreadServer docasnespojeni = null;
        while (true) {
            try {
                ServerSocket s = new ServerSocket(5678);
                System.err.println("prijimam");
                docasnespojeni = new ThreadServer(s.accept());
                System.err.println("prijmul sem spojeni"+docasnespojeni.Adresa().getHostAddress());
            } catch (IOException ex) {
                System.err.println("nepodedlo se prijmout spojeni");
            }

            boolean vlozeno = false;
            for (int i = 0; i <= polespojeni.size(); i++) {
                if (polespojeni.get(i).Adresa().equals(docasnespojeni.Adresa())) {
                    polespojeni.get(i).priradServer(docasnespojeni);
                    vlozeno = true;
                }
                if (!vlozeno) {
                    TwoDSpojeni a = new TwoDSpojeni(docasnespojeni);
                    polespojeni.add(a);
                }
            }




        }
    }
}
