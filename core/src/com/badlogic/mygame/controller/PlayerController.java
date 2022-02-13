package com.badlogic.mygame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.mygame.model.Player;

public class PlayerController {
    public static Player player;
    static String player2SpritesRelativePath = "player2AnimationSheet.png";
    static float playerVelocity = 0.05f;

    public static void initializeController(){
        player = new Player(new Vector2(5,5), 48, 48, player2SpritesRelativePath, playerVelocity);
    }
    public static void update(float deltaTime){
        player.update(deltaTime);
        handleInput();
    }

    public static void draw(Batch spriteBatch) {
        player.draw(spriteBatch);
    }

    public static void handleInput(){
        Vector2 movDir = new Vector2(0f,0f);
        float SPEED;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            SPEED = 1.5f; // faster speed
        }
        else{
            SPEED = 1f; // normal speed
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            movDir.add(new Vector2(-playerVelocity*SPEED, 0f));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            movDir.add(new Vector2(playerVelocity*SPEED, 0f));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            movDir.add(new Vector2(0f, playerVelocity*SPEED));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //camera.position.y -=3f;
            movDir.add(new Vector2(0f, -playerVelocity*SPEED));
        }

        /*
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            //System.out.println("Camera X Position Before" + camera.position.x); // 7
            //camera.position.set(camera.viewportWidth/2f-7, camera.viewportHeight/2f, 0);
            //camera.position.x -=7f;
            movDir.add(new Vector2(-SPEED, 0f));
            /*
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                movDir.add(new Vector2(-VELOCITY*2, 0f));
            }*/
        /*}
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            //camera.position.x +=3f;
            movDir.add(new Vector2(SPEED, 0f));
            /*
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                movDir.add(new Vector2(VELOCITY*2, 0f));
            }*/
        /*}
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            //camera.position.y +=3f;
            movDir.add(new Vector2(0f, SPEED));
            /*
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                movDir.add(new Vector2(0f, VELOCITY*2));
            }*/
        /*}
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //camera.position.y -=3f;
            movDir.add(new Vector2(0f, -SPEED));
            /*
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                movDir.add(new Vector2(0f, -VELOCITY*2));
            }*/
        /*}*/

        player.setMovDir(movDir);
        player.setSpriteVelocity(SPEED);
        System.out.println("--------Controlling if SPEED increase when I press Shift Left");
        System.out.println(SPEED);
    }


    /*

    // private can only be executed here in PlayerController
    // get the movDir input to change the velocity and position of the box2d
    // than, the player position needs to follow the box 2d
    public static void handleInput(Vector2 movDir, float movementAngle){
        // read input, update player's position
        // velocity // get from physicsBody
        Vector2 physicsBodyVelocity = player.physicsBody.getLinearVelocity();
        System.out.println("handleInput physicsBody velocity: " + physicsBodyVelocity); // (0.0,0.0)
        Vector2 physicsBodyPosition = player.physicsBody.getPosition();
        System.out.println("handleInput physicsBody position: " + physicsBodyPosition); // (5.0,5.0)
        player.VELOCITY = 1f;

        //physicsBodyVelocity.add(movDir);
        //System.out.println("handleInput movDir velocity: " + physicsBodyVelocity); // (0.05,0.0)
        //physicsBodyPosition.add(movDir);
        //System.out.println("handleInput movDir position: " + physicsBodyPosition); // (5.05,5.0)

        //System.out.println("MOVEMENT ANGLE: " + movementAngle);
        //System.out.println("movDir: " + movDir);

        if(movementAngle== 0 && movDir.x == 0 && movDir.y== 0){
            System.out.println("I'm stand................");
            player.setCurrentAnimation("idleFront");
        }
        if(movementAngle== 0 && movDir.x != 0){
            System.out.println("I'm going Right................");
            player.setCurrentAnimation("walkRight");
            //update player position
            player.physicsBody.applyLinearImpulse(player.VELOCITY, player.VELOCITY, physicsBodyPosition.x, physicsBodyPosition.y, true);
            player.physicsBody.setLinearVelocity(physicsBodyVelocity.x, physicsBodyVelocity.y);
        }
        if(movementAngle== 180 && movDir.x != 0){
            System.out.println("I'm going Left................");
            player.setCurrentAnimation("walkLeft");
        }
        if(movementAngle== 270 && movDir.y != 0){
            System.out.println("I'm going Up................");
            player.setCurrentAnimation("walkBack");
        }
        if(movementAngle== 90 && movDir.y != 0){
            System.out.println("I'm going Down................");
            player.setCurrentAnimation("walkFront");
        }
    }*/

}
