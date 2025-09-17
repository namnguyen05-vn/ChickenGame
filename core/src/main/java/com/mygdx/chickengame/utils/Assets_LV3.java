package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets_LV3 {
    public static Texture enemyTex_Boss_100HP;
    public static Texture enemyTex_Boss_50HP;
    public static Texture enemyTex_Chicken2;
    public static Texture enemyTex_Chick;
    public static Texture backgroundTex;
    public static Texture ChickenBullet;
    public static Texture BossBullet;
    public static Sound ChickenHit;
    public static Sound BossHit;

    public static Music BGMusic;
    public static void load() {
        enemyTex_Boss_100HP = new Texture("Image/Boss_100%HP.png");
        enemyTex_Boss_50HP = new Texture("Image/Boss_50%HP.png");
        enemyTex_Chicken2 = new Texture("Image/Chicken2.png");
        enemyTex_Chick = new Texture("Image/Chick.png");
        backgroundTex = new Texture("Image/background3.png");
        ChickenBullet = new Texture("Image/EnemyBullet.png");
        BossBullet = new Texture("Image/BossBullet.png");

        ChickenHit = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/ChickenDie.ogg"));
        BossHit = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/HitEnemy.ogg"));

        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Level3Music.ogg"));
        BGMusic.setLooping(true);
        BGMusic.setVolume(0.5f);

    }
    public static void dispose() {
        enemyTex_Boss_100HP.dispose();
        enemyTex_Boss_50HP.dispose();
        enemyTex_Chicken2.dispose();
        enemyTex_Chick.dispose();
        backgroundTex.dispose();
        ChickenBullet.dispose();
        BossBullet.dispose();
        ChickenHit.dispose();
        BossHit.dispose();
        BGMusic.dispose();
    }
}

