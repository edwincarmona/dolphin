/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.util.ArrayList;
import mx.sat.cfd33.CTipoDeComprobante;
import mx.sat.cfd33.Comprobante33;

/**
 *
 * @author Edwin
 */
public class Lector {
    
    public static ArrayList<ExportData> leerPorTipo(Comprobante33 comprobante, ExportData renglon) {
        CTipoDeComprobante tdc = comprobante.getTipoDeComprobante();
        if (tdc.equals(CTipoDeComprobante.P)) {
            return Lector.leerPago(comprobante, renglon);
        }
        else if (tdc.equals(CTipoDeComprobante.I)) {
            return Lector.leerIngreso(comprobante, renglon);
        }
        else if (tdc.equals(CTipoDeComprobante.E)) {
            return null;
        }
        else if (tdc.equals(CTipoDeComprobante.N)) {
            return null;
        }
        
        return null;
    }
    
    public static ArrayList<ExportData> leerIngreso(Comprobante33 comprobante, ExportData renglon) {
        ArrayList<ExportData> data = new ArrayList<>();
        if (comprobante.getComplemento().size() > 0) {
            renglon.setUuid(((ElementNSImpl)comprobante.getComplemento().get(0).getAny().get(1)).getAttribute("UUID"));
        }
        else {
            renglon.setUuid("-");
        }
        
        for (Comprobante33.Conceptos.Concepto concepto : comprobante.getConceptos().getConcepto()) {
            ExportData renglonAux = renglon.clone();
            renglonAux.setCantidad(concepto.getCantidad().doubleValue());
            renglonAux.setConcepto(concepto.getDescripcion());
            renglonAux.setValorUnitario(concepto.getValorUnitario().doubleValue());
            renglonAux.setImporteConcepto(concepto.getImporte().doubleValue());
            
            data.add(renglonAux);
        }
        
        return data;
    }
    
    public static ArrayList<ExportData> leerPago(Comprobante33 comprobante, ExportData renglon) {
        ArrayList<ExportData> data = new ArrayList<>();
        
        for (Comprobante33.Complemento comp : comprobante.getComplemento()) {
            for (Object any : comp.getAny()) {
                ElementNSImpl elComplemento = (ElementNSImpl) any;
                if (elComplemento.getLocalName().equals("TimbreFiscalDigital")) {
                    renglon.setUuid(elComplemento.getAttribute("UUID"));
                }
                else if (elComplemento.getLocalName().equals("Pagos")) {
                    for (int i = 0; i < elComplemento.getChildNodes().getLength(); i++) {
                        System.out.println(elComplemento.getChildNodes().item(i).getAttributes().getNamedItem("NomBancoOrdExt"));
                        renglon.setUuidRelacionado(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("IdDocumento").
                                            getNodeValue());
                    }
                } 
            }
        }
        
        data.add(renglon);
        return data;
    }
    
    public static String getTipoComprobante(CTipoDeComprobante tcomp) {
        if (tcomp.equals(CTipoDeComprobante.P)) {
            return "P - Pagos";
        }
        else if (tcomp.equals(CTipoDeComprobante.I)) {
            return "I - Ingreso";
        }
        else if (tcomp.equals(CTipoDeComprobante.E)) {
            return "E - Egreso";
        }
        else if (tcomp.equals(CTipoDeComprobante.N)) {
            return "N - Nómina";
        }
        
        return "";
    }
}
