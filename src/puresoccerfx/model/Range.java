/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx.model;

/**
 *
 * @author s145633
 */
public class Range {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public Range(double minx, double maxx, double miny, double maxy){
        minX = minx;
        maxX = maxx;
        minY = miny;
        maxY = maxy;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
    
   
    
    
}
