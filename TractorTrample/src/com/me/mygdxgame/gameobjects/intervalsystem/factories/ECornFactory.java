package com.me.mygdxgame.gameobjects.intervalsystem.factories;

import java.util.Random;

import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.intervalsystem.Corn;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;
import com.me.mygdxgame.gameobjects.intervalsystem.Interval;
import com.me.mygdxgame.gameobjects.intervalsystem.IntervalFactory;

public class ECornFactory implements IntervalFactory {

	@Override
	public Interval createInterval(float xPos, float yPos, SlidingSpeed slidingSpeed) {
		Entity[] output = new Entity[10];
		Random r = new Random(System.nanoTime());
		for (int i = 0; i<10; i++)
			output[i] = Corn.Factory().setPosition(xPos + (i * 40) + (i * 50), yPos+r.nextInt(200)).setSlidingSpeed(slidingSpeed);
		
		Interval interval = Interval.newInterval(xPos, 900, slidingSpeed, output);
		return interval;
	}
	
}
