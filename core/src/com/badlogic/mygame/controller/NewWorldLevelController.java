package com.badlogic.mygame.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.mygame.helper.MyTiledMap;

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
    static Texture grassTexture;
    static Pixmap grassPixmap;
    static TiledMapTileLayer.Cell grassCells;
    //TiledMapTileSet tileSet;

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
        myTiledMap = new MyTiledMap();
        renderer = myTiledMap.setUpMap();

        grassTr = myTiledMap.getTextureRegionOfTile(25);
        //grassTexture = myTiledMap.getTextureOfTile(25); //63 cage //29 with erbs
        grassPixmap = myTiledMap.extractPixmapFromTextureRegion(25); //63 cage //29 with erbs
        grassTexture = myTiledMap.getTextureFromPixmap(grassPixmap);


        //25 central plant ground //tR.getRegionX() : 32 //tR.getRegionY() : 64
        //grassCells = myTiledMap.getCellOfTile(48, grassTr);

        myTiledMap.addTextureToLayer(grassTr, renderer.getMap());
        //myTiledMap.createMap(grassCells, renderer.getMap());
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
        /*for(int i=0;i<100;i++){
            batch.draw(grassTexture, i,0,32,32);
        }*/
        //System.out.println("grassTexture.getDepth() : " + grassTexture.getDepth());
        //if(PlayerController.player.getPosition().y>grassTexture.getDepth())
        //grassTr = myTiledMap.getTextureRegionOfTile(63);
        //if(PlayerController.player.getPosition().y>grassTr.getRegionY())
        if(PlayerController.player.getPosition().y>grassTexture.getDepth())
        {
            System.out.println("Player Y is higher than Tile Y");
            //System.out.println("player.getPosition().x" + PlayerController.player.getPosition().x);
            //System.out.println("player.getPosition().y" + PlayerController.player.getPosition().y);
            //System.out.println("grassTr.getRegionX(): " + grassTr.getRegionX());
            //System.out.println("grassTr.getRegionY(): " + grassTr.getRegionY());
            // here comes the player
            PlayerController.draw(batch); // here comes the player
            //batch.draw(grassTr, grassTr.getRegionX(), grassTr.getRegionY(), grassTr.getRegionWidth(), grassTr.getRegionHeight());
            //batch.draw(grassTr, 0, 0, 32, 32);
            //grassTexture.draw(grassPixmap, 0,0);
            batch.draw(grassTexture, 0,0,32,32);
        }
        else{
            batch.draw(grassTexture, 0,0,32,32);
            //grassTexture.draw(grassPixmap, 0,0);
            //batch.draw(grassTr, grassTr.getRegionX(), grassTr.getRegionY(), grassTr.getRegionWidth(), grassTr.getRegionHeight());
            //batch.draw(grassTr, 0, 0, 32, 32);
            PlayerController.draw(batch); // here comes the player
        }
        batch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

}
