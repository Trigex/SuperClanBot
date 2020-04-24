package moe.trigex.superclanbot.core;

import moe.trigex.superclanbot.catbridge.DiscordListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.ini4j.Wini;
import org.pircbotx.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Configuration {
    private String Name;
    private String RealName;
    private String ServerAddress;
    private String ServerPassword;
    private String[] Channels;
    private String DiscordToken;
    private String DiscordUserID;
    private String IRCBridgedChannel;

    public Configuration(String configPath) throws IOException {
        Wini ini = new Wini(new File(configPath));
        Name = ini.get("irc", "Name", String.class);
        RealName = ini.get("irc", "RealName", String.class);
        ServerAddress = ini.get("irc", "Server", String.class);
        ServerPassword = ini.get("irc", "ServerPassword", String.class);
        Channels = ini.get("irc", "Channels", String.class).split(" ");
        DiscordToken = ini.get("catbridge", "Token", String.class);
        DiscordUserID = ini.get("catbridge", "UserID", String.class);
        IRCBridgedChannel = ini.get("catbridge", "IrcBridgedChannel", String.class);
    }

    public org.pircbotx.Configuration createPircConfiguration(ListenerAdapter listener) {
        return new org.pircbotx.Configuration.Builder()
                .setName(Name)
                .setRealName(RealName)
                .addServer(ServerAddress)
                .addAutoJoinChannels(Arrays.asList(Channels))
                .setServerPassword(ServerPassword)
                .setMessageDelay(0)
                .addListener(listener)
                .buildConfiguration();
    }

    public JDA createDiscordBot(DiscordListener listener) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.CLIENT).addEventListener(listener);
        builder.setToken(getDiscordToken());
        return builder.build();
    }

    public String getName() {
        return Name;
    }
    public String getRealName() {
        return RealName;
    }
    public String getServerAddress() {
        return ServerAddress;
    }
    public String getServerPassword() {
        return ServerPassword;
    }
    public String[] getChannels() { return Channels; }
    public String getDiscordToken() {
        return DiscordToken;
    }
    public String getDiscordUserID() {
        return DiscordUserID;
    }
    public String getIRCBridgedChannel() {
        return IRCBridgedChannel;
    }
}
