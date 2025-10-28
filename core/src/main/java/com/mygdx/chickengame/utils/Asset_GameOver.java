package com.mygdx.chickengame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/**
 * Asset cho màn Game Over.
 * - Ảnh: background_GameOver.png, TryAgainButton.png, menugameover.png, GameOverText.png (thư mục Image/)
 * - Nhạc/Âm thanh: Music/FailedSound.ogg
 * - Có setFilter Linear để thu nhỏ/phóng to chữ mượt hơn.
 */
public class Asset_GameOver {
    public static Texture backgroundTex;     // Image/background_GameOver.png   // CHANGED (tên ảnh)
    public static Texture titleGameOverTex;  // Image/GameOverText.png
    public static Texture btnPlayAgainTex;   // Image/TryAgainButton.png        // CHANGED (tên ảnh)
    public static Texture btnMenuTex;        // Image/menugameover.png          // giữ nguyên

    public static Music BGMusic;             // FailedSound.ogg                 // CHANGED (nguồn nhạc)
    private static boolean loaded = false;

    public static void load() {
        if (loaded) return;

        // CHANGED: đồng bộ với tên file bạn có trong thư mục Image/
        backgroundTex    = safeLoad("Image/background_GameOver.png"); // CHANGED
        titleGameOverTex = safeLoad("Image/GameOverText.png");
        btnPlayAgainTex  = safeLoad("Image/TryAgainButton.png");      // CHANGED
        btnMenuTex       = safeLoad("Image/menugameover.png");

        // filter mượt khi scale
        if (backgroundTex    != null) backgroundTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (titleGameOverTex != null) titleGameOverTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (btnPlayAgainTex  != null) btnPlayAgainTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        if (btnMenuTex       != null) btnMenuTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // CHANGED: dùng FailedSound.ogg thay vì nhạc menu
        try {
            BGMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/FailedSound.ogg"));
            BGMusic.setLooping(false);  // âm thất bại chơi 1 lần
            BGMusic.setVolume(0.8f);
        } catch (Exception e) {
            Gdx.app.error("Asset_GameOver", "Không tìm thấy Music/FailedSound.ogg: " + e.getMessage());
            BGMusic = null;
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
        // CHANGED: có thể dispose nhạc vì không dùng chung như menu
        if (backgroundTex    != null) backgroundTex.dispose();
        if (titleGameOverTex != null) titleGameOverTex.dispose();
        if (btnPlayAgainTex  != null) btnPlayAgainTex.dispose();
        if (btnMenuTex       != null) btnMenuTex.dispose();
        if (BGMusic          != null) BGMusic.dispose(); // CHANGED
        loaded = false;
    }
}
