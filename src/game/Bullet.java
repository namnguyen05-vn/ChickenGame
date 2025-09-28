package game;
import java.awt.Rectangle;

public class Bullet {
    private int x, y, width, height;
    private boolean active = true;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = Constants.BULLET_WIDTH;
        this.height = Constants.BULLET_HEIGHT;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
