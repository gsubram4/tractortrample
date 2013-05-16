package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.mygdxgame.GameInstanceContainer.GameMode;
import com.me.mygdxgame.GameInstanceContainer.SlidingSpeed;
import com.me.mygdxgame.gameobjects.Farmer;
import com.me.mygdxgame.gameobjects.GameBackground;
import com.me.mygdxgame.gameobjects.TitleScreen;
import com.me.mygdxgame.gameobjects.intervalsystem.NewIntervalSystem;


import static com.me.mygdxgame.GDXUtilities.width;
import static com.me.mygdxgame.GDXUtilities.height;
import static com.me.mygdxgame.GDXUtilities.initialWorldSpeed;
import static com.me.mygdxgame.GDXUtilities.initialIntervalSize;
import static com.me.mygdxgame.GDXUtilities.fudgeFactor;
import static com.me.mygdxgame.GDXUtilities.speedIncreaseRate;


import static com.me.mygdxgame.GDXUtilities.DEBUG;

import static com.me.mygdxgame.GameInstanceContainer.Score;

public class MyGdxGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private BitmapFont font;
	private BitmapFont impact;
	
	
	TitleScreen titleScreen;
	
	GameBackground backgroundLayer;
	GameInstanceContainer gameInstance;
	NewIntervalSystem intervals;
	
	TextureAtlas atlas;
	
	ShapeRenderer render;
	
	
	//(TODO) Make controlling speed A LOT easier
	@Override
	public void create() {
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);		
		camera.zoom = 1.0f;
		camera.update();
		
		batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("data/game.atlas"));
		
		GameInstanceContainer.initialize(GameMode.TITLESCREEN);
		titleScreen = new TitleScreen(atlas);
		backgroundLayer = new GameBackground(atlas, gameInstance);
		intervals = new NewIntervalSystem(atlas);
		
		font = new BitmapFont(Gdx.files.internal("data/Arial.fnt"), Gdx.files.internal("data/Arial.png"), false);
		impact = new BitmapFont(Gdx.files.internal("data/Impact.fnt"), Gdx.files.internal("data/Impact.png"), false);

		render = new ShapeRenderer();
		
		reset();
		intervals.moveFarmerOffScreen();
	}
	
	public void reset()
	{
		
		GameInstanceContainer.reset();
		intervals.reset();
		
	}
	

	@Override
	public void dispose() {
		batch.dispose();
		atlas.dispose();
	}
	

	
	private float deltaTime = 0;
	private void updateGame()
	{
		deltaTime = Gdx.graphics.getDeltaTime();
		
		titleScreen.update(deltaTime);
		
		backgroundLayer.update(deltaTime);
		intervals.update(deltaTime);
		
		
	}

	

	private void drawGame()
	{
		Gdx.gl.glClearColor(.529f, .807f, .98f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			backgroundLayer.draw(batch);
			intervals.draw(batch);
			
			font.draw(batch, "Intervals: "+intervals.gameIntervals.size(), 26, 80);
			font.draw(batch, "Speed: "+GameInstanceContainer.getSlidingSpeed(SlidingSpeed.FOREGROUND), 26, 50);
			font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 26, 20);
			impact.draw(batch, "SCORE : "+Score, 26, height - 50);
			
			titleScreen.draw(batch);
			
		batch.end();
		
		//TURN ON TO DEBUG
		if (DEBUG == true)
		{
			render.setProjectionMatrix(camera.combined);
			render.begin(ShapeType.Box);
			intervals.drawDebug(render);
			render.end();
		}
		
	}
	
	@Override
	public void render() {			
	
		updateGame();
		drawGame();
		
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
