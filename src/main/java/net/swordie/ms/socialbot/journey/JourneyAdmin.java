package net.swordie.ms.socialbot.journey;

import net.swordie.ms.client.character.Char;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** The bot extension isn't part of this source: no bots to list or control. */
public final class JourneyAdmin {

    private JourneyAdmin() {
    }

    public static String kindOf(Char chr) {
        return null;
    }

    public static Map<String, Object> list() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("bots", java.util.Collections.emptyList());
        return res;
    }

    public static String act(String name, String action, String by) {
        return "Bots aren't part of this server.";
    }

    public static void pauseAll(boolean pause) throws IOException {
    }
}
