package com.badlogic.mygame.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.mygame.controller.NewWorldLevelController;
import com.badlogic.mygame.controller.PlayerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTiledMap extends ApplicationAdapter {
    private String pathToFile = "Land.png";

    public static TiledMap map;
    static TiledMapTileLayer tileLayer;
    //static MapLayers layers;
    TiledMapTileSet tileSet;
    //TiledMapTile tile;
    int tileId;
    //********* GRASS
    private int GRASSID = 48;
    private int GRASSREGIONX = 60;
    private int GRASSREGIONY = 64;
    //*********

    private Texture png;
    TextureRegion tilesTR;
    TextureRegion [] tilesInPng;
    TextureRegion[][] tilesMatrix;
    public int[] ground; // for ex. ground and water - Bottom Layer
    MapLayer layer;

    // array list of TextureRegion
    private List<TextureRegion> myTilesList = new ArrayList<>();

    public OrthogonalTiledMapRenderer setUpMap(){
        map = new TiledMap();
        tileLayer = new TiledMapTileLayer(NewWorldLevelController.MAPWIDTH, NewWorldLevelController.MAPHEIGHT, NewWorldLevelController.TILEWIDTH, NewWorldLevelController.TILEHEIGHT);
        map.getLayers().add(tileLayer);
        png = new Texture(pathToFile);
        myTilesList = createMyTilesList(png);
        return new OrthogonalTiledMapRenderer(map, NewWorldLevelController.UNIT_SCALE);
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

    public TextureRegion getTextureRegionOfTile(int tileId, int regionX, int regionY){
        int id=tileId; // 528 tiles in total // ABOVE 46
        TextureRegion tR = myTilesList.get(id);
        //TiledMapTile tile = new StaticTiledMapTile(tR);
        //tR.getRegionX();
        //tR.getRegionY();
        System.out.println("tR.getRegionX() : " + tR.getRegionX());
        System.out.println("tR.getRegionY() : " + tR.getRegionY());
        tR.setRegionX(regionX); // 60
        tR.setRegionY(regionY); // 64 the same
        /*FOR GRASS: id=49 / 48, tR.setRegionX(60)*/

        //createCell(tileId, tR);

        return tR;
        /*tileSet = new TiledMapTileSet();
        tileSet.putTile(id, tile); //undo
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tileSet.getTile(id));
        return cell;*/
    }

    public void createCell(int tileId, TextureRegion tR){
        //******************************************************************************
        //TextureRegion grassTr = getTextureRegionOfTile(grassId, grassRegionX, grassRegionY);
        //******************************************************************************

        TiledMapTile tile = new StaticTiledMapTile(tR);
        tileSet = new TiledMapTileSet();
        tileSet.putTile(tileId, tile);

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tileSet.getTile(tileId));

        for (int col = 0; col < NewWorldLevelController.MAPWIDTH; col++) {
            tileLayer.setCell(col, 0, cell);
        }
        /*// Reading Map Layers
        MapLayers layers = map.getLayers();
        System.out.println("layers: " + map.getLayers());
        System.out.println("layers: " + map.getLayers().get(0)); //tileLayer
        System.out.println("layers: " + map.getLayers().get(0));*/
    }

    public void positionYControl(TextureRegion tR, Batch batch){
        batch.begin();
        if(PlayerController.player.getPosition().y>tR.getRegionY())
        {
            System.out.println("Player Y is higher than Tile Y");
            // here comes the player
            PlayerController.draw(batch); // here comes the player
            batch.draw(tR, tR.getRegionX(), tR.getRegionY(), tR.getRegionWidth(), tR.getRegionHeight());
        }
        else{
            batch.draw(tR, tR.getRegionX(), tR.getRegionY(), tR.getRegionWidth(), tR.getRegionHeight());
            PlayerController.draw(batch); // here comes the player
        }
        batch.end();
    }


}