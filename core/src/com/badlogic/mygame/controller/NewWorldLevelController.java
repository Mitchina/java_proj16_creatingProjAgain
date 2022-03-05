package com.badlogic.mygame.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.mygame.helper.MyTiledMap;

public class NewWorldLevelController {
    // display in 2d plane
    private static OrthogonalTiledMapRenderer renderer;
    // load map
    private static MyTiledMap myTiledMap;

    // draw sprites
    public static Batch batch;
    public static World world;
    private static Box2DDebugRenderer box2DDebugRenderer;

    static TextureRegion grassTr;
    static Texture grassTexture;
    static Pixmap grassPixmap;

    public static final float UNIT_SCALE = 1f;

    //14,14,32,32
    public static final int MAPWIDTH = 14;
    public static final int MAPHEIGHT = 14;

    public static final int TILEWIDTH = 32;
    public static final int TILEHEIGHT = 32;

    /**
     * Below, methods called just once
     */

    public static void initializeController(){
        myTiledMap = new MyTiledMap();
        renderer = myTiledMap.setUpMap();

        //grassTr = myTiledMap.getTextureRegionOfTile(myTiledMap.GRASS_ID);

        //25 central plant ground //tR.getRegionX() : 32 //tR.getRegionY() : 64
        grassPixmap = myTiledMap.extractPixmapFromTextureRegion(myTiledMap.CAGE_ID); //63 cage //29 with erbs
        grassTexture = myTiledMap.getTextureFromPixmap(grassPixmap);

        //******** Create layer
        //myTiledMap.addTextureToLayer(grassTr, renderer.getMap());
        //*****************************

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
        renderer.render();
        batch.begin();

        if(PlayerController.player.getPosition().y>grassTexture.getDepth())
        {
            //System.out.println("Player Y is higher than Tile Y");
            PlayerController.draw(batch);
            batch.draw(grassTexture, 0,0,32,32);
        }
        else{
            batch.draw(grassTexture, 0,0,32,32);
            PlayerController.draw(batch);
        }
        batch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

}
