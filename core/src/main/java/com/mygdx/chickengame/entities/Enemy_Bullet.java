package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_LV2;

public class Enemy_Bullet {
    public Rectangle rect;
    private static final float SPEED = 50f; // tốc độ bay của đạn

    // Constructor: truyền vào vị trí spawn
    public Enemy_Bullet(float x, float y) {
        rect = new Rectangle(x, y, 15, 25);
    }

    public void update(float delta) {
        rect.y -= SPEED * delta; // bay xuống dưới
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV2.EnemyBullet, rect.x, rect.y, rect.width, rect.height);
    }

    // Check đạn có ra khỏi màn hình không
    public boolean isOutOfScreen() {
        return rect.y + rect.height < 0;
    }
}
