/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx.model;

/**
 *
 * @author s145633
 */
public class Event {
    private int team_id;
    private int player_id;
    private Coordinate coordinate;
    private int time; // time is in ms
    private boolean isFirstHalf; // first or second half
    private int event_type; // event type indicator
    private int round;
    private String opponent;
    private boolean isHome;
    
    
    public Event(){
        team_id = -1;
        player_id = -1;
        coordinate = null;
        time = -1;
        event_type = -1;
        round = -1;
        opponent = null;
    }
    
    public Event(int team_id, int player_id, Coordinate c, int time, boolean is1half, int round, String opponent, boolean isHome){
        this.team_id = team_id;
        this.player_id = player_id;
        this.coordinate = c;
        this.time = time;
        this.isFirstHalf = is1half;
        this.round = round;
        this.opponent = opponent;
        this.isHome = isHome;
    }
    
    public boolean isHome(){
        return this.isHome;
    }
    
    public void setIsHome(boolean is){
        this.isHome = is;
    }
    
    public boolean isFirstHalf(){
        return this.isFirstHalf;
    }
    
    public void setIsFirstHalf(boolean is){
        this.isFirstHalf = is;
    }
    
    public int getRound(){
        return this.round;
    }
    
    public void setRound(int round){
        this.round = round;
    }
    
    public String getOpponent(){
        return this.opponent;
    }
    
    public void setOpponent(String opponent){
        this.opponent = opponent;
    }
    
    public Coordinate getCoordinate(){
        return coordinate;
    }
    
    public void setCoordinate(Coordinate c){
        this.coordinate = c;
    }
    
    public int getTime(){
        return time;
    }
    
    public void setTime(int time){
        this.time = time;
    }
    
    public int getEventType(){
        return event_type;
    }
    
    public void setEventType(int type){
        this.event_type = type;
    }
    
    public int getTeamId(){
        return this.team_id;
    }
    
    public void setTeamId(int id){
        this.team_id = id;
    }
    
    public int getPlayerId(){
        return this.player_id;
    }
    
    public void setPlayerId(int id){
        this.player_id = id;
    }
}
