package moe.trigex.superclanbot;

import moe.trigex.superclanbot.catbridge.DiscordListener;
import moe.trigex.superclanbot.commands.*;
import moe.trigex.superclanbot.core.BotListener;
import moe.trigex.superclanbot.core.Command;
import moe.trigex.superclanbot.core.Configuration;
import moe.trigex.superclanbot.superclanrpg.SuperClanRPG;
import moe.trigex.superclanbot.superclanrpg.commands.Attack;
import moe.trigex.superclanbot.superclanrpg.commands.Status;
import net.dv8tion.jda.core.JDA;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import javax.security.auth.login.LoginException;
import java.io.IOException;

public class SuperClanBot {
    public static Configuration Configuration;
    public static PircBotX IrcBot;
    public static BotListener BotListener;
    public static Command[] Commands;
    public static SuperClanRPG SuperClanRPG = null;
    public static JDA DiscordBot;
    public static DiscordListener DiscordListener;
    public static MultiBotManager MultiBotManager;

    public static void main(String[] args) {
        // Create Configuration from Ini
        try {
            Configuration = new Configuration("config.ini");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Commands
        Commands = new Command[]{
                new Ping(),
                new Help(),
                new Disc0rd(),
                new Goatse(),
                new Attack(),
                new Status(),
                new GodSpeak()};

        // Core listener for bot
        BotListener = new BotListener(Commands);
        // Create bot manager
        MultiBotManager = new MultiBotManager();
        // Create bot from configuration
        IrcBot = new PircBotX(Configuration.createPircConfiguration(BotListener));
        MultiBotManager.addBot(IrcBot);

        // Cat Bridge
        DiscordListener = new DiscordListener();
        try {
            DiscordBot = Configuration.createDiscordBot(DiscordListener);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        // Start bot
        //MultiBotManager.start();
    }
}
