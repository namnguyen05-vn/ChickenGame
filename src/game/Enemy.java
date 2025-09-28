package game;
import java.awt.Rectangle;

public class Enemy {
    private int x, y, width, height;
    private int health;

    public Enemy(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.width = Constants.ENEMY_WIDTH;
        this.height = Constants.ENEMY_HEIGHT;
        this.health = health;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        System.out.println("Enemy bi trung dan! Mau con: " + health);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
