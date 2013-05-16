package com.me.mygdxgame.gameobjects.intervalsystem;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.GameInstanceContainer.CollisionResponse;

/////STEP 1: Copy + Rename file to your new object
/////STEP 2: Set your Variables
/////STEP 3: Fix Collision (See Below)
/////STEP 4: Add your class (i.e. Corn) into NewIntervalSystem (See step 4 in NewIntervalSystem.java)
/////STEP 4: Go Make a Factory! (See CornFactory.java)
public class Corn extends Entity{

////STEP 2 Here!
	private static final CollisionResponse myCollisionResponse = CollisionResponse.SCORE;
	private static final String bodyString = "cornspark";
	private static final String shadowString = "produceShadow";
	private static final float bodyOffsetY = 14f;
	private static final float bodyOffsetX = 0f;
	private static final float shadowOffsetY = 0f;
	private static final float shadowOffsetX = 5f;
	private static final float frameDuration = .1f;
	
	private static Array<? extends TextureRegion> bodyTextures;
	private static TextureRegion shadowTexture;

	public static void Build(TextureAtlas atlas)
	{
		bodyTextures = atlas.findRegions(bodyString);
		shadowTexture = atlas.findRegion(shadowString);
	}
	
	public static Corn Factory()
	{
		return new Corn();
	}
		
		
	//-----------------NOT TOUCHED BY BUILDER----------------
		
		float stateTime = 0;
		Sprite body = null;
		Sprite shadow = null;
		
		Animation bodyAnimation = null;
		Animation shadowAnimation = null;
	
		protected Corn()
		{
			collisionResponse = myCollisionResponse;
			bodyAnimation = new Animation(frameDuration, bodyTextures);
			bodyAnimation.setPlayMode(Animation.LOOP_PINGPONG);
			body = new Sprite(bodyAnimation.getKeyFrame(stateTime, true));
			
			shadow = new Sprite(shadowTexture);
			
			setSprites();

			Random r = GameInstanceContainer.random;
			stateTime += r.nextFloat();
		}
		
		protected void setSprites()
		{
			body.setPosition(xPos + bodyOffsetX, yPos + bodyOffsetY);
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

	/////STEP 3: Set your collisionBox to be correct!
		@Override
		protected void setCollisionBox() {
			collisionBox.set(xPos + shadowOffsetX, yPos+shadowOffsetY, body.getWidth(), shadow.getHeight());
		}
		
		
		
}
