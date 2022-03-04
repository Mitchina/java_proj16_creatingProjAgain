package com.badlogic.mygame.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.mygame.controller.LevelController;

// our model of Level
public class TileMapHelper extends ApplicationAdapter {
    String firstSceneRelativePath = "myTileMap.tmx";
    String secondSceneRelativePath = "myTileMap_part2.tmx";
    public static TiledMap tiledMap;
    static TiledMapTileLayer layer;
    static MapLayers mapLayers;
    static TiledMapTile tile;

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
        //MapLayers mapLayers = tiledMap.getLayers();
        mapLayers = tiledMap.getLayers();

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


    public static void tryingThings() {
        int columns=0;
        int rows=0;
        int tileW=0;
        int tileH=0;
        //layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        for (int index = 0; index < mapLayers.getCount(); index++) // 5 layers // one is collision
        {
            //layer = (TiledMapTileLayer) tiledMap.getLayers().get(i);
            //System.out.println("tiledMap.toString: "+ tiledMap.getLayers().toString());
            if(! mapLayers.get(index).getName().equalsIgnoreCase("Collision")){
                System.out.println(mapLayers.get(index).getName());
                System.out.println(index);
                layer = (TiledMapTileLayer) mapLayers.get(index); // I have 4 layers, get all cells of each one

                columns = layer.getWidth(); //14
                rows = layer.getHeight(); //14
                tileW = layer.getTileWidth();
                tileH = layer.getTileHeight();

                //System.out.println("tileW: " + tileW); //32
                //System.out.println("tileH: " + tileH); //32


                int r = 0;
                int c = 0;
                while(r<rows){
                    System.out.println("row id: "+r);
                    while(c<columns){
                        System.out.println("column id: "+c);
                        if(layer.getCell(r, c) != null){
                            TiledMapTileLayer.Cell cell = layer.getCell(r, c);
                            System.out.println("cell" + cell.toString()); //cellcom.badlogic.gdx.maps.tiled.TiledMapTileLayer$Cell@4ba0fe49
                            /*
                            cell.getTile()
                            cell.getTile().getId();
                            cell.getTile().getTextureRegion();
                            */
                            // or
                            /*
                            tile = cell.getTile();
                            tileId = tile.getId();
                            tileTextureRegion = tile.getTextureRegion();
                            tileTextureRegionTexture = tileTextureRegion.getTexture();
                            */
                            //System.out.println("LandSetId draw: " + cell.getTile().getId()); //LandSetId draw: 98
                            System.out.println(
                                    "tile: " + cell.getTile() + // tile: com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile@167d8073
                                    " tileId: " + cell.getTile().getId() + // tileId: 98
                                    " tileTextureRegion: " + cell.getTile().getTextureRegion() + // tileTextureRegion: com.badlogic.gdx.graphics.g2d.TextureRegion@23aeae20
                                    " tileTextureRegion.getTexture" + cell.getTile().getTextureRegion().getTexture() // Land.png
                            );

                        }
                        else
                            System.out.println("******Empty cell********");
                        c++;
                    }
                    r++;
                    c=0;
                }
            }
        }
    }

}
