package com.me.mygdxgame.gameobjects.intervalsystem.factories;

import java.util.Random;

import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;
import com.me.mygdxgame.gameobjects.intervalsystem.Interval;
import com.me.mygdxgame.gameobjects.intervalsystem.IntervalFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.Tree;


public class OneTreeFactory implements IntervalFactory {

	@Override
	public Interval createInterval(float xPos, float yPos, SlidingSpeed slidingSpeed) {
		Entity[] output = new Entity[3];
		Random r = new Random(System.nanoTime());
		for (int i = 0; i<3; i++)
			output[i] = Tree.treeFactory().setPosition(xPos + (i * 300), yPos+r.nextInt(200)).setSlidingSpeed(slidingSpeed);
		
		Interval i = Interval.newInterval(xPos, 800, slidingSpeed, output);
		
		return i;
	}
	

}
