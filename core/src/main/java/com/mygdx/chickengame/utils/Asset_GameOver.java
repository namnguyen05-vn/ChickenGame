package com.mygdx.chickengame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/**
 * Asset cho màn Game Over.
 * - Ảnh: background_gameover.png, Gameover.png, playagain.png, menugameover.png (thư mục Image/)
 * - Nhạc: dùng lại nhạc menu (Assets_Menu.BGMusic).
 * - Có setFilter Linear để thu nhỏ/phóng to chữ mượt hơn.
 */
public class Asset_GameOver {
    public static Texture backgroundTex;     // Image/background_gameover.png
    public static Texture titleGameOverTex;  // Image/Gameover.png
    public static Texture btnPlayAgainTex;   // Image/playagain.png
    public static Texture btnMenuTex;        // Image/menugameover.png

    public static Music BGMusic;             // dùng lại nhạc menu
    private static boolean loaded = false;

    public static void load() {
        if (loaded) return;

        backgroundTex    = safeLoad("Image/background_gameover.png");
        titleGameOverTex = safeLoad("Image/Gameover.png");
        btnPlayAgainTex  = safeLoad("Image/playagain.png");
        btnMenuTex       = safeLoad("Image/menugameover.png");

        // filter mượt khi scale
        if (backgroundTex    != null) backgroundTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (titleGameOverTex != null) titleGameOverTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (btnPlayAgainTex  != null) btnPlayAgainTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (btnMenuTex       != null) btnMenuTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // nhạc dùng lại từ menu
        BGMusic = Assets_Menu.BGMusic; // có thể null nếu menu chưa load
        if (BGMusic != null) {
            BGMusic.setLooping(true);
        }

        loaded = true;
    }

    private static Texture safeLoad(String path) {
        if (Gdx.files.internal(path).exists()) {
            try {
                return new Texture(path);
            } catch (Exception e) {
                Gdx.app.error("Asset_GameOver", "Lỗi load " + path + ": " + e.getMessage());
                return null;
            }
        } else {
            Gdx.app.error("Asset_GameOver", "Thiếu file: " + path);
            return null;
        }
    }

    public static void dispose() {
        // KHÔNG dispose BGMusic vì dùng chung với menu
        if (backgroundTex    != null) backgroundTex.dispose();
        if (titleGameOverTex != null) titleGameOverTex.dispose();
        if (btnPlayAgainTex  != null) btnPlayAgainTex.dispose();
        if (btnMenuTex       != null) btnMenuTex.dispose();
        loaded = false;
    }
}
