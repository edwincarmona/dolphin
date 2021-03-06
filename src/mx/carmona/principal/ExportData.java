/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.carmona.principal;

/**
 *
 * @author Edwin
 */
public class ExportData  implements Cloneable{
    protected String rfcEmisor;
    protected String rfcReceptor;
    protected String emisor;
    protected String receptor;
    protected String fecha;
    protected String metodoDePago;
    protected String formaDePago;
    protected String tipoDeComprobante;
    protected String usoCfdi;
    protected String uuid;
    protected String uuidRelacionado;
    protected String cfdiRelacionadosNc;
    protected String tipoRelacion;
    protected double cantidad;
    protected double total;
    protected double subTotal;
    protected double descuento;
    protected double totalImpuestosTrasladados;
    protected double totalImpuestosRetenidos;
    protected double impSaldoAnterior;
    protected double impPagado;
    protected double impSaldoInsoluto;
    protected String concepto;
    protected double importeConcepto;
    protected double valorUnitario;
    protected String metodoDePagoDR;
    protected int parcialidad;

    public ExportData() {
        this.concepto = "";
        this.rfcEmisor = "";
        this.rfcReceptor = "";
        this.emisor = "";
        this.receptor = "";
        this.fecha = "";
        this.metodoDePago = "";
        this.formaDePago = "";
        this.tipoDeComprobante = "";
        this.usoCfdi = "";
        this.uuid = "";
        this.uuidRelacionado = "";
        this.cfdiRelacionadosNc = "";
        this.tipoRelacion = "";
        this.metodoDePagoDR = "";
    }

    public String getRfcEmisor() {
        return rfcEmisor;
    }

    public void setRfcEmisor(String rfcEmisor) {
        this.rfcEmisor = rfcEmisor;
    }

    public String getRfcReceptor() {
        return rfcReceptor;
    }

    public void setRfcReceptor(String rfcReceptor) {
        this.rfcReceptor = rfcReceptor;
    }
    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }
    public String getTipoDeComprobante() {
        return tipoDeComprobante;
    }

    public void setTipoDeComprobante(String tipoDeComprobante) {
        this.tipoDeComprobante = tipoDeComprobante;
    }

    public String getUsoCfdi() {
        return usoCfdi;
    }

    public void setUsoCfdi(String usoCfdi) {
        this.usoCfdi = usoCfdi;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getUuidRelacionado() {
        return uuidRelacionado;
    }

    public void setUuidRelacionado(String uuidRelacionado) {
        this.uuidRelacionado = uuidRelacionado;
    }

    public String getCfdiRelacionadosNc() {
        return cfdiRelacionadosNc;
    }

    public void setCfdiRelacionadosNc(String cfdiRelacionadosNc) {
        this.cfdiRelacionadosNc = cfdiRelacionadosNc;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }

    public void setTotalImpuestosTrasladados(double totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }
    
    public double getTotalImpuestosRetenidos() {
        return totalImpuestosRetenidos;
    }

    public void setTotalImpuestosRetenidos(double totalImpuestosRetenidos) {
        this.totalImpuestosRetenidos = totalImpuestosRetenidos;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getImporteConcepto() {
        return importeConcepto;
    }

    public void setImporteConcepto(double importeConcepto) {
        this.importeConcepto = importeConcepto;
    }
    
    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getImpSaldoAnterior() {
        return impSaldoAnterior;
    }

    public void setImpSaldoAnterior(double impSaldoAnterior) {
        this.impSaldoAnterior = impSaldoAnterior;
    }

    public double getImpPagado() {
        return impPagado;
    }

    public void setImpPagado(double impPagado) {
        this.impPagado = impPagado;
    }

    public double getImpSaldoInsoluto() {
        return impSaldoInsoluto;
    }

    public void setImpSaldoInsoluto(double impSaldoInsoluto) {
        this.impSaldoInsoluto = impSaldoInsoluto;
    }

    public String getMetodoDePagoDR() {
        return metodoDePagoDR;
    }

    public void setMetodoDePagoDR(String MetodoDePagoDR) {
        this.metodoDePagoDR = MetodoDePagoDR;
    }

    public int getParcialidad() {
        return parcialidad;
    }

    public void setParcialidad(int parcialidad) {
        this.parcialidad = parcialidad;
    }
    
    
    @Override
    public ExportData clone(){
        ExportData obj=null;
        try{
            obj = (ExportData) super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        
        return obj;
    }
}
