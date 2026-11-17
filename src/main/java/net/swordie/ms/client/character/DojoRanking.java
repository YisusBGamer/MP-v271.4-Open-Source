package net.swordie.ms.client.character;

import net.swordie.ms.connection.packet.ContentPacket;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.util.FileTime;
import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Mu Lung Dojo weekly ranking, as on live servers (maplestorywiki.net/w/Mu_Lung_Dojo): each character's best run
 * of the week (highest floor, then fastest) in its group, Novice (Lv. 105-200) or Master (Lv. 201+). The ranking is
 * counted on Wednesday from 23:30 to Thursday 00:30 (reset time zone, ResetClock); nobody can enter the Dojo then. Rewards, from So
 * Gong in the Dojo Hall afterwards:
 * <ul>
 * <li>Novice #1: So Gong's Gloves and the One with 120 Years of Inner Power title; #2-3: Expert's Gloves</li>
 * <li>Master #1: Hero's Gloves and One with 600 Years of Inner Power; #1 of a class: Mu Gong's Gloves, a Mu
 * Gong-Certified Wellness Tonic and One with 300 Years of Inner Power; #2-3 of a class: So Gong's Gloves</li>
 * </ul>
 * Gloves and titles last a week (the time the shop's gloves have).
 */
public final class DojoRanking {
    private static final Logger log = LogManager.getLogger(DojoRanking.class);

    public static final String TABLE = "dojo_ranking";
    public static final int NOVICE_MIN_LEVEL = 105;
    public static final int MASTER_MIN_LEVEL = 201;
    public static final int NOVICE = 0;
    public static final int MASTER = 1;

    public static final int HEROS_GLOVES = 1082392;
    public static final int MU_GONGS_GLOVES = 1082393;
    public static final int SO_GONGS_GLOVES = 1082394;
    public static final int EXPERTS_GLOVES = 1082660;
    public static final int WELLNESS_TONIC = 2023882;
    public static final int INNER_POWER_120 = 3700306;
    public static final int INNER_POWER_300 = 3700307;
    public static final int INNER_POWER_600 = 3700308;
    public static final int REWARD_HOURS = 7 * 24;

    private DojoRanking() {
    }

    private static ZonedDateTime now() {
        return net.swordie.ms.util.ResetClock.now();
    }

    /** The ranking week a moment belongs to: the date of the Thursday (00:30) it starts on. */
    public static String week(ZonedDateTime t) {
        ZonedDateTime start = t.with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY)).withHour(0).withMinute(30)
                .withSecond(0).withNano(0);
        if (t.isBefore(start)) {
            start = start.minusWeeks(1);
        }
        return start.toLocalDate().toString();
    }

    public static String currentWeek() {
        return week(now());
    }

    public static String lastWeek() {
        return week(now().minusWeeks(1));
    }

    /** Wednesday 23:30 to Thursday 00:30: the ranking is being counted and the Dojo is closed. */
    public static boolean isCounting() {
        ZonedDateTime t = now();
        return (t.getDayOfWeek() == DayOfWeek.WEDNESDAY && t.getHour() == 23 && t.getMinute() >= 30)
                || (t.getDayOfWeek() == DayOfWeek.THURSDAY && t.getHour() == 0 && t.getMinute() < 30);
    }

    public static int group(int level) {
        return level >= MASTER_MIN_LEVEL ? MASTER : level >= NOVICE_MIN_LEVEL ? NOVICE : -1;
    }

    /** A cleared floor: kept when it beats the character's best of this week (higher, or as high and faster). */
    public static void record(Char chr, int floor, int timeSec) {
        if (chr == null || ServerHooks.isAutomated(chr)) { // bots and probes don't rank
            return;
        }
        int grp = group(chr.getLevel());
        if (grp < 0 || isCounting()) {
            return;
        }
        String sql = "INSERT INTO " + TABLE + " (week, char_id, char_name, job, level, grp, floor, time_sec)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE"
                + " char_name = VALUES(char_name), job = VALUES(job), level = VALUES(level), grp = VALUES(grp),"
                + " time_sec = IF(VALUES(floor) > floor OR (VALUES(floor) = floor AND VALUES(time_sec) < time_sec),"
                + " VALUES(time_sec), time_sec),"
                + " floor = GREATEST(floor, VALUES(floor))";
        try (Connection con = HikariCPDataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, currentWeek());
            ps.setInt(2, chr.getId());
            ps.setString(3, chr.getName());
            ps.setInt(4, chr.getJob());
            ps.setInt(5, chr.getLevel());
            ps.setInt(6, grp);
            ps.setInt(7, floor);
            ps.setInt(8, timeSec);
            ps.executeUpdate();
            // Contributed by Arixal (from GitLab).
            con.commit();
        } catch (Exception e) {
            log.error("Could not save a Mu Lung Dojo record", e);
        }
    }

    private record Row(int charId, String name, int job, int grp, int floor, int timeSec) {
    }

    private static List<Row> rows(String week) {
        List<Row> res = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT char_id, char_name, job, grp, floor, time_sec FROM "
                     + TABLE + " WHERE week = ? ORDER BY grp, floor DESC, time_sec ASC, updated_at ASC")) {
            ps.setString(1, week);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(new Row(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
                            rs.getInt(6)));
                }
            }
        } catch (Exception e) {
            log.error("Could not read the Mu Lung Dojo ranking", e);
        }
        return res;
    }

    /** Ranks a finished week and sets each character's reward (once; the next call does nothing). */
    public static synchronized void settle(String week) {
        if (week.compareTo(currentWeek()) >= 0) {
            return;
        }
        try (Connection con = HikariCPDataSource.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM " + TABLE
                    + " WHERE week = ? AND settled = 0")) {
                ps.setString(1, week);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next() || rs.getInt(1) == 0) {
                        return;
                    }
                }
            }
            Map<Integer, Integer> overall = new HashMap<>();
            Map<Integer, Integer> inClass = new HashMap<>();
            Map<String, Integer> counters = new HashMap<>();
            for (Row r : rows(week)) {
                overall.put(r.charId(), counters.merge("g" + r.grp(), 1, Integer::sum));
                inClass.put(r.charId(), counters.merge("g" + r.grp() + "j" + r.job(), 1, Integer::sum));
            }
            try (PreparedStatement ps = con.prepareStatement("UPDATE " + TABLE + " SET settled = 1, rank_overall = ?,"
                    + " rank_class = ?, reward = ? WHERE week = ? AND char_id = ?")) {
                for (Row r : rows(week)) {
                    int o = overall.get(r.charId()), c = inClass.get(r.charId());
                    List<Integer> items = rewardItems(r.grp(), o, c);
                    ps.setInt(1, o);
                    ps.setInt(2, c);
                    ps.setString(3, items.isEmpty() ? null : String.join(",", items.stream().map(String::valueOf)
                            .toList()));
                    ps.setString(4, week);
                    ps.setInt(5, r.charId());
                    ps.addBatch();
                }
                ps.executeBatch();
                // Contributed by Arixal (from GitLab).
                con.commit();
            }
        } catch (Exception e) {
            log.error("Could not settle the Mu Lung Dojo ranking of " + week, e);
        }
    }

    static List<Integer> rewardItems(int grp, int overall, int inClass) {
        List<Integer> res = new ArrayList<>();
        if (grp == NOVICE) {
            if (overall == 1) {
                res.add(SO_GONGS_GLOVES);
                res.add(INNER_POWER_120);
            } else if (overall <= 3) {
                res.add(EXPERTS_GLOVES);
            }
        } else {
            if (overall == 1) {
                res.add(HEROS_GLOVES);
                res.add(INNER_POWER_600);
            }
            if (inClass == 1) {
                res.add(MU_GONGS_GLOVES);
                res.add(WELLNESS_TONIC);
                res.add(INNER_POWER_300);
            } else if (inClass <= 3) {
                res.add(SO_GONGS_GLOVES);
            }
        }
        return res;
    }

    /** The character's place last week: {group, overall rank, class rank, floor, seconds}, or null. */
    public static int[] lastWeekPlace(Char chr) {
        String week = lastWeek();
        settle(week);
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT grp, rank_overall, rank_class, floor, time_sec FROM "
                     + TABLE + " WHERE week = ? AND char_id = ?")) {
            ps.setString(1, week);
            ps.setInt(2, chr.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new int[]{rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)};
                }
            }
        } catch (Exception e) {
            log.error("Could not read a Mu Lung Dojo place", e);
        }
        return null;
    }

    /** This week's best so far: {floor, seconds}, or null. */
    public static int[] thisWeekBest(Char chr) {
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT floor, time_sec FROM " + TABLE
                     + " WHERE week = ? AND char_id = ?")) {
            ps.setString(1, currentWeek());
            ps.setInt(2, chr.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new int[]{rs.getInt(1), rs.getInt(2)};
                }
            }
        } catch (Exception e) {
            log.error("Could not read a Mu Lung Dojo record", e);
        }
        return null;
    }

    /** Ranking window types (DOJANG_RANKING_RESULT): Novice, Master, Master by job. */
    public static final int TYPE_NOVICE = 0;
    public static final int TYPE_MASTER = 2;
    public static final int TYPE_MASTER_JOB = 3;
    public static final int WINDOW_ROWS = 100;
    /** The window's header reads this quest-ex: Floor, Time (this week's best). */
    public static final int WINDOW_BEST_QUEST = 100466;
    private static final int NO_PERCENT = 101;

    private record LevelRow(int charId, String name, int job, int level, int grp, int floor, int timeSec) {
    }

    private static List<LevelRow> levelRows(String week) {
        List<LevelRow> res = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT char_id, char_name, job, level, grp, floor, time_sec"
                     + " FROM " + TABLE + " WHERE week = ? ORDER BY grp, floor DESC, time_sec ASC, updated_at ASC")) {
            ps.setString(1, week);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(new LevelRow(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
                            rs.getInt(6), rs.getInt(7)));
                }
            }
        } catch (Exception e) {
            log.error("Could not read the Mu Lung Dojo ranking", e);
        }
        return res;
    }

    /** The window's score: floor * 1000 + 1000 - seconds (it shows floor and time from it). */
    public static int point(int floor, int timeSec) {
        return floor * 1000 + 1000 - Math.max(1, Math.min(999, timeSec));
    }

    private static List<LevelRow> filter(List<LevelRow> rows, int grp, int job) {
        return rows.stream().filter(r -> r.grp() == grp && (job < 0 || r.job() == job)).toList();
    }

    /** {point, rank, top %} of a character in a ranked list, or {0, 0, 101}. */
    private static int[] place(List<LevelRow> rows, int charId) {
        for (int i = 0; i < rows.size(); i++) {
            LevelRow r = rows.get(i);
            if (r.charId() == charId) {
                return new int[]{point(r.floor(), r.timeSec()), i + 1, Math.max(1, (i + 1) * 100 / rows.size())};
            }
        }
        return new int[]{0, 0, NO_PERCENT};
    }

    private static ContentPacket.DojoRankTab tab(Char chr, int type, int grp, int job, List<LevelRow> now,
                                                 List<LevelRow> last) {
        List<LevelRow> cur = filter(now, grp, job);
        int[] a = place(cur, chr.getId());
        int[] b = place(filter(last, grp, job), chr.getId());
        int[] record = {chr.getJob(), chr.getLevel(), a[0], a[1], a[2], b[0], b[1], b[2]};
        List<ContentPacket.DojoRankRow> list = new ArrayList<>();
        for (int i = 0; i < Math.min(WINDOW_ROWS, cur.size()); i++) {
            LevelRow r = cur.get(i);
            list.add(new ContentPacket.DojoRankRow(r.job(), r.level(), point(r.floor(), r.timeSec()), i + 1,
                    r.name()));
        }
        return new ContentPacket.DojoRankTab(type, record, list);
    }

    /** Opens the client's Mu Lung Dojo ranking window: this week's Novice, Master and Master-by-job tabs. */
    public static void openWindow(Char chr) {
        String lw = lastWeek();
        settle(lw);
        List<LevelRow> now = levelRows(currentWeek());
        List<LevelRow> last = levelRows(lw);
        int[] best = thisWeekBest(chr);
        if (best != null) {
            chr.getScriptManager().createQuestWithQRValue(WINDOW_BEST_QUEST, "Floor=" + best[0] + ";Time=" + best[1]);
        }
        List<ContentPacket.DojoRankTab> tabs = List.of(
                tab(chr, TYPE_NOVICE, NOVICE, -1, now, last),
                tab(chr, TYPE_MASTER, MASTER, -1, now, last),
                tab(chr, TYPE_MASTER_JOB, MASTER, chr.getJob(), now, last));
        chr.write(ContentPacket.dojoRanking(tabs));
    }

    /** The top of a group this week (or last week), as dialog lines. */
    public static String top(int grp, boolean last, int count) {
        String week = last ? lastWeek() : currentWeek();
        if (last) {
            settle(week);
        }
        StringBuilder sb = new StringBuilder();
        int n = 0;
        for (Row r : rows(week)) {
            if (r.grp() != grp) {
                continue;
            }
            n++;
            sb.append(n).append(". ").append(r.name()).append(" - floor ").append(r.floor()).append(" (")
                    .append(r.timeSec()).append(" s)\r\n");
            if (n >= count) {
                break;
            }
        }
        return n == 0 ? "No records yet.\r\n" : sb.toString();
    }

    /**
     * Hands out the character's unclaimed rewards of past weeks (they need room in the inventory).
     *
     * @return what happened, for So Gong to say
     */
    public static synchronized String claim(Char chr) {
        settle(lastWeek());
        List<String[]> pending = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT week, reward FROM " + TABLE
                     + " WHERE char_id = ? AND settled = 1 AND claimed = 0 AND reward IS NOT NULL")) {
            ps.setInt(1, chr.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pending.add(new String[]{rs.getString(1), rs.getString(2)});
                }
            }
        } catch (Exception e) {
            log.error("Could not read Mu Lung Dojo rewards", e);
            return "I can't find the ranking records right now. Try again later.";
        }
        if (pending.isEmpty()) {
            return null;
        }
        StringBuilder got = new StringBuilder();
        for (String[] p : pending) {
            List<Integer> items = new ArrayList<>();
            for (String s : p[1].split(",")) {
                items.add(Integer.parseInt(s.trim()));
            }
            for (int id : items) {
                if (!chr.canHold(id, 1)) {
                    return "Make some room in your inventory first.";
                }
            }
            try {
                if (!markClaimed(p[0], chr.getId())) {
                    continue;
                }
            } catch (Exception e) {
                log.error("Could not mark a Mu Lung Dojo reward", e);
                return "I can't find the ranking records right now. Try again later.";
            }
            for (int id : items) {
                var item = ItemData.getItemDeepCopy(id);
                if (item == null) {
                    continue;
                }
                if (id / 10000 == 108 || id / 10000 == 370) {
                    item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusHours(REWARD_HOURS)));
                }
                item.setQuantity(1);
                chr.addItemToInventory(item);
                got.append("#i").append(id).append(":# #t").append(id).append("#\r\n");
            }
        }
        return got.length() == 0 ? null : "Here are your ranking rewards:\r\n" + got;
    }

    // Contributed by Arixal (from GitLab).
    /** @return whether this call claimed the week's reward (false: it was claimed already) */
    static boolean markClaimed(String week, int charId) throws Exception {
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE " + TABLE + " SET claimed = 1 WHERE week = ?"
                     + " AND char_id = ? AND claimed = 0")) {
            ps.setString(1, week);
            ps.setInt(2, charId);
            boolean claimed = ps.executeUpdate() > 0;
            con.commit();
            return claimed;
        }
    }
}
