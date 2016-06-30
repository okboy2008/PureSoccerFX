/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

/**
 *
 * @author s145633
 */
public class Zone {
    private char op1_1;
    private char op1_2;
    private char op2_1;
    private char op2_2;
    private Coordinate c1;
    private Coordinate c2;
    
    public Zone(char op1_1, Coordinate c1, char op1_2, char op2_1, Coordinate c2, char op2_2){
        this.op1_1 = op1_1;
        this.c1 = c1;
        this.op1_2 = op1_2;
        this.op2_1 = op2_1;
        this.c2 = c2;
        this.op2_2 = op2_2;
    }
    
    public char getOp1_1(){
        return this.op1_1;
    }
    
    public void setOp1_1(char op){
        this.op1_1 = op;
    }
    
    public char getOp1_2(){
        return this.op1_2;
    }
    
    public void setOp1_2(char op){
        this.op1_2 = op;
    }
    
    public char getOp2_1(){
        return this.op2_1;
    }
    
    public void setOp2_1(char op){
        this.op2_1 = op;
    }
    
    public char getOp2_2(){
        return this.op2_2;
    }
    
    public void setOp2_2(char op){
        this.op2_2 = op;
    }
    
    public Coordinate getC1(){
        return this.c1;
    }
    
    public void setC1(Coordinate c){
        this.c1 = c;
    }
    
    public Coordinate getC2(){
        return this.c2;
    }
    
    public void setC2(Coordinate c){
        this.c2 = c;
    }
    
    public boolean isInZone(Coordinate c){
        // check op1_1
        if(this.op1_1 == config.ProjectSetting.GREATER){
            if(c.getX() <= this.c1.getX())
                return false;
        }else{
            if(c.getX() < this.c1.getX())
                return false;
        }
        // check op1_2
        if(this.op1_2 == config.ProjectSetting.LESS){
            if(c.getY() <= this.c1.getY())
                return false;
        }else{
            if(c.getY() < this.c1.getY())
                return false;
        }
        // check op2_1
        if(this.op2_1 == config.ProjectSetting.GREATER){
            if(c.getX() >= this.c2.getX())
                return false;
        }else{
            if(c.getX() > this.c2.getX())
                return false;
        }
        // check op2_2
        if(this.op2_2 == config.ProjectSetting.LESS){
            if(c.getY() >= this.c2.getY())
                return false;
        }else{
            if(c.getY() > this.c2.getY())
                return false;
        }
        
        return true;
    }
}
