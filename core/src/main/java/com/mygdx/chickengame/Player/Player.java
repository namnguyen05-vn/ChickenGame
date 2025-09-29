package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.Constants;

import java.util.ArrayList;

public class Player {
    public float x, y;
    public float speed = 300; // tốc độ di chuyển
    public float width, height;

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private float fireCooldown = 0.25f; // 0.25s / viên
    private float timeSinceLastShot = 0;

    public Player() {
        width = Assets_Common.playerTex.getWidth();
        height = Assets_Common.playerTex.getHeight();
        resetPosition();
    }

    public void resetPosition() {
        x = (Constants.SCREEN_WIDTH - width) / 2f;
        y = 50; // đáy màn hình
    }

    public void update(float delta) {
        // Di chuyển
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += speed * delta;

        // Giữ player trong màn hình
        if (x < 0) x = 0;
        if (x + width > Constants.SCREEN_WIDTH) x = Constants.SCREEN_WIDTH - width;

        // Bắn đạn
        timeSinceLastShot += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeSinceLastShot >= fireCooldown) {
            bullets.add(new Bullet(x + width / 2 - 5, y + height));
            Assets_Common.BulletSound.play();
            timeSinceLastShot = 0;
        }

        // Update đạn
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update(delta);
            if (!b.alive) bullets.remove(i--);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets_Common.playerTex, x, y);
        for (Bullet b : bullets) {
            b.render(batch);
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}