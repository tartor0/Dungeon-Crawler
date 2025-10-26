package object;

import com.example.dungeoncrawler.GamePanel;
import entity.Entity;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {

        super(gp);
        type = type_shield;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nBlue shiny shield.";
    }
}
