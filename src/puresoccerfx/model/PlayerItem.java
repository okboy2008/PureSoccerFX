/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx.model;

import datatype.Player;

/**
 *
 * @author s145633
 */
public class PlayerItem {
    private int team_id;
    private int player_id;
    private String team_name;
    private String player_name;

    public Player getPlayer(){
        return config.GlobalVariable.TEAMS.get(team_id).getPlayerByIndex(player_id);
    }
    
    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }
    

    public PlayerItem(){
        team_id = -1;
        player_id = -1;
    }
    
    public void setTeamId(int id){
        team_id = id;
        this.team_name = config.GlobalVariable.TEAMS.get(id).getName().toUpperCase();
    }
    
    public int getTeamId(){
        return team_id;
    }
    
    public void setPlayerId(int id){
        player_id = id;
        this.player_name = config.GlobalVariable.TEAMS.get(team_id).getPlayerByIndex(id).getName();
    }
    
    public int getPlayerId(){
        return player_id;
    }
    
    public String getPlayerName(){
        return config.GlobalVariable.TEAMS.get(team_id).getPlayerByIndex(player_id).getName();
    }
    
    public String getTeamName(){
        return config.GlobalVariable.TEAMS.get(team_id).getName();
    }
    
    @Override
    public String toString() {
        if (isRoot()){
            return "Eredivisie";
        }else if (isTeam()) {
            return config.GlobalVariable.TEAMS.get(team_id).getName().toUpperCase();
        } else {
            return config.GlobalVariable.TEAMS.get(team_id).getPlayerByIndex(player_id).getName();
        }
    }

    public boolean isTeam() {
        if (player_id == -1) {
            return true;
        }
        return false;
    }
    
    public boolean isRoot() {
        if (team_id == -1)
            return true;
        return false;
    }
}
