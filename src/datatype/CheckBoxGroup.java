/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import java.util.ArrayList;
import javax.swing.JCheckBox;

/**
 *
 * @author s145633
 */
public class CheckBoxGroup {
    private final JCheckBox root;
    private ArrayList<JCheckBox> children;
    private boolean lastRootSelected;
    
    public CheckBoxGroup(JCheckBox root){
        this.root = root;
        this.lastRootSelected = true;
        children = new ArrayList<>();
    }
    
    public void addCheckBox(JCheckBox c){
        this.children.add(c);
    }
    
    public void validate(){
        boolean allSelected = true;
        for (JCheckBox c : children) {
            if (!c.isSelected()) {
                allSelected = false;
                break;
            }
        }
        //System.out.println("last all selected: " + this.lastRootSelected + " current all selected: " + allSelected + " root selected: " + root.isSelected());
        if((!this.lastRootSelected) && root.isSelected()){
            for(JCheckBox c : children){
                c.setSelected(true);
            }
            this.lastRootSelected = true;
        }else if(this.lastRootSelected && (!root.isSelected())){
            for(JCheckBox c : children){
                c.setSelected(false);
            }
            this.lastRootSelected = false;
        }else if(allSelected){
            root.setSelected(true);
            this.lastRootSelected = true;
        }else{
            root.setSelected(false);
            this.lastRootSelected = false;
        }
        
    }
}
