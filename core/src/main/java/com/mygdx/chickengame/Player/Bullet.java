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
    private Rectangle rect;
    private Vector2 velocity;
    private int level;
    private float speed;

    public Bullet(float x, float y, int level, float angleOffset) {
        this.level = level;

        // Chọn texture theo cấp độ
        Texture tex;
        switch (level) {
            case 2: tex = Assets_Common.bulletLV2; break;
            case 3: tex = Assets_Common.bulletLV3; break;
            case 4: tex = Assets_Common.bulletLV4; break;
            case 5: tex = Assets_Common.bulletLV5; break;
            default: tex = Assets_Common.bulletLV1;
        }

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
