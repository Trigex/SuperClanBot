package moe.trigex.superclanbot.commands;

import moe.trigex.superclanbot.core.Command;
import org.json.JSONArray;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class GodSpeak extends Command {
    private final int GODSPEAK_WORD_CAP = 1000;
    public GodSpeak() {
        super("!godspeak",
                "Godspeak",
                "Speak to Mr. God!",
                new String[]{"!godspeak", "!godspeak <number> - Allow Mr. God to speak any number of words to you"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        Scanner s = null;
        String words = "";
        String godSong = "";
        try {
            s = new Scanner(new File("words.json"));
            s.useDelimiter("\\Z");
            words = s.next();
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONArray arr = new JSONArray(words);

        if(args.length == 0) { // !godspeak
            godSong = generateGodSpeak(arr, 32);
        } else { // !godspeak <number>
            int num = 0;
            try {
                num = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                fromChannel.send().message("Please supply a valid number");
                return;
            }
            if(num > GODSPEAK_WORD_CAP) {
                fromChannel.send().message("That's too many words, give Mr. God a break!");
                return;
            }
            godSong = generateGodSpeak(arr, num);
        }

        // Strip space from start of godSong
        fromChannel.send().message(godSong);
    }

    private String generateGodSpeak(JSONArray arr, int num) {
        String godSong = "";
        Random rng = new Random();
        for(int i = 0; i < num; i++) {
            int index =rng.nextInt(arr.length());
            godSong = godSong+" "+arr.get(index).toString();
        }
        godSong = godSong.substring(1);
        return godSong;
    }
}
