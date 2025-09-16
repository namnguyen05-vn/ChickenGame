package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets_LV2 {
    public static Texture enemyTex;
    public static Texture backgroundTex;
    public static Texture EnemyBullet;

    public static Sound ChickenHit;

    public static Music BGL2;
    public static void load() {
        enemyTex = new Texture("Image/Chicken1.png");
        backgroundTex = new Texture("Image/background2.png");
        EnemyBullet = new Texture("Image/EnemyBullet.png");

        ChickenHit = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/ChickenDie.ogg"));

        BGL2 = Gdx.audio.newMusic(Gdx.files.internal("Music/Level2Music.ogg"));
        BGL2.setLooping(true);
        BGL2.setVolume(0.5f);

    }
    public static void dispose() {
        enemyTex.dispose();
        backgroundTex.dispose();
        ChickenHit.dispose();
        EnemyBullet.dispose();
        BGL2.dispose();
    }
}

