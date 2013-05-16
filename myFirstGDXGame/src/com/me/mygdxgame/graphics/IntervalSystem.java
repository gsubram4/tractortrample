package com.me.mygdxgame.graphics;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.GameInstanceContainer;


import static com.me.mygdxgame.GDXUtilities.width;
import static com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;

public class IntervalSystem {

	public static class Builder {

		private int interval = 0;
		private int fudgeFactor = 0;
		private SlidingSpeed slidingSpeed = SlidingSpeed.FOREGROUND;
		private int initialNumToGenerate = 0;
		private int verticalPosition = 0;
		
		private Random randomNumberGen;
		
		TextureRegion[] textureRegions;
		
		public Builder(TextureAtlas atlas,String... tex)
		{
			this.textureRegions = new TextureRegion[tex.length];
			for (int i = 0; i<tex.length; i++)
				textureRegions[i]  = atlas.findRegion(tex[i]);
			
		}
		public Builder SlidingSpeed(SlidingSpeed s)
		{
			this.slidingSpeed = s;
			return this;
		}
		
		public Builder fudgeFactor(int i) { this.fudgeFactor = i; return this; }
		public Builder interval(int i) { this.interval = i; return this; }
		public Builder initialNumToGenerate(int i ) { this.initialNumToGenerate = i; return this; }
		public Builder verticalPosition(int i) { this.verticalPosition = i; return this; }
		public Builder randomNumberGen(Random r) { this.randomNumberGen = r; return this; }
		
		public IntervalSystem build()
		{
			IntervalSystem i = new IntervalSystem();
			i.slidingSpeed = slidingSpeed;
			i.fudgeFactor = fudgeFactor;
			i.interval = interval;
			i.initialNumToGenerate = initialNumToGenerate;
			i.verticalPosition = verticalPosition;
			i.randomNumberGen = randomNumberGen;
			i.textureRegions = textureRegions;
			i.Build();
			return i;
		}
		
	}
	
	private int interval = 0;
	private int fudgeFactor = 0;
	private SlidingSpeed slidingSpeed;
	private int initialNumToGenerate = 0;
	private int verticalPosition = 0;
	
	private Random randomNumberGen;
	
	private float counter = 0; //used to determine if it's time to make a new object
	
	TextureRegion[] textureRegions;
	Array<Sprite> sprites;
	
	private TextureRegion getRandomTextureRegion()
	{
		return this.textureRegions[randomNumberGen.nextInt(textureRegions.length)];
	}
	
	private int getFudgeFactor()
	{
		return (randomNumberGen.nextInt(fudgeFactor)-fudgeFactor/2);
	}
	
	private void Build()
	{
		if (randomNumberGen == null)
			randomNumberGen = new Random(System.nanoTime());
		sprites = new Array<Sprite>(false, initialNumToGenerate);
		for (int i = 0; i< initialNumToGenerate; i++)
		{
			Sprite s = new Sprite(getRandomTextureRegion());
			s.setPosition(width + (i * interval) + getFudgeFactor(), verticalPosition);
			sprites.add(s);
		}
	}
	
	public int getVerticalPosition() { return this.verticalPosition; }
	
	
	public void update(float deltaTime)
	{
		float slidingSpeedFloat = GameInstanceContainer.getSlidingSpeed(slidingSpeed);
		
		Sprite toRemove = null;
		int positionOfLast = 0;
		for (int i = 0; i< sprites.size; i++)
		{
			Sprite s = sprites.get(i);
			s.translateX(-slidingSpeedFloat * deltaTime);
			if (s.getX() < -s.getWidth())
			{
				toRemove = s;
			}
			if (s.getX() > positionOfLast)
				positionOfLast = (int) s.getX();
		}
		
		if (toRemove != null)
			sprites.removeValue(toRemove, true);
		
		//increment the frame count
		counter = counter + deltaTime;
		if (counter > interval/slidingSpeedFloat)  // interval = px slidingSpeed = px/second = seconds
		{
			Sprite s = new Sprite(getRandomTextureRegion());
			s.setPosition(positionOfLast + interval + getFudgeFactor(), verticalPosition);
			sprites.add(s);
			counter = 0;
		}
	}
	
	
	public void drawDebug(ShapeRenderer r)
	{
		for (int i = 0; i< sprites.size; i++)
		{
			Sprite s = sprites.get(i);
			r.box(s.getX(),verticalPosition, 0, 24, 10, 0);
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		for (int i =0; i< sprites.size; i++)
		{
			sprites.get(i).draw(batch);
		}
		
	}
}
