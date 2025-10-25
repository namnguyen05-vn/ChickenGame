package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Assets_Common;

public class VictoryScreen extends BaseScreen {

    public VictoryScreen(ChickenGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        clearScreen(0f, 0f, 0.1f);

        game.batch.begin();
        Assets_Common.font.draw(game.batch, "YOU WIN!", 100, 300);
        Assets_Common.font.draw(game.batch, "R = Replay Level 1", 100, 260);
        Assets_Common.font.draw(game.batch, "M = Menu", 100, 230);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.goLevel1();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.goMenu();
        }
    }
}
