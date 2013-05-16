package com.me.mygdxgame.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.mygdxgame.GameInstanceContainer;
import com.me.mygdxgame.graphics.IntervalSystem;
import com.me.mygdxgame.graphics.PlxMultiFill;
import com.me.mygdxgame.graphics.PlxSingle;

import static com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;

public class GameBackground extends GameObject {

	
	PlxSingle background;
	IntervalSystem foreground;

	PlxSingle playableArea;
	PlxSingle cutAway;
	PlxSingle road;
	PlxMultiFill fences;
	
	TextureAtlas atlas;
	
	GameInstanceContainer gameInstance;
	
	public GameBackground(TextureAtlas atlas, GameInstanceContainer gameInstance)
	{
		this.atlas = atlas;
		this.gameInstance = gameInstance;
		create();
	}
	
	public void create()
	{
		
		background = new PlxSingle.Builder(atlas,"PlxBgMtn").verticalPosition(478).slidingSpeed(SlidingSpeed.BACKGROUNDLAYER2).overlap(50).build();
		foreground = new IntervalSystem.Builder(atlas, "silosmall","windmillsmall","reactor","giantwheat","barnBk","hayBale").verticalPosition(475).SlidingSpeed(SlidingSpeed.BACKGROUNDLAYER1).initialNumToGenerate(3).interval(500).fudgeFactor(100).build();
		road = new PlxSingle.Builder(atlas, "road").verticalPosition(400).slidingSpeed(SlidingSpeed.BACKGROUNDLAYER1).build();
		playableArea = new PlxSingle.Builder(atlas,"playableArea").verticalPosition(0).slidingSpeed(SlidingSpeed.FOREGROUND).build();
		cutAway = new PlxSingle.Builder(atlas,"cutAway").verticalPosition(0).slidingSpeed(SlidingSpeed.FOREGROUND).build();
		fences = new PlxMultiFill.Builder(atlas,"fence1").VerticalPosition(395).SlidingSpeed(SlidingSpeed.FOREGROUND).Count(3).build();
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {

		
		background.draw(batch);
		road.draw(batch);
		foreground.draw(batch);
		fences.draw(batch);
		
		playableArea.draw(batch);
		cutAway.draw(batch);
	}
	@Override
	public void update(float deltaTime) {

		background.update(deltaTime);
		foreground.update(deltaTime);
		road.update(deltaTime);
		playableArea.update(deltaTime);
		cutAway.update(deltaTime);
		fences.update(deltaTime);
	}
	
}
