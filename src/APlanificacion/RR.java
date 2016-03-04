/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APlanificacion;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import static simulador.FXMLDocumentController.bandera;
import static simulador.FXMLDocumentController.cpu;
import static simulador.FXMLDocumentController.data;
import static simulador.FXMLDocumentController.data1;
import static simulador.FXMLDocumentController.data2;
import static simulador.FXMLDocumentController.t;
import simulador.Row;

/**
 *
 * @author Javier
 */
public class RR extends Thread {

    
    int te = 0;
    double sumTr = 0, sumPe = 0, sumTe = 0;
    int trespuesta = 0;
    int thick = 1;
    int numProceso = 0;
    double penalizacion = 0;
    TableView tableV, tableCPU, tableSalida;
    TextArea txtTe, txtTr, txtP;
    int quantum;
ArrayList<String>colaEspera;
    public RR() {

    }

    public RR(ArrayList<String>colaEspera,int quantum, TableView tableV, TableView tableCPU, TableView tableSalida, TextArea txtTe, TextArea txtTr, TextArea txtP) {
        this.tableV = tableV;
        this.tableCPU = tableCPU;
        this.tableSalida = tableSalida;
        this.txtP = txtP;
        this.txtTe = txtTe;
        this.txtTr = txtTr;
        this.quantum = quantum;
        this.colaEspera = colaEspera;
    }

    //Pone los elementos en tabla salida

    private void setTabla(String tL, String tR, String te, String tRespuesta, String penali, String or) {
        Row aux = new Row(tL, tR);

        aux.setTe(te);
        aux.setTr(tRespuesta);
        aux.setP(penali);
        aux.setOrden(or);
        aux.setTipo("Sistema");
        data2.add(aux);
        tableSalida.setItems(data2);

    }

    private void eliminar(String tL, String tR) {
        Row aux = new Row(tL, tR);

        for (int i = 0; i < data.size(); i++) {

            if (tL.equals(data.get(i).getTiempoLlegada())
                    && tR.equals(data.get(i).getTiempoRequerido())) {
                data.remove(i);

                tableV.setItems(data);
                return;
            }

        }

    }

    private String getTiempoEspera(int tiempoL) {
        te = t - tiempoL;
        //System.out.println("Metetod getTE:"+te);
        if (te <= 0) {
            te = 0;
            //System.out.println("negativo"+te);
            return 0 +""; 
        }
        return te + "";
    }

    private void setTablaCPU(String tL, String th) {
        tableCPU.getItems().clear();
        data1.clear();
       Row aux = new Row(tL + "", th + "");
       aux.setTipo("Sistema");
        data1.add(aux);
        tableCPU.setItems(data1);
    }

    private void regresar(String t,String tL, String tR, String te, String trestante, String proceso) {
        //System.out.println("Proceso regresado a espera");
        //System.out.println(proceso);
        Row aux = new Row(tL, tR);
        aux.setTe(te);
        aux.setTiempoRestante(trestante);
        aux.setTSali(t);
        aux.setTipo("Sistema");
        data.add(aux);
        //System.out.println("Se regreso "+tL +" "+tR);
        colaEspera.add(proceso);
        tableV.setItems(data);
        return;
    }

    
    
    
    @Override
    public void run() {

        do{
            String proceso = "";
            int tRes = 0;
            boolean completo = true;
            if(!colaEspera.isEmpty()){       
                    
                String aux = colaEspera.remove(0);     
                //System.out.println("aux sacada"+aux);
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido               
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
                proceso = tiempoL+":"+thick+":";
                //tllegada:trequerido:trestante:te:tsalida
                if(!split[2].equals("N")){
                 
                    tiempoL =Integer.parseInt(split[4]);
                    thick =Integer.parseInt(split[2]);
                    if(thick > quantum){
                        tRes = thick-quantum;
                       proceso+= tRes; 
                       thick = quantum;
                       completo = false;
                    } 
                  
                    
                }else{
                    if(thick > quantum){
                         tRes = thick-quantum;
                       proceso+= tRes; 
                       thick = quantum;
                       completo = false;
                    } 
                }

                getTiempoEspera(tiempoL);
                
                if(!completo){
                    int teAux = te;
                    if(!split[3].equals("N"))
                     teAux = te+Integer.parseInt(split[3]);
                    proceso+=":"+teAux;
                    t= t + thick;
                     proceso+=":"+t;
                }else{
                    
                    if(!split[3].equals("N"))
                        te = te+Integer.parseInt(split[3]);
                    //System.out.println("te final "+te);
                    numProceso++;  
                     t = t+ thick;
                     proceso+=":"+t;
                     tiempoL = Integer.parseInt(split[0]);
                     thick = Integer.parseInt(split[1]);
                    trespuesta = thick +te;  
                     //System.out.println("t"+t+" -"+tiempoL );
                    //System.out.println("t: "+t+" -"+tiempoL+ "= "+trespuesta);
                    penalizacion = (double)trespuesta/(double)thick;
                     sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
                }
                
              
                    cpu.add(aux );
                    setTablaCPU(split[0],split[1]);
                    eliminar(split[0],split[1]);
                    //System.out.println("Eliminar: "+split[0]+"  "+split[1]);
                
                 try {
                     if(bandera){
                Thread.sleep(1000 * thick);}
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(FCFS.class.getName()).log(Level.SEVERE, null, ex);
            }   
                 if(completo){
                    tableCPU.getItems().clear();
                    setTabla(split[0],split[1],te+"",trespuesta+"",penalizacion+"",numProceso+"");
                }else{
                     //System.out.println("Proceso regresado a espera");
        System.out.println(proceso);
        Row aux2 = new Row(split[0]+"", split[1]);
        aux2.setTe(te+"");
        aux2.setTiempoRestante(tRes+"");
        aux2.setTSali(t+"");
        aux2.setTipo("Sistema");
        data.add(aux2);
                     System.out.println("proceso:"+proceso);
        colaEspera.add(proceso);
        tableV.setItems(data);
                }
            }else{
                 try {
                if(bandera)
                Thread.sleep(1000 * thick);
              
            } catch (InterruptedException ex) {
                Logger.getLogger(FCFS.class.getName()).log(Level.SEVERE, null, ex);
            }      
            }             
           
             tableV.setItems(data);
        }while(bandera || !colaEspera.isEmpty());
       
        setFinales();
       
        //System.out.println("Hilo terminado RR");
    }

    public void empezar() {

       
            String proceso = "";
            int tRes = 0;
            boolean completo = true;
             
                    
                String aux = colaEspera.remove(0);               
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido               
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
                proceso = tiempoL+":"+thick+":";
                //tllegada:trequerido:trestante:te:tsalida
                if(!split[2].equals("N")){
                 
                    tiempoL =Integer.parseInt(split[4]);
                    thick =Integer.parseInt(split[2]);
                    if(thick > quantum){
                        tRes = thick-quantum;
                       proceso+= tRes; 
                       thick = quantum;
                       completo = false;
                    } 
                  
                    
                }else{
                    if(thick > quantum){
                         tRes = thick-quantum;
                       proceso+= tRes; 
                       thick = quantum;
                       completo = false;
                    } 
                }

                getTiempoEspera(tiempoL);
                
                if(!completo){
                    int teAux = te;
                    if(!split[3].equals("N"))
                     teAux = te+Integer.parseInt(split[3]);
                    proceso+=":"+teAux;
                    t= t + thick;
                     proceso+=":"+t;
                }else{
                    
                    if(!split[3].equals("N"))
                        te = te+Integer.parseInt(split[3]);
                    //System.out.println("te final "+te);
                    numProceso++;  
                     t = t+ thick;
                     proceso+=":"+t;
                     tiempoL = Integer.parseInt(split[0]);
                     thick = Integer.parseInt(split[1]);
                      trespuesta = thick +te;
                    //System.out.println("t: "+t+" -"+tiempoL+ "= "+trespuesta);
                    penalizacion = (double)trespuesta/(double)thick;
                     sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
                }
                
              
                     cpu.add(aux );
                    setTablaCPU(split[0],split[1]);
                    eliminar(split[0],split[1]);
                
                 try {
                     if(bandera)
                Thread.sleep(1000 * thick);
                if(completo){
                    tableCPU.getItems().clear();
                    setTabla(split[0],split[1],te+"",trespuesta+"",penalizacion+"",numProceso+"");
                }else{
                    //regresar(t+"",split[0],split[1], te+"",tRes+"" , proceso);
                    //System.out.println("Proceso regresado a espera");
        //System.out.println(proceso);
        Row aux2 = new Row(split[0]+"", split[1]);
        aux2.setTe(te+"");
        aux2.setTiempoRestante(tRes+"");
        aux2.setTSali(t+"");
        aux2.setTipo("Sistema");
        data.add(aux2);

        colaEspera.add(proceso);
        tableV.setItems(data);
   
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(FCFS.class.getName()).log(Level.SEVERE, null, ex);
            }      
                         
           
    
    }

    public void setFinales() {
        
        double aux = (double) sumTe / (double) numProceso;
        txtTe.setText(aux + "");
        //System.out.println("tr " + sumTr);
        aux = (double) sumTr / (double) numProceso;
        txtTr.setText(aux + "");

        aux = (double) sumPe / (double) numProceso;
        //System.out.println(aux);
        txtP.setText(aux + "");
    }
     public double getTe(){
        return sumTe;
    }
    public double getTr(){
        return sumTr;
    }
    public double getPe(){
        return sumPe;
    }
    public int getNum(){
        return numProceso;
    }

}
