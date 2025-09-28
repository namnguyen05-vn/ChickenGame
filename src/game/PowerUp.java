package game;
import java.awt.Rectangle;

public class PowerUp {
    private int x, y, size = 20;

    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
