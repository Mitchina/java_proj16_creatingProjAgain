package com.badlogic.mygame.abstractClasses;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

abstract class DrawableObjects {
    Vector2 position;
    abstract Body createBox2d(Vector2 position, float boxWidth, float boxHeight);
}
