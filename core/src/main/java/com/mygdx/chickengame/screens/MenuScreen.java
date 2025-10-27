package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Assets_Common;

public class MenuScreen extends BaseScreen {
    public MenuScreen(ChickenGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        clearScreen(0f, 0f, 0f);

        game.batch.begin();
        Assets_Common.font.draw(game.batch, "MENU", 100, 300);
        Assets_Common.font.draw(game.batch, "ENTER = Start Level 1", 100, 260);
        Assets_Common.font.draw(game.batch, "ESC   = Quit (not implemented)", 100, 230);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.session.level = 1;
            game.goLevel1();
        }
    }
}
