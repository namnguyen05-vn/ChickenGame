 package game;
 import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        // Tạo Player
        Player player = new Player(100, 100);

        // Tạo Enemy
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(100, 100, 1)); // gà con: 1 máu
        enemies.add(new Enemy(200, 100, 2)); // gà lớn: 2 máu

        // Tạo Bullet (Player bắn)
        List<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet(100, 100)); // bắn trúng gà con
        bullets.add(new Bullet(200, 100)); // bắn trúng gà lớn

        // Tạo EnemyBullet
        List<EnemyBullet> enemyBullets = new ArrayList<>();
        enemyBullets.add(new EnemyBullet(100, 100)); // trúng Player

        // Danh sách PowerUp
        List<PowerUp> powerUps = new ArrayList<>();

        // Test va chạm Bullet ↔ Enemy
        CollisionManager.checkBulletEnemyCollision(bullets, enemies, powerUps);

        // Test va chạm EnemyBullet ↔ Player
        CollisionManager.checkEnemyBulletPlayerCollision(enemyBullets, player);

        // Test va chạm Enemy ↔ Player
        CollisionManager.checkEnemyPlayerCollision(enemies, player);

        System.out.println("So PowerUp roi ra: " + powerUps.size());
    }
}
