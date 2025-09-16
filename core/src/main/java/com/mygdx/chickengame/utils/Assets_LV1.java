package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets_LV1 {
    public static Texture enemyTex;
    public static Texture backgroundTex;

    public static Sound ChickenHit;


    public static Music BGL1;
    public static void load() {
        enemyTex = new Texture("Image/Chick.png");
        backgroundTex = new Texture("Image/background1.png");

        ChickenHit = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/ChickenDie.ogg"));

        BGL1 = Gdx.audio.newMusic(Gdx.files.internal("Music/Level1Music.ogg"));
        BGL1.setLooping(true);
        BGL1.setVolume(0.5f);

    }
    public static void dispose() {
        enemyTex.dispose();
        backgroundTex.dispose();
        ChickenHit.dispose();
    }
}

