/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import APlanificacion.Prioridad;
import APlanificacion.Procesos;
import APlanificacion.ProcesosNodo;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable {
    public static ArrayList<String> colaEspera = new ArrayList();
    public static ArrayList<String> cpu = new ArrayList();

    
    public static boolean bandera = false;
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        bandera = !bandera;
        if(bandera){
            Procesos pro = new Procesos(4);
       pro.start();
        Prioridad pri = new Prioridad();
        pri.start();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       
    }    
    
}
