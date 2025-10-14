package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Asset_Winning;
import com.mygdx.chickengame.utils.PlayerState;

public class VictoryScreen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;

    // Vị trí và kích thước các nút
    private Rectangle playAgainButtonBounds;
    private Rectangle menuButtonBounds;

    // Vector để lưu vị trí click chuột
    private Vector3 touchPos;

    public VictoryScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.touchPos = new Vector3();

        Asset_Winning.load(); // load asset victory

        // Khởi tạo vị trí các nút
        setupButtons();

        // Phát nhạc thắng
        Asset_Winning.winningMusic.play();
    }

    private void setupButtons() {
        // Sử dụng kích thước màn hình dynamic
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Nút Play Again ở giữa màn hình
        playAgainButtonBounds = new Rectangle(
                screenWidth / 2 - 100, // x (centered)
                screenHeight / 2 - 50, // y
                200, // width
                60 // height
        );

        // Nút Menu bên dưới nút Play Again
        menuButtonBounds = new Rectangle(
                screenWidth / 2 - 100, // x (centered)
                screenHeight / 2 - 130, // y
                200, // width
                60 // height
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.1f, 1); // Màu xanh nhạt cho victory
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        batch.begin();

        // Vẽ background
        batch.draw(Asset_Winning.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Vẽ text "VICTORY" ở giữa trên (có thể thay bằng texture nếu có)
        // batch.draw(victoryTitleTexture, x, y, width, height);

        // Vẽ nút Play Again
        batch.draw(Asset_Winning.playAgainButton,
                playAgainButtonBounds.x, playAgainButtonBounds.y,
                playAgainButtonBounds.width, playAgainButtonBounds.height);

        // Vẽ nút Back to Menu
        batch.draw(Asset_Winning.menuButton,
                menuButtonBounds.x, menuButtonBounds.y,
                menuButtonBounds.width, menuButtonBounds.height);

        batch.end();
    }

    private void handleInput() {
        // Xử lý phím ESC để về menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Asset_Winning.winningMusic.stop();
            game.setScreen(new MenuScreen(game));
        }

        // Xử lý phím Enter để chơi lại
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Asset_Winning.winningMusic.stop();
            // Reset player state khi chơi lại
            PlayerState.reset();
            game.setScreen(new Level1Screen(game));
        }

        // Xử lý click chuột
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Chuyển đổi tọa độ từ screen space sang world space
            touchPos.y = Gdx.graphics.getHeight() - touchPos.y;

            // Kiểm tra click vào nút Play Again
            if (playAgainButtonBounds.contains(touchPos.x, touchPos.y)) {
                Asset_Winning.winningMusic.stop();
                // Reset player state khi chơi lại
                PlayerState.reset();
                game.setScreen(new Level1Screen(game));
            }

            // Kiểm tra click vào nút Menu
            if (menuButtonBounds.contains(touchPos.x, touchPos.y)) {
                Asset_Winning.winningMusic.stop();
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        // Cập nhật lại vị trí các nút khi thay đổi kích thước màn hình
        setupButtons();
    }

    @Override
    public void pause() {
        Asset_Winning.winningMusic.pause();
    }

    @Override
    public void resume() {
        Asset_Winning.winningMusic.play();
    }

    @Override
    public void hide() {
        Asset_Winning.winningMusic.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Asset_Winning.dispose();
    }
}