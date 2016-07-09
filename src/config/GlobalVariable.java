/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import datatype.Round;
import datatype.Team;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import puresoccerfx.model.PlayerStatistic;

/**
 *
 * @author s145633
 */
public class GlobalVariable {
    public static ArrayList<Team> TEAMS = new ArrayList<>(); // store team and player infomation
    public static ArrayList<Round> SEASON = new ArrayList<>();
    
    public static ObservableList<String> PLAYERATTRIBUTE = FXCollections.observableArrayList();
    public static ObservableList<String> CATEGORY = FXCollections.observableArrayList();
    public static ObservableList<String> ATTRIBUTE = FXCollections.observableArrayList();
    public static ObservableList<String> DEFINITION = FXCollections.observableArrayList();
    
    public static String SELECTEDPLAYERATTRIBUTE = "";
    public static ScatterChart<Number,Number> CLICKEDSCATTERCHART;
    public static ObservableList<XYChart.Series<Number,Number>> CLICKEDCHARTDATA = FXCollections.observableArrayList();
    
    public static HashMap<String, PlayerStatistic> MAPNAMETOSTATS= new HashMap<>(); 
    public static Menu M_STATISTIC;
    public static Menu M_CUSTOMIZED;
}
