/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Javier
 */
public class Row {
    private final StringProperty tiempoLlegada;
    private final StringProperty tiempoRequerido;
    private final StringProperty prioridad;
    private StringProperty tipo;
    private  StringProperty te;
    private  StringProperty tr;
    private  StringProperty p;
    private StringProperty orden;
    private StringProperty tRestante;
     private StringProperty tSali;
    
    /**
     *
     * @param tiempoLlegada
     * @param tiempoRequerido
     * @param prioridad
     * @param tipo
     */
    public Row(String tiempoLlegada, String tiempoRequerido,String prioridad, String tipo) {
        this.tiempoLlegada = new SimpleStringProperty(tiempoLlegada);
        this.tiempoRequerido = new SimpleStringProperty(tiempoRequerido);
        this.tipo = new SimpleStringProperty(tipo);
        this.te = new SimpleStringProperty(" ");
        this.prioridad = new SimpleStringProperty(prioridad);
        this.tr = new SimpleStringProperty(" ");
        this.p = new SimpleStringProperty(" ");    
        this.orden = new SimpleStringProperty(" ");    
    }
    
    /**
     *
     * @param tiempoLlegada
     * @param tiempoRequerido
     * @param prioridad
     */
    public Row(String tiempoLlegada, String tiempoRequerido,String prioridad) {
        this.tiempoLlegada = new SimpleStringProperty(tiempoLlegada);
        this.tiempoRequerido = new SimpleStringProperty(tiempoRequerido);
        this.tipo = new SimpleStringProperty(" ");
        this.te = new SimpleStringProperty(" ");
        this.prioridad = new SimpleStringProperty(prioridad);
        this.tr = new SimpleStringProperty(" ");
        this.p = new SimpleStringProperty(" ");  
        this.orden = new SimpleStringProperty(" "); 
    }
    /**
     *
     * @param tiempoLlegada
     * @param tiempoRequerido
     * @param prioridad
     */
    public Row(String tiempoLlegada, String tiempoRequerido) {
        this.tiempoLlegada = new SimpleStringProperty(tiempoLlegada);
        this.tiempoRequerido = new SimpleStringProperty(tiempoRequerido);
        this.tipo = new SimpleStringProperty(" ");
        this.te = new SimpleStringProperty(" ");
        this.prioridad = new SimpleStringProperty(" ");
        this.tr = new SimpleStringProperty(" ");
        this.p = new SimpleStringProperty(" "); 
        this.orden = new SimpleStringProperty(" "); 
        
    }
    public void setTe(String te){
       this.te = new SimpleStringProperty(te); 
    }
    public void setTSali(String tSali){
       this.tSali = new SimpleStringProperty(tSali); 
    }
    public void setOrden(String orden){
        this.orden = new SimpleStringProperty(orden);  
    }
    public void setTr(String tr){
       this.tr = new SimpleStringProperty(tr); 
    }
     public void setP(String p){
       this.p = new SimpleStringProperty(p); 
    }
    public void setTipo(String tipo){
        this.tipo = new SimpleStringProperty(tipo);
    }
   public void setTiempoRestante(String tRestante){
        this.tRestante= new SimpleStringProperty(tRestante);
    }
    public String getTiempoLlegada(){
       return tiempoLlegada.get();
    }
    public String getTiempoRestante(){
       return tRestante.get();
    }
    public String getTiempoRequerido(){
       return tiempoRequerido.get();
    }
    public String getPrioridad(){
       return prioridad.get();
    }
    public String getTipo(){
       return tipo.get();
    }
    public StringProperty tiempoLlegadaProperty() {
        return tiempoLlegada;
    }
    public StringProperty tiempoRequeridoProperty() {
        return tiempoRequerido;
    }
    public StringProperty tSaliProperty() {
        return tSali;
    }
    public StringProperty prioridadProperty() {
        return prioridad;
    }
    public StringProperty tipoProperty() {
        return tipo;
    }
    public StringProperty teProperty() {
        return te;
    }
    public StringProperty tRestanteProperty() {
        return tRestante;
    }
    public StringProperty trProperty() {
        return tr;
    }
    public StringProperty pProperty() {
        return p;
    }
    public StringProperty ordenProperty() {
        return orden;
    }
}
