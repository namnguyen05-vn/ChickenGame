package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture playerTex;
    public static Texture enemyTex;
    public static Texture bulletTex;
    public static Texture backgroundTex;

    public static void load() {
        playerTex = new Texture("player.png");
        enemyTex = new Texture("enemy.png");
        bulletTex = new Texture("bullet.png");
        backgroundTex = new Texture("background.png");
    }

    public static void dispose() {
        playerTex.dispose();
        enemyTex.dispose();
        bulletTex.dispose();
        backgroundTex.dispose();
    }
}
