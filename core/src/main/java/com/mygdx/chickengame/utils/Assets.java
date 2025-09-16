package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture playerTex;
    public static Texture enemyTex;
    public static Texture bulletTex;
    public static Texture backgroundTex;

    public static void load() {
        playerTex = new Texture("Image/player2.png");
        enemyTex = new Texture("Image/Chick.png");
        bulletTex = new Texture("Image/bullet.png");
        backgroundTex = new Texture("Image/background2.png");
    }
    public static void dispose() {
        playerTex.dispose();
        enemyTex.dispose();
        bulletTex.dispose();
        backgroundTex.dispose();
    }
}
