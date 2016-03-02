
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
    TableView tableV;
    TextArea txtTe,txtTr,txtP;
    public ColasMulti(){
        
    }
   public ColasMulti(TableView tableV,TextArea txtTe,TextArea  txtTr,TextArea txtP){
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
                aux.setTipo(data.get(i).getTipo());
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
   
   
   public void setTiempos(int tiempoL){
       numProceso++;
       trespuesta = t - tiempoL + thick;
        getTiempoEspera(tiempoL);                
                penalizacion = (double)trespuesta/(double)thick;
                System.out.println("pen: "+trespuesta+" / "+thick+" = "+penalizacion);
                sumTr += trespuesta;
                sumPe += penalizacion;
                sumTe += te;
                setTabla(tiempoL+"",thick+"",te+"",trespuesta+"",penalizacion+"",numProceso+"");
                t = t+ thick;
   }
   
    @Override
    public void run(){
        System.out.println("corriendo");
        do{
            if(!colaEspera.isEmpty()){   
                System.out.println("Cola: sistema");
                String aux = colaEspera.remove(0);
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido                
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
                cpu.add(aux );
                setTiempos(tiempoL);
      
            }else if(!colaEspera2.isEmpty()){
                System.out.println("Cola: interactivos");
                String aux = colaEspera2.remove(0);
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[2]); //tiempo requerido               
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                cpu.add(aux );
                setTiempos(tiempoL);
            }else if(!colaEspera3.isEmpty()){
                System.out.println("Cola: edición interactivos");
                String aux = colaEspera3.remove(0);
                String[] split = aux.split(":");
                 thick = Integer.parseInt(split[0]); //tiempo requerido              
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                cpu.add(aux );
                setTiempos(tiempoL);
                
            }else if(!colaEspera4.isEmpty()){
                System.out.println("Cola: lotes");
                String aux = colaEspera4.remove(0);
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[1]); //tiempo requerido                
                int tiempoL = Integer.parseInt(split[0]); //tiempo llegada
                cpu.add(aux );
                setTiempos(tiempoL);
            }             
            try {
                Thread.sleep(1000 * thick);
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
}

