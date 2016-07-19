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
public class ScatterChartExtraData {
    //private PlayerItem item;
    private String name;
    private int player_id;
    private int team_id;
    private String X;
    private String Y;

    public ScatterChartExtraData(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    
    
    

    public String getX() {
        return X;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getY() {
        return Y;
    }

    public void setY(String Y) {
        this.Y = Y;
    }
    
    public Player getPlayer(){
        return config.GlobalVariable.TEAMS.get(team_id).getPlayerByIndex(player_id);
    }
    
    public PlayerItem getPlayerItem(){
        PlayerItem item = new PlayerItem();
        item.setTeamId(team_id);
        item.setPlayerId(player_id);
        return item;
    }
    
    public boolean isSamePlayer(PlayerItem p){
        if(p.getTeamId() == this.team_id && p.getPlayerId() == this.player_id)
            return true;
        return false;
    }
    
}
