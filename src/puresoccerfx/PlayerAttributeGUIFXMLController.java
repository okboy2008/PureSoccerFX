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
import javafx.scene.control.ComboBox;
import org.controlsfx.control.CheckComboBox;
import puresoccerfx.model.PlayerStatistic;

/**
 * FXML Controller class
 *
 * @author s145633
 */
public class PlayerAttributeGUIFXMLController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button applyButton;
    @FXML
    private ComboBox<String> playerAttributeCombobox;
    @FXML
    private ComboBox<String> categoryCombobox;
    @FXML
    private CheckComboBox<String> attributeCheckCombobox;
    @FXML
    private CheckComboBox<String> definitionCheckCombobox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.init();
    }   
    
    private void init(){
        this.initStatisticName();
        this.initCategory();
        this.initAttribute();
        this.initDefinition();
        this.initApplyButton();
    }
    
    private void update(){
        this.clearChecked();
        this.updateStatisticName();
        this.updateCategory();
        this.updateAttribute();
        this.updateDefinition();
        this.initApplyButton();
    }
    
    private void initStatisticName(){
        this.playerAttributeCombobox.setEditable(true);
        
        this.playerAttributeCombobox.setItems(config.GlobalVariable.PLAYERATTRIBUTE);
        this.updateStatisticName();
        this.playerAttributeCombobox.valueProperty()
                .addListener((v, oldValue, newValue) -> {
                    if(newValue.equals("New Statistic"))
                        config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = null;
                    else
                        config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = newValue;
                    
                    this.update();
                });
    }
    
    private void updateStatisticName(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE != null)
            this.playerAttributeCombobox.getSelectionModel().select(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
        else
            this.playerAttributeCombobox.setPromptText("New Statistic");
    }
    
    private void clearChecked(){
        this.attributeCheckCombobox.getCheckModel().clearChecks();
        this.definitionCheckCombobox.getCheckModel().clearChecks();
    }
    
    private void initCategory(){
        this.categoryCombobox.setItems(config.GlobalVariable.CATEGORY);
        this.updateCategory();
        
    }
    
    private void updateCategory(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE != null){
            PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
            if(ps.getCategory().equals("none"))
                this.categoryCombobox.setPromptText("Please select");
            else
                this.categoryCombobox.getSelectionModel().select(ps.getCategory());
        }else
            this.categoryCombobox.setPromptText("Please select");
    }
    
    private void initAttribute(){
        this.attributeCheckCombobox.getItems().addAll(config.GlobalVariable.ATTRIBUTE);
        this.updateAttribute();
    }
    
    private void updateAttribute(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE != null){
            PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
            for(int i=0;i<ps.getAttribute().size();i++){
                System.out.println("attribute"+i+": "+ps.getAttribute().get(i));
                if(!ps.getAttribute().get(i).equals("none"))
                    this.attributeCheckCombobox.getCheckModel().check(ps.getAttribute().get(i));
            }
        }
    }
    
    private void initDefinition(){
        this.definitionCheckCombobox.getItems().addAll(config.GlobalVariable.DEFINITION);
        this.updateDefinition();
    }
    
    private void updateDefinition(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE != null){
            PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
            for(int i=0;i<ps.getDefinition().size();i++){
                if(!ps.getDefinition().get(i).equals("none"))
                    this.definitionCheckCombobox.getCheckModel().check(ps.getDefinition().get(i));
            }
        }
    }
    
    private void initApplyButton(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE == null){
            this.applyButton.setText("Add");
        }else{
            this.applyButton.setText("Apply");
        }
    }
    
    public void addStatistic(PlayerStatistic ps){
        config.GlobalVariable.MAPNAMETOSTATS.put(ps.getName(), ps);
    }
    
    public void close(){
        cancelButton.getScene().getWindow().hide();
    }
    
}
