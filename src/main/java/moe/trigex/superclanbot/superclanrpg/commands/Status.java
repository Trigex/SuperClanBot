package moe.trigex.superclanbot.superclanrpg.commands;

import moe.trigex.superclanbot.SuperClanBot;
import moe.trigex.superclanbot.core.Command;
import moe.trigex.superclanbot.superclanrpg.Player;
import moe.trigex.superclanbot.superclanrpg.Weapon;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class Status extends Command {
    public Status() {
        super("!status",
                "Status",
                "[SuperClanRPG] Get your player status",
                new String[]{"!status", "!status <nick>"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        // Get the Player
        Player p;
        if(args.length == 0) { // !status
            p = SuperClanBot.SuperClanRPG.GetPlayer(fromUser.getNick());
        } else { // !status <nick>
            p = SuperClanBot.SuperClanRPG.GetPlayer(args[0]);
        }

        if(p != null) {
            fromChannel.send().message(p.getUser().getNick()+" - Level " + p.getLevel());
            fromChannel.send().message("Base Health: "+p.getHealth()+", Current Health: "+p.getCurrentHealth());
            fromChannel.send().message("Adds: "+p.getAdds());
            fromChannel.send().message("Money: " + p.getMoney());
            fromChannel.send().message("Xp: "+p.getXp()+"/"+(p.getLevel()*200)/10);
            String weapons = "";
            for(Weapon w : p.getWeapons()) {
                weapons = weapons.concat(w.getName()+" (Dice: "+w.getDice()+"d6, "+"Cost: "+w.getCost()+", ID: "+w.getIdName()+") ");
            }
            fromChannel.send().message("Weapons:");
            fromChannel.send().message(weapons);
        }
    }
}
