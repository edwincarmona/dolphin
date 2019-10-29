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
import mx.sat.cfd33.Comprobante33.CfdiRelacionados.CfdiRelacionado;

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
        else if (tdc.equals(CTipoDeComprobante.I) || tdc.equals(CTipoDeComprobante.E)) {
            return Lector.leerEgreso(comprobante, renglon);
        }
        else if (tdc.equals(CTipoDeComprobante.N)) {
            return Lector.leerNomina(comprobante, renglon);
        }
        
        return null;
    }
    
    public static ArrayList<ExportData> leerIngreso(Comprobante33 comprobante, ExportData renglon) {
        ArrayList<ExportData> data = new ArrayList<>();
        for (Comprobante33.Complemento comp : comprobante.getComplemento()) {
            for (Object any : comp.getAny()) {
                ElementNSImpl elComplemento = (ElementNSImpl) any;
                if (elComplemento.getLocalName().equals("TimbreFiscalDigital")) {
                    renglon.setUuid(elComplemento.getAttribute("UUID"));
                }
            }
        }
        
        for (Comprobante33.Conceptos.Concepto concepto : comprobante.getConceptos().getConcepto()) {
//            ExportData renglonAux = renglon.clone();
            String renglonCadena = "(" + concepto.getCantidad().doubleValue() + " - " +
                                    concepto.getDescripcion() + " - " + concepto.getValorUnitario().doubleValue() +
                                    " - " + concepto.getImporte().doubleValue() + ")";
            
            renglon.setConcepto(renglonCadena);
        }
        
        data.add(renglon);
        
        return data;
    }
        
    public static ArrayList<ExportData> leerEgreso(Comprobante33 comprobante, ExportData renglon) {
        ArrayList<ExportData> data = new ArrayList<>();
        for (Comprobante33.Complemento comp : comprobante.getComplemento()) {
            for (Object any : comp.getAny()) {
                ElementNSImpl elComplemento = (ElementNSImpl) any;
                if (elComplemento.getLocalName().equals("TimbreFiscalDigital")) {
                    renglon.setUuid(elComplemento.getAttribute("UUID"));
                }
            }
        }
        
        if (comprobante.getCfdiRelacionados() != null) {
            renglon.setTipoRelacion(Lector.getTipoDeRelacion(comprobante.getCfdiRelacionados().getTipoRelacion()));
            renglon.setCfdiRelacionadosNc("");
            
            for (CfdiRelacionado cfdiRel : comprobante.getCfdiRelacionados().getCfdiRelacionado()) {
                renglon.setCfdiRelacionadosNc( cfdiRel.getUUID() + "__" + renglon.getCfdiRelacionadosNc());
            }
        }
        
        for (Comprobante33.Conceptos.Concepto concepto : comprobante.getConceptos().getConcepto()) {
//            ExportData renglonAux = renglon.clone();
            String renglonCadena = "(" + concepto.getCantidad().doubleValue() + " - " +
                                    concepto.getDescripcion() + " - " + concepto.getValorUnitario().doubleValue() +
                                    " - " + concepto.getImporte().doubleValue() + ")";
            
            renglon.setConcepto(renglon.getConcepto() + renglonCadena);
        }
        
        data.add(renglon);
        
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
                        if (elComplemento.getChildNodes().item(i).getAttributes() == null) {
                            continue;
                        }
                        renglon.setUuidRelacionado(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("IdDocumento").
                                            getNodeValue());
                        renglon.setImpSaldoAnterior(Double.parseDouble(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("ImpSaldoAnt").
                                            getNodeValue()));
                        renglon.setImpPagado(Double.parseDouble(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("ImpPagado").
                                            getNodeValue()));
                        renglon.setImpSaldoInsoluto(Double.parseDouble(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("ImpSaldoInsoluto").
                                            getNodeValue()));
                        renglon.setMetodoDePagoDR(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("MetodoDePagoDR").
                                            getNodeValue());
                        renglon.setParcialidad(Integer.parseInt(elComplemento.getChildNodes().
                                            item(i).getChildNodes().item(0).
                                            getAttributes().getNamedItem("NumParcialidad").
                                            getNodeValue()));
                    }
                } 
            }
        }
        
        data.add(renglon);
        return data;
    }
    
    public static ArrayList<ExportData> leerNomina(Comprobante33 comprobante, ExportData renglon) {
        ArrayList<ExportData> data = new ArrayList<>();
        
        for (Comprobante33.Complemento comp : comprobante.getComplemento()) {
            for (Object any : comp.getAny()) {
                ElementNSImpl elComplemento = (ElementNSImpl) any;
                if (elComplemento.getLocalName().equals("TimbreFiscalDigital")) {
                    renglon.setUuid(elComplemento.getAttribute("UUID"));
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
        else if (tcomp.equals(CTipoDeComprobante.T)) {
            return "T - Traslado";
        }
        
        return "";
    }
    
    public static String getTipoDeRelacion(String tRelacion) {
//        01	Nota de crédito de los documentos relacionados
//        02	Nota de débito de los documentos relacionados
//        03	Devolución de mercancía sobre facturas o traslados previos
//        04	Sustitución de los CFDI previos
//        05	Traslados de mercancias facturados previamente
//        06	Factura generada por los traslados previos
//        07	CFDI por aplicación de anticipo
        if (tRelacion.equals("01")) {
            return tRelacion + " - Nota de crédito de los documentos relacionados";
        }
        else if (tRelacion.equals("02")) {
            return tRelacion + " - Nota de débito de los documentos relacionados";
        }
        else if (tRelacion.equals("03")) {
            return tRelacion + " - Devolución de mercancía sobre facturas o traslados previos";
        }
        else if (tRelacion.equals("04")) {
            return tRelacion + " - Sustitución de los CFDI previos";
        }
        else if (tRelacion.equals("05")) {
            return tRelacion + " - Traslados de mercancias facturados previamente";
        }
        else if (tRelacion.equals("06")) {
            return tRelacion + " - Factura generada por los traslados previos";
        }
        else if (tRelacion.equals("07")) {
            return tRelacion + " - CFDI por aplicación de anticipo";
        }
        
        return "";
    }
    
    
}
