package com.mygdx.chickengame.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.chickengame.utils.Assets_Common;

public class Boss {
    public Rectangle rect;
    private int hp = 100;

    public Boss() {
        rect = new Rectangle(300, 400, 64, 64);
    }

    public void update(float dt) {
        // Boss movement / hành vi có thể thêm sau
    }

    public void render(SpriteBatch batch) {
        // tạm dùng playerLV3 làm sprite của boss
        batch.draw(Assets_Common.playerLV3, rect.x, rect.y, rect.width, rect.height);

        // hiển thị HP
        Assets_Common.font.draw(batch, "HP:" + hp, rect.x, rect.y + rect.height + 20);
    }

    public void hit() {
        hp -= 5;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
