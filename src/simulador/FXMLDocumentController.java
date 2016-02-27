/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import APlanificacion.FCFS;
import APlanificacion.Prioridad;
import APlanificacion.Procesos;
import APlanificacion.RR;
import APlanificacion.SSF;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable {
    public static ArrayList<String> colaEspera = new ArrayList();
    public static ArrayList<String> cpu = new ArrayList();

    
    public static boolean bandera = false;
    @FXML
    private RadioButton FCFS,RR,SSF,PRI,CM;
    @FXML
    private AnchorPane menu,simulacion;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Procesos pro = new Procesos(1);
        if(PRI.isSelected()){
            pro = new Procesos(2); //Se manda 2 para indicar que se genran procesos con prioridad
            Prioridad pri = new Prioridad();
            pri.start();
        }else if(CM.isSelected()){
            pro = new Procesos(3);
        }else if(FCFS.isSelected()){
            FCFS al = new FCFS();
        }else if(RR.isSelected()){
            RR ro = new RR();
        }else if(SSF.isSelected()){
            SSF ss = new SSF();
        }
       bandera = true;
       pro.start();
        
    }
    @FXML 
    public void stop(ActionEvent a){
        bandera = !bandera;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       menu.setVisible(true);
       simulacion.setVisible(false);
               
       
    }    
    
}
