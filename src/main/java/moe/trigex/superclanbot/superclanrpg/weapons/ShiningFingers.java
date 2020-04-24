package moe.trigex.superclanbot.superclanrpg.weapons;

import moe.trigex.superclanbot.superclanrpg.Weapon;
import moe.trigex.superclanbot.superclanrpg.Rarity;
import moe.trigex.superclanbot.utils.Color;

public class ShiningFingers extends Weapon {
    public ShiningFingers() {
        super("Shining Finger", 20, 10000, "shiningfingers", Rarity.SuperFuckingRare);
    }

    public String GetAttackText(String attackingNick, String victimNick, int damage) {
        return attackingNick+" fucking"+Color.YELLOW+" zapped "+Color.RESET+victimNick+" with "+getName()+"!";
    }
}
