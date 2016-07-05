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
import functions.LoadSettings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puresoccerfx.model.PlayerStatistic;


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
    @FXML
    private SplitPane leftSplitPane;
    @FXML
    private SplitPane middleSplitPane;
    @FXML
    private SplitPane rightSplitPane;
    @FXML 
    private Menu statisticsMenu;
    @FXML
    private Menu customizedAttributeMenu;
    @FXML
    private ComboBox<String> combo1;
    @FXML
    private ComboBox<String> combo2;
    @FXML
    private ComboBox<String> combo3;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot01;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot02;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot12;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot10;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot20;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot21;
    
    
    
    // SearchTable observableList
    private ObservableList<PlayerItem> table_list = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number,Number>> chart01 = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number,Number>> chart02 = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number,Number>> chart12 = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number,Number>> chart10 = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number,Number>> chart20 = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number,Number>> chart21 = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.loadingProjectData();
        this.initStatusBar();
        this.initSearchTextField();
        this.initPlayerSearchTable();
        this.initGlobalVariables();
        this.initMenus();
        this.initAttributeComboBox(combo1,0);
        this.initAttributeComboBox(combo2,1);
        this.initAttributeComboBox(combo3,2);
        //this.updateAllScatterPlot();
        this.disableStage();
    }
    
    private void updateAllScatterPlot(){
        this.updateScatterPlot(scatterPlot01, combo1.getSelectionModel().getSelectedItem(), combo2.getSelectionModel().getSelectedItem());
        this.updateScatterPlot(scatterPlot02, combo1.getSelectionModel().getSelectedItem(), combo3.getSelectionModel().getSelectedItem());
        this.updateScatterPlot(scatterPlot12, combo2.getSelectionModel().getSelectedItem(), combo3.getSelectionModel().getSelectedItem());
        
        this.updateAvgScatterPlot(scatterPlot10, combo2.getSelectionModel().getSelectedItem(), combo1.getSelectionModel().getSelectedItem());
        this.updateAvgScatterPlot(scatterPlot20, combo3.getSelectionModel().getSelectedItem(), combo1.getSelectionModel().getSelectedItem());
        this.updateAvgScatterPlot(scatterPlot21, combo3.getSelectionModel().getSelectedItem(), combo2.getSelectionModel().getSelectedItem());
    }
    
    private void updateAvgScatterPlot(ScatterChart<Number,Number> sc, String X, String Y){
        sc.getData().clear();
        sc.setTitle(X+" - "+Y);
        double X_max = 0;
        double Y_max = 0;
        //PlayerStatistic psX = config.GlobalVariable.MAPNAMETOSTATS.get(X);
        //PlayerStatistic psY = config.GlobalVariable.MAPNAMETOSTATS.get(Y);
        XYChart.Series series1 = new XYChart.Series();
        // all players
        for(Team team:config.GlobalVariable.TEAMS){
            for(Player p:team.getPlayers()){
                double x_val = ((double)p.getStatisticByName(X))/p.getAppearence();
                if(x_val>X_max)
                    X_max = x_val;
                double y_val = ((double)p.getStatisticByName(Y))/p.getAppearence();
                if(y_val>Y_max)
                    Y_max = y_val;
                series1.getData().add(new XYChart.Data(x_val, y_val));
            }
        }
        
        NumberAxis xAxis = new NumberAxis(0, X_max, X_max/5);
        //xAxis.autoRangingProperty().
        NumberAxis YAxis = new NumberAxis(0, Y_max, Y_max/5);
        
        sc.getData().add(series1);
    }
    
    private void updateScatterPlot(ScatterChart<Number,Number> sc, String X, String Y){
        sc.getData().clear();
        sc.setTitle(X+" - "+Y);
        int X_max = 0;
        int Y_max = 0;
        //PlayerStatistic psX = config.GlobalVariable.MAPNAMETOSTATS.get(X);
        //PlayerStatistic psY = config.GlobalVariable.MAPNAMETOSTATS.get(Y);
        XYChart.Series series1 = new XYChart.Series();
        // all players
        for(Team team:config.GlobalVariable.TEAMS){
            for(Player p:team.getPlayers()){
                int x_val = p.getStatisticByName(X);
                if(x_val>X_max)
                    X_max = x_val;
                int y_val = p.getStatisticByName(Y);
                if(y_val>Y_max)
                    Y_max = y_val;
                series1.getData().add(new XYChart.Data(x_val, y_val));
            }
        }
        
        NumberAxis xAxis = new NumberAxis(0, X_max, X_max/5);
        //xAxis.autoRangingProperty().
        NumberAxis YAxis = new NumberAxis(0, Y_max, Y_max/5);
        
        sc.getData().add(series1);
    }
    
    private void initGlobalVariables(){
        config.GlobalVariable.M_STATISTIC = this.statisticsMenu;
        config.GlobalVariable.M_CUSTOMIZED = this.customizedAttributeMenu;
    }
    
    private void loadingProjectData(){
        LoadSettings loader = new LoadSettings();
        File file = new File(config.ProjectSetting.CATEGORYFILEPATH);
        System.out.println(file.canRead());
        loader.setFile(file);
        // System.out.println(loader.getFile().getAbsoluteFile());
        config.GlobalVariable.CATEGORY = loader.readList();
        file = new File(config.ProjectSetting.ATTRIBUTEFILEPATH);
        loader.setFile(file);
        config.GlobalVariable.ATTRIBUTE = loader.readList();
        file = new File(config.ProjectSetting.DEFINITIONFILEPATH);
        loader.setFile(file);
        config.GlobalVariable.DEFINITION = loader.readList();
        file = new File(config.ProjectSetting.STATISTICFILEPATH);
        loader.setFile(file);
        config.GlobalVariable.MAPNAMETOSTATS = loader.readStatisticList();
        System.out.println(config.GlobalVariable.MAPNAMETOSTATS.size());
    }
    
    private void initMenus(){
        this.initSettingsMenu();
    }
    
    private void initSettingsMenu(){
        this.initStatisticsMenu();
    }
    
    private void initStatisticsMenu(){
        //System.out.println(config.GlobalVariable.MAPNAMETOSTATS.values().size());
//        for(PlayerStatistic ps:config.GlobalVariable.MAPNAMETOSTATS.values()){
//            System.out.println(ps.getName());
//            if(ps.isCustomized()){
//                MenuItem item = new MenuItem(ps.getName());
//                item.setOnAction(e -> {
//                    config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = item.getText();
//                    this.openPlayerAttributeGUI();
//                });
//                this.customizedAttributeMenu.getItems().add(this.customizedAttributeMenu.getItems().size()-2, item);
//            }else{
//                MenuItem item = new MenuItem(ps.getName());
//                item.setOnAction(e -> {
//                    config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = item.getText();
//                    this.openPlayerAttributeGUI();
//                });
//                //System.out.println("smenu size: "+ this.statisticsMenu.getItems().size());
//                this.statisticsMenu.getItems().add(this.statisticsMenu.getItems().size()-2, item);
//            }
//        }
        for(String s:config.GlobalVariable.PLAYERATTRIBUTE){
//            System.out.println(ps.getName());
            PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(s);
            if(ps.isCustomized()){
                MenuItem item = new MenuItem(ps.getName());
                item.setOnAction(e -> {
                    config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = item.getText();
                    this.openPlayerAttributeGUI();
                });
                this.customizedAttributeMenu.getItems().add(this.customizedAttributeMenu.getItems().size()-2, item);
            }else{
                MenuItem item = new MenuItem(ps.getName());
                item.setOnAction(e -> {
                    config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = item.getText();
                    this.openPlayerAttributeGUI();
                });
                //System.out.println("smenu size: "+ this.statisticsMenu.getItems().size());
                this.statisticsMenu.getItems().add(this.statisticsMenu.getItems().size()-2, item);
            }
        }
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
    
    private void initAttributeComboBox(ComboBox cb, int index){
        cb.setItems(config.GlobalVariable.PLAYERATTRIBUTE);
        cb.getSelectionModel().select(index);
        cb.valueProperty()
                .addListener((e, oldVal, newVal)->{
                    this.updateAllScatterPlot();
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
    
    public void disableStage(){
        this.leftSplitPane.setDisable(true);
        this.middleSplitPane.setDisable(true);
        this.rightSplitPane.setDisable(true);
    }
    
    public void enableStage(){
        this.leftSplitPane.setDisable(false);
        this.middleSplitPane.setDisable(false);
        this.rightSplitPane.setDisable(false);
    }
    
    public void openAboutGUI(){
        
        try {
            Parent root;
            System.out.println(getClass().getResource("/puresoccerfx/view/AboutGUIFXML.fxml"));
            
            root = FXMLLoader.load(getClass().getResource("/puresoccerfx/view/AboutGUIFXML.fxml"));
            Stage stage = new Stage();
            stage.setTitle("About " + config.VersionInfo.NAME);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainGUIFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            

    }
    
    public void openNewStatisticGUI(){
        config.GlobalVariable.SELECTEDPLAYERATTRIBUTE = null;
        this.openPlayerAttributeGUI();
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
    
    public void openFile(){
        File file = getSelectedFile();
        if(file!=null){
            this.setStatusBarLabel("Loading " + file.getName() + " ...");
            loadDataFromFile(file);
            this.enableStage();
            this.setStatusBarLabel(file.getName() + " loaded");
            // System.out.println(config.GlobalVariable.TEAMS.size());
            this.updatePlayerTreeView();
            this.searchPlayer();
            this.updateAllScatterPlot();
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
                        if(!newValue.getValue().isTeam()){
                            newValue.getValue().getPlayer().printEvents();
                            if(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE!=null)
                                System.out.println(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE+": "+newValue.getValue().getPlayer().getStatisticByName(config.GlobalVariable.SELECTEDPLAYERATTRIBUTE));
                        }
                        
                    }
                });
    }
    
    public void saveSettings() {
        //File file = new File(config.ProjectSetting.STATISTICFILEPATH);
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(config.ProjectSetting.STATISTICFILEPATH), "utf-8"));
            for(String s : config.GlobalVariable.PLAYERATTRIBUTE){
                PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(s);
                String line =ps.getName()+","+ps.getCategory()+",";
                for(String s1:ps.getAttribute()){
                    line+=s1+"|";
                }
                line+=",";
                for(String s1:ps.getDefinition()){
                    line+=s1+"|";
                }
                line+=",";
                if(ps.isCustomized())
                    line+="true";
                else
                    line+="false";
                line+=",\n";
                System.out.print(line);
                writer.write(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            // report
        } 
        
    }
    
}
