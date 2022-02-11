package com.badlogic.mygame.controller;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.mygame.model.Spritesheet;

import java.util.HashMap;

public class SpriteController {
    // position and sprite sheet
    public Vector2 position;
    public Spritesheet spritesheet;
    public Animation<TextureRegion> animation;
    protected HashMap<String, Animation> animations;
    public String currentAnimation;
    //String player2SpritesRelativePath = "player2AnimationSheet.png";
    protected float widthEachPlayer;
    protected float heightEachPlayer;
    private float stateTime; // tracks elapsed time for the animation

    // passing al the parameters of the player
    public SpriteController(Vector2 position, int widthEachPlayer, int heightEachPlayer, String player2SpritesRelativePath){
        this.position = position;
        this.widthEachPlayer = widthEachPlayer * LevelController.UNIT_SCALE;
        this.heightEachPlayer = heightEachPlayer * LevelController.UNIT_SCALE;
        this.spritesheet = new Spritesheet(player2SpritesRelativePath, widthEachPlayer, heightEachPlayer);
        this.animations = new HashMap<String, Animation>();
        stateTime =0f;
    }

    public void draw(Batch batch){
        TextureRegion currentFrame = (TextureRegion) animations.get(currentAnimation).getKeyFrame(stateTime, true);
        batch.draw(currentFrame, this.position.x, this.position.y, this.widthEachPlayer, this.heightEachPlayer);
    }

    public void update(float deltaTime){
        stateTime += deltaTime;
        // change player position
    }

}
