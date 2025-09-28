package com.chicken.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class BossScreen extends BaseScreen {
    private final Player player;
    private final Boss boss;

    public BossScreen(ChickenGame game) {
        super(game);
        player = new Player();
        boss = new Boss(360, 420);
        game.session.level = 3;
    }

    private void update(float dt) {
        player.update(dt);
        boss.update(dt);

        if (boss.isDead()) game.setScreen(new VictoryScreen(game));
        if (game.session.lives <= 0) game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        player.draw(game.batch);
        boss.draw(game.batch);
        Assets.font.draw(game.batch, "Boss Fight | Score: " + game.session.score +
                "  Lives: " + game.session.lives, 10, 470);
        game.batch.end();
    }
}
