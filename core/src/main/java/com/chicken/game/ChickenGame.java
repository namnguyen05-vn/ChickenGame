package com.chicken.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChickenGame extends Game {
    public SpriteBatch batch;
    public GameSession session;

    @Override
    public void create() {
        batch = new SpriteBatch();
        session = new GameSession();
        Assets.load();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        Assets.dispose();
        batch.dispose();
    }
}
