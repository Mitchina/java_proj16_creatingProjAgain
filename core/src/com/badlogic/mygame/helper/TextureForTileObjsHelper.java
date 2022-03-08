package com.badlogic.mygame.helper;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class TextureForTileObjsHelper {

    public static TextureRegion getTextureRegionOfTile(int tileId, List<TextureRegion> myTilesList){
        int id=tileId; // 528 tiles in total
        TextureRegion tR = myTilesList.get(id);
        System.out.println("tR.getRegionX() : " + tR.getRegionX());
        System.out.println("tR.getRegionY() : " + tR.getRegionY());
        return tR;
    }

    public static Texture getTextureFromPixmap(Pixmap pixmap){
        Texture texture = new Texture(pixmap, false);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //pixmap.dispose();
        return texture;
    }

    public static Pixmap extractPixmapFromTextureRegion(int tileId, List<TextureRegion> myTilesList) {
        int id=tileId; // 528 tiles in total
        TextureRegion tR = myTilesList.get(id);
        //System.out.println("1.tR.getRegionX() : " + tR.getRegionX());
        //System.out.println("1.tR.getRegionY() : " + tR.getRegionY());

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
}
