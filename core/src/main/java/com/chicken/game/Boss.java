package com.chicken.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Boss {
    float x, y;
    boolean dead = false;

    public Boss(float x, float y) { this.x = x; this.y = y; }

    public void update(float dt) { /* TODO: Người 2 làm */ }

    public void draw(SpriteBatch batch) {
        Assets.font.draw(batch, "BOSS", x, y);
    }

    public boolean isDead() { return dead; }
}
