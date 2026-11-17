package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.quest.progress.QuestProgressMobRequirement;
import net.swordie.ms.client.character.quest.progress.QuestProgressRequirement;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.CommodityItem;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.util.ResetClock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Boss Reward Points (Etc.wz BossMileageInfo). Each entry names its [Maple Rewards] quest (charQuestID), whose kill
 * requirement is the boss mob; the first kill of that boss each day on the account (one world) pays mileageAmount,
 * as the quest's dayByDay "once a day per world" rule. The points are the Cash Shop's third balance and pay up to a
 * commodity's mileageRate percent of an NX price.
 */
public final class BossMileage {

    private static final Logger log = LogManager.getLogger(BossMileage.class);
    private static final String CLAIM_PREFIX = "mileage_";
    private static final int PERCENT = 100;
    // GMS Reward Point rules, likely but not confirmed (plans/research-boss-rewards-1105.md, section 2; digitaltq.com
    // maplestory-reward-points-boss-rewards, 2026-02-25). Points here go straight to the balance, so the
    // deposit-within-7-days rule (maplestorywiki.net Cash Shop) has no step to apply to.
    /** Most Reward Points one account earns in a day. */
    public static final int DAILY_EARN_CAP = 5_000;
    /** Most Reward Points one account earns in a month. */
    public static final int MONTHLY_EARN_CAP = 50_000;
    /** Deposited points expire at the end of this many months after the month they were earned. */
    public static final int EXPIRY_MONTHS_AFTER_EARNING = 1;

    /** One BossMileageInfo row; worldQuestId and key are the client's per-world record and its key. */
    public record Entry(String boss, String difficulty, int amount, int worldQuestId, String key) {
    }

    private static volatile Map<Integer, List<Entry>> byMob;

    private BossMileage() {
    }

    /** @return boss mob id -> its mileage entries */
    public static Map<Integer, List<Entry>> byMob() {
        Map<Integer, List<Entry>> res = byMob;
        if (res == null) {
            synchronized (BossMileage.class) {
                if (byMob == null) {
                    byMob = load();
                }
                res = byMob;
            }
        }
        return res;
    }

    private static Map<Integer, List<Entry>> load() {
        Map<Integer, List<Entry>> res = new HashMap<>();
        WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("BossMileageInfo.img");
        if (root == null) {
            log.warn("Etc.wz has no BossMileageInfo.img: boss kills give no Reward Points");
            return Map.of();
        }
        for (WzObject<?, ?> row : root) {
            int questId = intOf(row.getChild("charQuestID"));
            int mobId = killMob(questId);
            Entry e = new Entry(strOf(row.getChild("bossName")), strOf(row.getChild("difficulty")),
                    intOf(row.getChild("mileageAmount")), intOf(row.getChild("worldQuestID")),
                    strOf(row.getChild("worldQuestCheckString")));
            if (mobId <= 0 || e.amount() <= 0 || e.key().isEmpty()) {
                log.warn("BossMileageInfo {} ({} {}): no kill mob in quest {} or no amount; skipped", row.getName(),
                        e.boss(), e.difficulty(), questId);
                continue;
            }
            res.computeIfAbsent(mobId, k -> new ArrayList<>()).add(e);
        }
        Map<Integer, List<Entry>> frozen = new HashMap<>();
        res.forEach((k, v) -> frozen.put(k, List.copyOf(v)));
        return Map.copyOf(frozen);
    }

    private static int killMob(int questId) {
        QuestInfo qi = questId > 0 ? QuestData.getQuestInfoById(questId) : null;
        if (qi == null) {
            return 0;
        }
        for (QuestProgressRequirement req : qi.getQuestProgressRequirements()) {
            if (req instanceof QuestProgressMobRequirement mobReq) {
                return mobReq.getMobID();
            }
        }
        return 0;
    }

    /** Pays the day's Reward Points of this boss to everyone the kill counts for (not in practice runs). */
    public static void onMobKilled(Mob mob) {
        List<Entry> entries = byMob().get(mob.getTemplateId());
        if (entries == null) {
            return;
        }
        for (Char chr : SwordieCoinRewards.creditedInField(mob)) {
            if (chr.inBossPracticeRun()) {
                continue;
            }
            for (Entry e : entries) {
                award(chr, e);
            }
        }
    }

    private static synchronized void award(Char chr, Entry e) {
        if (chr.getAccount() == null) {
            return;
        }
        expire(chr);
        int room = roomToEarn(chr.getAccount().getId());
        if (room <= 0) {
            if (room == 0) {
                chr.chatMessage("You have reached the Reward Point limit for today or this month.");
            }
            return;
        }
        if (!AccountWeekly.claimToday(chr, claimKey(e))) {
            return;
        }
        int amount = Math.min(e.amount(), room);
        chr.setRewardPoints((int) Math.min(Integer.MAX_VALUE, (long) Math.max(0, chr.getRewardPoints()) + amount));
        chr.chatMessage(String.format("You earned %d Reward Points for defeating %s %s (%d total).", amount,
                capitalized(e.difficulty()), e.boss(), chr.getRewardPoints()));
        log.info("Reward Points +{} for {} {} to character {} (now {})", amount, e.difficulty(), e.boss(),
                chr.getId(), chr.getRewardPoints());
    }

    private static String claimKey(Entry e) {
        return CLAIM_PREFIX + e.worldQuestId() + "_" + e.key();
    }

    /** @return the points a day claim stands for, 0 for an unknown claim */
    private static int amountOf(String claim) {
        for (List<Entry> entries : byMob().values()) {
            for (Entry e : entries) {
                if (claimKey(e).equals(claim)) {
                    return e.amount();
                }
            }
        }
        return 0;
    }

    /** @return Reward Points the claims from one day to another stand for, or -1 when the database failed */
    private static long earned(int accountId, int charId, LocalDate from, LocalDate to) {
        List<String> claims = AccountWeekly.dailyClaims(accountId, charId, CLAIM_PREFIX, from, to);
        if (claims == null) {
            return -1;
        }
        long sum = 0;
        for (String claim : claims) {
            sum += amountOf(claim);
        }
        return sum;
    }

    /** @return points the account can still earn under the daily and monthly caps; -1 when unknown */
    private static int roomToEarn(int accountId) {
        LocalDate today = ResetClock.today();
        long day = earned(accountId, 0, today, today);
        long month = earned(accountId, 0, today.withDayOfMonth(1), today);
        if (day < 0 || month < 0) {
            return -1;
        }
        return (int) Math.max(0, Math.min(DAILY_EARN_CAP - day, MONTHLY_EARN_CAP - month));
    }

    /**
     * Drops the points earned before the expiry window. Spending takes the oldest points first, so what is left of
     * the balance is at most what this character earned inside the window.
     */
    public static synchronized void expire(Char chr) {
        if (chr == null || chr.getAccount() == null || chr.getRewardPoints() <= 0) {
            return;
        }
        LocalDate today = ResetClock.today();
        long live = earned(chr.getAccount().getId(), chr.getId(),
                today.withDayOfMonth(1).minusMonths(EXPIRY_MONTHS_AFTER_EARNING), today);
        if (live >= 0 && chr.getRewardPoints() > live) {
            log.info("Reward Points of character {} expired: {} -> {}", chr.getId(), chr.getRewardPoints(), live);
            chr.setRewardPoints((int) live);
        }
    }

    /**
     * @return the Reward Points a purchase uses: the commodity's mileageRate percent of the cost, only when the
     * balance covers all of it (the client greys the partial box otherwise)
     */
    public static int usable(Char chr, CommodityItem item, long cost) {
        if (item == null || item.getMileageRate() <= 0 || cost <= 0) {
            return 0;
        }
        long cap = cost * item.getMileageRate() / PERCENT;
        return chr.getRewardPoints() >= cap ? (int) cap : 0;
    }

    /** Takes Reward Points for a purchase. @return false when the character doesn't have them */
    public static synchronized boolean spend(Char chr, int amount) {
        if (amount <= 0) {
            return true;
        }
        if (chr.getRewardPoints() < amount) {
            return false;
        }
        chr.setRewardPoints(chr.getRewardPoints() - amount);
        return true;
    }

    /** Puts back points taken for a purchase that then failed. */
    public static synchronized void refund(Char chr, int amount) {
        if (amount > 0) {
            chr.setRewardPoints((int) Math.min(Integer.MAX_VALUE, (long) chr.getRewardPoints() + amount));
        }
    }

    private static String capitalized(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static int intOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop ? prop.getIntValue() : 0;
    }

    private static String strOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop && prop.getValue() != null ? String.valueOf(prop.getValue()) : "";
    }
}
