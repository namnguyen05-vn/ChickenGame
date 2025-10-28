package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

/**
 * Asset cho màn Victory:
 * - Title: Image/VictoryText.png
 * - Buttons:
 *      + Image/PlayAgainButton.png  (Chơi lại)
 *      + Image/menugameover.png     (Về menu)
 * - Background: Image/background_Winning.png
 * - Music: Music/WinningSound.ogg
 */
public class Asset_Winning {
    public static Texture backgroundTex;    // Image/background_Winning.png
    public static Texture titleVictoryTex;  // Image/VictoryText.png
    public static Texture playAgainButton;  // Image/PlayAgainButton.png
    public static Texture menuButton;       // Image/menugameover.png

    public static Music winningMusic;       // Music/WinningSound.ogg
    private static boolean loaded = false;

    public static void load() {
        if (loaded) return;

        backgroundTex   = new Texture("Image/background_Winning.png");
        titleVictoryTex = new Texture("Image/VictoryText.png");
        playAgainButton = new Texture("Image/PlayAgainButton.png");
        menuButton      = new Texture("Image/menugameover.png");

        if (backgroundTex   != null) backgroundTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (titleVictoryTex != null) titleVictoryTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (playAgainButton != null) playAgainButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (menuButton      != null) menuButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        winningMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/WinningSound.ogg"));
        winningMusic.setLooping(false);
        winningMusic.setVolume(0.7f);

        loaded = true;
    }

    public static void dispose() {
        if (backgroundTex   != null) backgroundTex.dispose();
        if (titleVictoryTex != null) titleVictoryTex.dispose();
        if (playAgainButton != null) playAgainButton.dispose();
        if (menuButton      != null) menuButton.dispose();
        if (winningMusic    != null) winningMusic.dispose();
        loaded = false;
    }
}
