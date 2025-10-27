package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.chickengame.utils.Assets_LV3;

public class Boss_Bullet {
    private Vector2 position;
    private Vector2 velocity;
    public Rectangle rect;
    private float speed = 50; // tốc độ đạn px/s

    // nhận vị trí Boss và Player
    public Boss_Bullet(float startX, float startY, float targetX, float targetY) {
        position = new Vector2(startX, startY);
        // Tính hướng từ Boss -> Player rồi nhân với speed
        velocity = new Vector2(targetX - startX, targetY - startY).nor().scl(speed);

        rect = new Rectangle(startX, startY, 15, 25);
    }

    public void update(float delta) {
        // Cập nhật vị trí
        position.add(velocity.x * delta, velocity.y * delta);

        // Cập nhật lại
        rect.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV3.BossBullet, rect.x, rect.y, rect.width, rect.height);
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isOffScreen() {
        // Sử dụng kích thước màn hình dynamic
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        int screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        return rect.x < -rect.width || rect.x > screenWidth ||
                rect.y < -rect.height || rect.y > screenHeight;
    }
}
