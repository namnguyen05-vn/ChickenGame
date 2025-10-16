package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_Common;

/**
 * Player có 3 cấp độ.
 * - Cấp 1: bắn 1–3 viên (tăng dần theo cấp đạn)
 * - Cấp 2: bắn chữ V 4 viên, tốc độ nhanh hơn
 * - Cấp 3: bắn chữ V 7 viên, nhanh hơn nữa
 */

public class Player {
    public Rectangle rect;
    private float shootTimer = 0;
    private float shootCooldown = 0.3f;
    private float moveSpeed = 300f;
    private int playerLevel = 1; // 1-3
    private int bulletLevel = 1; // 1-3 (cấp đạn, tối đa 3 ở playerLevel 1)

    public Player() {
        rect = new Rectangle(Gdx.graphics.getWidth() / 2f - 32, 80, 64, 64);
    }

    public void update(float delta, Array<Bullet> bullets) {
        handleInput(delta);
        shootTimer -= delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer <= 0) {
            shoot(bullets);
        }
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            rect.x -= moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            rect.x += moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            rect.y += moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            rect.y -= moveSpeed * delta;
        // Giới hạn màn hình
        rect.x = Math.max(0, Math.min(rect.x, Gdx.graphics.getWidth() - rect.width));
        rect.y = Math.max(0, Math.min(rect.y, Gdx.graphics.getHeight() - rect.height));
    }

    private void shoot(Array<Bullet> bullets) {
        Assets_Common.BulletSound.play(0.3f);
        float centerX = rect.x + rect.width / 2f;
        float startY = rect.y + rect.height;
        if (playerLevel == 1) {
            // Cấp 1: bắn 1-3 viên thẳng
            if (bulletLevel == 1) {
                bullets.add(new Bullet(centerX - 8, startY, 1, 0));
                shootCooldown = 0.3f;
            } else if (bulletLevel == 2) {
                bullets.add(new Bullet(centerX - 16, startY, 1, 0));
                bullets.add(new Bullet(centerX + 0, startY, 1, 0));
                shootCooldown = 0.25f;
            } else {
                bullets.add(new Bullet(centerX - 20, startY, 1, 0));
                bullets.add(new Bullet(centerX - 4, startY, 1, 0));
                bullets.add(new Bullet(centerX + 12, startY, 1, 0));
                shootCooldown = 0.2f;
            }
        } else if (playerLevel == 2) {
            // Cấp 2: bắn 5 viên chữ V
            bullets.add(new Bullet(centerX - 8, startY, 1, 0));
            bullets.add(new Bullet(centerX - 30, startY, 1, -20));
            bullets.add(new Bullet(centerX + 14, startY, 1, 20));
            bullets.add(new Bullet(centerX - 50, startY, 1, -35));
            bullets.add(new Bullet(centerX + 34, startY, 1, 35));
            shootCooldown = 0.15f;
        } else {
            // Cấp 3: bắn 10 viên chữ V rộng, tốc độ tăng
            for (int i = -5; i <= 4; i++) {
                float angle = i * 10;
                bullets.add(new Bullet(centerX - 8, startY, 1, angle));
            }
            shootCooldown = 0.09f;
        }
        shootTimer = shootCooldown;
    }

    // Nâng cấp đạn hoặc player
    public void upgrade() {
        if (playerLevel == 1 && bulletLevel < 3) {
            bulletLevel++;
        } else if (playerLevel < 3) {
            playerLevel++;
            bulletLevel = 1;
        }
    }

    // Getter cho X, Y
    public float getX() { return rect.x; }
    public float getY() { return rect.y; }
    public int getPlayerLevel() { return playerLevel; }
    public int getBulletLevel() { return bulletLevel; }
    public Rectangle getRect() { return rect; }
    public void setPlayerLevel(int lv) { playerLevel = Math.max(1, Math.min(3, lv)); }
    public void setBulletLevel(int lv) { bulletLevel = Math.max(1, Math.min(3, lv)); }

    public void render(SpriteBatch batch) {
        Texture tex;
        if (playerLevel == 2) tex = Assets_Common.playerLV2;
        else if (playerLevel == 3) tex = Assets_Common.playerLV3;
        else tex = Assets_Common.playerLV1;
        batch.draw(tex, rect.x, rect.y, rect.width, rect.height);
    }
}
