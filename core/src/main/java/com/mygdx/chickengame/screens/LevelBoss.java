
package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Boss_100;
import com.mygdx.chickengame.entities.Boss_50;
import com.mygdx.chickengame.entities.Boss_Bullet;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy1;
import com.mygdx.chickengame.entities.Enemy3;
import com.mygdx.chickengame.entities.Enemy_Bullet;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.entities.PowerUp;
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.Assets_LV3;

public class LevelBoss implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Array<Enemy1> enemies1; // Gà con cho pha 1
    private Array<Enemy3> enemies3; // Gà lớn cho pha 2
    private Array<Bullet> bullets;
    private Array<Boss_Bullet> bossBullets;
    private Array<Enemy_Bullet> enemyBullets;
    private Array<PowerUp> powerUps;

    private Boss_100 boss_100;
    private Boss_50 boss_50;

    // Thông số boss
    private float maxBossHp = 100f;
    private float currentBossHp = 100f;
    private boolean isPhase1 = true; // Pha 1: 100-50% HP, Pha 2: 50-0% HP
    private float spawnTimer = 0f;
    private boolean bossDefeated = false;

    public LevelBoss(ChickenGame game) {
        this(game, new Player());
    }

    // Constructor mới nhận player hiện có (giữ mạng) nhưng reset vị trí/cấp
    public LevelBoss(ChickenGame game, Player player) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.player = player;
        this.player.resetForNewLevel();
        this.enemies1 = new Array<>();
        this.enemies3 = new Array<>();
        this.bullets = new Array<>();
        this.bossBullets = new Array<>();
        this.enemyBullets = new Array<>();
        this.powerUps = new Array<>();

    Assets_Common.load();
    Assets_LV3.load();
    Assets_LV3.BGMusic.play();

    // Bắt đầu với boss pha 1
    this.boss_100 = new Boss_100(this.player);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        // vẽ
        batch.begin();
        // Sử dụng kích thước màn hình dynamic
        batch.draw(Assets_LV3.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);

        // Vẽ boss tùy theo pha
        if (isPhase1 && boss_100 != null) {
            boss_100.render(batch);
        } else if (!isPhase1 && boss_50 != null) {
            boss_50.render(batch);
        }

        // Vẽ enemies
        for (Enemy1 e : enemies1)
            e.render(batch);
        for (Enemy3 e : enemies3)
            e.render(batch);

        // Vẽ bullets
        for (Bullet b : bullets)
            b.render(batch);
        for (Boss_Bullet bb : bossBullets)
            bb.render(batch);
        for (Enemy_Bullet eb : enemyBullets)
            eb.render(batch);
        for (PowerUp p : powerUps)
            p.render(batch);

        batch.end();

    // Vẽ thanh HP của boss
        drawBossHealthBar();
    }

    private void update(float delta) {
    // Cập nhật player
        player.update(delta, bullets);

        // Update boss
        if (isPhase1 && boss_100 != null) {
            boss_100.update(delta);
            currentBossHp = boss_100.getHp();
        } else if (!isPhase1 && boss_50 != null) {
            boss_50.update(delta);
            currentBossHp = boss_50.getHp();
        }
        // 🔥 Kiểm tra nếu Boss_50 chết thì sinh Boss_100 (Boss cuối)
        if (!isPhase1 && boss_50 != null && boss_50.isDead()) {
            System.out.println("Boss 50 died — spawning Boss 100!");
            boss_50 = null;
            boss_100 = new Boss_100(player);  // Tạo lại boss 100
            isPhase1 = true; // Có thể xem như pha 3, hoặc reuse pha 1 để logic render hoạt động
            currentBossHp = boss_100.getHp();
        }

        // Cập nhật các enemy và loại bỏ nếu ra khỏi màn
        for (int i = enemies1.size - 1; i >= 0; i--) {
            Enemy1 e = enemies1.get(i);
            e.update(delta);

            // Remove enemies that went off-screen
            if (e.isOffScreen()) {
                enemies1.removeIndex(i);
            }
        }

        for (int i = enemies3.size - 1; i >= 0; i--) {
            Enemy3 e = enemies3.get(i);
            e.update(delta);

            // Collect bullets from enemies
            enemyBullets.addAll(e.bullets);
            e.bullets.clear();

            // Remove enemies that went off-screen
            if (e.isOffScreen()) {
                enemies3.removeIndex(i);
            }
        }

    // Cập nhật các đạn
        updateBullets(delta);

        // Update power-ups
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update(delta);
            if (powerUp.isOffScreen()) {
                powerUps.removeIndex(i);
            }
        }

        // Kiểm tra chuyển pha (50% HP)
        if (isPhase1 && currentBossHp <= maxBossHp / 2) {
            transitionToPhase2();
        }

        // Check collisions
        checkCollisions();

        // Kiểm tra điều kiện chiến thắng
        if (currentBossHp <= 0 && !bossDefeated) {
            bossDefeated = true;
            Assets_LV3.BGMusic.stop();
            game.setScreen(new VictoryScreen(game));
        }
    }

    private void updateBullets(float delta) {
        // Update player bullets
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);
            if (bullet.isOffScreen()) {
                bullets.removeIndex(i);
            }
        }

        // Update boss bullets
        if (boss_100 != null) {
            bossBullets.addAll(boss_100.bullets);
            boss_100.bullets.clear();
        }
        if (boss_50 != null) {
            bossBullets.addAll(boss_50.bullets);
            boss_50.bullets.clear();
        }

        for (int i = bossBullets.size - 1; i >= 0; i--) {
            Boss_Bullet bossBullet = bossBullets.get(i);
            bossBullet.update(delta);
            if (bossBullet.isOffScreen()) {
                bossBullets.removeIndex(i);
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

        // Spawn enemies from boss
        if (boss_100 != null) {
            enemies1.addAll(boss_100.enemy1);
            boss_100.enemy1.clear();
        }
        if (boss_50 != null) {
            enemies3.addAll(boss_50.enemy3);
            boss_50.enemy3.clear();
        }
    }

    private void transitionToPhase2() {
        isPhase1 = false;
        boss_100 = null; // Remove phase 1 boss
        boss_50 = new Boss_50(player); // Create phase 2 boss
        currentBossHp = boss_50.getHp();

        // Clear existing enemies (optional)
        enemies1.clear();
    }

    private void checkCollisions() {
        // Player bullets vs Boss
        if (isPhase1 && boss_100 != null) {
            for (int i = bullets.size - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                if (bullet.rect.overlaps(boss_100.rect)) {
                    boss_100.takeDamage(bullet.getDamage());
                    bullets.removeIndex(i);
                    Assets_Common.BulletSound.play(0.2f);
                }
            }
        } else if (!isPhase1 && boss_50 != null) {
            for (int i = bullets.size - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                if (bullet.rect.overlaps(boss_50.rect)) {
                    boss_50.takeDamage(bullet.getDamage());
                    bullets.removeIndex(i);
                    Assets_Common.BulletSound.play(0.2f);
                }
            }
        }

        // Player bullets vs Enemies
        checkPlayerBulletsVsEnemies();

        // Player vs Enemies/Bullets (Player dies)
        checkPlayerCollisions();

        // Player vs PowerUps
        for (int i = powerUps.size - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            if (player.rect.overlaps(powerUp.rect)) {
                player.upgrade();
                powerUps.removeIndex(i);
            }
        }
    }

    private void checkPlayerBulletsVsEnemies() {
        // vs Enemy1 (chicks)
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies1.size - 1; j >= 0; j--) {
                Enemy1 enemy = enemies1.get(j);
                if (bullet.rect.overlaps(enemy.rect)) {
                    Assets_LV3.ChickenHit.play(0.3f);

                    if (Math.random() < 0.3f) {
                        powerUps.add(new PowerUp(enemy.rect.x, enemy.rect.y));
                    }

                    enemies1.removeIndex(j);
                    bullets.removeIndex(i);
                    break;
                }
            }
        }

        // vs Enemy3 (big chickens)
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemies3.size - 1; j >= 0; j--) {
                Enemy3 enemy = enemies3.get(j);
                if (bullet.rect.overlaps(enemy.rect)) {
                    enemy.takeDamage((int) bullet.getDamage());
                    bullets.removeIndex(i);

                    if (enemy.isDead()) {
                        Assets_LV3.ChickenHit.play(0.3f);

                        if (Math.random() < 0.25f) {
                            powerUps.add(new PowerUp(enemy.rect.x, enemy.rect.y));
                        }

                        enemies3.removeIndex(j);
                    }
                    break;
                }
            }
        }
    }

    private void checkPlayerCollisions() {
        // vs Enemies
        for (Enemy1 enemy : enemies1) {
            if (player.rect.overlaps(enemy.rect)) {
                playerDies();
                return;
            }
        }

        for (Enemy3 enemy : enemies3) {
            if (player.rect.overlaps(enemy.rect)) {
                playerDies();
                return;
            }
        }

        // vs Boss bullets
        for (Boss_Bullet bossBullet : bossBullets) {
            if (player.rect.overlaps(bossBullet.rect)) {
                playerDies();
                return;
            }
        }

        // vs Enemy bullets
        for (Enemy_Bullet enemyBullet : enemyBullets) {
            if (player.rect.overlaps(enemyBullet.rect)) {
                playerDies();
                return;
            }
        }
    }

    private void playerDies() {
        Assets_Common.PlayerExplosion.play(0.5f);
        Assets_LV3.BGMusic.stop();
        game.setScreen(new GameOverScreen(game));
    }

    private void drawBossHealthBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background của thanh máu (đỏ)
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(50, 550, 300, 20);

        // Thanh máu hiện tại (xanh lá)
        shapeRenderer.setColor(0, 1, 0, 1);
        float healthPercentage = currentBossHp / maxBossHp;
        shapeRenderer.rect(50, 550, 300 * healthPercentage, 20);

        shapeRenderer.end();
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
        Assets_LV3.dispose();
    }

}