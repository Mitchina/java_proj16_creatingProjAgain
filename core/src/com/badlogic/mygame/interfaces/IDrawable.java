package com.badlogic.mygame.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public interface IDrawable {
    String getName();
    public void draw(Batch batch);
    float getDepth();

}
