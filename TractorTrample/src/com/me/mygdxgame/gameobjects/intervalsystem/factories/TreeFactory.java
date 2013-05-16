package com.me.mygdxgame.gameobjects.intervalsystem.factories;

import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;
import com.me.mygdxgame.gameobjects.intervalsystem.Interval;
import com.me.mygdxgame.gameobjects.intervalsystem.IntervalFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.Tree;


public class TreeFactory implements IntervalFactory {

	@Override
	public Interval createInterval(float xPos, float yPos, SlidingSpeed slidingSpeed) {
		Entity[] output = new Entity[2];
		for (int i = 0; i<2; i++)
			output[i] = Tree.treeFactory().setPosition(xPos + (i * 150), yPos+160-(i*170)).setSlidingSpeed(slidingSpeed);
		
		Interval i = Interval.newInterval(xPos, 550, slidingSpeed, output);
		
		return i;
	}
	

}
