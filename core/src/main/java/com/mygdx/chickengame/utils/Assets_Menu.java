package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;



public class Assets_Menu {
    public static Texture Title_Game;
    public static Texture backgroundTex;
    public static Texture Start;
    public static Texture Escape;

    public static Music BGMusic;
    public static void load(){
            Title_Game = new Texture("Image/MenuTitle2.png");
        backgroundTex = new Texture("Image/background_menu.png");
        Start = new Texture("Image/StartButton.png");
        Escape = new Texture("Image/EscapeButton.png");

        BGMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/BackgroundMusic.ogg"));
        BGMusic.setLooping(true);
        BGMusic.setVolume(0.5f);
    }

    public static void dispose() {
        Title_Game.dispose();
        backgroundTex.dispose();
        BGMusic.dispose();
        Start.dispose();
        Escape.dispose();
    }
}
