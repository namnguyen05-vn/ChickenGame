package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Asset_GameOver;
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.Assets_Menu;
import com.mygdx.chickengame.utils.UIHelper;
import com.mygdx.chickengame.utils.ParticleEffect;
import com.mygdx.chickengame.utils.PlayerState;
import com.mygdx.chickengame.utils.UIHelper;

/**
 * Màn Game Over:
 * - Dùng hiệu ứng giao diện kiểu Menu (background full, title pulsate, shadow, particle)
 * - CHUỘT là chính:
 *      + Try Again (TRÊN)  → luôn vào Level1
 *      + Menu (DƯỚI)       → về MenuScreen
 * - ESC: thoát game
 * - Nhạc thất bại FailedSound.ogg phát 1 lần khi vào màn
 */
public class GameOverScreen implements Screen {
    private final ChickenGame game;
    private SpriteBatch batch;

    private int SCREEN_WIDTH, SCREEN_HEIGHT;

    // Nút (giữ comment để thuyết trình)
    private Rectangle playAgainBtn, menuBtn; // TryAgain ở TRÊN, Menu ở DƯỚI
    private Vector3 touchPos;

    // Hiệu ứng giống Menu
    private float animationTime = 0f;
    private boolean isPlayAgainHovered = false, isMenuHovered = false;
    private static final float BTN_W = 240f, BTN_H = 70f, BTN_SP = 20f;
    private static final float TITLE_W = 400f, TITLE_H = 100f;
    private static final float PULSE_SPEED = 2.0f;

    private ParticleEffect particles;

    public GameOverScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.touchPos = new Vector3();

        this.SCREEN_WIDTH  = Gdx.graphics.getWidth();
        this.SCREEN_HEIGHT = Gdx.graphics.getHeight();

        Assets_Common.load(); // dùng bulletTex cho particle nếu sẵn có
        Asset_GameOver.load(); // dùng asset nhóm trưởng (ảnh + FailedSound)

        setupButtons();
        particles = new ParticleEffect();

        // CHANGED: Phát FailedSound khi vào màn
        if (Asset_GameOver.failedMusic != null) {
            Asset_GameOver.failedMusic.stop();
            Asset_GameOver.failedMusic.play();
        }
    }

    private void setupButtons() {
        int totalH = (int)(BTN_H * 2 + BTN_SP);
        int startY = (SCREEN_HEIGHT / 2) - (totalH / 2);

        // Try Again ở TRÊN
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

        // Hover
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchPos.y = SCREEN_HEIGHT - touchPos.y;
        isPlayAgainHovered = playAgainBtn.contains(touchPos.x, touchPos.y);
        isMenuHovered      = menuBtn.contains(touchPos.x, touchPos.y);

        handleInput(); // CHUỘT là chính (ESC thoát)

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // BG (full) + alpha nhẹ giống menu
        if (Asset_GameOver.backgroundTex != null) {
            batch.setColor(1f, 1f, 1f, 0.9f);
            batch.draw(Asset_GameOver.backgroundTex, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            batch.setColor(1f, 1f, 1f, 1f);
        }

        // Particles nếu có
        if (Assets_Common.bulletTex != null) {
            particles.render(batch, Assets_Common.bulletTex);
        }

        // Title GameOverText với pulse/floating
        drawPulsingTitle();

        // Nút (ảnh chữ) + hover shadow
        drawButton(Asset_GameOver.playAgainButton, playAgainBtn, isPlayAgainHovered);
        drawButton(Asset_GameOver.menuButton,      menuBtn,      isMenuHovered);

        batch.end();
    }

    private void drawPulsingTitle() {
        if (Asset_GameOver.GameOverTex == null) return;

        float pulseScale     = UIHelper.calculatePulseScale(animationTime, PULSE_SPEED, 0.95f, 1.05f);
        float adjustedWidth  = TITLE_W * pulseScale;
        float adjustedHeight = TITLE_H * pulseScale;
        float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 8f, 1.5f);
        float titleY = SCREEN_HEIGHT * 0.75f - adjustedHeight / 2f;

        // shadow
        batch.setColor(0f, 0f, 0f, 0.45f);
        batch.draw(Asset_GameOver.GameOverTex,
            SCREEN_WIDTH / 2f - adjustedWidth / 2f + 4,
            titleY + floatingOffset - 4,
            adjustedWidth, adjustedHeight);

        // main
        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(Asset_GameOver.GameOverTex,
            SCREEN_WIDTH / 2f - adjustedWidth / 2f,
            titleY + floatingOffset,
            adjustedWidth, adjustedHeight);
    }

    private void drawButton(com.badlogic.gdx.graphics.Texture buttonTexture, Rectangle bounds, boolean isHovered) {
        if (buttonTexture == null) return;

        // Đổ bóng + nổi nhẹ giống Menu
        UIHelper.drawButtonWithShadow(batch, buttonTexture, bounds, isHovered);

        if (isHovered) {
            float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 3f, 4f);
            Rectangle fb = new Rectangle(bounds.x, bounds.y + floatingOffset, bounds.width, bounds.height);
            batch.setColor(1f, 1f, 1f, 0.9f);
            batch.draw(buttonTexture, fb.x, fb.y, fb.width, fb.height);
            batch.setColor(1f, 1f, 1f, 1f);
        } else {
            batch.draw(buttonTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void handleInput() {
        // ESC: thoát game
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            click();
            Gdx.app.exit();
            return;
        }

        // CHUỘT: TryAgain → Level1, Menu → MenuScreen
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.y = SCREEN_HEIGHT - touchPos.y;

            if (playAgainBtn.contains(touchPos.x, touchPos.y)) {
                click();
                if (Asset_GameOver.failedMusic != null) Asset_GameOver.failedMusic.stop();
                PlayerState.reset(); // reset nếu muốn bắt đầu sạch
                game.setScreen(new Level1Screen(game)); // Luôn về Level1
                return;
            }

            if (menuBtn.contains(touchPos.x, touchPos.y)) {
                click();
                if (Asset_GameOver.failedMusic != null) Asset_GameOver.failedMusic.stop();
                game.setScreen(new MenuScreen(game));
                return;
            }
        }

        // (BỎ phím M/R/ENTER/SPACE theo yêu cầu)
    }

    private void click() {
        if (Assets_Common.BulletSound != null) Assets_Common.BulletSound.play(0.3f);
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { this.SCREEN_WIDTH = width; this.SCREEN_HEIGHT = height; setupButtons(); }
    @Override public void pause()  { if (Asset_GameOver.failedMusic != null) Asset_GameOver.failedMusic.pause(); }
    @Override public void resume() { if (Asset_GameOver.failedMusic != null) Asset_GameOver.failedMusic.play(); }
    @Override public void hide()   { /* không stop cưỡng bức để đảm bảo onClick đã stop */ }
    @Override public void dispose() {
        if (batch != null) batch.dispose();
        if (particles != null) particles.dispose();
        // Tùy vòng đời, bạn có thể gọi Asset_GameOver.dispose() khi chắc chắn không quay lại màn này nữa
    }
}
