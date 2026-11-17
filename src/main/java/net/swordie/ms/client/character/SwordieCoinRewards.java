package net.swordie.ms.client.character;

import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.BossCooldown;
import net.swordie.ms.enums.FirstEnterRewardType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Boss clear, rebirth and Swordie Coin rewards.
 * <p>
 * A boss fight's clear (the monster that gives the boss clear NX, {@link MobConstants#isBossClearNxMob}) gives Charm
 * EXP and the first-clear announcement to everyone who did damage and their party members in the map. A rebirth
 * gives 1 Fame.
 * <p>
 * While the swordie_coins setting is on, coins come from: the first clear of each weekly boss per account per week
 * (monthly for the Black Mage, the tier of the difficulty cleared), level 200/250/260/270 (once per account), Maple
 * Achievements (score / 20, once per account), the Daily Gift's coin days (DailyGiftModule), rebirths and Polo's and
 * Fritto's bounty minigames ({@link #giveBountyCoins}). What an account has been paid is in coin_rewards. Coins that
 * don't fit go to the gift box.
 */
public final class SwordieCoinRewards {

    private static final Logger log = LogManager.getLogger(SwordieCoinRewards.class);

    /**
     * The old coin sources (the lie detector, event rewards) stay off; the sources above, the bounty minigames
     * included, follow the swordie_coins setting instead.
     */
    public static final boolean COINS_ON = false;

    public static final int REBIRTH_COINS = 100;

    /** Charm EXP for every boss clear (Charm level 30, which opens the pocket slot, takes about 11,000). */
    public static final int BOSS_CHARM_EXP = 2000;

    public static final String TABLE = "coin_rewards";
    public static final String EVER = "ever";

    /** Level milestones and their coins, paid to the first character on the account to reach each. */
    public static final int[][] LEVEL_COINS = {{200, 50}, {250, 100}, {260, 150}, {270, 200}};
    public static final int ACHIEVEMENT_SCORE_PER_COIN = 20;
    public static final int ACHIEVEMENT_MAX_COINS = 5;

    /** A boss clear that pays coins: the weekly (or monthly) entry it counts for, and its tier's coins. */
    public record BossPay(String group, int coins, BossCooldown.Limit limit) {
    }

    private static final Map<String, Integer> TIERS = new HashMap<>();
    private static final Map<Integer, BossPay> BOSS_PAY = new HashMap<>();

    static {
        tier(2, "CygnusEasy", "Cygnus", "ZakumHard", "HillaHard", "PinkBeanHard", "QueenHard", "PierreHard",
                "VonBonHard", "MagnusHard", "PapulatusHard");
        tier(4, "VellumHard", "Akechi");
        tier(8, "Lotus", "Damien", "GuardianAngelSlime", "LucidEasy", "Lucid", "WillEasy", "Will", "Gloom", "Darknell");
        tier(15, "LotusHard", "DamienHard", "GuardianAngelSlimeChaos", "LucidHard", "WillHard", "GloomHard",
                "DarknellHard", "VerusHilla");
        tier(25, "VerusHillaHard", "Seren");
        tier(40, "SerenHard");
        tier(60, "LotusExtreme", "SerenExtreme");
        for (BossCooldown bc : BossCooldown.values()) {
            Integer coins = TIERS.get(bc.name());
            if (coins == null || bc.getLimit() == BossCooldown.Limit.DAILY) {
                continue;
            }
            for (int id : bc.getTemplateIds()) {
                BOSS_PAY.put(id, new BossPay(bc.getGroup(), coins, bc.getLimit()));
            }
        }
        // one entry a week for every mode: the reward chest tells the mode (Easy, Normal, Chaos/Hard, Extreme)
        byChest(BossCooldown.Kalos, BossConstants.KALOS_CHEST, 25, 25, 40, 60);
        byChest(BossCooldown.Kaling, BossConstants.getKalingChests(), 25, 25, 40, 60);
        byChest(BossCooldown.FirstAdversary, BossConstants.chestsOf(BossConstants.ADVERSARY_BASE, 15), 25, 25, 40, 60);
        byChest(BossCooldown.Limbo, BossConstants.chestsOf(BossConstants.LIMBO_BASE, 34), 40, 40);
        byChest(BossCooldown.Baldrix, BossConstants.chestsOf(BossConstants.BALDRIX_BASE, 24), 40, 40);
        byChest(BossCooldown.MaleficStar, BossConstants.chestsOf(BossConstants.MALEFIC_BASE, BossConstants.MALEFIC_CHEST), 40, 60);
        byChest(BossCooldown.Jupiter, BossConstants.chestsOf(BossConstants.JUPITER_BASE, BossConstants.JUPITER_CHEST), 40, 60);
        byChest(BossCooldown.Kai, BossConstants.chestsOf(BossConstants.KAI_BASE, BossConstants.KAI_CHEST), 25, 40);
        BOSS_PAY.put(BossConstants.BLACK_MAGE_P4, new BossPay(BossCooldown.BlackMage.getGroup(), 80, BossCooldown.Limit.MONTHLY));
    }

    private static void tier(int coins, String... names) {
        for (String n : names) {
            TIERS.put(n, coins);
        }
    }

    private static void byChest(BossCooldown bc, int[] chests, int... coins) {
        for (int i = 0; i < chests.length && i < coins.length; i++) {
            BOSS_PAY.put(chests[i], new BossPay(bc.getGroup(), coins[i], bc.getLimit()));
        }
    }

    private SwordieCoinRewards() {
    }

    /** Whether Swordie Coins are on (the swordie_coins setting). */
    public static boolean on() {
        return ServerFeatures.on(ServerFeatures.Feature.SWORDIE_COINS);
    }

    public static BossPay bossPay(int templateId) {
        return BOSS_PAY.get(templateId);
    }

    // -------------------------------------------------------------------------------------------- the coin_rewards table

    /** The week a clear counts for: the date of the last Thursday reset (ResetClock), as the weekly bosses. */
    public static String week() {
        return net.swordie.ms.util.ResetClock.weekStart(DayOfWeek.THURSDAY).format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    /** The month (yyyyMM) of the reset day. */
    public static String monthUtc() {
        return net.swordie.ms.util.ResetClock.today("yyyyMM");
    }

    /** @return the amount stored for this record, or -1 if there is none (or the table can't be read) */
    public static int stored(int accId, String kind, String key, String period) {
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT amount FROM " + TABLE
                     + " WHERE accid = ? AND kind = ? AND rkey = ? AND period = ?")) {
            ps.setInt(1, accId);
            ps.setString(2, kind);
            ps.setString(3, key);
            ps.setString(4, period);
            try (ResultSet rs = ps.executeQuery()) {
                int res = rs.next() ? rs.getInt(1) : -1;
                con.rollback();
                return res;
            }
        } catch (Exception e) {
            log.error("Could not read " + TABLE + " " + kind + "/" + key + " of account " + accId, e);
            return Integer.MAX_VALUE; // treated as already paid / at the cap, so nothing is given twice
        }
    }

    /** Saves the amount for this record. @return whether it was saved */
    public static boolean store(int accId, String kind, String key, String period, int amount) {
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO " + TABLE
                     + " (accid, kind, rkey, period, amount) VALUES (?, ?, ?, ?, ?)"
                     + " ON DUPLICATE KEY UPDATE amount = VALUES(amount)")) {
            ps.setInt(1, accId);
            ps.setString(2, kind);
            ps.setString(3, key);
            ps.setString(4, period);
            ps.setInt(5, amount);
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (Exception e) {
            log.error("Could not save " + TABLE + " " + kind + "/" + key + " of account " + accId, e);
            return false;
        }
    }

    /** Removes a record (the probe uses it to test a source again). */
    public static void clear(int accId, String kind, String key, String period) {
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM " + TABLE
                     + " WHERE accid = ? AND kind = ? AND rkey = ? AND period = ?")) {
            ps.setInt(1, accId);
            ps.setString(2, kind);
            ps.setString(3, key);
            ps.setString(4, period);
            ps.executeUpdate();
            con.commit();
        } catch (Exception e) {
            log.error("Could not clear " + TABLE + " " + kind + "/" + key + " of account " + accId, e);
        }
    }

    /** Pays once per account and record. @return whether it was paid now */
    private static synchronized boolean payOnce(Char chr, String kind, String key, String period, int coins,
                                                String reason) {
        int accId = chr.getAccount().getId();
        if (stored(accId, kind, key, period) >= 0 || !store(accId, kind, key, period, coins)) {
            return false;
        }
        give(chr, coins, reason);
        return true;
    }

    /** Players (and the script probe's character, which tests this); never the other bots. */
    public static boolean earnsCoins(Char chr) {
        return chr != null && chr.getAccount() != null
                && (!ServerHooks.isAutomated(chr) || ServerHooks.isTestCharacter(chr));
    }

    private static boolean eligible(Char chr) {
        return earnsCoins(chr) && on();
    }

    // ------------------------------------------------------------------------------------------------------- sources

    /** Gives a boss clear's Charm EXP and says so when it opens the pocket slot (the client opens it at Charm 30). */
    private static void giveBossCharm(Char chr) {
        boolean wasOpen = chr.isPocketSlotOpen();
        chr.addTraitExp(net.swordie.ms.enums.Stat.charmEXP, BOSS_CHARM_EXP);
        if (!wasOpen && chr.isPocketSlotOpen()) {
            chr.chatMessage("Your Charm reached level " + Char.POCKET_CHARM_LEVEL
                    + ": your pocket slot is open. @upgrades can put a pocket item on now.");
        }
    }

    /**
     * Called when a monster dies: the boss clear rewards if it was a boss fight's clear, and the weekly boss coins.
     */
    public static void onMobKilled(Mob mob) {
        if (mob == null || mob.getField() == null) {
            return;
        }
        boolean clear = MobConstants.isBossClearNxMob(mob.getTemplateId());
        BossPay pay = bossPay(mob.getTemplateId());
        if (!clear && pay == null) {
            return;
        }
        List<Char> here = creditedInField(mob);
        if (pay != null) {
            for (Char chr : here) {
                try {
                    giveBossCoins(chr, pay);
                } catch (Exception e) {
                    log.error("Could not give the boss coins to " + chr.getName(), e);
                }
            }
        }
        if (!clear) {
            return;
        }
        // GMS: a cleared boss fight leaves the party 5 minutes (CLOCK 300) to loot and leave
        net.swordie.ms.world.field.instance.Instance instance = here.isEmpty() ? null : here.get(0).getInstance();
        if (instance != null && instance.getRemainingTime() > 300_000) {
            instance.setTimeout(300);
        }
        try {
            BossFirstClearModule.onBossCleared(mob, here); // "[First Clear] ..." to the world
        } catch (Exception e) {
            log.error("Could not check the boss first clear of " + mob.getTemplateId(), e);
        }
        for (Char chr : here) {
            try {
                giveBossCharm(chr);
            } catch (Exception e) {
                log.error("Could not give the boss Charm EXP to " + chr.getName(), e);
            }
        }
    }

    /** Everyone who damaged the monster and their party members, still in its map: who a boss clear counts for. */
    public static List<Char> creditedInField(Mob mob) {
        Set<Char> winners = new LinkedHashSet<>();
        for (var entry : mob.getDamageDone().entrySet()) {
            Char chr = entry.getKey();
            if (chr == null || entry.getValue() == null || entry.getValue() <= 0) {
                continue;
            }
            winners.add(chr);
            if (chr.getParty() != null) {
                winners.addAll(chr.getParty().getPartyMembersInField(chr));
            }
        }
        List<Char> here = new ArrayList<>();
        for (Char chr : winners) {
            if (chr.getField() == mob.getField()) {
                here.add(chr);
            }
        }
        return here;
    }

    /**
     * The first clear of a weekly boss this week (the Black Mage: this month) on the account pays its tier; a harder
     * difficulty of the same boss later in the week pays the difference.
     */
    public static synchronized int giveBossCoins(Char chr, BossPay pay) {
        if (!eligible(chr) || pay == null) {
            return 0;
        }
        int accId = chr.getAccount().getId();
        String period = pay.limit() == BossCooldown.Limit.MONTHLY ? monthUtc() : week();
        int paid = stored(accId, "boss", pay.group(), period);
        int due = pay.coins() - Math.max(0, paid);
        if (due <= 0 || !store(accId, "boss", pay.group(), period, pay.coins())) {
            return 0;
        }
        give(chr, due, paid > 0 ? "a harder clear this week" : (pay.limit() == BossCooldown.Limit.MONTHLY
                ? "first clear this month" : "first clear this week"));
        return due;
    }

    /** Level milestones: the first character on the account to reach each. */
    public static void onLevelUp(Char chr, int startLevel, int level) {
        if (!eligible(chr)) {
            return;
        }
        for (int[] m : LEVEL_COINS) {
            if (startLevel < m[0] && level >= m[0]) {
                try {
                    payOnce(chr, "level", String.valueOf(m[0]), EVER, m[1], "level " + m[0]);
                } catch (Exception e) {
                    log.error("Could not give the level " + m[0] + " coins to " + chr.getName(), e);
                }
            }
        }
    }

    public static int achievementCoins(int score) {
        return score <= 0 ? 0 : Math.max(1, Math.min(ACHIEVEMENT_MAX_COINS, score / ACHIEVEMENT_SCORE_PER_COIN));
    }

    /** A Maple Achievement was completed (once per account). */
    public static void onAchievement(Char chr, int achievementId, int score) {
        int coins = achievementCoins(score);
        if (coins <= 0 || !eligible(chr)) {
            return;
        }
        try {
            payOnce(chr, "achievement", String.valueOf(achievementId), EVER, coins, "Maple Achievement");
        } catch (Exception e) {
            log.error("Could not give the achievement coins to " + chr.getName(), e);
        }
    }

    /**
     * The Daily Gift's coin days: whether this character's gift (its day-th of the month) still pays coins, i.e.
     * no other character on the account got that day's coins this month.
     */
    public static boolean giftCoinsAvailable(Char chr, int day, String month) {
        return eligible(chr) && stored(chr.getAccount().getId(), "gift", String.valueOf(day), month) < 0;
    }

    /** Marks a Daily Gift coin day as paid for the account. */
    public static void markGiftCoins(Char chr, int day, String month, int coins) {
        store(chr.getAccount().getId(), "gift", String.valueOf(day), month, coins);
    }

    /**
     * Called after a successful rebirth.
     */
    public static void onRebirth(Char chr) {
        if (eligible(chr)) {
            try {
                give(chr, REBIRTH_COINS, "rebirth");
            } catch (Exception e) {
                log.error("Could not give the rebirth Swordie Coins to " + chr.getName(), e);
            }
        }
        try {
            // one Fame per rebirth
            chr.addStatAndSendPacket(net.swordie.ms.enums.Stat.pop, 1);
            chr.chatMessage("+1 Fame for your rebirth.");
        } catch (Exception e) {
            log.error("Could not give the rebirth Fame to " + chr.getName(), e);
        }
    }

    /**
     * Contributed by Arixal (from GitLab).
     *
     * Polo's and Fritto's bounty minigames (Polo_Out, Fritto_Out) pay as they're left, by the damage dealt or the
     * stages finished. Like every other source, nothing while the swordie_coins setting is off.
     *
     * @return the coins given
     */
    public static int giveBountyCoins(Char chr, int amount) {
        int coins = bountyCoins(chr, amount, on());
        give(chr, coins, "bounty");
        return coins;
    }

    /** @param coinsOn whether the swordie_coins setting is on */
    static int bountyCoins(Char chr, int amount, boolean coinsOn) {
        return coinsOn && earnsCoins(chr) ? Math.max(0, amount) : 0;
    }

    /** Gives coins, to the gift box when the Etc inventory is full. */
    public static void give(Char chr, int amount, String reason) {
        if (amount <= 0) {
            return;
        }
        if (chr.canHold(ItemConstants.SWORDIE_COIN, amount)) {
            chr.addItemToInventory(ItemConstants.SWORDIE_COIN, amount);
            chr.chatMessage(String.format("You received %d Swordie Coins (%s).", amount, reason));
        } else {
            chr.addFirstEnterReward(new FirstEnterReward(chr.getId(), ItemConstants.SWORDIE_COIN, amount,
                    FirstEnterRewardType.GameItem, String.format("%d Swordie Coins (%s).", amount, reason)));
            chr.checkFirstEnterReward();
            chr.chatMessage(String.format("Your Etc inventory is full, so your %d Swordie Coins (%s) were sent to "
                    + "your gift box.", amount, reason));
        }
    }
}
