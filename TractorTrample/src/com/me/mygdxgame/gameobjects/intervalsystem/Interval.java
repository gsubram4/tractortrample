package com.me.mygdxgame.gameobjects.intervalsystem;

import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.GameInstanceContainer.CollisionResponse;
import com.me.mygdxgame.GameInstanceContainer.GameMode;
import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.Farmer;
import com.me.mygdxgame.gameobjects.GameObject;



public class Interval extends  GameObject{

	
	Array<Entity> intervalEntities;
	float width;
	float xPos;
	SlidingSpeed slidingSpeed;
	Rectangle collisionBox;

	public static Interval newInterval(float xPos, float width, SlidingSpeed slidingSpeed,Entity[] entities)
	{
		return new Interval(xPos, width, slidingSpeed, entities);
	}
	
	private Interval(float xPos, float width, SlidingSpeed slidingSpeed, Entity[] ent) {
		this.xPos = xPos;
		this.width = width;
		this.slidingSpeed = slidingSpeed;	
		
		Arrays.sort(ent, Collections.reverseOrder());
		
		this.intervalEntities = new Array<Entity>(true,ent); 
		collisionBox = new Rectangle();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		for (int i = 0; i<intervalEntities.size; i++)
			intervalEntities.get(i).draw(batch);
	}
	
	public void draw(SpriteBatch batch, Farmer f)
	{
		int drawFarmer = 0;
		for (int i = 0; i<intervalEntities.size; i++)
		{
			Entity e = intervalEntities.get(i);
			Rectangle col = e.getCollisionBox();
			Rectangle fcol = f.getCollisionBox();
			if (col.getY() > fcol.getY() || drawFarmer == 1)
			{
				e.draw(batch);
			}
			else
			{
				f.draw(batch);
				e.draw(batch);
				drawFarmer = 1;
			}
		}
		if (drawFarmer == 0)
			f.draw(batch);
	}
	
	public void drawDebug(ShapeRenderer r) {
		// TODO Auto-generated method stub
		r.setColor(0, 0, 1, 1);
		r.box(collisionBox.x, collisionBox.y, 0, collisionBox.width, collisionBox.height, 0);
		for (int i = 0; i<intervalEntities.size; i++)
			intervalEntities.get(i).drawDebug(r);
	}

	private float getSlidingSpeed()
	{
		return GameInstanceContainer.getSlidingSpeed(slidingSpeed);
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		xPos -= getSlidingSpeed() * deltaTime;
		for (int i = 0; i<intervalEntities.size; i++)
			intervalEntities.get(i).update(deltaTime);
		
		collisionBox.set(xPos, 181, width, 220);
	} 
	
	public float getX() { return this.xPos; }
	public float getWidth() { return this.width; }
	public Rectangle getCollisionBox() { return this.collisionBox; }
	//TODO: this is bad and messy
	public void handleCollision(Farmer f)
	{
		Array<Entity> s = new Array<Entity>(false,intervalEntities.size);
		for (int i = 0; i<intervalEntities.size; i++)
		{
			if (Intersector.intersectRectangles(f.getCollisionBox(), intervalEntities.get(i).getCollisionBox()))
			{
				if (intervalEntities.get(i).collisionResponse == CollisionResponse.SCORE)
				{
					GameInstanceContainer.Score++;
					s.add((intervalEntities.get(i)));
				}
				else if (intervalEntities.get(i).collisionResponse == CollisionResponse.KILLHERO)
				{
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					GameInstanceContainer.currentGameMode = GameMode.TITLESCREEN;
				}
				else if (intervalEntities.get(i).collisionResponse == CollisionResponse.DESTROYSELF)
				{
					s.add((intervalEntities.get(i)));
				}
			}
		}
		intervalEntities.removeAll(s, true);
	}
}
