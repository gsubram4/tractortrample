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


public class LaserFence extends Entity{

	private static final CollisionResponse myCollisionResponse = CollisionResponse.KILLHERO;
	private static final String textureString = "laserfence";
	
	private static final float frameDuration = .1f;
	
	private static Array<? extends TextureRegion> spriteTextures;
	
	//private static LinkedList<Corn> unused = new LinkedList<Corn>();
	
	public static void Build(TextureAtlas atlas)
	{
		spriteTextures = atlas.findRegions(textureString);
	}
	
	
	public static LaserFence laserFactory()
	{
		return new LaserFence();
	}
		
	//-----------------NOT TOUCHED BY BUILDER----------------
		
		float stateTime = 0;
		Sprite body = null;
		
		Animation treeAnimation = null;
	
		protected LaserFence()
		{
			collisionResponse = myCollisionResponse;
			
			treeAnimation = new Animation(frameDuration, spriteTextures);
			treeAnimation.setPlayMode(Animation.LOOP_PINGPONG);
			body = new Sprite(treeAnimation.getKeyFrame(stateTime, true));
			
			
			setSprites();
			//TODO: Remove this
			Random r = new Random(System.nanoTime());
			stateTime += r.nextFloat();
		}
		
		protected void setSprites()
		{
			body.setPosition(xPos, yPos);
			setCollisionBox();
		}
		
		@Override
		public void draw(SpriteBatch batch) {
			body.draw(batch);
			
		}

		@Override
		public void update(float deltaTime) {
			xPos -= getSlidingSpeed() * deltaTime;
			
			setSprites();
			
			stateTime += deltaTime;
			body.setRegion(treeAnimation.getKeyFrame(stateTime,true));
		}


		@Override
		public float getWidth() {
			return body.getWidth();
		}


		@Override
		protected void setCollisionBox() {
			collisionBox.set(xPos+5 , yPos+5, body.getWidth()-5, body.getHeight()-5);
		}
		
		
}
