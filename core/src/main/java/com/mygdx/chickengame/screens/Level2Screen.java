package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.*;
import com.mygdx.chickengame.utils.*;

public class Level2Screen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private Player player;
    private Array<Enemy2> enemies;
    private Array<Bullet> bullets;
    private Array<Enemy_Bullet> enemyBullets;
    private Array<PowerUp> powerUps;

    private int wave = 1;
    private float spawnTimer = 0;
    private static final int WAVE1_ENEMIES = 10;
    private static final int WAVE2_ENEMIES = 10;
    private static final int WAVE3_ENEMIES = 15;
    private static final float SPAWN_INTERVAL = 1.2f;
    private int wave1Spawned = 0, wave2Spawned = 0, wave3Spawned = 0;

    public Level2Screen(ChickenGame game, Player player) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = player;
        this.enemies = new Array<>();
        this.bullets = new Array<>();
        this.enemyBullets = new Array<>();
        this.powerUps = new Array<>();

        Assets_LV2.load();
        Assets_LV2.BGMusic.play();

        GameSession.setCurrentLevel(2); // 🔹 Ghi nhớ màn hiện tại

        spawnTimer = SPAWN_INTERVAL;
    }

    @Override
    public void render(float delta) {
        // ❌ Không cho ấn R trong khi chơi

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.begin();
        batch.draw(Assets_LV2.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        for (Enemy2 e : enemies) e.render(batch);
        for (Bullet b : bullets) b.render(batch);
        for (Enemy_Bullet eb : enemyBullets) eb.render(batch);
        for (PowerUp p : powerUps) p.render(batch);
        batch.end();
    }

    private void update(float delta) {
        spawnTimer -= delta;

        if (wave == 1) spawnWave1();
        else if (wave == 2) spawnWave2();
        else if (wave == 3) spawnWave3();

        player.update(delta, bullets);

        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy2 e = enemies.get(i);
            e.update(delta, enemyBullets);
            if (e.isOffScreen()) enemies.removeIndex(i);
        }

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);
            if (bullet.isOffScreen()) bullets.removeIndex(i);
        }

        for (int i = enemyBullets.size - 1; i >= 0; i--) {
            Enemy_Bullet eb = enemyBullets.get(i);
            eb.update(delta);
            if (eb.isOffScreen()) enemyBullets.removeIndex(i);
        }

        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            p.update(delta);
            if (p.isOffScreen()) powerUps.removeIndex(i);
        }

        checkCollisions();

        if (wave == 1 && wave1Spawned >= WAVE1_ENEMIES && enemies.size == 0) {
            wave = 2;
            spawnTimer = SPAWN_INTERVAL;
        } else if (wave == 2 && wave2Spawned >= WAVE2_ENEMIES && enemies.size == 0) {
            wave = 3;
            spawnTimer = SPAWN_INTERVAL;
        } else if (wave == 3 && wave3Spawned >= WAVE3_ENEMIES && enemies.size == 0) {
            Assets_LV2.BGMusic.stop();
            player.resetForNewLevel();
            game.setScreen(new LevelBoss(game, player));
        }
    }

    private void spawnWave1() {
        if (wave1Spawned < WAVE1_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy2());
            wave1Spawned++;
            spawnTimer = SPAWN_INTERVAL;
        }
    }

    private void spawnWave2() {
        if (wave2Spawned < WAVE2_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy2());
            wave2Spawned++;
            spawnTimer = SPAWN_INTERVAL;
        }
    }

    private void spawnWave3() {
        if (wave3Spawned < WAVE3_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy2());
            wave3Spawned++;
            spawnTimer = SPAWN_INTERVAL;
        }
    }

    private void checkCollisions() {
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            for (int j = enemies.size - 1; j >= 0; j--) {
                Enemy2 e = enemies.get(j);
                if (b.rect.overlaps(e.rect)) {
                    e.takeDamage((int) b.getDamage());
                    bullets.removeIndex(i);
                    if (e.isDead()) {
                        Assets_LV2.ChickenHit.play(0.3f);
                        if (Math.random() < 0.15f) powerUps.add(new PowerUp(e.rect.x, e.rect.y));
                        enemies.removeIndex(j);
                    }
                    break;
                }
            }
        }

        for (Enemy2 e : enemies) {
            if (player.rect.overlaps(e.rect)) {
                Assets_Common.PlayerExplosion.play(0.5f);
                Assets_LV2.BGMusic.stop();
                game.setScreen(new GameOverScreen(game));
                return;
            }
        }

        for (Enemy_Bullet eb : enemyBullets) {
            if (player.rect.overlaps(eb.rect)) {
                Assets_Common.PlayerExplosion.play(0.5f);
                Assets_LV2.BGMusic.stop();
                game.setScreen(new GameOverScreen(game));
                return;
            }
        }

        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            if (player.rect.overlaps(p.rect)) {
                player.upgrade();
                powerUps.removeIndex(i);
            }
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() { Assets_LV2.BGMusic.pause(); }
    @Override public void resume() { Assets_LV2.BGMusic.play(); }
    @Override public void hide() { Assets_LV2.BGMusic.stop(); }
    @Override public void dispose() { batch.dispose(); Assets_LV2.dispose(); }
}
