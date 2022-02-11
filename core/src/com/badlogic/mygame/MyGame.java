package com.badlogic.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.mygame.GameScreen;

// cameraController
public class MyGame extends Game {
	/*SpriteBatch batch;
	Texture img;
	 */

	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;

	float tilesWidth = 14f;
	float tilesHeight = 14f;

	public static MyGame instance;

	public MyGame(){
		instance = this;
	}
	
	@Override
	public void create () {
		/*
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		 */

		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();

		this.orthographicCamera = new OrthographicCamera(tilesWidth, tilesHeight * (heightScreen/widthScreen));
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		this.orthographicCamera.position.set(this.orthographicCamera.viewportWidth/2f, this.orthographicCamera.viewportWidth/2f, 0);

		// passing the GameScreen
		setScreen(new GameScreen(orthographicCamera));
	}
	/*
	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		/*
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		 */
	//}
	/*
	
	@Override
	public void dispose () {
		/*
		batch.dispose();
		img.dispose();
		 */
	//}
}
