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
import com.badlogic.mygame.abstractClasses.DrawableObjects;
import com.badlogic.mygame.helper.MyTiledMap;
import com.badlogic.mygame.interfaces.IDrawable;
import com.badlogic.mygame.model.DecorationObject;
import com.badlogic.mygame.model.ObjectsInWorld;
import com.badlogic.mygame.model.Player;

import java.util.*;

public class NewWorldLevelController<drawableObjsList> {
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

    static List<IDrawable> drawableObjsList;

    static Player playerInWorld;

    static ObjectsInWorld objectsInWorld;

    static boolean isPrinted = false;

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
        objData2 = myTiledMap.menuObj("cage");

        objDataList.add(objData1);
        objDataList.add(objData2);

        tileObjsWithTexture = new ArrayList<>();

        for(int [] objData : objDataList){

            DecorationObject obj = new DecorationObject(objData[0], objData[1], objData[2]);
            obj.createTexture(tilesInPngFileMap);
            tileObjsWithTexture.add(obj);
        }

        // all objs + player in this list
        drawableObjsList = new ArrayList<>();

        world = new World(new Vector2(0,0), true); // this game doesn't need gravity

        objectsInWorld = new ObjectsInWorld();

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
        for(DecorationObject obj : tileObjsWithTexture){
            drawableObjsList.add(obj);
        }
        drawableObjsList.add(playerInWorld);
    }

    /**
     * Below, methods called every frame
     */

    public static void update(float deltaTime, OrthographicCamera camera){
        drawableObjsList.clear();
        world.step(1/60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        // updating list of depth obj positions
        // loop through all IDrawable obj and save order of depth position
        addDrawableListForDepths();
        //objectsInWorld.addList(drawableObjsList);

        Collections.sort(objectsInWorld.getList(), new Comparator<IDrawable>() {
            @Override
            public int compare(IDrawable obj1, IDrawable obj2) {
                int returningValue;
                if(obj1.getDepth()<obj2.getDepth())
                    returningValue = 1;
                else if(obj1.getDepth()>obj2.getDepth())
                    returningValue = -1;
                else
                    returningValue = 0;
                //return (int) (obj1.getDepth() - obj2.getDepth());
                return returningValue;
            }
        });

        objectsInWorld.addList(drawableObjsList); // passing the sorted list

        /*if(!isPrinted){
            System.out.println("****************BEFORE********************");
            System.out.println(Arrays.asList(objectsInWorld.getList()));

            Collections.sort(objectsInWorld.getList(), new Comparator<IDrawable>() {
                @Override
                public int compare(IDrawable obj1, IDrawable obj2) {
                    int returningValue;
                    if(obj1.getDepth()>obj2.getDepth())
                        returningValue = 1;
                    else if(obj1.getDepth()<obj2.getDepth())
                        returningValue = -1;
                    else
                        returningValue = 0;
                    //return (int) (obj1.getDepth() - obj2.getDepth());
                    return returningValue;
                }
            });

            System.out.println("****************AFTER********************");
            System.out.println(Arrays.asList(objectsInWorld.getList()));
            isPrinted = true;
        }*/
        //objectsInWorld.addList(drawableObjsList); // passing the sorted list*/
    }

    public static void draw(OrthographicCamera camera){
        renderer.render();
        batch.begin();

        objectsInWorld.drawObjects(batch);

        /*if(PlayerController.player.getPosition().y>tileObjsWithTexture.get(0).getObjDrawYPosition())
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

        if(PlayerController.player.getPosition().y>tileObjsWithTexture.get(1).getObjDrawYPosition())
        {
            //System.out.println("Player Y is higher than Tile Y");
            //PlayerController.draw(batch);
            PlayerController.draw(batch);
            batch.draw(tileObjsWithTexture.get(1).getObjTexture(), tileObjsWithTexture.get(1).getObjDrawXPosition(), tileObjsWithTexture.get(1).getObjDrawYPosition(),32,32);
            //System.out.println("grassTexture.getDepth(): " + grassTexture.getDepth());
            //System.out.println("PlayerController.player.getPosition().y: " + PlayerController.player.getPosition().y);
        }
        else{
            //System.out.println("Tile Y is higher than Player Y");
            //System.out.println("PlayerController.player.getPosition().y: " + PlayerController.player.getPosition().y);
            //batch.draw(myObjTexture, objData1[1], objData1[2],32,32);
            batch.draw(tileObjsWithTexture.get(1).getObjTexture(), tileObjsWithTexture.get(1).getObjDrawXPosition(), tileObjsWithTexture.get(1).getObjDrawYPosition(),32,32);
            //System.out.println("grassTexture.getDepth(): " + grassTexture.getDepth());
            PlayerController.draw(batch);
        }*/

        batch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

}
