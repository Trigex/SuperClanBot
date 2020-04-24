package moe.trigex.superclanbot.superclanrpg.weapons;

import moe.trigex.superclanbot.superclanrpg.Weapon;
import moe.trigex.superclanbot.superclanrpg.Rarity;
import moe.trigex.superclanbot.utils.Color;

public class GigaDrill extends Weapon {
    public GigaDrill() {
        super("Giga Drill", 15, 1000, "gigadrill", Rarity.SuperRare);
    }

    public String GetAttackText(String attackingNick, String victimNick, int damage) {
        return attackingNick+" "+Color.BLACK+"engaged"+Color.RESET+" his "+getName()+" into "+victimNick+"'s chest!!";
    }
}
