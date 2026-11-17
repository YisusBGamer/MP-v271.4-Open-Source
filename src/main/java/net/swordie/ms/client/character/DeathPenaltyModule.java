package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.life.mob.Mob;

/**
 * The death debuff, GMS v271: no EXP is lost; for 10 minutes (minus the death penalty duration reduction stat) hunting
 * EXP and the drop rate are 80% lower. DEATH_PENALTY 0 sets it, 1 updates it on every map change, 2 clears it (a
 * Safety Charm). Not given where a death is part of the content: boss and instance fields, death count fields.
 */
public class DeathPenaltyModule {
    /** Server-only record: "end=<epoch ms>;total=<s>". */
    public static final int RECORD = 89999967;
    public static final int SAFETY_CHARM = 5130000;

    public static boolean isExempt(Char chr) {
        if (net.swordie.ms.ext.ServerHooks.isAutomated(chr)) {
            return true; // test bots
        }
        if (chr.getLevel() < 10 || JobConstants.isBeginnerJob(chr.getJob())) {
            return true;
        }
        if (chr.getDeathCount() > 0 || chr.getInstance() != null || chr.getField() == null) {
            return true;
        }
        for (Mob mob : chr.getField().getMobs()) {
            if (mob.isBoss()) {
                return true;
            }
        }
        return false;
    }

    public static void onDeath(Char chr) {
        if (isExempt(chr)) {
            return;
        }
        int dec = Math.max(0, Math.min(100, chr.getTotalStat(BaseStat.deathPenaltyDurationDecR)));
        int total = GameConstants.DEATH_PENALTY_MAX_DURATION * (100 - dec) / 100;
        if (total <= 0) {
            return;
        }
        long end = System.currentTimeMillis() + total * 1000L;
        chr.getQuestManager().setHiddenRecord(RECORD, "end=" + end + ";total=" + total);
        chr.write(WvsContext.deathPenalty(true, total, total, GameConstants.DEATH_PENALTY_EXP_DEBUFF,
                GameConstants.DEATH_PENALTY_DROP_DEBUFF));
    }

    /** Seconds left, 0 when none. */
    public static int remaining(Char chr) {
        if (chr.getQuestManager() == null) {
            return 0;
        }
        String rec = chr.getQuestManager().getHiddenRecord(RECORD);
        if (rec == null || rec.isEmpty()) {
            return 0;
        }
        long end = 0;
        for (String part : rec.split(";")) {
            if (part.startsWith("end=")) {
                try {
                    end = Long.parseLong(part.substring(4));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        long left = (end - System.currentTimeMillis()) / 1000;
        return left > 0 ? (int) left : 0;
    }

    private static int total(Char chr) {
        String rec = chr.getQuestManager().getHiddenRecord(RECORD);
        if (rec != null) {
            for (String part : rec.split(";")) {
                if (part.startsWith("total=")) {
                    try {
                        return Integer.parseInt(part.substring(6));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        return GameConstants.DEATH_PENALTY_MAX_DURATION;
    }

    private static boolean isExemptBot(Char chr) {
        return net.swordie.ms.ext.ServerHooks.isAutomated(chr);
    }

    public static boolean isActive(Char chr) {
        return remaining(chr) > 0;
    }

    /** On every map change while it lasts (GMS: type 1 with the time left). */
    public static void onFieldEnter(Char chr) {
        int left = remaining(chr);
        if (left > 0) {
            chr.write(WvsContext.deathPenalty(false, total(chr), left, GameConstants.DEATH_PENALTY_EXP_DEBUFF,
                    GameConstants.DEATH_PENALTY_DROP_DEBUFF));
        } else if (chr.getQuestManager() != null && chr.getQuestManager().getHiddenRecord(RECORD) != null
                && !chr.getQuestManager().getHiddenRecord(RECORD).isEmpty()) {
            chr.getQuestManager().setHiddenRecord(RECORD, "");
        }
    }

    /** Hunting EXP and drop rate multiplier. */
    public static double rateFactor(Char chr) {
        return chr != null && !isExemptBot(chr) && isActive(chr) ? (100 - GameConstants.DEATH_PENALTY_EXP_DEBUFF) / 100D : 1D;
    }

    public static double dropFactor(Char chr) {
        return chr != null && !isExemptBot(chr) && isActive(chr) ? (100 - GameConstants.DEATH_PENALTY_DROP_DEBUFF) / 100D : 1D;
    }

    /** Safety Charm: clears the debuff (GMS: DEATH_PENALTY 2, 1, the charm used up). */
    public static void useSafetyCharm(Char chr, Item item) {
        if (!isActive(chr)) {
            chr.chatMessage("You don't have a death penalty to remove.");
            chr.dispose();
            return;
        }
        chr.getQuestManager().setHiddenRecord(RECORD, "");
        chr.consumeItem(item);
        chr.write(WvsContext.deathPenaltyCleared());
        chr.dispose();
    }
}
