
package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy1;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.entities.PowerUp;
import com.mygdx.chickengame.utils.Assets_LV1;
import com.mygdx.chickengame.utils.Assets_Common;

public class Level1Screen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private Player player;
    private Array<Enemy1> enemies;
    private Array<Bullet> bullets;
    private Array<PowerUp> powerUps;

    // Số lượng power-up đã rơi trong màn
    private int powerUpCount = 0;
    // Giới hạn số lượng power-up tối đa cho màn 1
    private static final int MAX_POWER_UPS = 2;

    // Quản lý wave (đợt kẻ địch)
    private int wave = 1;
    private int enemiesKilled = 0;
    private float spawnTimer = 0;
    private boolean levelComplete = false;

    // Hằng số
    private static final int WAVE1_ENEMIES = 5;
    private static final int WAVE2_ENEMIES = 10;

    public Level1Screen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = new Player();
        this.enemies = new Array<>();
        this.bullets = new Array<>();
        this.powerUps = new Array<>();

        Assets_LV1.load(); // load tài nguyên riêng cho màn 1
        Assets_LV1.BGMusic.play();

        // Initialize spawn timer
        spawnTimer = SPAWN_INTERVAL;
    }

    private int wave1Spawned = 0;
    private int wave2Spawned = 0;
    private static final float SPAWN_INTERVAL = 1.5f; // Sinh kẻ địch mỗi 1.5 giây

    private void spawnWave1() {
        // Spawn enemies theo thời gian thay vì một lúc
        if (wave1Spawned < WAVE1_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy1());
            wave1Spawned++;
            spawnTimer = SPAWN_INTERVAL;
        }
    }

    private void spawnWave2() {
        // Spawn enemies theo thời gian thay vì một lúc
        if (wave2Spawned < WAVE2_ENEMIES && spawnTimer <= 0) {
            enemies.add(new Enemy1());
            wave2Spawned++;
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
        batch.draw(Assets_LV1.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        for (Enemy1 e : enemies)
            e.render(batch);
        for (Bullet b : bullets)
            b.render(batch);
        for (PowerUp p : powerUps)
            p.render(batch);


        batch.end();
    }

    private void update(float delta) {
        // Update spawn timer
        spawnTimer -= delta;

        // Sinh kẻ địch dựa trên wave hiện tại
        if (wave == 1) {
            spawnWave1();
        } else if (wave == 2) {
            spawnWave2();
        }

    // Cập nhật player
        player.update(delta, bullets);

        // Cập nhật các enemy và loại bỏ những con đi ra ngoài màn hình
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy1 e = enemies.get(i);
            e.update(delta);

            // Remove enemies that went off-screen (di chuyển ra ngoài phía dưới)
            if (e.isOffScreen()) {
                enemies.removeIndex(i);
            }
        }

        // Cập nhật đạn
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);

            // Remove bullets that are off screen
            if (bullet.isOffScreen()) {
                bullets.removeIndex(i);
            }
        }

        // Cập nhật power-up
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update(delta);

            // Remove power-ups that are off screen
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
            // Chuyển sang Level 2, truyền player giữ nguyên mạng nhưng reset vị trí/cấp
            Assets_LV1.BGMusic.stop();
            player.resetForNewLevel();
            game.setScreen(new Level2Screen(game, player));
        }
    }

    private void checkCollisions() {
        // Bullet vs Enemy collisions
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies.size - 1; j >= 0; j--) {
                Enemy1 enemy = enemies.get(j);

                if (bullet.rect.overlaps(enemy.rect)) {
                    // Enemy bị hit - gà con chết luôn với 1 hit
                    Assets_LV1.ChickenHit.play(0.3f);

                    // Sinh power-up với xác suất 10% và kiểm tra giới hạn tối đa
                    if (Math.random() < 0.1f && powerUpCount < MAX_POWER_UPS) {
                        powerUps.add(new PowerUp(enemy.rect.x, enemy.rect.y));
                        powerUpCount++; // Tăng biến đếm khi thêm power-up mới
                    }

                    enemies.removeIndex(j);
                    bullets.removeIndex(i);
                    enemiesKilled++;
                    break;
                }
            }
        }

        // Player vs Enemy collisions (trừ mạng)
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy1 enemy = enemies.get(i);
            if (player.rect.overlaps(enemy.rect)) {
                Assets_Common.PlayerExplosion.play(0.5f);
                player.loseLife();
                enemies.removeIndex(i);
                if (!player.isAlive()) {
                    Assets_LV1.BGMusic.stop();
                    game.setScreen(new GameOverScreen(game));
                    return;
                }
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
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets_LV1.dispose(); // giải phóng asset màn 1
    }
}