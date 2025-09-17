package com.mygdx.chickengame.utils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
public class Asset_Winning {
    public static Texture PlayAgain;
    public static Music BGMusic;
    public static Texture backgroundTex;

    public static void load(){
        backgroundTex = new Texture("Image/background_menu.png");
        PlayAgain = new Texture("Image/PlayAgainButton.png");
        BGMusic =  Gdx.audio.newMusic(Gdx.files.internal("Music/WinningSound.ogg"));
    }
    public static void dispose(){
        backgroundTex.dispose();
        PlayAgain.dispose();
        BGMusic.dispose();
    }
}
