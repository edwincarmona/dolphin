/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static java.nio.charset.StandardCharsets.*;

/**
 *
 * @author Edwin Carmona
 */
public class SCsvFileManager {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "RFC del emisor,Emisor,RFC del receptor,Receptor,Fecha,"
            + "Forma de pago,Metodo de pago,CFDI Relacionados NC,Tipo de relacion,"
            + "Tipo de comprobante,Uso CFDI,UUID,SubTotal,"
            + "Descuento,Total,Total Imptos trasladados,Total Imptos retenidos,Concepto(cantidad-concepto-valorUnitario-importeConcepto),"
            + "CFDI Relacionado De Pago,Saldo anterior,Pagado,"
            + "Nuevo Saldo,Metodo Pago DR,Parcialidad,CFDI Relacionado,Tipo de Relacion";

    public static String writeCsvFile(ArrayList<ExportData> lAtributos) {
        String sResult = "";
        Writer fileWriter = null;
        DecimalFormat cantidad = new DecimalFormat("##.000");
        DecimalFormat monto = new DecimalFormat("##.00");
        int iSelection = -1;
        
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Delimitado por comas .csv", "csv");
            fileChooser.setFileFilter(filter);
            fileChooser.setSelectedFile(new File("resultado.csv"));
            
            iSelection = fileChooser.showSaveDialog(fileChooser);
 
            if (iSelection == JFileChooser.CANCEL_OPTION) {
                return "El archivo no fue guardado";
            }
            
            File fileName = fileChooser.getSelectedFile();
            
            if (fileName == null) {
                sResult = "Error al guardar el archivo";
                return "Error al guardar el archivo";
            }
            else {
                String fn = fileName.getName();
                if (! fn.substring(fn.lastIndexOf("."), fn.length()).toLowerCase().equals(".csv")) {
                    sResult = "Error al guardar el archivo";
                    return "Error al guardar el archivo";
                }
            }
            
            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "8859_1"));

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new Ingredient object list to the CSV file
            
            String emisor;
            String receptor;
            String text;
            
            for (ExportData renglon : lAtributos) {
//                RfcEmisor,RfcReceptor,Fecha,MétodoPago,Uso,UUID,Total,TotalImptras,concepto,importeConcepto
                fileWriter.append(renglon.getRfcEmisor());
                fileWriter.append(COMMA_DELIMITER);
                emisor = renglon.getEmisor().toUpperCase().replace(',', ' ');
                fileWriter.append(emisor);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getRfcReceptor());
                fileWriter.append(COMMA_DELIMITER);
                receptor = renglon.getReceptor() == null ? "" : renglon.getReceptor().toUpperCase().replace(',', ' ');
                fileWriter.append(receptor);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getFecha());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getFormaDePago());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getMetodoDePago());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getCfdiRelacionadosNc());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getTipoRelacion());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getTipoDeComprobante());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getUsoCfdi());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getUuid());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getSubTotal()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getDescuento()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getTotal()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getTotalImpuestosTrasladados()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getTotalImpuestosRetenidos()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getConcepto().replace(',', ' '));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getUuidRelacionado());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getImpSaldoAnterior()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getImpPagado()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getImpSaldoInsoluto()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getMetodoDePagoDR());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getParcialidad() + "");
                
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        }
        catch (HeadlessException | IOException e) {
            sResult += "Error al escribir el archivo";
        }
        finally {

            try {
                if (iSelection == JFileChooser.APPROVE_OPTION && sResult.isEmpty()) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            }
            catch (IOException e) {
                sResult += "Error al cerrar el archivo";
            }
        }
        
        return sResult;
    }
}

