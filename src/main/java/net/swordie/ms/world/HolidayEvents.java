package net.swordie.ms.world;

import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * The smaller holiday events, brought back every year with their official quests and dialogue (Quest.wz
 * Check/Act/Say), on the {@link SeasonalEvents} calendar:
 * <ul>
 *     <li><b>Winter Preparations</b> (Jan 8 - 31; GMS Jan 2012): 16 towns get ready for the cold (each wants 20 of
 *     something warm from the monsters around it), and Cassandra's Warm Thermometer warms up with every town helped
 *     (15 = the Waist Warmers).</li>
 *     <li><b>Valentine's Day</b> (Feb 7 - 21, Feb 14 holiday): the Ace of Hearts in Ludibrium makes Heart Chocolate
 *     and Chocolate Baskets; Cody's Valentine for Maya.</li>
 *     <li><b>Easter</b> (Mar 25 - Apr 25): Gaga's Great Easter Egg Hunt and Mad Bunny's Red Easter.</li>
 *     <li><b>Spring Flower Festival</b> (May; GMS May 2015): Cassandra's spring flowers, and the flower research of
 *     Betty, Lisa, the Rememberer, Keeny and Chief Tatamo (the flower stickers).</li>
 *     <li><b>Thanksgiving</b> (Nov 16 - 30): Cody's Turkey Egg Hunts and Cody and the Cyberturkeys.</li>
 *     <li><b>Moon Festival</b> (Sep 8 - Oct 4; GMS Sep 2010 + Sep 2011): Smartie Moon Bunny in the towns hosts
 *     it. The Moonlight Sonata story (Grandpa Moon Bunny, Cassandra, the Ellinia fairies, Gold Richie, Baby Moon Bunny
 *     in the Lunar World; the Moonlight Bard medal), Time To Make a Wish (Wish Papers from monsters, 30 = the wish),
 *     Gaga's Moonlight Bottle (30 minutes with the lid open = the Moon Festival Aura), the 1/3/7-day login titles and
 *     the Full Moon week's 2x EXP coupons.</li>
 *     <li><b>New Year's Day</b> (Jan 1 - 20; GMS Jan 2013): spell HAPPY NEW YEAR with the letters monsters
 *     drop, once a day, for the Maple Administrator's Magic Die.</li>
 *     <li><b>Lunar New Year</b> (Feb 5 - 20; GMS Feb 2013, Kid Draco): Cassandra's Game of Yut (Yut Sticks
 *     from monsters, 200 points = a New Year's Gift Box), the New Year's Gift Boxes monsters carry, her Rice Cake Soup,
 *     and Kid Draco in Henesys: a dragon-crystal wish a day and his uncle the Black Dragon to beat at :05 and :35
 *     every hour.</li>
 *     <li><b>Maple Music Festival</b> (Oct 25 - Nov 15; GMS Sep 2016 + Oct 2017): DJ JM in the towns takes
 *     you to the MMF Campgrounds: DJ JM's Lost Equipment (7 pieces monsters picked up; the DJ Booth chair), his daily
 *     Crumpled Flyer (30 minutes to un-crumple = JM's Gift Box), the Saturday Fireworks Shows (3 and 8 PM: a buff for
 *     everyone at the Campgrounds; a weekly invitation = a Fireworks Show Commemorative Box with a Show Picture Chair)
 *     and Traveling Tangyoon's BBQ (bring the ingredients, everyone at the Campgrounds eats when the grill is full).
 *     The concert stages' rhythm game isn't supported by the server, so the stages stay closed.</li>
 * </ul>
 * The events' NPCs that aren't in the towns' map data stand there while the event is on ({@link
 * SeasonalEvents#PLACEMENTS}). Talking to an NPC that starts or ends one of the event's quests opens the quest's
 * official dialogue first ({@link #hasDialog}, NpcHandler: scripts/npc/holidayNpc.py), since the client won't offer
 * quests whose Quest.wz dates are long gone. The items the quests ask for come from monsters while the event is on
 * ({@link #FINDS}), straight into the inventory. Every quest also gives a little EXP and 2 Swordie Coins (at most 10
 * coins a day per event).
 */
public final class HolidayEvents {

    private static final Logger log = LogManager.getLogger(HolidayEvents.class);

    public static final String WINTER = "winter";
    public static final String VALENTINE = "valentine";
    public static final String EASTER = "easter";
    public static final String SPRING = "spring";
    public static final String THANKSGIVING = "thanksgiving";
    public static final String MOON = "moon";
    public static final String NEW_YEARS_DAY = "newyearsday";
    public static final String LUNAR_NEW_YEAR = "lunarnewyear";
    public static final String MMF = "mmf";

    public static final int CASSANDRA = 9201256;
    public static final int CODY = 9200000;
    public static final int ACE_OF_HEARTS = 2041017;
    public static final int GAGA = 9000021;
    public static final int MAD_BUNNY = 9200001;
    public static final int MEDIN = 2050009; // Jr. Officer Medin (Omega Sector)
    public static final int CASSANDRA_2 = 9010010, MAPLE_ADMINISTRATOR_2 = 9201257;
    public static final int SMARTIE_MOON_BUNNY = 9001104; // hosts the Moon Festival in the towns
    public static final int GRANDPA_MOON_BUNNY = 9001105, BABY_MOON_BUNNY = 9001102;
    public static final int LUNAR_WORLD = 922230000;
    // New Year's Day and the Lunar New Year
    public static final int MAPLE_ADMINISTRATOR = 9010000; // in the towns' map data: hosts New Year's Day
    public static final int KID_DRACO = 9010043, KID_DRACO_DRAGON_MAP = 9010042;
    public static final int HAPPY_NEW_YEAR = 9270;
    public static final int YUT = 12090, GIFT_BOX_QUEST = 12091, BLACK_DRAGON_QUEST = 12092, RICE_CAKE_SOUP_QUEST = 12093,
            DRACO_WISH = 12094;
    public static final int YUT_STICKS = 2430659, GIFT_BOX_1 = 2430658, GIFT_BOX_2 = 2430662, RICE_CAKE_SOUP = 2430657;
    public static final int BLACK_DRAGON = 9500533, BLACK_DRAGON_HOMETOWN = 910026100;
    public static final int YUT_POINTS_PER_BOX = 200, YUT_TOTAL_PRIZE = 5000;
    // Rice Cake for Rice Cake Soup, Meat Garnish, Egg Garnish
    public static final int[] RICE_CAKE_SOUP_INGREDIENTS = {3994487, 3994488, 3994489};
    public static final int RICE_CAKE_SOUP_EACH = 20;
    /**
     * Kid Draco's wishes (12095-12099), resources/data/draco-wishes.tsv read once (scripts index it):
     * {Wishbearing crystal, reward, the reward to pick instead (0 = none), reward count}.
     */
    public static final int[][] DRACO_WISHES = DataTable.read("draco-wishes").rows().stream()
            .map(r -> new int[]{r.i("crystal"), r.i("reward"), r.i("alternative"), r.i("count")})
            .toArray(int[][]::new);
    // The Maple Music Festival
    public static final int DJ_JM_TOWN = 9400137, DJ_JM = 9400132, TANGYOON = 9400133;
    public static final int MMF_CAMPGROUNDS = 867112000;
    public static final int JM_EQUIPMENT_FIRST = 59900, JM_EQUIPMENT_LAST = 59906, JM_ITEM_FIRST = 4034900;
    public static final int DJ_BOOTH_CHAIR = 3015573, CRUMPLED_FLYER_QUEST = 59907, UNCRUMPLED_FLYER = 3996113;
    public static final int JM_GIFT_BOX = 2435644, JM_GIFT_BOX_CURES = 10;
    public static final int FIRE_GRILL_SKEWER = 2022352, FIRE_GRILL_SKEWERS_PER_GRILL = 5;
    public static final int[] FIREWORKS_QUESTS = {59920, 59921, 59922};
    public static final int[] FIREWORKS_LETTERS = {3996115, 3996117, 3996119}; // what the envelopes turn into
    public static final int[] FIREWORKS_BOXES = {2435773, 2435774, 2435775};
    public static final int[] SHOW_PICTURE_CHAIRS = {3014014, 3014015, 3014016, 3014017, 3014018};
    public static final int[] BBQ_INGREDIENTS = {4034908, 4034909, 4034910, 4034911, 4034912};
    public static final int BBQ_PER_GRILL = 10, BBQ_GRILLS_FOR_PARTY = 30;
    public static final int FIREWORKS_BUFF = 2022185, BBQ_BUFF = 2022982;

    // Hopeful .. Wishbearing Dragon Crystal
    public static final int DRAGON_CRYSTAL_FIRST = 3994490, DRAGON_CRYSTAL_LAST = 3994499;
    public static final int MOONLIGHT_SONATA = 10866, MUSIC_BOX = 10870, MOON_GRATITUDE = 10871;
    public static final int MOONLIGHT_BARD = 1142250;
    public static final int MOONLIGHT_BOTTLE = 11690;
    public static final int MOON_DAY_1 = 11800, MOON_DAY_3 = 11801, MOON_DAY_7 = 11802, MOON_DAYS_RECORD = 11803;
    public static final int FULL_MOON_EXP = 11805;
    /** Time To Make a Wish: 11681-11688, one per Wish Paper (3994400-3994407); 11680 is the notifier's own. */
    public static final int WISH_FIRST = 11681, WISH_LAST = 11688, WISH_PAPER_FIRST = 3994400, WISH_PAPERS = 30;
    private static final java.time.MonthDay FULL_MOON_FROM = java.time.MonthDay.of(9, 10),
            FULL_MOON_TO = java.time.MonthDay.of(9, 16);


    /** Server-only record: coins given today per event, login reminders. */
    public static final int HOLIDAY_QR = 89999988;
    private static final int COINS_PER_QUEST = 2;
    private static final int COINS_PER_DAY = 10;

    /** The default calendar entries (SeasonalEvents adds them to the calendar file). */
    static final List<SeasonalEvents.Season> SEASONS = List.of(
            new SeasonalEvents.Season(WINTER, "Winter Preparations", java.time.MonthDay.of(1, 8),
                    java.time.MonthDay.of(1, 31), null, true),
            new SeasonalEvents.Season(VALENTINE, "Valentine's Day", java.time.MonthDay.of(2, 7),
                    java.time.MonthDay.of(2, 21), java.time.MonthDay.of(2, 14), true),
            new SeasonalEvents.Season(EASTER, "Easter", java.time.MonthDay.of(3, 25),
                    java.time.MonthDay.of(4, 25), null, true),
            new SeasonalEvents.Season(SPRING, "Spring Flower Festival", java.time.MonthDay.of(5, 1),
                    java.time.MonthDay.of(5, 31), null, true),
            new SeasonalEvents.Season(THANKSGIVING, "Thanksgiving", java.time.MonthDay.of(11, 16),
                    java.time.MonthDay.of(11, 30), null, true),
            new SeasonalEvents.Season(MOON, "Moon Festival", java.time.MonthDay.of(9, 8),
                    java.time.MonthDay.of(10, 4), null, true),
            new SeasonalEvents.Season(NEW_YEARS_DAY, "New Year's Day", java.time.MonthDay.of(1, 1),
                    java.time.MonthDay.of(1, 20), java.time.MonthDay.of(1, 1), true),
            new SeasonalEvents.Season(LUNAR_NEW_YEAR, "Lunar New Year", java.time.MonthDay.of(2, 5),
                    java.time.MonthDay.of(2, 20), null, true),
            new SeasonalEvents.Season(MMF, "Maple Music Festival", java.time.MonthDay.of(10, 25),
                    java.time.MonthDay.of(11, 15), null, true)
    );

    // ----------------------------------------------------------------------------------------------- the quests

    /** Winter Preparations: one per town (Edelstein's is 10547). */
    static final int[] WINTER_TOWN_QUESTS = {10530, 10531, 10532, 10533, 10534, 10535, 10536, 10537, 10538, 10539,
            10540, 10541, 10542, 10543, 10544, 10545, 10547};
    public static final int WARM_THERMOMETER = 10546;
    public static final int THERMOMETER_GOAL = 15;

    private static final Map<String, Set<Integer>> QUESTS = new LinkedHashMap<>();

    static {
        Set<Integer> winter = new LinkedHashSet<>();
        for (int q : WINTER_TOWN_QUESTS) {
            winter.add(q);
        }
        winter.add(WARM_THERMOMETER);
        QUESTS.put(WINTER, winter);
        QUESTS.put(VALENTINE, new LinkedHashSet<>(List.of(8206, 8207, 9670)));
        QUESTS.put(EASTER, new LinkedHashSet<>(List.of(28769, 5069)));
        Set<Integer> spring = new LinkedHashSet<>();
        for (int q = 10240; q <= 10256; q++) {
            spring.add(q);
        }
        QUESTS.put(SPRING, spring);
        QUESTS.put(THANKSGIVING, new LinkedHashSet<>(List.of(28326, 28327, 28328, 28977)));
        // The Moon Festival (Moonlight Sonata 2010; the 2011 Moonlight Bottle, login titles and EXP coupons)
        QUESTS.put(MOON, new LinkedHashSet<>(List.of(MOONLIGHT_SONATA, 10867, 10868, 10869, MUSIC_BOX, 10878, 10879,
                10880, MOONLIGHT_BOTTLE, MOON_DAY_1, MOON_DAY_3, MOON_DAY_7, FULL_MOON_EXP)));
        QUESTS.put(NEW_YEARS_DAY, new LinkedHashSet<>(List.of(HAPPY_NEW_YEAR)));
        QUESTS.put(LUNAR_NEW_YEAR, new LinkedHashSet<>(List.of(YUT, GIFT_BOX_QUEST, RICE_CAKE_SOUP_QUEST)));
    }

    // ------------------------------------------------------------------------------------------- Moon Festival

    /**
     * NPCs of an event's quests that aren't in the towns (or that the client hides by their Npc.wz dates): the event's
     * host NPC starts and hands in their quests instead, and the dialogue is still theirs.
     */
    private static final Map<String, Map<Integer, Integer>> HOSTS = Map.of(
            MOON, Map.of(GRANDPA_MOON_BUNNY, SMARTIE_MOON_BUNNY, BABY_MOON_BUNNY, SMARTIE_MOON_BUNNY,
                    CASSANDRA_2, SMARTIE_MOON_BUNNY, CASSANDRA, SMARTIE_MOON_BUNNY, GAGA, SMARTIE_MOON_BUNNY,
                    MAPLE_ADMINISTRATOR_2, SMARTIE_MOON_BUNNY),
            NEW_YEARS_DAY, Map.of(MAPLE_ADMINISTRATOR_2, MAPLE_ADMINISTRATOR)
    );

    private static boolean npcMatches(String ev, int questNpc, int npc) {
        if (questNpc == npc) {
            return true;
        }
        Map<Integer, Integer> hosts = HOSTS.get(ev);
        return hosts != null && hosts.getOrDefault(questNpc, -1) == npc;
    }

    public static boolean isWish(int questId) {
        return questId >= WISH_FIRST && questId <= WISH_LAST;
    }

    public static boolean isFullMoonWeek() {
        java.time.MonthDay d = java.time.MonthDay.from(net.swordie.ms.util.ResetClock.today());
        return !d.isBefore(FULL_MOON_FROM) && !d.isAfter(FULL_MOON_TO);
    }

    /** Hands in a wish (30 Wish Papers); @param selection the scroll picked for the 'select' wishes (or -1) */
    public static boolean completeWish(Char chr, int questId, int selection) {
        if (!isWish(questId) || !chr.getQuestManager().hasQuestInProgress(questId)
                || !chr.hasItemCount(WISH_PAPER_FIRST + questId - WISH_FIRST, WISH_PAPERS)) {
            return false;
        }
        return chr.getQuestManager().completeQuest(questId, true, selection);
    }

    /** The town a player came to the Lunar World from (for its way out). */
    public static void rememberTown(Char chr) {
        Map<String, String> rec = record(chr);
        rec.put("moonTown", String.valueOf(chr.getField().getId()));
        save(chr, rec);
    }

    public static int returnTown(Char chr) {
        return townOf(chr, "moonTown");
    }

    /** The town remembered under key (Henesys when none). */
    private static int townOf(Char chr, String key) {
        try {
            String town = record(chr).get(key);
            return town == null ? FieldConstants.HENESYS_FIELD_ID : Integer.parseInt(town);
        } catch (NumberFormatException e) {
            return FieldConstants.HENESYS_FIELD_ID;
        }
    }

    /** Login during the Moon Festival: one more festival day for the 1/3/7-day titles (record 11803). */
    private static void countMoonDay(Char chr, Map<String, String> rec, String today) {
        if (today.equals(rec.get("moonLast"))) {
            return;
        }
        int year = net.swordie.ms.util.ResetClock.today().getYear();
        String[] v = rec.getOrDefault("moonDays", "").split("/");
        int days = v.length == 2 && v[0].equals(String.valueOf(year)) ? Integer.parseInt(v[1]) : 0;
        days++;
        rec.put("moonDays", year + "/" + days);
        rec.put("moonLast", today);
        chr.getScriptManager().setQRValue(chr, MOON_DAYS_RECORD, String.valueOf(days), false);
    }

    /** @return the event a quest belongs to, or null */
    public static String eventOf(int questId) {
        for (Map.Entry<String, Set<Integer>> e : QUESTS.entrySet()) {
            if (e.getValue().contains(questId)) {
                return e.getKey();
            }
        }
        if (questId == BLACK_DRAGON_QUEST || (questId > DRACO_WISH && questId <= 12099)) {
            return LUNAR_NEW_YEAR; // Kid Draco's (not offered through holidayNpc)
        }
        if (questId >= JM_EQUIPMENT_FIRST && questId <= JM_EQUIPMENT_LAST) {
            return MMF; // DJ JM's (his own script)
        }
        return null;
    }

    /** Quests of an event that's on run despite their old Quest.wz dates (QuestInfo#isExpiredEvent). */
    public static boolean overridesQuestDates(int questId) {
        String ev = eventOf(questId);
        if (ev == null && (isWish(questId) || questId == 11680 || questId == MOON_GRATITUDE)) {
            ev = MOON;
        }
        if (ev == null && questId >= BLACK_DRAGON_QUEST && questId <= 12099 && questId != RICE_CAKE_SOUP_QUEST) {
            ev = LUNAR_NEW_YEAR;
        }
        if (ev == null && ((questId >= JM_EQUIPMENT_FIRST && questId <= CRUMPLED_FLYER_QUEST)
                || (questId >= FIREWORKS_QUESTS[0] && questId <= FIREWORKS_QUESTS[2]))) {
            ev = MMF;
        }
        return ev != null && SeasonalEvents.isActive(ev);
    }

    // ----------------------------------------------------------------------------------------- monster finds

    /**
     * An item monsters have while an event is on.
     *
     * @param quests    quests in progress that want it (empty: any time during the event, for {@code openQuest})
     * @param regions   map id / 1,000,000 of where the monsters must be (empty: anywhere outside towns)
     * @param openQuest with no quests: only until this quest is done (0: always)
     */
    private record Find(String event, int item, int need, int chance, int minLevel, int[] quests, int[] regions,
                        int openQuest) {
    }

    /** resources/data/holiday-finds.tsv: event, item, need, chance, minLevel, quests, regions, openQuest */
    private static final Supplier<List<Find>> FINDS = DataTable.cached("holiday-finds", t ->
            t.rows().stream().map(r -> new Find(r.str("event").trim(), r.i("item"), r.i("need"), r.i("chance"),
                    r.i("minLevel"), r.ints("quests"), r.ints("regions"), r.i("openQuest", 0))).toList());

    private static boolean contains(int[] a, int v) {
        for (int x : a) {
            if (x == v) {
                return true;
            }
        }
        return false;
    }

    /** Mob death (SeasonalEvents#onMobKilled). */
    static void onMobKilled(Mob mob) {
        if (mob.getTemplateId() == BLACK_DRAGON) {
            onBlackDragonKilled(mob);
            return;
        }
        Char killer = mob.getMostDamageChar();
        if (killer == null || mob.isBoss() || mob.isBossFightAdd() || mob.getField().getInfo().isTown()
                || killer.getField() != mob.getField()) {
            return;
        }
        int region = FieldConstants.regionOf(mob.getField().getId());
        boolean nearLevel = Math.abs(mob.getForcedMobStat().getLevel() - killer.getLevel()) <= 20;
        QuestManager qm = killer.getQuestManager();
        for (Find f : FINDS.get()) {
            if (killer.getLevel() < f.minLevel() || !SeasonalEvents.isActive(f.event())) {
                continue;
            }
            if (f.regions().length > 0 ? !contains(f.regions(), region) : !nearLevel) {
                continue; // area finds: the monsters of that area; the others: monsters near your level
            }
            boolean wanted;
            if (f.quests().length > 0) {
                wanted = false;
                for (int q : f.quests()) {
                    if (qm.hasQuestInProgress(q)) {
                        wanted = true;
                        break;
                    }
                }
            } else {
                wanted = f.openQuest() == 0 || !qm.hasQuestCompleted(f.openQuest());
                if (f.event().equals(SPRING) && allSpringResearchDone(qm)) {
                    wanted = false;
                }
            }
            if (!wanted || killer.hasItemCount(f.item(), f.need()) || !Util.succeedProp(f.chance(), 10000)) {
                continue;
            }
            SeasonalEvents.grantFind(killer, f.item(), 1);
        }
    }

    private static boolean allSpringResearchDone(QuestManager qm) {
        for (int q = 10242; q <= 10256; q++) {
            if (!qm.hasQuestCompleted(q)) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------------------------------ NPC dialogue

    /** What an NPC can do for the player: "s:quest" start, "e:quest" hand in, "p:quest" in progress (not done). */
    public static List<String> npcOptions(Char chr, int npc) {
        List<String> res = new ArrayList<>();
        QuestManager qm = chr.getQuestManager();
        for (Map.Entry<String, Set<Integer>> e : QUESTS.entrySet()) {
            if (!SeasonalEvents.isActive(e.getKey())) {
                continue;
            }
            for (int q : e.getValue()) {
                QuestInfo qi = QuestData.getQuestInfoById(q);
                if (qi == null) {
                    continue;
                }
                if (q == FULL_MOON_EXP && !isFullMoonWeek()) {
                    continue;
                }
                if (qm.hasQuestInProgress(q)) {
                    if (npcMatches(e.getKey(), qi.getEndNpc(), npc)) {
                        Quest quest = qm.getQuestById(q);
                        boolean done = quest != null && quest.isComplete(chr) && qm.meetsCompleteRequirements(q);
                        res.add((done ? "e:" : "p:") + q);
                    }
                } else if (npcMatches(e.getKey(), qi.getStartNpc(), npc) && qm.canStartQuestQuietly(q)) {
                    res.add("s:" + q);
                }
            }
        }
        return res;
    }

    /** NpcHandler: an NPC with something to do in an event that's on talks about that first. */
    public static boolean hasDialog(Char chr, int npc) {
        try {
            if (SeasonalEvents.activeIds().stream().noneMatch(QUESTS::containsKey)) {
                return false;
            }
            for (String o : npcOptions(chr, npc)) {
                if (!o.startsWith("p:")) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("HolidayEvents: options of NPC " + npc, e);
        }
        return false;
    }

    /** holidayNpc.py's "(Talk)": the NPC's own shop or script. */
    public static void talkNormally(Char chr, int npc) {
        var shop = net.swordie.ms.loaders.NpcData.getShopById(npc);
        if (shop != null && !shop.getItems().isEmpty()) {
            net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
                if (chr.isOnline() && chr.getShop() == null) {
                    chr.setShop(shop);
                    chr.write(net.swordie.ms.connection.packet.ShopDlg.openShop(chr, 0, shop));
                }
            }, 200);
            return;
        }
        SeasonalEvents.openNpcLater(chr, npc);
    }

    /** @return the event's name, for the dialogue */
    public static String eventName(int questId) {
        String ev = eventOf(questId);
        SeasonalEvents.Season s = ev == null ? null : SeasonalEvents.get(ev);
        return s == null ? "" : s.name();
    }

    // -------------------------------------------------------------------------------------------- rewards

    /** QuestManager#completeQuest (with rewards): the event's EXP and coins, and the Warm Thermometer. */
    public static void onQuestCompleted(Char chr, int questId) {
        String ev = eventOf(questId);
        if (ev == null) {
            return;
        }
        try {
            chr.addExp(SeasonalEvents.expPercent(chr, questId == WARM_THERMOMETER ? 5 : 1));
            Map<String, String> rec = record(chr);
            String key = "coins." + ev;
            String today = net.swordie.ms.util.ResetClock.today().toString();
            String[] v = rec.getOrDefault(key, "").split("/");
            int given = v.length == 2 && v[0].equals(today) ? Integer.parseInt(v[1]) : 0;
            if (given < COINS_PER_DAY) {
                int coins = Math.min(COINS_PER_QUEST, COINS_PER_DAY - given);
                rec.put(key, today + "/" + (given + coins));
                save(chr, rec);
                SeasonalEvents.giveCoins(chr, coins, SeasonalEvents.get(ev) == null ? ev : SeasonalEvents.get(ev).name());
            }
            if (questId == MUSIC_BOX) {
                moonGratitude(chr);
            }
            if (questId == RICE_CAKE_SOUP_QUEST) {
                for (int item : RICE_CAKE_SOUP_INGREDIENTS) {
                    chr.consumeItem(item, RICE_CAKE_SOUP_EACH);
                }
                SeasonalEvents.giveItem(chr, RICE_CAKE_SOUP, 1);
            }
            if (WINTER.equals(ev) && questId != WARM_THERMOMETER
                    && chr.getQuestManager().hasQuestInProgress(WARM_THERMOMETER)) {
                int t = thermometer(chr);
                if (t < THERMOMETER_GOAL) {
                    t++;
                    chr.getScriptManager().setQRValue(chr, WARM_THERMOMETER, String.valueOf(t), false);
                    chr.chatMessage(ChatType.Notice2, t >= THERMOMETER_GOAL
                            ? "[Winter] The Warm Thermometer is as warm as it gets! Show it to Cassandra."
                            : String.format("[Winter] The Warm Thermometer warms up: %d/%d.", t, THERMOMETER_GOAL));
                }
            }
        } catch (Exception e) {
            log.error("HolidayEvents: rewards of quest " + questId + " for " + chr.getName(), e);
        }
    }

    /** Grandpa Moon Bunny's Gratitude (10871): the Moonlight Bard medal once Baby Moon Bunny has the music box. */
    private static void moonGratitude(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (qm.hasQuestCompleted(MOON_GRATITUDE)) {
            return;
        }
        if (!chr.canHold(MOONLIGHT_BARD, 1)) {
            chr.chatMessage(ChatType.Notice2, "[Moon Festival] Make room in your Equip inventory, then talk to "
                    + "Smartie Moon Bunny for Grandpa Moon Bunny's thanks.");
            return;
        }
        if (!qm.hasQuestInProgress(MOON_GRATITUDE)) {
            chr.getScriptManager().startQuestNoCheck(MOON_GRATITUDE);
        }
        qm.completeQuest(MOON_GRATITUDE, false);
        SeasonalEvents.giveItem(chr, MOONLIGHT_BARD, 1);
        chr.chatMessage(ChatType.Notice2, "[Moon Festival] Grandpa Moon Bunny: The Moonlight Sonata rings across the "
                + "moon again... Thank you. Please take this medal. (Moonlight Bard)");
    }

    /** Moon Festival: the medal again, for those who had no room when they handed in the music box. */
    public static boolean moonGratitudeDue(Char chr) {
        QuestManager qm = chr.getQuestManager();
        return qm.hasQuestCompleted(MUSIC_BOX) && !qm.hasQuestCompleted(MOON_GRATITUDE);
    }

    public static void giveMoonGratitude(Char chr) {
        moonGratitude(chr);
    }

    // ----------------------------------------------------------------------------- the Lunar New Year

    /** @return {points now, total this year} of Cassandra's Game of Yut */
    public static int[] yutPoints(Char chr) {
        Map<String, String> rec = record(chr);
        String year = String.valueOf(net.swordie.ms.util.ResetClock.today().getYear());
        String[] v = rec.getOrDefault("yut", "").split("/");
        if (v.length != 3 || !v[0].equals(year)) {
            return new int[]{0, 0};
        }
        return new int[]{Integer.parseInt(v[1]), Integer.parseInt(v[2])};
    }

    private static void saveYut(Char chr, int points, int total) {
        Map<String, String> rec = record(chr);
        rec.put("yut", net.swordie.ms.util.ResetClock.today().getYear() + "/" + points + "/" + total);
        save(chr, rec);
    }

    /**
     * Throws a set of Yut Sticks (item script): Do 1, Gae 2, Geol 3, Yut 4, Mo 5 points.
     *
     * @return what came up, for the chat; null if there are no sticks
     */
    public static String throwYut(Char chr) {
        if (!chr.hasItem(YUT_STICKS)) {
            return null;
        }
        chr.consumeItem(YUT_STICKS, 1);
        int r = Util.getRandom(99);
        int pts;
        String name;
        if (r < 15) {
            pts = 1;
            name = "Do";
        } else if (r < 50) {
            pts = 2;
            name = "Gae";
        } else if (r < 85) {
            pts = 3;
            name = "Geol";
        } else if (r < 97) {
            pts = 4;
            name = "Yut";
        } else {
            pts = 5;
            name = "Mo";
        }
        int[] p = yutPoints(chr);
        int before = p[1];
        saveYut(chr, p[0] + pts, p[1] + pts);
        if (before < YUT_TOTAL_PRIZE && before + pts >= YUT_TOTAL_PRIZE) {
            SeasonalEvents.giveCoins(chr, 20, String.format("%,d Yut points this year", YUT_TOTAL_PRIZE));
        }
        return String.format("%s! +%d (%d points; Kid Draco trades %d for a New Year's Gift Box)", name, pts,
                p[0] + pts, YUT_POINTS_PER_BOX);
    }

    /** Trades Yut points for New Year's Gift Boxes (as many as the points pay for). @return how many */
    public static int tradeYut(Char chr) {
        int[] p = yutPoints(chr);
        int boxes = p[0] / YUT_POINTS_PER_BOX;
        if (boxes <= 0) {
            return 0;
        }
        boxes = Math.min(boxes, 50);
        if (!chr.canHold(GIFT_BOX_2, boxes)) {
            return -1;
        }
        saveYut(chr, p[0] - boxes * YUT_POINTS_PER_BOX, p[1]);
        chr.addItemToInventory(GIFT_BOX_2, boxes);
        return boxes;
    }

    public static boolean hasDragonCrystals(Char chr, boolean charged) {
        for (int[] w : DRACO_WISHES) {
            if (chr.hasItem(charged ? w[0] : w[0] - 1)) {
                return true;
            }
        }
        return false;
    }

    /** Kid Draco's wish of the day: the five Hopeful Dragon Crystals (Act0 of 12094, 60 minutes to charge). */
    public static boolean canWishToDraco(Char chr) {
        return SeasonalEvents.isActive(LUNAR_NEW_YEAR) && !hasDragonCrystals(chr, false) && !hasDragonCrystals(chr, true)
                && !chr.getQuestManager().hasQuestInProgress(DRACO_WISH) && !doneToday(chr, DRACO_WISH);
    }

    /**
     * Grants one of Kid Draco's wishes (a charged crystal): the reward, and the day's crystals are used up.
     *
     * @param wish      0-4 (DRACO_WISHES)
     * @param pickOther for the two-scroll wishes: the second scroll
     */
    public static boolean grantDracoWish(Char chr, int wish, boolean pickOther) {
        if (wish < 0 || wish >= DRACO_WISHES.length || !chr.hasItem(DRACO_WISHES[wish][0])) {
            return false;
        }
        int reward = pickOther && DRACO_WISHES[wish][2] != 0 ? DRACO_WISHES[wish][2] : DRACO_WISHES[wish][1];
        int count = DRACO_WISHES[wish][3];
        if (!chr.canHold(reward, count)) {
            return false;
        }
        for (int c = DRAGON_CRYSTAL_FIRST; c <= DRAGON_CRYSTAL_LAST; c++) {
            int n = countOf(chr, c);
            if (n > 0) {
                chr.consumeItem(c, n);
            }
        }
        QuestManager qm = chr.getQuestManager();
        int q = DRACO_WISH + 1 + wish;
        for (int quest : new int[]{DRACO_WISH, q}) {
            if (!qm.hasQuestInProgress(quest)) {
                chr.getScriptManager().startQuestNoCheck(quest);
            }
            qm.completeQuest(quest, false);
        }
        chr.addItemToInventory(reward, count);
        onQuestCompleted(chr, q); // the event's EXP and coins
        return true;
    }

    private static int countOf(Char chr, int itemId) {
        int n = 0;
        while (n < 50 && chr.hasItemCount(itemId, n + 1)) {
            n++;
        }
        return n;
    }

    /** The Black Dragon's door is open from :05 to :14 and :35 to :44 of every hour, once per opening. */
    public static boolean isDragonTime() {
        int m = net.swordie.ms.util.ResetClock.now().getMinute();
        return (m >= 5 && m < 15) || (m >= 35 && m < 45);
    }

    private static String dragonWindow() {
        java.time.LocalDateTime t = net.swordie.ms.util.ResetClock.now().toLocalDateTime();
        return t.toLocalDate() + "T" + t.getHour() + (t.getMinute() < 30 ? "a" : "b");
    }

    public static boolean foughtDragonThisTime(Char chr) {
        return dragonWindow().equals(record(chr).get("dragon"));
    }

    public static void enterDragon(Char chr) {
        Map<String, String> rec = record(chr);
        rec.put("dragon", dragonWindow());
        rec.put("dragonTown", String.valueOf(chr.getField().getId()));
        save(chr, rec);
    }

    public static int dragonReturnTown(Char chr) {
        return townOf(chr, "dragonTown");
    }

    /** Draco Uncle Black Dragon beaten: two New Year's Gift Boxes for everyone in the room. */
    private static void onBlackDragonKilled(Mob mob) {
        if (mob.getField() == null || mob.getField().getId() != BLACK_DRAGON_HOMETOWN) {
            return;
        }
        for (Char chr : new ArrayList<>(mob.getField().getChars())) {
            try {
                QuestManager qm = chr.getQuestManager();
                if (!qm.hasQuestInProgress(BLACK_DRAGON_QUEST)) {
                    chr.getScriptManager().startQuestNoCheck(BLACK_DRAGON_QUEST);
                }
                qm.completeQuest(BLACK_DRAGON_QUEST, false);
                if (chr.canHold(GIFT_BOX_2, 2)) {
                    chr.addItemToInventory(GIFT_BOX_2, 2);
                }
                onQuestCompleted(chr, BLACK_DRAGON_QUEST);
                chr.chatMessage(ChatType.Notice2, "[Lunar New Year] Kid Draco: You beat Uncle! Here, two New Year's "
                        + "Gift Boxes. Talk to me when you want to go home.");
            } catch (Exception e) {
                log.error("HolidayEvents: Black Dragon reward for " + chr.getName(), e);
            }
        }
    }

    // --------------------------------------------------------------------------- Maple Music Festival

    /** @return whether a once-a-day quest was completed today (its Quest.wz start checks need records we don't keep) */
    public static boolean doneToday(Char chr, int questId) {
        Quest q = chr.getQuestManager().getQuestById(questId);
        if (q == null || !chr.getQuestManager().hasQuestCompleted(questId) || q.getCompletedTime() == null) {
            return false;
        }
        LocalDate done = net.swordie.ms.util.ResetClock.dayOf(q.getCompletedTime().toMillis());
        return done.equals(net.swordie.ms.util.ResetClock.today());
    }

    /** DJ JM's Crumpled Flyer: once a day. */
    public static boolean canTakeFlyer(Char chr) {
        return SeasonalEvents.isActive(MMF) && !chr.getQuestManager().hasQuestInProgress(CRUMPLED_FLYER_QUEST)
                && !doneToday(chr, CRUMPLED_FLYER_QUEST);
    }

    /** The town a player came to the Campgrounds from. */
    public static void rememberMmfTown(Char chr) {
        Map<String, String> rec = record(chr);
        rec.put("mmfTown", String.valueOf(chr.getField().getId()));
        save(chr, rec);
    }

    public static int mmfReturnTown(Char chr) {
        return townOf(chr, "mmfTown");
    }

    /** @return the DJ JM equipment quest in progress, the next one to start, or 0 when all are done */
    public static int jmEquipmentQuest(Char chr) {
        QuestManager qm = chr.getQuestManager();
        for (int q = JM_EQUIPMENT_FIRST; q <= JM_EQUIPMENT_LAST; q++) {
            if (!qm.hasQuestCompleted(q)) {
                return q;
            }
        }
        return 0;
    }

    /** Hands in a piece of DJ JM's equipment: 2% EXP and the coins; the last one gives his DJ Booth chair. */
    public static boolean returnJmEquipment(Char chr, int quest) {
        int item = JM_ITEM_FIRST + quest - JM_EQUIPMENT_FIRST;
        QuestManager qm = chr.getQuestManager();
        if (!qm.hasQuestInProgress(quest) || !chr.hasItem(item)) {
            return false;
        }
        if (quest == JM_EQUIPMENT_LAST && !chr.canHold(DJ_BOOTH_CHAIR, 1)) {
            return false;
        }
        chr.consumeItem(item, 1);
        qm.completeQuest(quest, false);
        chr.addExp(SeasonalEvents.expPercent(chr, 1));
        onQuestCompleted(chr, quest);
        if (quest == JM_EQUIPMENT_LAST) {
            SeasonalEvents.giveItem(chr, DJ_BOOTH_CHAIR, 1);
        }
        return true;
    }

    /** DJ JM's Crumpled Flyer (59907, once a day): un-crumpled after 30 minutes = JM's Gift Box. */
    public static boolean flattenedFlyer(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (!qm.hasQuestInProgress(CRUMPLED_FLYER_QUEST) || !chr.hasItem(UNCRUMPLED_FLYER)
                || !chr.canHold(JM_GIFT_BOX, 1)) {
            return false;
        }
        chr.consumeItem(UNCRUMPLED_FLYER, 1);
        qm.completeQuest(CRUMPLED_FLYER_QUEST, false);
        chr.addItemToInventory(JM_GIFT_BOX, 1);
        onQuestCompleted(chr, CRUMPLED_FLYER_QUEST);
        return true;
    }

    /** @return 0-2: the week of the festival (for the weekly Fireworks Show invitation) */
    public static int mmfWeek() {
        SeasonalEvents.Season s = SeasonalEvents.get(MMF);
        if (s == null) {
            return 0;
        }
        LocalDate today = net.swordie.ms.util.ResetClock.today();
        LocalDate start = s.start().atYear(today.getYear());
        if (start.isAfter(today)) {
            start = start.minusYears(1);
        }
        return (int) Math.max(0, Math.min(2, java.time.temporal.ChronoUnit.DAYS.between(start, today) / 7));
    }

    /** Opens a Fireworks Show Commemorative Box: a Show Picture Chair and some fireworks. */
    public static String openFireworksBox(Char chr, int boxId) {
        if (!chr.hasItem(boxId)) {
            return null;
        }
        int chair = SHOW_PICTURE_CHAIRS[Util.getRandom(SHOW_PICTURE_CHAIRS.length - 1)];
        if (!chr.canHold(chair, 1) || !chr.canHold(FIREWORKS_BUFF, 5)) {
            chr.chatMessage(ChatType.Notice2, "Make sure your Set-up and Use inventories have a free slot first.");
            return null;
        }
        chr.consumeItem(boxId, 1);
        chr.addItemToInventory(chair, 1);
        chr.addItemToInventory(FIREWORKS_BUFF, 5);
        String n = net.swordie.ms.loaders.StringData.getItemStringById(chair);
        return (n == null ? String.valueOf(chair) : n) + " and Fireworks x5";
    }

    /** Opens JM's Gift Box: an EXP coupon and All Cure Potions. */
    public static boolean openJmGiftBox(Char chr) {
        if (!chr.hasItem(JM_GIFT_BOX) || !chr.canHold(ItemConstants.EXP_COUPON_2X_1_HOUR, 1)
                || !chr.canHold(ItemConstants.ALL_CURE_POTION, JM_GIFT_BOX_CURES)) {
            return false;
        }
        chr.consumeItem(JM_GIFT_BOX, 1);
        chr.addItemToInventory(ItemConstants.EXP_COUPON_2X_1_HOUR, 1);
        chr.addItemToInventory(ItemConstants.ALL_CURE_POTION, JM_GIFT_BOX_CURES);
        return true;
    }

    /** The grills cooked at the Campgrounds since the last BBQ party (all channels). */
    private static final java.util.concurrent.atomic.AtomicInteger GRILLS = new java.util.concurrent.atomic.AtomicInteger();

    public static int grillsCooked() {
        return GRILLS.get();
    }

    /**
     * Gives Tangyoon 10 of each BBQ ingredient: a skewer and a coin for the cook, and when the grill has had enough,
     * everyone at the Campgrounds (every channel) eats.
     *
     * @return 1 cooked, 2 cooked and the party started, 0 not enough ingredients, -1 no room
     */
    public static int grill(Char chr) {
        for (int item : BBQ_INGREDIENTS) {
            if (!chr.hasItemCount(item, BBQ_PER_GRILL)) {
                return 0;
            }
        }
        if (!chr.canHold(FIRE_GRILL_SKEWER, FIRE_GRILL_SKEWERS_PER_GRILL)) {
            return -1;
        }
        for (int item : BBQ_INGREDIENTS) {
            chr.consumeItem(item, BBQ_PER_GRILL);
        }
        chr.addItemToInventory(FIRE_GRILL_SKEWER, FIRE_GRILL_SKEWERS_PER_GRILL);
        chr.addExp(SeasonalEvents.expPercent(chr, 1));
        Map<String, String> rec = record(chr);
        String today = net.swordie.ms.util.ResetClock.today().toString();
        String[] v = rec.getOrDefault("coins." + MMF, "").split("/");
        int given = v.length == 2 && v[0].equals(today) ? Integer.parseInt(v[1]) : 0;
        if (given < COINS_PER_DAY) {
            rec.put("coins." + MMF, today + "/" + (given + 1));
            save(chr, rec);
            SeasonalEvents.giveCoins(chr, 1, "Maple Music Festival");
        }
        if (GRILLS.incrementAndGet() >= BBQ_GRILLS_FOR_PARTY) {
            GRILLS.set(0);
            campgroundsParty(BBQ_BUFF, "[MMF] Tangyoon: The grill is full! BBQ PARTY at the Campgrounds - everybody "
                    + "eat up! (ATT and Magic ATT +40 for 30 minutes)");
            return 2;
        }
        return 1;
    }

    /** Gives everyone at the MMF Campgrounds (all channels) an item's buff, with a message. */
    private static void campgroundsParty(int buffItem, String msg) {
        for (World world : net.swordie.ms.Server.getInstance().getWorlds()) {
            for (Channel channel : world.getChannels()) {
                net.swordie.ms.world.field.Field f = channel.getFields().get(MMF_CAMPGROUNDS);
                if (f == null) {
                    continue;
                }
                for (Char c : new ArrayList<>(f.getChars())) {
                    try {
                        c.getScriptManager().useItem(buffItem);
                        c.chatMessage(ChatType.Notice2, msg);
                    } catch (Exception e) {
                        log.error("HolidayEvents: MMF buff for " + c.getName(), e);
                    }
                }
            }
        }
    }

    /** Saturdays at 3 PM and 8 PM, for 10 minutes. */
    public static boolean isFireworksTime() {
        java.time.LocalDateTime t = net.swordie.ms.util.ResetClock.now().toLocalDateTime();
        return t.getDayOfWeek() == java.time.DayOfWeek.SATURDAY && (t.getHour() == 15 || t.getHour() == 20)
                && t.getMinute() < 10;
    }

    /** Every minute (SeasonalEvents#tick): the Saturday Fireworks Show at the Campgrounds. */
    public static void mmfTick() {
        if (!SeasonalEvents.isActive(MMF) || !isFireworksTime()) {
            return;
        }
        int m = net.swordie.ms.util.ResetClock.now().getMinute();
        if (m == 0) {
            for (World world : net.swordie.ms.Server.getInstance().getWorlds()) {
                for (Channel channel : world.getChannels()) {
                    for (Char c : new ArrayList<>(channel.getChars().values())) {
                        c.chatMessage(ChatType.Notice2, "[MMF] The Fireworks Show is starting at the MMF Campgrounds! "
                                + "(DJ JM in any major town takes you there.)");
                    }
                }
            }
        }
        if (m % 3 == 0) {
            campgroundsParty(FIREWORKS_BUFF, "[MMF] Boom! The sky over the Campgrounds lights up! (ATT and Magic ATT "
                    + "+20)");
        }
    }

    public static int thermometer(Char chr) {
        try {
            String v = chr.getScriptManager().getQRValue(WARM_THERMOMETER);
            return v == null || v.isBlank() ? 0 : Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ------------------------------------------------------------------------------------------- holiday boxes

    private record Prize(int item, int count, int weight) {
    }

    /** resources/data/holiday-box-prizes.tsv: box (easter, new-year-1, new-year-2), item, count, weight */
    private static final Supplier<Map<String, List<Prize>>> PRIZES = DataTable.cached("holiday-box-prizes", t -> {
        Map<String, List<Prize>> m = new java.util.HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(r.str("box").trim(), k -> new java.util.ArrayList<>())
                    .add(new Prize(r.i("item"), r.i("count"), r.i("weight")));
        }
        m.replaceAll((k, v) -> List.copyOf(v));
        return m;
    });

    private static List<Prize> prizes(String box) {
        return PRIZES.get().getOrDefault(box, List.of());
    }

    /**
     * Opens a holiday box (item script): a prize and 2 Swordie Coins.
     *
     * @return what was inside, for the chat; null if there was no room or no box
     */

    public static String openBox(Char chr, int boxId) {
        if (!chr.hasItem(boxId)) {
            return null;
        }
        List<Prize> table = prizes(boxId == GIFT_BOX_1 ? "new-year-1" : boxId == GIFT_BOX_2 ? "new-year-2" : "easter");
        for (int sample : new int[]{ItemConstants.ELIXIR, ItemConstants.SPELL_TRACE_ID}) { // a Use and an Etc slot
            if (!chr.canHold(sample, 50)) {
                chr.chatMessage(ChatType.Notice2, "Make sure your Use and Etc inventories have a free slot first.");
                return null;
            }
        }
        int total = table.stream().mapToInt(Prize::weight).sum();
        int r = Util.getRandom(total - 1);
        Prize p = table.get(0);
        for (Prize x : table) {
            r -= x.weight();
            if (r < 0) {
                p = x;
                break;
            }
        }
        chr.consumeItem(boxId, 1);
        chr.addItemToInventory(p.item(), p.count());
        SeasonalEvents.giveCoins(chr, 2, "a holiday box");
        String n = net.swordie.ms.loaders.StringData.getItemStringById(p.item());
        return (n == null ? String.valueOf(p.item()) : n) + (p.count() > 1 ? " x" + p.count() : "");
    }

    // ---------------------------------------------------------------------------------------------- the world

    /** The start announcement (SeasonalEvents#announceStart), or null for the default one. */
    static String announceText(String id, String until) {
        return switch (id) {
            case WINTER -> "[Event] Winter Preparations (until " + until + "): the towns need help getting ready for "
                    + "the cold! Talk to Cassandra in any major town.";
            case VALENTINE -> "[Event] Valentine's Day (until " + until + ")! The Ace of Hearts in Ludibrium makes "
                    + "chocolate, and Cody (major towns) has a favor to ask.";
            case EASTER -> "[Event] Easter (until " + until + ")! Gaga's Great Easter Egg Hunt and Mad Bunny are in "
                    + "Henesys.";
            case SPRING -> "[Event] The Spring Flower Festival (until " + until + ")! Talk to Cassandra in any major "
                    + "town.";
            case THANKSGIVING -> "[Event] Thanksgiving (until " + until + ")! Cody (major towns) is hunting for "
                    + "Turkey Eggs.";
            case MOON -> "[Event] The Moon Festival (until " + until + ")! Smartie Moon Bunny (major towns, Lv. 10+) "
                    + "has wishes to grant, and Grandpa Moon Bunny has a favor to ask.";
            case NEW_YEARS_DAY -> "[Event] Happy New Year (until " + until + ")! Monsters carry the letters of HAPPY NEW "
                    + "YEAR: spell it for the Maple Administrator once a day (Lv. 13+).";
            case LUNAR_NEW_YEAR -> "[Event] Lunar New Year (until " + until + ")! Cassandra (major towns) has the Game "
                    + "of Yut and Rice Cake Soup; Kid Draco in Henesys grants a wish a day, and his uncle the Black "
                    + "Dragon takes challengers at :05 and :35.";
            case MMF -> "[Event] The Maple Music Festival (until " + until + ")! DJ JM (major towns, Lv. 33+) takes you "
                    + "to the MMF Campgrounds: BBQ, lost equipment, and Fireworks Shows on Saturdays at 3 and 8 PM.";
            default -> null;
        };
    }

    /** Login: a reminder about the events that are on, once a day each. */
    public static void onLogin(Char chr) {
        Map<String, String> rec = record(chr);
        String today = net.swordie.ms.util.ResetClock.today().toString();
        if (SeasonalEvents.isActive(MOON) && chr.getLevel() >= 10) {
            countMoonDay(chr, rec, today);
            save(chr, rec);
        }
        List<String> msgs = new ArrayList<>();
        for (String ev : QUESTS.keySet()) {
            if (!SeasonalEvents.isActive(ev) || today.equals(rec.get("notice." + ev))) {
                continue;
            }
            SeasonalEvents.Season s = SeasonalEvents.get(ev);
            String until = SeasonalEvents.windowText(ev).replaceAll(".* - ", "");
            String text = announceText(ev, until);
            if (s != null && text != null) {
                rec.put("notice." + ev, today);
                msgs.add(text.replace("[Event]", "[" + s.name() + "]"));
            }
        }
        if (msgs.isEmpty()) {
            return;
        }
        save(chr, rec);
        net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            if (chr.isOnline()) {
                for (String m : msgs) {
                    chr.chatMessage(ChatType.Notice2, m);
                }
            }
        }, 9_000);
    }

    // ------------------------------------------------------------------------------------------- the record

    static Map<String, String> record(Char chr) {
        Map<String, String> m = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(HOLIDAY_QR);
        if (v != null) {
            for (String kv : v.split(";")) {
                int i = kv.indexOf('=');
                if (i > 0) {
                    m.put(kv.substring(0, i), kv.substring(i + 1));
                }
            }
        }
        return m;
    }

    static void save(Char chr, Map<String, String> m) {
        StringBuilder sb = new StringBuilder();
        m.forEach((k, v) -> sb.append(sb.length() == 0 ? "" : ";").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(HOLIDAY_QR, sb.toString());
    }
}
