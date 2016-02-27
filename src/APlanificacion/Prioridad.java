/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APlanificacion;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import static simulador.FXMLDocumentController.bandera;
import static simulador.FXMLDocumentController.cpu;
import static simulador.FXMLDocumentController.colaEspera;
/**
 *
 * @author Javier
 */
public class Prioridad extends Thread{
    int ti;
    int cont = 0;
   
    @Override
    public void run(){
         int t = 0;
   int te = 0;
        int thick = 1;
        do{
            if(!colaEspera.isEmpty()){   
                String aux = colaEspera.remove(0);
                String[] split = aux.split(":");
                thick = Integer.parseInt(split[2]); //tiempo requerido
                System.out.println("tiempo requerido = "+thick);
                int tiempoL = Integer.parseInt(split[1]); //tiempo llegada
                 System.out.println("tiempo de llegada = "+tiempoL);
                
                te = te + t - tiempoL;
                t = t+ thick;
                System.out.println("tiempo espera: "+ te);
                cpu.add(aux );
                
               
            }         
             
            try {
                Thread.sleep(1000 * thick);
            } catch (InterruptedException ex) {
                Logger.getLogger(Prioridad.class.getName()).log(Level.SEVERE, null, ex);
            }
                
               
            
        }while(bandera);
    }

}
