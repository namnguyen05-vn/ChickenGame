package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets {
    public static Texture playerTex;
    public static Texture enemyTex;
    public static Texture bulletTex;
    public static Texture backgroundTex;

    public static Sound BulletSound;
    public static Sound HitEnemySound;

    public static Music BGM;
    public static Music BGL1;
    public static Music BGL2;
    public static Music BGL3;
    public static void load() {
        playerTex = new Texture("Image/player2.png");
        enemyTex = new Texture("Image/Chick.png");
        bulletTex = new Texture("Image/bullet.png");
        backgroundTex = new Texture("Image/background2.png");

        BulletSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/laser1.ogg"));
        HitEnemySound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/HitEnemy.ogg"));

        BGM = Gdx.audio.newMusic(Gdx.files.internal("Music/BackgroundMusic.ogg"));
        BGM.setLooping(true);
        BGM.setVolume(0.5f);

        BGL1 = Gdx.audio.newMusic(Gdx.files.internal("Music/Level1Music.ogg"));
        BGL1.setLooping(true);
        BGL1.setVolume(0.5f);

        BGL2 = Gdx.audio.newMusic(Gdx.files.internal("Music/Level2Music.ogg"));
        BGL2.setLooping(true);
        BGL2.setVolume(0.5f);

        BGL3 = Gdx.audio.newMusic(Gdx.files.internal("Music/Level3Music.ogg"));
        BGL3.setLooping(true);
        BGL3.setVolume(0.5f);
    }
    public static void dispose() {
        playerTex.dispose();
        enemyTex.dispose();
        bulletTex.dispose();
        backgroundTex.dispose();
        BulletSound.dispose();
        HitEnemySound.dispose();
        BGM.dispose();
    }
}
