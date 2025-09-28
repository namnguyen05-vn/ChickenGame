package com.chicken.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class BaseScreen extends ScreenAdapter {
    protected final ChickenGame game;
    protected OrthographicCamera camera;
    protected FitViewport viewport;
    protected Stage stage;

    public static final float VIRTUAL_WIDTH = 800;
    public static final float VIRTUAL_HEIGHT = 480;

    public BaseScreen(ChickenGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        stage = new Stage(viewport, game.batch);
    }

    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void dispose() { stage.dispose(); }
}
