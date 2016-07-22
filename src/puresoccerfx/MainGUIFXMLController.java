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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.PropertySheet.Item;
import org.gillius.jfxutils.chart.ChartZoomManager;
import puresoccerfx.model.PlayerStatistic;
import puresoccerfx.model.ScatterChartExtraData;


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
    private TableView<PlayerItem> playerListTable;
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
    private Rectangle selectRect01;
    @FXML
    private StackPane chartPane01;
    
    private ChartZoomManager cm01;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot02;
    @FXML
    private Rectangle selectRect02;
    @FXML
    private StackPane chartPane02;
    
    private ChartZoomManager cm02;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot12;
    @FXML
    private Rectangle selectRect12;
    @FXML
    private StackPane chartPane12;
    
    private ChartZoomManager cm12;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot10;
    @FXML
    private Rectangle selectRect10;
    @FXML
    private StackPane chartPane10;
    
    private ChartZoomManager cm10;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot20;
    @FXML
    private Rectangle selectRect20;
    @FXML
    private StackPane chartPane20;
    
    private ChartZoomManager cm20;
    @FXML 
    private ScatterChart<Number,Number> scatterPlot21;
    @FXML
    private Rectangle selectRect21;
    @FXML
    private StackPane chartPane21;
    
    private ChartZoomManager cm21;
    
    @FXML
    private StackedBarChart<Number, String> similarityBarChart;
    
//    private double SCALE_DELTA = 1.1;
    
    // SearchTable observableList
    private ObservableList<PlayerItem> table_list = FXCollections.observableArrayList();
    
    // Scatter chart
//    private ObservableList<XYChart.Series<Number,Number>> chart01 = FXCollections.observableArrayList();
//    private ObservableList<XYChart.Series<Number,Number>> chart02 = FXCollections.observableArrayList();
//    private ObservableList<XYChart.Series<Number,Number>> chart12 = FXCollections.observableArrayList();
//    private ObservableList<XYChart.Series<Number,Number>> chart10 = FXCollections.observableArrayList();
//    private ObservableList<XYChart.Series<Number,Number>> chart20 = FXCollections.observableArrayList();
//    private ObservableList<XYChart.Series<Number,Number>> chart21 = FXCollections.observableArrayList();
    
    // filter and selection level of player list
    
    
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
        this.initPlayerListTable();
        //this.updateAllScatterPlot();
        this.disableStage();
    }
    
    private void updateSimilarityBarChart(){
//        CategoryAxis yAxis = new CategoryAxis();//(CategoryAxis)this.similarityBarChart.getYAxis();
//        NumberAxis xAxis = new NumberAxis();//(NumberAxis)this.similarityBarChart.getXAxis();
        this.similarityBarChart.setCategoryGap(0);
        ((CategoryAxis)this.similarityBarChart.getYAxis()).getCategories().clear();
        System.out.println("category clear");
        this.similarityBarChart.getData().clear();
        this.similarityBarChart.layout();
        System.out.println("data clear");
        XYChart.Series<Number, String> series1 =
            new XYChart.Series<Number, String>(); // shot
        XYChart.Series<Number, String> series2 =
            new XYChart.Series<Number, String>(); // pass
        XYChart.Series<Number, String> series3 =
            new XYChart.Series<Number, String>(); // dribbles
        for(PlayerItem p:config.GlobalVariable.SELECTED_PLAYER_ITEM){
            // add player similarity bar one by one
            HashMap<String, Double> tmp = new HashMap<>();
            double shoot = p.getPlayer().getStatisticByName("Open Play Shot");
            double pass = p.getPlayer().getStatisticByName("Pass");
            double dribble = p.getPlayer().getStatisticByName("Dribble");
            double total = shoot + pass + dribble;
            shoot = shoot / total * 100.0;
            pass = pass / total * 100.0;
            dribble = dribble / total * 100.0;
            tmp.put("shoot", shoot);
            tmp.put("pass", pass);
            tmp.put("dribble", dribble);
            String cat = p.getTeam_name()+" - "+p.getPlayerName();
            if((shoot+pass+dribble)==0)
                continue;
            
            ((CategoryAxis)this.similarityBarChart.getYAxis()).getCategories().add(cat);
//            this.similarityBarChart.getYAxis().
            // add data

            series1.getData().add(new XYChart.Data(tmp.get("shoot"),cat));
            series2.getData().add(new XYChart.Data(tmp.get("pass"),cat));
            series3.getData().add(new XYChart.Data(tmp.get("dribble"),cat));
                
            if(config.GlobalVariable.SELECTED_PLAYER_ITEM.indexOf(p)>65)
                break;
        }
        this.similarityBarChart.getData().addAll(series1,series2,series3);
        System.out.println("finished update similarity chart");
    }
    
    private void initAllChartManager(){
//        this.setChartManager(cm01, chartPane01, selectRect01, scatterPlot01);
//        this.setChartManager(cm02, chartPane02, selectRect02, scatterPlot02);
//        this.setChartManager(cm12, chartPane12, selectRect12, scatterPlot12);
        cm01 = new ChartZoomManager(chartPane01, selectRect01, scatterPlot01);
        
        cm01.start();
        cm02 = new ChartZoomManager(chartPane02, selectRect02, scatterPlot02);
        
        cm02.start();
        cm12 = new ChartZoomManager(chartPane12, selectRect12, scatterPlot12);
        
        cm12.start();
        
        cm10 = new ChartZoomManager(chartPane10, selectRect10, scatterPlot10);
        
        cm10.start();
        cm20 = new ChartZoomManager(chartPane20, selectRect20, scatterPlot20);
        
        cm20.start();
        cm21 = new ChartZoomManager(chartPane21, selectRect21, scatterPlot21);
        
        cm21.start();
        this.resetManagerData();
    }
    
    private void resetManagerData(){
        cm01.setData(scatterPlot01.getData());
        cm01.setX(combo1.getSelectionModel().getSelectedItem());
        cm01.setY(combo2.getSelectionModel().getSelectedItem());
        cm02.setData(scatterPlot02.getData());
        cm02.setX(combo1.getSelectionModel().getSelectedItem());
        cm02.setY(combo3.getSelectionModel().getSelectedItem());
        cm12.setData(scatterPlot12.getData());
        cm12.setX(combo2.getSelectionModel().getSelectedItem());
        cm12.setY(combo3.getSelectionModel().getSelectedItem());
        
        cm10.setData(scatterPlot10.getData());
        cm10.setX(config.DataSetConfig.AVG_PREFIX+combo2.getSelectionModel().getSelectedItem());
        cm10.setY(config.DataSetConfig.AVG_PREFIX+combo1.getSelectionModel().getSelectedItem());
        cm20.setData(scatterPlot20.getData());
        cm20.setX(config.DataSetConfig.AVG_PREFIX+combo3.getSelectionModel().getSelectedItem());
        cm20.setY(config.DataSetConfig.AVG_PREFIX+combo1.getSelectionModel().getSelectedItem());
        cm21.setData(scatterPlot21.getData());
        cm21.setX(config.DataSetConfig.AVG_PREFIX+combo3.getSelectionModel().getSelectedItem());
        cm21.setY(config.DataSetConfig.AVG_PREFIX+combo2.getSelectionModel().getSelectedItem());
    }
    
    private void initAllScatterPlot(){
        this.updateScatterPlot(scatterPlot01, combo1.getSelectionModel().getSelectedItem(), combo2.getSelectionModel().getSelectedItem());
        
        this.updateScatterPlot(scatterPlot02, combo1.getSelectionModel().getSelectedItem(), combo3.getSelectionModel().getSelectedItem());
        
        this.updateScatterPlot(scatterPlot12, combo2.getSelectionModel().getSelectedItem(), combo3.getSelectionModel().getSelectedItem());
        
        
        this.updateAvgScatterPlot(scatterPlot10, combo2.getSelectionModel().getSelectedItem(), combo1.getSelectionModel().getSelectedItem());
        this.updateAvgScatterPlot(scatterPlot20, combo3.getSelectionModel().getSelectedItem(), combo1.getSelectionModel().getSelectedItem());
        this.updateAvgScatterPlot(scatterPlot21, combo3.getSelectionModel().getSelectedItem(), combo2.getSelectionModel().getSelectedItem());
    }
    
    private void updateAllScatterPlot(){
        this.updateScatterPlot(scatterPlot01, combo1.getSelectionModel().getSelectedItem(), combo2.getSelectionModel().getSelectedItem());
        
        this.updateScatterPlot(scatterPlot02, combo1.getSelectionModel().getSelectedItem(), combo3.getSelectionModel().getSelectedItem());
        
        this.updateScatterPlot(scatterPlot12, combo2.getSelectionModel().getSelectedItem(), combo3.getSelectionModel().getSelectedItem());
        
        
        this.updateAvgScatterPlot(scatterPlot10, combo2.getSelectionModel().getSelectedItem(), combo1.getSelectionModel().getSelectedItem());
        this.updateAvgScatterPlot(scatterPlot20, combo3.getSelectionModel().getSelectedItem(), combo1.getSelectionModel().getSelectedItem());
        this.updateAvgScatterPlot(scatterPlot21, combo3.getSelectionModel().getSelectedItem(), combo2.getSelectionModel().getSelectedItem());
        
        resetManagerData();
    }
    
    private void updateAvgScatterPlot(ScatterChart<Number,Number> sc, String X, String Y){
        sc.getData().clear();
        sc.setTitle(X+" - "+Y);
        double X_max = 0;
        double Y_max = 0;
        ObservableList<XYChart.Series<Number,Number>> chart = FXCollections.observableArrayList();
        //PlayerStatistic psX = config.GlobalVariable.MAPNAMETOSTATS.get(X);
        //PlayerStatistic psY = config.GlobalVariable.MAPNAMETOSTATS.get(Y);
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        // all players
        for(Team team:config.GlobalVariable.TEAMS){
            for(Player p:team.getPlayers()){
                double x_val = ((double)p.getStatisticByName(X))/p.getAppearence();
                if(x_val>X_max)
                    X_max = x_val;
                double y_val = ((double)p.getStatisticByName(Y))/p.getAppearence();
                if(y_val>Y_max)
                    Y_max = y_val;
                XYChart.Data<Number,Number> new_data = new XYChart.Data(x_val, y_val);
                ScatterChartExtraData extra_data = new ScatterChartExtraData();
                
                extra_data.setName(p.getName());
                extra_data.setPlayer_id(team.getPlayers().indexOf(p));
                extra_data.setTeam_id(config.GlobalVariable.TEAMS.indexOf(team));
                extra_data.setX(X);
                extra_data.setY(Y);
                new_data.setExtraValue(extra_data);
//                new_data.setExtraValue(p.getName());
                // check whether player is selected
//                if (config.GlobalVariable.SELECTED_PLAYER.size() != config.GlobalVariable.FILTERED_PLAYER.size()) {
                if (config.GlobalVariable.SELECTED_PLAYER_ITEM.size() != config.GlobalVariable.FILTERED_PLAYER_ITEM.size()) {
                    boolean player_found = false;
//                    for (Player pc : config.GlobalVariable.SELECTED_PLAYER) {
                    for (PlayerItem pc : config.GlobalVariable.SELECTED_PLAYER_ITEM) {
                        if (pc.getPlayer().equals(p)) {
                            player_found = true;
                            break;
                        }
                    }
                    if (player_found) {
                        series2.getData().add(new_data);
                    } else {
                        series1.getData().add(new_data);
                    }
                } else {
                    series1.getData().add(new_data);
                }

            }
        }
        
        NumberAxis xAxis = new NumberAxis(0, X_max, X_max/5);
        //xAxis.autoRangingProperty().
        NumberAxis YAxis = new NumberAxis(0, Y_max, Y_max/5);
        
        chart.add(series1);
        chart.add(series2);
        sc.setData(chart);
        this.addTooltipToChart(sc);
//        sc.setOnMouseClicked(e -> {
//            config.GlobalVariable.CLICKEDCHARTDATA = sc.getData();
//            //sc.getTitle();
//            //sc.getData();
//            this.openFXMLWindow("/puresoccerfx/view/ScatterChartGUIFXML.fxml", X + " - " + Y + " Scatter Chart");
////            sc.getData().clear();
////            sc.getData().add(series1);
//        });
        
      

    }
    
    private void addTooltipToChart(ScatterChart<Number,Number> chart){
        for (XYChart.Series<Number, Number> s : chart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
                
                d.getNode().setOnMouseEntered(e -> {
                    int index = -1;
                    for(PlayerItem p: this.playerListTable.getItems()){
//                        index = -1;
                        if(((ScatterChartExtraData)d.getExtraValue()).isSamePlayer(p)){
                            index = this.playerListTable.getItems().indexOf(p);
                            break;
                        } 
                    }
                    final int in = index;
//                    for(index; ; index++){
//                        if(this.playerListTable.getColumns().get(0).getequals({
//                            
//                        }
//                    }
                    System.out.println("enter "+in + " "+((ScatterChartExtraData)d.getExtraValue()).getName());
//                    this.playerListTable.getSelectionModel().select(((ScatterChartExtraData)d.getExtraValue()).getPlayerItem());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            playerListTable.requestFocus();
                            playerListTable.scrollTo(in);
                            playerListTable.getSelectionModel().select(in);
                          
                            playerListTable.getSelectionModel().focus(in);
                            
//                            playerListTable.getFocusModel().focus(in);
                        }
                    });
//                    this.playerListTable.getSelectionModel().se
                    
                });
                // System.out.println(d.getNode() == null);
                DecimalFormat f = new DecimalFormat("#.##");
                Tooltip.install(d.getNode(), new Tooltip(((ScatterChartExtraData)d.getExtraValue()).getName() + "\n"
                        + ((ScatterChartExtraData)d.getExtraValue()).getX()+ ": " + f.format(d.getXValue()) + "\n"
                        + ((ScatterChartExtraData)d.getExtraValue()).getY()+ ": " + f.format(d.getYValue()) + "\n"));
            }
        }
    }
    
    private void updateScatterPlot(ScatterChart<Number,Number> sc, String X, String Y){
        sc.getData().clear();
        sc.setTitle(X+" - "+Y);
        int X_max = 0;
        int Y_max = 0;
        ObservableList<XYChart.Series<Number,Number>> chart = FXCollections.observableArrayList();
        //PlayerStatistic psX = config.GlobalVariable.MAPNAMETOSTATS.get(X);
        //PlayerStatistic psY = config.GlobalVariable.MAPNAMETOSTATS.get(Y);
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        // all players
        for(Team team:config.GlobalVariable.TEAMS){
            for(Player p:team.getPlayers()){
                int x_val = p.getStatisticByName(X);
                if(x_val>X_max)
                    X_max = x_val;
                int y_val = p.getStatisticByName(Y);
                if(y_val>Y_max)
                    Y_max = y_val;
                XYChart.Data<Number,Number> new_data = new XYChart.Data(x_val, y_val);
                // new_data.set
                
                ScatterChartExtraData extra_data = new ScatterChartExtraData();
                
                extra_data.setName(p.getName());
                extra_data.setPlayer_id(team.getPlayers().indexOf(p));
                extra_data.setTeam_id(config.GlobalVariable.TEAMS.indexOf(team));
                extra_data.setX(X);
                extra_data.setY(Y);
                new_data.setExtraValue(extra_data);
//                new_data.setExtraValue(p.getName());
                // check whether player is selected
//                if (config.GlobalVariable.SELECTED_PLAYER.size() != config.GlobalVariable.FILTERED_PLAYER.size()) {
//                    boolean player_found = false;
//                    for (Player pc : config.GlobalVariable.SELECTED_PLAYER) {
//                        if (pc.isEqual(p)) {
//                            player_found = true;
//                            break;
//                        }
//                    }
//                    if (player_found) {
//                        series2.getData().add(new_data);
//                    } else {
//                        series1.getData().add(new_data);
//                    }
//                } else {
//                    series1.getData().add(new_data);
//                }
                if (config.GlobalVariable.SELECTED_PLAYER_ITEM.size() != config.GlobalVariable.FILTERED_PLAYER_ITEM.size()) {
                    boolean player_found = false;
//                    for (Player pc : config.GlobalVariable.SELECTED_PLAYER) {
                    for (PlayerItem pc : config.GlobalVariable.SELECTED_PLAYER_ITEM) {
                        if (pc.getPlayer().equals(p)) {
                            player_found = true;
                            break;
                        }
                    }
                    if (player_found) {
                        series2.getData().add(new_data);
                    } else {
                        series1.getData().add(new_data);
                    }
                } else {
                    series1.getData().add(new_data);
                }
            }
        }
        
        NumberAxis xAxis = new NumberAxis(0, X_max, X_max/5);
        //xAxis.autoRangingProperty().
        NumberAxis YAxis = new NumberAxis(0, Y_max, Y_max/5);
        
        chart.add(series1);
        chart.add(series2);
     
        sc.setData(chart);
        this.addTooltipToChart(sc);
        
//        sc.setOnMouseClicked(e -> {
//         
////            config.GlobalVariable.CLICKEDSCATTERCHART = new ScatterChart<>(xAxis, YAxis, sc.getData());
//            config.GlobalVariable.CLICKEDCHARTDATA = sc.getData();
//            // config.GlobalVariable.CLICKEDSCATTERCHART.add(series);
//            this.openFXMLWindow("/puresoccerfx/view/ScatterChartGUIFXML.fxml", X + " - " + Y + " Scatter Chart");
////            this.updateScatterPlot(sc, X, Y);
////            sc.getData().clear();
////            sc.getData().add(series1);
//        });

    }
    
    private void setChartManager(ChartZoomManager cm, Pane chartPane, Rectangle selectRect, ScatterChart<Number,Number> sc){
        // set chart manager
//        ChartZoomManager zoomManager = new ChartZoomManager( chartPane, selectRect, sc );
        cm = new ChartZoomManager(chartPane, selectRect, sc);
//        ScatterChartExtraData extra = (ScatterChartExtraData)sc.getData().get(0).getData().get(0).getExtraValue();
//        cm.setX(extra.getX());
//        cm.setY(extra.getY());
//        cm.setData(sc.getData());
        cm.start();
    }
    
    private void initGlobalVariables(){
        config.GlobalVariable.M_STATISTIC = this.statisticsMenu;
        config.GlobalVariable.M_CUSTOMIZED = this.customizedAttributeMenu;
        config.GlobalVariable.FILTERED_PLAYER_ITEM.addListener(new ListChangeListener(){
 
            @Override
            public void onChanged(ListChangeListener.Change c) {
               // System.out.println("\nFiltered list onChanged()");
                  
//                while(c.next()){
//                    System.out.println("next: ");
//                    if(c.wasAdded()){
//                        System.out.println("- wasAdded");
//                    }
//                    if(c.wasPermutated()){
//                        System.out.println("- wasPermutated");
//                    }
//                    if(c.wasRemoved()){
//                        System.out.println("- wasRemoved");
//                    }
//                    if(c.wasReplaced()){
//                        System.out.println("- wasReplaced");
//                    }
//                    if(c.wasUpdated()){
//                        System.out.println("- wasUpdated");
//                    }
//                }
                 
                 
            }
        });
        
        config.GlobalVariable.SELECTED_PLAYER_ITEM.addListener(new ListChangeListener(){
 
            @Override
            public void onChanged(ListChangeListener.Change c) {
                //System.out.println("\nSelected list onChanged()");
                  if(config.GlobalVariable.NEED_UPDATE){
                     System.out.println("update all scatter charts");
                    updateAllScatterPlot();
                    System.out.println("update similarity bar charts");
                    updateSimilarityBarChart();
                    config.GlobalVariable.NEED_UPDATE = false;
                 }
//                while(c.next()){
//                    System.out.println("next: ");
//                    if(c.wasAdded()){
//                        System.out.println("- wasAdded");
//                    }
//                    if(c.wasPermutated()){
//                        System.out.println("- wasPermutated");
//                    }
//                    if(c.wasRemoved()){
//                        System.out.println("- wasRemoved");
//                    }
//                    if(c.wasReplaced()){
//                        System.out.println("- wasReplaced");
//                    }
//                    if(c.wasUpdated()){
//                        System.out.println("- wasUpdated");
//                    }
//                }
                 
            }
        });
 
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
    
    
    // this table view is only for selected players
    private void initPlayerListTable(){
        this.playerListTable.getColumns().clear();
        // column Player, Team
        TableColumn<PlayerItem, String> player_column = new TableColumn<>("Player");
        player_column.setCellValueFactory(new PropertyValueFactory<>("player_name"));
        TableColumn<PlayerItem, String> team_column = new TableColumn<>("Team");
        team_column.setCellValueFactory(new PropertyValueFactory<>("team_name"));
        this.playerListTable.getColumns().add(player_column);
        this.playerListTable.getColumns().add(team_column);
        // add columns from attribute
        for(String s:config.GlobalVariable.PLAYERATTRIBUTE){
            TableColumn<PlayerItem, String> column = new TableColumn<>(s);
            column.setCellValueFactory(new Callback<CellDataFeatures<PlayerItem, String>, ObservableValue<String>>(){
                @Override
                public ObservableValue<String> call(CellDataFeatures<PlayerItem, String> param) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      return new ReadOnlyObjectWrapper(String.valueOf(param.getValue().getPlayer().getStatisticByName(s)));
                }
                
            
            });
            this.playerListTable.getColumns().add(column);
        }
        this.playerListTable.setItems(config.GlobalVariable.SELECTED_PLAYER_ITEM);
    }
    
    private void updatePlayerListTable(){
        for(PlayerItem p:config.GlobalVariable.SELECTED_PLAYER_ITEM){
            for(TableColumn<PlayerItem, ?> column : this.playerListTable.getColumns()){
//                column.
            }
        }
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
    
     public void openFXMLWindow(String path, String title){
        
        try {
            Parent root;
            // System.out.println(getClass().getResource(path));
            
            root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainGUIFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            

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
            this.initAllScatterPlot();
            this.initAllChartManager();
            this.updateSimilarityBarChart();
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
        for(Team t:config.GlobalVariable.TEAMS){
            for(Player p : t.getPlayers()){
                PlayerItem new_player = new PlayerItem();
                new_player.setTeamId(config.GlobalVariable.TEAMS.indexOf(t));
                new_player.setPlayerId(t.getPlayers().indexOf(p));
                config.GlobalVariable.FILTERED_PLAYER_ITEM.add(new_player);
                config.GlobalVariable.SELECTED_PLAYER_ITEM.add(new_player);
                config.GlobalVariable.FILTERED_PLAYER.add(p);
                config.GlobalVariable.SELECTED_PLAYER.add(p);
            }
        }
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
