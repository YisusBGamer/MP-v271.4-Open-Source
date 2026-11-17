package net.swordie.ms.connection.db;

import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Account edits Server Control queued while the server ran (logs/control/desktop-queue.json), applied at boot before
 * logins open. The queue is moved to desktop-queue.applying.json first and shrunk after each entry, so a crash part way
 * never applies an entry twice; failures go back into the queue with their error. Same SQL as Server Control's db.js.
 */
public class QueuedAccountChanges {
    private static final Logger log = LogManager.getLogger();
    private static final Path DIR = Path.of("logs", "control");
    private static final Path QUEUE = DIR.resolve("desktop-queue.json");
    private static final Path APPLYING = DIR.resolve("desktop-queue.applying.json");
    private static final Path AUDIT = DIR.resolve("desktop-audit.jsonl");

    public static void applyAtBoot() {
        try {
            if (!Files.exists(APPLYING)) {
                JSONArray queued = read(QUEUE);
                if (queued == null || queued.isEmpty()) {
                    return;
                }
                Files.move(QUEUE, APPLYING, StandardCopyOption.ATOMIC_MOVE);
            }
            JSONArray pending = read(APPLYING);
            if (pending == null) {
                log.error("Queued account changes: " + APPLYING + " is unreadable; left as is.");
                return;
            }
            JSONArray failed = new JSONArray();
            Set<String> applied = new HashSet<>();
            while (!pending.isEmpty()) {
                JSONObject entry = pending.getJSONObject(0);
                String text = entry.optString("text", "?");
                String user = entry.optString("userName", "?");
                try {
                    apply(entry.getInt("userId"), entry.getJSONObject("change"));
                    applied.add(entry.optString("id"));
                    audit(entry, null);
                    log.info("Queued account change applied: " + user + ": " + text);
                } catch (Exception e) {
                    entry.put("error", e.getMessage() == null ? e.toString() : e.getMessage());
                    failed.put(entry);
                    audit(entry, entry.getString("error"));
                    log.error("Queued account change failed (stays queued): " + user + ": " + text + ": " + e.getMessage());
                }
                pending.remove(0);
                write(APPLYING, pending);
            }
            // Server Control may have queued more meanwhile: keep those, drop any copy of an entry just applied
            JSONArray current = read(QUEUE);
            JSONArray next = new JSONArray();
            for (int i = 0; i < failed.length(); i++) {
                next.put(failed.get(i));
            }
            if (current != null) {
                for (int i = 0; i < current.length(); i++) {
                    JSONObject entry = current.optJSONObject(i);
                    if (entry != null && !applied.contains(entry.optString("id"))) {
                        next.put(entry);
                    }
                }
            }
            write(QUEUE, next);
            Files.deleteIfExists(APPLYING);
            log.info(String.format("Queued account changes: %d applied, %d failed.", applied.size(), failed.length()));
        } catch (Exception e) {
            log.error("Queued account changes were not applied", e);
        }
    }

    private static void apply(int userId, JSONObject change) throws SQLException {
        String action = change.getString("action");
        try (Connection con = HikariCPDataSource.getConnection()) {
            try {
                switch (action) {
                    case "account-type" -> update(con, "UPDATE users SET accounttype = ? WHERE id = ?", change.getInt("accountType"), userId);
                    case "password" -> update(con, "UPDATE users SET password = ? WHERE id = ?", change.getString("hash"), userId);
                    case "character-slots" -> update(con, "UPDATE users SET characterslots = ? WHERE id = ?", change.getInt("characterSlots"), userId);
                    case "ban" -> {
                        int days = change.optInt("days", 0);
                        String reason = change.optString("reason", "Banned by an admin");
                        if (days <= 0) {
                            update(con, "UPDATE users SET banExpireDate = DATE_ADD(NOW(), INTERVAL 100 YEAR), banReason = ? WHERE id = ?", reason, userId);
                        } else {
                            update(con, "UPDATE users SET banExpireDate = DATE_ADD(NOW(), INTERVAL ? DAY), banReason = ? WHERE id = ?", days, reason, userId);
                        }
                    }
                    case "unban" -> update(con, "UPDATE users SET banExpireDate = NULL, banReason = NULL WHERE id = ?", userId);
                    case "delete" -> {
                        try (PreparedStatement ps = con.prepareStatement("SELECT (SELECT COUNT(*) FROM characters c INNER JOIN accounts a ON a.id = c.accid WHERE a.userid = ?) + (SELECT COUNT(*) FROM characters WHERE userid = ?)")) {
                            ps.setInt(1, userId);
                            ps.setInt(2, userId);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next() && rs.getLong(1) != 0) {
                                    throw new SQLException("This login still has characters. Only logins without characters can be deleted.");
                                }
                            }
                        }
                        update(con, "DELETE FROM users WHERE id = ?", userId);
                    }
                    default -> throw new SQLException("Unknown account change: " + action);
                }
                con.commit();
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        }
    }

    private static void update(Connection con, String sql, Object... args) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.executeUpdate();
        }
    }

    private static JSONArray read(Path file) {
        try {
            if (!Files.exists(file)) {
                return null;
            }
            String text = Files.readString(file, StandardCharsets.UTF_8).trim();
            return text.isEmpty() ? new JSONArray() : new JSONArray(text);
        } catch (Exception e) {
            return null;
        }
    }

    private static void write(Path file, JSONArray list) throws Exception {
        Files.createDirectories(DIR);
        Path tmp = Files.createTempFile(DIR, file.getFileName().toString(), ".tmp");
        Files.writeString(tmp, list.toString(2), StandardCharsets.UTF_8);
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    // same line format as Server Control's audit log
    private static void audit(JSONObject entry, String error) {
        try {
            JSONObject line = new JSONObject();
            line.put("time", Instant.now().toString());
            line.put("by", "server:boot");
            line.put("action", error == null ? "accounts." + entry.getJSONObject("change").optString("action") : "accounts.queue-failed");
            line.put("userId", entry.optInt("userId"));
            line.put("user", entry.optString("userName"));
            line.put("change", entry.optString("text"));
            line.put("queued", true);
            line.put("automatic", true);
            if (error != null) {
                line.put("error", error);
            }
            Files.writeString(AUDIT, line + "\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) {
            // the change itself already happened
        }
    }
}
