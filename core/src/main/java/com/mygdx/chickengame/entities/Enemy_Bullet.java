package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets;

public class Enemy_Bullet {
    public Rectangle rect;

    public Enemy_Bullet(float x, float y) {
        rect = new Rectangle(x, y, 15, 25);
    }

    public void update(float delta) {
        rect.y -= 200 * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets.enemy_bulletsTex, rect.x, rect.y, rect.width, rect.height);
    }
}