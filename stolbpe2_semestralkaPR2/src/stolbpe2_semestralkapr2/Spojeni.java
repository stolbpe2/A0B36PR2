/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stolbpe2_semestralkapr2;

import java.net.InetAddress;

/**
 *
 * @author Punk
 */
public interface Spojeni{
  public void Odesli(String s);
  public void Ukonci();
  public InetAddress Adresa();
  public boolean Stav();
    
}
