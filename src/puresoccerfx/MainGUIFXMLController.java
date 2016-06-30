/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx;

import datatype.Player;
import puresoccerfx.model.PlayerItem;
import datatype.Team;
import functions.LoadCSVdata;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;


/**
 *
 * @author s145633
 */
public class MainGUIFXMLController implements Initializable {  
    
    // JavaFX components
    @FXML
    private TreeView<PlayerItem> PlayerTreeView;
    @FXML
    private TableView<PlayerItem> PlayerSearchTable;
    @FXML
    private Label StatusBarLabel;
    @FXML
    private TextField SearchTextField;
    
    // SearchTable observableList
    private ObservableList<PlayerItem> table_list = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.initStatusBar();
        this.initSearchTextField();
        this.initPlayerSearchTable();
    }

    private void initStatusBar(){
        this.setStatusBarLabel("Ready");
    }
    
    private void initSearchTextField(){
        SearchTextField.textProperty()
                .addListener((v, oldValue, newValue) -> {
                    this.searchPlayer();
                });
    }
    
    private void initPlayerSearchTable(){
        TableColumn<PlayerItem, String> team_column = new TableColumn<>("Team");
        team_column.setCellValueFactory(new PropertyValueFactory<>("team_name"));
        team_column.setMinWidth(120);
        TableColumn<PlayerItem, String> player_column = new TableColumn<>("Player");
        player_column.setCellValueFactory(new PropertyValueFactory<>("player_name"));
        player_column.setMinWidth(150);
        this.PlayerSearchTable.getColumns().clear();
        this.PlayerSearchTable.getColumns().addAll(team_column, player_column);
        this.PlayerSearchTable.setItems(table_list);
        this.PlayerSearchTable.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) ->{
                    if(newValue!=null){
                        System.out.println(newValue.getTeamName()+", "+newValue.getPlayerName());
                    }
                });
    }
    
    public void searchPlayer(){
        this.clearSearchResultTable();
        for(Team t:config.GlobalVariable.TEAMS){
            for(Player p:t.getPlayers()){
                if(isMatch(p.getName(), SearchTextField.getText())){
                    PlayerItem new_player = new PlayerItem();
                    new_player.setTeamId(config.GlobalVariable.TEAMS.indexOf(t));
                    new_player.setPlayerId(t.getPlayers().indexOf(p));
                    
                    this.updateSearchResultTable(new_player);
                }
            }
        }
    }
    
    private void clearSearchResultTable(){
        this.table_list.clear();
    }
    
    private boolean isMatch(String s1, String s2){
        String ss1[] = s1.split(" ");
        String ss2[] = s2.split(" ");
        boolean flag = true;
        if (ss1.length < ss2.length) {
            return false;
        }

        for (int i = 0; i < ss2.length; i++) {
            
            if (!ss1[i].toUpperCase().startsWith(ss2[i].toUpperCase())) {
                flag = false;
                break;
            }
        }
        if(!flag && ss1.length!=ss2.length){
            flag = isMatch(s1.substring(ss1[0].length()+1),s2);
        }
        
        return flag;
    }
    
    private void updateSearchResultTable(PlayerItem item){
        //System.out.println(item.toString());
        table_list.add(item);
    }
    
    public void openFile(){
        File file = getSelectedFile();
        if(file!=null){
            this.setStatusBarLabel("Loading " + file.getName() + " ...");
            loadDataFromFile(file);
            this.setStatusBarLabel(file.getName() + " loaded");
            // System.out.println(config.GlobalVariable.TEAMS.size());
            this.updatePlayerTreeView();
        }
    }

    private void setStatusBarLabel(String text){
        StatusBarLabel.setText(text);
    }
    
    private File getSelectedFile(){
        // ExtensionFilter filter = new ExtensionFilter("soccer data files", "csv");
        
        FileChooser chooser = new FileChooser();
        
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("csv", "*.csv")
            );
        chooser.setTitle("Choose data set to open");
        
        return chooser.showOpenDialog(null);
    }
    
    private void loadDataFromFile(File file){
        LoadCSVdata csv = new LoadCSVdata(file);
        config.GlobalVariable.TEAMS = csv.getTeamList();
    }
    
    private void updatePlayerTreeView(){
        
        
        TreeItem<PlayerItem> root = new TreeItem<>(new PlayerItem());
        root.setExpanded(true);
        // add team
        for(int i = 0; i<config.GlobalVariable.TEAMS.size(); i++){
            PlayerItem team = new PlayerItem();
            team.setTeamId(i);
            TreeItem<PlayerItem> t = new TreeItem<>(team);
            // add player
            for(int j = 0; j<config.GlobalVariable.TEAMS.get(i).getPlayers().size(); j++){
                PlayerItem player = new PlayerItem();
                player.setTeamId(i);
                player.setPlayerId(j);
                TreeItem<PlayerItem> p = new TreeItem<>(player);
                t.getChildren().add(p);
            }
            root.getChildren().add(t);
        }
        this.PlayerTreeView.setRoot(root);
        this.PlayerTreeView.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if(newValue!=null){
                        System.out.println(newValue.getValue().toString());
                    }
                });
    }
    
}
