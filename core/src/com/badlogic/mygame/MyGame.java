package com.badlogic.mygame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.mygame.controller.LevelController;
import com.badlogic.mygame.controller.NewWorldLevelController;
import com.badlogic.mygame.controller.PlayerController;
import com.badlogic.mygame.model.Player;

// cameraController
public class MyGame implements ApplicationListener {

	private static MyGame instance = null;

	private OrthographicCamera camera;

	// later on I'll have here a MENU screen for example

	private MyGame(){
	}

	/**
	 * Singleton
	 */

	public static MyGame getInstance(){
		if(instance == null)
			instance = new MyGame();
		return instance;
	}

	/**
	 * Below, methods called just once
	 */
	
	@Override
	public void create () {
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//LevelController.initializeController();
		NewWorldLevelController.initializeController();
		PlayerController.initializeController();
		Player initPlayer = PlayerController.getInitializedPlayer();
		NewWorldLevelController.getPlayer(initPlayer);
	}

	@Override
	public void resize(int width, int height) {
	}

	/**
	 * Below, methods called every frame
	 */

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();

		// clean screen, removing all the graphics from before
		Gdx.gl.glClearColor(0.91f, 0.71f, 0.46f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// close the game when pressed Esc button
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
		// testing cleaning bodies
		if(Gdx.input.isKeyPressed(Input.Keys.R)){
			LevelController.clearLevel = true;
		}

		//LevelController.update(delta, this.camera);
		NewWorldLevelController.update(delta, this.camera);
		PlayerController.update(delta);
		//LevelController.draw(this.camera);
		NewWorldLevelController.draw(this.camera);

		cameraUpdate(PlayerController.player.getPosition());
	}

	private void cameraUpdate(Vector2 targetPosition){
		camera.position.set(targetPosition,0f);
		camera.update();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}


}
