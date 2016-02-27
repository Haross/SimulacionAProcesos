/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APlanificacion;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import simulador.FXMLDocumentController;
import static simulador.FXMLDocumentController.colaEspera;
import static simulador.FXMLDocumentController.data;
import static simulador.FXMLDocumentController.bandera;
import simulador.Row;

/**
 *
 * @author Javier
 */
public class Procesos extends Thread {
    TableView tableV;
    final double probabilidad = 0.9502;
    int cont = 0;
    String proceso = "";
    int algoritmo;
    
    
    /*
    * @param 4 prioridad
    */
    public Procesos(int opc,TableView tableV){
        algoritmo = opc;
        this.tableV =tableV;
        
    }
    
    public int getThick() {
        return randInt(1, 10);
    }

    public int getPrioridad() {
        return randInt(1, 10);
    }

    public int getTiempoLlegada() {
        return cont;
    }
    private void setTabla(int tL, int th, int prioridad){
        data.add(new Row(tL+"",th+"" , prioridad+""));
                tableV.setItems(data);
    }
     private void setTabla(int tL, int th){
        data.add(new Row(tL+"",th+""));
                tableV.setItems(data);
    }
   
    public String setDatos(double x){
        proceso = "";
        switch(algoritmo){
            case 1:
                int tL1 = getTiempoLlegada();
                int th1 = getThick();
                setTabla(tL1, th1);
                return "";
            case 2:
                int prioridad = getPrioridad();
                int tL = getTiempoLlegada();
                int th = getThick();
                setTabla(tL, th,prioridad);
                proceso =  prioridad+":"+tL+":"+th;
                colaEspera.add(proceso);

                
                
                Collections.sort(colaEspera, comparator);
                return proceso;
                
            case 3:
                 if(x <= 0.23755){ //procesos del sistema
                    proceso = "sistema";
                }else if(x <= 0.4750){
                    proceso = "interactivos";
                }else if(x<=0.7125){
                    proceso = "eInteractivos";
                }else{
                    proceso = "lotes";
                }
                break;
        }
        
        return "";
        
    }
    @Override
    public void run() {
        Random rand = new Random();
        do {
            Double x = rand.nextDouble();
            //System.out.println("x:"+x);
            x = truncate(x, 4); //para tener numeros de 4 decimales
            if (x <= probabilidad) {
                proceso = setDatos(x);
                 //System.out.println("Proceso creado: "+proceso);      
            }
            try {   
                    Thread.sleep(1000);
                    cont++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                }
        } while (bandera);
        //System.out.println("proceso parado clase Procesos");

    }

    public double truncate(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = (long) value;
        return (double) tmp / factor;
    }
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
     Comparator<String> comparator = new Comparator<String>() {
		    public int compare(String c1, String c2) {
		    	String[] a = c1.split(":");
		    	String[] b = c2.split(":");
		        return Integer.parseInt(a[0]) - Integer.parseInt(b[0]); // use your logic
		    }
		};

}
