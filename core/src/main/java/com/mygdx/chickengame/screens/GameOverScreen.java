package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Asset_GameOver;
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.ParticleEffect;
import com.mygdx.chickengame.utils.PlayerState;
import com.mygdx.chickengame.utils.UIHelper;

public class GameOverScreen implements Screen {
    private final ChickenGame game;
    private SpriteBatch batch;
    private ShapeRenderer shape;

    private int SCREEN_WIDTH, SCREEN_HEIGHT;

    private Rectangle playAgainBtn, menuBtn; // TryAgain ở TRÊN, menu ở DƯỚI
    private Vector3 touchPos;

    private float animationTime = 0f;
    private boolean isPlayAgainHovered = false, isMenuHovered = false;
    private ParticleEffect particles;

    // Kích thước giống menu
    private static final float TITLE_W = 400f;
    private static final float TITLE_H = 100f;
    private static final float BTN_W   = 240f;
    private static final float BTN_H   = 70f;
    private static final float BTN_SP  = 20f;

    public GameOverScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.touchPos = new Vector3();

        this.SCREEN_WIDTH  = Gdx.graphics.getWidth();
        this.SCREEN_HEIGHT = Gdx.graphics.getHeight();

        Assets_Common.load();
        Asset_GameOver.load(); // CHANGED: âm FailedSound

        setupButtons();
        particles = new ParticleEffect();

        // CHANGED: phát FailedSound (nếu có)
        if (Asset_GameOver.BGMusic != null) {
            Asset_GameOver.BGMusic.stop();
            Asset_GameOver.BGMusic.play();
        }
    }

    private void setupButtons() {
        int totalH = (int)(BTN_H * 2 + BTN_SP);
        int startY = (SCREEN_HEIGHT / 2) - (totalH / 2);

        // Try again ở TRÊN
        playAgainBtn = new Rectangle(
            SCREEN_WIDTH / 2f - BTN_W / 2f,
            startY + BTN_H + BTN_SP,
            BTN_W, BTN_H
        );

        // Menu ở DƯỚI
        menuBtn = new Rectangle(
            SCREEN_WIDTH / 2f - BTN_W / 2f,
            startY,
            BTN_W, BTN_H
        );
    }

    @Override
    public void render(float delta) {
        animationTime += delta;
        particles.update(delta);

        // hover
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchPos.y = SCREEN_HEIGHT - touchPos.y;
        isPlayAgainHovered = playAgainBtn.contains(touchPos.x, touchPos.y);
        isMenuHovered      = menuBtn.contains(touchPos.x, touchPos.y);

        handleInput(); // CHANGED: chỉ dùng chuột (ESC để thoát)

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // BG + Title + Particles (giống menu)
        batch.begin();
        batch.setColor(1,1,1,1);

        if (Asset_GameOver.backgroundTex != null)
            batch.draw(Asset_GameOver.backgroundTex, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (Assets_Common.bulletTex != null)
            particles.render(batch, Assets_Common.bulletTex);

        drawTitle();
        batch.end();

        // Vẽ nút (ảnh chữ)
        batch.begin();
        drawButton(Asset_GameOver.btnPlayAgainTex, playAgainBtn, isPlayAgainHovered);
        drawButton(Asset_GameOver.btnMenuTex,      menuBtn,      isMenuHovered);
        batch.end();
    }

    private void drawTitle() {
        if (Asset_GameOver.titleGameOverTex == null) return;

        float pulse = UIHelper.calculatePulseScale(animationTime, 2.0f, 0.95f, 1.05f);
        float w = TITLE_W * pulse;
        float h = TITLE_H * pulse;
        float y = SCREEN_HEIGHT * 0.78f - h / 2f;

        // shadow nhẹ
        batch.setColor(0, 0, 0, 0.45f);
        batch.draw(Asset_GameOver.titleGameOverTex, SCREEN_WIDTH/2f - w/2f + 4, y - 4, w, h);
        batch.setColor(1, 1, 1, 1);
        batch.draw(Asset_GameOver.titleGameOverTex, SCREEN_WIDTH/2f - w/2f,     y,     w, h);
    }

    private void drawButton(com.badlogic.gdx.graphics.Texture tex, Rectangle bounds, boolean hovered) {
        if (tex == null) return;
        float dy = hovered ? UIHelper.calculateFloatingOffset(animationTime, 3f, 4f) : 0f;
        batch.setColor(1, 1, 1, 1);
        batch.draw(tex, bounds.x, bounds.y + dy, bounds.width, bounds.height);
    }

    private void handleInput() {
        // CHANGED: Chỉ dùng chuột để thao tác (giống yêu cầu). ESC vẫn cho thoát.
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            click();
            Gdx.app.exit();
            return;
        }

        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.y = SCREEN_HEIGHT - touchPos.y;

            // Try Again → luôn bắt đầu Level 1
            if (playAgainBtn.contains(touchPos.x, touchPos.y)) {
                click();
                if (Asset_GameOver.BGMusic != null) Asset_GameOver.BGMusic.stop();
                PlayerState.reset(); // reset state nếu bạn muốn
                game.setScreen(new Level1Screen(game)); // CHANGED: luôn LV1
                return;
            }

            // Menu → về MenuScreen
            if (menuBtn.contains(touchPos.x, touchPos.y)) {
                click();
                if (Asset_GameOver.BGMusic != null) Asset_GameOver.BGMusic.stop();
                game.setScreen(new MenuScreen(game));
                return;
            }
        }

        // (BỎ cơ chế phím M/R/ENTER/SPACE — theo yêu cầu)
        // if (Gdx.input.isKeyJustPressed(Input.Keys.M)) { ... }      // REMOVED
        // if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { ... }      // REMOVED
        // if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { ... }  // REMOVED
        // if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { ... }  // REMOVED
    }

    private void click() {
        if (Assets_Common.BulletSound != null) Assets_Common.BulletSound.play(0.3f);
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { this.SCREEN_WIDTH = width; this.SCREEN_HEIGHT = height; setupButtons(); }
    @Override public void pause()  { if (Asset_GameOver.BGMusic != null) Asset_GameOver.BGMusic.pause(); }
    @Override public void resume() { if (Asset_GameOver.BGMusic != null) Asset_GameOver.BGMusic.play(); }
    @Override public void hide()   {}
    @Override public void dispose() {
        if (batch != null) batch.dispose();
        if (shape != null) shape.dispose();
        if (particles != null) particles.dispose();
        // Tùy bạn: có thể gọi Asset_GameOver.dispose() khi chắc chắn không quay lại màn này nữa
    }
}
