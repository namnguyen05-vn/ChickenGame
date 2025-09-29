package com.mygdx.chickengame.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Player {
    private Texture texture;
    private Rectangle bounds;
    private float speed = 300f; // tốc độ di chuyển
    private ArrayList<Bullet> bullets;
    private float shootCooldown = 0.3f; // delay giữa các viên đạn
    private float shootTimer = 0;

    public Player() {
        texture = new Texture("player.png");
        bounds = new Rectangle(
                (Gdx.graphics.getWidth() - 64) / 2f,
                50,
                64,
                64
        );
        bullets = new ArrayList<>();
    }

    public void update(float delta) {
        handleInput(delta);

        // cập nhật cooldown bắn
        if (shootTimer > 0) shootTimer -= delta;

        // update bullets
        for (Bullet b : bullets) {
            b.update(delta);
        }
        bullets.removeIf(b -> b.isOffScreen());
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            bounds.x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            bounds.x += speed * delta;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            shoot();
        }

        // giữ player trong màn hình
        if (bounds.x < 0) bounds.x = 0;
        if (bounds.x > Gdx.graphics.getWidth() - bounds.width) {
            bounds.x = Gdx.graphics.getWidth() - bounds.width;
        }
    }

    private void shoot() {
        if (shootTimer <= 0) {
            bullets.add(new Bullet(bounds.x + bounds.width / 2 - 4, bounds.y + bounds.height));
            shootTimer = shootCooldown;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        for (Bullet b : bullets) {
            b.render(batch);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void resetPosition() {
        bounds.x = (Gdx.graphics.getWidth() - bounds.width) / 2f;
    }

    public void dispose() {
        texture.dispose();
    }
}
