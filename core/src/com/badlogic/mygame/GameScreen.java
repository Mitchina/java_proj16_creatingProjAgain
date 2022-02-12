package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.mygame.controller.LevelController;
import com.badlogic.mygame.controller.PlayerController;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    float tilesWidth = 14f;
    float tilesHeight = 14f;

    public GameScreen(OrthographicCamera camera){
        this.camera = camera;
        LevelController.initializeController(this);
        // player controller init goes here:
        PlayerController.initializeController();
    }

    private void update(){
        cameraUpdate();

        // close the game when pressed Esc button
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        // capture inputs - then, move camera
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            camera.position.x -=3f;
            PlayerController.player.position.x -= .05f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camera.position.x +=3f;
            PlayerController.player.position.x += .05f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            camera.position.y +=3f;
            PlayerController.player.position.y += .05f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            camera.position.y -=3f;
            PlayerController.player.position.y -= .05f;
        }
    }

    private void cameraUpdate(){
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
    }

    @Override
    public void render(float delta){
        // clean screen, removing all the graphics from before
        Gdx.gl.glClearColor(0.91f, 0.71f, 0.46f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.update();

        LevelController.update(delta, this.camera);
        // here I'll need to update the player too
        PlayerController.update(delta);
        LevelController.draw(this.camera);
    }

    @Override
    public void resize(int width, int height){
        camera.viewportWidth = tilesWidth; // display 14 pixel units
        camera.viewportHeight = tilesHeight * height / width;
        camera.update();
    }
}
