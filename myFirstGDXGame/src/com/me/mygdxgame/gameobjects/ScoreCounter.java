package com.me.mygdxgame.gameobjects;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.mygdxgame.gameobjects.intervalsystem.Corn;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;
import com.me.mygdxgame.gameobjects.intervalsystem.Interval;

public class ScoreCounter extends GameObject {

	
	public LinkedList<Corn> cornTracker = new LinkedList<Corn>();
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		for (Iterator<Corn> it = cornTracker.iterator(); it.hasNext();)
		{
			Corn c = it.next();
			
		}
	}

}
