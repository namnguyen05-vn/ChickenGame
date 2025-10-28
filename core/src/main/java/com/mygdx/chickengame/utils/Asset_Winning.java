package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

/**
 * Asset cho màn Victory (dùng tên/tệp theo nhóm trưởng):
 * - Background: Image/background_Winning.png
 * - Title     : Image/VictoryTex.png
 * - Buttons   : Image/PlayAgainButton.png, Image/MenuButton.png
 * - Nhạc win  : Music/WinningSound.ogg (phát 1 lần)
 */
public class Asset_Winning {
    public static Texture backgroundTex;  // Image/background_Winning.png
    public static Texture VictoryTex;     // Image/VictoryTex.png
    public static Texture playAgainButton;// Image/PlayAgainButton.png
    public static Texture menuButton;     // Image/MenuButton.png

    public static Music winningMusic;     // Music/WinningSound.ogg

    public static void load() {
        backgroundTex    = new Texture("Image/background_Winning.png");
        VictoryTex       = new Texture("Image/VictoryText.png");
        playAgainButton  = new Texture("Image/PlayAgainButton.png");
        menuButton       = new Texture("Image/MenuButton.png");

        backgroundTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        VictoryTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playAgainButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        menuButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        winningMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/WinningSound.ogg"));
        winningMusic.setLooping(false);
        winningMusic.setVolume(0.7f);
    }

    public static void dispose() {
        if (backgroundTex != null)   backgroundTex.dispose();
        if (VictoryTex != null)      VictoryTex.dispose();
        if (playAgainButton != null) playAgainButton.dispose();
        if (menuButton != null)      menuButton.dispose();
        if (winningMusic != null)    winningMusic.dispose();
    }
}
