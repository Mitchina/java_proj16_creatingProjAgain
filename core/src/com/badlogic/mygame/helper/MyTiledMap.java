package com.badlogic.mygame.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
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

public class MyTiledMap extends ApplicationAdapter {
    String pathToFile = "Land.png";

    public static TiledMap map;
    static TiledMapTileLayer tileLayer;
    static MapLayers layers;
    TiledMapTileSet tileSet;
    //TiledMapTile tile;
    int tileId=0;
    Texture png;
    TextureRegion tileInPng;
    public int[] ground; // for ex. ground and water - Bottom Layer
    MapLayer layer;

    public OrthogonalTiledMapRenderer setUpMap(){
        map = new TiledMap();
        tileLayer = new TiledMapTileLayer(NewWorldLevelController.MAPWIDTH, NewWorldLevelController.MAPHEIGHT, NewWorldLevelController.TILEWIDTH, NewWorldLevelController.TILEHEIGHT);
        map.getLayers().add(tileLayer);

        png = new Texture(pathToFile);
        tileInPng = new TextureRegion(png, 32, 32);

        //TILE DEFINITION ###########
        TiledMapTile tile = new StaticTiledMapTile(tileInPng);
        //###########################

        //TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        //cell.setTile(tile);

        tileSet = new TiledMapTileSet();
        tileSet.putTile(0, tile);

        int id=0;
        for (int row = 0; row < NewWorldLevelController.MAPHEIGHT; row++) {
            for (int col = 0; col < NewWorldLevelController.MAPWIDTH; col++) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                //tileSet.putTile(tileId, tile);
                cell.setTile(tileSet.getTile(id));
                //id++;

                if(tileSet.getTile(id) != null) {
                    System.out.println("1.*********************"); //id=0 is not null
                    tileSet.getTile(id).getProperties(); //com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile@a82cf78
                    tileLayer.setCell(col, row, cell);
                }
                System.out.println(tileSet.getTile(id)); //com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile@122648c9

                System.out.println("tileLayer.toString(): " + tileLayer.toString()); //com.badlogic.gdx.maps.tiled.TiledMapTileLayer@2d436cc
                System.out.println("tile: " + tile); //com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile@122648c9
                System.out.println("tileSet.getName(): " + tileSet.getName()); //tileSet.getName(): null
                System.out.println("tile.getProperties(): " + tile.getProperties()); //com.badlogic.gdx.maps.MapProperties@5f442ac3
                id++;

                //tileLayer.setCell(col, row, cell);

                if(tileSet.getTile(id) != null) {
                    System.out.println("2.*********************");

                }
                //System.out.printf("tileSet.getTile(%d): %d\n", id , tileSet.getTile(id)); //null
                //id++;
            }
        }
        // Reading Map Layers
        MapLayers layers = map.getLayers();
        System.out.println("layers: " + map.getLayers());
        System.out.println("layers: " + map.getLayers().get(0)); //tileLayer
        System.out.println("layers: " + map.getLayers().get(0));
        //layers = map.getLayers();
        //layer = map.getLayers().get(0);
        //ground = new int[]{
                //0
        //};
        //ground = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
        //map.getLayers().get(0); // I added just one layer
        return new OrthogonalTiledMapRenderer(map, NewWorldLevelController.UNIT_SCALE);
    }

    public int[] getGround() {
        return ground;
    }
}