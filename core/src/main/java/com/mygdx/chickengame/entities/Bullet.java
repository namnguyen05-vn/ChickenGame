package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.chickengame.utils.Assets_Common;

/**
 * Lớp đạn: xử lý chuyển động, vẽ, và góc nghiêng theo hướng bay (chữ V).
 */
public class Bullet {
    private Sprite sprite;
    public Rectangle rect;
    // Getter cho rect
    public Rectangle getRect() {
        return rect;
    }

    // Trả về sát thương của đạn theo cấp độ
    public int getDamage() {
        switch (level) {
            case 2: return 2;
            case 3: return 3;
            case 4: return 4;
            case 5: return 5;
            default: return 1;
        }
    }
    private Vector2 velocity;
    private int level;
    private float speed;

    public Bullet(float x, float y, int level, float angleOffset) {
        this.level = level;
        // Luôn dùng 1 ảnh đạn duy nhất
        Texture tex = Assets_Common.bulletLV1;
        this.sprite = new Sprite(tex);
        this.sprite.setSize(16, 32);
        this.sprite.setPosition(x, y);
        this.sprite.setOriginCenter();
        this.sprite.setRotation(angleOffset); // xoay hình đạn
        this.rect = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        // Đặt vận tốc bay (chữ V nghĩa là lệch trái hoặc phải 1 góc)
        float radians = (float) Math.toRadians(angleOffset);
        this.velocity = new Vector2((float) Math.sin(radians) * 250, 400f); // Y hướng lên
        this.speed = 400f;
    }

    public void update(float delta) {
        sprite.setX(sprite.getX() + velocity.x * delta);
        sprite.setY(sprite.getY() + velocity.y * delta);
        rect.setPosition(sprite.getX(), sprite.getY());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isOffScreen() {
        return sprite.getY() > com.badlogic.gdx.Gdx.graphics.getHeight();
    }
}
