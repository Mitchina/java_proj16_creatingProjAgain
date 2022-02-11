package com.badlogic.mygame.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.mygame.model.Player;

public class PlayerController {
    public static Player player;
    static String player2SpritesRelativePath = "player2AnimationSheet.png";

    public static void initializeController(){
        player = new Player(new Vector2(5,5), 48, 48, player2SpritesRelativePath);
    }
    public static void update(float deltaTime){
        player.update(deltaTime);

    }
}
