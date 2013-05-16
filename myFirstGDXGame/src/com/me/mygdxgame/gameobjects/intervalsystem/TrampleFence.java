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
public class TrampleFence extends Entity{

////STEP 2 Here!
	private static final CollisionResponse myCollisionResponse = CollisionResponse.DESTROYSELF;
	private static final String bodyString = "fenceTrample";
	private static final float bodyOffsetY = 0f;
	private static final float bodyOffsetX = 0f;
	private static final float frameDuration = .1f;
	
	private static Array<? extends TextureRegion> bodyTextures;
	private static TextureRegion shadowTexture;

	public static void Build(TextureAtlas atlas)
	{
		bodyTextures = atlas.findRegions(bodyString);
	}
	
	public static TrampleFence Factory()
	{
		return new TrampleFence();
	}
		
		
	//-----------------NOT TOUCHED BY BUILDER----------------
		
		float stateTime = 0;
		Sprite body = null;
		
		Animation bodyAnimation = null;
		Animation shadowAnimation = null;
	
		protected TrampleFence()
		{
			collisionResponse = myCollisionResponse;
			bodyAnimation = new Animation(frameDuration, bodyTextures);
			bodyAnimation.setPlayMode(Animation.LOOP_PINGPONG);
			body = new Sprite(bodyAnimation.getKeyFrame(stateTime, true));
			
			
			setSprites();

			Random r = GameInstanceContainer.random;
			stateTime += r.nextFloat();
		}
		
		protected void setSprites()
		{
			body.setPosition(xPos + bodyOffsetX, yPos + bodyOffsetY);
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
			body.setRegion(bodyAnimation.getKeyFrame(stateTime,true));
		}


		@Override
		public float getWidth() {
			return body.getWidth();
		}

	/////STEP 3: Set your collisionBox to be correct!
		@Override
		protected void setCollisionBox() {
			collisionBox.set(xPos+5 , yPos+5, body.getWidth()-10, 56);
		}
		
		
		
}
