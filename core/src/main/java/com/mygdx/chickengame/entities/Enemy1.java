package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_LV1;

public class Enemy1 {
    public Rectangle rect;
    private float speedX, speedY;
    private float Hp=2;

    public Enemy1() {
        rect = new Rectangle(MathUtils.random(50, 750), MathUtils.random(300, 550), 48, 48);
        speedX = MathUtils.random(-100, 100);
        speedY = MathUtils.random(-100, 100);
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Bật lại khi chạm viền
        if (rect.x < 50 || rect.x > 700 - rect.width) speedX = -speedX;
        if (rect.y < 200 || rect.y > 600 - rect.height) speedY = -speedY;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV1.enemyTex, rect.x, rect.y, rect.width, rect.height);
    }
}
