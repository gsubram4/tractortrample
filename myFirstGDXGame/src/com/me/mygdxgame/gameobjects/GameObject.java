package com.me.mygdxgame.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {

	protected float xPos = 0;
	protected float yPos = 0;
	
	public float getX() { return xPos; }
	public float getY() { return yPos; }
	
	abstract public void draw(SpriteBatch batch);
	abstract public void update(float deltaTime);
	
}