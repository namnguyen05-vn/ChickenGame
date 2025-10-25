package com.mygdx.chickengame.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Assets_Common;

public abstract class BaseScreen extends ScreenAdapter {
    protected final ChickenGame game;
    protected final BitmapFont font;

    public BaseScreen(ChickenGame game) {
        this.game = game;
        this.font = Assets_Common.font; // dùng font chung đã load trong Assets_Common.load()
    }

    protected void clearScreen(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        // KHÔNG dispose font ở đây vì font thuộc Assets_Common và sẽ được dispose ở đó
    }
}
