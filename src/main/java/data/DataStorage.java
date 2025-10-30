package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable { // serializable allows you to read and write the files or smth like that

    //PLAYER STATS
    public int level;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int strength;
    public int dexterity;
    public int exp;
    public int nextLevelExp;
    public int coin;

    //INVENTORY
    public ArrayList<String> itemName = new ArrayList<>();
//    ArrayList<Integer> itemAmounts = new ArrayList<>();
}
