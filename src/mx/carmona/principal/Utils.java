/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import mx.sat.cfd33.Comprobante33;
import mx.sat.cfd32.Comprobante32;
import mx.sat.cfd33.Comprobante33.Complemento;

/**
 *
 * @author Edwin Carmona
 */
public class Utils {
    
    public void procesarXmls(File[] files) throws JAXBException {
        ArrayList<ExportData> data;
        ArrayList<ExportData> allData = new ArrayList<>();
        for (File file: files) {
            Comprobante33 comp33 = fileToComprobante33(file);
            Comprobante32 comp32 = null;
            if (comp33.getVersion() != null && comp33.getVersion().equals("3.3")) {
                data = this.comprobante33ToData(comp33);
            }
            else {
                comp32 = fileToComprobante32(file);
                
                if (comp32.getVersion().equals("3.2")) {
                    data = this.comprobante32ToData(comp32);
                }
                else {
                    data = new ArrayList<>();
                }
            }
            
            allData.addAll(data);
        }
        
        crearSalida(allData);
    }
    
    private Comprobante33 fileToComprobante33(File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Comprobante33.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Comprobante33 comprobante = (Comprobante33) unmarshaller.unmarshal(file);
        
        return comprobante;
    }
    
    private Comprobante32 fileToComprobante32(File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Comprobante32.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Comprobante32 comprobante = (Comprobante32) unmarshaller.unmarshal(file);
        
        return comprobante;
    }
    
    private ArrayList<ExportData> comprobante33ToData(Comprobante33 comprobante) {
        ArrayList<ExportData> data;
        
        ExportData renglon = new ExportData();
        renglon.setRfcEmisor(comprobante.getEmisor().getRfc());
        renglon.setEmisor(comprobante.getEmisor().getNombre());
        renglon.setRfcReceptor(comprobante.getReceptor().getRfc());
        renglon.setReceptor(comprobante.getReceptor().getNombre());
        renglon.setFecha(comprobante.getFecha().toString());
        renglon.setTipoDeComprobante(Lector.getTipoComprobante(comprobante.getTipoDeComprobante()));
        renglon.setMetodoDePago(comprobante.getMetodoPago() == null ? "-" : comprobante.getMetodoPago().value());
        renglon.setTotal(comprobante.getTotal().doubleValue());
        renglon.setTotalImpuestosTrasladados(comprobante.getImpuestos() == null 
                                                ? 0d :
                                                comprobante.getImpuestos().getTotalImpuestosTrasladados().doubleValue());
        renglon.setUsoCfdi(comprobante.getReceptor().getUsoCFDI().value());
        
        data = Lector.leerPorTipo(comprobante, renglon);
        
        return data;
    }
    private ArrayList<ExportData> comprobante32ToData(Comprobante32 comprobante) {
        ArrayList<ExportData> data = new ArrayList<>();
        
        ExportData renglon = new ExportData();
        renglon.setRfcEmisor(comprobante.getEmisor().getRfc());
        renglon.setRfcReceptor(comprobante.getReceptor().getRfc());
        renglon.setFecha(comprobante.getFecha().toString());
        renglon.setMetodoDePago(comprobante.getMetodoDePago());
        renglon.setTotal(comprobante.getTotal().doubleValue());
        renglon.setTotalImpuestosTrasladados(comprobante.getImpuestos() == null 
                                                ? 0d :
                                                comprobante.getImpuestos().getTotalImpuestosTrasladados().doubleValue());
        renglon.setUsoCfdi("--");
        renglon.setUuid(comprobante.getComplemento() == null ? "" : comprobante.getComplemento().getAny().get(0).toString());
        
        for (Comprobante32.Conceptos.Concepto concepto : comprobante.getConceptos().getConcepto()) {
            ExportData renglonAux = renglon.clone();
            renglonAux.setConcepto(concepto.getDescripcion());
            renglonAux.setImporteConcepto(concepto.getImporte().doubleValue());
            
            data.add(renglonAux);
        }
                
        return data;
    }
    
    private void crearSalida(ArrayList<ExportData> datos) {
        SCsvFileManager1.writeCsvFile(datos);
        JOptionPane.showMessageDialog(null, "Operación finalizada.");
    }
}
