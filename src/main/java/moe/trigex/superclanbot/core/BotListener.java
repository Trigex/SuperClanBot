package moe.trigex.superclanbot.core;
import moe.trigex.superclanbot.SuperClanBot;
import moe.trigex.superclanbot.superclanrpg.Player;
import moe.trigex.superclanbot.superclanrpg.SuperClanRPG;
import moe.trigex.superclanbot.utils.GatchaShit;
import org.apache.commons.lang3.ArrayUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import java.util.Collection;
import net.dv8tion.jda.client.entities.impl.GroupImpl;
import net.dv8tion.jda.core.entities.User;

public class BotListener extends ListenerAdapter {
    private Command[] Commands;

    public BotListener(Command[] commands) {
        super();
        Commands = commands;
    }

    @Override
    public void onMessage(MessageEvent event) {
        // catbridge!!
        if(!IsUserBridgedUser(event.getUser().getNick()) && event.getChannel().getName().equals(SuperClanBot.Configuration.getIRCBridgedChannel())) {
            String msg = "**<" +event.getUser().getNick()+">** " + event.getMessage();
            GroupImpl group = (GroupImpl)SuperClanBot.DiscordListener.getBridgedChannel();

            for(String name : SuperClanBot.DiscordListener.getBridgeNicks().keySet()) {
                msg = msg.replace(name, SuperClanBot.DiscordListener.getBridgeNicks().get(name));
            }

            for(User user : group.getUsers()) {
                msg = msg.replace(user.getName()+"[d]", user.getAsMention()).replace(user.getName().toLowerCase()+"[d]", user.getAsMention());
                msg = msg.replace(user.getName(), user.getAsMention()).replace(user.getName().toLowerCase(), user.getAsMention());

            }

            SuperClanBot.DiscordListener.getBridgedChannel().sendMessage(msg).complete();
        }

        if(SuperClanBot.SuperClanRPG != null) {
            Player p = SuperClanBot.SuperClanRPG.GetPlayer(event.getUser().getNick());
            int preLevel = p.getLevel();
            // 1 in 5 chance of XP on message
            if(GatchaShit.RollRange(1, 5) == 5) {
                // Give 2 to 10 XP
                int xp = GatchaShit.RollRange(2, 10);
                p.AddXp(xp);
                // If xp resulted in level up
                if(p.getLevel() > preLevel) {
                    String nick = event.getUser().getNick();
                    event.getChannel().send().message(nick+" leveled up! "+nick+" is now Level "+p.getLevel());
                }
            }
        }

        // Loop all of the listener's commands
        for(Command command : Commands) {
            // if the message starts with the command's trigger, run the command
            if (event.getMessage().startsWith(command.getTrigger())) {
                // Args is the message without the trigger, so get rid of that
                String[] args = event.getMessage().split(" ");
                args = ArrayUtils.remove(args, 0);
                // Logging
                System.out.println(command.getTrigger()+" invoked. Arguments:");
                if(args.length != 0) {
                    for(String arg : args) {
                        System.out.print(arg+" ");
                    }
                    System.out.println("");
                } else {
                    System.out.println("None");
                }

                // Run the command
                command.Run(event.getChannel(), event.getUser(), args);
                break;
            }
        }
    }

    @Override
    public void onJoin(JoinEvent event) {
        // Check if we've joined to all configured channels
        if(SuperClanBot.IrcBot.getUserBot().getChannels().size() == SuperClanBot.Configuration.getChannels().length && SuperClanBot.SuperClanRPG == null) {
            // When in all channels, init SuperClanRPG!
            SuperClanBot.SuperClanRPG = new SuperClanRPG();
        }
    }

    private boolean IsUserBridgedUser(String nick) {
        boolean isBridged = false;
        Collection<PircBotX> bots = SuperClanBot.DiscordListener.getBridgedUsers().values();
        for(PircBotX bot : bots) {
            if(bot.getUserBot().getNick().equals(nick)) {
                isBridged = true;
                break;
            }
        }
        return isBridged;
    }
}
