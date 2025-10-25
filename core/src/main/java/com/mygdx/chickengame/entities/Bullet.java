package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_Common;

/**
 * Đạn của Player.
 * Hỗ trợ 2 kiểu tạo:
 *  - new Bullet(x, y)
 *  - new Bullet(x, y, dirX, dirY)  // code Player của bạn đang dùng (1,0)
 */
public class Bullet {
    public Rectangle rect;

    private float vx;
    private float vy;

    // Constructor mặc định: bắn thẳng lên
    public Bullet(float x, float y) {
        rect = new Rectangle(x - 3, y, 6, 12);
        this.vx = 0f;
        this.vy = 400f;
    }

    // Constructor theo hướng: (x,y,dirX,dirY)
    // Hiện tại Player gọi new Bullet(..., 1, 0)
    // nên mình map (1,0) => bắn thẳng lên
    public Bullet(float x, float y, int dirX, int dirY) {
        rect = new Rectangle(x - 3, y, 6, 12);

        float baseSpeed = 400f;
        this.vx = dirX * baseSpeed;
        this.vy = dirY * baseSpeed;

        // nếu team bạn truyền (1,0) nhưng thực tế muốn bắn lên
        if (dirX == 1 && dirY == 0) {
            this.vx = 0f;
            this.vy = 400f;
        }
    }

    public void update(float dt) {
        rect.x += vx * dt;
        rect.y += vy * dt;
    }

    public void render(SpriteBatch batch) {
        // dùng bulletTex từ Assets_Common
        batch.draw(Assets_Common.bulletTex, rect.x, rect.y, rect.width, rect.height);
        // nếu team muốn nâng cấp đạn đổi sprite, sau này có thể chọn upgradedbulletTex
    }

    // Dùng trong Level1Screen / Level2Screen:
    // bullets.get(i).isOffScreen(Gdx.graphics.getHeight())
    public boolean isOffScreen(float screenHeight) {
        return rect.y > screenHeight + 50   // bay quá đỉnh
            || rect.y < -50                // lọt đáy
            || rect.x < -50
            || rect.x > Gdx.graphics.getWidth() + 50;
    }

    // Dự phòng cho code nào gọi không tham số
    public boolean isOffScreen() {
        return rect.y > Gdx.graphics.getHeight() + 50
            || rect.y < -50
            || rect.x < -50
            || rect.x > Gdx.graphics.getWidth() + 50;
    }
}
