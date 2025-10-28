package tiles_interactive;

import com.example.dungeoncrawler.GamePanel;
import entity.Entity;

import java.awt.*;

public class IT_DryTree extends InteractiveTile{
    GamePanel gp;
    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp , col, row);
        this.gp = gp;

        life =3;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
        destructible = true;

    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectItem = true;
        }
        return  isCorrectItem;
    }

    public void playSE() {
        gp.playSE(11);
    };

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
        return  color;
    }
    public int getParticleSize() {
        int size =6;
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
