/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
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
        //this.setEditable();
    }
    
    private void initStatisticName(){
        this.playerAttributeCombobox.setEditable(true);
        
        this.playerAttributeCombobox.setItems(config.GlobalVariable.PLAYERATTRIBUTE);
        this.updateStatisticName();
        this.playerAttributeCombobox.valueProperty()
                .addListener((v, oldValue, newValue) -> {
                    //System.out.println(newValue.toString());
                    if(newValue == null){
                        config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = null;
                        this.update();
                    }
                    else if(config.GlobalVariable.PLAYERATTRIBUTE.indexOf(newValue)!= -1){
                        config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = newValue;
                        this.update();
                    }
                    //this.initApplyButton();
                    
                });
    }
    
    private void setEditable(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE == null )
            this.playerAttributeCombobox.setEditable(true);
        else if(config.GlobalVariable.MAPNAMETOSTATS.get(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE).isCustomized()||this.applyButton.getText().equals("Add"))
            this.playerAttributeCombobox.setEditable(true);
        else
            this.playerAttributeCombobox.setEditable(false);
    }
    
    private void updateStatisticName(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE != null){
            this.playerAttributeCombobox.getSelectionModel().select(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
            //this.playerAttributeCombobox.setEditable(true);
        }
        else{
            this.playerAttributeCombobox.getSelectionModel().clearSelection();
            this.playerAttributeCombobox.setPromptText("New Statistic");
            //this.playerAttributeCombobox.setEditable(true);
        }
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
//            if(ps.getCategory().equals(config.DataSetConfig.NONE))
//                this.categoryCombobox.setPromptText("Please select");
//            else
            this.categoryCombobox.getSelectionModel().select(ps.getCategory());
        }else
            this.categoryCombobox.getSelectionModel().select(config.DataSetConfig.NONE);
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
//                if(!ps.getAttribute().get(i).equals("none"))
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
//                if(!ps.getDefinition().get(i).equals("none"))
                    this.definitionCheckCombobox.getCheckModel().check(ps.getDefinition().get(i));
            }
        }
    }
    
    private void initApplyButton(){
        this.applyButton.setOnAction(e -> {this.clickApplyButton();});
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE == null){
            this.applyButton.setText("Add");
        }else{
            this.applyButton.setText("Apply");
        }
    }
    
    public void addCustomizedMenu(String name){
        MenuItem item = new MenuItem(name);
                item.setOnAction(e -> {
                    config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = item.getText();
                    this.openPlayerAttributeGUI();
                });
                config.GlobalVariable.M_CUSTOMIZED.getItems().add(config.GlobalVariable.M_CUSTOMIZED.getItems().size()-2, item);
    }
    
    public void updateCustomizedMenu(String old, String newName){
        //System.out.println(" old item: "+ old + ", new item: "+newName);
        //MenuItem old_item = new MenuItem(old);
        MenuItem item = new MenuItem(newName);
                item.setOnAction(e -> {
                    config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = item.getText();
                    this.openPlayerAttributeGUI();
                });
                int index = 0;
                for(MenuItem i : config.GlobalVariable.M_CUSTOMIZED.getItems()){
                    if(i.getText().equals(old))
                        break;
                    index++;
                }
                System.out.println("old index: "+index);
                //config.GlobalVariable.M_CUSTOMIZED.getItems().remove(index);
                config.GlobalVariable.M_CUSTOMIZED.getItems().set(index,item);
    }
    
    public void openPlayerAttributeGUI(){
        
        try {
            Parent root;
            System.out.println(getClass().getResource("/puresoccerfx/view/PlayerAttributeGUIFXML.fxml"));
            
            root = FXMLLoader.load(getClass().getResource("/puresoccerfx/view/PlayerAttributeGUIFXML.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Statitics Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainGUIFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            

    }
    
    public void clickApplyButton(){
        if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE == null){
            PlayerStatistic ps = new PlayerStatistic();
            ps.setName(this.playerAttributeCombobox.getValue());
            ps.setCategory(this.categoryCombobox.getSelectionModel().getSelectedItem());
            for(String item: this.attributeCheckCombobox.getCheckModel().getCheckedItems()){
                ps.getAttribute().add(item);
            }
            for(String item: this.definitionCheckCombobox.getCheckModel().getCheckedItems()){
                ps.getDefinition().add(item);
            }
            ps.setCustomized(true);
            config.GlobalVariable.PLAYERATTRIBUTE.add(ps.getName());
            config.GlobalVariable.MAPNAMETOSTATS.put(ps.getName(), ps);
            this.applyButton.setText("Apply");
            config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = ps.getName();
            this.addCustomizedMenu(ps.getName());
            this.update();
        }else{
            System.out.println(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
            PlayerStatistic ps = new PlayerStatistic(config.GlobalVariable.MAPNAMETOSTATS.get(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE));
            System.out.println("old name: "+ps.getName());
            if (ps.isCustomized()) {
                // remove from map
                config.GlobalVariable.MAPNAMETOSTATS.remove(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
                // category_list
                int index = config.GlobalVariable.PLAYERATTRIBUTE.indexOf(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
                
                System.out.println("getValue: "+this.playerAttributeCombobox.getValue());
                ps.setName(this.playerAttributeCombobox.getValue());
                ps.setCategory(this.categoryCombobox.getSelectionModel().getSelectedItem());
                ps.getAttribute().clear();
                for (String item : this.attributeCheckCombobox.getCheckModel().getCheckedItems()) {
                    ps.getAttribute().add(item);
                }
                ps.getDefinition().clear();
                for (String item : this.definitionCheckCombobox.getCheckModel().getCheckedItems()) {
                    ps.getDefinition().add(item);
                }
                
                // update category list
                config.GlobalVariable.PLAYERATTRIBUTE.set(index, ps.getName());
                // update map
                System.out.println("new name: "+ps.getName());
                config.GlobalVariable.MAPNAMETOSTATS.put(ps.getName(), ps);
                this.updateCustomizedMenu(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE , ps.getName());
                config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = ps.getName();
                this.update();
            }else{
                ps = config.GlobalVariable.MAPNAMETOSTATS.get(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE);
                
                ps.setCategory(this.categoryCombobox.getSelectionModel().getSelectedItem());
                ps.getAttribute().clear();
                for (String item : this.attributeCheckCombobox.getCheckModel().getCheckedItems()) {
                    ps.getAttribute().add(item);
                }
                ps.getDefinition().clear();
                for (String item : this.definitionCheckCombobox.getCheckModel().getCheckedItems()) {
                    ps.getDefinition().add(item);
                }
                this.update();
            }

        }
    }
    
    public void addStatistic(PlayerStatistic ps){
        config.GlobalVariable.MAPNAMETOSTATS.put(ps.getName(), ps);
    }
    
    public void close(){
        cancelButton.getScene().getWindow().hide();
    }
    
}
