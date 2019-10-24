/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Edwin Carmona
 */
public class SCsvFileManager {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "RfcEmisor,Emisor,RfcReceptor,Receptor,Fecha,"
            + "FormaDePago,MetodoPago,cfdiRelacionados,tipoRelacion,"
            + "TipoComprobante,Uso,UUID,SubTotal,"
            + "Descuento,Total,TotalImptras,cantidad,concepto,"
            + "Unitario,importeConcepto,PagoCfdiRelacionado,saldoAnterior,pagado,"
            + "nuevoSaldo,metodoPagoDR,Parcialidad,cfdiRelacionado,tipoRelacion";

    public static String writeCsvFile(ArrayList<ExportData> lAtributos) {
        String sResult = "";
        FileWriter fileWriter = null;
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
                return "Error al guardar el archivo";
            }
            else {
                String fn = fileName.getName();
                if (!fn.substring(fn.lastIndexOf("."), fn.length()).toLowerCase().equals(".csv")){
                    return "Error al guardar el archivo";
                }
            }
            
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new Ingredient object list to the CSV file
            
            for (ExportData renglon : lAtributos) {
//                RfcEmisor,RfcReceptor,Fecha,MétodoPago,Uso,UUID,Total,TotalImptras,concepto,importeConcepto
                fileWriter.append(renglon.getRfcEmisor());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getEmisor().replace(',', ' '));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getRfcReceptor());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getReceptor().replace(',', ' '));
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
                fileWriter.append(cantidad.format(renglon.getCantidad()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(renglon.getConcepto().replace(',', ' '));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getValorUnitario()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(monto.format(renglon.getImporteConcepto()));
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
