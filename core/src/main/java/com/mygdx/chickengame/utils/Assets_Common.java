package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Lớp chứa tài nguyên chung: ảnh và âm thanh dùng trong toàn game.
 * Load một lần khi khởi động game, dùng lại ở mọi màn chơi.
 */
public class Assets_Common {

    // =============================
    // HÌNH ẢNH
    // =============================

    // Player (3 cấp)
    public static Texture playerLV1;
    public static Texture playerLV2;
    public static Texture playerLV3;
    public static Texture playerTex;
    public static Texture playerUpgradedTex;

    // Bullet (5 cấp)
    public static Texture bulletLV1;
    public static Texture bulletLV2;
    public static Texture bulletLV3;
    public static Texture bulletLV4;
    public static Texture bulletLV5;
    public static Texture bulletTex;
    public static Texture upgradedbulletTex;

    // Power-up
    public static Texture powerUpTex;

    // =============================
    // ÂM THANH
    // =============================
    public static Sound BulletSound;
    public static Sound PlayerExplosion;
    public static Sound PowerUpSound;

    // =============================
    // FONT (THÊM MỚI)
    // =============================
    public static BitmapFont font;

    // =============================
    // LOAD
    // =============================
    public static void load() {
        // --- Load ảnh Player ---
        playerLV1 = new Texture("Image/player1.png");
        playerLV2 = new Texture("Image/player2.png");
        playerLV3 = new Texture("Image/player3.png");

        playerTex = playerLV1;
        playerUpgradedTex = playerLV2;

        // --- Load ảnh Bullet ---
        bulletLV1 = new Texture("Image/bullet.png");
        bulletLV2 = new Texture("Image/bullet.png");
        bulletLV3 = new Texture("Image/UpdateBullet.png");
        bulletLV4 = new Texture("Image/UpdateBullet.png");
        bulletLV5 = new Texture("Image/UpdateBullet.png");

        bulletTex = bulletLV1;
        upgradedbulletTex = bulletLV3;

        // --- Power up icon ---
        powerUpTex = new Texture("Image/UpdateBullet.png");

        // --- Âm thanh ---
        BulletSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/laser1.ogg"));
        PlayerExplosion = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/PlayerDie.ogg"));
        PowerUpSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect/PowerUp.ogg"));

        // --- FONT (cực quan trọng để vẽ chữ MENU, LIVES...) ---
        font = new BitmapFont(); // font mặc định LibGDX
    }

    // =============================
    // GIẢI PHÓNG TÀI NGUYÊN
    // =============================
    public static void dispose() {
        playerLV1.dispose();
        playerLV2.dispose();
        playerLV3.dispose();

        bulletLV1.dispose();
        bulletLV2.dispose();
        bulletLV3.dispose();
        bulletLV4.dispose();
        bulletLV5.dispose();

        powerUpTex.dispose();

        BulletSound.dispose();
        PlayerExplosion.dispose();
        PowerUpSound.dispose();

        font.dispose(); // THÊM
    }
}
