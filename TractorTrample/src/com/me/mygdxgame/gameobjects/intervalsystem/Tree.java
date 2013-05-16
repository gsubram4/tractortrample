package com.me.mygdxgame.gameobjects.intervalsystem;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.GameInstanceContainer.CollisionResponse;
import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;


public class Tree extends Entity{
	private static final CollisionResponse myCollisionResponse = CollisionResponse.KILLHERO;
	private static final String bodyString = "tree";
	private static final String shadowString = "charshadow";
	private static final float shadowOffsetY = 10f;
	private static final float shadowOffsetX = 51f;
	
	private static final float frameDuration = .1f;
	
	private static Array<? extends TextureRegion> bodyTextures;
	private static TextureRegion shadowTexture;
	
	//private static LinkedList<Corn> unused = new LinkedList<Corn>();
	
	public static void Build(TextureAtlas atlas)
	{
		bodyTextures = atlas.findRegions(bodyString);
		shadowTexture = atlas.findRegion(shadowString);
	}
	
	
	public static Tree treeFactory()
	{
		return new Tree();
	}

	//-----------------NOT TOUCHED BY BUILDER----------------
		
		float stateTime = 0;
		Sprite body = null;
		Sprite shadow = null;
		
		Animation bodyAnimation = null;
		Animation shadowAnimation = null;
	
		protected Tree()
		{
			collisionResponse = myCollisionResponse;
			
			bodyAnimation = new Animation(frameDuration, bodyTextures);
			bodyAnimation.setPlayMode(Animation.LOOP_PINGPONG);
			body = new Sprite(bodyAnimation.getKeyFrame(stateTime, true));
			
			shadow = new Sprite(shadowTexture);
			
			setSprites();
			//TODO: Remove this
			Random r = new Random(System.nanoTime());
			stateTime += r.nextFloat();
		}
		
		protected void setSprites()
		{
			body.setPosition(xPos, yPos);
			shadow.setPosition(xPos + shadowOffsetX, yPos + shadowOffsetY);
			setCollisionBox();
		}
		
		@Override
		public void draw(SpriteBatch batch) {
			shadow.draw(batch);
			body.draw(batch);
			
		}

		@Override
		public void update(float deltaTime) {
			xPos -= getSlidingSpeed() * deltaTime;
			
			setSprites();
			
			stateTime += deltaTime;
			body.setRegion(bodyAnimation.getKeyFrame(stateTime,true));
		}


		@Override
		public float getWidth() {
			return body.getWidth();
		}


		@Override
		protected void setCollisionBox() {
			collisionBox.set(xPos + shadowOffsetX, yPos+shadowOffsetY, shadow.getWidth()-5, shadow.getHeight()-5);
		}
		
		
}
