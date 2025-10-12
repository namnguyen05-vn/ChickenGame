package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Assets_Common {
    public static Texture playerTex;
    public static Texture playerUpgradedTex;
    public static Texture bulletTex;
    public static Texture powerUpTex;
    public static Texture upgradedbulletTex;

    public static Sound BulletSound;
    public static Sound PlayerExplosion;
    public static Sound PowerUpSound;

    public static void load() {
        playerTex = new Texture("Image/player1.png");
        playerUpgradedTex = new Texture("Image/player3.png");
        bulletTex = new Texture("Image/bullet.png");
        powerUpTex = new Texture("Image/UpdateBullet.png");
        upgradedbulletTex = new Texture("Image/bullet.png");

        BulletSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/laser1.ogg"));
        PlayerExplosion = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/PlayerDie.ogg"));
        PowerUpSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/PowerUp.ogg"));
    }

    public static void dispose() {
        playerTex.dispose();
        playerUpgradedTex.dispose();
        bulletTex.dispose();
        powerUpTex.dispose();
        upgradedbulletTex.dispose();
        BulletSound.dispose();
        PlayerExplosion.dispose();
        PowerUpSound.dispose();
    }
}
