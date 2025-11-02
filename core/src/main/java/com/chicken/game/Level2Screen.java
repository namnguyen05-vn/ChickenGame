package com.chicken.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import java.util.ArrayList;
import java.util.List;

public class Level2Screen extends BaseScreen {
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();

    public Level2Screen(ChickenGame game) {
        super(game);
        player = new Player();
        for (int i = 0; i < 12; i++) enemies.add(new Enemy(i * 60 + 40, 420));
        game.session.level = 2;
    }

    private void update(float dt) {
        player.update(dt);
        for (Enemy e : enemies) e.update(dt);

        boolean allDead = true;
        for (Enemy e : enemies) if (!e.isDead()) { allDead = false; break; }
        if (allDead) game.setScreen(new BossScreen(game));
        if (game.session.lives <= 0) game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.05f, 0f, 0.05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        player.draw(game.batch);
        for (Enemy e : enemies) e.draw(game.batch);
        Assets.font.draw(game.batch, "Level 2 | Score: " + game.session.score +
                "  Lives: " + game.session.lives, 10, 470);
        game.batch.end();
    }
}
