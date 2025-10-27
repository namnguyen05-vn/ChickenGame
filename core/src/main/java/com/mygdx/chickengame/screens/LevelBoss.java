package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.*;
import com.mygdx.chickengame.utils.*;

public class LevelBoss implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Array<Enemy1> enemies1;
    private Array<Enemy3> enemies3;
    private Array<Bullet> bullets;
    private Array<Boss_Bullet> bossBullets;
    private Array<Enemy_Bullet> enemyBullets;
    private Array<PowerUp> powerUps;

    private Boss_100 boss_100;
    private Boss_50 boss_50;

    private float maxBossHp = 100f;
    private float currentBossHp = 100f;
    private boolean isPhase1 = true;
    private boolean bossDefeated = false;

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

        this.boss_100 = new Boss_100(this.player);

        GameSession.setCurrentLevel(3); // 🔹 Ghi nhớ màn Boss
    }

    @Override
    public void render(float delta) {
        // ❌ Không cho ấn R trong khi chơi
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.begin();
        batch.draw(Assets_LV3.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        if (isPhase1 && boss_100 != null) boss_100.render(batch);
        if (!isPhase1 && boss_50 != null) boss_50.render(batch);
        for (Enemy1 e : enemies1) e.render(batch);
        for (Enemy3 e : enemies3) e.render(batch);
        for (Bullet b : bullets) b.render(batch);
        for (Boss_Bullet bb : bossBullets) bb.render(batch);
        for (Enemy_Bullet eb : enemyBullets) eb.render(batch);
        for (PowerUp p : powerUps) p.render(batch);
        batch.end();

        drawBossHealthBar();
    }

    private void update(float delta) {
        player.update(delta, bullets);

        if (isPhase1 && boss_100 != null) {
            boss_100.update(delta);
            currentBossHp = boss_100.getHp();
        } else if (!isPhase1 && boss_50 != null) {
            boss_50.update(delta);
            currentBossHp = boss_50.getHp();
        }

        updateBullets(delta);
        checkCollisions();

        if (isPhase1 && currentBossHp <= maxBossHp / 2) transitionToPhase2();

        if (currentBossHp <= 0 && !bossDefeated) {
            bossDefeated = true;
            Assets_LV3.BGMusic.stop();
            game.setScreen(new VictoryScreen(game));
        }
    }

    private void updateBullets(float delta) {
        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update(delta);
            if (b.isOffScreen()) bullets.removeIndex(i);
        }

        if (boss_100 != null) { bossBullets.addAll(boss_100.bullets); boss_100.bullets.clear(); }
        if (boss_50 != null) { bossBullets.addAll(boss_50.bullets); boss_50.bullets.clear(); }

        for (int i = bossBullets.size - 1; i >= 0; i--) {
            Boss_Bullet bb = bossBullets.get(i);
            bb.update(delta);
            if (bb.isOffScreen()) bossBullets.removeIndex(i);
        }
    }

    private void transitionToPhase2() {
        isPhase1 = false;
        boss_100 = null;
        boss_50 = new Boss_50(player);
        currentBossHp = boss_50.getHp();
        enemies1.clear();
    }

    private void checkCollisions() {
        // Player bullets vs Boss
        if (isPhase1 && boss_100 != null) {
            for (int i = bullets.size - 1; i >= 0; i--) {
                Bullet b = bullets.get(i);
                if (b.rect.overlaps(boss_100.rect)) {
                    boss_100.takeDamage(b.getDamage());
                    bullets.removeIndex(i);
                    Assets_Common.BulletSound.play(0.2f);
                }
            }
        } else if (!isPhase1 && boss_50 != null) {
            for (int i = bullets.size - 1; i >= 0; i--) {
                Bullet b = bullets.get(i);
                if (b.rect.overlaps(boss_50.rect)) {
                    boss_50.takeDamage(b.getDamage());
                    bullets.removeIndex(i);
                    Assets_Common.BulletSound.play(0.2f);
                }
            }
        }

        // Player chết
        for (Boss_Bullet bb : bossBullets) {
            if (player.rect.overlaps(bb.rect)) {
                playerDies();
                return;
            }
        }

        for (Enemy_Bullet eb : enemyBullets) {
            if (player.rect.overlaps(eb.rect)) {
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
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(50, 550, 300, 20);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(50, 550, 300 * (currentBossHp / maxBossHp), 20);
        shapeRenderer.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); Assets_LV3.dispose(); }
}
