package net.swordie.ms.world.field.instance.instancehelper.arcane;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.loaders.QuestData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * The Arcane River weekly content counters (Etc.wz MapleScheduler: key wCount, 3 a week, reset on Thursday, per
 * character). A run counts when it's cleared; a failed run doesn't.
 */
public class ArcaneWeekly {
    public static final int WEEKLY_CLEARS = 3;
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yy/MM/dd");

    /** @return the Thursday the current week started on */
    private static LocalDate weekStart() {
        return net.swordie.ms.util.ResetClock.weekStart(DayOfWeek.THURSDAY);
    }

    private static Quest record(Char chr, int qid) {
        var qm = chr.getQuestManager();
        Quest q = qm.getQuestById(qid);
        if (q == null) {
            q = QuestData.createQuestFromId(qid);
            qm.addQuest(q);
        }
        return q;
    }

    /** @return the current week, as a record value (the Thursday it started) */
    public static String weekKey() {
        return weekStart().toString();
    }

    /** @return this week's clears (an older week's count reads 0) */
    public static int clears(Char chr, int qid) {
        Quest q = record(chr, qid);
        String last = q.getProperty("lastDate");
        if (last == null || last.isEmpty()) {
            return 0;
        }
        try {
            if (LocalDate.parse(last, DATE).isBefore(weekStart())) {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
        try {
            return Math.min(Integer.parseInt(q.getProperty("wCount")), WEEKLY_CLEARS);
        } catch (Exception e) {
            return 0;
        }
    }

    /** setting arcane_weekly_unlimited: no weekly limit on clears or instant clears */
    public static boolean unlimited() {
        return net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.ARCANE_WEEKLY_UNLIMITED);
    }

    public static boolean canClear(Char chr, int qid) {
        return unlimited() || clears(chr, qid) < WEEKLY_CLEARS;
    }

    /** Counts a clear for this week (and marks the content as cleared once, for the instant clears). */
    public static void addClear(Char chr, int qid) {
        int instant = instantClears(chr, qid);
        // the record (shown by the client) stays within the weekly cap; the unlimited setting still lets runs in
        int n = Math.min(clears(chr, qid) + 1, WEEKLY_CLEARS);
        var sm = chr.getScriptManager();
        sm.setQRValue(qid, "wCount", String.valueOf(n));
        sm.setQRValue(qid, "lastDate", net.swordie.ms.util.ResetClock.today().format(DATE));
        sm.setQRValue(qid, "iCount", String.valueOf(instant));
        sm.setQRValue(qid, "first", "1");
    }

    // ---------------------------------------------------------------------------------------------- instant clears

    /** The weekly contents in region order (Etc.wz MapleScheduler): Erda Spectrum, Hungry Muto, Midnight Chaser,
     * Spirit Savior, Ranheim Defense, Esfera Guardian */
    public static final int[] CONTENTS = {34170, 34222, 34341, 16214, 39077, 34596};

    /** The six weekly minigames are behind ServerFeatures ARCANE_WEEKLY_MINIGAMES */
    public static boolean isOpen() {
        return net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.ARCANE_WEEKLY_MINIGAMES);
    }

    public static boolean everCleared(Char chr, int qid) {
        return "1".equals(record(chr, qid).getProperty("first"));
    }

    /** @return this week's instant clears of a content */
    public static int instantClears(Char chr, int qid) {
        if (clears(chr, qid) == 0) {
            return 0; // a new week (or nothing yet)
        }
        try {
            return Integer.parseInt(record(chr, qid).getProperty("iCount"));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * GMS since the Savior update: each of the next two regions' contents the character has cleared lets it finish one
     * run of this one at once (Spirit Savior: Ranheim Defense 1, Esfera Guardian 2), once the content itself has
     * been cleared. Instant runs count as weekly clears.
     * @return how many instant clears are left this week
     */
    public static int instantLeft(Char chr, int qid) {
        int idx = -1;
        for (int i = 0; i < CONTENTS.length; i++) {
            if (CONTENTS[i] == qid) {
                idx = i;
            }
        }
        if (idx < 0 || !everCleared(chr, qid) || !isOpen()) {
            return 0;
        }
        int allowed = 0;
        for (int i = idx + 1; i <= idx + 2 && i < CONTENTS.length; i++) {
            if (everCleared(chr, CONTENTS[i])) {
                allowed++;
            }
        }
        if (unlimited()) {
            return allowed;
        }
        int left = Math.min(allowed - instantClears(chr, qid), WEEKLY_CLEARS - clears(chr, qid));
        return Math.max(0, left);
    }

    /** Uses an instant clear: counts it for the week. @return whether one was left */
    public static boolean useInstant(Char chr, int qid) {
        if (instantLeft(chr, qid) <= 0) {
            return false;
        }
        int instant = instantClears(chr, qid) + 1;
        addClear(chr, qid);
        chr.getScriptManager().setQRValue(qid, "iCount", String.valueOf(instant));
        return true;
    }
}
