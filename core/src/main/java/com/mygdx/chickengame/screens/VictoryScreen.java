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
// import com.mygdx.chickengame.utils.PlayerState; // (Không dùng restart ở Victory theo yêu cầu)

public class VictoryScreen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;

    // Vị trí và kích thước các nút (GIỮ cấu trúc, NHƯNG KHÔNG dùng)
    private Rectangle playAgainButtonBounds;
    private Rectangle menuButtonBounds;

    private Vector3 touchPos;

    public VictoryScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.touchPos = new Vector3();

        Asset_Winning.load(); // load asset victory

        setupButtons();

        // Phát nhạc thắng
        Asset_Winning.winningMusic.play();
    }

    private void setupButtons() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        playAgainButtonBounds = new Rectangle(
            screenWidth / 2 - 100,
            screenHeight / 2 - 50,
            200, 60
        );

        menuButtonBounds = new Rectangle(
            screenWidth / 2 - 100,
            screenHeight / 2 - 130,
            200, 60
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        batch.begin();
        batch.draw(Asset_Winning.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // VẼ nút vẫn được, nhưng sẽ không có tác dụng nhấn (khóa input)
        // (Giữ nguyên cấu trúc UI cho đẹp)
        batch.draw(Asset_Winning.playAgainButton,
            playAgainButtonBounds.x, playAgainButtonBounds.y,
            playAgainButtonBounds.width, playAgainButtonBounds.height);

        batch.draw(Asset_Winning.menuButton,
            menuButtonBounds.x, menuButtonBounds.y,
            menuButtonBounds.width, menuButtonBounds.height);
        batch.end();
    }

    private void handleInput() {
        // Chỉ cho phép phím M để về Menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Asset_Winning.winningMusic.stop();
            game.setScreen(new MenuScreen(game));
        }

        // KHÓA các cách restart khác theo yêu cầu:
        // if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { ... } // (TẮT – theo yêu cầu)
        // if (Gdx.input.justTouched()) { ... } // (TẮT – theo yêu cầu)
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { setupButtons(); }
    @Override public void pause()  { Asset_Winning.winningMusic.pause(); }
    @Override public void resume() { Asset_Winning.winningMusic.play(); }
    @Override public void hide()   { Asset_Winning.winningMusic.stop(); }
    @Override public void dispose() {
        if (batch != null) batch.dispose();
        // Asset_Winning.dispose(); // Tuỳ bạn quản lý vòng đời tài nguyên
    }
}
