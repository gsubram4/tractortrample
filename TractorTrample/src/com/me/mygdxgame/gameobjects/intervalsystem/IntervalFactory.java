package com.me.mygdxgame.gameobjects.intervalsystem;

import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;


public interface IntervalFactory {
	public Interval createInterval(float xPos, float yPos, SlidingSpeed slidingSpeed);
	
}
