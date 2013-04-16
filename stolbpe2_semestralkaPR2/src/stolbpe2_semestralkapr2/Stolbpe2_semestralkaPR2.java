/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Punk
 */
public class Stolbpe2_semestralkaPR2 extends JFrame {

    static void Zobraz(Message m) {
        list.append("\n" + m.odesilatel + ":  " + m.obsah);
        JScrollBar vertical = slist.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    static void Seznam(String s) {
        seznam.setText(s);
    }
    static JTextArea list = new JTextArea();
    static History History = new History();
    static JTextArea seznam = new JTextArea();
    static JScrollPane slist = new JScrollPane(list);

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException unused) {
            System.out.println("neni problem");
        }
        Stolbpe2_semestralkaPR2 window = new Stolbpe2_semestralkaPR2();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setSize(400, 500);
    }

    //samotný program
    public Stolbpe2_semestralkaPR2() {

        final JTextArea zadavadlo = new JTextArea();
        zadavadlo.setEditable(true);
        JScrollPane szadavadlo = new JScrollPane(zadavadlo);
        szadavadlo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        szadavadlo.setPreferredSize(new Dimension(280, 90));
        final JTextArea IP = new JTextArea("127.0.0.1");
        IP.setPreferredSize(new Dimension(200, 15));
        list.setLineWrap(true);
         slist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        slist.setPreferredSize(new Dimension(280, 300));
        seznam.setPreferredSize(new Dimension(100, 300));
        seznam.setEditable(false);
        zadavadlo.setLineWrap(true);
        
        JButton Odesli = new JButton("Send");
        JButton spojeni = new JButton("Connect");
        JButton kontakty = new JButton("ObnovSeznam");

        list.setEditable(false);


        JPanel spoj = new JPanel();
        JPanel zadavani = new JPanel();
        JPanel seznamy = new JPanel();
        seznamy.add(slist);
        seznamy.add(seznam);
        Odesli.setPreferredSize(new Dimension(100, 70));
        zadavani.add(szadavadlo);
        zadavani.add(Odesli);
        spoj.add(IP);
        spoj.add(spojeni);
        spoj.add(kontakty);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, WIDTH));
        panel.add(spoj);
        panel.add(seznamy);
        panel.add(zadavani);




        this.setContentPane(panel);
        this.pack();
        this.setTitle("Messenger Stolbpe2");
        this.setResizable(false);
        this.setLocationRelativeTo(null);


        final Server server = new Server();
        server.execute();






        //obsluha kontaktů       
        kontakty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seznam.setText(server.seznamSpojeni());
            }
        });
        //obsluha spojení        
        spojeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.pridejClienta(InetAddress.getByName(IP.getText()));
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Stolbpe2_semestralkaPR2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //spuštění směrovacího serveru       
        kontakty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
//obsluha odesílání
        Odesli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //list.append("\n" + zadavadlo.getText());

                server.Odesli(zadavadlo.getText());
                zadavadlo.setText(null);
            }
        });
    }
}
