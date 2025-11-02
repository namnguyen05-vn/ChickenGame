package com.chicken.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    float x, y;
    boolean dead = false;

    public Enemy(float x, float y) { this.x = x; this.y = y; }

    public void update(float dt) { /* TODO: Người 2 làm */ }

    public void draw(SpriteBatch batch) {
        Assets.font.draw(batch, "E", x, y);
    }

    public boolean isDead() { return dead; }
}
