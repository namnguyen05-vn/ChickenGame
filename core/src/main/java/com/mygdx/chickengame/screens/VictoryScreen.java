package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Asset_Winning;
import com.mygdx.chickengame.utils.Assets_Common;   // dùng particle bulletTex nếu đã có
import com.mygdx.chickengame.utils.UIHelper;
import com.mygdx.chickengame.utils.ParticleEffect;
import com.mygdx.chickengame.utils.PlayerState;

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
    private float buttonHoverScale = 1.0f;
    private static final float PULSE_SPEED = 2.0f;
    private static final float HOVER_SCALE = 1.1f;

    private ParticleEffect particles;

    public VictoryScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.touchPos = new Vector3();

        this.SCREEN_WIDTH  = Gdx.graphics.getWidth();
        this.SCREEN_HEIGHT = Gdx.graphics.getHeight();

        Assets_Common.load();  // bulletTex (nếu có)
        Asset_Winning.load();  // load asset Victory

        setupButtons();
        particles = new ParticleEffect();

        if (Asset_Winning.winningMusic != null) {
            Asset_Winning.winningMusic.play();
        }
    }

    private void setupButtons() {
        int buttonWidth = 240;
        int buttonHeight = 70;
        int buttonSpacing = 20;

        int totalButtonsHeight = (buttonHeight * 2) + buttonSpacing;
        int startY = (SCREEN_HEIGHT / 2) - (totalButtonsHeight / 2);

        // Trên: "Chơi lại"
        playAgainButtonBounds = new Rectangle(
            SCREEN_WIDTH / 2f - buttonWidth / 2f,
            startY + buttonHeight + buttonSpacing,
            buttonWidth, buttonHeight
        );

        // Dưới: "Menu"
        menuButtonBounds = new Rectangle(
            SCREEN_WIDTH / 2f - buttonWidth / 2f,
            startY,
            buttonWidth, buttonHeight
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
        // scale hover (không bắt buộc vẽ tỷ lệ nhưng giữ biến để sau mở rộng)
        if (isPlayAgainHovered || isMenuHovered) {
            buttonHoverScale += (HOVER_SCALE - buttonHoverScale) * 5f * delta;
        } else {
            buttonHoverScale += (1.0f - buttonHoverScale) * 5f * delta;
        }

        handleInput(); // chỉ chuột: PlayAgain → LV1, Menu → MenuScreen

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

        // Title VictoryText.png (pulse như menu title)
        drawVictoryTitle();

        // Buttons
        drawButton(Asset_Winning.playAgainButton, playAgainButtonBounds, isPlayAgainHovered);
        drawButton(Asset_Winning.menuButton,      menuButtonBounds,      isMenuHovered);

        batch.end();
    }

    private void drawVictoryTitle() {
        if (Asset_Winning.titleVictoryTex == null) return;

        float titleWidth = 400f;
        float titleHeight = 100f;
        float pulseScale = UIHelper.calculatePulseScale(animationTime, PULSE_SPEED, 0.95f, 1.05f);
        float adjustedWidth = titleWidth * pulseScale;
        float adjustedHeight = titleHeight * pulseScale;
        float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 8f, 1.5f);
        float titleY = SCREEN_HEIGHT * 0.75f - adjustedHeight / 2f;

        // shadow
        batch.setColor(0f, 0f, 0f, 0.45f);
        batch.draw(Asset_Winning.titleVictoryTex,
            SCREEN_WIDTH / 2f - adjustedWidth / 2f + 4,
            titleY + floatingOffset - 4,
            adjustedWidth, adjustedHeight);

        // main
        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(Asset_Winning.titleVictoryTex,
            SCREEN_WIDTH / 2f - adjustedWidth / 2f,
            titleY + floatingOffset,
            adjustedWidth, adjustedHeight);
    }

    private void drawButton(com.badlogic.gdx.graphics.Texture buttonTexture, Rectangle bounds, boolean isHovered) {
        if (buttonTexture == null) return;
        // đổ bóng nhẹ giống menu (dùng UIHelper nếu bạn đang dùng)
        com.mygdx.chickengame.utils.UIHelper.drawButtonWithShadow(batch, buttonTexture, bounds, isHovered);
        // nổi nhẹ khi hover
        if (isHovered) {
            float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 3f, 4f);
            Rectangle floatingBounds = new Rectangle(bounds.x, bounds.y + floatingOffset, bounds.width, bounds.height);
            batch.setColor(1f, 1f, 1f, 0.9f);
            batch.draw(buttonTexture, floatingBounds.x, floatingBounds.y, floatingBounds.width, floatingBounds.height);
            batch.setColor(1f, 1f, 1f, 1f);
        } else {
            batch.draw(buttonTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private void handleInput() {
        // Chỉ dùng chuột (theo yêu cầu)
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos.y = SCREEN_HEIGHT - touchPos.y;

            // Chơi lại → Level1
            if (playAgainButtonBounds.contains(touchPos.x, touchPos.y)) {
                if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.stop();
                PlayerState.reset(); // nếu muốn bắt đầu sạch
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

        // (Không cần M nữa, nên không xử lý phím ở đây)
    }

    @Override public void show() {}

    @Override
    public void resize(int width, int height) {
        this.SCREEN_WIDTH = width;
        this.SCREEN_HEIGHT = height;
        setupButtons();
    }

    @Override public void pause()  { if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.pause(); }
    @Override public void resume() { if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.play(); }
    @Override public void hide()   { if (Asset_Winning.winningMusic != null) Asset_Winning.winningMusic.stop(); }

    @Override
    public void dispose() {
        if (batch != null)     batch.dispose();
        if (particles != null) particles.dispose();
        // Tùy bạn muốn dispose asset ở đâu:
        // Asset_Winning.dispose();
    }
}
