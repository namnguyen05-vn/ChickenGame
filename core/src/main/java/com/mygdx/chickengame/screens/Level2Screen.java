package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
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

    // Số lượng power-up đã rơi trong màn
    private int powerUpCount = 0;
    // Giới hạn số lượng power-up tối đa cho màn 2
    private static final int MAX_POWER_UPS = 3;

    // Quản lý wave (đợt kẻ địch)
    private int wave = 1;
    private int enemiesKilled = 0;
    private float spawnTimer = 0;

    // Hằng số
    private static final int WAVE1_ENEMIES = 10;
    private static final int WAVE2_ENEMIES = 10;
    private static final int WAVE3_ENEMIES = 15;
    private static final float SPAWN_INTERVAL = 1.2f; // Sinh kẻ địch mỗi 1.2 giây

    // Spawn counters
    private int wave1Spawned = 0;
    private int wave2Spawned = 0;
    private int wave3Spawned = 0;

    public Level2Screen(ChickenGame game, Player player) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = player; // dùng lại player từ màn trước
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
    // Cập nhật bộ đếm sinh (spawn timer)
        spawnTimer -= delta;

        // Sinh kẻ địch theo wave hiện tại
        if (wave == 1) {
            spawnWave1();
        } else if (wave == 2) {
            spawnWave2();
        } else if (wave == 3) {
            spawnWave3();
        }

    // Cập nhật player
        player.update(delta, bullets);

        // Cập nhật enemy và hành vi bắn của chúng, loại bỏ con ra khỏi màn hình
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy2 e = enemies.get(i);
            e.update(delta, enemyBullets);

            // Remove enemies that went off-screen
            if (e.isOffScreen()) {
                enemies.removeIndex(i);
            }
        }

        // Cập nhật đạn của player
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);

            if (bullet.isOffScreen()) {
                bullets.removeIndex(i);
            }
        }

        // Cập nhật đạn của kẻ địch
        for (int i = enemyBullets.size - 1; i >= 0; i--) {
            Enemy_Bullet enemyBullet = enemyBullets.get(i);
            enemyBullet.update(delta);

            if (enemyBullet.isOffScreen()) {
                enemyBullets.removeIndex(i);
            }
        }

        // Cập nhật power-up
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update(delta);

            if (powerUp.isOffScreen()) {
                powerUps.removeIndex(i);
            }
        }

    // Kiểm tra va chạm
        checkCollisions();

        // Kiểm tra tiến trình wave
        if (wave == 1 && wave1Spawned >= WAVE1_ENEMIES && enemies.size == 0) {
            wave = 2;
            spawnTimer = SPAWN_INTERVAL; // Reset spawn timer cho wave 2
        } else if (wave == 2 && wave2Spawned >= WAVE2_ENEMIES && enemies.size == 0) {
            wave = 3;
            spawnTimer = SPAWN_INTERVAL; // Reset spawn timer cho wave 3
        } else if (wave == 3 && wave3Spawned >= WAVE3_ENEMIES && enemies.size == 0) {
            // Chuyển sang Level Boss, truyền player giữ nguyên mạng nhưng reset vị trí/cấp
            Assets_LV2.BGMusic.stop();
            player.resetForNewLevel();
            game.setScreen(new LevelBoss(game, player));
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


                        // Sinh power-up với xác suất 10% và kiểm tra giới hạn tối đa
                        if (Math.random() < 0.1f && powerUpCount < MAX_POWER_UPS) {
                            powerUps.add(new PowerUp(enemy.rect.x, enemy.rect.y));
                            powerUpCount++; // Tăng biến đếm khi thêm power-up mới
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

    // Sinh ngẫu nhiên một số PowerUp trong khoảng [min,max] tại các vị trí ngẫu nhiên trên màn
    private void spawnRandomPowerUps(int min, int max) {
        int count = MathUtils.random(min, max);
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();
        for (int i = 0; i < count; i++) {
            float x = MathUtils.random(20, Math.max(20, screenW - 52));
            float y = MathUtils.random(screenH / 2, screenH - 80);
            powerUps.add(new PowerUp(x, y));
        }
    }
}
