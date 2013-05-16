package com.me.mygdxgame.graphics;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.me.mygdxgame.GDXUtilities.width;

public class PlxMultiRandom {
public static class Builder {
		
		
		private float slidingSpeed = 0f;
		private int verticalPosition = 0;
		
		private int count = 0;
		private int min = 1;
		private int max = 1;
		
		private Sprite[] copySprites;
		
		public Builder(TextureAtlas atlas,String... tex)
		{
			this.copySprites = new Sprite[tex.length];
			for (int i = 0; i<tex.length; i++)
			{
				copySprites[i]  = atlas.createSprite(tex[i]);
				if (copySprites[i] == null)
					System.out.println("WHOA WHOA");
			}
		}
		public Builder SlidingSpeed(float f)
		{
			this.slidingSpeed = f;
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
		public Builder MinimumInterval(int i)
		{
			this.min = i;
			return this;
		}
		
		public Builder MaximumInterval(int i)
		{
			this.max = i;
			return this;
		}
		
		public PlxMultiRandom build()
		{
			PlxMultiRandom result = new PlxMultiRandom(copySprites);
			result.slidingSpeed = slidingSpeed;
			result.verticalPosition = verticalPosition;
			result.count = count;
			result.max = max;
			result.min = min;
			result.build();
			return result;
		}
	}
	
	
	private float slidingSpeed = 0f;
	private int verticalPosition = 0;
	private int count = 0;
	private int min = 1;
	private int max = 1;
	
	private Sprite[] copySprites;
	private Sprite[] sprites;
	
	private Random randomNumberGen;
	
	private PlxMultiRandom(Sprite[] copySprites)
	{
		this.copySprites = copySprites;
		this.randomNumberGen = new Random(System.nanoTime());
	}
	
	/**+
	 * (TODO) Clean this function up!
	 * @param copySprites
	 */
	private void build()
	{
		sprites = new Sprite[count];
		for (int i = 0; i < count; i++)
		{
			sprites[i] = new Sprite(copySprites[randomNumberGen.nextInt(copySprites.length)]);
			sprites[i].setPosition(width + (i * min) + randomNumberGen.nextInt(max), verticalPosition);
		}
	}
	
	/**
	 * Watch out for that plus 10 
	 */
	public void update()
	{
		for (int i = 0; i< sprites.length; i++)
		{
			sprites[i].translateX(-slidingSpeed);
			if (sprites[i].getX() < -sprites[i].getWidth())
			{
				sprites[i] = new Sprite(copySprites[randomNumberGen.nextInt(copySprites.length)]);
				sprites[i].setPosition(width+min+randomNumberGen.nextInt(max),verticalPosition);
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
