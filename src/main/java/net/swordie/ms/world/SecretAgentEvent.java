package net.swordie.ms.world;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

/**
 * The Secret Agent event (GMS summer 2009, "Master M"), rewritten to run all year. The 2009 event can't come
 * back as it was: its Agent HQ map isn't in the game files any more, six of its quests are empty and the rest were
 * driven by scripts that were never kept. What's left - every agent NPC, the quests' dialogue, Chumji's watermelon
 * field and all the agent gear - is put together like this:
 * <ul>
 *     <li><b>Agent E</b> (major towns, and the HQ) recruits Lv. 10+ (quest 10000 "Suspicious Offer?!", Parachute
 *     Agent Shoes),
 *     gives <b>Today's Mission</b> (one a day: badge, speed quiz, watermelon, special operation or enrichment
 *     program), runs the <b>special operation</b> (20 of Master M's scrolls from monsters near your level) and the
 *     points rewards. The agents work from the <b>M-Forcer Operations HQ</b> (Omega Sector), standing in for the old
 *     Agent HQ.</li>
 *     <li><b>Agent O</b>: Special Agent Badges (found on monsters near your level): every 10 = Agent O's Nylon Gloves
 *     (7 days), 30 in all = Agent O's Leather Gloves (100 in 2009).</li>
 *     <li><b>Agent C</b> (HQ and the party quests' bonus rooms): Intelligence Documents (found in party quests, and
 *     now and then on monsters near your level anywhere): every 5 = Agent C's Old Receiver (7 days), 30 in all =
 *     Agent C's Titanium Receiver (100 in 2009).</li>
 *     <li><b>Agent W</b>: Chumji's watermelon field (a little jump quest past the Hoguls): every 5 watermelons = a
 *     Pink Flower Tube for the player's weapon type for 7 days and EXP (quest 10005's rewards; the Lv. 50
 *     tubes from Lv. 50, and canes, dual bowguns and cannons too; weapon types with no tube get the EXP only).</li>
 *     <li><b>Agent Meow</b>: the Speed Quiz (10 questions every 30 minutes, 1 point each, 10 seconds per answer).</li>
 *     <li><b>Agent Kitty</b>: the Enrichment Program (defeat 200 monsters near your level, once a day).</li>
 * </ul>
 * Agent Points: 100 in one day = Parachute Agent Suit and Agent N's Disposable Receiver (7 days, as in 2009);
 * 200 in all = Agent Cape; 350 = Invincible Agent Suit and Agent N's Receiver; 700 = Ultimate Agent Suit (* shortened from 2009's 300 / 500 / 1,000). Wearing the full set earns the Ultimate Agent Shoes and the Secret Agent medal ("You are a True Special
 * Agent!"). Each day's mission also gives a Secret Box (and 3 Swordie Coins while coins are on).
 */
public final class SecretAgentEvent {

    private static final Logger log = LogManager.getLogger(SecretAgentEvent.class);

    public static final int AGENT_W = 9000032;
    public static final int AGENT_C = 9000033;
    public static final int AGENT_O = 9000034;
    public static final int AGENT_P = 9000035;
    public static final int AGENT_E = 9000036;
    public static final int AGENT_MEOW = 9000037;
    public static final int AGENT_KITTY = 9000038;
    public static final int HQ = 922100000; // M-Forcer Operations HQ
    public static final int WATERMELON_FIELD = 922210000;
    public static final int RECRUIT_QUEST = 10000;
    public static final int AGENT_QR = 89999986;

    public static final int PARACHUTE_SHOES = 1072366, ULTIMATE_SHOES = 1072368;
    public static final int PARACHUTE_SUIT = 1052165, INVINCIBLE_SUIT = 1052166, ULTIMATE_SUIT = 1052167;
    public static final int NYLON_GLOVES = 1082244, LEATHER_GLOVES = 1082245;
    public static final int OLD_RECEIVER = 1032055, TITANIUM_RECEIVER = 1032058;
    public static final int N_DISPOSABLE = 1002797, N_RECEIVER = 1002800;
    public static final int AGENT_CAPE = 1102174, SECRET_AGENT_MEDAL = 1142576;
    public static final int BADGE = 4031988, DOCUMENT = 4001192, WATERMELON = 4031701, SECRET_BOX = 2022336;
    public static final int[] SCROLLS = {4031999, 4032000, 4032001}; // Master M's Direction / Order / Command Scroll

    public static final int DAILY_SUIT_POINTS = 100;
    public static final int CAPE_POINTS = 200, INVINCIBLE_POINTS = 350, ULTIMATE_POINTS = 700; // GMS: 300/500/1000
    public static final int LEATHER_BADGES = 30, TITANIUM_DOCS = 30; // GMS: 100 each
    public static final int SCROLLS_NEEDED = 20, ENRICH_KILLS = 200, QUIZ_MISSION_POINTS = 20;
    public static final int MELONS_PER_DAY = 5;
    public static final long QUIZ_COOLDOWN_MS = 30 * 60 * 1000L;
    private static final int BADGE_CHANCE = 150, SCROLL_CHANCE = 800, DOCUMENT_CHANCE = 500; // out of 10000
    private static final int DOCUMENT_FIELD_CHANCE = 100; // hardcode-ok; documents outside party quests too
    private static final int BADGE_MISSION_CHANCE = 300; // hardcode-ok; while today's mission wants a badge
    private static final int TEMP_HOURS = 7 * 24;

    /** Today's missions (quests 10015, 10018, 10019, 10020, 10017). */
    public enum Mission {
        BADGE(10015, "Obtain a Special Agent Badge", "Find a #bSpecial Agent Badge#k on monsters near your level "
                + "and hand it to #bAgent O#k at the HQ."),
        QUIZ(10018, "Earn 20 points on the Speed Quiz", "Score #b20 points#k today on #bAgent Meow#k's Speed Quiz "
                + "at the HQ."),
        MELON(10019, "Invade the Watermelon Field", "Bring #bAgent W#k a watermelon from #bChumji's watermelon "
                + "field#k (ask Agent W at the HQ)."),
        SPECIAL_OP(10020, "Help Agent E", "Complete my #bspecial operation#k: find #b20 of Master M's scrolls#k on "
                + "monsters near your level."),
        ENRICH(10017, "Special Agent Enrichment Program", "Finish #bAgent Kitty#k's Enrichment Program at the HQ: "
                + "defeat #b200 monsters#k near your level.");

        public final int quest;
        public final String title, task;

        Mission(int quest, String title, String task) {
            this.quest = quest;
            this.title = title;
            this.task = task;
        }
    }

    private SecretAgentEvent() {
    }

    public static boolean isAgent(Char chr) {
        return chr.getQuestManager().hasQuestCompleted(RECRUIT_QUEST);
    }

    // ------------------------------------------------------------------------------------------------ the record

    private static String today() {
        return net.swordie.ms.util.ResetClock.today().toString();
    }

    /** The agent record, with the daily values reset when the day changed. */
    public static Map<String, String> record(Char chr) {
        Map<String, String> m = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(AGENT_QR);
        if (v != null) {
            for (String kv : v.split(";")) {
                int i = kv.indexOf('=');
                if (i > 0) {
                    m.put(kv.substring(0, i), kv.substring(i + 1));
                }
            }
        }
        if (!today().equals(m.get("day"))) {
            m.put("day", today());
            for (String k : List.of("dpts", "mission", "mstate", "kills", "qpts", "scrolls", "melons", "enrich",
                    "suit")) {
                m.remove(k);
            }
            m.keySet().removeIf(k -> k.startsWith("did")); // missions done before today's was picked
        }
        return m;
    }

    public static void save(Char chr, Map<String, String> m) {
        StringBuilder sb = new StringBuilder();
        m.forEach((k, v) -> sb.append(sb.length() == 0 ? "" : ";").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(AGENT_QR, sb.toString());
    }

    public static int getInt(Char chr, String key) {
        return num(record(chr).get(key));
    }

    private static int num(String s) {
        try {
            return s == null ? 0 : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void add(Map<String, String> m, String key, int amount) {
        m.put(key, String.valueOf(num(m.get(key)) + amount));
    }

    // -------------------------------------------------------------------------------------------- recruiting

    /** Recruits the player (quest 10000) and gives the Parachute Agent Shoes. @return false if there's no room */
    public static final int MIN_LEVEL = 10;

    public static boolean recruit(Char chr) {
        if (isAgent(chr)) {
            return true;
        }
        if (chr.getLevel() < MIN_LEVEL) {
            return false;
        }
        if (!chr.canHold(PARACHUTE_SHOES, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Equip inventory first.");
            return false;
        }
        chr.getScriptManager().startQuestNoCheck(RECRUIT_QUEST);
        chr.getScriptManager().completeQuestNoRewards(RECRUIT_QUEST); // its Act.img shoes expired in 2009
        giveTimed(chr, PARACHUTE_SHOES, 30 * 24);
        return true;
    }

    /** The replacement shoes (quest 10023): only when the player has none. */
    public static boolean replaceShoes(Char chr) {
        if (hasAnywhere(chr, PARACHUTE_SHOES) || hasAnywhere(chr, ULTIMATE_SHOES)
                || !chr.canHold(PARACHUTE_SHOES, 1)) {
            return false;
        }
        giveTimed(chr, PARACHUTE_SHOES, 30 * 24);
        return true;
    }

    // ------------------------------------------------------------------------------------------------ missions

    /** @return today's mission (picked when first asked for each day) */
    public static Mission todaysMission(Char chr) {
        Map<String, String> m = record(chr);
        String id = m.get("mission");
        if (id == null) {
            Mission[] all = Mission.values();
            id = all[Util.getRandom(all.length - 1)].name();
            m.put("mission", id);
            // Already done today before asking Agent E (badges/melons/scrolls handed in, quiz or program done)
            boolean done = m.get("did" + id) != null
                    || ("QUIZ".equals(id) && num(m.get("qpts")) >= QUIZ_MISSION_POINTS)
                    || ("ENRICH".equals(id) && num(m.get("enrich")) >= ENRICH_KILLS);
            m.put("mstate", done ? "clear" : "ing");
            save(chr, m);
        }
        try {
            return Mission.valueOf(id);
        } catch (IllegalArgumentException e) {
            return Mission.BADGE;
        }
    }

    /** "ing", "clear" (done, not reported yet) or "done" (reported). */
    public static String missionState(Char chr) {
        todaysMission(chr);
        return record(chr).getOrDefault("mstate", "ing");
    }

    /** Marks today's mission as done if it's the given one. */
    public static void clearMission(Char chr, Mission mission) {
        Map<String, String> m = record(chr);
        if (m.get("mission") == null) {
            m.put("did" + mission.name(), "1"); // counts once today's mission is picked
            save(chr, m);
            return;
        }
        if (mission.name().equals(m.get("mission")) && "ing".equals(m.get("mstate"))) {
            m.put("mstate", "clear");
            save(chr, m);
            chr.chatMessage(ChatType.Notice2, "[Agent] Today's mission is done! Report to Agent E.");
        }
    }

    /** Reports today's mission: Secret Box, 20 Agent Points (and coins while they're on). @return false if there's no room */
    public static boolean reportMission(Char chr) {
        Map<String, String> m = record(chr);
        if (!"clear".equals(m.get("mstate"))) {
            return false;
        }
        if (!chr.canHold(SECRET_BOX, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Use inventory first.");
            return false;
        }
        m.put("mstate", "done");
        save(chr, m);
        chr.addItemToInventory(SECRET_BOX, 1);
        SeasonalEvents.giveCoins(chr, 3, "Today's Mission");
        addPoints(chr, 20, "Today's Mission");
        return true;
    }

    /** Staff accounts (Tester and up) get Agent E's testing options. */
    public static boolean isStaff(Char chr) {
        return chr.getUser() != null && !chr.getUser().isPlayer();
    }

    /** Staff testing option: sets today's mission, not done yet. */
    public static void setMission(Char chr, Mission mission) {
        Map<String, String> m = record(chr);
        m.put("mission", mission.name());
        m.put("mstate", "ing");
        save(chr, m);
    }

    /** Staff testing option: today's progress starts over (mission, points today, melons, quiz timer...). */
    public static void resetToday(Char chr) {
        Map<String, String> m = record(chr);
        m.put("day", "");
        m.remove("quizAt");
        save(chr, m);
        save(chr, record(chr)); // the day change clears the daily values
    }

    /** How far along today's mission is, for Agent E. */
    public static String missionProgress(Char chr) {
        Mission mission = todaysMission(chr);
        Map<String, String> m = record(chr);
        switch (mission) {
            case BADGE:
                return String.format("Special Agent Badges in your bag: #b%d#k (Agent O takes them).",
                        chr.getInventoryByType(net.swordie.ms.enums.InvType.ETC).getQuantity(BADGE));
            case QUIZ:
                return String.format("Speed Quiz points today: #b%d / %d#k.", num(m.get("qpts")),
                        QUIZ_MISSION_POINTS);
            case MELON:
                return String.format("Watermelons picked today: #b%d / %d#k (hand one to Agent W).",
                        num(m.get("melons")), MELONS_PER_DAY);
            case SPECIAL_OP: {
                int scroll = scrollFor(chr);
                return String.format("#t%d#: #b%d / %d#k.", scroll, chr.getInventoryByType(
                        net.swordie.ms.enums.InvType.ETC).getQuantity(scroll), SCROLLS_NEEDED);
            }
            default:
                return String.format("Enrichment Program: #b%d / %d#k monsters near your level.",
                        Math.max(0, enrichmentKills(chr)), ENRICH_KILLS);
        }
    }

    /** Agent P's status board. */
    public static String statusBoard(Char chr) {
        Map<String, String> m = record(chr);
        Mission mission = todaysMission(chr);
        String state = missionState(chr);
        long wait = quizWaitMs(chr);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Today's Mission: #b%s#k (%s)\r\n", mission.title,
                "done".equals(state) ? "reported" : "clear".equals(state) ? "#gdone - report to Agent E#k"
                        : "in progress"));
        if (!"done".equals(state)) {
            sb.append("  ").append(missionProgress(chr)).append("\r\n");
        }
        sb.append(String.format("Agent Points: #b%d#k today, #b%,d#k in all\r\n", num(m.get("dpts")),
                num(m.get("pts"))));
        sb.append(String.format("Badges delivered: #b%d#k (%d = Leather Gloves%s)\r\n", num(m.get("badges")),
                LEATHER_BADGES, m.get("leather") != null ? ", #gdone#k" : ""));
        sb.append(String.format("Documents delivered: #b%d#k (%d = Titanium Receiver%s)\r\n", num(m.get("docs")),
                TITANIUM_DOCS, m.get("titanium") != null ? ", #gdone#k" : ""));
        sb.append(String.format("Watermelons: #b%d#k in all, #b%d#k left to pick today\r\n",
                num(m.get("melonsAll")), melonsLeftToday(chr)));
        sb.append(wait > 0 ? String.format("Next Speed Quiz: in #b%d min#k", (wait + 59999) / 60000)
                : "Next Speed Quiz: #bopen now#k");
        if (isOnSpecialOp(chr)) {
            int scroll = scrollFor(chr);
            sb.append(String.format("\r\nSpecial operation: #b%d / %d#k #t%d#", chr.getInventoryByType(
                    net.swordie.ms.enums.InvType.ETC).getQuantity(scroll), SCROLLS_NEEDED, scroll));
        }
        return sb.toString();
    }

    // -------------------------------------------------------------------------------------------- agent points

    /**
     * Adds Agent Points (total and today's) and gives the point rewards that are due.
     */
    public static void addPoints(Char chr, int points, String reason) {
        if (points <= 0) {
            return;
        }
        Map<String, String> m = record(chr);
        add(m, "pts", points);
        add(m, "dpts", points);
        save(chr, m);
        chr.chatMessage(ChatType.Notice2, String.format("[Agent] +%d Agent Points (%s). Today: %d, total: %,d.",
                points, reason, num(m.get("dpts")), num(m.get("pts"))));
        checkPointRewards(chr);
    }

    private static void checkPointRewards(Char chr) {
        Map<String, String> m = record(chr);
        int total = num(m.get("pts"));
        int day = num(m.get("dpts"));
        boolean changed = false;
        if (day >= DAILY_SUIT_POINTS && m.get("suit") == null && chr.canHold(PARACHUTE_SUIT, 1)
                && chr.canHold(N_DISPOSABLE, 1)) {
            m.put("suit", "1");
            changed = true;
            giveTimed(chr, PARACHUTE_SUIT, TEMP_HOURS);
            giveTimed(chr, N_DISPOSABLE, TEMP_HOURS);
            chr.chatMessage(ChatType.Notice2, "[Agent] 100 points today! You got the Parachute Agent Suit and Agent "
                    + "N's Disposable Receiver (7 days).");
        }
        changed |= milestone(chr, m, total, CAPE_POINTS, "cape", new int[]{AGENT_CAPE}, "the Agent Cape");
        changed |= milestone(chr, m, total, INVINCIBLE_POINTS, "inv", new int[]{INVINCIBLE_SUIT, N_RECEIVER},
                "the Invincible Agent Suit and Agent N's Receiver");
        changed |= milestone(chr, m, total, ULTIMATE_POINTS, "ult", new int[]{ULTIMATE_SUIT}, "the Ultimate Agent "
                + "Suit");
        // The gloves and receiver, also for agents already past the (lower) counts
        changed |= milestone(chr, m, num(m.get("badges")), LEATHER_BADGES, "leather", new int[]{LEATHER_GLOVES},
                "Agent O's Leather Gloves", "Special Agent Badges");
        changed |= milestone(chr, m, num(m.get("docs")), TITANIUM_DOCS, "titanium", new int[]{TITANIUM_RECEIVER},
                "Agent C's Titanium Receiver", "Intelligence Documents");
        if (changed) {
            save(chr, m);
        }
    }

    private static boolean milestone(Char chr, Map<String, String> m, int total, int need, String key, int[] items,
                                     String what) {
        return milestone(chr, m, total, need, key, items, what, "Agent Points");
    }

    private static boolean milestone(Char chr, Map<String, String> m, int total, int need, String key, int[] items,
                                     String what, String unit) {
        if (total < need || m.get(key) != null) {
            return false;
        }
        for (int id : items) {
            if (!chr.canHold(id, 1)) {
                chr.chatMessage(ChatType.Notice2, String.format("[Agent] You've earned %s! Make room in your Equip "
                        + "inventory and talk to Agent E.", what));
                return false;
            }
        }
        for (int id : items) {
            SeasonalEvents.giveItem(chr, id, 1);
        }
        m.put(key, "1");
        chr.chatMessage(ChatType.Notice2, String.format("[Agent] %,d %s! You got %s.", need, unit, what));
        return true;
    }

    /** Agent E's "claim" button: gives rewards that didn't fit before. */
    public static void retryPointRewards(Char chr) {
        checkPointRewards(chr);
    }

    // ---------------------------------------------------------------------------------------- hand-ins (NPCs)

    /** Agent O: takes every badge. Every 10 = Nylon Gloves (7 days); 30 in all = Leather Gloves. @return taken */
    public static int turnInBadges(Char chr) {
        int n = chr.getInventoryByType(net.swordie.ms.enums.InvType.ETC).getQuantity(BADGE);
        if (n <= 0) {
            return 0;
        }
        if (!chr.canHold(NYLON_GLOVES, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Equip inventory first.");
            return 0;
        }
        chr.consumeItem(BADGE, n);
        Map<String, String> m = record(chr);
        int before = num(m.get("badges"));
        int after = before + n;
        m.put("badges", String.valueOf(after));
        save(chr, m);
        if (after / 10 > before / 10) {
            giveTimed(chr, NYLON_GLOVES, TEMP_HOURS);
        }
        clearMission(chr, Mission.BADGE);
        addPoints(chr, 5 * n, "Special Agent Badges");
        return n;
    }

    /** Agent C: takes every document. Every 5 = Old Receiver (7 days); 30 in all = Titanium Receiver. */
    public static int turnInDocuments(Char chr) {
        int n = chr.getInventoryByType(net.swordie.ms.enums.InvType.ETC).getQuantity(DOCUMENT);
        if (n <= 0) {
            return 0;
        }
        if (!chr.canHold(OLD_RECEIVER, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Equip inventory first.");
            return 0;
        }
        chr.consumeItem(DOCUMENT, n);
        Map<String, String> m = record(chr);
        int before = num(m.get("docs"));
        int after = before + n;
        m.put("docs", String.valueOf(after));
        save(chr, m);
        if (after / 5 > before / 5) {
            giveTimed(chr, OLD_RECEIVER, TEMP_HOURS);
        }
        addPoints(chr, 5 * n, "Intelligence Documents");
        return n;
    }

    /** Agent W: takes every watermelon. Every 5 in all = a Pink Flower Tube (7 days) and EXP (2%, at least 10,000). */
    public static int turnInWatermelons(Char chr) {
        int n = chr.getInventoryByType(net.swordie.ms.enums.InvType.ETC).getQuantity(WATERMELON);
        if (n <= 0) {
            return 0;
        }
        int weapon = agentWeapon(chr);
        if (weapon != 0 && !chr.canHold(weapon, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Equip inventory first.");
            return 0;
        }
        chr.consumeItem(WATERMELON, n);
        Map<String, String> m = record(chr);
        int before = num(m.get("melonsAll"));
        int after = before + n;
        m.put("melonsAll", String.valueOf(after));
        save(chr, m);
        if (after / 5 > before / 5) {
            if (weapon != 0) {
                giveTimed(chr, weapon, TEMP_HOURS);
            } else {
                SeasonalEvents.giveCoins(chr, 5, "Agent W (no Pink Flower Tube for your weapon)");
            }
            chr.addExp(Math.max(10000, SeasonalEvents.expPercent(chr, 2)));
        }
        clearMission(chr, Mission.MELON);
        addPoints(chr, 10 * n, "watermelons");
        return n;
    }

    /** @return how many watermelons the player can still pick today */
    public static int melonsLeftToday(Char chr) {
        return Math.max(0, MELONS_PER_DAY - getInt(chr, "melons"));
    }

    /** The watermelon field's reactor: one watermelon a run (5 a day). */
    public static boolean pickWatermelon(Char chr) {
        if (!isAgent(chr) || melonsLeftToday(chr) <= 0 || !SeasonalEvents.grantFind(chr, WATERMELON, 1)) {
            return false;
        }
        Map<String, String> m = record(chr);
        add(m, "melons", 1);
        save(chr, m);
        return true;
    }

    /** Agent E's special operation: 20 of Master M's scrolls (the one for your level). @return true if handed in */
    public static boolean turnInScrolls(Char chr) {
        int scroll = scrollFor(chr);
        if (!chr.hasItemCount(scroll, SCROLLS_NEEDED)) {
            scroll = 0; // 20 of an earlier tier still count (levelled up during the operation)
            for (int s : SCROLLS) {
                if (chr.hasItemCount(s, SCROLLS_NEEDED)) {
                    scroll = s;
                }
            }
            if (scroll == 0) {
                return false;
            }
        }
        chr.consumeItem(scroll, SCROLLS_NEEDED);
        Map<String, String> m = record(chr);
        m.put("op", "0");
        save(chr, m);
        clearMission(chr, Mission.SPECIAL_OP);
        chr.addExp(SeasonalEvents.expPercent(chr, 2));
        addPoints(chr, 30, "special operation");
        return true;
    }

    /** Starts the special operation (scrolls drop while it's on). */
    public static void startSpecialOp(Char chr) {
        Map<String, String> m = record(chr);
        m.put("op", "1");
        save(chr, m);
    }

    public static boolean isOnSpecialOp(Char chr) {
        return "1".equals(record(chr).get("op"));
    }

    /** Level 10-30: Direction Scroll, 31-50: Order Scroll, 51+: Command Scroll (quests 10010-10012). */
    public static int scrollFor(Char chr) {
        return chr.getLevel() <= 30 ? SCROLLS[0] : chr.getLevel() <= 50 ? SCROLLS[1] : SCROLLS[2];
    }

    /** Agent Kitty's Enrichment Program. */
    public static void startEnrichment(Char chr) {
        Map<String, String> m = record(chr);
        if (m.get("enrich") == null) {
            m.put("enrich", "0");
            save(chr, m);
        }
    }

    /** @return kills so far today, or -1 if the program wasn't started today */
    public static int enrichmentKills(Char chr) {
        String v = record(chr).get("enrich");
        return v == null ? -1 : num(v);
    }

    /** Agent Meow's quiz: whether the next round is open, and records a round's score. */
    public static long quizWaitMs(Char chr) {
        long last = 0;
        try {
            last = Long.parseLong(record(chr).getOrDefault("quizAt", "0"));
        } catch (NumberFormatException ignored) {
        }
        return Math.max(0, last + QUIZ_COOLDOWN_MS - System.currentTimeMillis());
    }

    public static void startQuizRound(Char chr) {
        Map<String, String> m = record(chr);
        m.put("quizAt", String.valueOf(System.currentTimeMillis()));
        save(chr, m);
    }

    public static void finishQuizRound(Char chr, int score) {
        Map<String, String> m = record(chr);
        add(m, "qpts", score);
        save(chr, m);
        if (num(m.get("qpts")) >= QUIZ_MISSION_POINTS) {
            clearMission(chr, Mission.QUIZ);
        }
        addPoints(chr, score, "Speed Quiz");
    }

    /** "You are a True Special Agent!" (quest 10021): the full set worn. @return what's missing, or null */
    public static String missingForTrueAgent(Char chr) {
        List<String> missing = new ArrayList<>();
        int[][] need = {{N_RECEIVER}, {TITANIUM_RECEIVER}, {AGENT_CAPE}, {PARACHUTE_SHOES, ULTIMATE_SHOES},
                {LEATHER_GLOVES}, {ULTIMATE_SUIT, INVINCIBLE_SUIT}};
        for (int[] any : need) {
            boolean ok = false;
            for (int id : any) {
                ok |= isWearing(chr, id);
            }
            if (!ok) {
                missing.add("#t" + any[0] + "#");
            }
        }
        return missing.isEmpty() ? null : String.join(", ", missing);
    }

    public static boolean claimTrueAgent(Char chr) {
        Map<String, String> m = record(chr);
        if (m.get("true") != null || missingForTrueAgent(chr) != null || !chr.canHold(ULTIMATE_SHOES, 2)) {
            return false;
        }
        m.put("true", "1");
        save(chr, m);
        SeasonalEvents.giveItem(chr, ULTIMATE_SHOES, 1);
        SeasonalEvents.giveItem(chr, SECRET_AGENT_MEDAL, 1);
        SeasonalEvents.giveCoins(chr, 30, "True Special Agent");
        return true;
    }

    public static boolean isTrueAgent(Char chr) {
        return record(chr).get("true") != null;
    }

    // --------------------------------------------------------------------------------------------- monsters

    static void onMobKilled(Mob mob) {
        Char killer = mob.getMostDamageChar();
        if (killer == null || killer.getField() != mob.getField() || mob.isBoss() || mob.isBossFightAdd()
                || mob.getField().getInfo().isTown() || !isAgent(killer)) {
            return;
        }
        int lvDiff = Math.abs(mob.getForcedMobStat().getLevel() - killer.getLevel());
        if (lvDiff > 15) {
            return;
        }
        Map<String, String> rec = record(killer);
        boolean badgeMission = "BADGE".equals(rec.get("mission")) && "ing".equals(rec.get("mstate"))
                && !killer.hasItem(BADGE);
        if (Util.succeedProp(badgeMission ? BADGE_MISSION_CHANCE : BADGE_CHANCE, 10000)) {
            SeasonalEvents.grantFind(killer, BADGE, 1);
        }
        if (isOnSpecialOp(killer) && Util.succeedProp(SCROLL_CHANCE, 10000)) {
            int scroll = scrollFor(killer);
            if (!killer.hasItemCount(scroll, SCROLLS_NEEDED)) {
                SeasonalEvents.grantFind(killer, scroll, 1);
                if (killer.hasItemCount(scroll, SCROLLS_NEEDED)) {
                    killer.chatMessage(ChatType.Notice2, "[Agent] That's 20 scrolls. Take them to Agent E.");
                }
            }
        }
        boolean partyQuest = !mob.getField().isChannelField() && killer.getParty() != null;
        if (Util.succeedProp(partyQuest ? DOCUMENT_CHANCE : DOCUMENT_FIELD_CHANCE, 10000)) {
            SeasonalEvents.grantFind(killer, DOCUMENT, 1); // party quests, and anywhere now and then
        }
        Map<String, String> m = record(killer);
        String e = m.get("enrich");
        if (e != null && num(e) < ENRICH_KILLS) {
            int kills = num(e) + 1;
            m.put("enrich", String.valueOf(kills));
            save(killer, m);
            if (kills == ENRICH_KILLS) {
                clearMission(killer, Mission.ENRICH);
                addPoints(killer, 20, "Enrichment Program");
            } else if (kills % 50 == 0) {
                killer.chatMessage(ChatType.Notice2, String.format("[Agent] Enrichment Program: %d/%d.", kills,
                        ENRICH_KILLS));
            }
        }
    }

    // ------------------------------------------------------------------------------------------------ helpers

    /** Remembers the town the player came from (leaving the HQ goes back there). */
    public static void rememberTown(Char chr) {
        if (chr.getField() != null && chr.getField().getInfo().isTown() && chr.getFieldID() != HQ
                && chr.getFieldID() != WATERMELON_FIELD) {
            Map<String, String> m = record(chr);
            m.put("town", String.valueOf(chr.getFieldID()));
            save(chr, m);
        }
    }

    /** @return the town to go back to from the HQ (Henesys if none) */
    public static int returnTown(Char chr) {
        int t = num(record(chr).get("town"));
        return t == 0 ? FieldConstants.HENESYS_FIELD_ID : t;
    }

    private static void giveTimed(Char chr, int itemId, int hours) {
        Item item = net.swordie.ms.loaders.ItemData.getItemDeepCopy(itemId);
        if (item == null) {
            return;
        }
        item.setDateExpire(FileTime.fromDate(java.time.LocalDateTime.now().plusHours(hours)));
        item.setQuantity(1);
        chr.addItemToInventory(item);
        String name = StringData.getItemStringById(itemId);
        chr.chatMessage(ChatType.Notice2, String.format("You got %s (for %d days).", name == null ? "an item" : name,
                hours / 24));
    }

    private static boolean isWearing(Char chr, int itemId) {
        return chr.getEquippedInventory().getItems().stream().anyMatch(i -> i.getItemId() == itemId);
    }

    private static boolean hasAnywhere(Char chr, int itemId) {
        return isWearing(chr, itemId)
                || chr.getEquipInventory().getItems().stream().anyMatch(i -> i.getItemId() == itemId);
    }

    /** Quest 10005's Pink Flower Tubes (Lv. 30), the Lv. 50 ones from the event's later runs, and the tube each job
     * branch gets with no weapon worn ("other": every other branch) */
    private record Tubes(int[] low, int[] high, Map<String, Integer> fallback) {
    }

    private static final int TUBE_HIGH_LEVEL = 50;
    private static final Supplier<Tubes> TUBES = DataTable.cached("secret-agent-tubes", t -> {
        List<Integer> low = new ArrayList<>(), high = new ArrayList<>();
        Map<String, Integer> fallback = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            int id = r.i("item");
            (r.i("level") >= TUBE_HIGH_LEVEL ? high : low).add(id);
            if (r.has("fallback")) {
                fallback.put(r.str("fallback").trim(), id);
            }
        }
        return new Tubes(ints(low), ints(high), fallback);
    });

    private static int[] ints(Collection<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * The Pink Flower Tube (quest 10005) for the weapon the player uses: the Lv. 50 one from Lv. 50. Wands get the
     * staff. @return 0 when there's no tube for that weapon type (the newer classes' weapons)
     */
    public static int agentWeapon(Char chr) {
        Item w = chr.getEquippedItemByBodyPart(net.swordie.ms.client.character.items.BodyPart.Weapon);
        if (w != null) {
            int type = w.getItemId() / 10000;
            if (type == 137) {
                type = 138; // wand -> staff
            }
            Tubes tubes = TUBES.get();
            int[] tier = chr.getLevel() >= TUBE_HIGH_LEVEL ? tubes.high() : tubes.low();
            for (int[] list : new int[][]{tier, tubes.high(), tubes.low()}) {
                for (int id : list) {
                    if (id / 10000 == type) {
                        return id;
                    }
                }
            }
            return 0;
        }
        Map<String, Integer> fallback = TUBES.get().fallback();
        Integer id = fallback.get(String.valueOf(chr.getJob() / 100 % 10));
        return id != null ? id : fallback.getOrDefault("other", 0);
    }

    // ---------------------------------------------------------------------------------------------- the quiz

    /** One Speed Quiz question: the text, four answers and the right one's index. */
    public record Question(String text, List<String> answers, int correct) {
    }

    /** Speed Quiz pools: items, monsters (with the frame to show), and {item, the monster known for dropping it} */
    private record Quiz(int[] items, int[] mobs, int[][] drops, Map<Integer, String> actions) {
    }

    private static final Supplier<Quiz> QUIZ = DataTable.cached("secret-agent-quiz", t -> {
        List<Integer> items = new ArrayList<>(), mobs = new ArrayList<>();
        List<int[]> drops = new ArrayList<>();
        Map<Integer, String> actions = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            int id = r.i("id");
            switch (r.str("kind").trim()) {
                case "item" -> items.add(id);
                case "mob" -> {
                    mobs.add(id);
                    if (r.has("action")) {
                        actions.put(id, r.str("action").trim());
                    }
                }
                case "drop" -> drops.add(new int[]{id, r.i("mob")});
                default -> log.warn("secret-agent-quiz.tsv line {}: unknown kind", r.line());
            }
        }
        return new Quiz(ints(items), ints(mobs), drops.toArray(new int[0][]), actions);
    });

    /** A random question: an item's name, a monster's name, a monster's level, or who drops an item. */
    public static Question randomQuestion() {
        Quiz quiz = QUIZ.get();
        int[] items = quiz.items(), mobPool = quiz.mobs();
        int[][] drops = quiz.drops();
        int type = Util.getRandom(3);
        switch (type) {
            case 0: {
                int id = pick(items);
                return nameQuestion("What is this item?\r\n#i" + id + "#", id, items, true);
            }
            case 1: {
                int id = pick(mobPool);
                return nameQuestion(String.format("Which monster is this?\r\n#fMob/%07d.img/%s/0#", id,
                        quiz.actions().getOrDefault(id, "stand")), id, mobPool, false);
            }
            case 2: {
                int id = pick(mobPool);
                int lv = mobLevel(id);
                if (lv <= 0) {
                    return randomQuestion();
                }
                Set<Integer> opts = new LinkedHashSet<>();
                opts.add(lv);
                while (opts.size() < 4) {
                    int o = lv + (Util.getRandom(1) == 0 ? -1 : 1) * (2 + Util.getRandom(12));
                    if (o > 0) {
                        opts.add(o);
                    }
                }
                List<String> answers = new ArrayList<>();
                for (int o : opts) {
                    answers.add("Lv. " + o);
                }
                return shuffled("What level is #b" + mobName(id) + "#k?", answers, 0);
            }
            default: {
                int[] pair = drops[Util.getRandom(drops.length - 1)];
                Set<Integer> mobs = new LinkedHashSet<>();
                mobs.add(pair[1]);
                for (int[] other : drops) {
                    if (mobs.size() < 4 && other[1] != pair[1] && Util.getRandom(2) == 0) {
                        mobs.add(other[1]);
                    }
                }
                while (mobs.size() < 4) {
                    int[] other = drops[Util.getRandom(drops.length - 1)];
                    mobs.add(other[1]);
                }
                List<String> answers = new ArrayList<>();
                for (int m : mobs) {
                    answers.add(mobName(m));
                }
                return shuffled("Which monster drops #b#t" + pair[0] + "##k?\r\n#i" + pair[0] + "#", answers, 0);
            }
        }
    }

    private static Question nameQuestion(String text, int id, int[] pool, boolean item) {
        Set<Integer> ids = new LinkedHashSet<>();
        ids.add(id);
        while (ids.size() < 4) {
            ids.add(pick(pool));
        }
        List<String> answers = new ArrayList<>();
        for (int i : ids) {
            answers.add(item ? StringData.getItemStringById(i) : mobName(i));
        }
        return shuffled(text, answers, 0);
    }

    private static Question shuffled(String text, List<String> answers, int correct) {
        String right = answers.get(correct);
        List<String> list = new ArrayList<>(answers);
        Collections.shuffle(list);
        return new Question(text, list, list.indexOf(right));
    }

    private static int pick(int[] pool) {
        return pool[Util.getRandom(pool.length - 1)];
    }

    private static String mobName(int id) {
        String n = StringData.getMobStringById(id);
        return n == null ? "???" : n.trim();
    }

    private static int mobLevel(int id) {
        try {
            Mob mob = MobData.getMobDeepCopyById(id);
            return mob == null ? 0 : mob.getForcedMobStat().getLevel();
        } catch (Exception e) {
            return 0;
        }
    }
}
