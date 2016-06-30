/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

/**
 *
 * @author s145633
 */
public class ShotFilter {
    // type
    public boolean isNormalShot = true;
    public boolean isFreekick = true;
    public boolean isPenalty = true;
    // shot quaility
    // public boolean isShotNotOnTarget = true;
    public boolean isShotOffTarget = true;
    public boolean isShotBlocked = true;
    //public boolean isShotOnTarget = true;
    public boolean isShotGoal = true;
    public boolean isShotSavedByKeeper = true;
    
    // how shot is made
    public boolean isLeftFoot = true;
    public boolean isRightFoot = true;
    public boolean isHead = true;
    public boolean isBody = true;
    
    public ShotFilter(){
        
    }
    
    public boolean isShotNotOnTarget(){
        return this.isShotOffTarget&&this.isShotBlocked;
    }
    
    public boolean isShotOnTarget(){
        return this.isShotGoal&&this.isShotSavedByKeeper;
    }
    
    public boolean isAllShotType(){
        return this.isNormalShot&&this.isFreekick&&this.isPenalty;
    }
    
    public boolean noShotType(){
        return !(this.isNormalShot||this.isFreekick||this.isPenalty);
    }
    
    public boolean isAllShotResult(){
        return this.isShotBlocked&&this.isShotOffTarget&&this.isShotGoal&&this.isShotSavedByKeeper;
    }
    
    public boolean noShotResult(){
        return !(this.isShotBlocked||this.isShotOffTarget||this.isShotGoal||this.isShotSavedByKeeper);
    }
    
    public boolean isAllShotPart(){
        return this.isLeftFoot&&this.isRightFoot&&this.isHead&&this.isBody;
    }
    
    public boolean noShotPart(){
        return !(this.isLeftFoot||this.isRightFoot||this.isHead||this.isBody);
    }
    
    public boolean isAllShot(){
        return this.isAllShotPart()&&this.isAllShotResult()&&this.isAllShotType();
    }
    
    public boolean isNoShot(){
        return this.noShotPart()||this.noShotResult()||this.noShotType();
    }
}
