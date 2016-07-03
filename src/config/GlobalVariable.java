/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import datatype.Team;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import puresoccerfx.model.PlayerStatistic;

/**
 *
 * @author s145633
 */
public class GlobalVariable {
    public static ArrayList<Team> TEAMS = new ArrayList<>(); // store team and player infomation
    
    public static ObservableList<String> PLAYERATTRIBUTE = FXCollections.observableArrayList();
    public static ObservableList<String> CATEGORY = FXCollections.observableArrayList();
    public static ObservableList<String> ATTRIBUTE = FXCollections.observableArrayList();
    public static ObservableList<String> DEFINITION = FXCollections.observableArrayList();
    
    public static String SELECTEDPLAYERATTRIBUTE = "";
    
    public static HashMap<String, PlayerStatistic> MAPNAMETOSTATS= new HashMap<>(); 
}
