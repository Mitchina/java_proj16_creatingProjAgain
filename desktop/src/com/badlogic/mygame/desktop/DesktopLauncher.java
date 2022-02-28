package com.badlogic.mygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

// importing the Game app
import com.badlogic.mygame.MyGame;

public class DesktopLauncher {

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// name at the top of the window
		config.title = "MyGame";
		config.resizable = false;

		config.width = WINDOW_WIDTH;
		config.height = WINDOW_HEIGHT;

		// calling the Game App
		new LwjglApplication(MyGame.getInstance(), config);
	}
}
