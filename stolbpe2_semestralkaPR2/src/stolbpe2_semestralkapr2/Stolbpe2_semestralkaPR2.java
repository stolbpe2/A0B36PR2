/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        zadavadlo.setEditable(true);
        JButton Odesli = new JButton("Odesli");
        JButton spojeni = new JButton("Spojeni");
        final JTextArea list = new JTextArea();
                list.setMinimumSize(new Dimension(100,200));
        list.setEditable(false);
        
//obsluha spojení        
        spojeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.append("\n spojeno");
                //server.ObnovSpojeni();
            }
        });
//obsluha odesílání
        Odesli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.append("\n"+zadavadlo.getText());
                
                //server.Posli("zadavadlo.getText());
                zadavadlo.setText(null);
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, WIDTH));
        panel.add(list);
        panel.add(Odesli);
        panel.add(zadavadlo);
        panel.add(spojeni);




        this.setContentPane(panel);
        this.pack();
        this.setTitle("Messenger Stolbpe2");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
