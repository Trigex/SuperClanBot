package moe.trigex.superclanbot.superclanrpg.commands;

import moe.trigex.superclanbot.SuperClanBot;
import moe.trigex.superclanbot.core.Command;
import moe.trigex.superclanbot.superclanrpg.Player;
import moe.trigex.superclanbot.superclanrpg.Weapon;
import moe.trigex.superclanbot.utils.Color;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class Attack extends Command {
    public Attack() {
        super("!attack",
                "Attack",
                "[SuperClanRPG] Attack a user in SuperClanRPG, violently!",
                new String[]{"!attack <nick> - Attack a user with your default weapon",
                            "!attack <nick> <weapon> - Attack a user with <weapon>"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        if(args.length != 0) {
            Player victim = SuperClanBot.SuperClanRPG.GetPlayer(args[0]);
            Player attacking = SuperClanBot.SuperClanRPG.GetPlayer(fromUser.getNick());
            String victimNick = victim.getUser().getNick();
            String attackingNick = attacking.getUser().getNick();
            if(victim.getCurrentHealth() <= 0) {
                fromChannel.send().message(victimNick+" is already dead asshole");
                return;
            }

            if(attacking.getCurrentHealth() <= 0) {
                fromChannel.send().message("You're dead retard you're not a zombie go fuck yourself");
                return;
            }

            Weapon weapon = null;
            int dmg = 0;

            switch(args.length) {
                case 1: // !attack <nick>
                    weapon = attacking.getDefaultWeapon();
                    dmg = weapon.Use()+attacking.getAdds();
                    victim.TakeDamage(dmg);
                    break;
                case 2: // !attack <nick> <weapon>
                    String wId = args[1];
                    weapon = attacking.getWeapon(wId);
                    if(weapon == null) {
                        fromChannel.send().message("No such weapon \""+wId+"\" was found!");
                        return;
                    }

                    dmg = weapon.Use()+attacking.getAdds();
                    victim.TakeDamage(dmg);
                    break;
            }
            String attackText = weapon.GetAttackText(attackingNick, victimNick, dmg)+" "+dmg+" damage is dealt!";
            if(victim.getCurrentHealth() <= 0) {
                fromChannel.send().message(attackText+" "+victimNick+Color.RED+" fucking died!!!");
            } else {
                fromChannel.send().message(attackText+" "+victimNick+" is left with "+victim.getCurrentHealth()+" health!");
            }
        }
    }
}
