package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets_LV1 {
    public static Texture enemyTex;
    public static Texture backgroundTex;
    public static Texture enemy_bulletsTex;
    public static Sound ChickenHit;


    public static Music BGMusic;
    public static void load() {
        enemyTex = new Texture("Image/Chick.png");
        backgroundTex = new Texture("Image/background1.png");
        enemy_bulletsTex = new Texture("Image/EnemyBullet.png");
        ChickenHit = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/ChickenDie.ogg"));

        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Level1Music.ogg"));
        BGMusic.setLooping(true);
        BGMusic.setVolume(0.5f);

        // --- Áp dụng Linear Filter ---
        AssetUtils.applyLinearFilter(enemyTex, backgroundTex, enemy_bulletsTex);
    }
    public static void dispose() {
        AssetUtils.disposeResources(enemyTex, enemy_bulletsTex, backgroundTex, ChickenHit, BGMusic);
    }
}

