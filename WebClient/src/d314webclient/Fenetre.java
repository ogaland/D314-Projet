/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d314webclient;

/**
 *
 * @author KLO
 */
import javax.swing.JFrame;
 
public class Fenetre extends JFrame {
  public Fenetre(){
    this.setTitle("Fenêtre de contrôle");
    this.setSize(1000, 500);
    this.setAlwaysOnTop(true);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
    this.setVisible(true);
  }
}
