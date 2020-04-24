package moe.trigex.superclanbot.commands;

import moe.trigex.superclanbot.core.Command;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pircbotx.Channel;
import org.pircbotx.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Random;

public class Disc0rd extends Command {
    private final String VIDEOS_URL = "https://www.disc0rd.xyz/videos/";
    private final String JSON_URL = "https://www.disc0rd.xyz/json.html";

    public Disc0rd() {
        super("!disc0rd",
                "Disc0rd",
                "Grab a random video from disc0rd.xyz",
                new String[]{"!disc0rd - Get a video", "!disc0rd <keyword> - Get a video with a title containing <keyword>"});
    }

    public void Run(Channel fromChannel, User fromUser, String[] args) {
        try {
            if(args.length == 0) { // !disc0rd
                fromChannel.send().message(VIDEOS_URL+URLEncoder.encode(GetRandomVideo(), "UTF-8"));
            } else { // !disc0rd <keyword>
                String keyword = args[0];
                String video = GetVideoFromKeyword(keyword);
                if(video == null) {
                    fromChannel.send().message("Unable to find a video with keyword \""+keyword+"\"");
                } else {
                    fromChannel.send().message(VIDEOS_URL+URLEncoder.encode(video, "UTF-8"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String GetRandomVideo() throws IOException {
        // Get teh array
        JSONArray arr = GetVideoArray();
        // Get random index
        int rand = new Random().nextInt(arr.length());
        // Select array element from random index
        String video = arr.get(rand).toString();
        // return final video
        return video;
    }

    private String GetVideoFromKeyword(String keyword) throws IOException {
        // Get teh array
        JSONArray arr = GetVideoArray();
        String video = null;
        for (Iterator<Object> it = arr.iterator(); it.hasNext(); ) {
            String str = it.next().toString();
            if(str.toLowerCase().contains(keyword)) {
                video = str;
                break;
            }
        }

        return video;
    }

    private JSONArray GetVideoArray() throws IOException {
        // Get json
        String json = GetDisc0rdJson();
        // Read into object
        JSONObject obj = new JSONObject(json);
        // Return files array
        return obj.getJSONArray("files");
    }

    private String GetDisc0rdJson() throws IOException {
        // Open connection
        URL url = new URL(JSON_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);

        // Read data into string
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // Return final json
        return content.toString();
    }
}
