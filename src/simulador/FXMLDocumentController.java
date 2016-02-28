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
import APlanificacion.SJF;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable{

    public static ArrayList<String> colaEspera = new ArrayList();
    public static ArrayList<String> cpu = new ArrayList();
    public static ObservableList<Row> data = FXCollections.observableArrayList();
    Procesos pro ;
    public static boolean bandera = false;
    @FXML
    private TextArea txtTe,txtTr,txtP;
    @FXML
    public TableView tableV;
    @FXML
    TableColumn<Row, String> llegada,orden, tCPU, tPri, tTipo, te, tr, p;
    @FXML
    private RadioButton FCFS, RR, SJF, PRI, CM;
    @FXML
    private AnchorPane menu, simulacion;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        pro = new Procesos(1,tableV);
        boolean b = false;
        if (PRI.isSelected()) {
            b = true;
            tTipo.setVisible(false);
            pro = new Procesos(4,tableV); //Se manda 2 para indicar que se genran procesos con prioridad
            Prioridad pri = new Prioridad(tableV,txtTe,txtTr,txtP); //se manda tabla para poner datos
            pri.start();
        } else if (CM.isSelected()) {
            pro = new Procesos(5,tableV);
            b = true;
            pro = new Procesos(3,tableV);
        } else if (FCFS.isSelected()) {
            tTipo.setVisible(false);
            tPri.setVisible(false);
            pro = new Procesos(1,tableV);
            FCFS fc = new FCFS(tableV,txtTe,txtTr,txtP);
            fc.start();
            b = true;
        } else if (RR.isSelected()) {
            b = true;
            pro = new Procesos(2,tableV);
            RR ro = new RR();
        } else if (SJF.isSelected()) {
            pro = new Procesos(3,tableV);
            b = true;
            SJF ss = new SJF();
        }
        if(b){
        bandera = true;
        pro.start();
        menu.setVisible(false);
        simulacion.setVisible(true);
        }

    }

    @FXML
    public void stop(ActionEvent a) {
        bandera = !bandera;
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
        tr.setCellValueFactory(cellData -> cellData.getValue().trProperty());
        p.setCellValueFactory(cellData -> cellData.getValue().pProperty());
       orden.setCellValueFactory(cellData -> cellData.getValue().ordenProperty());

    }


}
