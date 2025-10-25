package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.utils.Assets_Common;

/**
 * Màn "cảnh báo boss", ví dụ hiển thị WARNING / BOSS INCOMING.
 * Người chơi nhấn ENTER để bắt đầu đánh boss thật (BossScreen).
 */
public class LevelBoss extends BaseScreen {

    private float timer = 0f; // nếu muốn auto chuyển sau vài giây

    public LevelBoss(ChickenGame game) {
        super(game);
        game.session.level = 3; // boss stage
    }

    private void update(float dt) {
        timer += dt;

        // Cách 1: Người chơi nhấn ENTER để vào boss
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.goBoss();
        }

        // Cách 2 (tuỳ bạn): Tự động chuyển sau 3 giây
        // if (timer > 3f) {
        //     game.goBoss();
        // }
    }

    @Override
    public void render(float delta) {
        update(delta);
        clearScreen(0.2f, 0f, 0f);

        game.batch.begin();
        Assets_Common.font.draw(game.batch, "!!! BOSS INCOMING !!!", 100, 300);
        Assets_Common.font.draw(game.batch, "Press ENTER to fight", 100, 260);
        game.batch.end();
    }
}
