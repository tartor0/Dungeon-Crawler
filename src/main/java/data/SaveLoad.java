package data;

import com.example.dungeoncrawler.GamePanel;
import entity.Entity;
import object.*;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }
    public Entity getObject (String itemName){
        Entity obj = null;
        switch (itemName) {
            case "Woodcutter's Axe" : obj = new OBJ_Axe(gp); break;
            case "Red Potion": obj = new OBJ_Potion_Red(gp); break;
            case "Blue Shield": obj = new OBJ_Shield_Blue(gp); break;
            case "Shield": obj = new OBJ_Shield_Wood(gp); break;
            case "Normal Sword": obj = new OBJ_Sword_Normal(gp); break;
        }
        return obj;
    }

    public void save() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            //PLAYER STATS
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            //PLAYER INVENTORY
            for(int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemName.add(gp.player.inventory.get(i).name);
//                ds.itemAmounts.add(gp.player.inventory.get(i).);
            }

            //WRITE THE DATA STORAGE OBJECT
            oos.writeObject(ds);

        }
        catch(Exception e) {
            System.out.println("Save Exception!");
        }
    }

    public void load() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //READ THE DATA STORAGE OBJECT
            DataStorage ds = (DataStorage)ois.readObject();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            gp.player.inventory.clear();
            for (String itemName : ds.itemName) {
                Entity obj = getObject(itemName);
                if (obj != null) {
                    gp.player.inventory.add(obj);
                } else {
                    System.out.println("⚠️ Unknown or null item skipped: " + itemName);
                }
            }


        }
        catch(Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
