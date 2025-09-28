package game;
import java.awt.Rectangle;

public class EnemyBullet {
    private int x, y, width, height;

    public EnemyBullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = Constants.ENEMY_BULLET_WIDTH;
        this.height = Constants.ENEMY_BULLET_HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
