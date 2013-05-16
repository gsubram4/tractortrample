package com.me.mygdxgame.graphics;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.mygdxgame.GameInstanceContainer;

import static com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;

public class PlxMultiFill {

	public static class Builder {
		
		
		private SlidingSpeed slidingSpeed = SlidingSpeed.FOREGROUND;
		private int verticalPosition = 0;
		private int count = 1;
		
		private Sprite[] copySprites;
		
		public Builder(TextureAtlas atlas,String... tex)
		{
			this.copySprites = new Sprite[tex.length];
			for (int i = 0; i<tex.length; i++)
				copySprites[i]  = atlas.createSprite(tex[i]);
		}
		public Builder SlidingSpeed(SlidingSpeed s)
		{
			this.slidingSpeed = s;
			return this;
		}
		public Builder VerticalPosition(int i )
		{
			this.verticalPosition = i;
			return this;
		}
		public Builder Count(int i)
		{
			this.count = i;
			return this;
		}
		
		public PlxMultiFill build()
		{
			PlxMultiFill result = new PlxMultiFill((int)copySprites[0].getWidth());
			result.slidingSpeed = slidingSpeed;
			result.verticalPosition = verticalPosition;
			result.count = count;
			result.build(copySprites);
			return result;
		}
	}
	
	
	private SlidingSpeed slidingSpeed;
	private int verticalPosition = 0;
	private int textureWidth = 0;
	private int count = 1;
	
	
	private int copySprites_length = 0;
	private Sprite[] sprites;
	
	private PlxMultiFill(int width)
	{
		this.textureWidth = width;
		
	}
	
	/**
	 * (TODO) Clean this function up!
	 * @param copySprites
	 */
	private void build(Sprite[] copySprites)
	{
		this.copySprites_length = copySprites.length;
		this.sprites = new Sprite[count];
		for (int i = 0; i<count; i++)
		{
			sprites[i] = new Sprite(copySprites[i%copySprites_length]);
			sprites[i].setPosition(i * textureWidth, verticalPosition);
		}
		
	}
	
	public void update(float deltaTime)
	{
		float slidingSpeedFloat = GameInstanceContainer.getSlidingSpeed(slidingSpeed);
		
		for (int i = 0; i< sprites.length; i++)
		{
			sprites[i].translateX(-slidingSpeedFloat * deltaTime);
			if (sprites[i].getX() < -textureWidth)
			{
				sprites[i].setX((sprites.length-1)* textureWidth);
			
			}
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		for (int i =0; i< sprites.length; i++)
		{
			sprites[i].draw(batch);
		}
		
	}
	
	
}
