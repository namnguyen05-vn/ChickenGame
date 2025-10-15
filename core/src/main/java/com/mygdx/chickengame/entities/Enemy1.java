package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_LV1;

public class Enemy1 {
    public Rectangle rect;
    private float speedX, speedY;
    private int hp = 1; // Gà con chỉ có 1 HP

    // Constants
    private static final int BORDER_MARGIN = 20; // Margin từ viền

    public Enemy1() {
        // Spawn từ phía trên màn hình, di chuyển xuống dưới
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        int screenHeight = com.badlogic.gdx.Gdx.graphics.getHeight();

        rect = new Rectangle(
                MathUtils.random(BORDER_MARGIN, screenWidth - 48 - BORDER_MARGIN),
                screenHeight + MathUtils.random(0, 100), // Spawn phía trên màn hình
                48, 48);

        // Tốc độ di chuyển xuống dưới với một chút drift ngang
        speedX = MathUtils.random(-40, 40); // Di chuyển ngang nhẹ
        speedY = MathUtils.random(-80, -40); // Di chuyển xuống dưới
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Sử dụng dynamic screen size
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();

        // Bounce logic cho di chuyển ngang
        if (rect.x <= BORDER_MARGIN) {
            rect.x = BORDER_MARGIN;
            speedX = Math.abs(speedX); // Đảm bảo di chuyển sang phải
        } else if (rect.x >= screenWidth - rect.width - BORDER_MARGIN) {
            rect.x = screenWidth - rect.width - BORDER_MARGIN;
            speedX = -Math.abs(speedX); // Đảm bảo di chuyển sang trái
        }

        // Enemy sẽ bị xóa khi ra khỏi màn hình phía dưới (không bounce)
        // Điều này sẽ được xử lý trong Level1Screen
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV1.enemyTex, rect.x, rect.y, rect.width, rect.height);
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int getHp() {
        return hp;
    }

    public boolean isOffScreen() {
        // Enemy được coi là off-screen khi ra khỏi phía dưới màn hình
        return rect.y + rect.height < 0;
    }
}
