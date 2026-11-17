package net.swordie.ms.connection.packet;

import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Which maps send MOB_MOVE without the movement's key pad trailer, so the byte after the path is read as the
 * teleport-finished flag. server.properties "mob.moveNoKeypadTrailerMaps": comma list of map ids, "all", or
 * "none". Re-read when the file changes (checked every 5 s), so no restart is needed.
 */
public final class MobMoveLayout {
    private static final Logger log = LogManager.getRootLogger();
    private static final String KEY = "mob.moveNoKeypadTrailerMaps";
    /** Damien (Normal), whose teleports observers should now see finish. */
    private static final String DEFAULT = "350160200";
    private static final long CHECK_MS = 5_000;
    private static final Path FILE = Path.of(System.getProperty("user.dir"), "server.properties");

    private static volatile boolean all;
    private static volatile Set<Integer> maps = parse(DEFAULT);
    private static volatile String current = DEFAULT;
    private static volatile long lastCheck;
    private static volatile long lastModified = -1;

    private MobMoveLayout() {
    }

    public static boolean withoutKeyPads(Field field) {
        refresh();
        if (all) {
            return true;
        }
        return field != null && maps.contains(field.getId());
    }

    private static void refresh() {
        long now = System.currentTimeMillis();
        if (now - lastCheck < CHECK_MS) {
            return;
        }
        lastCheck = now;
        try {
            long modified = Files.isRegularFile(FILE) ? Files.getLastModifiedTime(FILE).toMillis() : 0;
            if (modified == lastModified) {
                return;
            }
            String value = DEFAULT;
            if (modified != 0) {
                Properties props = new Properties();
                try (InputStream in = Files.newInputStream(FILE)) {
                    props.load(in);
                }
                value = props.getProperty(KEY, DEFAULT).trim();
            }
            lastModified = modified; // only once read, so a failed read is tried again

            if (!value.equals(current)) {
                current = value;
                all = value.equalsIgnoreCase("all");
                maps = parse(value);
                log.info("[MobMove] no key pad trailer on: {}", value.isEmpty() ? "none" : value);
            }
        } catch (Exception e) {
            log.warn("[MobMove] could not read {}: {}", KEY, e.getMessage());
        }
    }

    private static Set<Integer> parse(String value) {
        Set<Integer> res = new HashSet<>();
        for (String s : value.split(",")) {
            s = s.trim();
            if (!s.isEmpty() && s.chars().allMatch(Character::isDigit)) {
                res.add(Integer.parseInt(s));
            }
        }
        return res;
    }
}
