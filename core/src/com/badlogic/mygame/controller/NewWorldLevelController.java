package com.badlogic.mygame.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.mygame.helper.MyTiledMap;
import com.badlogic.mygame.helper.TileMapHelper;

public class NewWorldLevelController {
    // display in 2d plane
    private static OrthogonalTiledMapRenderer renderer;
    // load map
    private static MyTiledMap myTiledMap;
    // draw sprites
    public static Batch spriteBatch;
    public static World world;
    private static Array<Body> bodiesInWorld; //Every time that a body is added, add it to a list as well
    private static Box2DDebugRenderer box2DDebugRenderer;
    //public static final float UNIT_SCALE = 1/32f;

    public static final float UNIT_SCALE = 1f;
    public static boolean clearLevel = false;

    //14,14,32,32
    public static final int MAPWIDTH = 14;
    public static final int MAPHEIGHT = 14;
    public static final int TILEWIDTH = 32;
    public static final int TILEHEIGHT = 32;

    /**
     * Below, methods called just once
     */

    public static void initializeController(){
        MyTiledMap myTiledMap = new MyTiledMap();
        renderer = myTiledMap.setUpMap();

        world = new World(new Vector2(0,0), true); // this game doesn't need gravity

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(true);

        spriteBatch = renderer.getBatch();
    }

    /**
     * Below, methods called every frame
     */

    public static void update(float deltaTime, OrthographicCamera camera){
        world.step(1/60f, 6, 2);
        spriteBatch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
    }

    public static void draw(OrthographicCamera camera){
        //Cannot read field "ground" because "com.badlogic.mygame.controller.NewWorldLevelController.myTiledMap" is null
        //renderer.render(myTiledMap.getGround());
        //renderer.render();
        renderer.render();
        spriteBatch.begin();
        // here comes the player
        PlayerController.draw(spriteBatch); // here comes the player
        spriteBatch.end();
        //renderer.render(tileMapHelper.aboveCharLayersIndices); // put here clouds

        box2DDebugRenderer.render(world, camera.combined);
    }

}
