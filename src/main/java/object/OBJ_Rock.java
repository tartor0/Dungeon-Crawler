package object;

import com.example.dungeoncrawler.GamePanel;
import entity.Projectile;

import java.awt.*;

public class OBJ_Rock extends Projectile {

    GamePanel gp;
    public OBJ_Rock(GamePanel gp) {

        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage() {
        up1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectiles/rock_down_1", gp.tileSize, gp.tileSize);
    }
    public Color getParticleColor() {
        Color color = new Color(65, 50, 30);
        return  color;
    }
    public int getParticleSize() {
        int size = 10;
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
