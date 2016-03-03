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
public class SJF extends Thread{
   
    int te = 0;
    double sumTr=0,sumPe=0,sumTe=0;
    int trespuesta = 0;
    int thick = 1;
    int numProceso = 0;
    double penalizacion = 0;
    TableView tableV,tableCPU,tableSalida;
    TextArea txtTe,txtTr,txtP;
    ArrayList<String> colaEspera;
    public SJF(){
        
    }
   public SJF(ArrayList<String> colaEspera,TableView tableV,TableView tableCPU, TableView tableSalida,TextArea txtTe,TextArea  txtTr,TextArea txtP){
       this.tableV = tableV;
       this.tableCPU = tableCPU;
       this.tableSalida = tableSalida;
       this.txtP = txtP;
       this.txtTe = txtTe;
       this.txtTr = txtTr;
       this.colaEspera = colaEspera;
   }
   //Pone los elementos en tabla salida
   private void setTabla(String tL,String tR,String te,String tRespuesta,String penali,String or){
       Row aux = new Row(tL,tR);  
       

            
                aux.setTe(te);
                aux.setTr(tRespuesta);
                aux.setP(penali);
                aux.setOrden(or);
                aux.setTipo("E interactivos");
                data2.add(aux);
                tableSalida.setItems(data2);
              
        
   }
     private void eliminar(String tL,String tR){
       Row aux = new Row(tL,tR);  
       
       for (int i = 0; i < data.size(); i++) {
            
            if(tL.equals(data.get(i).getTiempoLlegada()) && 
                    tR.equals(data.get(i).getTiempoRequerido() ) ){
               
      
                data.remove(i);
               
                tableV.setItems(data);
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
   
     private void setTablaCPU(String tL, String th) {
         tableCPU.getItems().clear();
         data1.clear();
         Row aux = new Row(tL + "", th + "");
         aux.setTipo("E interactivos");
        data1.add(aux);
        tableCPU.setItems(data1);
    }

    @Override
    public void run(){
        String a = " ", b= " ";
        do{
            if(!colaEspera.isEmpty()){   
                
                numProceso++;
                String aux = colaEspera.remove(0);
                
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[0]); //tiempo requerido
                
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                a = split[1];
                b = split[0];

                trespuesta = t - tiempoL + thick;
                getTiempoEspera(tiempoL);
                
                
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
              
                t = t+ thick;
                cpu.add(aux );
                setTablaCPU(a, b);
                eliminar(a,b);
                 try {
                Thread.sleep(2000 * thick);
                 tableCPU.getItems().clear();
                 setTabla(a,b,te+"",trespuesta+"",penalizacion+"",numProceso+"");
            } catch (InterruptedException ex) {
                Logger.getLogger(SJF.class.getName()).log(Level.SEVERE, null, ex);
            }      
            }else{
                 try {
                Thread.sleep(2000 * thick);
              
            } catch (InterruptedException ex) {
                Logger.getLogger(SJF.class.getName()).log(Level.SEVERE, null, ex);
            }      
            }             
           
            
        }while(bandera);
        System.out.println("Hilo terminado");
    }
 
    public void empezar(){
        String a = " ", b= " ";
     
          
                
                numProceso++;
                String aux = colaEspera.remove(0);
                
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[0]); //tiempo requerido
                
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                a = split[1];
                b = split[0];

                trespuesta = t - tiempoL + thick;
                getTiempoEspera(tiempoL);
                
                
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
              
                t = t+ thick;
                cpu.add(aux );
                setTablaCPU(a, b);
                eliminar(a,b);
                 try {
                Thread.sleep(2000 * thick);
                 tableCPU.getItems().clear();
                 setTabla(a,b,te+"",trespuesta+"",penalizacion+"",numProceso+"");
            } catch (InterruptedException ex) {
                Logger.getLogger(SJF.class.getName()).log(Level.SEVERE, null, ex);
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
