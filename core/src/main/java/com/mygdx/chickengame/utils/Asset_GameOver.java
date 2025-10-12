package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class Asset_GameOver {
    public static Texture playAgainButton;
    public static Texture menuButton;
    public static Music failedMusic;
    public static Texture backgroundTex;

    public static void load() {
        backgroundTex = new Texture("Image/background_menu.png");
        playAgainButton = new Texture("Image/PlayAgainButton.png");
        menuButton = new Texture("Image/EscapeButton.png");
        failedMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/FailedSound.ogg"));
        failedMusic.setLooping(false);
        failedMusic.setVolume(0.6f);
    }

    public static void dispose() {
        playAgainButton.dispose();
        menuButton.dispose();
        failedMusic.dispose();
        backgroundTex.dispose();
    }
}
