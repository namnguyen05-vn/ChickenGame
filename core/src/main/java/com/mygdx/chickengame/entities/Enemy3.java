package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_LV3;

public class Enemy3 {
    public Rectangle rect;
    private float speedX, speedY;
    private float Hp=3;

    public Enemy3() {
        rect = new Rectangle(MathUtils.random(50, 750), MathUtils.random(300, 550), 48, 48);
        speedX = MathUtils.random(-100, 100);
        speedY = MathUtils.random(-100, 100);
    }

    public void update(float delta) {
        rect.x += speedX * delta;
        rect.y += speedY * delta;

        // Bật lại khi chạm viền
        if (rect.x < 0 || rect.x > 800 - rect.width) speedX = -speedX;
        if (rect.y < 200 || rect.y > 600 - rect.height) speedY = -speedY;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_LV3.enemyTex_Chicken2, rect.x, rect.y, rect.width, rect.height);
    }
}
