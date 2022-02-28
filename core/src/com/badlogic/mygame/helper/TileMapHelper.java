package com.badlogic.mygame.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.mygame.controller.LevelController;

// our model of Level
public class TileMapHelper extends ApplicationAdapter {
    String firstSceneRelativePath = "myTileMap.tmx";
    String secondSceneRelativePath = "myTileMap_part2.tmx";
    public static TiledMap tiledMap;

    public int[] groundLayerIndices; // for ex. ground and water - Bottom Layer
    public int[] belowCharLayerIndices; // for ex. flowers - Top layer
    public int[] decorationLayersIndices; // for ex. houses, bushes, trees - Collision Objs bellow Player
    public int[] aboveCharLayersIndices; // for ex. houses, bushes, trees - Collision Objs above Player

    // for the collisions, I'll have it save as <objectgroup color="..." name="Collision">
    // <object name="decoration" type="solid" x, y, width and height for each of them>

    /**
     * Below, methods called just once
     */

    public TileMapHelper(){
    }
    // have access to all the layers in tiles, include the collision obj
    // those 2 methods are the second way to do the ones below
    public static MapLayer getMapObjGroupFromName(String layerName){
        //System.out.println(tiledMap.getLayers().get(layerName)); //com.badlogic.gdx.maps.MapLayer@3507eca5
        return tiledMap.getLayers().get(layerName);
    }
    // LevelController will get the objs found in the tiledMap layer
    public static MapObjects getLayerObjects(MapLayer tiledMapLayers){
        //System.out.println("Halloooo" + tiledMapLayers.getObjects()); //com.badlogic.gdx.maps.MapObjects@23856da2
        return tiledMapLayers.getObjects(); // we can access the collision layer - it will return an MapObjects array with the shapes
    }

    public OrthogonalTiledMapRenderer setupMap(String setUpMapName){
        if (setUpMapName == "setupMap1"){
            tiledMap = new TmxMapLoader().load(firstSceneRelativePath);
        }
        else if (setUpMapName == "setupMap2"){
            tiledMap = new TmxMapLoader().load(secondSceneRelativePath);
        }
        else
            System.out.println("Review your SetUp Tiled Map name");
        //tiledMap = new TmxMapLoader().load(firstSceneRelativePath);

        // Reading Map Layers
        MapLayers mapLayers = tiledMap.getLayers();

        groundLayerIndices = new int[]{
                mapLayers.getIndex("BottomLayer")
        };
        belowCharLayerIndices = new int[]{
                mapLayers.getIndex("NoCollisionObjsLayer")
        };
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("CollisionObjsLayer")
        };
        aboveCharLayersIndices = new int[]{
                mapLayers.getIndex("AbovePlayerLayer")
        };
        return new OrthogonalTiledMapRenderer(tiledMap, LevelController.UNIT_SCALE);
    }
}
