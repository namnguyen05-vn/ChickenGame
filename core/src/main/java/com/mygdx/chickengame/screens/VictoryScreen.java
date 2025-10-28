package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Asset_Winning;
import com.mygdx.chickengame.utils.Assets_Common; // dùng particle bulletTex nếu đã có
import com.mygdx.chickengame.utils.UIHelper;
import com.mygdx.chickengame.utils.ParticleEffect;
import com.mygdx.chickengame.utils.PlayerState;

/**
 * Màn Victory:
 * - Giao diện style Menu (background, title pulse/floating, hover, particle)
 * - CHUỘT:
 *      + PlayAgain (TRÊN) → luôn về Level1
 *      + Menu (DƯỚI)      → MenuScreen
 * - Không còn phím M/R (giữ comment để thuyết trình)
 */
public class VictoryScreen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;

    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    private Rectangle playAgainButtonBounds; // Chơi lại từ Level 1
    private Rectangle menuButtonBounds;      // Về Menu

    private Vector3 touchPos;

    // Hiệu ứng giống Menu
    private float animationTime = 0f;
    private boolean isPlayAgainHovered = false;
    private boolean isMenuHovered = false;
    private static final float PULSE_SPEED = 2.0f;
    private static final float BTN_W = 240f, BTN_H = 70f, BTN_SP = 20f;

    private ParticleEffect particles;

    public VictoryScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.touchPos = new Vector3();

        this.SCREEN_WIDTH  = Gdx.graphics.getWidth();
        this.SCREEN_HEIGHT = Gdx.graphics.getHeight();

        Assets_Common.load();  // bulletTex (nếu có)
        Asset_Winning.load();  // asset Victory (nhóm trưởng)

        setupButtons();
        particles = new ParticleEffect();

        if (Asset_Winning.winningMusic != null) {
            Asset_Winning.winningMusic.play();
        }
    }

    private void setupButtons() {
        int totalButtonsHeight = (int)(BTN_H * 2 + BTN_SP);
        int startY = (SCREEN_HEIGHT / 2) - (totalButtonsHeight / 2);

        // Trên: "Play Again"
        playAgainButtonBounds = new Rectangle(
            SCREEN_WIDTH / 2f - BTN_W / 2f,
            startY + BTN_H + BTN_SP,
            BTN_W, BTN_H
        );

        // Dưới: "Menu"
        menuButtonBounds = new Rectangle(
            SCREEN_WIDTH / 2f - BTN_W / 2f,
            startY,
            BTN_W, BTN_H
        );
    }

    @Override
    public void render(float delta) {
        animationTime += delta;
        particles.update(delta);

        // Hover detection
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchPos.y = SCREEN_HEIGHT - touchPos.y;
        isPlayAgainHovered = playAgainButtonBounds.contains(touchPos.x, touchPos.y);
        isMenuHovered      = menuButtonBounds.contains(touchPos.x, touchPos.y);

        handleInput(); // CHUỘT: PlayAgain → LV1, Menu → MenuScreen

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Background
        if (Asset_Winning.backgroundTex != null) {
            batch.setColor(1f, 1f, 1f, 0.9f);
            batch.draw(Asset_Winning.backgroundTex, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            batch.setColor(1f, 1f, 1f, 1f);
        }

        // Particles (nếu có bulletTex)
        if (Assets_Common.bulletTex != null) {
            particles.render(batch, Assets_Common.bulletTex);
        }

        // Title Victory (pulse/floating)
        drawVictoryTitle();

        // Buttons
        drawButton(Asset_Winning.playAgainButton, playAgainButtonBounds, isPlayAgainHovered);
        drawButton(Asset_Winning.menuButton,      menuButtonBounds,      isMenuHovered);

        batch.end();
    }

    private void drawVictoryTitle() {
        if (Asset_Winning.VictoryTex == null) return;

        float titleWidth = 400f;
        float titleHeight = 100f;
        float pulseScale = UIHelper.calculatePulseScale(animationTime, PULSE_SPEED, 0.95f, 1.05f);
        float adjustedWidth = titleWidth * pulseScale;
        float adjustedHeight = titleHeight * pulseScale;
        float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 8f, 1.5f);
        float titleY = SCREEN_HEIGHT * 0.75f - adjustedHeight / 2f;

        // shadow
        batch.setColor(0f, 0f, 0f, 0.45f);
        batch.draw(Asset_Winning.VictoryTex,
            SCREEN_WIDTH / 2f - adjustedWidth / 2f + 4,
            titleY + floatingOffset - 4,
            adjustedWidth, adjustedHeight);

        // main
        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(Asset_Winning.VictoryTex,
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
        // CHỈ dùng chuột (không M/R)
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.y = SCREEN_HEIGHT - touchPos.y;

            // Chơi lại → Level1
            if (playAgainButtonBounds.contains(touchPos.x, touchPos.y)) {
                if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.stop();
                PlayerState.reset(); // bắt đầu sạch (nếu muốn)
                game.setScreen(new Level1Screen(game));
                return;
            }

            // Về Menu
            if (menuButtonBounds.contains(touchPos.x, touchPos.y)) {
                if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.stop();
                game.setScreen(new MenuScreen(game));
                return;
            }
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { this.SCREEN_WIDTH = width; this.SCREEN_HEIGHT = height; setupButtons(); }
    @Override public void pause()  { if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.pause(); }
    @Override public void resume() { if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.play(); }
    @Override public void hide()   { if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.stop(); }
    @Override public void dispose() {
        if (batch != null)     batch.dispose();
        if (particles != null) particles.dispose();
        // Asset_Winning.dispose(); // tuỳ vòng đời bạn muốn dispose khi nào
    }
}
