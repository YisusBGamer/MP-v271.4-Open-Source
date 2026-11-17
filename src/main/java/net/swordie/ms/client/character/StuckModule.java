package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.loaders.StringData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gets a stuck character out (the @stuck command and the web API): stops any script, unlocks the
 * screen, takes it out of a boss or event instance and warps it to Henesys. A character that is offline is moved to
 * Henesys in the database, so it logs in there.
 * <p>
 * Every use is written to logs/stuck.log (time, how, account, character, map and position), so the places players get
 * stuck can be looked at and fixed.
 */
public final class StuckModule {

    private static final Logger log = LogManager.getLogger(StuckModule.class);

    public static final int HENESYS = 100000000;
    public static final Path LOG_FILE = Paths.get(ServerConstants.LOG_DIR, "stuck.log");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private StuckModule() {
    }

    /**
     * Gets an online character out to Henesys.
     *
     * @param via who asked: "@stuck" or "web"
     */
    public static void unstuck(Char chr, String via) {
        var field = chr.getField();
        int fieldId = field == null ? -1 : field.getId();
        var pos = chr.getPosition();
        String instance = chr.getInstance() == null ? "" : " (in an instance)";
        write(via, chr.getUser() == null ? "?" : chr.getUser().getName(), chr.getName(), chr.getId(), fieldId,
                pos == null ? "?" : pos.getX() + "," + pos.getY(), instance + (chr.getHP() <= 0 ? " (dead)" : ""));
        try {
            chr.getScriptManager().lockInGameUI(false);
        } catch (Exception e) {
            log.debug("Stuck: could not unlock the UI of " + chr.getName(), e);
        }
        if (chr.getHP() <= 0) {
            chr.heal(chr.getMaxHP(), false, true);
        }
        if (chr.getInstance() != null) {
            // leaves the boss/event instance properly (death count, events), straight to Henesys
            chr.getInstance().removeChar(chr, HENESYS, 0, true, true);
        } else {
            chr.warp(HENESYS, 0);
        }
        chr.chatMessage("You were moved to Henesys. If you got stuck somewhere, the server owner can see where.");
        // last: stops every script (also the @stuck one that may be calling this), without throwing
        try {
            chr.getScriptManager().dispose(false);
        } catch (Exception e) {
            log.debug("Stuck: could not stop the scripts of " + chr.getName(), e);
        }
        chr.dispose();
    }

    /**
     * Moves an offline character to Henesys (it logs in there).
     *
     * @return whether the character was moved
     */
    public static boolean unstuckOffline(int charId, String account, String name, long fieldId, String via) {
        write(via, account, name, charId, fieldId, "?", " (offline)");
        try {
            // the character's own stats row (through its avatar data), like the game loads it
            DatabaseManager.executeQuery("UPDATE characterstats cs INNER JOIN avatardata ad ON ad.characterstat = cs.id "
                    + "INNER JOIN characters c ON c.avatardata = ad.id SET cs.posmap = ?, cs.portal = 0 WHERE c.id = ?",
                    HENESYS, charId);
            return true;
        } catch (Exception e) {
            log.error("Stuck: could not move offline character " + charId, e);
            return false;
        }
    }

    private static synchronized void write(String via, String account, String name, int charId, long fieldId,
                                           String position, String extra) {
        String mapName = StringData.getMapStringById((int) fieldId);
        String line = String.format("%s | %s | account %s | %s (id %d) | map %d %s | position %s%s%n",
                LocalDateTime.now().format(TIME), via, account, name, charId, fieldId,
                mapName == null ? "" : "(" + mapName + ")", position, extra);
        log.info("Stuck: " + line.trim());
        try {
            Files.createDirectories(LOG_FILE.getParent());
            Files.writeString(LOG_FILE, line, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Stuck: could not write " + LOG_FILE, e);
        }
    }
}
