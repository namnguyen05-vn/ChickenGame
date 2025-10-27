package com.mygdx.chickengame.utils;

import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.screens.Level1Screen;
import com.mygdx.chickengame.screens.Level2Screen;
import com.mygdx.chickengame.screens.LevelBoss;

public class GameSession {
    private static int lastLevelIndex = 1;

    public static void setCurrentLevel(int idx) {
        lastLevelIndex = idx;
    }

    public static boolean restartLastLevel(ChickenGame game) {
        switch (lastLevelIndex) {
            case 1:
                game.setScreen(new Level1Screen(game));
                return true;
            case 2:
                game.setScreen(new Level2Screen(game, new Player()));
                return true;
            case 3:
                game.setScreen(new LevelBoss(game, new Player()));
                return true;
            default:
                return false;
        }
    }
}
