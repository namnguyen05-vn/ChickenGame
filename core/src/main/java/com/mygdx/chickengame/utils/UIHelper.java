package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class UIHelper {

    /**
     * Vẽ button với shadow effect
     */
    public static void drawButtonWithShadow(SpriteBatch batch, Texture buttonTexture, Rectangle bounds,
            boolean isHovered) {
        if (isHovered) {
            // Vẽ shadow
            batch.setColor(0.2f, 0.2f, 0.2f, 0.5f);
            batch.draw(buttonTexture, bounds.x + 5, bounds.y - 5, bounds.width, bounds.height);

            // Vẽ button chính với glow effect
            batch.setColor(1.3f, 1.3f, 1.1f, 1f);
            batch.draw(buttonTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            // Vẽ shadow nhẹ
            batch.setColor(0.1f, 0.1f, 0.1f, 0.3f);
            batch.draw(buttonTexture, bounds.x + 3, bounds.y - 3, bounds.width, bounds.height);

            // Vẽ button chính
            batch.setColor(1f, 1f, 1f, 1f);
            batch.draw(buttonTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        }

        batch.setColor(1f, 1f, 1f, 1f); // Reset color
    }

    /**
     * Tính toán floating animation
     */
    public static float calculateFloatingOffset(float time, float amplitude, float frequency) {
        return (float) Math.sin(time * frequency) * amplitude;
    }

    /**
     * Tính toán pulse animation
     */
    public static float calculatePulseScale(float time, float speed, float minScale, float maxScale) {
        float pulseValue = (float) Math.sin(time * speed) * 0.5f + 0.5f;
        return minScale + (maxScale - minScale) * pulseValue;
    }

    /**
     * Smooth lerp cho animations
     */
    public static float lerp(float current, float target, float speed, float delta) {
        return current + (target - current) * speed * delta;
    }
}