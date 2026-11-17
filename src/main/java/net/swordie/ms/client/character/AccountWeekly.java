package net.swordie.ms.client.character;

import net.swordie.ms.util.ResetClock;
import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Weekly clears an account shares (Etc.wz MapleScheduler shareLimit "nxid"): Erda's Request, the Epic Dungeons.
 * One row per week, content and character. The table is created on first use, so it takes no migration number.
 */
public final class AccountWeekly {

    private static final Logger log = LogManager.getLogger(AccountWeekly.class);
    private static final String TABLE = "account_weekly_clears";
    private static volatile boolean ready;

    public static final String ERDA_REQUEST = "erda_request";
    public static final String EPIC_PREFIX = "epic_";

    /** One clear: what, by which character. */
    public record Clear(String content, int charId, String charName) {
    }

    private AccountWeekly() {
    }

    private static void ensureTable(Connection con) throws Exception {
        if (ready) {
            return;
        }
        try (Statement s = con.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                    + "week VARCHAR(10) NOT NULL, "
                    + "content VARCHAR(32) NOT NULL, "
                    + "char_id INT NOT NULL, "
                    + "account_id INT NOT NULL, "
                    + "char_name VARCHAR(16) NOT NULL DEFAULT '', "
                    + "cleared_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "PRIMARY KEY (week, content, char_id), "
                    + "KEY account_weekly_account (week, account_id))");
        }
        ready = true;
    }

    /** @return the week a reset day starts, as stored ("2026-10-26") */
    public static String week(DayOfWeek resetDay) {
        return ResetClock.weekStart(resetDay).toString();
    }

    /** @return this week's clears on the account whose content starts with the prefix, oldest first */
    public static List<Clear> clears(int accountId, String contentPrefix, DayOfWeek resetDay) {
        List<Clear> res = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement("SELECT content, char_id, char_name FROM " + TABLE
                    + " WHERE week = ? AND account_id = ? AND content LIKE ? ORDER BY cleared_at")) {
                ps.setString(1, week(resetDay));
                ps.setInt(2, accountId);
                ps.setString(3, contentPrefix.replace("_", "\\_") + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        res.add(new Clear(rs.getString(1), rs.getInt(2), rs.getString(3)));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not read the weekly account clears", e);
        }
        return res;
    }

    public static List<Clear> clears(Char chr, String contentPrefix, DayOfWeek resetDay) {
        return chr.getAccount() == null ? List.of() : clears(chr.getAccount().getId(), contentPrefix, resetDay);
    }

    /** Saves a clear of this character (once per week and content). */
    public static void record(Char chr, String content, DayOfWeek resetDay) {
        if (chr.getAccount() == null) {
            return;
        }
        try (Connection con = HikariCPDataSource.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO " + TABLE
                    + " (week, content, char_id, account_id, char_name) VALUES (?, ?, ?, ?, ?)")) {
                ps.setString(1, week(resetDay));
                ps.setString(2, content);
                ps.setInt(3, chr.getId());
                ps.setInt(4, chr.getAccount().getId());
                ps.setString(5, chr.getName());
                ps.executeUpdate();
                // Contributed by Arixal (from GitLab).
                con.commit();
            }
        } catch (Exception e) {
            log.error("Could not save a weekly account clear", e);
        }
    }

    // ------------------------------------------------------------------------------------------- Erda's Request

    /** Erda's Request resets on Thursday. */
    public static final DayOfWeek ERDA_REQUEST_RESET_DAY = DayOfWeek.THURSDAY;

    /**
     * Erda's Request is shared by the whole login (shareLimit nxid), across worlds: the content key carries the
     * login id, since the account_id column is the per-world account.
     */
    private static String erdaKey(Char chr) {
        return chr.getUser() == null ? null : ERDA_REQUEST + "_u" + chr.getUser().getId();
    }

    /** @return the character on this login that took this week's Erda's Request reward, or null */
    public static String erdaRequestTakenBy(Char chr) {
        String key = erdaKey(chr);
        if (key == null) {
            return null;
        }
        try (Connection con = HikariCPDataSource.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement("SELECT char_name FROM " + TABLE
                    + " WHERE week = ? AND content = ? ORDER BY cleared_at LIMIT 1")) {
                ps.setString(1, week(ERDA_REQUEST_RESET_DAY));
                ps.setString(2, key);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? rs.getString(1) : null;
                }
            }
        } catch (Exception e) {
            log.error("Could not read the weekly Erda's Request claim", e);
            return "another character"; // unknown: don't hand the weekly reward out twice
        }
    }

    public static void recordErdaRequest(Char chr) {
        String key = erdaKey(chr);
        if (key != null) {
            record(chr, key, ERDA_REQUEST_RESET_DAY);
        }
    }

    // ------------------------------------------------------------------------------------------- daily claims

    /**
     * Claims content for today on the character's account (one world), the period column holding the day.
     * Callers serialize claims of the same content.
     *
     * @return true when this call made the claim; false when the account already has it today or the database failed
     */
    public static boolean claimToday(Char chr, String content) {
        if (chr.getAccount() == null) {
            return false;
        }
        String day = ResetClock.today().toString();
        try (Connection con = HikariCPDataSource.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM " + TABLE
                    + " WHERE week = ? AND account_id = ? AND content = ? LIMIT 1")) {
                ps.setString(1, day);
                ps.setInt(2, chr.getAccount().getId());
                ps.setString(3, content);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return false;
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO " + TABLE
                    + " (week, content, char_id, account_id, char_name) VALUES (?, ?, ?, ?, ?)")) {
                ps.setString(1, day);
                ps.setString(2, content);
                ps.setInt(3, chr.getId());
                ps.setInt(4, chr.getAccount().getId());
                ps.setString(5, chr.getName());
                boolean claimed = ps.executeUpdate() > 0;
                con.commit();
                return claimed;
            }
        } catch (Exception e) {
            log.error("Could not save the daily claim {} of character {}", content, chr.getId(), e);
            return false;
        }
    }

    /**
     * Daily claims whose content starts with the prefix, from one day to another (inclusive).
     *
     * @param charId only that character's claims, or 0 for the whole account
     * @return the claims' content strings, or null when the database failed
     */
    public static List<String> dailyClaims(int accountId, int charId, String contentPrefix, LocalDate from,
                                           LocalDate to) {
        List<String> res = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection()) {
            ensureTable(con);
            try (PreparedStatement ps = con.prepareStatement("SELECT content FROM " + TABLE
                    + " WHERE week BETWEEN ? AND ? AND account_id = ? AND content LIKE ? AND (? = 0 OR char_id = ?)")) {
                ps.setString(1, from.toString());
                ps.setString(2, to.toString());
                ps.setInt(3, accountId);
                ps.setString(4, contentPrefix.replace("_", "\\_") + "%");
                ps.setInt(5, charId);
                ps.setInt(6, charId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        res.add(rs.getString(1));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not read the daily claims {} of account {}", contentPrefix, accountId, e);
            return null;
        }
        return res;
    }
}
