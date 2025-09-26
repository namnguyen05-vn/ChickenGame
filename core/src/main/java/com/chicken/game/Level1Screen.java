package com.chicken.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import java.util.ArrayList;
import java.util.List;

public class Level1Screen extends BaseScreen {
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();

    public Level1Screen(ChickenGame game) {
        super(game);
        player = new Player();
        for (int i = 0; i < 8; i++) enemies.add(new Enemy(i * 80 + 60, 400));
        game.session.level = 1;
    }

    private void update(float dt) {
        player.update(dt);
        for (Enemy e : enemies) e.update(dt);

        boolean allDead = true;
        for (Enemy e : enemies) if (!e.isDead()) { allDead = false; break; }
        if (allDead) game.setScreen(new Level2Screen(game));
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new MenuScreen(game));
        update(delta);

        Gdx.gl.glClearColor(0f, 0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        player.draw(game.batch);
        for (Enemy e : enemies) e.draw(game.batch);
        Assets.font.draw(game.batch, "Level 1 | Score: " + game.session.score +
                "  Lives: " + game.session.lives, 10, 470);
        game.batch.end();
    }
}
