package moe.trigex.superclanbot.superclanrpg.weapons;

import moe.trigex.superclanbot.superclanrpg.Weapon;
import moe.trigex.superclanbot.superclanrpg.Rarity;
import moe.trigex.superclanbot.utils.Color;

public class CommonSword extends Weapon {
    public CommonSword() {
        super("Common Sword", 7, 10, "commonsword", Rarity.Common);
    }

    public String GetAttackText(String attackingNick, String victimNick, int damage) {
        return attackingNick+" "+ Color.MAGENTA+"flailed"+Color.RESET+" his "+getName()+" towards "+victimNick+"!";
    }
}
