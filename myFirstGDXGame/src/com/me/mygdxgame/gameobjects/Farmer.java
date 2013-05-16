package com.me.mygdxgame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.intervalsystem.Entity;

import static com.me.mygdxgame.GDXUtilities.height;
import static com.me.mygdxgame.GDXUtilities.width;
import static com.me.mygdxgame.GDXUtilities.farmerStartXPos;
import static com.me.mygdxgame.GDXUtilities.farmerStartYPos;
import static com.me.mygdxgame.GDXUtilities.farmerMovementSpeedX;
import static com.me.mygdxgame.GDXUtilities.farmerMovementSpeedY;

import static com.me.mygdxgame.GDXUtilities.initialWorldSpeed;

public class Farmer extends Entity {
	
	private static final Vector2 bodyOffset = new Vector2(0,11);
	private static final Vector2 bWheelOffset = new Vector2(8,-2);
	private static final Vector2 fWheelOffset = new Vector2(87,0);
	private static final Vector2 shadowOffset = new Vector2(3,-7);
	private static final String bodyTextureString = "tractorbody";
	private static final String rearWheelString = "rearwheel";
	private static final String frontWheelString = "frontwheel";
	private static final String shadowString = "charshadow";
	private static final float wheelFrameDuration = 0.04f;

	
	public void Build(TextureAtlas atlas)
	{
		fWheelTextures = atlas.findRegions(frontWheelString);
		rWheelTextures = atlas.findRegions(rearWheelString);
		bodyTexture = atlas.findRegion(bodyTextureString);
		shadowTexture = atlas.findRegion(shadowString);
	}
		
	public void reset()
	{
		xPos = farmerStartXPos;
		yPos = farmerStartYPos;
		movementSpeed = new Vector2(farmerMovementSpeedX, farmerMovementSpeedY);
	
		body = new Sprite(bodyTexture);
		bWheelAnim = new Animation(wheelFrameDuration, rWheelTextures);
		fWheelAnim = new Animation(wheelFrameDuration/2, fWheelTextures);
		
		fWheel = new Sprite(fWheelAnim.getKeyFrame(stateTime, true));
		bWheel = new Sprite(bWheelAnim.getKeyFrame(stateTime, true));
		
		shadow = new Sprite(shadowTexture);
			
		collisionBox = new Rectangle();
		
		stateTime = 0;
		sineWave = 0;
	}	
	
	//-----------------BUILDER----------------	
	
	Vector2 movementSpeed;

	
	//-----------------NOT TOUCHED BY BUILDER----------------
	Array<? extends TextureRegion> fWheelTextures;
	Array<? extends TextureRegion> rWheelTextures;
	TextureRegion bodyTexture;
	TextureRegion shadowTexture;
	
	float stateTime = 0;
	Sprite body = null;
	Sprite bWheel = null;
	Sprite fWheel = null;
	Sprite shadow = null;
	
	Animation bWheelAnim = null;
	Animation fWheelAnim = null;
	
	Animation poofAnim = null;
	//-----------------NOT TOUCHED BY BUILDER----------------
	float sineWave = 0;
	Vector2 touchPos = new Vector2();
	
	public Farmer(TextureAtlas atlas)
	{
		Build(atlas);
		reset();
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		shadow.draw(batch);
		body.draw(batch);
		bWheel.draw(batch);
		fWheel.draw(batch);
	}

	@Override
	public void update(float deltaTime) {
		// Handle Input
		int moveUp = 0;
		
		if(Gdx.input.isTouched()) {
		     
		      touchPos.set(Gdx.input.getX(), height - Gdx.input.getY());  //unproject these coords
		     
		      if (touchPos.y > yPos)
		      {
		    	  if (yPos < 373)
		    	  {
		    		  yPos += movementSpeed.y * GameInstanceContainer.getSlidingSpeed(SlidingSpeed.FOREGROUND) / initialWorldSpeed * deltaTime;
		    		  yPos -= movementSpeed.x * deltaTime;
		    	  }
		    	  
		      }
		      else
		      {
		    	  if (yPos > 181)
		    	  {
		    		  yPos -= movementSpeed.y * GameInstanceContainer.getSlidingSpeed(SlidingSpeed.FOREGROUND) / initialWorldSpeed * deltaTime;
		    		  xPos += movementSpeed.x * deltaTime;
		    	  }
		      }
		}/*
		if(Gdx.input.isTouched()) {
			moveUp = 1;
		}
		
		if (moveUp == 1)
	      {
	    	  if (yPos < 373)
	    	  {
	    		  yPos += movementSpeed.y * GameInstanceContainer.getSlidingSpeed(SlidingSpeed.FOREGROUND) / initialWorldSpeed * deltaTime;
	    		  yPos -= movementSpeed.x * deltaTime;
	    	  }
	    	  
	      }
	      else
	      {
	    	  if (yPos > 181)
	    	  {
	    		  yPos -= movementSpeed.y * GameInstanceContainer.getSlidingSpeed(SlidingSpeed.FOREGROUND) / initialWorldSpeed * deltaTime;
	    		  xPos += movementSpeed.x * deltaTime;
	    	  }
	      }*/
		
		setSprites();
		//Advance Animation
		stateTime += deltaTime;
		bWheel.setRegion(bWheelAnim.getKeyFrame(stateTime, true));
		fWheel.setRegion(fWheelAnim.getKeyFrame(stateTime,true));
		//Handle Collision
		setCollisionBox();
	}
	
	protected void setSprites()
	{
		body.setPosition(xPos + bodyOffset.x , yPos + bodyOffset.y + MathUtils.sin(stateTime*10));

		bWheel.setPosition(xPos + bWheelOffset.x, yPos + bWheelOffset.y);
		fWheel.setPosition(xPos + fWheelOffset.x, yPos + fWheelOffset.y);
		shadow.setPosition(xPos + shadowOffset.x, yPos + shadowOffset.y);
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return body.getWidth();
	}

	@Override
	protected void setCollisionBox() {
		// TODO Auto-generated method stub
		collisionBox.set(xPos + shadowOffset.y + 50, yPos, body.getWidth()/2, 30);
	}

	public void moveOffScreen()
	{
		this.xPos = -width;
		this.setSprites();
	}
	
}
