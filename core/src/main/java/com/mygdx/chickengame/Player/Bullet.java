package com.mygdx.chickengame.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    private Texture texture;
    private Rectangle bounds;
    private float speed = 400f;

    public Bullet(float x, float y) {
        texture = new Texture("bullet.png");
        bounds = new Rectangle(x, y, 8, 16);
    }

    public void update(float delta) {
        bounds.y += speed * delta;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public boolean isOffScreen() {
        return bounds.y > com.badlogic.gdx.Gdx.graphics.getHeight();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
