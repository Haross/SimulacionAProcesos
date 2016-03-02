package APlanificacion;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import static simulador.FXMLDocumentController.colaEspera;
import static simulador.FXMLDocumentController.colaEspera2;
import static simulador.FXMLDocumentController.colaEspera3;
import static simulador.FXMLDocumentController.colaEspera4;
import static simulador.FXMLDocumentController.data;
import static simulador.FXMLDocumentController.bandera;
import simulador.Row;

/**
 *
 * @author Javier
 */
public class Procesos extends Thread {

    TableView tableV;
    //final double probabilidad = 0.9502;
    final double probabilidad = 0.7;
    int cont = 0;
    String proceso = "";
    int algoritmo;

    public Procesos(int opc, TableView tableV) {
        algoritmo = opc;
        this.tableV = tableV;
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

    private void setTabla(int tL, int th, int prioridad) {
        data.add(new Row(tL + "", th + "", prioridad + ""));
        tableV.setItems(data);
    }

    private void setTabla(int tL, int th) {
        try{
        data.add(new Row(tL + "", th + ""));
        tableV.setItems(data);
        }catch(Exception  e){
            System.out.println("Cacth");
             data.add(new Row(tL + "", th + ""));
        tableV.setItems(data);
            System.out.println("se añadio ");
        }
    }
      private void setTabla(int tL, int th,String tipo) {
        Row aux = new Row(tL + "", th + "");
        aux.setTipo(tipo);
        data.add(aux);
        tableV.setItems(data);
    }

    public String setDatos(double x) {
        proceso = "";
        int tL = getTiempoLlegada();
        int th = getThick();
        switch (algoritmo) {
            case 1: //FCFS
                setTabla(tL, th);
                proceso = tL + ":" + th;
                break;
            case 2: //RR
                setTabla(tL, th);
                proceso = tL + ":" + th+":N:N:N";
                colaEspera.add(proceso);
                return proceso;
            case 3: //SJF
                setTabla(tL, th);
                proceso = th + ":" + tL;
                break;
            case 4: //Prioridad
                int prioridad = getPrioridad();
                setTabla(tL, th, prioridad);
                proceso = prioridad + ":" + tL + ":" + th;
                break;
            case 5:
                
                proceso = tL + ":" + th;
                if (x <= 0.23755) { //procesos del sistema
                    setTabla(tL, th,"Sistema");
                    proceso += ":sistema";
                    colaEspera.add(proceso);
                } else if (x <= 0.4750) {
                     int prioridad1 = getPrioridad();
                    setTabla(tL, th, prioridad1);
                    proceso = prioridad1 + ":" + tL + ":" + th;
                        colaEspera2.add(proceso);
                } else if (x <= 0.7125) {
                     setTabla(tL, th);
                     proceso = th + ":" + tL;
                    colaEspera3.add(proceso);
                } else {
                    setTabla(tL, th,"Lotes");
                    proceso += ":lotes";
                    colaEspera4.add(proceso);
                }
                return proceso;
        }
        colaEspera.add(proceso);
        Collections.sort(colaEspera, comparator);
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
            }else{
                tableV.setItems(data);
            }
            try {
                Thread.sleep(2000);
                cont++;
            } catch (InterruptedException ex) {
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (bandera);
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
            return Integer.parseInt(a[0]) - Integer.parseInt(b[0]); 
        }
    };

}
