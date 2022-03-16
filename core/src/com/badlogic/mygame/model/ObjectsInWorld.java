package com.badlogic.mygame.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.mygame.interfaces.IDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ObjectsInWorld {//implements Comparable<IDrawable> {
    // sorted list (by depth) of world Objects
    List<IDrawable> drawableObjsList = new ArrayList<>();

    public void addList(List<IDrawable> drawableObjsList){
        this.drawableObjsList = drawableObjsList;
    }

    public List<IDrawable> getList() {
        return drawableObjsList;
    }

    public void drawObjects(Batch batch){
        for(IDrawable obj : drawableObjsList) {
            //Collections.sort((List<Comparable>) obj);
            /*System.out.println("************************************");
            System.out.println("Depth: "+ obj.getDepth());
            System.out.println("************************************");*/
            //compareTo(obj);
            obj.draw(batch);
        }
    }

    /*@Override
    public int compareTo(IDrawable otherObj) {
        return 0;
        //return Integer.compare((int) getDepth(), (int) otherObj.getDepth());
    }*/
}
