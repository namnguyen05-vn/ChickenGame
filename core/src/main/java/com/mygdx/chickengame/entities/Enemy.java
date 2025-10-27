package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_Common;

public class Enemy {
    public Rectangle rect;
    private float speedY = -40f;
    private int hp = 3;

    public Enemy(float x, float y) {
        rect = new Rectangle(x, y, 28, 28);
    }

    public void update(float dt) {
        rect.y += speedY * dt;
    }

    public void render(SpriteBatch batch) {
        // tạm dùng playerLV1 làm sprite kẻ địch
        batch.draw(Assets_Common.playerLV1, rect.x, rect.y, rect.width, rect.height);
    }

    public void hit() {
        hp--;
    }

    public boolean isDead() {
        return hp <= 0 || rect.y < -40;
    }
}
