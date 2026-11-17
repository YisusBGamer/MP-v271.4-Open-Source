package net.swordie.ms.socialbot;

import java.util.LinkedHashMap;
import java.util.Map;

/** The bot extension isn't part of this source; the admin bot controls report that. */
public final class SocialBotHooks {

    private SocialBotHooks() {
    }

    public static Map<String, Object> status() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("installed", false);
        return res;
    }

    public static String controlAction(String action) {
        return "Bots aren't part of this server.";
    }
}
