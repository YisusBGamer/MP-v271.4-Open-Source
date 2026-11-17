package net.swordie.ms.loaders;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.swordie.ms.ServerConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 24/06/2021.
 *
 * Used for Swordie forbidden words.
 * These names will be banned from usage in features such as Megaphones/Chat/Character Creation
 *
 * This class is used for reading this forbidden words list.
 *
 * @author Asura
 */
public class ForbiddenWordsData {

    private static final Logger log = LogManager.getLogger(ForbiddenWordsData.class);

    private final static List<String> forbiddenWordsChat = new ArrayList<>(); // words that are forbidden in chat usages
    private final static List<String> forbiddenWordsIGN = new ArrayList<>(); // words that are forbidden as IGNs.

    static {
        loadForbiddenWords();
    }

    public static boolean isForbiddenChat(String chat) {
        if (forbiddenWordsChat.size() <= 0) {
            return false;
        }

        // Preparing chat for pattern matching
        //
        var sanitisedChat = chat.replaceAll("[^a-zA-Z0-9#@$ ]", "").replace(" ", ""); // remove most special characters & spaces
        //


        // Building pattern from 'forbiddenWords'
        //
        var sb = new StringBuilder("(");
        var i = 0;
        for (var fw : forbiddenWordsChat) {
            sb.append(fw);

            if (i < forbiddenWordsChat.size() - 1) {
                sb.append("|");
            }

            i++;
        }
        sb.append(")");
        var regex = sb.toString();
        //


        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sanitisedChat);

        return m.find();
    }

    public static boolean isForbiddenIGN(String name) {
        if (forbiddenWordsIGN.size() <= 0) {
            return false;
        }

        return forbiddenWordsIGN.stream().anyMatch(fw -> fw.equalsIgnoreCase(name));
    }

    private static void loadForbiddenWords() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(ServerConstants.LOADINS_DIR + "/ForbiddenWords.json")) {
            JSONObject obj = (JSONObject) parser.parse(reader);

            // Chat
            var chatObj = (JSONArray) obj.get("chat");
            chatObj.forEach(fw -> forbiddenWordsChat.add((String) fw));

            // IGN
            var ignObj = (JSONArray) obj.get("ign");
            ignObj.forEach(fw -> forbiddenWordsIGN.add((String) fw));

        } catch (IOException | ParseException e) {
            log.error("ForbiddenWordsData.loadForbiddenWords", e);
        }

    }
}
