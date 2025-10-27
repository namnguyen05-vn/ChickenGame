package com.mygdx.chickengame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.utils.Assets_Common;



public class Player {
    public Rectangle rect;
    private float shootTimer = 0;
    private float shootCooldown = 0.3f;
    private float moveSpeed = 300f;
    private int playerLevel = 1; // giữ cho sử dụng sau này
    private int bulletLevel = 1; // 1..5 (số viên bắn mỗi lần)
    // Số mạng của player
    private int lives = 1; // Số mạng mặc định (1)
    // Trừ mạng khi bị trúng đạn
    // Với lives = 1: lần trúng đầu tiên sẽ làm lives = 0 -> isAlive() trả về false
    public void loseLife() {
        if (lives > 0) lives--;
    }

    // Kiểm tra còn sống
    public boolean isAlive() {
        return lives > 0;
    }

    // Getter/setter cho lives
    public int getLives() { return lives; }
    public void setLives(int l) { lives = Math.max(0, l); }

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
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            rect.y += moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            rect.y -= moveSpeed * delta;
        // Giới hạn màn hình
        rect.x = Math.max(0, Math.min(rect.x, Gdx.graphics.getWidth() - rect.width));
        rect.y = Math.max(0, Math.min(rect.y, Gdx.graphics.getHeight() - rect.height));
    }

    private void shoot(Array<Bullet> bullets) {
        Assets_Common.BulletSound.play(0.3f);
        float centerX = rect.x + rect.width / 2f;
        float startY = rect.y + rect.height;
        // Bắn số viên bằng bulletLevel theo dải ngang căn giữa (1..5)
        int count = Math.max(1, Math.min(5, bulletLevel));
        float spacing = 18f; // pixels between bullets
        float startOffset = -spacing * (count - 1) / 2f;
        for (int i = 0; i < count; i++) {
            float offsetX = startOffset + i * spacing;
            bullets.add(new Bullet(centerX + offsetX, startY, 1, 0));
        }
        // Điều chỉnh thời gian chờ (cooldown) theo số viên
        if (count <= 1) shootCooldown = 0.3f;
        else if (count == 2) shootCooldown = 0.25f;
        else if (count == 3) shootCooldown = 0.2f;
        else if (count == 4) shootCooldown = 0.15f;
        else shootCooldown = 0.12f;
        shootTimer = shootCooldown;
    }

    public void upgrade() {
        // Tăng cấp đạn (tối đa 5)
        if (bulletLevel < 5) {
            bulletLevel++;
            Assets_Common.PowerUpSound.play(0.5f);
        }
    }

    // Getter cho X, Y
    public float getX() { return rect.x; }
    public float getY() { return rect.y; }
    public int getPlayerLevel() { return playerLevel; }
    public int getBulletLevel() { return bulletLevel; }
    public Rectangle getRect() { return rect; }
    public void setPlayerLevel(int lv) { playerLevel = Math.max(1, Math.min(3, lv)); }
    public void setBulletLevel(int lv) { bulletLevel = Math.max(1, Math.min(3, lv)); }

    public void resetForNewLevel() {
        // Đặt lại vị trí về mặc định
        // Chỉ đặt lại vị trí player khi vào màn mới. Giữ nguyên cấp đạn và các trạng thái khác.
        rect.x = Gdx.graphics.getWidth() / 2f - rect.width / 2f;
        rect.y = 80;
    }

    public void render(SpriteBatch batch) {
        // Sử dụng ảnh playerLV3 cho mọi cấp đạn 
        Texture tex = Assets_Common.playerLV3;
        batch.draw(tex, rect.x, rect.y, rect.width, rect.height);
    }
}
