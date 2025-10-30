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
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) continue;

            // Predictive bounding boxes
            int entityLeft = entity.worldX + entity.solidArea.x;
            int entityRight = entityLeft + entity.solidArea.width;
            int entityTop = entity.worldY + entity.solidArea.y;
            int entityBottom = entityTop + entity.solidArea.height;

            int objLeft = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
            int objRight = objLeft + gp.obj[gp.currentMap][i].solidArea.width;
            int objTop = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
            int objBottom = objTop + gp.obj[gp.currentMap][i].solidArea.height;

            switch (entity.direction) {
                case "up": entityTop -= entity.speed; break;
                case "down": entityBottom += entity.speed; break;
                case "left": entityLeft -= entity.speed; break;
                case "right": entityRight += entity.speed; break;
            }

            boolean intersects = entityRight > objLeft && entityLeft < objRight &&
                    entityBottom > objTop && entityTop < objBottom;

            if (intersects) {
                if (gp.obj[gp.currentMap][i].collision) entity.collisionOn = true;
                if (player) index = i;
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] [] target) {
        int index = 999;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] == null) continue;

            int entityLeft = entity.worldX + entity.solidArea.x;
            int entityRight = entityLeft + entity.solidArea.width;
            int entityTop = entity.worldY + entity.solidArea.y;
            int entityBottom = entityTop + entity.solidArea.height;

            int targetLeft = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
            int targetRight = targetLeft + target[gp.currentMap][i].solidArea.width;
            int targetTop = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;
            int targetBottom = targetTop + target[gp.currentMap][i].solidArea.height;

            switch (entity.direction) {
                case "up": entityTop -= entity.speed; break;
                case "down": entityBottom += entity.speed; break;
                case "left": entityLeft -= entity.speed; break;
                case "right": entityRight += entity.speed; break;
            }

            boolean intersects = entityRight > targetLeft && entityLeft < targetRight &&
                    entityBottom > targetTop && entityTop < targetBottom;

            if (intersects) {
                if(target[gp.currentMap][i] != entity) {
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
