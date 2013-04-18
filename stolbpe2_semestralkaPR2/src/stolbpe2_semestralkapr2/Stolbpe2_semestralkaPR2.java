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
import javax.swing.JTextField;
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
        vertical.setValue(vertical.getMaximum()+1);
        vertical.setValue(vertical.getValue()+1);
    }

    static void Seznam() {
        seznam.setText(server.seznamSpojeni());
    }
    static JTextArea list = new JTextArea();
    static History History = new History();
    static JTextArea seznam = new JTextArea();
    static JScrollPane slist = new JScrollPane(list);
    static Server server = new Server();

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

    static void pridejClienta(InetAddress IP) {
        server.pridejClienta(IP);
    }

    //samotný program
    public Stolbpe2_semestralkaPR2() {

        final JTextArea zadavadlo = new JTextArea();
        zadavadlo.setEditable(true);
        JScrollPane szadavadlo = new JScrollPane(zadavadlo);
        szadavadlo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        szadavadlo.setPreferredSize(new Dimension(280, 90));
        final JTextField IP = new JTextField("127.000.000.001");
        IP.setPreferredSize(new Dimension(120, 20));
        final JTextField myIP = new JTextField("");
        myIP.setPreferredSize(new Dimension(100, 20));
        list.setLineWrap(true);
        slist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        slist.setPreferredSize(new Dimension(280, 300));
        seznam.setEditable(false);
        JScrollPane sseznam = new JScrollPane(seznam);
        sseznam.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sseznam.setPreferredSize(new Dimension(100, 300));
        myIP.setEditable(false);
        zadavadlo.setLineWrap(true);

        final JButton Odesli = new JButton("Send");
        JButton spojeni = new JButton("Connect");
        JButton kontakty = new JButton("Obnov");

        list.setEditable(false);


        JPanel spoj = new JPanel();
        JPanel zadavani = new JPanel();
        JPanel seznamy = new JPanel();
        seznamy.add(slist);
        seznamy.add(sseznam);
        Odesli.setPreferredSize(new Dimension(100, 70));
        zadavani.add(szadavadlo);
        zadavani.add(Odesli);
        spoj.add(IP);
        myIP.setText("my IP: " + server.GetMyIP());
        spoj.add(myIP);
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


        //obsluha kontaktů       
        kontakty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seznam.setText(server.seznamSpojeni());
                server.PredejSpojeni();
                myIP.setText("moje IP: " + server.GetMyIP());

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
