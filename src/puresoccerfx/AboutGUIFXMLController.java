/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author s145633
 */
public class AboutGUIFXMLController implements Initializable {

    @FXML
    private Button okbutton;
    @FXML
    private TextArea textarea;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.setAboutContent();
    }    
    
    public void setAboutContent(){
        String text = "";
        text += config.VersionInfo.NAME + "\n"
                +"Version: " + config.VersionInfo.VERSION + "\n"
                +"Author: " + config.VersionInfo.AUTHOR + "\n"
                +"Date: " + config.VersionInfo.DATE;
        textarea.setText(text);
    }
    
    public void close(){
        okbutton.getScene().getWindow().hide();
    }
    
}
