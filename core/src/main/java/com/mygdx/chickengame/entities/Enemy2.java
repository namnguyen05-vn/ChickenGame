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

    public Enemy2() {
        rect = new Rectangle(MathUtils.random(50, 750), MathUtils.random(300, 550), 48, 48);
        speedX = MathUtils.random(-100, 100);
        speedY = MathUtils.random(-100, 100);

        cd_shoot = MathUtils.random(1.5f, 2.0f);
        bullets = new Array<>();
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Bật lại khi chạm viền
        if (rect.x < 50 || rect.x > 700 - rect.width) speedX = -speedX;
        if (rect.y < 200 || rect.y > 600 - rect.height) speedY = -speedY;

        // Bắn đạn
        time_shoot += delta;
        if (time_shoot >= cd_shoot) {
            bullets.add(new Enemy_Bullet(rect.x + rect.width/2-7, rect.y)); // spawn từ vị trí enemy
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

    public void takeDamage(float damage) {
        Hp -= damage;
    }

    public boolean isAlive() {
        return Hp > 0;
    }
}
