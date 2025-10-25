package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy;
import com.mygdx.chickengame.utils.Assets_Common;

public class Level2Screen extends BaseScreen {
    private final Player player;
    private final Array<Bullet> bullets = new Array<>();
    private final Array<Enemy> enemies = new Array<>();

    public Level2Screen(ChickenGame game) {
        super(game);
        player = new Player();
        player.resetForNewLevel();

        // spawn nhiều enemy hơn
        for (int i = 0; i < 10; i++) {
            enemies.add(new Enemy(60 + i * 60, 450));
        }

        game.session.level = 2;
    }

    private void update(float dt) {
        player.update(dt, bullets);

        for (Bullet b : bullets) {
            b.update(dt);
        }

        for (Enemy e : enemies) {
            e.update(dt);
        }

        // clear bullet offscreen
        for (int i = bullets.size - 1; i >= 0; i--) {
            if (bullets.get(i).isOffScreen(Gdx.graphics.getHeight())) {
                bullets.removeIndex(i);
            }
        }

        // check win -> boss (sau khi clear hết enemy)
        boolean allDead = true;
        for (Enemy e : enemies) {
            if (!e.isDead()) {
                allDead = false;
                break;
            }
        }
        if (allDead) {
            // nếu bạn muốn qua màn cảnh báo boss trước:
            // game.goLevelBoss();
            // còn nếu muốn nhảy thẳng vào boss:
            game.goBoss();
        }

        // player chết -> game over
        if (!player.isAlive()) {
            game.goGameOver();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen(0.05f, 0.1f, 0.05f);

        game.batch.begin();

        // vẽ player
        player.render(game.batch);

        // vẽ enemy
        for (Enemy e : enemies) {
            e.render(game.batch);
        }

        // vẽ bullet
        for (Bullet b : bullets) {
            b.render(game.batch);
        }

        // HUD
        Assets_Common.font.draw(game.batch, "LEVEL 2", 20, 460);
        Assets_Common.font.draw(game.batch, "Lives: " + player.getLives(), 20, 440);

        game.batch.end();
    }
}
