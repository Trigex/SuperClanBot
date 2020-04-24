package moe.trigex.superclanbot.commands;

import moe.trigex.superclanbot.SuperClanBot;
import moe.trigex.superclanbot.core.Command;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class Help extends Command {
    public Help() {
        super("!help",
                "Help",
                "Give helpful information documenting commands",
                new String[]{"!help <command> - Get help on a specific command", "!help - List all commands"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        if(args.length == 0) { // !help
            // List out all commands
            for(Command command : SuperClanBot.Commands) {
                String commandText = command.getTitle()+" - "+command.getDescription()+" (Send \""+getTrigger()+" "+command.getTitle()+"\" for more information)";
                fromChannel.send().message(commandText);
            }
        } else { // !help <command>
            // Get target command from argument
            String commandArg = args[0];
            boolean commandFound = false;
            for(Command command : SuperClanBot.Commands) {
                // Matches argument
                if(command.getTrigger().equals(commandArg) || command.getTitle().equals(commandArg)) {
                    // Base description
                    String commandText = command.getTitle()+" - "+command.getDescription();
                    fromChannel.send().message(commandText);
                    // Send usage examples
                    fromChannel.send().message("Usage:");
                    for(String usage : command.getUsage()) {
                        fromChannel.send().message(usage);
                    }
                    commandFound = true;
                    break;
                }
            }

            // No command was found
            if(!commandFound) {
                fromChannel.send().message("No command \""+commandArg+"\" was found. Use !help for a list of all commands");
            }
        }
    }
}
