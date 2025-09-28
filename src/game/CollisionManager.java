// File: CollisionManager.java
// Tuần 2 - Người 5 (Logic & Collision)
// Hoàn thiện va chạm: Bullet ↔ Enemy, EnemyBullet ↔ Player, Enemy ↔ Player
// Thêm cơ chế Enemy chết thì biến mất, rơi item, xóa Enemy ra khỏi màn hình.
package game;
import java.awt.Rectangle;
import java.util.List;
import java.util.Iterator;

public class CollisionManager {

    // Hàm kiểm tra va chạm giữa 2 đối tượng (dùng hitbox Rectangle)
    public static boolean isColliding(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    // ===========================================================
    // 1. Va chạm Bullet ↔ Enemy
    // ===========================================================
    public static void checkBulletEnemyCollision(List<Bullet> bullets, List<Enemy> enemies, List<PowerUp> powerUps) {
        Iterator<Bullet> bulletIter = bullets.iterator();

        while (bulletIter.hasNext()) {
            Bullet b = bulletIter.next();

            Iterator<Enemy> enemyIter = enemies.iterator();
            while (enemyIter.hasNext()) {
                Enemy e = enemyIter.next();

                if (isColliding(b.getBounds(), e.getBounds())) {
                    // Đạn trúng Enemy
                    b.setActive(false);  // hủy viên đạn
                    e.takeDamage(1);     // Enemy mất máu (gà con: 1 máu, gà lớn: 2 máu, Boss nhiều máu)

                    // Nếu Enemy chết thì xóa khỏi danh sách
                    if (!e.isAlive()) {
                        enemyIter.remove();

                        // Xác suất rơi item (ví dụ 20%)
                        if (Math.random() < Constants.POWERUP_DROP_RATE) {
                            powerUps.add(new PowerUp(e.getX(), e.getY()));
                        }
                    }
                }
            }
        }
    }

    // ===========================================================
    // 2. Va chạm EnemyBullet ↔ Player
    // ===========================================================
    public static void checkEnemyBulletPlayerCollision(List<EnemyBullet> enemyBullets, Player player) {
        Iterator<EnemyBullet> iter = enemyBullets.iterator();

        while (iter.hasNext()) {
            EnemyBullet eb = iter.next();

            if (isColliding(eb.getBounds(), player.getBounds())) {
                iter.remove();         // Xóa viên đạn
                player.loseLife();     // Player mất mạng
            }
        }
    }

    // ===========================================================
    // 3. Va chạm Enemy ↔ Player
    // ===========================================================
    public static void checkEnemyPlayerCollision(List<Enemy> enemies, Player player) {
        for (Enemy e : enemies) {
            if (isColliding(e.getBounds(), player.getBounds())) {
                player.loseLife();  // Player mất mạng khi va vào Enemy
            }
        }
    }

    // ===========================================================
    // 4. Xóa Enemy ra ngoài màn hình (không để rác trên RAM)
    // ===========================================================
    public static void removeOffscreenEnemies(List<Enemy> enemies) {
        enemies.removeIf(e -> e.getY() > Constants.SCREEN_HEIGHT);
    }
}
