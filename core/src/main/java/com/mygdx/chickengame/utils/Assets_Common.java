package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets_Common {
    public static Texture playerTex;
    public static Texture bulletTex;

    public static Sound BulletSound;
    public static Sound PlayerExplosion;

    public static void load() {
        playerTex = new Texture("Image/player3.png");
        bulletTex = new Texture("Image/bullet.png");

        BulletSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/laser1.ogg"));
        PlayerExplosion = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/PlayerDie.ogg"));
    }
    public static void dispose() {
        playerTex.dispose();
        bulletTex.dispose();
        BulletSound.dispose();
        PlayerExplosion.dispose();
    }
}

