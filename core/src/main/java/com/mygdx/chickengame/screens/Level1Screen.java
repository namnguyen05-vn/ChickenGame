package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.utils.Assets;

public class Level1Screen implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private Player player;
    private Array<Enemy> enemies;
    private Array<Bullet> bullets;

    public Level1Screen(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = new Player();
        this.enemies = new Array<>();
        this.bullets = new Array<>();

        // tạo 5 con gà
        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update
        player.update(delta, bullets);
        for (Enemy e : enemies) e.update(delta);
        for (Bullet b : bullets) b.update(delta);

        // vẽ
        batch.begin();
        batch.draw(Assets.backgroundTex, 0, 0, 800, 600);
        player.render(batch);
        for (Enemy e : enemies) e.render(batch);
        for (Bullet b : bullets) b.render(batch);
        batch.end();
    }
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); }
}
