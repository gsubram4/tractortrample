package com.me.mygdxgame.gameobjects.intervalsystem.factories;

import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.intervalsystem.Corn;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;
import com.me.mygdxgame.gameobjects.intervalsystem.Interval;
import com.me.mygdxgame.gameobjects.intervalsystem.IntervalFactory;


public class SquareCornFactory implements IntervalFactory {

	@Override
	public Interval createInterval(float xPos, float yPos, SlidingSpeed slidingSpeed) {
		Entity[] output = new Entity[25];
		for (int i = 0; i<5; i++)
			output[i] = Corn.Factory().setPosition(xPos + (i * 50), yPos+160-(i*40)).setSlidingSpeed(slidingSpeed);
		for (int i = 0; i<5; i++)
			output[i+5] = Corn.Factory().setPosition(xPos + 100 + (i * 50), yPos+160-(i*40)).setSlidingSpeed(slidingSpeed);
		for (int i = 0; i<5; i++)
			output[i+10] = Corn.Factory().setPosition(xPos + 200 + (i * 50), yPos+160-(i*40)).setSlidingSpeed(slidingSpeed);
		for (int i = 0; i<5; i++)
			output[i+15] = Corn.Factory().setPosition(xPos + 300 + (i * 50), yPos+160-(i*40)).setSlidingSpeed(slidingSpeed);
		for (int i = 0; i<5; i++)
			output[i+20] = Corn.Factory().setPosition(xPos + 400 + (i * 50), yPos+160-(i*40)).setSlidingSpeed(slidingSpeed);
	
		
		Interval i = Interval.newInterval(xPos, 650, slidingSpeed, output);
		
		return i;
	}
	

}
