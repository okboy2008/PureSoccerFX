/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import java.util.ArrayList;

/**
 *
 * @author s145633
 */
public class Player {
    private String name;
    private String pos[];
    private String age;
    private ArrayList<ShotEvent> shotEvents;
    
    @Override
    public String toString(){
        return this.name;
    }
    
    public Player(String n){
        this.name = n;
        shotEvents = new ArrayList<>();
    }
    
    public ArrayList<ShotEvent> getShotEvents(){
        return shotEvents;
    }
    
    public void setShotEvents(ArrayList<ShotEvent> list){
        shotEvents = list;
    }
    
    public void addShotEvent(ShotEvent e){
        this.shotEvents.add(e);
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public String getAge(){
        return this.age;
    }
    
    public void setAge(String age){
        this.age = age;
    }
    
    public String[] getPos(){
        return pos;
    }
    
    public void setPos(String[] pos){
        this.pos = pos;
    }
    
    public ArrayList<ShotEvent> getFreeKicks(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if(e.isFreekick())
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getFreeKickGoals(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if(e.isFreekick()&&e.isShotGoal())
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getPenalties(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if(e.isPenalty())
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getPenaltyGoals(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if(e.isPenalty()&&e.isShotGoal())
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getGoals(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if(e.isShotGoal())
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getRunningShots(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if(!(e.isPenalty()||e.isFreekick()))
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getRunningShotsOnTarget(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if((!(e.isPenalty()||e.isFreekick()))&&e.isShotOnTarget())
                result.add(e);
        }
        return result;
    }
    
    public ArrayList<ShotEvent> getRunningGoals(){
        ArrayList<ShotEvent> result = new ArrayList<>();
        for(ShotEvent e : this.shotEvents){
            if((!(e.isPenalty()||e.isFreekick()))&&e.isShotGoal())
                result.add(e);
        }
        return result;
    }
}
