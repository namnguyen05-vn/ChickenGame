package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_Common;

/**
 * Player có 3 cấp độ.
 * - Cấp 1: bắn 1–3 viên (tăng dần theo cấp đạn)
 * - Cấp 2: bắn chữ V 4 viên, tốc độ nhanh hơn
 * - Cấp 3: bắn chữ V 7 viên, nhanh hơn nữa
 */
public class Player {
    private Rectangle rect;
    private float shootTimer = 0;
    private float shootCooldown = 0.3f;
    private float moveSpeed = 300f;
    private int level = 1; // mặc định cấp 1

    public Player() {
        rect = new Rectangle(Gdx.graphics.getWidth() / 2f - 32, 80, 64, 64);
    }

    public void update(float delta, Array<Bullet> bullets) {
        handleInput(delta);
        shootTimer -= delta;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer <= 0) {
            shoot(bullets);
        }
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            rect.x -= moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            rect.x += moveSpeed * delta;

        // Giới hạn màn hình
        rect.x = Math.max(0, Math.min(rect.x, Gdx.graphics.getWidth() - rect.width));
    }

    private void shoot(Array<Bullet> bullets) {
        Assets_Common.BulletSound.play(0.3f);

        float centerX = rect.x + rect.width / 2f;
        float startY = rect.y + rect.height;

        switch (level) {
            case 1:
                // cấp 1: bắn 1 viên
                bullets.add(new Bullet(centerX - 8, startY, 1, 0));
                shootCooldown = 0.3f;
                break;

            case 2:
                // cấp 2: bắn 3 viên (chữ V nhẹ)
                bullets.add(new Bullet(centerX - 8, startY, 2, 0));
                bullets.add(new Bullet(centerX - 20, startY, 2, -10));
                bullets.add(new Bullet(centerX + 10, startY, 2, 10));
                shootCooldown = 0.25f;
                break;

            case 3:
                // cấp 3: bắn chữ V mạnh 4 viên
                bullets.add(new Bullet(centerX - 8, startY, 4, 0));
                bullets.add(new Bullet(centerX - 25, startY, 4, -15));
                bullets.add(new Bullet(centerX + 10, startY, 4, 15));
                bullets.add(new Bullet(centerX - 40, startY, 4, -25));
                shootCooldown = 0.2f;
                break;

            default:
                // cấp 3 nâng cao: 7 viên chữ V rộng
                bullets.add(new Bullet(centerX - 8, startY, 5, 0));
                for (int i = 1; i <= 3; i++) {
                    bullets.add(new Bullet(centerX - i * 15, startY, 5, -i * 10));
                    bullets.add(new Bullet(centerX + i * 15, startY, 5, i * 10));
                }
                shootCooldown = 0.15f;
        }

        shootTimer = shootCooldown;
    }

    public void render(SpriteBatch batch) {
        Texture tex = getTextureByLevel();
        batch.draw(tex, rect.x, rect.y, rect.width, rect.height);
    }

    private Texture getTextureByLevel() {
        switch (level) {
            case 2: return Assets_Common.playerLV2;
            case 3: return Assets_Common.playerLV3;
            default: return Assets_Common.playerLV1;
        }
    }

    // ====== GET / SET ======
    public Rectangle getRect() { return rect; }
    public void setLevel(int newLevel) { this.level = Math.min(3, newLevel); }
    public int getLevel() { return level; }
}
