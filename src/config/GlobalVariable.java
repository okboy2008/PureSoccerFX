/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import datatype.Player;
import datatype.Round;
import datatype.Team;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import puresoccerfx.model.Filter;
import puresoccerfx.model.PlayerItem;
import puresoccerfx.model.PlayerStatistic;

/**
 *
 * @author s145633
 */
public class GlobalVariable {
    public static ArrayList<Team> TEAMS = new ArrayList<>(); // store team and player infomation
    public static ArrayList<Round> SEASON = new ArrayList<>(); // store original match events
    
    // Filter level - after applying filter
    public static ObservableList<Player> FILTERED_PLAYER = FXCollections.observableArrayList();
    public static ObservableList<PlayerItem> FILTERED_PLAYER_ITEM = FXCollections.observableArrayList();
    // Selction level - after applying selection in scatter plot matrix
    public static ObservableList<Player> SELECTED_PLAYER = FXCollections.observableArrayList();
    public static ObservableList<PlayerItem> SELECTED_PLAYER_ITEM = FXCollections.observableArrayList();
    public static boolean NEED_UPDATE = false;
    
    // player statistic settings
    public static ObservableList<String> PLAYERATTRIBUTE = FXCollections.observableArrayList();
    public static ObservableList<String> CATEGORY = FXCollections.observableArrayList();
    public static ObservableList<String> ATTRIBUTE = FXCollections.observableArrayList();
    public static ObservableList<String> DEFINITION = FXCollections.observableArrayList();
    
    public static String SELECTEDPLAYERATTRIBUTE = "";
    
    // copy of clicked small scatter plot
    public static ScatterChart<Number,Number> CLICKEDSCATTERCHART;
    public static ObservableList<XYChart.Series<Number,Number>> CLICKEDCHARTDATA = FXCollections.observableArrayList();
    
    // selected area from scatter plot
    public static double MIN_X = -1.0;
    public static double MIN_Y = -1.0;
    public static double MAX_X = -1.0;
    public static double MAX_Y = -1.0;
    
    public static HashMap<String, PlayerStatistic> MAPNAMETOSTATS= new HashMap<>(); 
    public static HashMap<String, Filter> MAPSCATTERTOFILTER = new HashMap<>();
    public static Menu M_STATISTIC;
    public static Menu M_CUSTOMIZED;
}
