package com.me.mygdxgame;


public class GDXUtilities {
	public static int width = 1280;
	public static int height = 720;
	
    public static final int SCREEN_CENTER = height/2;
    
    public static final float initialWorldSpeed = 350;
    public static final float maxWorldSpeed = 700;
    public static final float speedIncreaseRate = 10;
    
    public static final int initialIntervalSize = 200;
    public static final int minimumIntervalSize = 50;
    public static final int intervalReductionSize = 50;
    public static final int fudgeFactor = 0;
    
    public static final float farmerStartXPos = 200;
    public static final float farmerStartYPos = 300;
	public static float farmerMovementSpeedY = 150; //px / second
	public static float farmerMovementSpeedX = 0;  //px /second
    
    public static final boolean DEBUG = false;
}
