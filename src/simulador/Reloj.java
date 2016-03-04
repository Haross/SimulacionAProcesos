/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador;
import java.util.logging.Level;
import java.util.logging.Logger;
import static simulador.FXMLDocumentController.bandera;
import static simulador.FXMLDocumentController.t;

import javafx.scene.control.TextArea;

/**
 *
 * @author Javier
 */
public class Reloj{
    TextArea txtA;
    int cont = 0;
    Reloj(TextArea txtA){
        this.txtA = txtA;
    }
    public void setR(int c){
        txtA.setText(c+"");
    }
}
