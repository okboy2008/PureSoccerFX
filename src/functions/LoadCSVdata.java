/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import datatype.Coordinate;
import datatype.Event;
import datatype.Player;
import datatype.ShotEvent;
import datatype.Team;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.commons.csv.*;

/**
 *
 * @author s145633
 */
public class LoadCSVdata {
    CSVParser parser = null;
    File file;
    // private ArrayList<Team> teams;
    
    public LoadCSVdata(File path){
        this.file = path;
    }
    
    public ArrayList<Team> getTeamList(){
        ArrayList<Team> teams = new ArrayList<>();
        try{
           
            System.out.println(file.toString());
            parser = CSVParser.parse(file, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
//            parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : parser) {
                
                // skip first record
                if(csvRecord.getRecordNumber() == 1)
                    continue;
                
                // skip "own goal for" event
                if(csvRecord.get(config.DataSetConfig.CATEGORY).equals(config.DataSetConfig.C_OWNGOALFOR))
                    continue;
                
                // check if team has been added
                int team_index = -1;
                for (Team team : teams) {
                    if(team.getName().equals(csvRecord.get(config.DataSetConfig.TEAM))){
                        team_index = teams.indexOf(team);
                        break;
                    } 
                }
                // new team found
                if(team_index == -1){
                    Team new_team = new Team(csvRecord.get(config.DataSetConfig.TEAM));
                    teams.add(new_team);
                    team_index = teams.indexOf(new_team);
                }
                
                // check if player has been added
                int player_index = -1;
                for(Player player : teams.get(team_index).getPlayers()){
                    if(player.getName().equals(csvRecord.get(config.DataSetConfig.PLAYER))){
                        player_index = teams.get(team_index).getPlayers().indexOf(player);
                        break;
                    }
                }
                // new player found
                if(player_index == -1){
                    Player new_player = new Player(csvRecord.get(config.DataSetConfig.PLAYER));
                    teams.get(team_index).addPlayer(new_player);
                    player_index = teams.get(team_index).getPlayers().indexOf(new_player);
                }
                // add player events
                Coordinate c = new Coordinate(Double.parseDouble(csvRecord.get(config.DataSetConfig.LOCATIONX)),Double.parseDouble(csvRecord.get(config.DataSetConfig.LOCATIONY)));
                String opponent = csvRecord.get(config.DataSetConfig.OPPONENT);
                String[] two_teams = opponent.split(" - ");
                boolean isHome = false;
                if(two_teams[0].equals(csvRecord.get(config.DataSetConfig.TEAM))){
                    isHome = true;
                    opponent = two_teams[1];
                }else{
                    opponent = two_teams[0];
                }
                Event event = new Event(team_index, player_index, c,Integer.parseInt(csvRecord.get(config.DataSetConfig.TIME)),csvRecord.get(config.DataSetConfig.HALF).equals("1"),Integer.parseInt(csvRecord.get(config.DataSetConfig.ROUND)),opponent,isHome);
                /////////////////////////////////////////////////////////////////////////////////////////////
                // add shot events
                /////////////////////////////////////////////////////////////////////////////////////////////
                
                // shot attempt event
                if(csvRecord.get(config.DataSetConfig.CATEGORY).equals(config.DataSetConfig.C_GOALATTEMPT)){
                    ShotEvent new_shot_event = new ShotEvent(event);
                    new_shot_event.setEventType(config.EventTypeDefinition.SHOT);
                    new_shot_event.setIsFreekick(false);
                    new_shot_event.setIsPenalty(false);
                    // shoot by where
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_BODY))
                        new_shot_event.setShootByWhere(config.ShotDefinition.BODY);
                    else if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_HEAD))
                        new_shot_event.setShootByWhere(config.ShotDefinition.HEAD);
                    else if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_LEFTFOOT))
                        new_shot_event.setShootByWhere(config.ShotDefinition.LEFTFOOT);
                    else
                        new_shot_event.setShootByWhere(config.ShotDefinition.RIGHTFOOT);
                    
                    // is shot blocked
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_BLOCKED)){
                        new_shot_event.setIsShotBlocked(true);
                        new_shot_event.setIsShotOnTarget(false);
                    }else
                        new_shot_event.setIsShotBlocked(false);
                    // is shot off target
                    if(csvRecord.get(config.DataSetConfig.DEFINITION).contains(config.DataSetConfig.D_ISSHOTOFFTARGET)){
                        new_shot_event.setIsShotOnTarget(false);
                    }else
                        new_shot_event.setIsShotOnTarget(true);
                    // is shot goal
                     if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_ISGOAL)){
                        new_shot_event.setIsShotGoal(true);
                    }else
                        new_shot_event.setIsShotGoal(false);
                     
                     teams.get(team_index).getPlayerByIndex(player_index).addShotEvent(new_shot_event);
                }
                // freekick
                if(csvRecord.get(config.DataSetConfig.CATEGORY).equals(config.DataSetConfig.C_DIRECTFREEKICK)){
                    ShotEvent new_shot_event = new ShotEvent(event);
                    new_shot_event.setEventType(config.EventTypeDefinition.SHOT);
                    new_shot_event.setIsFreekick(true);
                    new_shot_event.setIsPenalty(false);
                    // shoot by where
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_LEFTFOOT))
                        new_shot_event.setShootByWhere(config.ShotDefinition.LEFTFOOT);
                    else
                        new_shot_event.setShootByWhere(config.ShotDefinition.RIGHTFOOT);
                    
                    // is shot blocked
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_BLOCKED)){
                        new_shot_event.setIsShotBlocked(true);
                        new_shot_event.setIsShotOnTarget(false);
                    }else
                        new_shot_event.setIsShotBlocked(false);
                    // is shot off target
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_OVER)||csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_OFFTARGET)){
                        new_shot_event.setIsShotOnTarget(false);
                    }else
                        new_shot_event.setIsShotOnTarget(true);
                    // is shot goal
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_ISGOAL)){
                        new_shot_event.setIsShotGoal(true);
                    }else
                        new_shot_event.setIsShotGoal(false);
                    teams.get(team_index).getPlayerByIndex(player_index).addShotEvent(new_shot_event);
                } 
                
                // penalty
                if(csvRecord.get(config.DataSetConfig.CATEGORY).equals(config.DataSetConfig.C_PENALTY)){
                    ShotEvent new_shot_event = new ShotEvent(event);
                    new_shot_event.setEventType(config.EventTypeDefinition.SHOT);
                    new_shot_event.setIsFreekick(false);
                    new_shot_event.setIsPenalty(true);
                    new_shot_event.setIsShotBlocked(false);
                   
                    // shoot by where
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_LEFTFOOT))
                        new_shot_event.setShootByWhere(config.ShotDefinition.LEFTFOOT);
                    else
                        new_shot_event.setShootByWhere(config.ShotDefinition.RIGHTFOOT);
                    // where in net
                    int h;
                    int v;
                    int where;
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(String.valueOf(config.DataSetConfig.A_LEFTCORNER)))
                        h = config.ShotDefinition.LEFTCORNER;
                    else if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(String.valueOf(config.DataSetConfig.A_RIGHTCORNER)))
                        h = config.ShotDefinition.RIGHTCORNER;
                    else
                        h = config.ShotDefinition.THROUGHCENTER;
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(String.valueOf(config.DataSetConfig.A_GROUND)))
                        v = config.ShotDefinition.GROUND;
                    else
                        v = config.ShotDefinition.HIGH;
                    where = h * v;
                    // is shot goal
                    if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_ISGOAL)){
                        new_shot_event.setWhereInNet(where);
                        new_shot_event.setIsShotOnTarget(true);
                        new_shot_event.setIsShotGoal(true);
                    }else if(csvRecord.get(config.DataSetConfig.ATTRIBUTE).contains(config.DataSetConfig.A_SAVEDBYGOALKEEPER)){
                        new_shot_event.setWhereInNet(where);
                        new_shot_event.setIsShotOnTarget(true);
                        new_shot_event.setIsShotGoal(false);
                    }else{
                        new_shot_event.setIsShotGoal(false);
                        new_shot_event.setIsShotOnTarget(false);
                    }
                    teams.get(team_index).getPlayerByIndex(player_index).addShotEvent(new_shot_event);
                } 
                /////////////////////////////////////////////////////////////////////////////////////////////
                // add pass events
                /////////////////////////////////////////////////////////////////////////////////////////////
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        return teams;
    }

    public CSVParser getParser() {
        return parser;
    }

    public void setParser(CSVParser parser) {
        this.parser = parser;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

