package moe.trigex.superclanbot.superclanrpg;

import moe.trigex.superclanbot.utils.GatchaShit;

import java.util.ArrayList;
import java.util.Random;

public abstract class Weapon {
    // Name of the weapon
    private String Name;
    private String IdName;
    // The amount of dice it rolls for damage in battle (eg, 9 = 9d6 rolled)
    private int Dice;
    // The cost of the weapon in the shop
    private int Cost;
    private Rarity Rarity;

    public Weapon(String name, int dice, int cost, String idName, Rarity rarity) {
        Name = name;
        Dice = dice;
        Cost = cost;
        IdName = idName;
        Rarity = rarity;
    }

    public int Use() {
        // Roll dice
        int total = 0;
        for(int i=0;i<Dice;i++) {
            int roll = GatchaShit.RollRange(1, 6);
            total+=roll;
        }

        return total;
    }

    public abstract String GetAttackText(String attackingNick, String victimNick, int damage);

    public int getCost() {
        return Cost;
    }
    public int getDice() {
        return Dice;
    }
    public String getName() {
        return Name;
    }

    public String getIdName() {
        return IdName;
    }
}
