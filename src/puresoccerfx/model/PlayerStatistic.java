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
public class PlayerStatistic {
    private String name;
    private String category;
    private ArrayList<String> attribute;
    private ArrayList<String> definition;
    private boolean customized;
       
    public PlayerStatistic(){
        attribute = new ArrayList<>();
        definition = new ArrayList<>();
    }
    
    public PlayerStatistic(PlayerStatistic ps){
        this.name = ps.getName();
        this.category = ps.getCategory();
        this.attribute = ps.getAttribute();
        this.definition = ps.getDefinition();
        this.customized = ps.isCustomized();
    }
    
    public PlayerStatistic(String name){
        attribute = new ArrayList<>();
        definition = new ArrayList<>();
        this.name = name;
    }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(ArrayList<String> attribute) {
        this.attribute = attribute;
    }

    public ArrayList<String> getDefinition() {
        return definition;
    }

    public void setDefinition(ArrayList<String> definition) {
        this.definition = definition;
    }

    public boolean isCustomized() {
        return customized;
    }

    public void setCustomized(boolean isCustomized) {
        this.customized = isCustomized;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
