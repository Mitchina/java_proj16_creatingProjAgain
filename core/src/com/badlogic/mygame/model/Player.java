package com.badlogic.mygame.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.mygame.controller.LevelController;
import com.badlogic.mygame.controller.SpriteController;

import java.util.HashMap;

public class Player extends SpriteController {
    private HashMap<String, Animation> animations;
    public String currentAnimation;
    // tracking elapsed time for animation
    private float stateTime;
    public static Batch batch;

    // passing al the parameters of the player
    public Player(Vector2 position, int widthEachPlayer, int heightEachPlayer, String player2SpritesRelativePath){
        super(position, widthEachPlayer, heightEachPlayer, player2SpritesRelativePath);
        setSpriteAnimations("idleFront",4, 6, 0.25f);
        setSpriteAnimations("idleLeft",7, 9, 0.25f);
        setSpriteAnimations("idleBack",10, 11, 0.25f);
        setSpriteAnimations("idleRight",12, 14, 0.25f);
        setSpriteAnimations("walkFront",15, 19, 0.25f);
        setSpriteAnimations("walkLeft",20, 24, 0.25f);
        setSpriteAnimations("walkRight",25, 29, 0.25f);
        setSpriteAnimations("walkBack",30, 34, 0.25f);
        setCurrentAnimation("idleFront");
        /*
        animations = new HashMap<>();
        animations.put("idleFront", spritesheet.createAnimation(4, 6, 0.25f));
        animations.put("idleLeft", spritesheet.createAnimation(7, 9, 0.25f));
        animations.put("idleBack", spritesheet.createAnimation(10, 11, 0.25f));
        animations.put("idleRight", spritesheet.createAnimation(12, 14, 0.25f));
        animations.put("walkFront", spritesheet.createAnimation(15, 19, 0.25f));
        animations.put("walkLeft", spritesheet.createAnimation(20, 24, 0.25f));
        animations.put("walkRight", spritesheet.createAnimation(25, 29, 0.25f));
        animations.put("walkBack", spritesheet.createAnimation(30, 34, 0.25f));
        currentAnimation = "idleFront";
        */
        createBox2d();
    }
    public void setSpriteAnimations(String animationName, int startFrame, int lastFrame, float animationSpeed){
        super.setSpriteAnimations(animationName, startFrame, lastFrame, animationSpeed);
    }

    public void setCurrentAnimation(String currentAnimation){
        super.setCurrentAnimation(currentAnimation);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    public void update(float deltaTime){
        super.update(deltaTime);
    }

    // specific for this player
    public void createBox2d(){
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.position.set(this.position);
        //bodyDefinition.type = BodyDef.BodyType.DynamicBody;

        Body playerBody = LevelController.world.createBody(bodyDefinition);
        playerBody.setUserData(this);

        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(this.widthEachPlayer/2-1.1f, this.heightEachPlayer/2-1.3f, new Vector2(this.widthEachPlayer/2, this.heightEachPlayer/2+.05f), 0f);

        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = rectangleShape;

        playerBody.createFixture(fixtureDefinition);
    }
}
