package net.swordie.ms.client;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.db.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Login screen Quick Start. The client keeps two records, each (world, channel index, character id): the Quick Start
 * target (with a favourite flag) and the last played character. The server sends both in the world list end and in
 * the select world result, and gets them back in WORLD_LIST_REQUEST; the favourite only reaches the server there.
 */
public class QuickStart {
    private static final Logger log = LogManager.getLogger();

    public record Entry(int world, int channel, int charId) {
        public static final Entry NONE = new Entry(-1, 0, 0);

        public boolean isSet() {
            return world >= 0 && charId > 0;
        }
    }

    public record Records(Entry last, Entry favourite) {
        public static final Records NONE = new Records(Entry.NONE, Entry.NONE);

        /** The character Quick Start enters with: the favourite, else the last played. */
        public Entry target() {
            return favourite.isSet() ? favourite : last;
        }
    }

    public static Records load(User user) {
        if (user == null) {
            return Records.NONE;
        }
        List<Map<String, Object>> rows;
        try {
            rows = DatabaseManager.queryRows("SELECT lastworld, lastchannel, lastcharid, favworld, favchannel, "
                    + "favcharid FROM user_quick_start WHERE userid = ?", user.getId());
        } catch (Exception ex) {
            log.error("Could not read Quick Start for user " + user.getId(), ex);
            return Records.NONE;
        }
        if (rows.isEmpty()) {
            return Records.NONE;
        }
        Map<String, Object> r = rows.get(0);
        Entry last = new Entry(num(r, "lastworld", -1), num(r, "lastchannel", 0), num(r, "lastcharid", 0));
        Entry fav = new Entry(num(r, "favworld", -1), num(r, "favchannel", 0), num(r, "favcharid", 0));
        // drop characters that were deleted or moved away
        if (last.isSet() && findChar(user, last) == null) {
            last = Entry.NONE;
        }
        if (fav.isSet() && findChar(user, fav) == null) {
            fav = Entry.NONE;
        }
        return new Records(last, fav);
    }

    public static Char findChar(User user, Entry e) {
        if (user == null || e == null || !e.isSet()) {
            return null;
        }
        Account acc = user.getAccountByWorldId(e.world());
        return acc == null ? null : acc.getCharById(e.charId());
    }

    /** The character the login screen's Quick Start panel shows, or null. */
    public static Char targetChar(User user) {
        return findChar(user, load(user).target());
    }

    public static void saveLast(User user, Entry e) {
        if (user == null || e == null || !e.isSet()) {
            return;
        }
        save(user, "lastworld", "lastchannel", "lastcharid", e);
    }

    public static void saveFavourite(User user, Entry e) {
        if (user == null) {
            return;
        }
        save(user, "favworld", "favchannel", "favcharid", e == null || !e.isSet() ? Entry.NONE : e);
    }

    /** Character select theme (Etc.wz customLoginTheme.img id); 0 when none was chosen. */
    public static int loadTheme(User user) {
        if (user == null) {
            return 0;
        }
        try {
            List<Map<String, Object>> rows = DatabaseManager.queryRows(
                    "SELECT logintheme FROM user_quick_start WHERE userid = ?", user.getId());
            return rows.isEmpty() ? 0 : Math.max(0, num(rows.get(0), "logintheme", 0));
        } catch (Exception ex) {
            log.error("Could not read the login theme of user " + user.getId(), ex);
            return 0;
        }
    }

    public static void saveTheme(User user, int theme) {
        if (user == null || theme < 0) {
            return;
        }
        try {
            DatabaseManager.executeQuery("INSERT INTO user_quick_start (userid, logintheme) VALUES (?, ?) "
                    + "ON DUPLICATE KEY UPDATE logintheme = VALUES(logintheme)", user.getId(), theme);
        } catch (Exception ex) {
            log.error("Could not save the login theme of user " + user.getId(), ex);
        }
    }

    private static void save(User user, String w, String ch, String id, Entry e) {
        try {
            DatabaseManager.executeQuery(String.format("INSERT INTO user_quick_start (userid, %1$s, %2$s, %3$s) "
                            + "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE %1$s = VALUES(%1$s), %2$s = VALUES(%2$s), "
                            + "%3$s = VALUES(%3$s)", w, ch, id),
                    user.getId(), e.world(), e.channel(), e.charId());
        } catch (Exception ex) {
            log.error("Could not save the Quick Start character of user " + user.getId(), ex);
        }
    }

    private static int num(Map<String, Object> row, String key, int def) {
        Object o = row.get(key);
        return o instanceof Number n ? n.intValue() : def;
    }
}
