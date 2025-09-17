package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;


public class Asset_GameOver {
    public static Texture TryAgain;
    public static Music BGMusic;

    public static void load(){
        Texture TryAgain = new Texture("Image/PlayAgainButton.png");
        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/FailedSound.ogg"));
    }
    public static void dispose(){
        TryAgain.dispose();
        BGMusic.dispose();
    }
}
