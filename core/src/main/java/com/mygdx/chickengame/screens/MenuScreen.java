package com.chicken.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen extends BaseScreen {
    private final GlyphLayout layout = new GlyphLayout();

    public MenuScreen(ChickenGame game) { super(game); }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            game.session.resetForNewGame();
            game.setScreen(new Level1Screen(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        if (Assets.bgMenu != null) game.batch.draw(Assets.bgMenu, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        Assets.font.getData().setScale(2f);
        layout.setText(Assets.font, "CHICKEN INVADERS CLONE");
        Assets.font.draw(game.batch, layout, (VIRTUAL_WIDTH - layout.width)/2f, VIRTUAL_HEIGHT - 80);
        Assets.font.getData().setScale(1.2f);
        Assets.font.draw(game.batch, "Press ENTER to Play", 280, 200);
        game.batch.end();
    }
}
