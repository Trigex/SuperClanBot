package moe.trigex.superclanbot.superclanrpg;

import moe.trigex.superclanbot.SuperClanBot;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.util.ArrayList;


public class SuperClanRPG {
    private ArrayList<Player> Players;
    private String[] UserBlacklist = {"superclanbot", "disc0rd", "clanbot", "catBridge"};

    public SuperClanRPG() {
        System.out.println("Initializing SuperClanRPG...");
        Players = new ArrayList<Player>();
        // Create a player for every user in channel the bot is in
        for(Channel channel : SuperClanBot.IrcBot.getUserBot().getChannels()) {
            for(User user : channel.getUsers()) {
                // If the given user isn't already in the list, add them
                if(!IsUserInPlayers(user.getNick())) {
                    // Ensure the user isn't in the blacklist
                    if(!IsUserInBlacklist(user.getNick())) {
                        Player p = new Player(user);
                        Players.add(p);
                        System.out.println("New User "+user.getNick()+" in "+channel.getName()+" with "+p.getHealth()+" health");
                    }
                }
            }
        }
    }

    public Player GetPlayer(String nick) {
        for(Player player : Players) {
            if(player.getUser().getNick().equals(nick)) {
                return player;
            }
        }
        return null;
    }

    private boolean IsUserInPlayers(String nick) {
        for(Player player : Players) {
            if(player.getUser().getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    private boolean IsUserInBlacklist(String nick) {
        for(String blacklistNick : UserBlacklist) {
            if(blacklistNick.equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public Player[] getPlayers() {
        return (Player[])Players.toArray();
    }
}
