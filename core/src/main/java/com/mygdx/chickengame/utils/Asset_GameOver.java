package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

/**
 * Asset cho màn Game Over (dùng tên/tệp theo nhóm trưởng):
 * - Background: Image/background_GameOver.png
 * - Title     : Image/GameOverText.png
 * - Buttons   : Image/TryAgainButton.png (Try Again), Image/MenuButton.png (Menu)
 * - Nhạc fail : Music/FailedSound.ogg (phát 1 lần)
 */
public class Asset_GameOver {
    public static Texture backgroundTex;   // Image/background_GameOver.png
    public static Texture GameOverTex;     // Image/GameOverText.png
    public static Texture playAgainButton; // Image/TryAgainButton.png
    public static Texture menuButton;      // Image/MenuButton.png

    public static Music failedMusic;       // Music/FailedSound.ogg

    public static void load() {
        backgroundTex   = new Texture("Image/background_GameOver.png");
        GameOverTex     = new Texture("Image/GameOverText.png");
        playAgainButton = new Texture("Image/TryAgainButton.png");
        menuButton      = new Texture("Image/MenuButton.png");

        // Lọc mượt khi scale
        backgroundTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        GameOverTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playAgainButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        menuButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        failedMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/FailedSound.ogg"));
        failedMusic.setLooping(false); // phát 1 lần
        failedMusic.setVolume(0.6f);
    }

    public static void dispose() {
        if (playAgainButton != null) playAgainButton.dispose();
        if (menuButton != null)      menuButton.dispose();
        if (failedMusic != null)     failedMusic.dispose();
        if (backgroundTex != null)   backgroundTex.dispose();
        if (GameOverTex != null)     GameOverTex.dispose();
    }
}
