/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;
/**
 *
 * @author Punk
 */
public class Stolbpe2_semestralkaPR2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //ClientGUI gui = new ClientGUI();
        //gui.setVisible(true);
        
        while(true){
            try {
                Client klient= new Client();
            } catch (Exception ex) {
               System.out.println("chyba");
            }
        Server server= new Server();
    }
}
}
