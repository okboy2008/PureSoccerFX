/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import datatype.Coordinate;
import datatype.Event;
import datatype.Match;
import datatype.MatchEvent;
import datatype.Player;
import datatype.PlayerEvent;
import datatype.Round;
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
            int round = 0;
            int match = 0;
            String last_match = "";
            System.out.println(file.toString());
            parser = CSVParser.parse(file, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
//            parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : parser) {
                
                // skip first record
                if(csvRecord.getRecordNumber() == 1)
                    continue;
//                if(csvRecord.getRecordNumber() == 2)
//                    last_match = csvRecord.get(config.DataSetConfig.OPPONENT);
                
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
                
                
                // determine round_id and match_id
                int round_id = 0, match_id = 0;
                int current_round = Integer.parseInt(csvRecord.get(config.DataSetConfig.ROUND));
                if(current_round > round){
                    round = current_round;
                    round_id = round -1;
                    //System.out.println(round_id);
                    config.GlobalVariable.SEASON.add(new Round());
                    match = 0;
                }
                
                String current_match = csvRecord.get(config.DataSetConfig.OPPONENT);
                if(!current_match.equals(last_match)){
                    match_id = match ++;
                    //System.out.println(match_id);
                    config.GlobalVariable.SEASON.get(round_id).getMatches().add(new Match());
                    last_match = current_match;
                }
                
                // add events
                MatchEvent new_event = new MatchEvent();
                new_event.setTime(Integer.parseInt(csvRecord.get(config.DataSetConfig.TIME)));
                new_event.setHalf(csvRecord.get(config.DataSetConfig.HALF));
                new_event.setCategory(csvRecord.get(config.DataSetConfig.CATEGORY));
                new_event.setAttribute(csvRecord.get(config.DataSetConfig.ATTRIBUTE));
                new_event.setDefinition(csvRecord.get(config.DataSetConfig.DEFINITION));
                new_event.setTeam(csvRecord.get(config.DataSetConfig.TEAM));
                new_event.setPlayer(csvRecord.get(config.DataSetConfig.PLAYER));
                
                String opponent = csvRecord.get(config.DataSetConfig.OPPONENT);
                String[] two_teams = opponent.split(" - ");
                String home_team = two_teams[0];
                String away_team = two_teams[1];
                new_event.setHome_team(home_team);
                new_event.setAway_team(away_team);
                
                new_event.setRound(Integer.parseInt(csvRecord.get(config.DataSetConfig.ROUND)));
                
                Coordinate c = new Coordinate(Double.parseDouble(csvRecord.get(config.DataSetConfig.LOCATIONX)),Double.parseDouble(csvRecord.get(config.DataSetConfig.LOCATIONY)));
                new_event.setCoord(c);
                config.GlobalVariable.SEASON.get(round_id).getMatches().get(match_id).getMatch_events().add(new_event);
                int event_id = config.GlobalVariable.SEASON.get(round_id).getMatches().get(match_id).getMatch_events().indexOf(new_event);
                //System.out.println(event_id);
                
                ArrayList<PlayerEvent> list = teams.get(team_index).getPlayerByIndex(player_index).getEvents();
                //System.out.println(list == null);
                list.add(new PlayerEvent(round_id, match_id, event_id));
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

