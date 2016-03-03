/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APlanificacion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static simulador.FXMLDocumentController.bandera;
import static simulador.FXMLDocumentController.cpu;
import static simulador.FXMLDocumentController.colaEspera;
import static simulador.FXMLDocumentController.data;
import static simulador.FXMLDocumentController.data1;
import static simulador.FXMLDocumentController.data2;
import static simulador.FXMLDocumentController.t;
import simulador.Row;
/**
 *
 * @author Javier
 */
public class Prioridad extends Thread{
  
    int te = 0;
    double sumTr=0,sumPe=0,sumTe=0;
    int trespuesta = 0;
    int thick = 1;
    int numProceso = 0;
    double penalizacion = 0;
    TableView tableV,tableCPU,tableSalida;
    TextArea txtTe,txtTr,txtP;
    ArrayList<String> colaEspera;
    public Prioridad(){
        
    }
   public Prioridad( ArrayList<String> colaEspera,TableView tableV,TableView tableCPU, TableView tableSalida,TextArea txtTe,TextArea  txtTr,TextArea txtP){
       this.tableV = tableV;
       this.tableCPU = tableCPU;
       this.tableSalida = tableSalida;
       this.txtP = txtP;
       this.txtTe = txtTe;
       this.txtTr = txtTr;
       this.colaEspera=colaEspera;
   }
   //Pone los elementos en tabla salida
   private void setTabla(String pri,String tL,String tR,String te,String tRespuesta,String penali,String or){
       Row aux = new Row(tL,tR, pri);  
       

            
                aux.setTe(te);
                aux.setTr(tRespuesta);
                aux.setP(penali);
                aux.setOrden(or);
                aux.setTipo("Interactivos");
                data2.add(aux);
                tableSalida.setItems(data2);
              
        
   }
     private void eliminar(String tL,String tR, String Pri){
       Row aux = new Row(tL,tR);  
       
       for (int i = 0; i < data.size(); i++) {
            
            if(tL.equals(data.get(i).getTiempoLlegada()) && 
                    tR.equals(data.get(i).getTiempoRequerido() ) && Pri.equals(data.get(i).getPrioridad())){
               
      
                data.remove(i);
                
                return;
            }
            
        }   
        
   }
   
   private String getTiempoEspera(int tiempoL) {
        te = t - tiempoL;
        System.out.println("Metetod getTE:"+te);
        if (te <= 0) {
            te = 0;
            System.out.println("negativo"+te);
            return 0 +""; 
        }
        return te + "";
    }
   
     private void setTablaCPU(String tL, String th, String prioridad) {
         tableCPU.getItems().clear();
         data1.clear();
         Row aux = new Row(tL + "", th + "", prioridad + "");
         aux.setTipo("Interactivos");
        data1.add(aux);
        tableCPU.setItems(data1);
    }

    @Override
    public void run(){
        String a = " ", b= " ",c=" ";
        do{
            if(!colaEspera.isEmpty()){   
                
                numProceso++;
                String aux = colaEspera.remove(0);
                
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[2]); //tiempo requerido
                
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                a = split[0];
                b = split[1];
                c = split[2];
                trespuesta = t - tiempoL + thick;
                getTiempoEspera(tiempoL);
                
                 System.out.println("t"+t+" -"+tiempoL +"+ "+thick);
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
              
                t = t+ thick;
                cpu.add(aux );
                setTablaCPU(b, c, a);
                eliminar(b,c,a);
                 try {
                Thread.sleep(2000 * thick);
                 tableCPU.getItems().clear();
                 setTabla(a,b,c,te+"",trespuesta+"",penalizacion+"",numProceso+"");
            } catch (InterruptedException ex) {
                Logger.getLogger(Prioridad.class.getName()).log(Level.SEVERE, null, ex);
            }      
            }else{
                 try {
                Thread.sleep(2000 * thick);
              
            } catch (InterruptedException ex) {
                Logger.getLogger(Prioridad.class.getName()).log(Level.SEVERE, null, ex);
            }      
            }             
           
            
        }while(bandera);
        System.out.println("Hilo terminado");
    }
    
    public void empezar(){
        String a = " ", b= " ",c=" ";
 
                numProceso++;
                String aux = colaEspera.remove(0);
                
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[2]); //tiempo requerido
                
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                a = split[0];
                b = split[1];
                c = split[2];
                trespuesta = t - tiempoL + thick;
                getTiempoEspera(tiempoL);
                
                 System.out.println("t"+t+" -"+tiempoL +"+ "+thick);
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
              
                t = t+ thick;
                cpu.add(aux );
                setTablaCPU(b, c, a);
                eliminar(b,c,a);
                 try {
                Thread.sleep(2000 * thick);
                 tableCPU.getItems().clear();
                 setTabla(a,b,c,te+"",trespuesta+"",penalizacion+"",numProceso+"");
            } catch (InterruptedException ex) {
                Logger.getLogger(Prioridad.class.getName()).log(Level.SEVERE, null, ex);
            }      
           
    }
    public void setFinales(){
        double aux = (double)sumTe/(double)numProceso;
        txtTe.setText(aux+"");
        System.out.println("tr "+sumTr);
        aux = (double)sumTr/(double)numProceso;
        txtTr.setText(aux+"");
     
        aux = (double)sumPe/(double)numProceso;
        System.out.println(aux);
        txtP.setText(aux+"");
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
