package com.badlogic.mygame.abstractClasses;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.mygame.interfaces.IDrawable;

import java.util.ArrayList;
import java.util.List;

public abstract class DrawableObjects implements Comparable<List<IDrawable>> {
    List<IDrawable> drawableObjsList = new ArrayList<>();
    public Vector2 position;
    float depth = position.y;

    float getDepth() {
        return depth;
    }

    /*@Override
    public int compareTo(@NotNull List<IDrawable> o) {
        return 0;
    }*/
}
