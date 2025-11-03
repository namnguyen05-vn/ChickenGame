package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.chickengame.utils.Assets_Common;


public class Bullet {
    private Sprite sprite;
    public Rectangle rect;

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

    public Bullet(float x, float y, int level) {
    this.level = level;

    // Dùng 1 ảnh đạn duy nhất
        // Constructor: đạn bắn thẳng lên
        Texture tex = Assets_Common.bulletLV1;
        this.sprite = new Sprite(tex);
        this.sprite.setSize(16, 32);
    
        // Đặt vị trí theo tâm 
        this.sprite.setPosition(x - this.sprite.getWidth() / 2f, y - this.sprite.getHeight() / 2f);
    
        // Không cần xoay vì đạn bay thẳng
        this.sprite.setOriginCenter();
    
        // Hitbox (vùng va chạm)
        this.rect = new Rectangle(this.sprite.getX(), this.sprite.getY(), sprite.getWidth(), sprite.getHeight());
    
        // Hướng bay thẳng lên (0 độ)
        this.velocity = new Vector2(0, 400f);
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
