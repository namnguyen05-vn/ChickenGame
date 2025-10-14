package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_LV2;

public class Enemy2 {
    public Rectangle rect;
    private float speedX, speedY;
    private float Hp = 5;
    private float time_shoot = 0f;
    private float cd_shoot;
    public Array<Enemy_Bullet> bullets;

    // Constants
    private static final int BORDER_MARGIN = 30; // Margin từ viền

    public Enemy2() {
        // Spawn từ phía trên màn hình, di chuyển xuống dưới
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        int screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        rect = new Rectangle(
                MathUtils.random(BORDER_MARGIN, screenWidth - 48 - BORDER_MARGIN),
                screenHeight + MathUtils.random(0, 150), // Spawn phía trên màn hình
                48, 48);

        // Tốc độ di chuyển xuống dưới với drift ngang
        speedX = MathUtils.random(-60, 60); // Di chuyển ngang
        speedY = MathUtils.random(-100, -60); // Di chuyển xuống dưới

        cd_shoot = MathUtils.random(1.5f, 2.0f);
        bullets = new Array<>();
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Sử dụng dynamic screen size
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();

        // Bounce logic chỉ cho di chuyển ngang
        if (rect.x <= BORDER_MARGIN) {
            rect.x = BORDER_MARGIN;
            speedX = Math.abs(speedX); // Đảm bảo di chuyển sang phải
        } else if (rect.x >= screenWidth - rect.width - BORDER_MARGIN) {
            rect.x = screenWidth - rect.width - BORDER_MARGIN;
            speedX = -Math.abs(speedX); // Đảm bảo di chuyển sang trái
        }

        // Enemy sẽ bị xóa khi ra khỏi màn hình phía dưới (không bounce)

        // Bắn đạn
        time_shoot += delta;
        if (time_shoot >= cd_shoot) {
            bullets.add(new Enemy_Bullet(rect.x + rect.width / 2 - 7, rect.y)); // spawn từ vị trí enemy
            time_shoot = 0f;
            cd_shoot = MathUtils.random(1.5f, 2.0f);
        }

        // Update & remove bullets
        for (int i = bullets.size - 1; i >= 0; i--) {
            Enemy_Bullet b = bullets.get(i);
            b.update(delta);
            if (b.isOutOfScreen()) {
                bullets.removeIndex(i);
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV2.enemyTex, rect.x, rect.y, rect.width, rect.height);
        for (Enemy_Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    public void takeDamage(int damage) {
        Hp -= damage;
    }

    public boolean isDead() {
        return Hp <= 0;
    }

    public boolean isAlive() {
        return Hp > 0;
    }

    public float getHp() {
        return Hp;
    }

    // Method to handle shooting for external bullet management
    public void update(float delta, Array<Enemy_Bullet> externalBullets) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Sử dụng dynamic screen size
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();

        // Bounce logic chỉ cho di chuyển ngang
        if (rect.x <= BORDER_MARGIN) {
            rect.x = BORDER_MARGIN;
            speedX = Math.abs(speedX); // Đảm bảo di chuyển sang phải
        } else if (rect.x >= screenWidth - rect.width - BORDER_MARGIN) {
            rect.x = screenWidth - rect.width - BORDER_MARGIN;
            speedX = -Math.abs(speedX); // Đảm bảo di chuyển sang trái
        }

        // Bắn đạn vào external bullet array
        time_shoot += delta;
        if (time_shoot >= cd_shoot) {
            externalBullets.add(new Enemy_Bullet(rect.x + rect.width / 2 - 7, rect.y));
            time_shoot = 0f;
            cd_shoot = MathUtils.random(1.5f, 3.0f);
        }
    }

    public boolean isOffScreen() {
        // Enemy được coi là off-screen khi ra khỏi phía dưới màn hình
        return rect.y + rect.height < 0;
    }
}
