package entity;

import com.example.dungeoncrawler.GamePanel;
import com.example.dungeoncrawler.KeyHandler;
import com.example.dungeoncrawler.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    boolean moving = false;
    int pixelCounter = 0;
    int standCounter = 0;

    public Player (GamePanel gp, KeyHandler keyH){

        super(gp);
        this.keyH = keyH;

        //returns center of screen
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttack();


    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttack() {
        attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
    }

    public void update() {

        if (attacking) {
            attacking();
            return;
        }

        // 🔵 Movement input
        if (!moving) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) direction = "up";
                else if (keyH.downPressed) direction = "down";
                else if (keyH.leftPressed) direction = "left";
                else if (keyH.rightPressed) direction = "right";

                moving = true;
                pixelCounter = 0;
            }
            else if (keyH.enterPressed) {
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
                moving = false;
            }
            else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }

        // 🔵 Movement logic
        if (moving) {
            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

            pixelCounter += speed;
            if (pixelCounter >= gp.tileSize) {
                moving = false;
                pixelCounter = 0;
            }
        }

        gp.eHandler.checkEvent();

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        gp.keyH.enterPressed = false;
    }




    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // Save current world and solid area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Shift temporarily for attack area
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check collision with monster
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            // ✅ Restore original position and solid area
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {

        if(i != 999){

        }
    }

    public void interactNPC( int i) {

        if(gp.keyH.enterPressed == true){
            if(i != 999){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }else {
                gp.playSE(7);
                attacking = true;
            }
        }

    }

    public void contactMonster(int i) {

        if(i != 999){

            if(invincible == false) {
                gp.playSE(6);
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {

        if(i != 999){
            if(gp.monster[i].invincible == false) {

                gp.playSE(5);
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReact();

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY= screenY;

        switch(direction){
            case "up" :
                if(attacking == false) {
                    if(spriteNum == 1){ image = up1;}
                    if(spriteNum == 2){ image = up2;}
                }
                if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){ image = attackUp1;}
                    if(spriteNum == 2){ image = attackUp2;}
                }
                break;
            case "down" :
                if (attacking == false) {
                    if(spriteNum == 1){ image = down1;}
                    if(spriteNum == 2){ image = down2;}
                }
                if (attacking == true) {
                    if(spriteNum == 1){ image = attackDown1;}
                    if(spriteNum == 2){ image = attackDown2;}
                }
                break;
            case "left" :
                if(attacking == false) {
                    if(spriteNum == 1){ image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){ image = attackLeft1;}
                    if(spriteNum == 2){ image = attackLeft2;}
                }
                break;
            case "right" :
                if(attacking == false){
                   if(spriteNum == 1){ image = right1;}
                   if(spriteNum == 2){ image = right2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){ image = attackRight1;}
                    if(spriteNum == 2){ image = attackRight2;}
                }
                break;
        }

        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }


}
