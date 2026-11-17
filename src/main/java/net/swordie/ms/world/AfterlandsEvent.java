package net.swordie.ms.world;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The Afterlands (GMS v177 "Strange Stories", Oct 2016). The story's quests (63020-63122) are all in Quest.wz, but
 * the dialogue, scenes and helper NPCs live in scripts; these are written from the quests' own structure (Quest.wz
 * Check/QuestInfo/Say) and what is known of the 2016 event.
 * <p>
 * Lv. 75+, all year. The Old Man in Henesys has four requests, and the last one gets you killed: you wake up in the
 * Land of Beginnings, where Ashkaya explains that the Huge Door home has twelve locks. The four lands around it each
 * hold some of the keys, and they affect each other: the Solar Pulley in the Land of Warriors turns day into night,
 * the children of the Land of Innocence make it rain (eating the Snack Hoard) or thunder (knocking over the Ludi-Block
 * Fort), and the Land of Contemplation gets its color back from Billy's paint. The day/night and the weather are each
 * player's own (quest record 63090: DnN, raining, lightning), like the quests that check them.
 * <p>
 * The twelve keys: Adler's White Key, the Lightning-Touched Wooden Key, the God of Battle's Key, the Golden Key, the
 * Raven Black Key, the Crystalline Key (in Tina's storybook, opened with the Balrog's Key), the Cast Iron Key, the
 * Yellow Mage's Key (Judgment Day), the Carved Stone Key and the Molded Clay Key (the Old Tombstone Maker), the
 * Heart-shaped Key (Lonnie) and the Striped Candy Key (the Silent Boy). With all twelve in the door (quest record
 * 63099), Home, Sweet Home (63086) takes you back to Henesys with the Weird Gift Box: the four Afterlands figurines.
 * <p>
 * The Henesys prologue (Chief Stan, Camila, the Suspicious Sorcerer: 63112-63118) ran Oct 19-25 2016 and runs again
 * while Halloween is on.
 */
public final class AfterlandsEvent {

    private static final Logger log = LogManager.getLogger(AfterlandsEvent.class);

    public static final int MIN_LEVEL = 75;
    public static final int BEGINNINGS = 867113100;
    public static final int WARRIORS = 867113200;
    public static final int RICHES = 867113300;
    public static final int CONTEMPLATION = 867113400;
    public static final int INNOCENCE = 867113500;
    public static final int TOMB_WORKSHOP = 867113722;
    public static final int TOMB_FIELD = 867113900;
    public static final int HENESYS = 100000000;

    public static final int OLD_MAN = 9400200;
    public static final int SUSPICIOUS_SORCERER = 9400300;
    public static final int SOLAR_PULLEY = 9400232;

    /** Server-only record: gold eaten (God of Battle), the color collector, keys put in the door, rewards given. */
    public static final int AFTER_QR = 89999989;
    /** Official records the quests check. */
    public static final int WORLD_QR = 63090; // DnN (1 = night), raining, lightning
    public static final int DOOR_QR = 63099;  // door = keys in the Huge Door

    // monsters
    public static final int FOOD_THIEF = 9402045, TREASURE_THIEF = 9402046, RAMPIKE = 9402047, RAVEN = 9402048;
    public static final int HORRID_DEATHBEAST = 9402049, PAPERCRAFT_MENACE = 9402050, PAPERCRAFT_HORROR = 9402051;
    public static final int MUSHROOM_MARAUDER = 9402052, BATTLE_BALROG = 9402053, RIBBON_PIG = 9402054;
    public static final int FASHIONABLE_BALROG = 9402055, SUSPICIOUS_BUSH = 9402075;

    // items
    public static final int BALROG_KEY = 4034992, WARRIOR_TOKEN = 4034993, FOOD_BASKET = 4034994;
    public static final int CHILD_TOY = 4034996, FROZEN_TREAT = 4034997, OLIVE_TREE = 4034998, BALROG_ARMOR = 4036014;
    public static final int TEDDY_BEAR = 4036015, LOST_STORYBOOK = 4036016, UNFINISHED_STONE = 4036047;
    public static final int MAGIC_EYEPATCH = 4036049, COLOR_COLLECTOR = 4036012, FILLED_COLLECTOR = 4036013;
    public static final int GOLD_PIECE = 2436086, TINA_STORYBOOK = 2436113, WEIRD_GIFT_BOX = 2436114;
    /** Belly Full of Gold, one per Delicious Gold Piece eaten in a row (1-3) */
    public static final int BELLY_FULL_OF_GOLD_1 = 2003604;
    /** The twelve keys of the Huge Door (4036000-4036011), in the order of the locks. */
    public static final int FIRST_KEY = 4036000, KEY_COUNT = 12;
    public static final int[] KEYS = java.util.stream.IntStream.range(FIRST_KEY, FIRST_KEY + KEY_COUNT).toArray();
    /** the Henesys prologue quests (dated Oct 2016) and the Afterlands map block */
    private static final int PROLOGUE_FIRST_QUEST = 63112, PROLOGUE_LAST_QUEST = 63118;
    private static final int AFTERLANDS_FIRST_FIELD = 867113100, AFTERLANDS_END_FIELD = 867114000;
    public static final int[] FIGURINES = {1202237, 1202238, 1202239, 1202240}; // Beodog, Mansa, Adler, Billy

    /** Kills of Afterlands monsters that fill Billy's Color Collector. */
    public static final int COLOR_GOAL = 100;

    private AfterlandsEvent() {
    }

    /** The Henesys prologue's quests (dated Oct 2016) run again while Halloween is on. */
    public static boolean overridesQuestDates(int questId) {
        return questId >= PROLOGUE_FIRST_QUEST && questId <= PROLOGUE_LAST_QUEST && SeasonalEvents.isActive(SeasonalEvents.HALLOWEEN);
    }

    public static boolean isAfterlands(int fieldId) {
        return fieldId >= AFTERLANDS_FIRST_FIELD && fieldId < AFTERLANDS_END_FIELD;
    }

    // ------------------------------------------------------------------------------------- day, night, weather

    private static String world(Char chr, String key) {
        String v = chr.getScriptManager().getQRValue(WORLD_QR, key);
        return v == null ? "" : v;
    }

    public static boolean isNight(Char chr) {
        return "1".equals(world(chr, "DnN"));
    }

    /** The Solar Pulley: turns day into night or night into day for this player. @return whether it's night now */
    public static boolean turnPulley(Char chr) {
        boolean night = !isNight(chr);
        chr.getScriptManager().setQRValue(WORLD_QR, "DnN", night ? "1" : "0");
        return night;
    }

    public static boolean isRaining(Char chr) {
        return "1".equals(world(chr, "raining"));
    }

    public static boolean isLightning(Char chr) {
        return "1".equals(world(chr, "lightning"));
    }

    /** The children of the Land of Innocence change the weather: "rain", "lightning" or "clear". */
    public static void setWeather(Char chr, String weather) {
        chr.getScriptManager().setQRValue(WORLD_QR, "raining", "rain".equals(weather) ? "1" : "0");
        chr.getScriptManager().setQRValue(WORLD_QR, "lightning", "lightning".equals(weather) ? "1" : "0");
    }

    /**
     * Records the client looks at to show the Afterlands' NPCs (Npc.wz conditions): the Snack Hoard (63252), the
     * Ludi-Block Fort (63251), the Broken Tree (63253), the warriors' day posts (63254), Tina and Riley (63096), the
     * bench (63092). Set when a player first arrives.
     */
    public static void showNpcs(Char chr) {
        for (int qr : new int[]{63251, 63252, 63253, 63254, 63096, 63092}) {
            String v = chr.getScriptManager().getQRValue(qr);
            if (v == null || v.isEmpty()) {
                chr.getScriptManager().setQRValue(qr, "1", false);
            }
        }
    }

    // ---------------------------------------------------------------------------------------------- the door

    /** @return how many keys are in the Huge Door */
    public static int keysInDoor(Char chr) {
        int n = 0;
        for (int i = 0; i < KEYS.length; i++) {
            if (record(chr).getOrDefault("lock" + i, "").equals("1")) {
                n++;
            }
        }
        return n;
    }

    /** Puts every key the player carries into its lock. @return how many were put in now */
    public static int insertKeys(Char chr) {
        Map<String, String> rec = record(chr);
        int added = 0;
        for (int i = 0; i < KEYS.length; i++) {
            if (!"1".equals(rec.get("lock" + i)) && chr.hasItem(KEYS[i])) {
                chr.consumeItem(KEYS[i], 1);
                rec.put("lock" + i, "1");
                added++;
            }
        }
        if (added > 0) {
            save(chr, rec);
            chr.getScriptManager().setQRValue(DOOR_QR, "door", String.valueOf(keysInDoor(chr)));
        }
        return added;
    }

    /** @return the names of the keys not in the door yet */
    public static List<String> missingKeys(Char chr) {
        List<String> res = new ArrayList<>();
        Map<String, String> rec = record(chr);
        for (int i = 0; i < KEYS.length; i++) {
            if (!"1".equals(rec.get("lock" + i))) {
                String n = net.swordie.ms.loaders.StringData.getItemStringById(KEYS[i]);
                res.add(n == null ? String.valueOf(KEYS[i]) : n);
            }
        }
        return res;
    }

    /** @return whether the player has (or had) the key: carried, or already in the door */
    public static boolean hasKey(Char chr, int key) {
        for (int i = 0; i < KEYS.length; i++) {
            if (KEYS[i] == key) {
                return chr.hasItem(key) || "1".equals(record(chr).get("lock" + i));
            }
        }
        return chr.hasItem(key);
    }

    /** Gives a door key (once). @return whether it was given now */
    public static boolean giveKey(Char chr, int key) {
        if (hasKey(chr, key) || !chr.canHold(key, 1)) {
            if (!hasKey(chr, key)) {
                chr.chatMessage(ChatType.Notice2, "Make room in your Etc inventory for the key.");
            }
            return false;
        }
        chr.addItemToInventory(key, 1);
        int have = 0;
        for (int k : KEYS) {
            if (hasKey(chr, k)) {
                have++;
            }
        }
        chr.chatMessage(ChatType.Notice2, String.format("[Afterlands] Keys for the Huge Door: %d/12.", have));
        SeasonalEvents.giveCoins(chr, 2, "an Afterlands key");
        return true;
    }

    // --------------------------------------------------------------------------------------------- gold, color

    /**
     * Delicious Gold Piece (item script): the more you eat in a minute, the bigger you get (Belly Full of Gold:
     * double, triple, quadruple size). @return how many in a row (1-3)
     */
    public static int eatGold(Char chr) {
        Map<String, String> rec = record(chr);
        long now = System.currentTimeMillis();
        long last = parseLong(rec.get("goldAt"));
        int n = now - last < 60_000 ? Math.min(3, (int) parseLong(rec.get("gold")) + 1) : 1;
        rec.put("gold", String.valueOf(n));
        rec.put("goldAt", String.valueOf(now));
        save(chr, rec);
        chr.getScriptManager().useItem(BELLY_FULL_OF_GOLD_1 + n - 1);
        return n;
    }

    /** @return whether the player is quadruple size (three Delicious Gold Pieces in a minute) */
    public static boolean isEnormous(Char chr) {
        Map<String, String> rec = record(chr);
        return parseLong(rec.get("gold")) >= 3 && System.currentTimeMillis() - parseLong(rec.get("goldAt")) < 60_000;
    }

    public static int colorFill(Char chr) {
        return (int) parseLong(record(chr).get("color"));
    }

    public static void resetColor(Char chr) {
        Map<String, String> rec = record(chr);
        rec.put("color", "0");
        save(chr, rec);
    }

    private static long parseLong(String s) {
        try {
            return s == null || s.isEmpty() ? 0 : Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ------------------------------------------------------------------------------------------ monster finds

    /** A quest item from Afterlands monsters, while a quest wants it: {item, need, chance/10000, mobs..., quests...}. */
    private record Find(int item, int need, int chance, int[] mobs, int[] quests) {
    }

    private static final List<Find> FINDS = List.of(
            new Find(FOOD_BASKET, 30, 6000, new int[]{FOOD_THIEF}, new int[]{63038, 63039, 63043, 63066, 63121}),
            new Find(WARRIOR_TOKEN, 30, 6000, new int[]{MUSHROOM_MARAUDER, BATTLE_BALROG}, new int[]{63036}),
            new Find(BALROG_ARMOR, 30, 6000, new int[]{BATTLE_BALROG}, new int[]{63064}),
            new Find(FROZEN_TREAT, 30, 6000, new int[]{PAPERCRAFT_HORROR}, new int[]{63051, 63057}),
            new Find(CHILD_TOY, 30, 6000, new int[]{PAPERCRAFT_MENACE}, new int[]{63052, 63058}),
            new Find(OLIVE_TREE, 3, 1500, new int[]{RAMPIKE}, new int[]{63078}),
            new Find(LOST_STORYBOOK, 1, 500, new int[]{PAPERCRAFT_HORROR, PAPERCRAFT_MENACE}, new int[]{63081}),
            new Find(BALROG_KEY, 1, 10000, new int[]{FASHIONABLE_BALROG}, new int[]{63031})
    );

    private static boolean has(int[] a, int v) {
        for (int x : a) {
            if (x == v) {
                return true;
            }
        }
        return false;
    }

    /** Mob death (SeasonalEvents#onMobKilled). */
    static void onMobKilled(Mob mob) {
        if (!isAfterlands(mob.getField().getId()) && mob.getTemplateId() != RIBBON_PIG) {
            return;
        }
        for (Char chr : mob.getDamageDone().keySet()) {
            if (chr == null || chr.getField() != mob.getField()) {
                continue;
            }
            try {
                QuestManager qm = chr.getQuestManager();
                // the Food Basket quests take 30, 20 or 10 baskets: the most any of them wants
                for (Find f : FINDS) {
                    if (!has(f.mobs(), mob.getTemplateId())) {
                        continue;
                    }
                    boolean wanted = false;
                    for (int q : f.quests()) {
                        if (qm.hasQuestInProgress(q)) {
                            wanted = true;
                            break;
                        }
                    }
                    if (wanted && !chr.hasItemCount(f.item(), f.need()) && Util.succeedProp(f.chance(), 10000)) {
                        SeasonalEvents.grantFind(chr, f.item(), 1);
                    }
                }
                if (mob.getTemplateId() == HORRID_DEATHBEAST && qm.hasQuestInProgress(63075)) {
                    giveKey(chr, KEYS[4]); // Raven Black Key: the deathbeast swallowed it
                }
                if (chr.hasItem(COLOR_COLLECTOR) && isAfterlands(mob.getField().getId())) {
                    Map<String, String> rec = record(chr);
                    int c = (int) parseLong(rec.get("color")) + 1;
                    rec.put("color", String.valueOf(c));
                    save(chr, rec);
                    if (c >= COLOR_GOAL && chr.canHold(FILLED_COLLECTOR, 1)) {
                        chr.consumeItem(COLOR_COLLECTOR, 1);
                        chr.addItemToInventory(FILLED_COLLECTOR, 1);
                        rec.put("color", "0");
                        save(chr, rec);
                        chr.chatMessage(ChatType.Notice2, "[Afterlands] The Color Collector is full! Take it to "
                                + "Billy.");
                    } else if (c % 25 == 0) {
                        chr.chatMessage(ChatType.Notice2, String.format("[Afterlands] Color Collector: %d%%",
                                c * 100 / COLOR_GOAL));
                    }
                }
            } catch (Exception e) {
                log.error("Afterlands: kill of " + mob.getTemplateId() + " for " + chr.getName(), e);
            }
        }
    }

    // -------------------------------------------------------------------------------------------- the story

    /**
     * Completes an Afterlands quest (started first if needed) with a little EXP: Quest.wz has no rewards for them
     * (the 2016 scripts gave the keys and the figurines).
     *
     * @return whether it got completed now
     */
    public static boolean finish(Char chr, int quest) {
        QuestManager qm = chr.getQuestManager();
        if (qm.hasQuestCompleted(quest) && !net.swordie.ms.loaders.QuestData.getQuestInfoById(quest).isRepeatable()) {
            return false;
        }
        if (!qm.hasQuestInProgress(quest)) {
            chr.getScriptManager().startQuestNoCheck(quest);
        }
        if (!qm.completeQuest(quest)) {
            return false;
        }
        try {
            chr.addExp(SeasonalEvents.expPercent(chr, quest == 63086 ? 10 : 2));
        } catch (Exception e) {
            log.error("Afterlands: EXP for quest " + quest, e);
        }
        return true;
    }

    /** Opens the Weird Gift Box (item script): the four figurines of the Afterlands. @return whether it opened */
    public static boolean openGiftBox(Char chr) {
        if (!chr.hasItem(WEIRD_GIFT_BOX)) {
            return false;
        }
        int free = chr.getInventoryByType(net.swordie.ms.enums.InvType.EQUIP).getEmptySlots();
        if (free < FIGURINES.length) {
            chr.chatMessage(ChatType.Notice2, "Make room for 4 items in your Equip inventory first.");
            return false;
        }
        chr.consumeItem(WEIRD_GIFT_BOX, 1);
        for (int f : FIGURINES) {
            SeasonalEvents.giveItem(chr, f, 1);
        }
        SeasonalEvents.giveCoins(chr, 20, "the Afterlands");
        return true;
    }

    // ------------------------------------------------------------------------------------------- the record

    public static Map<String, String> record(Char chr) {
        Map<String, String> m = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(AFTER_QR);
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

    public static void save(Char chr, Map<String, String> m) {
        StringBuilder sb = new StringBuilder();
        m.forEach((k, v) -> sb.append(sb.length() == 0 ? "" : ";").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(AFTER_QR, sb.toString());
    }

    public static String get(Char chr, String key) {
        return record(chr).getOrDefault(key, "");
    }

    public static void put(Char chr, String key, String value) {
        Map<String, String> m = record(chr);
        m.put(key, value);
        save(chr, m);
    }
}
