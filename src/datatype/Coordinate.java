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
public class Coordinate {
    private double x;
    private double y;
    
    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double getX(){
        return x;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public double getY(){
        return y;
    }
    
    public void segY(int y){
        this.y = y;
    }
}
