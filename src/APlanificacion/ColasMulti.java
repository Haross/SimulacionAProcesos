
package APlanificacion;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import static simulador.FXMLDocumentController.bandera;
import static simulador.FXMLDocumentController.colaEspera2;
import static simulador.FXMLDocumentController.colaEspera3;
import static simulador.FXMLDocumentController.colaEspera4;
import static simulador.FXMLDocumentController.colaEspera;
import static simulador.FXMLDocumentController.cpu;
import static simulador.FXMLDocumentController.data;
import static simulador.FXMLDocumentController.data1;
import static simulador.FXMLDocumentController.data2;
import simulador.Row;

/**
 *
 * @author Javier
 */
public class ColasMulti extends Thread {
    int t = 0,t2=0,t3=0,t4=0; //almacena todo el tiempo en cpu
    int te = 0,te2=0,te3 = 0,te4=0;
    double sumTr=0,sumPe=0,sumTe=0;
    int trespuesta = 0;
    int thick = 1;
    int numProceso = 0;
    double penalizacion = 0;
    TableView tableV,tableCPU,tableSalida;
    TextArea txtTe,txtTr,txtP;
    boolean completo ;
    int quantum;
    String proceso = "";
     int tRes = 0;
    String[] split;
    public ColasMulti(){
        
    }
    public ColasMulti(int quantum, TableView tableV, TableView tableCPU, TableView tableSalida, TextArea txtTe, TextArea txtTr, TextArea txtP) {
        this.tableV = tableV;
        this.tableCPU = tableCPU;
        this.tableSalida = tableSalida;
        this.txtP = txtP;
        this.txtTe = txtTe;
        this.txtTr = txtTr;
        this.quantum = quantum;
    }
  
     //Pone los elementos en tabla salida
   private void setTabla(String tL,String tR,String te,String tRespuesta,String penali,String or,String tipo){
       Row aux = new Row(tL,tR);  
       

            
                aux.setTe(te);
                aux.setTr(tRespuesta);
                aux.setP(penali);
                aux.setOrden(or);
                aux.setTipo(tipo);
                data2.add(aux);
                tableSalida.setItems(data2);
              
        
   }
   
 
   private String getTiempoEspera(int tiempoL){
       te = t - tiempoL;
       if(te <=0){
           te = 0;
           return te+"";
       }
       return te+"";
   }
   
   
   public void setTiempos(int tiempoL){
       numProceso++;
       trespuesta = t - tiempoL + thick;
        getTiempoEspera(tiempoL);                
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
              setTablaCPU(tiempoL+"", thick+"",split[split.length-1]);
                eliminar(tiempoL+"",thick+"");
                t = t+ thick;
   }
   /*
   * ROUND ROBIN
   */
   public void RRobin(){
        proceso = "";
            tRes = 0;
             completo = true;
       String aux = colaEspera.remove(0);               
                split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido               
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
                
                //tllegada:trequerido:trestante:te:tsalida
                if(!split[2].equals("N")){
                 
                    tiempoL =Integer.parseInt(split[4]);
                    thick =Integer.parseInt(split[2]);
                    if(thick > quantum){
                        tRes = thick-quantum;
                       proceso+= tiempoL+":"+thick+":"+tRes; 
                       thick = quantum;
                       completo = false;
                    }        
                }else{
                    if(thick > quantum){
                         tRes = thick-quantum;
                       proceso+= tiempoL+":"+thick+":"+tRes; 
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
                    System.out.println("te final "+te);
                    numProceso++;  
                     t = t+ thick;
                     proceso+=":"+t;
                     tiempoL = Integer.parseInt(split[0]);
                     thick = Integer.parseInt(split[1]);
                    trespuesta = t - tiempoL;  
                    System.out.println("t: "+t+" -"+tiempoL+ "= "+trespuesta);
                    penalizacion = (double)trespuesta/(double)thick;
                     sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
                }
                
              
                     cpu.add(aux );
                    setTablaCPU(split[0],split[1],split[split.length-1]);
                    eliminar(split[0],split[1]);
                
   }
   private void setTablaCPU(String tL, String th,String tipo) {
        tableCPU.getItems().clear();
        data1.clear();
        Row aux =new Row(tL + "", th + "");
        System.out.println("SETCPU: "+tipo);
        aux.setTipo(tipo);
        data1.add(aux);
        tableCPU.setItems(data1);
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
     private void regresar(String t,String tL, String tR, String te, String trestante, String proceso,String tipo) {
        System.out.println("Proceso regresado a espera");
        System.out.println(proceso);
        Row aux = new Row(tL, tR);
        aux.setTipo(tipo);
        aux.setTe(te);
        aux.setTiempoRestante(trestante);
        aux.setTSali(t);
        data.add(aux);

        colaEspera.add(proceso);
        tableV.setItems(data);
        return;
    }

     
    public void Pri(){
         numProceso++;
                String aux = colaEspera2.remove(0);
                
                split = aux.split(":");
                thick = Integer.parseInt(split[2]); //tiempo requerido
                
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
    
                trespuesta = t - tiempoL + thick;
                getTiempoEspera(tiempoL);
                
                
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
              
                t = t+ thick;
                cpu.add(aux );
                setTablaCPU(split[1], split[2], split[0],split[split.length-1]);
                eliminar(split[1],split[2],split[0]);
               
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

      private void setTablaCPU(String tL, String th, String prioridad,String tipo) {
         tableCPU.getItems().clear();
         data1.clear();
         Row aux = new Row(tL + "", th + "", prioridad + "");
         aux.setTipo(tipo);
        data1.add(aux);
        tableCPU.setItems(data1);
    }
        private void setTablaP(String pri,String tL,String tR,String te,String tRespuesta,String penali,String or,String tipo){
       Row aux = new Row(tL,tR, pri);  
       

            
                aux.setTe(te);
                aux.setTr(tRespuesta);
                aux.setP(penali);
                aux.setOrden(or);
                aux.setTipo(tipo);
                data2.add(aux);
                tableSalida.setItems(data2);
              
        
   }
    @Override
    public void run(){
        System.out.println("corriendo");
        do{
            int cola = 0;
            if(!colaEspera.isEmpty()){   
                System.out.println("Cola: sistema"); //RR
                RRobin();     
                cola = 1;
            }else if(!colaEspera2.isEmpty()){
                System.out.println("Cola: interactivos"); //Prioridad
                Pri();
                cola = 2;
            }else if(!colaEspera3.isEmpty()){
                System.out.println("Cola: edición interactivos"); //SJF
                String aux = colaEspera3.remove(0);
                split = aux.split(":");
                 thick = Integer.parseInt(split[0]); //tiempo requerido              
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                cpu.add(aux );
                setTiempos(tiempoL);
                setTablaCPU(split[1], split[0],split[split.length-1]);
                cola=3;
                eliminar(split[1],split[0]);
            }else if(!colaEspera4.isEmpty()){
                System.out.println("Cola: lotes");
                String aux = colaEspera4.remove(0);
               split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido                
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
                cpu.add(aux );
                setTiempos(tiempoL);
                setTablaCPU(split[0], split[1],split[split.length-1]);
                eliminar(split[0],split[1]);
            }             
            try {
                Thread.sleep(2000 * thick);
                if(cola ==1){
                     if(completo){
                        tableCPU.getItems().clear();
                        setTabla(split[0],split[1],te+"",trespuesta+"",penalizacion+"",numProceso+"",split[split.length-1]);
                    }else{
                        regresar(t+"",split[0],split[1], te+"",tRes+"" , proceso,split[split.length-1]);
                    }
                }else if(cola==2){
                    tableCPU.getItems().clear();
                    setTablaP(split[0],split[1],split[2],te+"",trespuesta+"",penalizacion+"",numProceso+"",split[split.length-1]);
                }else if(cola ==3){
                    tableCPU.getItems().clear();
                 setTabla(split[1],split[0],te+"",trespuesta+"",penalizacion+"",numProceso+"",split[split.length-1]);
                }else if(cola==1){
                       tableCPU.getItems().clear();
                 setTabla(split[0],split[1],te+"",trespuesta+"",penalizacion+"",numProceso+"",split[split.length-1]);
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Prioridad.class.getName()).log(Level.SEVERE, null, ex);
            }      
            
        }while(bandera);
        System.out.println("Hilo terminado");
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
    /*
    *SJF Y FCFS
    */

}

