package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy2;
import com.mygdx.chickengame.entities.Enemy_Bullet;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.entities.PowerUp;
import com.mygdx.chickengame.utils.Assets_LV2;
import com.mygdx.chickengame.utils.Assets_Common;

public class Level2Screen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private Player player;
    private Array<Enemy2> enemies;
    private Array<Bullet> bullets;
    private Array<Enemy_Bullet> enemyBullets;
    private Array<PowerUp> powerUps;

    // Wave management
    private int wave = 1;
    private int enemiesKilled = 0;
    private float spawnTimer = 0;

    // Constants
    private static final int WAVE1_ENEMIES = 10;
    private static final int WAVE2_ENEMIES = 10;
    private static final int WAVE3_ENEMIES = 15;
    private static final float SPAWN_INTERVAL = 1.2f; // Spawn enemy mỗi 1.2 giây

    // Spawn counters
    private int wave1Spawned = 0;
    private int wave2Spawned = 0;
    private int wave3Spawned = 0;

    public Level2Screen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = new Player();
        this.enemies = new Array<>();
        this.bullets = new Array<>();
        this.enemyBullets = new Array<>();
        this.powerUps = new Array<>();

        Assets_LV2.load();
        Assets_LV2.BGMusic.play();

        // Initialize spawn timer
        spawnTimer = SPAWN_INTERVAL;
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        // vẽ
        batch.begin();
        // Sử dụng kích thước màn hình dynamic
        batch.draw(Assets_LV2.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        for (Enemy2 e : enemies)
            e.render(batch);
        for (Bullet b : bullets)
            b.render(batch);
        for (Enemy_Bullet eb : enemyBullets)
            eb.render(batch);
        for (PowerUp p : powerUps)
            p.render(batch);
        batch.end();
    }

    private void update(float delta) {
        // Update spawn timer
        spawnTimer -= delta;

        // Spawn enemies based on wave
        if (wave == 1) {
            spawnWave1();
        } else if (wave == 2) {
            spawnWave2();
        } else if (wave == 3) {
            spawnWave3();
        }

        // Update player
        player.update(delta, bullets);

        // Update enemies and their shooting, remove off-screen ones
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy2 e = enemies.get(i);
            e.update(delta, enemyBullets);

            // Remove enemies that went off-screen
            if (e.isOffScreen()) {
                enemies.removeIndex(i);
            }
        }

        // Update bullets
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);

            if (bullet.isOffScreen()) {
                bullets.removeIndex(i);
            }
        }

        // Update enemy bullets
        for (int i = enemyBullets.size - 1; i >= 0; i--) {
            Enemy_Bullet enemyBullet = enemyBullets.get(i);
            enemyBullet.update(delta);

            if (enemyBullet.isOffScreen()) {
                enemyBullets.removeIndex(i);
            }
        }

        // Update power-ups
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update(delta);

            if (powerUp.isOffScreen()) {
                powerUps.removeIndex(i);
            }
        }

        // Check collisions
        checkCollisions();

        // Check wave progression
        if (wave == 1 && wave1Spawned >= WAVE1_ENEMIES && enemies.size == 0) {
            wave = 2;
            spawnTimer = SPAWN_INTERVAL; // Reset spawn timer cho wave 2
        } else if (wave == 2 && wave2Spawned >= WAVE2_ENEMIES && enemies.size == 0) {
            wave = 3;
            spawnTimer = SPAWN_INTERVAL; // Reset spawn timer cho wave 3
        } else if (wave == 3 && wave3Spawned >= WAVE3_ENEMIES && enemies.size == 0) {
            // Chuyển sang Level Boss
            Assets_LV2.BGMusic.stop();
            game.setScreen(new LevelBoss(game));
        }
    }

    private void checkCollisions() {
        // Player bullet vs Enemy collisions
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size - 1; j >= 0; j--) {
                Enemy2 enemy = enemies.get(j);

                if (bullet.rect.overlaps(enemy.rect)) {
                    enemy.takeDamage((int) bullet.getDamage());
                    bullets.removeIndex(i);

                    if (enemy.isDead()) {
                        Assets_LV2.ChickenHit.play(0.3f);

                        // Drop power-up randomly (15% chance)
                        if (Math.random() < 0.15f) {
                            powerUps.add(new PowerUp(enemy.rect.x, enemy.rect.y));
                        }

                        enemies.removeIndex(j);
                        enemiesKilled++;
                    }
                    break;
                }
            }
        }

        // Player vs Enemy collisions (player dies)
        for (Enemy2 enemy : enemies) {
            if (player.rect.overlaps(enemy.rect)) {
                Assets_Common.PlayerExplosion.play(0.5f);
                Assets_LV2.BGMusic.stop();
                game.setScreen(new GameOverScreen(game));
                return;
            }
        }

        // Player vs Enemy Bullet collisions (player dies)
        for (Enemy_Bullet enemyBullet : enemyBullets) {
            if (player.rect.overlaps(enemyBullet.rect)) {
                Assets_Common.PlayerExplosion.play(0.5f);
                Assets_LV2.BGMusic.stop();
                game.setScreen(new GameOverScreen(game));
                return;
            }
        }

        // Player vs PowerUp collisions
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            if (player.rect.overlaps(powerUp.rect)) {
                player.upgrade();
                powerUps.removeIndex(i);
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        Assets_LV2.BGMusic.pause();
    }

    @Override
    public void resume() {
        Assets_LV2.BGMusic.play();
    }

    @Override
    public void hide() {
        Assets_LV2.BGMusic.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets_LV2.dispose();
    }
}