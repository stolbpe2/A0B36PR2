/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Punk
 */
public class Stolbpe2_semestralkaPR2 extends JFrame {

    /**
     * @param args the command line arguments
     */
    static History History = new History();

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
        final JTextArea IP = new JTextArea("192.168.0.100");
        final JTextArea list = new JTextArea();
        IP.setPreferredSize(new Dimension(100,15));
        //zadavadlo.setPreferredSize(new Dimension(600,600));
        list.setPreferredSize(new Dimension(400,400));
        //zadavadlo.setEditable(true);
        JButton Odesli = new JButton("Send");
        JButton spojeni = new JButton("Connect");
        JButton serverEnable = new JButton("I`m Server");
        JButton OK = new JButton("OK");
        
        list.setMinimumSize(new Dimension(100, 200));
        list.setEditable(false);


        JPanel spoj = new JPanel();
        JPanel zadavani = new JPanel();
        zadavadlo.setPreferredSize(new Dimension(300, 200));
        Odesli.setPreferredSize(new Dimension(70, 90));
        zadavani.add(zadavadlo);
        zadavani.add(Odesli);
        spoj.add(IP);
        spoj.add(OK);
        spoj.add(serverEnable);
        spoj.add(spojeni);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, WIDTH));
        panel.add(spoj);
        panel.add(list);
        panel.add(zadavani);




        this.setContentPane(panel);
        this.pack();
        this.setTitle("Messenger Stolbpe2");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        
              Server server=new Server();

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
         //nastavení směrovacího serveru      
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        //obsluha spojení        
        spojeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.append("\n spojeno");
                //server.ObnovSpojeni();
            }
        });
        //spuštění směrovacího serveru       
        serverEnable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
//obsluha odesílání
        Odesli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.append("\n" + zadavadlo.getText());

                //server.Send(zadavadlo.getText());

                zadavadlo.setText(null);
            }
        });
        

    }
    
}
