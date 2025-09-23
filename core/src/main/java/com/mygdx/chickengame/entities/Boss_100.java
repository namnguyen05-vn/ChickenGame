package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_LV3;

public class Boss_100 {
    public Rectangle rect;
    private float speedX, speedY;
    private float hp = 30;              // máu Boss
    private float shootInterval;        // thời gian chờ giữa 2 phát bắn
    private float time_last = 0f;
    private Player player;              // tham chiếu tới player

    public Array<Boss_Bullet> bullets;

    public Boss_100(Player player) {
        this.player = player;

        rect = new Rectangle(350, 350, 231, 130);
        speedX = 75;
        speedY = 0;

        shootInterval = MathUtils.random(0.5f, 0.9f);
        bullets = new Array<>();
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Bật lại khi chạm viền
        if (rect.x < 50 || rect.x > 700 - rect.width) speedX = -speedX;
        if (rect.y < 449 || rect.y > 452 - rect.height) speedY = -speedY;

        // Tăng thời gian chờ
        time_last += delta;

        if (time_last >= shootInterval) {
            // Boss bắn về phía Player
            float playerX = player.getX();
            float playerY = player.getY();

            bullets.add(new Boss_Bullet(rect.x + rect.width-65, rect.y, playerX, playerY));
            bullets.add(new Boss_Bullet(rect.x + rect.width-195, rect.y, playerX, playerY));

            time_last = 0f;
            shootInterval = MathUtils.random(0.5f, 0.9f);
        }

        // Update đạn
        for (Boss_Bullet bullet : bullets) {
            bullet.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV3.enemyTex_Boss_100HP, rect.x, rect.y, rect.width, rect.height);
        for (Boss_Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    public float getX() {
        return rect.x;
    }

    public float getY() {
        return rect.y;
    }

    public float getHp() {
        return hp;
    }

    public void takeDamage(float damage) {
        hp -= damage;
    }
}
