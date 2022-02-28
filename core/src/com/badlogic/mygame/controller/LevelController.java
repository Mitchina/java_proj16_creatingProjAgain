package com.badlogic.mygame.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.mygame.abstractClasses.DrawableObjects;
import com.badlogic.mygame.helper.TileMapHelper;

import com.badlogic.gdx.utils.Array;
import com.badlogic.mygame.interfaces.IDrawable;
import com.badlogic.mygame.model.Bodies;

public class LevelController {
    // display in 2d plane
    private static OrthogonalTiledMapRenderer renderer;
    // load map
    private static TileMapHelper tileMapHelper;
    // draw sprites
    public static Batch spriteBatch;
    public static World world;
    private static Array<Body> bodiesInWorld; //Every time that a body is added, add it to a list as well
    private static Box2DDebugRenderer box2DDebugRenderer;

    //public static final float UNIT_SCALE = 1/32f;
    public static final float UNIT_SCALE = 1f;
    public static boolean clearLevel = false;

    /**
     * Below, methods called just once
     */

    public static void initializeController(){
        // call the levels
        tileMapHelper = new TileMapHelper();

        // rendering the first tileset - first scene:
        renderer = tileMapHelper.setupMap("setupMap1"); //setupMap2 to render the second map
        world = new World(new Vector2(0,0), true); // this game doesn't need gravity

        bodiesInWorld = new Array<>();

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(true);

        spriteBatch = renderer.getBatch();

        createLevelBodies();
    }

    // get the objects found in tiled
    private static void createLevelBodies(){
        MapObjects mapObjects = TileMapHelper.getLayerObjects(TileMapHelper.getMapObjGroupFromName("Collision"));

        for(MapObject mapObj : mapObjects){
            Bodies.createBody(mapObj);
        }
    }

    /**
     * Below, methods called every frame
     */

    public static void update(float deltaTime, OrthographicCamera camera){
        world.step(1/60f, 6, 2);
        spriteBatch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        updateBodiesInWorld();
    }

    private static void updateBodiesInWorld(){
        bodiesInWorld.clear();
        world.getBodies(bodiesInWorld);
        for(Body b: bodiesInWorld){
            DrawableObjects spritePhysicsBody = (DrawableObjects) b.getUserData();

            if(spritePhysicsBody != null){
                spritePhysicsBody.position = b.getPosition();
            }
        }
    }

    public static void draw(OrthographicCamera camera){
        renderer.render(tileMapHelper.groundLayerIndices);
        renderer.render(tileMapHelper.belowCharLayerIndices);
        renderer.render(tileMapHelper.decorationLayersIndices); // if player position above item, change it to above player
        spriteBatch.begin();
        // here comes the player
        PlayerController.draw(spriteBatch);
        spriteBatch.end();
        //renderer.render(tileMapHelper.decorationLayersIndices);
        renderer.render(tileMapHelper.aboveCharLayersIndices); // put here clouds

        box2DDebugRenderer.render(world, camera.combined);
    }

    public World getWorld(){
        return this.world;
    }


}
