/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Edwin Carmona
 */
public class SCsvFileManager1 {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "Clave,Item,Unidad,Necesidad,Stock,BackOrder";

    public static String writeCsvFile(ArrayList<String> lAtributos) {
        String sResult = "";
        FileWriter fileWriter = null;
        DecimalFormat df = new DecimalFormat("#.00000000");
        int iSelection = -1;
        
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Delimitado por comas .csv", "csv");
            fileChooser.setFileFilter(filter);
            fileChooser.setSelectedFile(new File("explosion.csv"));
            
            iSelection = fileChooser.showSaveDialog(fileChooser);
 
            if (iSelection == JFileChooser.CANCEL_OPTION) {
                return "El archivo no fue guardado";
            }
            
            File fileName = fileChooser.getSelectedFile();

            if (fileName == null) {
                return "Error al guardar el archivo";
            }
            
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new Ingredient object list to the CSV file
            
            Iterator it = lAtributos.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                SDataExplotionMaterialsEntry ingredient = (SDataExplotionMaterialsEntry) pair.getValue();
                
                fileWriter.append(ingredient.getDbmsItemKey());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(ingredient.getDbmsItem());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(ingredient.getDbmsItemUnitSymbol());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(df.format(ingredient.getGrossReq()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(df.format(ingredient.getAvailable()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(df.format(ingredient.getBackorder()));
                fileWriter.append(NEW_LINE_SEPARATOR);
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch (Exception e) {
            sResult += "Error al escribir el archivo";
            e.printStackTrace();
        } finally {

            try {
                if (iSelection == JFileChooser.APPROVE_OPTION && sResult.isEmpty()) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
            catch (IOException e) {
                sResult += "Error al cerrar el archivo";
                e.printStackTrace();
            }
        }
        
        return sResult;
    }
}

