package com.me.mygdxgame;

import static com.me.mygdxgame.GDXUtilities.initialWorldSpeed;
import static com.me.mygdxgame.GDXUtilities.maxWorldSpeed;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameInstanceContainer {

	public static enum CollisionResponse
	{
		KILLHERO,
		SCORE,
		DESTROYSELF,
		NOTHING
	}
	
	public static enum SlidingSpeed 
	{
		BACKGROUNDLAYER2,
		BACKGROUNDLAYER1,
		FOREGROUND
	}
	
	public static enum GameMode
	{
		TITLESCREEN,
		GAMEPLAY
	}
	
	private static float worldSlidingSpeed; //240 px / second 
	private static float backgroundLayer2Speed; //px /second
	private static float backgroundLayer1Speed; //px / second
	private static float foregroundSlidingSpeed;
	
	public static GameMode currentGameMode;
	public static int Score;
	
	public static Texture transparency;
	
	public static Random random;
	
	public static void initialize(GameMode startMode)
	{
		currentGameMode = startMode;
		transparency = new Texture(Gdx.files.internal("data/transparency.png"));
		random = new Random();
	}
	
	public static void setWorldSpeed(float speed)
	{
		worldSlidingSpeed = speed; //240 px / second 
		backgroundLayer2Speed = worldSlidingSpeed / 4; //px /second
		backgroundLayer1Speed = worldSlidingSpeed / 2; //px / second
		foregroundSlidingSpeed = worldSlidingSpeed;
	}
	
	public static void reset()
	{
		setWorldSpeed(initialWorldSpeed);
		Score = 0;
	}
	
	public static void increaseWorldSpeed(float speed)
	{
		if (worldSlidingSpeed < maxWorldSpeed)
		{
			worldSlidingSpeed += speed; //240 px / second 
			backgroundLayer2Speed = worldSlidingSpeed / 4; //px /second
			backgroundLayer1Speed = worldSlidingSpeed / 2; //px / second
			foregroundSlidingSpeed = worldSlidingSpeed;
		}
	}
	
	public static boolean isAtMaxSpeed()
	{
		if (worldSlidingSpeed < maxWorldSpeed)
			return false;
		return true;
	}
	
	public static float getSlidingSpeed(SlidingSpeed s)
	{
		switch(s) { 
		case BACKGROUNDLAYER2:
			return backgroundLayer2Speed;
		case BACKGROUNDLAYER1:
			return backgroundLayer1Speed;
		case FOREGROUND:
			return foregroundSlidingSpeed;
		default:
			return 0;
		}
	}
	
	
}
