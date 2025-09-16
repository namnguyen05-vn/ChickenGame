package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Assets_Menu {
    public static Texture Title_Game;
    public static Texture Background;
    public static Texture Start;
    public static Texture Escape;

    public static Music Background_Music;
    public static void load(){
        Title_Game = new Texture("Image/MenuTitle.png");
        Background = new Texture("Image/background_menu.png");
        Start = new Texture("Image/StartButton.png");
        Escape = new Texture("Image/EscapeButton.png");

        Background_Music = Gdx.audio.newMusic(Gdx.files.internal("Music/BackgroundMusic.ogg"));
        Background_Music.setLooping(true);
        Background_Music.setVolume(0.5f);

        Background_Music = Gdx.audio.newMusic(Gdx.files.internal("Music/BackgroundMusic.ogg"));
        Background_Music.setLooping(true);
        Background_Music.setVolume(0.5f);
    }

    public static void dispose() {
        Title_Game.dispose();
        Background.dispose();
        Background_Music.dispose();
        Start.dispose();
        Escape.dispose();
    }
}
