/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import static config.GlobalVariable.TEAMS;
import datatype.Player;
import datatype.ShotEvent;
import datatype.Team;
import filter.ShotFilter;
import java.util.ArrayList;

/**
 *
 * @author s145633
 */
public class FilterFunctions {
    
    public static ArrayList<ShotEvent> getNormalShotEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(!(ee.isFreekick()||ee.isPenalty()))
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getFreekickEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.isFreekick())
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getPenaltyEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.isPenalty())
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotOffTargetEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(!(ee.isShotBlocked()||ee.isShotOnTarget()))
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotBlockedEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.isShotBlocked())
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotNotOnTargetEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(!ee.isShotOnTarget())
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getGoalEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.isShotGoal())
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotSavedByKeeperEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.isShotOnTarget()&&(!ee.isShotGoal()))
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotOnTargetEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.isShotOnTarget())
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotByLeftFootEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.getShootByWhere() == config.ShotDefinition.LEFTFOOT)
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotByRightFootEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.getShootByWhere() == config.ShotDefinition.RIGHTFOOT)
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotByHeadEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.getShootByWhere() == config.ShotDefinition.HEAD)
               events.add(ee);
       }
       return events;
    }
    
    public static ArrayList<ShotEvent> getShotByBodyEvents(ArrayList<ShotEvent> e){
        ArrayList<ShotEvent> events = new ArrayList<>();
       // check filters
       for(ShotEvent ee : e){
           if(ee.getShootByWhere() == config.ShotDefinition.BODY)
               events.add(ee);
       }
       return events;
    }
    
    
    public static ArrayList<ShotEvent> getFilteredShotEventsByType(ArrayList<ShotEvent> e, ShotFilter shotfilter){
        ArrayList<ShotEvent> events = new ArrayList<>();
        
        if (shotfilter.isAllShotType()) {
            System.out.println("is all type: " + shotfilter.isAllShotType());
            return e;
        } else if (!shotfilter.noShotType()) {
            if (shotfilter.isNormalShot) {
                events.addAll(filter.FilterFunctions.getNormalShotEvents(e));
            }
            if (shotfilter.isFreekick) {
                events.addAll(filter.FilterFunctions.getFreekickEvents(e));
            }
            if (shotfilter.isPenalty) {
                events.addAll(filter.FilterFunctions.getPenaltyEvents(e));
            }
        }
        return events;
    }
    
    public static ArrayList<ShotEvent> getFilteredShotEventsByResult(ArrayList<ShotEvent> e, ShotFilter shotfilter){
        ArrayList<ShotEvent> events = new ArrayList<>();
        
        if (shotfilter.isAllShotResult()) {
            System.out.println("is all result: " + shotfilter.isAllShotResult());
            return e;
        } else if (!shotfilter.noShotResult()) {
            if (shotfilter.isShotNotOnTarget()) {
                events.addAll(filter.FilterFunctions.getShotNotOnTargetEvents(e));
            } else {
                if (shotfilter.isShotBlocked) {
                    events.addAll(filter.FilterFunctions.getShotBlockedEvents(e));
                }
                if (shotfilter.isShotOffTarget) {
                    events.addAll(filter.FilterFunctions.getShotOffTargetEvents(e));
                }
            }
            if (shotfilter.isShotOnTarget()) {
                events.addAll(filter.FilterFunctions.getShotOnTargetEvents(e));
            } else {
                if (shotfilter.isShotGoal) {
                    events.addAll(filter.FilterFunctions.getGoalEvents(e));
                }
                if (shotfilter.isShotSavedByKeeper) {
                    events.addAll(filter.FilterFunctions.getShotSavedByKeeperEvents(e));
                }
            }
        }
        return events;
    }
    
    public static ArrayList<ShotEvent> getFilteredShotEventsByPart(ArrayList<ShotEvent> e, ShotFilter shotfilter){
        ArrayList<ShotEvent> events = new ArrayList<>();
        
        if (shotfilter.isAllShotPart()) {
            System.out.println("is all part: " + shotfilter.isAllShotPart());
            return e;
        } else if (!shotfilter.noShotPart()) {
            if (shotfilter.isLeftFoot) {
                events.addAll(filter.FilterFunctions.getShotByLeftFootEvents(e));
            }
            if (shotfilter.isRightFoot) {
                events.addAll(filter.FilterFunctions.getShotByRightFootEvents(e));
            }
            if (shotfilter.isHead) {
                events.addAll(filter.FilterFunctions.getShotByHeadEvents(e));
            }
            if (shotfilter.isBody) {
                events.addAll(filter.FilterFunctions.getShotByBodyEvents(e));
            }
        }
        return events;
    }
    
    public static ArrayList<ShotEvent> getFilteredShotEvents(ArrayList<ShotEvent> e, ShotFilter shotfilter){
       ArrayList<ShotEvent> events = new ArrayList<>();
       
        // check filters
        if (shotfilter.isAllShot()) {
            System.out.println("is all shot: " + shotfilter.isAllShot());
            return e;
        }
        if (shotfilter.isNoShot()) {
            System.out.println("is no shot: " + shotfilter.isNoShot());
            return events;
        }
        events = filter.FilterFunctions.getFilteredShotEventsByType(e, shotfilter);
        //System.out.println("sizeof event type: " + events.size());
        events = filter.FilterFunctions.getFilteredShotEventsByResult(events, shotfilter);
        //System.out.println("sizeof event rusult: " + events.size());
        events = filter.FilterFunctions.getFilteredShotEventsByPart(events, shotfilter);
        //System.out.println("sizeof event part: " + events.size());

        return events;
    }
    
    public static ArrayList<ShotEvent> getAllShotsFiltered(ShotFilter shotfilter){
        ArrayList<ShotEvent> events = new ArrayList<>();
        for(Team team : TEAMS){
            for(Player player : team.getPlayers()){
                events.addAll(filter.FilterFunctions.getFilteredShotEvents(player.getShotEvents(), shotfilter));
                // System.out.println(events.size());
            }
        }
        filter.FilterFunctions.printShotEventDebugInfo(events);
        return events;
    }
    
    public static void printShotEventDebugInfo(ArrayList<ShotEvent> e){
        for(ShotEvent ee : e){
            System.out.println(TEAMS.get(ee.getTeamId()).getName() + ", " + TEAMS.get(ee.getTeamId()).getPlayerByIndex(ee.getPlayerId()).getName() +", "
            + "isFreekick: " + ee.isFreekick() + ", isPenalty: "+ ee.isPenalty());
        }
        System.out.println(e.size() + " shot events.");
    }
}
