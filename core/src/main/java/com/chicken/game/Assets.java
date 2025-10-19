package com.chicken.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static AssetManager manager;
    public static Texture bgMenu;
    public static BitmapFont font;

    public static void load() {
        manager = new AssetManager();
        bgMenu = new Texture(Gdx.files.internal("bg_menu.png")); // core/assets/bg_menu.png
        font = new BitmapFont(); // dùng font mặc định
    }

    public static void dispose() {
        if (bgMenu != null) bgMenu.dispose();
        if (font != null) font.dispose();
        if (manager != null) manager.dispose();
    }
}
