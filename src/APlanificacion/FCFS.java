/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APlanificacion;

/**
 *
 * @author Javier
 */
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import static simulador.FXMLDocumentController.bandera;
import static simulador.FXMLDocumentController.colaEspera;
import static simulador.FXMLDocumentController.cpu;
import static simulador.FXMLDocumentController.data;
import simulador.Row;

public class FCFS extends Thread {
    int t = 0; //almacena todo el tiempo en cpu
    int te = 0;
    double sumTr=0,sumPe=0,sumTe=0;
    int trespuesta = 0;
    int thick = 1;
    int numProceso = 0;
    double penalizacion = 0;
    TableView tableV;
    TextArea txtTe,txtTr,txtP;
    public FCFS(){
        
    }
   public FCFS(TableView tableV,TextArea txtTe,TextArea  txtTr,TextArea txtP){
       this.tableV = tableV;
       this.txtP = txtP;
       this.txtTe = txtTe;
       this.txtTr = txtTr;
   }
   private void setTabla(String tL,String tR,String te,String tRespuesta,String penali,String or){
       Row aux = new Row(tL,tR);  
       
       for (int i = 0; i < data.size(); i++) {
            
            if(tL.equals(data.get(i).getTiempoLlegada()) && 
                    tR.equals(data.get(i).getTiempoRequerido())){
               
                aux.setTe(te);
                aux.setTr(tRespuesta);
                aux.setP(penali);
                aux.setOrden(or);
                data.remove(i);
                data.add(i,aux);
                return;
            }
            
        }   
        
   }
   
   private String getTiempoEspera(int tiempoL){
       te = t - tiempoL;
       if(te * -1 >=0){
           te = -1 * te;
           return -te+"";
       }
       return te+"";
   }
   
    @Override
    public void run(){
        System.out.println("corriendo");
        do{
            if(!colaEspera.isEmpty()){   
                numProceso++;
                String aux = colaEspera.remove(0);
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido
                
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
       
                trespuesta = t - tiempoL + thick;
                getTiempoEspera(tiempoL);                
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
                setTabla(split[0],split[1],te+"",trespuesta+"",penalizacion+"",numProceso+"");
                t = t+ thick;
                cpu.add(aux );
      
            }             
            try {
                Thread.sleep(1000 * thick);
            } catch (InterruptedException ex) {
                Logger.getLogger(Prioridad.class.getName()).log(Level.SEVERE, null, ex);
            }      
            
        }while(bandera);
        double aux = (double)sumTe/(double)numProceso;
        txtTe.setText(aux+"");
        System.out.println("tr "+sumTr);
        aux = (double)sumTr/(double)numProceso;
        txtTr.setText(aux+"");
     
        aux = (double)sumPe/(double)numProceso;
        System.out.println(aux);
        txtP.setText(aux+"");
    }
}
