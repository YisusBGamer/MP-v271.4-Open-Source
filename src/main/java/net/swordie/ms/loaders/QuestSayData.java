package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The official quest dialogue (Quest.wz/Say.img) of quests whose start or end script doesn't exist. The client shows
 * Say.img itself only for quests without a script; for a quest that names a script that was never written, the
 * fallback script (quest/undefined.py) plays these lines instead, so the story isn't skipped.
 * <p>
 * Loaded on first use from dat/questSay.txt (generated from Quest.wz): one line per dialogue line,
 * <code>quest, part (0 start / 1 end), branch (say / yes / no), index, userSay, npcId, text</code>, tab separated,
 * with "\n" for line breaks.
 */
public final class QuestSayData {

    private static final Logger log = LogManager.getLogger(QuestSayData.class);
    private static final String FILE = ServerConstants.DAT_DIR + "/questSay.txt";

    private static volatile Map<String, List<Line>> lines;

    private QuestSayData() {
    }

    /** One line of dialogue. */
    public static final class Line {
        private final String text;
        private final boolean userSay;
        private final int npcId;

        private Line(String text, boolean userSay, int npcId) {
            this.text = text;
            this.userSay = userSay;
            this.npcId = npcId;
        }

        public String getText() {
            return text;
        }

        /** @return whether the player says this line */
        public boolean isUserSay() {
            return userSay;
        }

        /** @return the NPC that says this line, or 0 for the quest's NPC */
        public int getNpcId() {
            return npcId;
        }
    }

    private static String key(int questId, int part, String branch) {
        return questId + "/" + part + "/" + branch;
    }

    private static Map<String, List<Line>> load() {
        Map<String, List<Line>> map = new HashMap<>();
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) {
            log.warn("No " + FILE + ": quests without a script won't show their dialogue.");
            return map;
        }
        try {
            for (String s : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                if (s.isEmpty() || s.startsWith("#")) {
                    continue;
                }
                String[] f = s.split("\t", 7);
                if (f.length < 7) {
                    continue;
                }
                try {
                    String k = key(Integer.parseInt(f[0]), Integer.parseInt(f[1]), f[2]);
                    map.computeIfAbsent(k, x -> new ArrayList<>()).add(new Line(f[6].replace("\\n", "\r\n"),
                            "1".equals(f[4]), Integer.parseInt(f[5])));
                } catch (NumberFormatException ignored) {
                }
            }
            log.info(String.format("Loaded the dialogue of %d quest parts without a script.", map.size()));
        } catch (IOException e) {
            log.error("Could not read " + FILE, e);
        }
        return map;
    }

    /**
     * @param questId the quest
     * @param part    0 = start, 1 = end
     * @param branch  "say" (before the question), "yes" (after accepting) or "no" (after declining)
     * @return the lines, in order (empty if there are none)
     */
    public static List<Line> getLines(int questId, int part, String branch) {
        Map<String, List<Line>> m = lines;
        if (m == null) {
            synchronized (QuestSayData.class) {
                if (lines == null) {
                    lines = load();
                }
                m = lines;
            }
        }
        return m.getOrDefault(key(questId, part, branch), Collections.emptyList());
    }
}
