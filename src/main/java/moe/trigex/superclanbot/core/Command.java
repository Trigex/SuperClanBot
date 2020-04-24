package moe.trigex.superclanbot.core;

import org.pircbotx.Channel;
import org.pircbotx.User;

public abstract class Command {
    private String Trigger;
    private String Title;
    private String Description;
    private String[] Usage;

    public Command(String trigger, String title, String description, String[] usage) {
        Trigger = trigger;
        Title = title;
        Description = description;
        Usage = usage;
    }

    // Abstract method ran when the trigger is detected in chat
    public abstract void Run(Channel fromChannel, User fromUser, String[] args);

    public String getTrigger() {
        return Trigger;
    }
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public String[] getUsage() {
        return Usage;
    }
}
