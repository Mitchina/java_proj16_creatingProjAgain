package com.badlogic.mygame.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.mygame.controller.NewWorldLevelController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyTiledMap extends ApplicationAdapter {
    private String pathToFile = "Land.png";

    public static TiledMap map;
    static TiledMapTileLayer tileLayer;
    private Texture png;
    TextureRegion tilesTR;
    TextureRegion [] tilesInPng;
    TextureRegion[][] tilesMatrix;
    TiledMapTileSet tileSet;

    // array list of TextureRegion
    private List<TextureRegion> myTilesList = new ArrayList<>();
    // ******** Textures ids:
    public static final int GRASS_ID = 25;
    public static final int CAGE_ID = 63;
    public static final int GRASS_ERBS_ID = 29;

    public OrthogonalTiledMapRenderer setUpMap(){
        map = new TiledMap();
        tileLayer = new TiledMapTileLayer(NewWorldLevelController.MAPWIDTH, NewWorldLevelController.MAPHEIGHT, NewWorldLevelController.TILEWIDTH, NewWorldLevelController.TILEHEIGHT);

        map.getLayers().add(tileLayer);
        map.getLayers().get(0).setName("ground");

        png = new Texture(pathToFile);
        myTilesList = createMyTilesList(png);

        //******** Create layer
        addTextureToLayer(map);
        //*****************************

        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, NewWorldLevelController.UNIT_SCALE);
        return renderer;
    }

    public List<TextureRegion> createMyTilesList(Texture png){
        //tileInPng = new TextureRegion(png, 32, 32);
        //#******** tiles count in png file ********************************************
        tilesMatrix = TextureRegion.split(png, NewWorldLevelController.TILEWIDTH, NewWorldLevelController.TILEHEIGHT);
        System.out.println("tilesInPng.length : "+ tilesMatrix.length); //23
        int matrixLength = tilesMatrix.length; //23

        for(int i =0; i<matrixLength;i++){
            tilesInPng = new TextureRegion[matrixLength];
            for (TextureRegion[] tilesInPng:tilesMatrix) {
                /*System.out.println("tilesInPng[0].getRegionX();: " + tilesInPng[i].getRegionX());
                System.out.println("tilesInPng[0].getRegionY();: " + tilesInPng[i].getRegionY());
                System.out.println("tilesInPng[0].getTexture();: " + tilesInPng[i].getTexture()); //Land.png
                System.out.println("tilesInPng[0].getRegionWidth();: " + tilesInPng[i].getRegionWidth()); //32
                System.out.println("tilesInPng[0].getRegionHeight();: " + tilesInPng[i].getRegionHeight()); //32*/
                tilesTR = new TextureRegion(tilesInPng[i]);
                myTilesList.add(tilesTR);
            }
        }
        for(int i=0;i<myTilesList.size();i++)
        {
            System.out.println("myTilesList.get(i): " + myTilesList.get(i));
            //System.out.println("i: " + i); // 528 total
        }
        return myTilesList;
    }

    public Texture getTextureFromPixmap(Pixmap pixmap){
        Texture texture = new Texture(pixmap, false);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //pixmap.dispose();
        return texture;
    }

    public TextureRegion getTextureRegionOfTile(int tileId){
        int id=tileId; // 528 tiles in total
        TextureRegion tR = myTilesList.get(id);
        System.out.println("tR.getRegionX() : " + tR.getRegionX());
        System.out.println("tR.getRegionY() : " + tR.getRegionY());
        return tR;
    }

    public Pixmap extractPixmapFromTextureRegion(int tileId) {
        int id=tileId; // 528 tiles in total
        TextureRegion tR = myTilesList.get(id);
        System.out.println("1.tR.getRegionX() : " + tR.getRegionX());
        System.out.println("1.tR.getRegionY() : " + tR.getRegionY());

        TextureData textureData = tR.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap pixmap = new Pixmap(
                tR.getRegionWidth(),
                tR.getRegionHeight(),
                textureData.getFormat()
        );
        pixmap.drawPixmap(
                textureData.consumePixmap(), // The other Pixmap
                0, // The target x-coordinate (top left corner)
                0, // The target y-coordinate (top left corner)
                tR.getRegionX(), // The source x-coordinate (top left corner)
                tR.getRegionY(), // The source y-coordinate (top left corner)
                tR.getRegionWidth(), // The width of the area from the other Pixmap in pixels
                tR.getRegionHeight() // The height of the area from the other Pixmap in pixels
        );
        return pixmap;
    }

    //public void addTextureToLayer(TextureRegion tR, TiledMap map){
    public void addTextureToLayer(TiledMap map){
        //******************************************************************************
        TextureRegion grassTr = getTextureRegionOfTile(GRASS_ID);
        TextureRegion grassErbsTr = getTextureRegionOfTile(GRASS_ERBS_ID);
        //******************************************************************************

        TiledMapTile tile0 = new StaticTiledMapTile(grassTr);
        TiledMapTile tile1 = new StaticTiledMapTile(grassErbsTr);

        tileSet = new TiledMapTileSet();
        tileSet.putTile(0, tile0); //my used tiles
        tileSet.putTile(1, tile1); //my used tiles
        //System.out.println("tileSet.size() : " + tileSet.size()); //2
        tileSet.setName("myUsedTiles");

        if(map.getLayers().get(0).getName().equalsIgnoreCase("ground")){
            for(int row=0; row<NewWorldLevelController.MAPHEIGHT; row++){
                for (int col = 0; col < NewWorldLevelController.MAPWIDTH; col++) {
                    TiledMapTileLayer.Cell cell = getRandomCell(tileSet);
                    tileLayer.setCell(col, row, cell);
                }
            }
        }
    }

    public TiledMapTileLayer.Cell getRandomCell(TiledMapTileSet tileSet){
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        // GENERATE TILE PROBABILITY:
        Random random = new Random();
        int upperbound = 25; //generate random values from 0-24
        int int_random = random.nextInt(upperbound);
        //System.out.println("upperbound : " + int_random);
        if(int_random<=1){
            cell.setTile(tileSet.getTile(1)); // id 1 = grassErbsTr
        }
        else
            cell.setTile(tileSet.getTile(0)); // id 0 = grassTr

        return cell;
    }

    public int[] menuObj(String obj){
        /*String obj = "";
        String cage = "cage";
        String rock = "grass";*/
        int [] objData = new int[3];
        int id = 0;
        int objDrawXPosition = 0;
        int objDrawYPosition = 0;

        switch (obj){
            case "cage":
            {
                id = CAGE_ID;
                Random random = new Random();
                int int_random = random.nextInt(500);
                objDrawXPosition = int_random;
                objDrawYPosition = int_random;
            }
            break;
            case "grass":
            {
                id = GRASS_ID;
                Random random = new Random();
                int int_random = random.nextInt(500);
                objDrawXPosition = int_random;
                objDrawYPosition = int_random;
            }
            break; //263
            case "rock":
            {
                id = 263;
                Random random = new Random();
                int int_random = random.nextInt(500);
                objDrawXPosition = int_random;
                objDrawYPosition = int_random;
            }
            break;
            case "randomObj":
            {
                Random random = new Random();
                int id_random = random.nextInt(528); // total of tiles: 528
                id = id_random;

                int int_randomX = random.nextInt(500);
                objDrawXPosition = int_randomX;
                int int_randomY = random.nextInt(500);
                objDrawYPosition = int_randomY;
            }
            break;
            default:
                break;
        }
        objData[0] = id;
        objData[1] = objDrawXPosition;
        objData[2] = objDrawYPosition;
        return objData;
    }
}