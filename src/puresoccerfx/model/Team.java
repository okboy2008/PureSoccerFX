/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx.model;

import java.util.ArrayList;

/**
 *
 * @author s145633
 */
public class Team {
    private String name;
    private ArrayList<Player> players;
    
    public Team(String name){
        this.name = name;
        players = new ArrayList<>();
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public ArrayList<Player> getPlayers(){
        return players;
    }
    
    public void setPlayers(ArrayList<Player> p){
        this.players = p;
    }
    
    public void addPlayer(Player p){
        players.add(p);
    }
    
    public Player getPlayerByIndex(int i){
        return players.get(i);
    }
}
