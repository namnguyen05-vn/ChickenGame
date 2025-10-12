package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_Common;

public class Bullet {
    public Rectangle rect;
    private float damage = 1;
    private boolean isUpgraded;

    public Bullet(float x, float y) {
        this(x, y, false);
    }

    public Bullet(float x, float y, boolean upgraded) {
        rect = new Rectangle(x, y, 16, 32);
        this.isUpgraded = upgraded;
        this.damage = upgraded ? 2 : 1; // đạn nâng cấp gây 2 damage
    }

    public void update(float delta) {
        rect.y += 400 * delta;
    }

    public void render(SpriteBatch batch) {
        if (isUpgraded) {
            batch.draw(Assets_Common.upgradedbulletTex, rect.x, rect.y, rect.width, rect.height);
        } else {
            batch.draw(Assets_Common.bulletTex, rect.x, rect.y, rect.width, rect.height);
        }
    }

    public float getDamage() {
        return damage;
    }

    public boolean isOffScreen() {
        // Sử dụng kích thước màn hình dynamic
        return rect.y > com.badlogic.gdx.Gdx.graphics.getHeight();
    }
}
