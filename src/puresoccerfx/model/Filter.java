/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx.model;

import datatype.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author s145633
 */
public class Filter {
    private String X;
    private String Y;
    private ObservableList<Range> filters = FXCollections.observableArrayList();

    public Filter(){
        
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
    
   

    public ObservableList<Range> getFilters() {
        return filters;
    }

    public void setFilters(ObservableList<Range> filters) {
        this.filters = filters;
    }   
    
    public boolean isPlayerSatisfied(Player p){
        if(filters.isEmpty())
            return true;
        for(Range r:filters){
            if(p.isPlayerMeetRequirement(r.getMinX(), r.getMaxX(), X)&&p.isPlayerMeetRequirement(r.getMinY(), r.getMaxY(), Y))
                return true;
        }
        return false;
    }
    
    public boolean isPlayerSatisfiedAVG(Player p){
        if(filters.isEmpty())
            return true;
        for(Range r:filters){
            if(p.isPlayerMeetRequirementAVG(r.getMinX(), r.getMaxX(), X)&&p.isPlayerMeetRequirementAVG(r.getMinY(), r.getMaxY(), Y))
                return true;
        }
        return false;
    }
    
}
