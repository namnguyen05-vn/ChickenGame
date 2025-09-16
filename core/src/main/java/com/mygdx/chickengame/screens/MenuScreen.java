package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Assets_LV1;

public class MenuScreen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;

    public MenuScreen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        Assets_LV1.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // sau này có thể vẽ logo, nút start
        batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new Level1Screen(game));
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); }
}
