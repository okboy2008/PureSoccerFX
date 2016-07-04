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
public class Match {
    ArrayList<MatchEvent> match_events;

    public Match(){
        match_events = new ArrayList<>();
    }
    
    public ArrayList<MatchEvent> getMatch_events() {
        return match_events;
    }

    public void setMatch_events(ArrayList<MatchEvent> match_events) {
        this.match_events = match_events;
    }
    
    
}
