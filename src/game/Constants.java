// File: Constants.java
// Chứa các hằng số (screen, tốc độ, kích thước,...) dùng chung trong game
package game;
public class Constants {

    // Kích thước màn hình
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Tốc độ di chuyển
    public static final int PLAYER_SPEED = 5;   // tốc độ máy bay
    public static final int BULLET_SPEED = 10;  // tốc độ đạn của Player
    public static final int ENEMY_SPEED = 2;    // tốc độ gà con/gà lớn

    // Thời gian delay giữa 2 lần bắn (ms)
    public static final int FIRE_RATE = 300;

    // Kích thước hitbox Player
    public static final int PLAYER_WIDTH = 64;
    public static final int PLAYER_HEIGHT = 64;

    // Kích thước hitbox Enemy
    public static final int ENEMY_WIDTH = 48;
    public static final int ENEMY_HEIGHT = 48;

    // Kích thước hitbox Bullet
    public static final int BULLET_WIDTH = 8;
    public static final int BULLET_HEIGHT = 16;

    // Kích thước đạn của Enemy
    public static final int ENEMY_BULLET_WIDTH = 8;
    public static final int ENEMY_BULLET_HEIGHT = 16;

    // Xác suất rơi item (ví dụ 20%)
    public static final double POWERUP_DROP_RATE = 0.2;
}
