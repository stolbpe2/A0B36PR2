/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

    private static boolean spustenserver = false;

    static void Zobraz(Message m) {
        list.append(m.odesilatel + ":  " + m.obsah + "\n");
        JScrollBar vertical = slist.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    static void Seznam() {
        seznam.setText(server.seznamSpojeni());
    }
    static final JTextArea list = new JTextArea();
    static History History = new History();
    static JTextArea seznam = new JTextArea();
    static JScrollPane slist = new JScrollPane(list);
    static Server server = null;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException unused) {
            System.out.println("neni problem");
        }
        Stolbpe2_semestralkaPR2 window = new Stolbpe2_semestralkaPR2();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setSize(100, 100);
        //server.execute();
    }

    static void pridejClienta(InetAddress IP, int intsocket) {
        server.Pridej(IP, intsocket);
    }

    //samotný program
    public Stolbpe2_semestralkaPR2() {


        final JTextArea zadavadlo = new JTextArea();
        zadavadlo.setEditable(true);
        JScrollPane szadavadlo = new JScrollPane(zadavadlo);
        szadavadlo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        final JTextField IP = new JTextField("127.0.0.1");
        IP.setPreferredSize(new Dimension(95, 20));
        final JTextField spojport = new JTextField("5678");
        spojport.setPreferredSize(new Dimension(40, 20));
        final JTextField myIP = new JTextField("");
        myIP.setPreferredSize(new Dimension(95, 20));
        list.setLineWrap(true);
        slist.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        seznam.setEditable(false);
        JScrollPane sseznam = new JScrollPane(seznam);
        sseznam.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sseznam.setPreferredSize(new Dimension(110, 90));
        boolean spustenserever = false;

        myIP.setEditable(false);
        zadavadlo.setLineWrap(true);

        final JButton Odesli = new JButton("    SEND!    ");
        Odesli.setSize(20, 90);
        JButton spojeni = new JButton("Connect");
        JButton kontakty = new JButton("Obnov");
        JLabel majIP = new JLabel("moje IP:");
        myIP.setText(Server.GetMyIP());
        list.setEditable(false);
        this.pack();
        this.setTitle("Messenger stolbpe2");
        this.setMinimumSize(new Dimension(400, 230));
        this.setLocationRelativeTo(null);



        JPanel spoj = new JPanel();
        spoj.add(IP);
        spoj.add(spojport);
        spoj.add(majIP);
        spoj.add(myIP);
        spoj.add(spojeni);
        final JPanel panel = new JPanel();
        this.setContentPane(panel);
        panel.setLayout(new GridBagLayout());



        GridBagConstraints a = new GridBagConstraints();
        a.fill = GridBagConstraints.HORIZONTAL;
        a.weightx = 100;
        a.gridx = 0;
        a.gridy = 1;
        a.gridwidth = 4;
        panel.add(spoj, a);

        GridBagConstraints b = new GridBagConstraints();
        b.fill = GridBagConstraints.BOTH;
        b.gridx = 0;
        b.gridy = 2;
        b.gridwidth = 3;
        b.gridheight = 1;
        b.weightx = 30000;
        b.weighty = 5000;
        panel.add(slist, b);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 100;
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 30000;
        c.weighty = 3000;
        panel.add(sseznam, c);

        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.BOTH;
        d.weightx = 100;
        d.gridx = 0;
        d.gridy = 3;
        d.gridwidth = 1;
        d.gridheight = 1;
        d.weightx = 3;
        d.weighty = 3;
        panel.add(szadavadlo, d);

        GridBagConstraints e = new GridBagConstraints();
        e.fill = GridBagConstraints.VERTICAL;
        e.weightx = 100;
        e.gridx = 3;
        e.gridy = 3;
        e.gridwidth = 1;
        e.gridheight = 1;
        e.weightx = 3;
        e.weighty = 3;
        panel.add(Odesli, e);









        //obsluha kontaktů       
        kontakty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seznam.setText(server.seznamSpojeni());
                panel.updateUI();
                Server.PredejSpojeni();
                myIP.setText(Server.GetMyIP());

            }
        });
        //obsluha spojení        
        spojeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t = IP.getText();
                try {
                    if (spustenserver) {
                        if(Server.getSocket()==Integer.parseInt(spojport.getText())){
                        Server.Pridej(InetAddress.getByName(t), Integer.parseInt(spojport.getText()));
                        }else{
                        server=new Server(Integer.parseInt(spojport.getText()));
                        server.execute();
                        Stolbpe2_semestralkaPR2.Zobraz(new Message("spouštím server na portu: " + spojport.getText()));
                        Server.Pridej(InetAddress.getByName(t), Integer.parseInt(spojport.getText()));
                        }
                    } else {
                        Stolbpe2_semestralkaPR2.Zobraz(new Message("spouštím server na portu: " + spojport.getText()));
                        server = new Server(Integer.parseInt(spojport.getText()));
                        server.execute();
                        spustenserver = true;
                        Server.Pridej(InetAddress.getByName(t), Integer.parseInt(spojport.getText()));
                    }
                
                } catch (IOException ex) {
                    Stolbpe2_semestralkaPR2.Zobraz(new Message("nepovedlo se připojit k IP " + t, " možná špatný socket?"));
                }
            }
        });

//obsluha odesílání
        Odesli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.Odesli(zadavadlo.getText());
                zadavadlo.setText(null);
            }
        });

        zadavadlo.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    if (e.getModifiers() == 0) {
                        String text = zadavadlo.getText();
                        if (text.length() > 0) {
                            text = text.substring(0, text.length() - 1);
                        }
                        server.Odesli(text);
                        zadavadlo.setText(null);
                    } else {
                        zadavadlo.append("\n");
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }
}