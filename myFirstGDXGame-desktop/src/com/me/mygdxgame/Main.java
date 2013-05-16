package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import static com.me.mygdxgame.GDXUtilities.height;
import static com.me.mygdxgame.GDXUtilities.width;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;


public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "myFirstGDXGame";
		cfg.useGL20 = true;
		cfg.width = width;
		cfg.height = height;
		cfg.useCPUSynch = false;
		cfg.vSyncEnabled = false;
		//cfg.fullscreen = true;
		
		Settings settings = new Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
       
        //TexturePacker2.process(settings, "../../Nabil/assets", "../myFirstGDXGame-android/assets/data", "game");
     
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
