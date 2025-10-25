package com.example.dungeoncrawler;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) continue;

            // Predictive bounding boxes
            int entityLeft = entity.worldX + entity.solidArea.x;
            int entityRight = entityLeft + entity.solidArea.width;
            int entityTop = entity.worldY + entity.solidArea.y;
            int entityBottom = entityTop + entity.solidArea.height;

            int objLeft = gp.obj[i].worldX + gp.obj[i].solidArea.x;
            int objRight = objLeft + gp.obj[i].solidArea.width;
            int objTop = gp.obj[i].worldY + gp.obj[i].solidArea.y;
            int objBottom = objTop + gp.obj[i].solidArea.height;

            switch (entity.direction) {
                case "up": entityTop -= entity.speed; break;
                case "down": entityBottom += entity.speed; break;
                case "left": entityLeft -= entity.speed; break;
                case "right": entityRight += entity.speed; break;
            }

            boolean intersects = entityRight > objLeft && entityLeft < objRight &&
                    entityBottom > objTop && entityTop < objBottom;

            if (intersects) {
                if (gp.obj[i].collision) entity.collisionOn = true;
                if (player) index = i;
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] == null) continue;

            int entityLeft = entity.worldX + entity.solidArea.x;
            int entityRight = entityLeft + entity.solidArea.width;
            int entityTop = entity.worldY + entity.solidArea.y;
            int entityBottom = entityTop + entity.solidArea.height;

            int targetLeft = target[i].worldX + target[i].solidArea.x;
            int targetRight = targetLeft + target[i].solidArea.width;
            int targetTop = target[i].worldY + target[i].solidArea.y;
            int targetBottom = targetTop + target[i].solidArea.height;

            switch (entity.direction) {
                case "up": entityTop -= entity.speed; break;
                case "down": entityBottom += entity.speed; break;
                case "left": entityLeft -= entity.speed; break;
                case "right": entityRight += entity.speed; break;
            }

            boolean intersects = entityRight > targetLeft && entityLeft < targetRight &&
                    entityBottom > targetTop && entityTop < targetBottom;

            if (intersects) {
                if(target[i] != entity) {
                    entity.collisionOn = true;
                    index = i;
                }
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;
        int entityLeft = entity.worldX + entity.solidArea.x;
        int entityRight = entityLeft + entity.solidArea.width;
        int entityTop = entity.worldY + entity.solidArea.y;
        int entityBottom = entityTop + entity.solidArea.height;

        int playerLeft = gp.player.worldX + gp.player.solidArea.x;
        int playerRight = playerLeft + gp.player.solidArea.width;
        int playerTop = gp.player.worldY + gp.player.solidArea.y;
        int playerBottom = playerTop + gp.player.solidArea.height;

        switch (entity.direction) {
            case "up": entityTop -= entity.speed; break;
            case "down": entityBottom += entity.speed; break;
            case "left": entityLeft -= entity.speed; break;
            case "right": entityRight += entity.speed; break;
        }

        boolean intersects = entityRight > playerLeft && entityLeft < playerRight &&
                entityBottom > playerTop && entityTop < playerBottom;

        if (intersects){
            entity.collisionOn = true;
            contactPlayer = true;
        }
        return contactPlayer;
    }
}
