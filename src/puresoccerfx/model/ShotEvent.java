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
public class ShotEvent extends Event{
    private boolean isShotBlocked;
    private boolean isShotOnTarget;
    private boolean isShotGoal;
    private boolean isPenalty;
    private boolean isFreekick;
    
    private int shootByWhere;
    private int whereInNet;
    
    public ShotEvent(Event e){
        this.setCoordinate(e.getCoordinate());
        this.setIsFirstHalf(e.isFirstHalf());
        this.setIsHome(e.isHome());
        this.setOpponent(e.getOpponent());
        this.setRound(e.getRound());
        this.setTime(e.getTime());
        this.setEventType(e.getEventType());
        this.setTeamId(e.getTeamId());
        this.setPlayerId(e.getPlayerId());
    }
    
    public boolean isShotBlocked(){
        return this.isShotBlocked;
    }
    
    public void setIsShotBlocked(boolean b){
        this.isShotBlocked = b;
    }
    
    public boolean isShotOnTarget(){
        return this.isShotOnTarget;
    }
    
    public void setIsShotOnTarget(boolean b){
        this.isShotOnTarget = b;
    }
    
    public boolean isShotGoal(){
        return this.isShotGoal;
    }
    
    public void setIsShotGoal(boolean b){
        this.isShotGoal = b;
    }
    
    public boolean isPenalty(){
        return this.isPenalty;
    }
    
    public void setIsPenalty(boolean b){
        this.isPenalty = b;
    }
    
    public boolean isFreekick(){
        return this.isFreekick;
    }
    
    public void setIsFreekick(boolean b){
        this.isFreekick = b;
    }
    
    public int getShootByWhere(){
        return this.shootByWhere;
    }
    
    public void setShootByWhere(int w){
        this.shootByWhere = w;
    }
    
    public int getWhereInNet(){
        return this.whereInNet;
    }
    
    public void setWhereInNet(int w){
        this.whereInNet = w;
    }
}
