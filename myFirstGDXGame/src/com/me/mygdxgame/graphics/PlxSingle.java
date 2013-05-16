package com.me.mygdxgame.graphics;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.GameInstanceContainer;

/**
 * @author eh542d
 *
 */
public class PlxSingle {
	public static class Builder {
		//TODO Change this to TextureRegion
		private Sprite copySprite;
		
		private SlidingSpeed slidingSpeed = SlidingSpeed.FOREGROUND;
		private int verticalPosition = 0;
		private int overlap = 0;
		
		public Builder(TextureAtlas atlas, String spriteName)
		{
			this.copySprite = atlas.createSprite(spriteName);
		}
		
		public Builder slidingSpeed(SlidingSpeed s)
		{
			this.slidingSpeed = s;
			return this;
		}
		
		public Builder verticalPosition(int i)
		{
			this.verticalPosition = i;
			return this;
		}
		
		public Builder overlap(int i)
		{
			this.overlap = i;
			return this;
		}
		
		public PlxSingle build()
		{
			PlxSingle result = new PlxSingle();
			result.slidingSpeed = slidingSpeed;
			result.verticalPosition = verticalPosition;
			result.overlap = overlap;
			result.build(copySprite);
			return result;
		}
	}
	
	Sprite[] mySprites;
	
	SlidingSpeed slidingSpeed;
	int verticalPosition;
	int width = 0;
	int overlap = 0;
	
	/**
	 * @param tex
	 */
	private PlxSingle()
	{
		
	}
	
	private void build(Sprite copySprite)
	{
		width = (int) copySprite.getWidth()-1;
		
		mySprites = new Sprite[2];
		mySprites[0] = new Sprite(copySprite);
		mySprites[1] = new Sprite(copySprite);
		
		mySprites[0].setPosition(0, verticalPosition);
		mySprites[1].setPosition(width-overlap, verticalPosition);
		
	}
	
	public void update(float deltaTime)
	{

		float slidingDelta = GameInstanceContainer.getSlidingSpeed(slidingSpeed) * deltaTime;

		mySprites[0].translateX(-slidingDelta);
		mySprites[1].setX(mySprites[0].getX()+width);
		
		if (mySprites[0].getX() < -width)
		{
			
			Sprite s = mySprites[1];
			mySprites[0].setX(s.getX()+width);
			
			mySprites[1] = mySprites[0];
			mySprites[0] = s; 
		}
		
	}
	
	public void draw(SpriteBatch batch)
	{
		for (int i =0; i<2; i++)
		{
			mySprites[i].draw(batch);
		}
	}
	
	
}
