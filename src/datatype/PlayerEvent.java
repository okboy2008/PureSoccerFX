/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import puresoccerfx.model.PlayerStatistic;

/**
 *
 * @author s145633
 */
public class PlayerEvent {
    int roundId;
    int matchId;
    int eventId;

    public PlayerEvent(){
        
    }
    
    public boolean isEventMatchStatistic(PlayerStatistic ps){
        MatchEvent e = this.getEvent();
        if(!e.getCategory().equals(ps.getCategory())){
            if(!ps.getCategory().equals(config.DataSetConfig.NONE))
                return false;
        }
        for(String s:ps.getAttribute()){
            if(!s.equals(config.DataSetConfig.NONE)){
            if(!e.getAttribute().contains(s))
                return false;
            }
        }
        for(String s:ps.getDefinition()){
            if(!s.equals(config.DataSetConfig.NONE)){
            if(!e.getDefinition().contains(s))
                return false;
            }
        }
        return true;
    }
    
    public PlayerEvent(int r_id, int m_id, int e_id){
        this.roundId = r_id;
        this.matchId = m_id;
        this.eventId = e_id;
    }
    
    public MatchEvent getEvent(){
        return config.GlobalVariable.SEASON.get(roundId).getMatches().get(matchId).getMatch_events().get(eventId);
    }
    
    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void printEvent(){
        MatchEvent e = config.GlobalVariable.SEASON.get(roundId).getMatches().get(matchId).getMatch_events().get(eventId);
        System.out.println(e.time + ", "+e.team+", "+e.player+", "+e.category+", "+e.attribute+", "+e.definition);
    }
    
}
