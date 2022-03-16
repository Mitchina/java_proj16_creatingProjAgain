package com.badlogic.mygame.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.mygame.abstractClasses.DrawableObjects;
import com.badlogic.mygame.controller.LevelController;
import com.badlogic.mygame.interfaces.IDrawable;

import java.util.List;

// we don't need to create an obj of type bodies, it will be static
public class Bodies extends DrawableObjects {
    // to create a body, we need to pass an mapObject found in LevelController.createLevelBodies()
    public static void createBody(MapObject tiledMapObject){
        // get the type of body this obj is // ex: solid
        // in the properties we've <object name="decoration" type="solid" x, y, width and height for each of them>
        String bodyType = tiledMapObject.getProperties().get("type").toString(); // convert it to a string

        if(bodyType.equalsIgnoreCase("solid")){
            // create box 2d body
            // we need to know which shape it has
            float width;
            float height;
            float x;
            float y;
            if (tiledMapObject instanceof RectangleMapObject) {
                width = (int) ((RectangleMapObject) tiledMapObject).getRectangle().width * LevelController.UNIT_SCALE;
                height = (int) ((RectangleMapObject) tiledMapObject).getRectangle().height * LevelController.UNIT_SCALE;
                // Convert the get position to the Position Scale
                x = (int) ((RectangleMapObject) tiledMapObject).getRectangle().x * LevelController.UNIT_SCALE;
                y = (int) ((RectangleMapObject) tiledMapObject).getRectangle().y * LevelController.UNIT_SCALE;
                //System.out.println("RectangleMapObject width: " + width);
                //System.out.println("RectangleMapObject height: " + height);
                //System.out.println("RectangleMapObject x: " + x);
                //System.out.println("RectangleMapObject y: " + y);
            }
            else if (tiledMapObject instanceof EllipseMapObject) {
                width = (int) ((EllipseMapObject) tiledMapObject).getEllipse().width * LevelController.UNIT_SCALE;
                height = (int) ((EllipseMapObject) tiledMapObject).getEllipse().height * LevelController.UNIT_SCALE;

                // Convert the get position to the Position Scale
                x = (int) ((EllipseMapObject) tiledMapObject).getEllipse().x * LevelController.UNIT_SCALE;
                y = (int) ((EllipseMapObject) tiledMapObject).getEllipse().y * LevelController.UNIT_SCALE;
                //System.out.println("EllipseMapObject width: " + width);
                //System.out.println("EllipseMapObject height: " + height);
                //System.out.println("EllipseMapObject x: " + x);
                //System.out.println("EllipseMapObject y: " + y);
            }
            else {
                width = 32; // width * LevelController.UNIT_SCALE
                height = 32;
                // Convert the get position to the Position Scale
                x = 5 * LevelController.UNIT_SCALE;
                y = 5 * LevelController.UNIT_SCALE;
                System.out.println("-----See more about that obj-----");
            }

            Vector2 position = new Vector2(x,y);
            Body physicBody = createBox2d(position, width, height);
        }
    }

    static Body createBox2d(Vector2 position, float boxWidth, float boxHeight) {
        BodyDef bodyDefinition = new BodyDef();
        // for player the body type was dynamic
        // for the other objs will be static bodies // not moving ones
        bodyDefinition.type = BodyDef.BodyType.StaticBody;
        bodyDefinition.position.set(position);

        Body boxBody = LevelController.world.createBody(bodyDefinition);

        //boxBody.setUserData(this);
        boxBody.setType(BodyDef.BodyType.StaticBody);

        PolygonShape rectangleShape = new PolygonShape();
        // if the object is tall, give it a smaller rect.
        if(boxHeight > 25){
            rectangleShape.setAsBox(boxWidth/2 - 2, boxHeight/2 - 10, new Vector2(boxWidth/2, boxHeight/2 - 2), 0f);
        }
        else
            rectangleShape.setAsBox(boxWidth/2 - 2, boxHeight/2 - 6, new Vector2(boxWidth/2, boxHeight/2), 0f);

        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = rectangleShape;

        boxBody.createFixture(fixtureDefinition);
        rectangleShape.dispose();
        return boxBody;
    }

    @Override
    public int compareTo(List<IDrawable> o) {
        return 0;
    }
}
