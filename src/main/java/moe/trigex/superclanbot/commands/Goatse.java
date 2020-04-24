package moe.trigex.superclanbot.commands;

import moe.trigex.superclanbot.core.Command;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class Goatse extends Command {
    private final String GOATSE_URL = "http://goatse.ru/hello.jpg";
    public Goatse() {
        super("!goatse",
                "Goatse",
                "Politely give the chat a good ol' look at Goatse",
                new String[]{"!goatse"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        fromChannel.send().message(GOATSE_URL);
    }
}
