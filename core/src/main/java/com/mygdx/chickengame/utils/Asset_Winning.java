package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class Asset_Winning {
    public static Texture playAgainButton;
    public static Texture menuButton;
    public static Music winningMusic;
    public static Texture backgroundTex;
    public static Texture VictoryTex;

    public static void load() {
        backgroundTex = new Texture("Image/background_Winning.png");
        VictoryTex = new Texture("Image/VictoryText.png");
        playAgainButton = new Texture("Image/PlayAgainButton.png");
        menuButton = new Texture("Image/MenuButton.png");
        winningMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/WinningSound.ogg"));
        winningMusic.setLooping(false);
        winningMusic.setVolume(0.7f);

        // --- Áp dụng Linear Filter ---
        AssetUtils.applyLinearFilter(backgroundTex, VictoryTex, playAgainButton, menuButton);
    }

    public static void dispose() {
        AssetUtils.disposeResources(backgroundTex, playAgainButton, menuButton, winningMusic, VictoryTex);
    }
}
