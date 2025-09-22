package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_LV3;

public class Boss {
    public Rectangle rect;
    private float speedX, speedY;

    private float shootInterval; // thời gian chờ giữa 2 phát bắn
    private float time_last = 0f;

    public Array<Enemy_Bullet> bullets;

    public Boss() {
        rect = new Rectangle(MathUtils.random(50, 750), MathUtils.random(300, 550), 64, 64);
        speedX = MathUtils.random(-100, 100);
        speedY = MathUtils.random(-100, 100);

        // random thời gian bắn, mỗi Enemy sẽ có interval riêng
        shootInterval = MathUtils.random(0.5f, 0.9f);

        bullets = new Array<>();
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Bật lại khi chạm viền
        if (rect.x < 0 || rect.x > 800 - rect.width) speedX = -speedX;
        if (rect.y < 200 || rect.y > 600 - rect.height) speedY = -speedY;

        // Tăng thời gian chờ
        time_last += delta;

        // Đủ thời gian thì bắn 1 viên
        if (time_last >= shootInterval) {
            bullets.add(new Enemy_Bullet(rect.x + rect.width/2, rect.y));
            time_last = 0f;

            // Optional: random lại interval cho phát tiếp theo (nếu muốn mỗi lần khác nhau)
            shootInterval = MathUtils.random(1.5f, 3.5f);
        }

        // Update đạn
        for (Enemy_Bullet bullet : bullets) {
            bullet.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV3.enemyTex_Boss_100HP, rect.x, rect.y, rect.width, rect.height);
        for (Enemy_Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }
}
