package com.chicken.game;

public class GameSession {
    public int score = 0;
    public int lives = 3;
    public int level = 1;

    public void resetForNewGame() {
        score = 0;
        lives = 3;
        level = 1;
    }
}
