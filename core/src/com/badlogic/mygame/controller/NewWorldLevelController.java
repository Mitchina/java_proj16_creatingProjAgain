package com.badlogic.mygame.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    public static Batch batch;
    public static World world;
    private static Array<Body> bodiesInWorld; //Every time that a body is added, add it to a list as well
    private static Box2DDebugRenderer box2DDebugRenderer;
    //public static final float UNIT_SCALE = 1/32f;

    static TextureRegion grassTr;

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

        grassTr = myTiledMap.getTextureRegionOfTile(48, 60, 64);

        world = new World(new Vector2(0,0), true); // this game doesn't need gravity

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(true);

        batch = renderer.getBatch();
    }

    /**
     * Below, methods called every frame
     */

    public static void update(float deltaTime, OrthographicCamera camera){
        world.step(1/60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
    }

    public static void draw(OrthographicCamera camera){

        batch.begin();
        if(PlayerController.player.getPosition().y>grassTr.getRegionY())
        {
            System.out.println("Player Y is higher than Tile Y");
            // here comes the player
            PlayerController.draw(batch); // here comes the player
            batch.draw(grassTr, grassTr.getRegionX(), grassTr.getRegionY(), grassTr.getRegionWidth(), grassTr.getRegionHeight());
        }
        else{
            batch.draw(grassTr, grassTr.getRegionX(), grassTr.getRegionY(), grassTr.getRegionWidth(), grassTr.getRegionHeight());
            PlayerController.draw(batch); // here comes the player
        }
        batch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

}
