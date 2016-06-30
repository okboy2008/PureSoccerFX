/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

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
public class ShotRelatedFunctions {
    
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
                events.addAll(functions.ShotRelatedFunctions.getNormalShotEvents(e));
            }
            if (shotfilter.isFreekick) {
                events.addAll(functions.ShotRelatedFunctions.getFreekickEvents(e));
            }
            if (shotfilter.isPenalty) {
                events.addAll(functions.ShotRelatedFunctions.getPenaltyEvents(e));
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
                events.addAll(functions.ShotRelatedFunctions.getShotNotOnTargetEvents(e));
            } else {
                if (shotfilter.isShotBlocked) {
                    events.addAll(functions.ShotRelatedFunctions.getShotBlockedEvents(e));
                }
                if (shotfilter.isShotOffTarget) {
                    events.addAll(functions.ShotRelatedFunctions.getShotOffTargetEvents(e));
                }
            }
            if (shotfilter.isShotOnTarget()) {
                events.addAll(functions.ShotRelatedFunctions.getShotOnTargetEvents(e));
            } else {
                if (shotfilter.isShotGoal) {
                    events.addAll(functions.ShotRelatedFunctions.getGoalEvents(e));
                }
                if (shotfilter.isShotSavedByKeeper) {
                    events.addAll(functions.ShotRelatedFunctions.getShotSavedByKeeperEvents(e));
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
                events.addAll(functions.ShotRelatedFunctions.getShotByLeftFootEvents(e));
            }
            if (shotfilter.isRightFoot) {
                events.addAll(functions.ShotRelatedFunctions.getShotByRightFootEvents(e));
            }
            if (shotfilter.isHead) {
                events.addAll(functions.ShotRelatedFunctions.getShotByHeadEvents(e));
            }
            if (shotfilter.isBody) {
                events.addAll(functions.ShotRelatedFunctions.getShotByBodyEvents(e));
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
        events = functions.ShotRelatedFunctions.getFilteredShotEventsByType(e, shotfilter);
        //System.out.println("sizeof event type: " + events.size());
        events = functions.ShotRelatedFunctions.getFilteredShotEventsByResult(events, shotfilter);
        //System.out.println("sizeof event rusult: " + events.size());
        events = functions.ShotRelatedFunctions.getFilteredShotEventsByPart(events, shotfilter);
        //System.out.println("sizeof event part: " + events.size());

        return events;
    }
    
    public static ArrayList<ShotEvent> getAllShotsFiltered(ShotFilter shotfilter){
        ArrayList<ShotEvent> events = new ArrayList<>();
        for(Team team : TEAMS){
            for(Player player : team.getPlayers()){
                events.addAll(functions.ShotRelatedFunctions.getFilteredShotEvents(player.getShotEvents(), shotfilter));
                // System.out.println(events.size());
            }
        }
        functions.ShotRelatedFunctions.printShotEventDebugInfo(events);
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
