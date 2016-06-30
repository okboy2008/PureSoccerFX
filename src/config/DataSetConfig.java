/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author s145633
 */
public class DataSetConfig {
    // Features
    public static final int TIME = 0;
    public static final int HALF = 1;
    public static final int CATEGORY = 3;
    public static final int PLAYER = 4;
    public static final int TEAM = 5;
    public static final int ATTRIBUTE = 6;
    public static final int DEFINITION = 7;
    public static final int OPPONENT = 8;
    public static final int ROUND = 9;
    public static final int LOCATIONX = 10;
    public static final int LOCATIONY = 11;
    // Category features
    public static final String C_OWNGOALFOR = "own goal for";
    public static final String C_GOALATTEMPT = "goal attempt";
    public static final String C_DIRECTFREEKICK = "direct free kick";
    public static final String C_PENALTY = "penalty";
    public static final String C_GOAL = "goal";
    // Attribute features
    public static final String A_ISGOAL = "goal";
    public static final String A_LEFTFOOT = "left foot";
    public static final String A_RIGHTFOOT = "right foot";
    public static final String A_BODY = "body";
    public static final String A_HEAD = "head";
    public static final String A_BLOCKED = "blocked";
    public static final String A_OVER = "over";
    public static final String A_OFFTARGET = "off target";
    public static final String A_SAVEDBYGOALKEEPER = "saved by the keeper";
    public static final String A_LEFTCORNER = "left corner";
    public static final String A_RIGHTCORNER = "right corner";
    public static final String A_THROUGHCENTER = "through the center";
    public static final String A_HIGH = "high";
    public static final String A_GROUND = "ground";
    // Definition features
    public static final String D_ISSHOTOFFTARGET = "isShotOffTarget";
    public static final String D_ISSHOTONTARGET = "isShotOnTarget";
    
}
