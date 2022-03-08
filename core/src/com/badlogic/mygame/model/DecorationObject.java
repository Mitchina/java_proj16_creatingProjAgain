package com.badlogic.mygame.model;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.mygame.controller.NewWorldLevelController;
import com.badlogic.mygame.helper.MyTiledMap;
import com.badlogic.mygame.helper.TextureForTileObjsHelper;
import com.badlogic.mygame.interfaces.IDrawable;

import java.util.List;

public class DecorationObject implements IDrawable {
    private int id = 0;
    private int objDrawXPosition = 0;
    private int objDrawYPosition = 0;
    private String name = null;
    //******general
    Pixmap objPixmap;
    Texture objTexture;
    private int width = NewWorldLevelController.TILEWIDTH;
    private int height = NewWorldLevelController.TILEHEIGHT;

    public DecorationObject(int id, int objDrawXPosition, int objDrawYPosition){
        this.id = id;
        this.objDrawXPosition = objDrawXPosition;
        this.objDrawYPosition = objDrawYPosition;
        setObjName();
    }

    public void setObjName(){
        if (this.id == MyTiledMap.CAGE_ID){
            this.name = "cage";
        }
        if (this.id == MyTiledMap.ROCK_ID){
            this.name = "rock";
        }
    }

    public String getName(){
        return this.name;
    }

    public float getDepth(){
        return (float)objDrawYPosition;
    }

    public Texture createTexture(List<TextureRegion> tilesInPngFileMap){
        objPixmap = TextureForTileObjsHelper.extractPixmapFromTextureRegion(id, tilesInPngFileMap);
        //System.out.println("objData[0] : " + objData[0]);
        //System.out.println("objData.toString() : " + objData.toString());
        objTexture = TextureForTileObjsHelper.getTextureFromPixmap(objPixmap);
        return objTexture;
    }

    public Texture getObjTexture()
    {
        return this.objTexture;
    }

    public int getObjDrawXPosition(){
        return this.objDrawXPosition;
    }

    public int getObjDrawYPosition(){
        return this.objDrawYPosition;
    }

    /*public int compareTo(Player p) {
        // control every frame the position of the objs with the player position:
        // if y player > obj = draw player first.
    }*/

    @Override
    public void draw(Batch batch) {
        batch.draw(objTexture, objDrawXPosition, objDrawYPosition,width,height);
    }
}
