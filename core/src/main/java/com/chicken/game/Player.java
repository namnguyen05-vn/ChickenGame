package com.chicken.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    float x = 380, y = 50;
    boolean dead = false;

    public void update(float dt) { /* TODO: Người 1 làm */ }

    public void draw(SpriteBatch batch) {
        Assets.font.draw(batch, "P", x, y); // vẽ placeholder
    }

    public boolean isDead() { return dead; }
}
