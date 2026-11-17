package net.swordie.ms.connection.packet;

import net.swordie.ms.ServerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Per-skill parts of the v271 remote attack packets that the old skill lists get wrong (resources/remote-attack-v271.tsv).
 */
public final class RemoteAttackLayout {
    private static final Logger log = LogManager.getLogger(RemoteAttackLayout.class);
    private static final Set<Integer> SHOOT_PASSIVE = new HashSet<>();
    private static final Map<Integer, Integer> EXTRA_BYTES = new HashMap<>();
    private static final Set<Integer> REPEAT_INDEX = new HashSet<>();
    private static final Map<Integer, long[]> LAST_REPEAT = new java.util.concurrent.ConcurrentHashMap<>();
    private static final long REPEAT_GAP_MS = 3000;
    private static boolean loaded;

    private RemoteAttackLayout() {
    }

    private static synchronized void load() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            for (String line : Files.readAllLines(Path.of(ServerConstants.RESOURCES_DIR, "remote-attack-v271.tsv"))) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] f = line.split("\t");
                if (f[0].equals("shootPassive")) {
                    SHOOT_PASSIVE.add(Integer.parseInt(f[1]));
                } else if (f[0].equals("repeatIndex")) {
                    REPEAT_INDEX.add(Integer.parseInt(f[1]));
                } else if (f[0].equals("extra")) {
                    EXTRA_BYTES.put(Integer.parseInt(f[1]), Integer.parseInt(f[2]));
                }
            }
        } catch (Exception e) {
            log.error("Could not read remote-attack-v271.tsv", e);
        }
    }

    /** Whether REMOTE_SHOOT_ATTACK has the passive hyper skill fields for this skill; null if the file is missing. */
    public static Boolean hasShootPassive(int skillId) {
        load();
        return SHOOT_PASSIVE.isEmpty() ? null : SHOOT_PASSIVE.contains(skillId);
    }

    /**
     * The int after the summoned id: GMS numbers the packets of a repeating skill 1, 2, 3... (the client plays the
     * start-only parts below 2); 0 for every other skill.
     */
    public static int repeatIndex(int chrId, int skillId) {
        load();
        if (!REPEAT_INDEX.contains(skillId)) {
            return 0;
        }
        long now = System.currentTimeMillis();
        long[] last = LAST_REPEAT.computeIfAbsent(chrId, k -> new long[3]);
        synchronized (last) {
            if (last[0] != skillId || now - last[2] > REPEAT_GAP_MS) {
                last[0] = skillId;
                last[1] = 0;
            }
            last[1]++;
            last[2] = now;
            return (int) last[1];
        }
    }

    /** Size of the skill block after the hit list when the old lists don't match the client, else null. */
    public static Integer extraBytes(int skillId) {
        load();
        return EXTRA_BYTES.get(skillId);
    }
}
