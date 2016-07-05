/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import java.util.ArrayList;
import puresoccerfx.model.PlayerStatistic;

/**
 *
 * @author s145633
 */
public class Player {
    private String name;
    private String pos[];
    private String age;
//    private ArrayList<ShotEvent> shotEvents;
    private ArrayList<PlayerEvent> events;
    
    @Override
    public String toString(){
        return this.name;
    }
    
    public Player(String n){
        this.name = n;
//        shotEvents = new ArrayList<>();
        events = new ArrayList<>();
    }
    
    public void printEvents(){
        for(PlayerEvent e:events){
            e.printEvent();
        }
        System.out.println(events.size()+ " events.");
    }
    
//    public ArrayList<ShotEvent> getShotEvents(){
//        return shotEvents;
//    }
//    
//    public void setShotEvents(ArrayList<ShotEvent> list){
//        shotEvents = list;
//    }
//    
//    public void addShotEvent(ShotEvent e){
//        this.shotEvents.add(e);
//    }
    
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
    
//    public ArrayList<ShotEvent> getFreeKicks(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if(e.isFreekick())
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getFreeKickGoals(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if(e.isFreekick()&&e.isShotGoal())
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getPenalties(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if(e.isPenalty())
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getPenaltyGoals(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if(e.isPenalty()&&e.isShotGoal())
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getGoals(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if(e.isShotGoal())
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getRunningShots(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if(!(e.isPenalty()||e.isFreekick()))
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getRunningShotsOnTarget(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if((!(e.isPenalty()||e.isFreekick()))&&e.isShotOnTarget())
//                result.add(e);
//        }
//        return result;
//    }
//    
//    public ArrayList<ShotEvent> getRunningGoals(){
//        ArrayList<ShotEvent> result = new ArrayList<>();
//        for(ShotEvent e : this.shotEvents){
//            if((!(e.isPenalty()||e.isFreekick()))&&e.isShotGoal())
//                result.add(e);
//        }
//        return result;
//    }

    public ArrayList<PlayerEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<PlayerEvent> events) {
        this.events = events;
    }
    
    public int getStatisticByName(String n){
        int count = 0;
        PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(n);
        for(PlayerEvent e:events){
            if(e.isEventMatchStatistic(ps))
                count++;
        }
        return count;
    }
    
    public ArrayList<PlayerEvent> getEventByName(String n){
        ArrayList<PlayerEvent> list = new ArrayList<>();
        PlayerStatistic ps = config.GlobalVariable.MAPNAMETOSTATS.get(n);
        for(PlayerEvent e:events){
            if(e.isEventMatchStatistic(ps))
                list.add(e);
        }
        return list;
    }
    
    public int getAppearence(){
        int count = 0;
        int round = 0;
        for(PlayerEvent e:events){
            if(e.getEvent().getRound()!=round){
                count ++;
                round = e.getEvent().getRound();
            }
        }
        return count;
    }
}
