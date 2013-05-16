package com.me.mygdxgame.gameobjects.intervalsystem;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.GameInstanceContainer.CollisionResponse;
import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.GameObject;

public abstract class Entity extends GameObject implements Comparable<Entity> {

	protected CollisionResponse collisionResponse = CollisionResponse.NOTHING;
	
	
	protected Rectangle collisionBox;
	protected SlidingSpeed slidingSpeed;
	abstract public float getWidth();
	protected abstract void setSprites();
	abstract protected void setCollisionBox();
	
	public Rectangle getCollisionBox() { return this.collisionBox; }
	
	public Entity setPosition(float xPos, float yPos) { this.xPos = xPos;	this.yPos = yPos; setSprites(); return this;}
	public Entity setSlidingSpeed(SlidingSpeed s) { this.slidingSpeed = s; return this; }
	public float getPositionX() { return this.xPos; }
	public float getPositionY() { return this.yPos; }
	
	
	public Entity()
	{
		slidingSpeed = SlidingSpeed.FOREGROUND;
		collisionBox = new Rectangle();
	}
	
	public void drawDebug(ShapeRenderer r) {
		r.setColor(0, 1, 0, 1);
		r.box(collisionBox.x, collisionBox.y, 0, collisionBox.width, collisionBox.height, 0);
	}
	
	protected float getSlidingSpeed()
	{
		return GameInstanceContainer.getSlidingSpeed(slidingSpeed);
	}
	
	public int compareTo(Entity compareEntity) {
		 
		if (compareEntity.collisionBox.y > this.collisionBox.y)
			return -1;
		else if (compareEntity.collisionBox.y < this.collisionBox.y)
			return 1;
		else
			return 0;

	}	
}
