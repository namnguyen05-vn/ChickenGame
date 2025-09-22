package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_Common;

public class Player {
    public Rectangle rect;

    public Player() {
        rect = new Rectangle(400, 50, 64, 64); // máy bay ở giữa đáy màn hình
    }

    public void update(float delta, Array<Bullet> bullets) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) rect.x -= 300 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rect.x += 300 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) rect.y += 300 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) rect.y -= 300 * delta;

        // Giới hạn trong màn hình
        if (rect.x < 0) rect.x = 0;
        if (rect.x > 800 - rect.width) rect.x = 800 - rect.width;
        if (rect.y < 0) rect.y = 0;
        if (rect.y > 600 - rect.height) rect.y = 600 - rect.height;

        // Bắn đạn
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bullets.add(new Bullet(rect.x + rect.width / 2 - 8, rect.y + rect.height));
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_Common.playerTex, rect.x, rect.y, rect.width, rect.height);
    }
}
