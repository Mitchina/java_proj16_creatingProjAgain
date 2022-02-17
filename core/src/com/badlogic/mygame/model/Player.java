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
import com.badlogic.mygame.helper.Spritesheet;
import com.badlogic.mygame.interfaces.IDrawable;

import java.util.HashMap;

public class Player implements IDrawable {

    //######## ANIMATIONS TAGS ########
    public static final float ANIMATION_SPEED_NORMAL = 0.25f;
    public static final float ANIMATION_SPEED_FAST = 0.10f;

    public static final String ANIMATION_TAG_IDLE_FRONT = "idleFront";
    public static final String ANIMATION_TAG_IDLE_LEFT = "idleLeft";
    public static final String ANIMATION_TAG_IDLE_BACK = "idleBack";
    public static final String ANIMATION_TAG_IDLE_RIGHT = "idleRight";
    public static final String ANIMATION_TAG_WALK_FRONT = "walkFront";
    public static final String ANIMATION_TAG_WALK_LEFT = "walkLeft";
    public static final String ANIMATION_TAG_WALK_RIGHT = "walkRight";
    public static final String ANIMATION_TAG_WALK_BACK = "walkBack";
    //#################################

    private Vector2 position;
    public Spritesheet spritesheet;
    //public Animation<TextureRegion> animation;
    protected HashMap<String, Animation> animations;
    public Animation currentAnimation;
    protected float widthEachPlayer;
    protected float heightEachPlayer;

    private float stateTime; // tracks elapsed time for the animation
    protected Vector2 movDir;
    protected float spriteVelocity;
    private Body physicBody;
    //public static Batch batch;

    /**
     * Below, methods called just once
     */

    // passing al the parameters of the player
    public Player(Vector2 position, int widthEachPlayer, int heightEachPlayer, String spritesRelativePath, float spriteVelocity){
        System.out.println("Player()");
        this.position = position;
        this.widthEachPlayer = widthEachPlayer * LevelController.UNIT_SCALE;
        this.heightEachPlayer = heightEachPlayer * LevelController.UNIT_SCALE;
        this.spritesheet = new Spritesheet(spritesRelativePath, widthEachPlayer, heightEachPlayer);
        this.animations = new HashMap<String, Animation>();
        this.stateTime =0f;

        this.spriteVelocity = spriteVelocity;

        setSpriteAnimations(ANIMATION_TAG_IDLE_FRONT,4, 6, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_IDLE_LEFT,7, 9, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_IDLE_BACK,10, 11, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_IDLE_RIGHT,12, 14, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_WALK_FRONT,15, 19, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_WALK_LEFT,20, 24, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_WALK_RIGHT,25, 29, ANIMATION_SPEED_NORMAL);
        setSpriteAnimations(ANIMATION_TAG_WALK_BACK,30, 34, ANIMATION_SPEED_NORMAL);
        setCurrentAnimation(ANIMATION_TAG_IDLE_FRONT,ANIMATION_SPEED_NORMAL);

        this.physicBody = createBox2d(this.position, this.widthEachPlayer, this.heightEachPlayer);
    }

    public void setSpriteAnimations(String animationName, int startFrame, int lastFrame, float animationSpeed) {
        System.out.println("Player.setSpriteAnimations()");
        this.animations.put(animationName, spritesheet.createAnimation(startFrame, lastFrame, animationSpeed));
    }

    // specific for this player
    public Body createBox2d(Vector2 position, float boxWidth, float boxHeight){
        System.out.println("Player.createBox2d()");

        BodyDef bodyDefinition = new BodyDef();

        bodyDefinition.position.set(position);

        Body boxBody = LevelController.world.createBody(bodyDefinition);
        boxBody.setUserData(this);
        boxBody.setType(BodyDef.BodyType.DynamicBody);

        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(boxWidth/2-1.1f, boxHeight/2-1.3f, new Vector2(boxWidth/2, boxHeight/2+.05f), 0f);

        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = rectangleShape;

        boxBody.createFixture(fixtureDefinition);

        return boxBody;
    }

    /**
     * Below, methods called every frame
     */

    public void update(float deltaTime) {
        System.out.println("Player.update()");
        this.stateTime += deltaTime;
        // change sprite position
        this.position = physicBody.getPosition();

        final double SPEEDTRANSLOC = 0.051; // numbers +/- as 0.03535534 and 0.05 = walking // 0.05656854 and 0.08 = running
    }

    public void updateBody(Vector2 movDir) {
        System.out.println("Player.updateBody()");
        elaborateNewAnimation(movDir);
        physicBody.setLinearVelocity(movDir.scl(1000));
    }

    public void elaborateNewAnimation(Vector2 movDir) {
        System.out.println("Player.elaborateNewAnimation()");

        float movDirAngle = new Vector2(1,0).angleDeg(movDir);

        boolean isRunning = movDir.len() > 0.05f;
        //System.out.println(isRunning);

        if(movDirAngle== 0 && movDir.x == 0 && movDir.y== 0){
            setCurrentAnimation(ANIMATION_TAG_IDLE_FRONT, ANIMATION_SPEED_NORMAL);
        }

        if((movDirAngle == 0 || movDirAngle == 45 || movDirAngle == 315) && movDir.x != 0){
            if(!isRunning)
                setCurrentAnimation(ANIMATION_TAG_WALK_RIGHT, ANIMATION_SPEED_NORMAL);
            else
                setCurrentAnimation(ANIMATION_TAG_WALK_RIGHT, ANIMATION_SPEED_FAST);

        }
        if((movDirAngle== 180 || movDirAngle == 135 || movDirAngle == 225) && movDir.x != 0){
            if(!isRunning)
                setCurrentAnimation(ANIMATION_TAG_WALK_LEFT, ANIMATION_SPEED_NORMAL);
            else
                setCurrentAnimation(ANIMATION_TAG_WALK_LEFT, ANIMATION_SPEED_FAST);
        }
        if(movDirAngle== 270 && movDir.y != 0){
            if(!isRunning)
                setCurrentAnimation(ANIMATION_TAG_WALK_BACK, ANIMATION_SPEED_NORMAL);
            else
                setCurrentAnimation(ANIMATION_TAG_WALK_BACK, ANIMATION_SPEED_FAST);
        }
        if(movDirAngle== 90 && movDir.y != 0){
            if(!isRunning)
                setCurrentAnimation(ANIMATION_TAG_WALK_FRONT, ANIMATION_SPEED_NORMAL);
            else
                setCurrentAnimation(ANIMATION_TAG_WALK_FRONT, ANIMATION_SPEED_FAST);
        }
    }

    public void setCurrentAnimation(String currentAnimationKey, float frameDuration) {
        System.out.println("Player.setCurrentAnimation()");
        this.currentAnimation = this.animations.get(currentAnimationKey);
        this.currentAnimation.setFrameDuration(frameDuration);
    }

    @Override
    public void draw(Batch batch) {
        System.out.println("Player.draw()");
        TextureRegion currentFrame = (TextureRegion) this.currentAnimation.getKeyFrame(this.stateTime, true);
        batch.draw(currentFrame, this.position.x, this.position.y, this.widthEachPlayer, this.heightEachPlayer);
    }

    public Vector2 getPosition() {
        System.out.println("Player.getPosition()");
        return this.physicBody.getPosition();
    }
}
