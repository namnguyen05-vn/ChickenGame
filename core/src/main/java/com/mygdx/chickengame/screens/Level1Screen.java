package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy1;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.entities.PowerUp;
import com.mygdx.chickengame.utils.Assets_LV1;
import com.mygdx.chickengame.utils.Assets_Common;

/**
 * Level 1 (giữ flow của bạn):
 * - Wave1 → Wave2 → chuyển Level2, giữ Player nhưng reset vị trí/cấp.
 * - Không có phím restart trong khi chơi.
 * - PowerUp rơi ngẫu nhiên ~20%.
 */
public class Level1Screen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private Player player;
    private Array<Enemy1> enemies;
    private Array<Bullet> bullets;
    private Array<PowerUp> powerUps;

    private int wave = 1;
    private int enemiesKilled = 0;
    private float spawnTimer = 0;
    private boolean levelComplete = false;

    private static final int WAVE1_ENEMIES = 5;
    private static final int WAVE2_ENEMIES = 10;
    private static final float SPAWN_INTERVAL = 1.5f;

    private int wave1Spawned = 0;
    private int wave2Spawned = 0;

    public Level1Screen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = new Player();
        this.enemies = new Array<>();
        this.bullets = new Array<>();
        this.powerUps = new Array<>();

        Assets_LV1.load();  // asset màn 1
        Assets_LV1.BGMusic.play();

        spawnTimer = SPAWN_INTERVAL;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.begin();
        batch.draw(Assets_LV1.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        for (Enemy1 e : enemies) e.render(batch);
        for (Bullet b : bullets) b.render(batch);
        for (PowerUp p : powerUps) p.render(batch);
        batch.end();
    }

    private void update(float delta) {
        spawnTimer -= delta;

        if (wave == 1) spawnWave1();
        else if (wave == 2) spawnWave2();

        player.update(delta, bullets);

        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy1 e = enemies.get(i);
            e.update(delta);
            if (e.isOffScreen()) enemies.removeIndex(i);
        }

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);
            if (bullet.isOffScreen()) bullets.removeIndex(i);
        }

        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update(delta);
            if (powerUp.isOffScreen()) powerUps.removeIndex(i);
        }

        checkCollisions();

        if (wave == 1 && wave1Spawned >= WAVE1_ENEMIES && enemies.size == 0) {
            wave = 2;
            spawnTimer = SPAWN_INTERVAL;
        } else if (wave == 2 && wave2Spawned >= WAVE2_ENEMIES && enemies.size == 0) {
            Assets_LV1.BGMusic.stop();
            player.resetForNewLevel();
            game.setScreen(new Level2Screen(game, player));
        }
    }

    private void spawnWave1() {
        if (wave1Spawned < WAVE1_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy1());
            wave1Spawned++;
            spawnTimer = SPAWN_INTERVAL;
        }
    }

    private void spawnWave2() {
        if (wave2Spawned < WAVE2_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy1());
            wave2Spawned++;
            spawnTimer = SPAWN_INTERVAL;
        }
    }

    private void checkCollisions() {
        // Bullet vs Enemy
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size - 1; j >= 0; j--) {
                Enemy1 enemy = enemies.get(j);
                if (bullet.rect.overlaps(enemy.rect)) {
                    Assets_LV1.ChickenHit.play(0.3f);
                    if (Math.random() < 0.2f) powerUps.add(new PowerUp(enemy.rect.x, enemy.rect.y));
                    enemies.removeIndex(j);
                    bullets.removeIndex(i);
                    enemiesKilled++;
                    break;
                }
            }
        }

        // Player vs Enemy
        for (Enemy1 enemy : enemies) {
            if (player.rect.overlaps(enemy.rect)) {
                Assets_Common.PlayerExplosion.play(0.5f);
                Assets_LV1.BGMusic.stop();
                game.setScreen(new GameOverScreen(game));
                return;
            }
        }

        // Player vs PowerUp
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            if (player.rect.overlaps(powerUp.rect)) {
                player.upgrade();
                powerUps.removeIndex(i);
            }
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        Assets_LV1.dispose();
    }
}
