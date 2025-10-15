package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_Common;

public class PowerUp {
    public Rectangle rect;
    private float speed = 100f;

    public PowerUp(float x, float y) {
        rect = new Rectangle(x, y, 32, 32);
    }

    public void update(float delta) {
        rect.y -= speed * delta; // rơi xuống
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_Common.powerUpTex, rect.x, rect.y, rect.width, rect.height);
    }

    public boolean isOffScreen() {
        return rect.y + rect.height < 0;
    }
}