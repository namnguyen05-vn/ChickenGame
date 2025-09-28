 package game;
 import java.awt.Rectangle;

public class Player {
    private int x, y, width, height;
    private int lives;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = Constants.PLAYER_WIDTH;
        this.height = Constants.PLAYER_HEIGHT;
        this.lives = 3;
    }

    public void loseLife() {
        lives--;
        System.out.println("Player mat 1 mang! Con: " + lives);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
