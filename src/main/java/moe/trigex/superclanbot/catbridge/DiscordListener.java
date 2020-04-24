package moe.trigex.superclanbot.catbridge;

import moe.trigex.superclanbot.SuperClanBot;
import net.dv8tion.jda.client.entities.impl.GroupImpl;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.pircbotx.PircBotX;

import java.util.HashMap;

public class DiscordListener extends ListenerAdapter {
    private MessageChannel BridgedChannel;
    private HashMap<String, PircBotX> BridgedUsers;
    private HashMap<String, String> BridgeNicks;

    public DiscordListener() {
        BridgedUsers = new HashMap<String, PircBotX>();
        BridgeNicks = new HashMap<String, String>();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(BridgedChannel == null) {
            BridgedChannel = event.getChannel();
        }

        if(BridgedUsers.isEmpty()) {
            initBridgedUsers();
        }

        if(!event.getAuthor().getId().equals(SuperClanBot.Configuration.getDiscordUserID())) {
            String msg = event.getMessage().getContentDisplay();
            String id = event.getAuthor().getId();

            for(Message.Attachment attachment : event.getMessage().getAttachments()) {
                msg = msg + " "+attachment.getUrl();
            }

            // handle multiline messages
            String lines[] = msg.split("\\r?\\n");
            for(String line : lines) {
                BridgedUsers.get(id).getUserBot().getChannels().first().send().message(line);
            }
        }
    }

    private void initBridgedUsers() {
        GroupImpl group = (GroupImpl)BridgedChannel;
        for(int i=0; i < group.getUsers().size(); i++) {
            if(!group.getUsers().get(i).getId().equals(SuperClanBot.Configuration.getDiscordUserID())) {
                String nick = group.getUsers().get(i).getName().replaceAll(" ","").replaceAll("\\.", "").replaceAll(",","");
                // Configure user
                org.pircbotx.Configuration config = new org.pircbotx.Configuration.Builder()
                        .setName(nick+"[d]")
                        .setRealName(nick)
                        .addServer(SuperClanBot.Configuration.getServerAddress())
                        .addAutoJoinChannel(SuperClanBot.Configuration.getIRCBridgedChannel())
                        .setServerPassword(SuperClanBot.Configuration.getServerPassword())
                        .setMessageDelay(0)
                        .buildConfiguration();

                // Create new bot
                PircBotX bot = new PircBotX(config);
                BridgedUsers.put(group.getUsers().get(i).getId(), bot);
                // Map irc nick to discord nick
                BridgeNicks.put(nick, group.getUsers().get(i).getName());
                SuperClanBot.MultiBotManager.addBot(bot);
            }
        }

        SuperClanBot.MultiBotManager.start();
    }

    public MessageChannel getBridgedChannel() {
        return BridgedChannel;
    }

    public HashMap<String, PircBotX> getBridgedUsers() {
        return BridgedUsers;
    }

    public HashMap<String, String> getBridgeNicks() {
        return BridgeNicks;
    }
}
