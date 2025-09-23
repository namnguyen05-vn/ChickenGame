
package com.mygdx.chickengame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.chickengame.ChickenGame;
import com.mygdx.chickengame.entities.Boss_100;
import com.mygdx.chickengame.entities.Boss_50;
import com.mygdx.chickengame.entities.Boss_Bullet;
import com.mygdx.chickengame.entities.Bullet;
import com.mygdx.chickengame.entities.Enemy1;
import com.mygdx.chickengame.entities.Enemy3;
import com.mygdx.chickengame.entities.Player;
import com.mygdx.chickengame.utils.Assets_Common;
import com.mygdx.chickengame.utils.Assets_LV3;


public class LevelBoss implements Screen {
    private ChickenGame game;
    private SpriteBatch batch;
    private Player player;
    private Array<Enemy1> enemies1;
    private Array<Enemy3> enemies3;
    private Array<Bullet> bullets;
    private Array<Boss_Bullet> b_bullets;
    private Boss_100 boss_100;
    private Boss_50 boss_50;

    public LevelBoss(ChickenGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.player = new Player();
        this.enemies1 = new Array<>();
        this.enemies3 = new Array<>();
        this.bullets = new Array<>();
        this.b_bullets = new Array<>();

        Assets_Common.load();
        Assets_LV3.load();   
        Assets_LV3.BGMusic.play();

        this.boss_100 = new Boss_100(player);

    }

    @Override
    public void render(float delta) {
        try {
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update
        player.update(delta, bullets);
        for (Enemy1 e : enemies1) e.update(delta);
        for (Bullet b : bullets) b.update(delta);
        if(boss_100!=null)boss_100.update(delta);

        // vẽ
        batch.begin();
        batch.draw(Assets_LV3.backgroundTex, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        if(boss_100!=null) boss_100.render(batch);
        for (Enemy1 e : enemies1) e.render(batch);
        for (Bullet b : bullets) b.render(batch);
        
batch.end();
        } catch (Exception e) {
            Gdx.app.error("LevelBoss", "Error in render", e);
        }
    }
    

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        Assets_LV3.dispose();  
    }
   
}