package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class Asset_GameOver {
    public static Texture playAgainButton;
    public static Texture menuButton;
    public static Music failedMusic;
    public static Texture backgroundTex;
    public static Texture GameOverTex;

    public static void load() {
        backgroundTex = new Texture("Image/background_GameOver.png");
        GameOverTex = new Texture("Image/GameOverText.png");
        playAgainButton = new Texture("Image/TryAgainButton.png");
        menuButton = new Texture("Image/MenuButton.png");
        failedMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/FailedSound.ogg"));
        failedMusic.setLooping(false);
        failedMusic.setVolume(0.6f);

        // --- Áp dụng Linear Filter ---
        AssetUtils.applyLinearFilter(backgroundTex, GameOverTex, playAgainButton, menuButton);
    }

    public static void dispose() {
        AssetUtils.disposeResources(playAgainButton, menuButton, failedMusic, backgroundTex, GameOverTex);
    }
}
