package com.chicken.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class VictoryScreen extends BaseScreen {
    public VictoryScreen(ChickenGame game) { super(game); }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) game.setScreen(new MenuScreen(game));

        Gdx.gl.glClearColor(0,0.2f,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        Assets.font.getData().setScale(2f);
        Assets.font.draw(game.batch, "YOU WIN!", 330, 280);
        Assets.font.getData().setScale(1f);
        Assets.font.draw(game.batch, "Press ENTER to return Menu", 280, 200);
        game.batch.end();
    }
}
