package com.me.mygdxgame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.GameInstanceContainer.GameMode;

import static com.me.mygdxgame.GDXUtilities.height;
import static com.me.mygdxgame.GDXUtilities.width;

import static com.me.mygdxgame.GameInstanceContainer.currentGameMode;

public class TitleScreen extends GameObject {

	private static final String titleString = "titlescreen";
	private static final String tapString = "taptotrample";
	
	private TextureRegion titleTexture;
	private TextureRegion tapTexture;
	
	private Sprite titleSprite;
	private Sprite tapSprite;
	
	float blinkRateOff = .300f;
	float blinkRateOn = 1.000f;
	
	float blinkRate = blinkRateOn;
	float stateTime = 0;
	boolean drawTapSprite = true;
	
	
	public TitleScreen(TextureAtlas atlas)
	{
		Build(atlas);
	}
	
	public void Build(TextureAtlas atlas)
	{
		titleTexture = atlas.findRegion(titleString);
		tapTexture = atlas.findRegion(tapString);
		
		titleSprite = new Sprite(titleTexture);
		tapSprite = new Sprite(tapTexture);
		
		//titleSprite.setOrigin(titleSprite.getWidth()/2, titleSprite.getHeight()/2);
		//tapSprite.setOrigin(tapSprite.getWidth()/2, tapSprite.getHeight()/2);
		
		titleSprite.setPosition(width/2 - titleSprite.getWidth()/2, height/2 - titleSprite.getHeight()/2 + tapSprite.getHeight());
		tapSprite.setPosition( width/2 - tapSprite.getWidth()/2, height/2 - tapSprite.getHeight()/2 - titleSprite.getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		if (currentGameMode == GameMode.TITLESCREEN)
		{
			batch.draw(GameInstanceContainer.transparency,0,0);
			titleSprite.draw(batch);
			if (drawTapSprite == true)
				tapSprite.draw(batch);
		}
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		if (currentGameMode == GameMode.TITLESCREEN)
		{
			stateTime += deltaTime;

			if (stateTime > blinkRate)
			{
				stateTime = 0;
				drawTapSprite ^= true;
				if (drawTapSprite == false)
					blinkRate = blinkRateOff;
				else
					blinkRate = blinkRateOn;
			}

			if(Gdx.input.isTouched()) 
			{
				currentGameMode = GameMode.GAMEPLAY;
			}
		}
	}

}
