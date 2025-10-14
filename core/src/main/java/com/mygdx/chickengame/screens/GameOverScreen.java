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
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.Assets_Menu;
import com.mygdx.chickengame.utils.UIHelper;
import com.mygdx.chickengame.utils.ParticleEffect;
import com.mygdx.chickengame.utils.PlayerState;

public class GameOverScreen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;

    // Kích thước màn hình - sử dụng dynamic thay vì hardcode
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    // Vị trí và kích thước các nút
    private Rectangle startButtonBounds;
    private Rectangle escapeButtonBounds;

    // Vector để lưu vị trí click chuột
    private Vector3 touchPos;

    // Animation và effects
    private float animationTime = 0f;
    private boolean isStartButtonHovered = false;
    private boolean isEscapeButtonHovered = false;
    private float buttonHoverScale = 1.0f;
    private ParticleEffect particles;
    private static final float PULSE_SPEED = 2.0f;
    private static final float HOVER_SCALE = 1.1f;

    public GameOverScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.touchPos = new Vector3();

        // Lấy kích thước màn hình hiện tại
        this.SCREEN_WIDTH = Gdx.graphics.getWidth();
        this.SCREEN_HEIGHT = Gdx.graphics.getHeight();

        Assets_Common.load(); // load asset chung
        Assets_Menu.load(); // load asset menu

        // Khởi tạo vị trí các nút
        setupButtons();

        // Khởi tạo particle effect
        particles = new ParticleEffect();

        // Phát nhạc nền menu
        Assets_Menu.BGMusic.play();
    }

    private void setupButtons() {
        // Perfect center layout với spacing cân đối
        int buttonWidth = 240;
        int buttonHeight = 70;
        int buttonSpacing = 20; // Giảm spacing để buttons gần nhau hơn

        // Tính toán vị trí để cả 2 buttons nằm giữa màn hình
        int totalButtonsHeight = (buttonHeight * 2) + buttonSpacing;
        int startY = (SCREEN_HEIGHT / 2) - (totalButtonsHeight / 2);

        // Nút Start (trên)
        startButtonBounds = new Rectangle(
                SCREEN_WIDTH / 2 - buttonWidth / 2, // x (perfect center)
                startY + buttonHeight + buttonSpacing, // y (top button)
                buttonWidth, // width
                buttonHeight // height
        );

        // Nút Escape (dưới)
        escapeButtonBounds = new Rectangle(
                SCREEN_WIDTH / 2 - buttonWidth / 2, // x (perfect center)
                startY, // y (bottom button)
                buttonWidth, // width
                buttonHeight // height
        );
    }

    @Override
    public void render(float delta) {
        // Update animations
        updateAnimations(delta);

        // Update particles
        particles.update(delta);

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1); // Darker blue background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        batch.begin();

        // Vẽ background với alpha effect
        batch.setColor(1f, 1f, 1f, 0.9f);
        batch.draw(Assets_Menu.backgroundTex, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.setColor(1f, 1f, 1f, 1f); // Reset color

        // Vẽ particles (dùng bullet texture làm particle)
        if (Assets_Common.bulletTex != null) {
            particles.render(batch, Assets_Common.bulletTex);
        }

        // Vẽ title game với pulse animation
        drawAnimatedTitle();

        // Vẽ các nút với hover effects
        drawButton(Assets_Menu.Start, startButtonBounds, isStartButtonHovered);
        drawButton(Assets_Menu.Escape, escapeButtonBounds, isEscapeButtonHovered);

        batch.end();
    }

    private void updateAnimations(float delta) {
        animationTime += delta;

        // Update hover detection
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchPos.y = SCREEN_HEIGHT - touchPos.y;

        isStartButtonHovered = startButtonBounds.contains(touchPos.x, touchPos.y);
        isEscapeButtonHovered = escapeButtonBounds.contains(touchPos.x, touchPos.y);

        // Smooth hover scale animation
        if (isStartButtonHovered || isEscapeButtonHovered) {
            buttonHoverScale += (HOVER_SCALE - buttonHoverScale) * 5f * delta;
        } else {
            buttonHoverScale += (1.0f - buttonHoverScale) * 5f * delta;
        }
    }

    private void drawAnimatedTitle() {
        float titleWidth = 400; // Giảm size title một chút
        float titleHeight = 100;

        // Calculate pulsing effect using UIHelper
        float pulseScale = UIHelper.calculatePulseScale(animationTime, PULSE_SPEED, 0.95f, 1.05f);
        float adjustedWidth = titleWidth * pulseScale;
        float adjustedHeight = titleHeight * pulseScale;

        // Add floating effect
        float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 8f, 1.5f);

        // Vị trí title cân đối với buttons - ở 1/3 trên của màn hình
        float titleY = SCREEN_HEIGHT * 0.75f - adjustedHeight / 2;

        // Vẽ shadow cho title
        batch.setColor(0.2f, 0.2f, 0.4f, 0.4f);
        batch.draw(Assets_Menu.Title_Game,
                SCREEN_WIDTH / 2 - adjustedWidth / 2 + 4,
                titleY + floatingOffset - 4,
                adjustedWidth, adjustedHeight);

        // Vẽ title chính với color tint
        batch.setColor(1f, 1f, 0.9f + pulseScale * 0.1f, 1f);
        batch.draw(Assets_Menu.Title_Game,
                SCREEN_WIDTH / 2 - adjustedWidth / 2,
                titleY + floatingOffset,
                adjustedWidth, adjustedHeight);

        batch.setColor(1f, 1f, 1f, 1f); // Reset color
    }

    private void drawButton(com.badlogic.gdx.graphics.Texture buttonTexture, Rectangle bounds, boolean isHovered) {
        // Sử dụng UIHelper để vẽ button với shadow
        UIHelper.drawButtonWithShadow(batch, buttonTexture, bounds, isHovered);

        // Thêm floating effect cho button khi hover
        if (isHovered) {
            float floatingOffset = UIHelper.calculateFloatingOffset(animationTime, 3f, 4f);
            Rectangle floatingBounds = new Rectangle(bounds.x, bounds.y + floatingOffset, bounds.width, bounds.height);

            // Vẽ lại button với floating effect
            batch.setColor(1f, 1f, 1f, 0.9f);
            batch.draw(buttonTexture, floatingBounds.x, floatingBounds.y, floatingBounds.width, floatingBounds.height);
        }

        batch.setColor(1f, 1f, 1f, 1f); // Reset color
    }

    private void handleInput() {
        // Xử lý phím ESC
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            playButtonSound();
            Gdx.app.exit();
        }

        // Xử lý phím Enter để start game
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            playButtonSound();
            Assets_Menu.BGMusic.stop();
            // Reset player state khi chơi lại
            PlayerState.reset();
            game.setScreen(new Level1Screen(game));
        }

        // Xử lý click chuột
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Chuyển đổi tọa độ từ screen space sang world space
            touchPos.y = SCREEN_HEIGHT - touchPos.y;

            // Kiểm tra click vào nút Start
            if (startButtonBounds.contains(touchPos.x, touchPos.y)) {
                playButtonSound();
                Assets_Menu.BGMusic.stop();
                // Reset player state khi chơi lại
                PlayerState.reset();
                game.setScreen(new Level1Screen(game));
            }

            // Kiểm tra click vào nút Escape
            if (escapeButtonBounds.contains(touchPos.x, touchPos.y)) {
                playButtonSound();
                Gdx.app.exit();
            }
        }
    }

    private void playButtonSound() {
        // Play a click sound effect (if available)
        if (Assets_Common.BulletSound != null) {
            Assets_Common.BulletSound.play(0.3f);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        // Cập nhật kích thước màn hình khi resize
        this.SCREEN_WIDTH = width;
        this.SCREEN_HEIGHT = height;

        // Cập nhật lại vị trí buttons
        setupButtons();
    }

    @Override
    public void pause() {
        Assets_Menu.BGMusic.pause();
    }

    @Override
    public void resume() {
        Assets_Menu.BGMusic.play();
    }

    @Override
    public void hide() {
        Assets_Menu.BGMusic.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        particles.dispose(); // giải phóng particle effects
        Assets_Common.dispose(); // giải phóng asset chung khi thoát menu
        Assets_Menu.dispose(); // giải phóng asset menu
    }
}
