package moe.trigex.superclanbot.commands;

import moe.trigex.superclanbot.core.Command;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class Ping extends Command {
    public Ping() {
        super("!ping",
                "Ping",
                "A simple command that spits back pong on invocation",
                new String[]{"!ping"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        fromChannel.send().message("pong");
    }
}
