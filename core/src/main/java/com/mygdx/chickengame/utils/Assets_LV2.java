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

    public static Music BGMusic;
    public static void load() {
        enemyTex = new Texture("Image/Chicken1.png");
        backgroundTex = new Texture("Image/background2.png");
        EnemyBullet = new Texture("Image/EnemyBullet.png");

        ChickenHit = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/ChickenDie.ogg"));

        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Level2Music.ogg"));
        BGMusic.setLooping(true);
        BGMusic.setVolume(0.5f);

        // --- Áp dụng Linear Filter ---
        AssetUtils.applyLinearFilter(enemyTex, backgroundTex, EnemyBullet);
    }
    public static void dispose() {
        AssetUtils.disposeResources(enemyTex, backgroundTex, ChickenHit, EnemyBullet, BGMusic);
    }
}
