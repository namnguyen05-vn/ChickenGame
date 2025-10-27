package com.mygdx.chickengame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.chickengame.screens.MenuScreen;
import com.mygdx.chickengame.screens.Level1Screen;
import com.mygdx.chickengame.screens.Level2Screen;
import com.mygdx.chickengame.screens.LevelBoss;
import com.mygdx.chickengame.screens.BossScreen;
import com.mygdx.chickengame.screens.VictoryScreen;
import com.mygdx.chickengame.screens.GameOverScreen;

import com.mygdx.chickengame.utils.GameSession;
import com.mygdx.chickengame.utils.Assets_Common;

public class ChickenGame extends Game {
    public SpriteBatch batch;
    public GameSession session;

    @Override
    public void create() {
        batch = new SpriteBatch();
        session = new GameSession();

        // load tất cả hình, âm thanh
        Assets_Common.load();

        // vào menu đầu tiên
        setScreen(new MenuScreen(this));
    }

    // Helpers để chuyển màn
    public void goMenu()     { setScreen(new MenuScreen(this)); }
    public void goLevel1()   { setScreen(new Level1Screen(this)); }
    public void goLevel2()   { setScreen(new Level2Screen(this)); }
    public void goLevelBoss(){ setScreen(new LevelBoss(this)); }
    public void goBoss()     { setScreen(new BossScreen(this)); }
    public void goVictory()  { setScreen(new VictoryScreen(this)); }
    public void goGameOver() { setScreen(new GameOverScreen(this)); }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }

        // giải phóng tài nguyên
        Assets_Common.dispose();
        batch.dispose();
    }
}
