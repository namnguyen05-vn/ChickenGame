package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ParticleEffect {

    public static class Particle {
        public Vector2 position;
        public Vector2 velocity;
        public float life;
        public float maxLife;
        public float size;
        public float alpha;

        public Particle(float x, float y, float vx, float vy, float life, float size) {
            this.position = new Vector2(x, y);
            this.velocity = new Vector2(vx, vy);
            this.life = life;
            this.maxLife = life;
            this.size = size;
            this.alpha = 1f;
        }

        public void update(float delta) {
            position.add(velocity.x * delta, velocity.y * delta);
            life -= delta;
            alpha = life / maxLife;
        }

        public boolean isDead() {
            return life <= 0;
        }
    }

    private Array<Particle> particles;
    private float spawnTimer;
    private static final float SPAWN_RATE = 0.1f;

    public ParticleEffect() {
        particles = new Array<>();
        spawnTimer = 0f;
    }

    public void update(float delta) {
        spawnTimer += delta;

        // Sinh particle mới
        if (spawnTimer >= SPAWN_RATE) {
            spawnParticle();
            spawnTimer = 0f;
        }

        // Cập nhật các particle hiện có
        for (int i = particles.size - 1; i >= 0; i--) {
            Particle particle = particles.get(i);
            particle.update(delta);

            if (particle.isDead()) {
                particles.removeIndex(i);
            }
        }
    }

    private void spawnParticle() {
        float x = MathUtils.random(0, 800);
        float y = 600 + 10; // Start above screen
        float vx = MathUtils.random(-20, 20);
        float vy = MathUtils.random(-100, -50);
        float life = MathUtils.random(3f, 6f);
        float size = MathUtils.random(2f, 6f);

        particles.add(new Particle(x, y, vx, vy, life, size));
    }

    public void render(SpriteBatch batch, Texture particleTexture) {
        for (Particle particle : particles) {
            batch.setColor(1f, 1f, 1f, particle.alpha * 0.6f);
            batch.draw(particleTexture,
                    particle.position.x - particle.size / 2,
                    particle.position.y - particle.size / 2,
                    particle.size, particle.size);
        }
        batch.setColor(1f, 1f, 1f, 1f); // Reset color
    }

    public void dispose() {
        particles.clear();
    }
}