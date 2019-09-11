/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mx.sat.cfd33.Comprobante;

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
