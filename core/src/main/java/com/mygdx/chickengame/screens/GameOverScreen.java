package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Assets_Common;

public class GameOverScreen extends BaseScreen {

    public GameOverScreen(ChickenGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        clearScreen(0f, 0f, 0f);

        game.batch.begin();
        Assets_Common.font.draw(game.batch, "GAME OVER", 100, 300);
        Assets_Common.font.draw(game.batch, "R = Retry Level 1", 100, 260);
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
