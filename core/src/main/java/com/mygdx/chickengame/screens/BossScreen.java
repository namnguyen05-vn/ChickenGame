package com.mygdx.chickengame.screens;

import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.entities.Boss;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.utils.Assets_Common;

public class BossScreen extends BaseScreen {
    private final Player player;
    private final Boss boss;
    private final Array<Bullet> bullets = new Array<>();

    public BossScreen(ChickenGame game) {
        super(game);
        player = new Player();
        player.resetForNewLevel();
        boss = new Boss();
        game.session.level = 3;
    }

    private void update(float dt) {
        player.update(dt, bullets);
        for (Bullet b : bullets) b.update(dt);
        boss.update(dt);

        // TODO: collision bullet vs boss -> boss.hit()
        // TODO: collision boss/enemy bullet vs player -> player.loseLife()

        if (boss.isDead()) {
            game.goVictory();
        }

        if (!player.isAlive()) {
            game.goGameOver();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen(0.1f, 0.05f, 0.05f);

        game.batch.begin();
        boss.render(game.batch);
        player.render(game.batch);
        for (Bullet b : bullets) b.render(game.batch);

        Assets_Common.font.draw(game.batch, "BOSS", 20, 460);
        Assets_Common.font.draw(game.batch, "Lives: " + player.getLives(), 20, 440);
        game.batch.end();
    }
}
