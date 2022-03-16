package com.badlogic.mygame.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface IDrawable {
    String getName();
    public void draw(Batch batch);
    float getDepth();
}
