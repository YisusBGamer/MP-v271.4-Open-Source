package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.util.Util;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Per-character party quest records, kept in one server-only quest record
 * ({@link GameConstants#PARTY_QUEST_QR}): for each party quest, today's entries (for daily entry limits), the number
 * of clears and how many clear rewards (e.g. Broken Glasses every 5 clears) were already handed out.
 * <p>
 * Format: {@code key=yyyy-MM-dd,entriesThatDay,clears,rewardsGiven;key2=...}
 */
public final class PartyQuestRecords {

    private static final Pattern WEEK_KEY = Pattern.compile("_\\d{4}w\\d+$");

    private PartyQuestRecords() {
    }

    private static Map<String, long[]> read(Char chr) {
        Map<String, long[]> map = new LinkedHashMap<>();
        String rec = chr.getQuestManager().getHiddenRecord(GameConstants.PARTY_QUEST_QR);
        if (rec == null || rec.isEmpty()) {
            return map;
        }
        for (String part : rec.split(";")) {
            int eq = part.indexOf('=');
            if (eq <= 0) {
                continue;
            }
            String[] v = part.substring(eq + 1).split(",");
            if (v.length < 4) {
                continue;
            }
            try {
                long day = LocalDate.parse(v[0]).toEpochDay();
                map.put(part.substring(0, eq), new long[]{day, Long.parseLong(v[1]), Long.parseLong(v[2]),
                        Long.parseLong(v[3])});
            } catch (Exception ignored) {
                // a broken entry is dropped
            }
        }
        return map;
    }

    private static void write(Char chr, Map<String, long[]> map) {
        StringBuilder sb = new StringBuilder();
        for (var e : map.entrySet()) {
            long[] v = e.getValue();
            if (sb.length() > 0) {
                sb.append(';');
            }
            sb.append(e.getKey()).append('=').append(LocalDate.ofEpochDay(v[0])).append(',').append(v[1])
                    .append(',').append(v[2]).append(',').append(v[3]);
        }
        chr.getQuestManager().setHiddenRecord(GameConstants.PARTY_QUEST_QR, sb.toString());
    }

    private static long[] get(Map<String, long[]> map, String pq) {
        long today = net.swordie.ms.util.ResetClock.today().toEpochDay();
        long[] v = map.computeIfAbsent(pq, k -> new long[]{today, 0, 0, 0});
        if (v[0] != today) { // a new day: today's entries start over
            v[0] = today;
            v[1] = 0;
        }
        return v;
    }

    /** @return how many times the character entered this party quest today (server clock) */
    public static int getTodayEntries(Char chr, String pq) {
        return (int) get(read(chr), pq)[1];
    }

    public static void addEntry(Char chr, String pq) {
        var map = read(chr);
        get(map, pq)[1]++;
        write(chr, map);
    }

    /** Today's entries back to 0 (test characters that run a party quest over and over). */
    public static void resetTodayEntries(Char chr, String pq) {
        var map = read(chr);
        get(map, pq)[1] = 0;
        write(chr, map);
    }

    /** Weekly clears (keys ending in a week, e.g. princess_no_2026w45) dropped and today's entries back to 0. */
    public static void resetLimits(Char chr) {
        var map = read(chr);
        map.keySet().removeIf(k -> WEEK_KEY.matcher(k).find());
        long today = net.swordie.ms.util.ResetClock.today().toEpochDay();
        for (long[] v : map.values()) {
            v[0] = today;
            v[1] = 0;
        }
        write(chr, map);
    }

    public static int getClears(Char chr, String pq) {
        return (int) get(read(chr), pq)[2];
    }

    public static void addClear(Char chr, String pq) {
        var map = read(chr);
        get(map, pq)[2]++;
        write(chr, map);
    }

    public static int getRewardsGiven(Char chr, String pq) {
        return (int) get(read(chr), pq)[3];
    }

    public static void addRewardGiven(Char chr, String pq) {
        var map = read(chr);
        get(map, pq)[3]++;
        write(chr, map);
    }

    /** The quest record the client and the Pub (9076002) in Spiegelmann's Guest House read Party Points from. */
    public static final int PARTY_POINTS_QUEST = 7907;

    /** @return the character's Party Points (spent at the Pub in Spiegelmann's Guest House) */
    public static int getPartyPoints(Char chr) {
        var q = chr.getQuestManager().getQuestById(PARTY_POINTS_QUEST);
        String v = q == null ? null : q.getProperty("point");
        return Util.isNumber(v) ? Integer.parseInt(v) : 0;
    }

    /** Gives Party Points (Cross World party quest clears give 10 on live servers). */
    public static void addPartyPoints(Char chr, int amount) {
        if (amount <= 0) {
            return;
        }
        int total = (int) Math.min(Integer.MAX_VALUE, (long) getPartyPoints(chr) + amount);
        var q = chr.getQuestManager().getOrCreateQuestById(PARTY_POINTS_QUEST);
        if (q == null) {
            return;
        }
        q.setProperty("point", total);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
        chr.chatMessage("You gained " + amount + " Party Points (" + total + " total). Spend them at the Pub in "
                + "Spiegelmann's Guest House.");
    }

    /** "Start today's Cross World Party Quest!": today's Cross World clears, kept on record 7906 "count". */
    public static final int CROSS_WORLD_DAILY_QUEST = 32415;
    public static final int CROSS_WORLD_DAILY_RECORD = 7906;
    public static final int CROSS_WORLD_DAILY_GOAL = 5;

    /** Counts a Cross World party quest clear for the daily quest, while it is held (up to its goal). */
    public static void addCrossWorldDailyClear(Char chr) {
        var qm = chr.getQuestManager();
        if (!qm.hasQuestInProgress(CROSS_WORLD_DAILY_QUEST)) {
            return;
        }
        var q = qm.getOrCreateQuestById(CROSS_WORLD_DAILY_RECORD);
        if (q == null) {
            return;
        }
        String v = q.getProperty("count");
        int count = Util.isNumber(v) ? Integer.parseInt(v) : 0;
        if (count >= CROSS_WORLD_DAILY_GOAL) {
            return;
        }
        q.setProperty("count", count + 1);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }
}
