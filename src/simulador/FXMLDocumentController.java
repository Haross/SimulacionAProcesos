/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;

import APlanificacion.ColasMulti;
import APlanificacion.FCFS;
import APlanificacion.Prioridad;
import APlanificacion.Procesos;
import APlanificacion.RR;
import APlanificacion.SJF;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable{
    public static int t = 0;
    public static ArrayList<String> colaEspera = new ArrayList(); //sistema
    public static ArrayList<String> colaEspera2 = new ArrayList(); //interactivos
    public static ArrayList<String> colaEspera3 = new ArrayList(); //edición interactivos
    public static ArrayList<String> colaEspera4 = new ArrayList(); //lotes
    public static ArrayList<String> cpu = new ArrayList();
    public static ObservableList<Row> data = FXCollections.observableArrayList();
    public static ObservableList<Row> data1 = FXCollections.observableArrayList();
    public static ObservableList<Row> data2 = FXCollections.observableArrayList();
    Procesos pro ;
    public static boolean bandera = false;
    @FXML 
    private Text txt;
    @FXML
    private TextArea txtTe,txtTr,txtP;
    @FXML
    private TextField txtQ;
    @FXML
    public TableView tableV,tableCPU,tableSalida;
    @FXML
    TableColumn<Row, String> llegada,orden, tCPU, tPri,tSali, tTipo,tRestante, te, tr, p,
            llegada11,orden11, tCPU11, tPri11, tTipo11, te11, tr11, p11,
            llegada1,orden1, tCPU1, tPri1, tTipo1, te1, tr1, p1;
    @FXML
    private RadioButton FCFS, RR, SJF, PRI, CM;
    @FXML
    private AnchorPane menu, simulacion;
SJF ss;
RR ro; 
Prioridad pri;
FCFS fc;
ColasMulti cm;

@FXML
private void seleccionado(){
    if (RR.isSelected()){
        txt.setVisible(true);
        txtQ.setVisible(true);
    }
}
@FXML
private void seleccionadoC(){
    if (CM.isSelected()){
        txt.setVisible(true);
        txtQ.setVisible(true);
    }
}
    @FXML
    private void handleButtonAction(ActionEvent event) {
        pro = new Procesos(1,tableV);
        boolean b = false;
        if (PRI.isSelected()) {
            b = true;
            tTipo.setVisible(false);
            tTipo1.setVisible(false);
            tTipo11.setVisible(false);
            pro = new Procesos(4,tableV); //Se manda 4 para indicar que se genran procesos con prioridad
            pri = new Prioridad(colaEspera,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP); //se manda tabla para poner datos
            pri.start();
                bandera = true;
        pro.start();
        menu.setVisible(false);
        simulacion.setVisible(true);
        } else if (CM.isSelected()) {
            
            tPri.setVisible(false);
            tPri11.setVisible(false);
            tPri1.setVisible(false);
            pro = new Procesos(5,tableV);
             //validar solo numeros

            int quantum = Integer.parseInt(txtQ.getText());
            ro = new RR(colaEspera,quantum,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            fc = new FCFS(colaEspera4,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            ss = new SJF(colaEspera3,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            pri = new Prioridad(colaEspera2,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            cm = new ColasMulti(pri,ss,fc,ro,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            cm.setColas(colaEspera,colaEspera2,colaEspera3,colaEspera4);
            bandera = true;
        pro.start();
            menu.setVisible(false);
        simulacion.setVisible(true);
            cm.start();
         
    
        } else if (FCFS.isSelected()) {
            tTipo.setVisible(false);
            tPri.setVisible(false);
            tTipo1.setVisible(false);
            tPri1.setVisible(false);
            tTipo11.setVisible(false);
            tPri11.setVisible(false);
            pro = new Procesos(1,tableV);
            fc = new FCFS(colaEspera,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            fc.start();
               bandera = true;
        pro.start();
        menu.setVisible(false);
        simulacion.setVisible(true);
        } else if (RR.isSelected()) {
       tTipo.setVisible(false);
            tPri.setVisible(false);
            tTipo1.setVisible(false);
            tPri1.setVisible(false);
            tTipo11.setVisible(false);
            tPri11.setVisible(false);
            pro = new Procesos(2,tableV);
            //validar solo numeros
            int quantum = Integer.parseInt(txtQ.getText());
             ro = new RR(colaEspera,quantum,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
              bandera = true;
             ro.start();
              
        pro.start();
        menu.setVisible(false);
        simulacion.setVisible(true);
        } else if (SJF.isSelected()) {
            tTipo.setVisible(false);
            tPri.setVisible(false);
            tTipo1.setVisible(false);
            tPri1.setVisible(false);
            tTipo11.setVisible(false);
            tPri11.setVisible(false);
            pro = new Procesos(3,tableV);
 
             ss = new SJF(colaEspera,tableV,tableCPU,tableSalida,txtTe,txtTr,txtP);
            ss.start();
                bandera = true;
        pro.start();
        menu.setVisible(false);
        simulacion.setVisible(true);
        }
  

    }

    @FXML
    public void stop(ActionEvent a) {
        bandera = !bandera;
        if (PRI.isSelected()) {
            pri.setFinales();
        } else if (CM.isSelected()) {
           cm.setFinales();
        } else if (FCFS.isSelected()) {           
            fc.setFinales();        
        } else if (RR.isSelected()) {         
            ro.setFinales();
             
        } else if (SJF.isSelected()) {         
            ss.setFinales();
        }
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menu.setVisible(true);
        simulacion.setVisible(false);

        llegada.setCellValueFactory(cellData -> cellData.getValue().tiempoLlegadaProperty());
        tCPU.setCellValueFactory(cellData -> cellData.getValue().tiempoRequeridoProperty());
        tPri.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        tTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        te.setCellValueFactory(cellData -> cellData.getValue().teProperty());
          tSali.setCellValueFactory(cellData -> cellData.getValue().tSaliProperty());
        tr.setCellValueFactory(cellData -> cellData.getValue().trProperty());
        p.setCellValueFactory(cellData -> cellData.getValue().pProperty());
       orden.setCellValueFactory(cellData -> cellData.getValue().ordenProperty());
       tRestante.setCellValueFactory(cellData -> cellData.getValue().tRestanteProperty());
        llegada11.setCellValueFactory(cellData -> cellData.getValue().tiempoLlegadaProperty());
        tCPU11.setCellValueFactory(cellData -> cellData.getValue().tiempoRequeridoProperty());
        tPri11.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        tTipo11.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        te11.setCellValueFactory(cellData -> cellData.getValue().teProperty());
        tr11.setCellValueFactory(cellData -> cellData.getValue().trProperty());
        p11.setCellValueFactory(cellData -> cellData.getValue().pProperty());
       orden11.setCellValueFactory(cellData -> cellData.getValue().ordenProperty());

        llegada1.setCellValueFactory(cellData -> cellData.getValue().tiempoLlegadaProperty());
        tCPU1.setCellValueFactory(cellData -> cellData.getValue().tiempoRequeridoProperty());
        tPri1.setCellValueFactory(cellData -> cellData.getValue().prioridadProperty());
        tTipo1.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        te1.setCellValueFactory(cellData -> cellData.getValue().teProperty());
        tr1.setCellValueFactory(cellData -> cellData.getValue().trProperty());
        p1.setCellValueFactory(cellData -> cellData.getValue().pProperty());
       orden1.setCellValueFactory(cellData -> cellData.getValue().ordenProperty());

    }

}
