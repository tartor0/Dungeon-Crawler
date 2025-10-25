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

        setDefaultValues();
        getPlayerImage();


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
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
    }

    public void update() {

        // ====== START MOVEMENT INPUT ======
        if (!moving) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

                if (keyH.upPressed) direction = "up";
                else if (keyH.downPressed) direction = "down";
                else if (keyH.leftPressed) direction = "left";
                else if (keyH.rightPressed) direction = "right";

                // Start movement
                moving = true;
                pixelCounter = 0;
            } else {
                // Idle animation
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }

        // ====== DURING MOVEMENT ======
        if (moving) {

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);


            // Stop if collision detected
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Animate movement
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

            // Move 48 pixels total before stopping
            pixelCounter += speed;
            if (pixelCounter >= gp.tileSize) {
                moving = false;
                pixelCounter = 0;
            }

            // Check event triggers each frame
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;
        }

        //this needs to be outside of key if statement
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void pickUpObject(int i) {

        if(i != 999){

        }
    }

    public void interactNPC( int i) {
        if(i != 999){
            System.out.println("you are hitting npc");

            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {

        if(i != 999){

            if(invincible == false) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {

//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up" :
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down" :
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left" :
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right" :
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }

        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }


}
