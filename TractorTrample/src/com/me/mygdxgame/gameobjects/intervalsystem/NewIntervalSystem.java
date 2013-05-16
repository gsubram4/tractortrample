package com.me.mygdxgame.gameobjects.intervalsystem;

import java.util.Iterator;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.GameInstanceContainer.GameMode;
import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.Farmer;
import com.me.mygdxgame.gameobjects.GameObject;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.CornFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.ECornFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.LaserFenceFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.MixFenceFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.OneTreeFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.SquareCornFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.TrampleFenceFactory;
import com.me.mygdxgame.gameobjects.intervalsystem.factories.TreeFactory;

import static com.me.mygdxgame.GDXUtilities.initialIntervalSize;
import static com.me.mygdxgame.GDXUtilities.fudgeFactor;
import static com.me.mygdxgame.GDXUtilities.minimumIntervalSize;
import static com.me.mygdxgame.GDXUtilities.intervalReductionSize;
import static com.me.mygdxgame.GDXUtilities.speedIncreaseRate;

import static com.me.mygdxgame.GDXUtilities.width;

public class NewIntervalSystem extends GameObject{

	/*--------------------------------------\
			
	    	NEW FACTORIES GO HERE
			
	\---------------------------------------*/
	IntervalFactory[] intervalFactory = {
			new CornFactory(),
			new SquareCornFactory(),
			new ECornFactory(),
			new TreeFactory(),
			new OneTreeFactory(),
			new LaserFenceFactory(),
			new TrampleFenceFactory(),
			new MixFenceFactory()
	};
	
	/*--------------------------------------\
	
      			NEW ENTITIES GO HERE
	
	\---------------------------------------*/
	private void Build(TextureAtlas atlas)
	{
		Corn.Build(atlas);
		Tree.Build(atlas);
		LaserFence.Build(atlas);	
		TrampleFence.Build(atlas);
		DeadFence.Build(atlas);
	}
	
	
	/*--------------------------------------\
	
				DONT TOUCH BELOW
	
	\---------------------------------------*/
	//-------BUILT------------
	private int interval = 0;
	private int fudgeFactor = 0;
	private SlidingSpeed slidingSpeed = SlidingSpeed.FOREGROUND;
	private int verticalPosition = 0;
	
	public NewIntervalSystem SlidingSpeed(SlidingSpeed s) { 	this.slidingSpeed = s;	return this; }
	public NewIntervalSystem fudgeFactor(int i) { this.fudgeFactor = i; return this; }
	public NewIntervalSystem interval(int i) { this.interval = i; return this; }
	public NewIntervalSystem verticalPosition(int i) { this.verticalPosition = i; return this; }

	
	//------PERSONAL--------------
	
	
	private Farmer farmer;
	
	public LinkedList<Interval> gameIntervals = new LinkedList<Interval>();
	private Random rand = new Random(System.nanoTime());
	
	private float stateTime = 0;
	private float xPosition = width;
	private float lastIntervalWidth = 0;
	
	private GameMode oldGameMode = GameMode.TITLESCREEN;
	
	public NewIntervalSystem(TextureAtlas atlas) {
		//TODO : Every single sprite now has to be built here, ain't that sad??
		Build(atlas);
		farmer = new Farmer(atlas);
	}
	
	public NewIntervalSystem reset()
	{
		interval = initialIntervalSize;
		fudgeFactor = fudgeFactor;
		slidingSpeed = SlidingSpeed.FOREGROUND;
		verticalPosition = 181;
        xPosition = width + interval;
		gameIntervals.clear();
		stateTime = 0;
		lastIntervalWidth = 0;
        
		farmer.reset();
		
		oldGameMode = GameInstanceContainer.currentGameMode;
		return this;
	}
	
	public void moveFarmerOffScreen()
	{
		farmer.moveOffScreen();
	}

	
	private void generateNewInterval()
	{
		int fudgeFactorGen = 0;
		if (fudgeFactor > 0)
			fudgeFactorGen = rand.nextInt(fudgeFactor);
		
		IntervalFactory ef = intervalFactory[rand.nextInt(intervalFactory.length)];
		Interval i = ef.createInterval(xPosition + (fudgeFactorGen), verticalPosition, slidingSpeed);
		xPosition += i.width + fudgeFactorGen + interval;
		lastIntervalWidth = i.width + fudgeFactorGen + interval;		
		
		gameIntervals.add(i);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		if (GameInstanceContainer.currentGameMode == GameMode.GAMEPLAY)
		{
			int drawFarmer = 0;
			for (Iterator<Interval> it = gameIntervals.iterator(); it.hasNext();)
			{
				Interval i = it.next();
				if (drawFarmer == 0  && Intersector.intersectRectangles(farmer.getCollisionBox(), i.getCollisionBox()))
				{
					i.draw(batch,farmer);
					drawFarmer = 1;
				}
				else
				{
					i.draw(batch);
				}
			}
			if (drawFarmer == 0) 
				farmer.draw(batch);
		}
	}
	
	public void drawDebug(ShapeRenderer r)
	{
		if (GameInstanceContainer.currentGameMode == GameMode.GAMEPLAY)
		{
			farmer.drawDebug(r);
			r.setColor(1, 1, 0, 1);
			r.box(xPosition, 300, 0, 10, 10, 0);
			for (Iterator<Interval> it = gameIntervals.iterator(); it.hasNext();)
			{
				it.next().drawDebug(r);
			}
		}
	}
	
	private float getSlidingSpeed()
	{
		return GameInstanceContainer.getSlidingSpeed(slidingSpeed);
	}
	
	public void gameplay(float deltaTime)
	{
		farmer.update(deltaTime);

		xPosition -= getSlidingSpeed() * deltaTime;

		Interval toRemove = null;
		for (Iterator<Interval> it = gameIntervals.iterator(); it.hasNext();)
		{
			Interval i = it.next();
			i.update(deltaTime);
			if (Intersector.intersectRectangles(farmer.getCollisionBox(), i.getCollisionBox()))
			{
				i.handleCollision(farmer);
			}
			else if (i.getX() < -i.getWidth())
			{
				toRemove = i;
			}
		}

		if (toRemove != null)
			gameIntervals.remove(toRemove);
		//increment the frame count
		stateTime += deltaTime;
		if (stateTime > (lastIntervalWidth)/getSlidingSpeed())  // interval = meters     slidingSpeed = meters/second = seconds
		{
			this.generateNewInterval();
			if (GameInstanceContainer.isAtMaxSpeed() && interval > minimumIntervalSize)
				interval -= intervalReductionSize;
			stateTime = 0;
		}

	}
	
	@Override
	public void update(float deltaTime) {
		
		if (GameInstanceContainer.currentGameMode == GameMode.GAMEPLAY)
		{
			if (oldGameMode != GameMode.GAMEPLAY)
			{
				reset();
				GameInstanceContainer.reset();
			}
			GameInstanceContainer.increaseWorldSpeed(deltaTime*speedIncreaseRate);
			gameplay(deltaTime);
		}
		oldGameMode = GameInstanceContainer.currentGameMode;
	}

	
}
