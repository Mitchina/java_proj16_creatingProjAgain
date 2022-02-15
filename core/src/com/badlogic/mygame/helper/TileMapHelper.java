package com.badlogic.mygame.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.mygame.GameScreen;
import com.badlogic.mygame.controller.LevelController;

// our model of Level
public class TileMapHelper extends ApplicationAdapter {
    String firstSceneRelativePath = "myTileMap.tmx";
    String secondSceneRelativePath = "myTileMap_part2.tmx";
    private GameScreen gameScreen;
    public TiledMap tiledMap;

    public int[] groundLayerIndices; // for ex. ground and water - Bottom Layer
    public int[] belowCharLayerIndices; // for ex. flowers - Top layer
    public int[] decorationLayersIndices; // for ex. houses, bushes, trees - EvenMoreTopLayer

    // for the collisions, I'll have it save as <objectgroup color="..." name="Collision">
    // <object name="decoration" type="solid" x, y, width and height for each of them>

    public TileMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    // have access to all the layers in tiles, include the collision obj
    // those 2 methods are the second way to do the ones below
    public MapLayer getMapLayer(String layerName){
        return tiledMap.getLayers().get(layerName);
    }
    // LevelController will get the objs found in the tiledMap layer
    public MapObjects getLayerObjects(MapLayer tiledMapLayers){
        return tiledMapLayers.getObjects(); // we can access the collision layer - it will return an MapObjects array with the shapes
    }

    public OrthogonalTiledMapRenderer setupMap(){
        tiledMap = new TmxMapLoader().load(firstSceneRelativePath);

        // Reading Map Layers
        MapLayers mapLayers = tiledMap.getLayers();

        groundLayerIndices = new int[]{
                mapLayers.getIndex("BottomLayer")
        };
        belowCharLayerIndices = new int[]{
                mapLayers.getIndex("TopLayer")
        };
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("EvenMoreTopLayer")
        };

        return new OrthogonalTiledMapRenderer(tiledMap, LevelController.UNIT_SCALE);

    }
    public OrthogonalTiledMapRenderer setupMap2(){
        tiledMap = new TmxMapLoader().load(secondSceneRelativePath);

        // Reading Map Layers
        MapLayers mapLayers = tiledMap.getLayers();

        groundLayerIndices = new int[]{
                mapLayers.getIndex("BottomLayer")
        };
        belowCharLayerIndices = new int[]{
                mapLayers.getIndex("TopLayer")
        };
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("EvenMoreTopLayer")
        };

        return new OrthogonalTiledMapRenderer(tiledMap, LevelController.UNIT_SCALE);

    }


}
