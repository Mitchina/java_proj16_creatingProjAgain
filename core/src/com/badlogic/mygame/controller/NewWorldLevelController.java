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
import com.badlogic.mygame.interfaces.IDrawable;
import com.badlogic.mygame.model.DecorationObject;
import com.badlogic.mygame.model.Player;

import java.util.ArrayList;
import java.util.List;

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
    //******grass
    static Pixmap grassPixmap;
    static Texture grassTexture;

    //******cage
    static Pixmap cagePixmap;
    static Texture cageTexture;

    //******general
    static Pixmap myObjPixmap;
    static Texture myObjTexture;

    static List<TextureRegion> tilesInPngFileMap;

    static int[] objData1;
    static int[] objData2;

    static List<int[]> objDataList;

    static List<DecorationObject> tileObjsWithTexture;

    static List<IDrawable> drawableObjs;

    static Player playerInWorld;


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

        // getting list of tiles in the Png tiled map file
        tilesInPngFileMap = myTiledMap.getTileslist();

        //25 central plant ground //tR.getRegionX() : 32 //tR.getRegionY() : 64
        /*grassPixmap = myTiledMap.extractPixmapFromTextureRegion(myTiledMap.GRASS_ID); //63 cage //29 with erbs
        grassTexture = myTiledMap.getTextureFromPixmap(grassPixmap);

        cagePixmap = myTiledMap.extractPixmapFromTextureRegion(myTiledMap.CAGE_ID); //63 cage //29 with erbs
        cageTexture = myTiledMap.getTextureFromPixmap(cagePixmap);*/

        objDataList = new ArrayList<>();
        // getting the tile texture caracteristics for the texture
        objData1 = myTiledMap.menuObj("cage");
        objData2 = myTiledMap.menuObj("rock");

        objDataList.add(objData1);
        objDataList.add(objData2);

        tileObjsWithTexture = new ArrayList<>();

        for(int [] objData : objDataList){

            DecorationObject obj = new DecorationObject(objData[0], objData[1], objData[2]);
            obj.createTexture(tilesInPngFileMap);
            tileObjsWithTexture.add(obj);
        }

        // all objs + player in this list
        drawableObjs = new ArrayList<>();

        world = new World(new Vector2(0,0), true); // this game doesn't need gravity

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(true);
        batch = renderer.getBatch();
    }

    // get the instance of the player:
    public static void getPlayer(Player initializedPlayer){
        playerInWorld = initializedPlayer;
    }

    public static void addDrawableListForDepths()
    {
        for(IDrawable obj : tileObjsWithTexture){
            drawableObjs.add(obj);
        }
        drawableObjs.add(playerInWorld);
    }

    /**
     * Below, methods called every frame
     */

    public static void update(float deltaTime, OrthographicCamera camera){
        world.step(1/60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        // updating list of depth obj positions
        // loop through all IDrawable obj and save order of depth position
        addDrawableListForDepths();

        /*for(IDrawable drawable : drawableObjs){
            System.out.println(drawable.getName());
            System.out.println(drawable.getDepth());
        }*/

    }

    public static void draw(OrthographicCamera camera){
        renderer.render();
        batch.begin();

        if(PlayerController.player.getPosition().y>tileObjsWithTexture.get(0).getObjDrawYPosition())
        {
            //System.out.println("Player Y is higher than Tile Y");
            //PlayerController.draw(batch);
            PlayerController.draw(batch);
            batch.draw(tileObjsWithTexture.get(0).getObjTexture(), tileObjsWithTexture.get(0).getObjDrawXPosition(), tileObjsWithTexture.get(0).getObjDrawYPosition(),32,32);
            //System.out.println("grassTexture.getDepth(): " + grassTexture.getDepth());
            //System.out.println("PlayerController.player.getPosition().y: " + PlayerController.player.getPosition().y);
        }
        else{
            //System.out.println("Tile Y is higher than Player Y");
            //System.out.println("PlayerController.player.getPosition().y: " + PlayerController.player.getPosition().y);
            //batch.draw(myObjTexture, objData1[1], objData1[2],32,32);
            batch.draw(tileObjsWithTexture.get(0).getObjTexture(), tileObjsWithTexture.get(0).getObjDrawXPosition(), tileObjsWithTexture.get(0).getObjDrawYPosition(),32,32);
            //System.out.println("grassTexture.getDepth(): " + grassTexture.getDepth());
            PlayerController.draw(batch);
        }

        batch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

}
