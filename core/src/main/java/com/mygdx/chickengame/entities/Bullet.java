package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_Common;

public class Bullet {
    public Rectangle rect;

    public Bullet(float x, float y) {
        rect = new Rectangle(x, y, 16, 32);
    }

    public void update(float delta) {
        rect.y += 400 * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_Common.bulletTex, rect.x, rect.y, rect.width, rect.height);
    }
}
