package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.PlayerState;

public class Player {
    public Rectangle rect;
    private float shootTimer = 0;
    private static final float SHOOT_COOLDOWN = 0.3f; // 0.3 giây cooldown

    // Movement constants
    private static final float MOVE_SPEED = 250f;
    private static final int BORDER_MARGIN = 10; // Margin từ viền màn hình

    public Player() {
        // Sử dụng kích thước màn hình hiện tại
        int screenWidth = Gdx.graphics.getWidth();
        rect = new Rectangle(screenWidth / 2 - 32, 80, 64, 64); // máy bay ở giữa, cao hơn một chút
    }

    public void update(float delta, Array<Bullet> bullets) {
        // Di chuyển
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            rect.x -= MOVE_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            rect.x += MOVE_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            rect.y += MOVE_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            rect.y -= MOVE_SPEED * delta;
        }

        // Giới hạn vị trí trong màn hình - sử dụng dynamic
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        if (rect.x < BORDER_MARGIN) {
            rect.x = BORDER_MARGIN;
        }
        if (rect.x > screenWidth - rect.width - BORDER_MARGIN) {
            rect.x = screenWidth - rect.width - BORDER_MARGIN;
        }
        if (rect.y < BORDER_MARGIN) {
            rect.y = BORDER_MARGIN;
        }
        if (rect.y > screenHeight - rect.height - BORDER_MARGIN) {
            rect.y = screenHeight - rect.height - BORDER_MARGIN;
        }

        // Cập nhật timer bắn
        shootTimer -= delta;

        // Bắn đạn
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer <= 0) {
            shoot(bullets);
            shootTimer = SHOOT_COOLDOWN;
            Assets_Common.BulletSound.play(0.3f);
        }
    }

    private void shoot(Array<Bullet> bullets) {
        if (PlayerState.isUpgraded()) {
            // Bắn 3 viên đạn
            bullets.add(new Bullet(rect.x + rect.width / 2 - 8, rect.y + rect.height, true)); // giữa
            bullets.add(new Bullet(rect.x + 10, rect.y + rect.height, true)); // trái
            bullets.add(new Bullet(rect.x + rect.width - 26, rect.y + rect.height, true)); // phải
        } else {
            // Bắn 1 viên đạn thông thường
            bullets.add(new Bullet(rect.x + rect.width / 2 - 8, rect.y + rect.height, false));
        }
    }

    public void upgrade() {
        PlayerState.setUpgraded(true);
        Assets_Common.PowerUpSound.play(0.5f);
    }

    public boolean isUpgraded() {
        return PlayerState.isUpgraded();
    }

    public void render(SpriteBatch batch) {
        if (PlayerState.isUpgraded()) {
            batch.draw(Assets_Common.playerUpgradedTex, rect.x, rect.y, rect.width, rect.height);
        } else {
            batch.draw(Assets_Common.playerTex, rect.x, rect.y, rect.width, rect.height);
        }
    }

    public float getX() {
        return rect.x + rect.width / 2;
    }

    public float getY() {
        return rect.y + rect.height / 2;
    }

}
