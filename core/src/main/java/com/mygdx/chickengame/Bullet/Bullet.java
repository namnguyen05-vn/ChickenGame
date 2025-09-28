package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.chickengame.utils.Assets_Common;

public class Bullet {
    public float x, y;
    public float speed = 400; // tốc độ bay lên (px/s)
    public float width, height;
    public boolean alive = true;

    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = Assets_Common.bulletTex.getWidth();
        this.height = Assets_Common.bulletTex.getHeight();
    }

    public void update(float delta) {
        y += speed * delta;
        // nếu bay ra khỏi màn hình thì cho chết
        if (y > 800) alive = false; 
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_Common.bulletTex, x, y);
    }
}
