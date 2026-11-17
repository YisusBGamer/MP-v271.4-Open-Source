package net.swordie.ms.world.field.instance.instancehelper.epic;

import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.connection.packet.EpicDungeonPacket;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.MessageType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.ResetClock;
import net.swordie.ms.world.field.instance.Instance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Epic Dungeons (Selazar's window, UI.wz EpicDungeonUI.img). Region 0 is High Mountain, region 1 the Angler Company,
 * region 2 Nightmare Paradise. The window (Out 2713) shows story mode until the region's quest is completed (26216 /
 * 27114 / 27143), then normal mode.
 * Its buttons come back as USER_RUN_SCRIPT 88 (story start), 89 (enter), 90 (bonus reward), 91 (express pass), each
 * with int field, int region. The weekly progress is the account's NX record (Etc.wz MapleScheduler weekly
 * epicDungeon: High Mountain record 50 clearStage 10, Angler record 58 clearStage 8, Nightmare Paradise record 63
 * clearStage 7, lastDate, reset on Thursday);
 * here it is kept per character.
 * Packet layouts: plans/epic-dungeons-re-20261027.md.
 */
public class EpicDungeon {
    public static final int REGION_HIGH_MOUNTAIN = 0;
    public static final int REGION_ANGLER = 1;
    public static final int REGION_NIGHTMARE = 2;

    public static final short RUN_REWARD = 86;
    public static final short RUN_BONUS_REWARD_OK = 87;
    public static final short RUN_STORY_START = 88;
    public static final short RUN_ENTER = 89;
    public static final short RUN_BONUS_REWARD = 90;
    public static final short RUN_EXPRESS = 91;

    public static final int HM_NX_RECORD = 50;
    public static final int ANGLER_NX_RECORD = 58;
    /**
     * server-only record: clearStage, lastDate, reward (1 = clear reward not taken yet), expbonus / erdabonus (bonus
     * tiers bought this week), everClear (1 once normal mode was cleared)
     */
    public static final int HM_RECORD = 89999995;
    public static final int HM_LEVEL = 260;
    public static final int HM_STORY_QUEST = 26216;
    public static final int HM_FIRST_STORY_QUEST = 26211;
    public static final int HM_STAGES = 10;
    public static final int HM_ENTRANCE = 410000500;
    public static final int HM_SAVE_ZONE = 993241900;
    public static final int HM_REWARD_ROOM = 993241950;
    public static final int SELAZAR = 3006700;

    /** Angler Company: the same record layout as High Mountain, in its own server-only record */
    public static final int ANGLER_RECORD = 89999996;
    public static final int ANGLER_LEVEL = 270;
    /** [Epic Dungeon] Angler Company: Selazar's tale; the window lists the Angler once it is completed */
    public static final int ANGLER_INTRO_QUEST = 27111;
    public static final int ANGLER_FIRST_STORY_QUEST = 27112;
    /** [Epic Dungeon] Outside the Building: the window shows normal mode once it is completed */
    public static final int ANGLER_STORY_QUEST = 27114;
    /** [Epic Dungeon] Delver of the Deep: Selazar's medal */
    public static final int ANGLER_MEDAL_QUEST = 27115;
    public static final int ANGLER_MEDAL = 1143379;
    public static final int ANGLER_STAGES = 8;
    public static final int ANGLER_ENTRANCE = 410007000;
    public static final int ANGLER_SAVE_ZONE = 993253200;
    public static final int ANGLER_REWARD_ROOM = 993253250;
    public static final int SELAZAR_ODIUM = 3006760;

    /** Nightmare Paradise: the same record layout, in its own server-only record */
    public static final int NP_NX_RECORD = 63;
    public static final int NP_RECORD = 89999946;
    public static final int NP_LEVEL = 280;
    /** [Epic Dungeon] Nightmare Paradise: Selazar's ballad (after the Angler medal 27115 and 38653) */
    public static final int NP_INTRO_QUEST = 27140;
    public static final int NP_INTRO_PREREQ = 38653;
    public static final int NP_FIRST_STORY_QUEST = 27141;
    /** [Epic Dungeon] Ember of Life: the window shows normal mode once it is completed */
    public static final int NP_STORY_QUEST = 27143;
    /** [Epic Dungeon] Wakebringer: Selazar's medal */
    public static final int NP_MEDAL_QUEST = 27144;
    public static final int NP_MEDAL = 1143482;
    public static final int NP_STAGES = 7;
    public static final int NP_ENTRANCE = 410007520;
    public static final int NP_SAVE_ZONE = 993271400;
    public static final int NP_REWARD_ROOM = 993271450;
    public static final int SELAZAR_NP = 3007044;

    /** One Epic Dungeon: its window region, records, entry rules and maps. */
    public enum Dungeon {
        HIGH_MOUNTAIN(REGION_HIGH_MOUNTAIN, HM_NX_RECORD, HM_RECORD, HM_LEVEL, HM_STORY_QUEST, HM_STAGES,
                HM_ENTRANCE, HM_SAVE_ZONE, HM_REWARD_ROOM),
        ANGLER(REGION_ANGLER, ANGLER_NX_RECORD, ANGLER_RECORD, ANGLER_LEVEL, ANGLER_STORY_QUEST, ANGLER_STAGES,
                ANGLER_ENTRANCE, ANGLER_SAVE_ZONE, ANGLER_REWARD_ROOM),
        NIGHTMARE(REGION_NIGHTMARE, NP_NX_RECORD, NP_RECORD, NP_LEVEL, NP_STORY_QUEST, NP_STAGES, NP_ENTRANCE,
                NP_SAVE_ZONE, NP_REWARD_ROOM);

        public final int region, nxRecord, record, level, storyQuest, stages, entrance, saveZone, rewardRoom;

        Dungeon(int region, int nxRecord, int record, int level, int storyQuest, int stages, int entrance,
                int saveZone, int rewardRoom) {
            this.region = region;
            this.nxRecord = nxRecord;
            this.record = record;
            this.level = level;
            this.storyQuest = storyQuest;
            this.stages = stages;
            this.entrance = entrance;
            this.saveZone = saveZone;
            this.rewardRoom = rewardRoom;
        }

        /** the name in messages */
        public String title() {
            return switch (this) {
                case HIGH_MOUNTAIN -> "High Mountain";
                case ANGLER -> "the Angler Company";
                case NIGHTMARE -> "Nightmare Paradise";
            };
        }

        public static Dungeon byRegion(int region) {
            for (Dungeon d : values()) {
                if (d.region == region) {
                    return d;
                }
            }
            return null;
        }

        public static Dungeon byNxRecord(int key) {
            for (Dungeon d : values()) {
                if (d.nxRecord == key) {
                    return d;
                }
            }
            return null;
        }
    }

    /** the clear reward items (Etc.wz MapleScheduler weekly epicDungeon): Sol Erda x1, Sol Erda Fragment x15 */
    public static final int[][] CLEAR_ITEMS = {{2637338, 1}, {4009550, 15}};
    /** bonus tier prices in Maple Points (tier 1, tier 2; the window shows tier 2 as the full price) */
    public static final int[] BONUS_COST = {0, 3_000, 6_000};
    /** EXP bonus tier: total EXP multiplier (the window adds EXP x (mult - 1)) */
    public static final int[] EXP_MULT = {1, 2, 3};
    /** Erda bonus tier: extra multiples of each clear item */
    public static final int[] ERDA_EXTRA = {0, 1, 2};

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yy/MM/dd");

    // ------------------------------------------------------------------------------------------------- the record

    private static LocalDate weekStart() {
        return ResetClock.weekStart(DayOfWeek.THURSDAY);
    }

    static Map<String, String> record(Char chr) {
        return record(chr, Dungeon.HIGH_MOUNTAIN);
    }

    static Map<String, String> record(Char chr, Dungeon d) {
        Map<String, String> map = new LinkedHashMap<>();
        String raw = chr.getQuestManager().getHiddenRecord(d.record);
        if (raw != null) {
            for (String part : raw.split(";")) {
                int eq = part.indexOf('=');
                if (eq > 0) {
                    map.put(part.substring(0, eq), part.substring(eq + 1));
                }
            }
        }
        return map;
    }

    private static void saveRecord(Char chr, Dungeon d, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb.append(k).append('=').append(v).append(';'));
        chr.getQuestManager().setHiddenRecord(d.record, sb.toString());
    }

    /** @return the stages cleared this week (0 once the week has turned) */
    public static int clearedStages(Char chr) {
        return clearedStages(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static int clearedStages(Char chr, Dungeon d) {
        Map<String, String> rec = record(chr, d);
        try {
            if (LocalDate.parse(rec.getOrDefault("lastDate", ""), DATE).isBefore(weekStart())) {
                return 0;
            }
            return Integer.parseInt(rec.getOrDefault("clearStage", "0"));
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean clearedThisWeek(Char chr) {
        return clearedThisWeek(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static boolean clearedThisWeek(Char chr, Dungeon d) {
        return clearedStages(chr, d) >= d.stages;
    }

    static void setClearedStages(Char chr, int stages) {
        setClearedStages(chr, Dungeon.HIGH_MOUNTAIN, stages);
    }

    static void setClearedStages(Char chr, Dungeon d, int stages) {
        Map<String, String> rec = record(chr, d);
        rec.put("clearStage", String.valueOf(stages));
        rec.put("lastDate", ResetClock.today().format(DATE));
        if (stages >= d.stages) {
            rec.put("reward", "1");
            rec.put("everClear", "1");
            rec.put("expbonus", "0");
            rec.put("erdabonus", "0");
        }
        saveRecord(chr, d, rec);
        chr.write(WvsContext.message(MessagePacket.nxRecordMessage(d.nxRecord, nxValue(chr, d))));
    }

    public static boolean hasReward(Char chr) {
        return hasReward(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static boolean hasReward(Char chr, Dungeon d) {
        return "1".equals(record(chr, d).get("reward"));
    }

    public static void takeReward(Char chr) {
        takeReward(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static void takeReward(Char chr, Dungeon d) {
        Map<String, String> rec = record(chr, d);
        rec.put("reward", "0");
        saveRecord(chr, d, rec);
    }

    /** @return the EXP (0) or Erda (1) bonus tier bought this week */
    public static int bonusTier(Char chr, boolean erda) {
        return bonusTier(chr, Dungeon.HIGH_MOUNTAIN, erda);
    }

    public static int bonusTier(Char chr, Dungeon d, boolean erda) {
        if (!clearedThisWeek(chr, d)) {
            return 0;
        }
        try {
            return Math.max(0, Math.min(2, Integer.parseInt(record(chr, d).getOrDefault(erda ? "erdabonus" : "expbonus", "0"))));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void setBonusTiers(Char chr, Dungeon d, int exp, int erda) {
        Map<String, String> rec = record(chr, d);
        rec.put("expbonus", String.valueOf(exp));
        rec.put("erdabonus", String.valueOf(erda));
        saveRecord(chr, d, rec);
    }

    public static boolean everCleared(Char chr) {
        return everCleared(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static boolean everCleared(Char chr, Dungeon d) {
        return "1".equals(record(chr, d).get("everClear")) || clearedThisWeek(chr, d);
    }

    /**
     * The window's NX record (50 / 58): reward=1 once this week's run is cleared (express pass off, tooltip "You
     * cleared ... this week" with cName / worldID), expbonus / erdabonus 0-2 (bonus reward button off at 2/2).
     */
    private static String nxValue(Char chr, Dungeon d) {
        int stages = clearedStages(chr, d);
        if (stages == 0) {
            return "";
        }
        String last = record(chr, d).getOrDefault("lastDate", "");
        StringBuilder sb = new StringBuilder("clearStage=" + stages + ";lastDate=" + last);
        if (stages >= d.stages) {
            sb.append(";reward=1;expbonus=").append(bonusTier(chr, d, false))
                    .append(";erdabonus=").append(bonusTier(chr, d, true))
                    .append(";cName=").append(chr.getName())
                    .append(";worldID=").append(chr.getClient() == null ? 0 : chr.getClient().getWorldId());
        }
        return sb.toString();
    }

    public static void sendRecord(Char chr) {
        sendRecord(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static void sendRecord(Char chr, Dungeon d) {
        chr.write(WvsContext.message(MessagePacket.nxRecordMessage(d.nxRecord, nxValue(chr, d))));
    }

    /**
     * The clear EXP: GMS gives 260.9B at Lv. 260 up to 759.6B at Lv. 294+; the steps between are filled in evenly
     * (the per-level GMS table isn't in the data).
     */
    public static long clearExp(int level) {
        int lv = Math.max(HM_LEVEL, Math.min(level, 294));
        return 260_900_000_000L + (759_600_000_000L - 260_900_000_000L) * (lv - HM_LEVEL) / (294 - HM_LEVEL);
    }

    /** Angler Company clear EXP: 600B at Lv. 270 up to 1.5T at Lv. 299+, evenly between (this server's choice) */
    public static long anglerClearExp(int level) {
        int lv = Math.max(ANGLER_LEVEL, Math.min(level, 299));
        return 600_000_000_000L + (1_500_000_000_000L - 600_000_000_000L) * (lv - ANGLER_LEVEL) / (299 - ANGLER_LEVEL);
    }

    /** Nightmare Paradise clear EXP: 1.2T at Lv. 280 up to 2T at Lv. 299+, evenly between (this server's choice) */
    public static long nightmareClearExp(int level) {
        int lv = Math.max(NP_LEVEL, Math.min(level, 299));
        return 1_200_000_000_000L + (2_000_000_000_000L - 1_200_000_000_000L) * (lv - NP_LEVEL) / (299 - NP_LEVEL);
    }

    public static long clearExp(Dungeon d, int level) {
        return switch (d) {
            case ANGLER -> anglerClearExp(level);
            case NIGHTMARE -> nightmareClearExp(level);
            default -> clearExp(level);
        };
    }

    // -------------------------------------------------------------------------------------------------- packets

    /** Selazar's window, showing the given region first */
    public static OutPacket uiOpen(Char chr, int region) {
        return EpicDungeonPacket.uiOpen(region,
                new EpicDungeonPacket.RegionRecord(REGION_HIGH_MOUNTAIN, HM_NX_RECORD, nxValue(chr, Dungeon.HIGH_MOUNTAIN)),
                new EpicDungeonPacket.RegionRecord(REGION_ANGLER, ANGLER_NX_RECORD, nxValue(chr, Dungeon.ANGLER)),
                new EpicDungeonPacket.RegionRecord(REGION_NIGHTMARE, NP_NX_RECORD, nxValue(chr, Dungeon.NIGHTMARE)));
    }

    // ------------------------------------------------------------------------------------------------- requests

    /** Selazar: opens the window with the given region first */
    public static void openWindow(Char chr, int region) {
        sendRecord(chr, Dungeon.HIGH_MOUNTAIN);
        sendRecord(chr, Dungeon.ANGLER);
        sendRecord(chr, Dungeon.NIGHTMARE);
        chr.write(uiOpen(chr, region));
    }

    /**
     * USER_RUN_SCRIPT 86-91. 88-91 (Selazar's window): int field, int region. 86 (clear reward window) and 87 (bonus
     * reward window): int field, int record key, byte EXP tier, byte Erda tier.
     */
    public static void handleRunScript(Char chr, short type, InPacket inPacket) {
        int fieldId = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        int second = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        if (fieldId != chr.getFieldID()) {
            chr.dispose();
            return;
        }
        if (type == RUN_REWARD || type == RUN_BONUS_REWARD_OK) {
            int expTier = inPacket.getUnreadAmount() >= 1 ? inPacket.decodeByte() : 0;
            int erdaTier = inPacket.getUnreadAmount() >= 1 ? inPacket.decodeByte() : 0;
            Dungeon d = Dungeon.byNxRecord(second);
            if (d == null) {
                chr.dispose();
                return;
            }
            if (type == RUN_REWARD) {
                claimClearReward(chr, d, expTier, erdaTier);
            } else {
                buyBonus(chr, d, expTier, erdaTier);
            }
            return;
        }
        Dungeon d = Dungeon.byRegion(second);
        if (d == null) {
            chr.dispose();
            return;
        }
        switch (type) {
            case RUN_STORY_START -> enter(chr, d, true);
            case RUN_ENTER -> enter(chr, d, false);
            case RUN_BONUS_REWARD -> openBonusWindow(chr, d);
            case RUN_EXPRESS -> express(chr, d);
            default -> chr.dispose();
        }
    }

    // ---------------------------------------------------------------------------------------------- the rewards

    private static boolean validTier(int tier) {
        return tier >= 0 && tier <= 2;
    }

    private static boolean hm(Dungeon d) {
        return d == Dungeon.HIGH_MOUNTAIN;
    }

    /** Out 212: the clear reward window (picks EXP / Erda bonus tiers, answers with run script 86) */
    public static void openClearWindow(Char chr) {
        openClearWindow(chr, Dungeon.HIGH_MOUNTAIN);
    }

    public static void openClearWindow(Char chr, Dungeon d) {
        if (!hasReward(chr, d)) {
            return;
        }
        chr.write(rewardWindow(OutHeader.EPIC_DUNGEON_CLEAR_REWARD, chr, d, false));
    }

    /** run script 86: the clear reward with the bonus tiers picked in the window */
    private static void claimClearReward(Char chr, Dungeon d, int expTier, int erdaTier) {
        if (!hasReward(chr, d) || !validTier(expTier) || !validTier(erdaTier)) {
            chr.dispose();
            return;
        }
        int cost = BONUS_COST[expTier] + BONUS_COST[erdaTier];
        if (cost > chr.getNX()) {
            popup(chr, "You don't have enough Maple Points for that bonus.");
            return;
        }
        int mult = 1 + ERDA_EXTRA[erdaTier];
        if (!canHoldItems(chr, mult)) {
            popup(chr, hm(d) ? "Make room in your USE and ETC inventories, then talk to Selazar at the summit."
                    : "Make room in your USE and ETC inventories, then talk to Selazar again.");
            return;
        }
        takeReward(chr, d);
        setBonusTiers(chr, d, expTier, erdaTier);
        chr.deductNX(cost);
        giveReward(chr, clearExp(d, chr.getLevel()) * EXP_MULT[expTier], mult);
        sendRecord(chr, d);
        chr.dispose();
        if (chr.getFieldID() == d.rewardRoom && chr.getInstance() != null) {
            chr.getInstance().removeChar(chr, d.entrance, 0, true, false);
        }
    }

    /** run script 90 (Selazar's window): the bonus reward window, after this week's clear reward was taken */
    private static void openBonusWindow(Char chr, Dungeon d) {
        if (!clearedThisWeek(chr, d)) {
            popup(chr, hm(d) ? "Reach the summit of High Mountain this week first."
                    : "Clear " + d.title() + " this week first.");
            return;
        }
        if (hasReward(chr, d)) {
            popup(chr, hm(d) ? "Claim your clear reward at the summit first." : "Claim your clear reward first.");
            return;
        }
        if (bonusTier(chr, d, false) >= 2 && bonusTier(chr, d, true) >= 2) {
            popup(chr, "You've already taken every bonus reward this week.");
            return;
        }
        sendRecord(chr, d);
        chr.write(rewardWindow(OutHeader.EPIC_DUNGEON_BONUS_REWARD, chr, d, true));
        chr.dispose();
    }

    /** run script 87: raises this week's bonus tiers, paying and giving only the difference */
    private static void buyBonus(Char chr, Dungeon d, int expTier, int erdaTier) {
        int curExp = bonusTier(chr, d, false);
        int curErda = bonusTier(chr, d, true);
        if (!clearedThisWeek(chr, d) || hasReward(chr, d) || !validTier(expTier) || !validTier(erdaTier)
                || expTier < curExp || erdaTier < curErda || (expTier == curExp && erdaTier == curErda)) {
            chr.dispose();
            return;
        }
        int cost = BONUS_COST[expTier] - BONUS_COST[curExp] + BONUS_COST[erdaTier] - BONUS_COST[curErda];
        if (cost > chr.getNX()) {
            popup(chr, "You don't have enough Maple Points for that bonus.");
            return;
        }
        int extra = ERDA_EXTRA[erdaTier] - ERDA_EXTRA[curErda];
        if (extra > 0 && !canHoldItems(chr, extra)) {
            popup(chr, "Make room in your USE and ETC inventories first.");
            return;
        }
        setBonusTiers(chr, d, expTier, erdaTier);
        chr.deductNX(cost);
        giveReward(chr, clearExp(d, chr.getLevel()) * (EXP_MULT[expTier] - EXP_MULT[curExp]), extra);
        sendRecord(chr, d);
        chr.dispose();
    }

    /**
     * run script 91 (Selazar's window, Express Pass): once the dungeon was cleared in normal mode, this week's run can
     * be skipped; the clear reward window opens right away.
     */
    private static void express(Char chr, Dungeon d) {
        if (chr.getInstance() != null || chr.getFieldID() != d.entrance || chr.getLevel() < d.level
                || !chr.getQuestManager().hasQuestCompleted(d.storyQuest)) {
            chr.dispose();
            return;
        }
        if (hasReward(chr, d)) {
            openClearWindow(chr, d);
            chr.dispose();
            return;
        }
        restartIfUnlimited(chr, d);
        if (!everCleared(chr, d)) {
            popup(chr, hm(d) ? "The Express Pass opens once you've reached the summit of High Mountain yourself."
                    : "The Express Pass opens once you've cleared " + d.title() + " yourself.");
            return;
        }
        if (clearedThisWeek(chr, d)) {
            popup(chr, hm(d) ? "You've already reached the summit this week. The mountain resets on Thursday."
                    : "You've already cleared " + d.title() + " this week. It resets on Thursday.");
            return;
        }
        String block = allowanceBlock(chr, d);
        if (block != null) {
            popup(chr, block);
            return;
        }
        countRun(chr, d);
        setClearedStages(chr, d, d.stages);
        openClearWindow(chr, d);
        chr.dispose();
    }

    private static boolean canHoldItems(Char chr, int mult) {
        for (int[] item : CLEAR_ITEMS) {
            if (!chr.canHold(item[0], item[1] * mult)) {
                return false;
            }
        }
        return true;
    }

    private static void giveReward(Char chr, long exp, int itemMult) {
        var sm = chr.getScriptManager();
        if (exp > 0) {
            sm.giveExp(exp);
        }
        if (itemMult > 0) {
            for (int[] item : CLEAR_ITEMS) {
                sm.giveItem(item[0], item[1] * itemMult);
            }
        }
    }

    /** the clear (218) or bonus (219) reward window */
    public static OutPacket rewardWindow(OutHeader header, Char chr, Dungeon d, boolean bonus) {
        return EpicDungeonPacket.rewardWindow(header, d.nxRecord, clearExp(d, chr.getLevel()), CLEAR_ITEMS, bonus,
                BONUS_COST, ERDA_EXTRA, EXP_MULT);
    }

    // ------------------------------------------------------------------------------------------- the allowance

    /** official allowance: weekly runs per account (Thursday reset), one dungeon per character */
    public static final int ACCOUNT_WEEKLY_RUNS = 2;

    private static String runKey(Dungeon d) {
        return net.swordie.ms.client.character.AccountWeekly.EPIC_PREFIX + d.name().toLowerCase(java.util.Locale.ROOT);
    }

    private static String displayName(String key) {
        for (Dungeon d : Dungeon.values()) {
            if (key.endsWith(d.name().toLowerCase(java.util.Locale.ROOT))) {
                return d.title();
            }
        }
        return key;
    }

    /**
     * With setting epic_dungeon_per_character off (official): why this character can't run this dungeon this week, or
     * null. A run already counted for this character and dungeon may go on.
     */
    static String allowanceBlock(Char chr, Dungeon d) {
        if (unlimited() || net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.EPIC_DUNGEON_PER_CHARACTER)) {
            return null;
        }
        var runs = net.swordie.ms.client.character.AccountWeekly.clears(chr,
                net.swordie.ms.client.character.AccountWeekly.EPIC_PREFIX, DayOfWeek.THURSDAY);
        StringBuilder names = new StringBuilder();
        for (var r : runs) {
            if (r.charId() == chr.getId()) {
                return r.content().equals(runKey(d)) ? null : "This character already ran "
                        + displayName(r.content()) + " this week: one Epic Dungeon per character. They reset on Thursday.";
            }
            names.append(names.length() == 0 ? "" : ", ").append(r.charName()).append(" (")
                    .append(displayName(r.content())).append(")");
        }
        if (runs.size() >= ACCOUNT_WEEKLY_RUNS) {
            return "Your account has used its " + ACCOUNT_WEEKLY_RUNS + " Epic Dungeon runs this week: " + names
                    + ". They reset on Thursday.";
        }
        return null;
    }

    static boolean unlimited() {
        return net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.EPIC_DUNGEON_UNLIMITED);
    }

    /** setting epic_dungeon_unlimited: a cleared run whose reward was claimed starts over from stage 1 */
    private static void restartIfUnlimited(Char chr, Dungeon d) {
        if (!unlimited() || !clearedThisWeek(chr, d) || hasReward(chr, d)) {
            return;
        }
        Map<String, String> rec = record(chr, d);
        rec.put("clearStage", "0");
        rec.put("expbonus", "0");
        rec.put("erdabonus", "0");
        saveRecord(chr, d, rec);
        sendRecord(chr, d);
    }

    static void countRun(Char chr, Dungeon d) {
        if (!unlimited() && !net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.EPIC_DUNGEON_PER_CHARACTER)) {
            net.swordie.ms.client.character.AccountWeekly.record(chr, runKey(d), DayOfWeek.THURSDAY);
        }
    }

    /** @return whether this week's run may start (counted against the account on its first entry) */
    private static boolean startRun(Char chr, Dungeon d) {
        String block = allowanceBlock(chr, d);
        if (block != null) {
            popup(chr, block);
            return false;
        }
        countRun(chr, d);
        return true;
    }

    private static void popup(Char chr, String msg) {
        chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(msg)));
        chr.dispose();
    }

    private static void enter(Char chr, Dungeon d, boolean story) {
        if (!hm(d)) {
            enterRun(chr, d, story);
            return;
        }
        if (chr.getInstance() != null || chr.getFieldID() != HM_ENTRANCE) {
            popup(chr, "Talk to Selazar in Cernium to climb High Mountain.");
            return;
        }
        if (chr.getLevel() < HM_LEVEL) {
            popup(chr, "High Mountain is too dangerous below Lv. " + HM_LEVEL + ".");
            return;
        }
        boolean storyDone = chr.getQuestManager().hasQuestCompleted(HM_STORY_QUEST);
        if (story == storyDone) {
            chr.dispose();
            return;
        }
        if (!story) {
            restartIfUnlimited(chr, d);
        }
        if (!story && clearedThisWeek(chr)) {
            popup(chr, "You've already reached the summit this week. The mountain resets on Thursday.");
            return;
        }
        if (!story && hasReward(chr)) {
            popup(chr, "Your reward from the summit is still waiting at the top. Claim it first.");
            return;
        }
        if (chr.getParty() != null && chr.getParty().getOnlineMembers().size() > 1) {
            popup(chr, "High Mountain is climbed alone. Leave your party first.");
            return;
        }
        if (!story && !startRun(chr, d)) {
            return;
        }
        int from = story ? 1 : clearedStages(chr) + 1;
        Instance instance = new Instance(chr);
        instance.addProperty(HighMountainInstanceHelper.STORY, story);
        instance.addProperty(HighMountainInstanceHelper.FIRST_STAGE, from);
        instance.setup(HighMountainInstanceHelper.stageField(story, from), 0);
    }

    /**
     * The Angler Company (Selazar in Odium, 8 stages) and Nightmare Paradise (Selazar in 410007520, 7 stages): story
     * mode until the story quest, then the weekly run.
     */
    private static void enterRun(Char chr, Dungeon d, boolean story) {
        boolean angler = d == Dungeon.ANGLER;
        String where = angler ? "in Odium" : "at the Empress Road Operations Center";
        if (chr.getInstance() != null || chr.getFieldID() != d.entrance) {
            popup(chr, "Talk to Selazar " + where + " to enter " + d.title() + ".");
            return;
        }
        if (chr.getLevel() < d.level) {
            popup(chr, capitalized(d.title()) + " is too dangerous below Lv. " + d.level + ".");
            return;
        }
        if (!chr.getQuestManager().hasQuestCompleted(angler ? ANGLER_INTRO_QUEST : NP_INTRO_QUEST)) {
            popup(chr, "Listen to Selazar's new song first.");
            return;
        }
        boolean storyDone = chr.getQuestManager().hasQuestCompleted(d.storyQuest);
        if (story == storyDone) {
            chr.dispose();
            return;
        }
        if (!story) {
            restartIfUnlimited(chr, d);
        }
        if (!story && clearedThisWeek(chr, d)) {
            popup(chr, "You've already cleared " + d.title() + " this week. It resets on Thursday.");
            return;
        }
        if (!story && hasReward(chr, d)) {
            popup(chr, "Your reward from " + d.title() + " is still waiting. Claim it from Selazar first.");
            return;
        }
        if (chr.getParty() != null && chr.getParty().getOnlineMembers().size() > 1) {
            popup(chr, capitalized(d.title()) + " is entered alone. Leave your party first.");
            return;
        }
        if (!story && !startRun(chr, d)) {
            return;
        }
        int from = story ? 1 : clearedStages(chr, d) + 1;
        Instance instance = new Instance(chr);
        if (angler) {
            instance.addProperty(AnglerCompanyInstanceHelper.STORY, story);
            instance.addProperty(AnglerCompanyInstanceHelper.FIRST_STAGE, from);
            instance.setup(AnglerCompanyInstanceHelper.stageField(story, from), 0);
        } else {
            instance.addProperty(NightmareParadiseInstanceHelper.STORY, story);
            instance.addProperty(NightmareParadiseInstanceHelper.FIRST_STAGE, from);
            instance.setup(NightmareParadiseInstanceHelper.stageField(story, from), 0);
        }
    }

    private static String capitalized(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
