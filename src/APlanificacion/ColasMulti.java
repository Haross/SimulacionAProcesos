
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
    Prioridad pri;
    SJF sjf;
    FCFS fcfs;
    RR robin;
    ArrayList<String> colaEspera,colaEspera2,colaEspera3,colaEspera4;
    public ColasMulti(){
        
    }
    public ColasMulti(Prioridad pri, SJF sjf, FCFS fcfs,RR robin, TableView tableV, TableView tableCPU, TableView tableSalida, TextArea txtTe, TextArea txtTr, TextArea txtP) {
        this.tableV = tableV;
        this.tableCPU = tableCPU;
        this.tableSalida = tableSalida;
        this.txtP = txtP;
        this.txtTe = txtTe;
        this.txtTr = txtTr;
        this.sjf = sjf;
        this.pri = pri;
        this.fcfs = fcfs;
        this.robin = robin;
    }
   public void setColas(ArrayList<String> colaEspera,ArrayList<String> colaEspera2,ArrayList<String> colaEspera3,ArrayList<String> colaEspera4){
       this.colaEspera = colaEspera;
       this.colaEspera4 = colaEspera4;
       this.colaEspera2 = colaEspera2;
       this.colaEspera3 = colaEspera3;
   }
    @Override
    public void run(){
       
        do{
            System.out.println("corriendo");
             while(colaEspera.isEmpty() && colaEspera2.isEmpty()&& colaEspera3.isEmpty()&& colaEspera4.isEmpty()){
            
        }   
            
            if(!colaEspera.isEmpty()){   
                System.out.println("robin");
               robin.empezar();
            }else if(!colaEspera2.isEmpty()){
                System.out.println("pri");
              pri.empezar();
            }else if(!colaEspera3.isEmpty()){
                System.out.println("sjf");
                sjf.empezar();
            }else if(!colaEspera4.isEmpty()){
                System.out.println("fcfs");
                fcfs.empezar();
            }             
              
        }while(bandera);
        System.out.println("Hilo terminado");
    }
    public void setFinales(){
        sumTe = sjf.getTe() + robin.getTe() + fcfs.getTe() + pri.getTe();
        sumTr = sjf.getTr() + robin.getTr() + fcfs.getTr() + pri.getTr();
        sumPe = sjf.getPe() + robin.getPe() + fcfs.getPe() + pri.getPe();
        numProceso = sjf.getNum()+ robin.getNum() + fcfs.getNum() + pri.getNum();
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

