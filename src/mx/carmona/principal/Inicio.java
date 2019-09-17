/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import javax.swing.JFrame;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Edwin Carmona
 */
public class Inicio {
    public static void main(String[] args) throws JAXBException {
        
        ReaderWindow wind = new ReaderWindow();
        wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wind.setLocationRelativeTo(null);
        wind.pack();
        wind.setVisible(true);
        
    }
}
