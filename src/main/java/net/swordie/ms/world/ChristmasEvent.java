package net.swordie.ms.world;

import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

/**
 * Christmas in Happyville, with the "Missing Santa" story (GMS, Dec 10 2014 - Jan 7 2015), brought back as a
 * yearly event (see {@link SeasonalEvents}: Dec 1 - Jan 7, holiday Dec 25). Lv. 25+.
 * <p>
 * Rooney and the Paperboy stand in the major towns while it's on. The Paperboy has the Maple Herald's news (Santa
 * isn't coming this year!); Rooney takes players to Happyville and runs the story's start and end. Santa Claus left
 * because the children's socks were too poor to hold his presents, so the player finds the six volumes of the
 * Santa Encyclopedia (No. 1-5 from monsters near their level, No. 6 from the Christmas Tree), helps Mrs. Claus knit a
 * proper Happyville sock (by hunting: the yarn comes from monsters), hangs it on the Christmas Tree and earns four
 * Mapler Recommendations by helping Happyville's people: Cliff and his reindeer Torr, Maple Claws and Rina,
 * Roodolph and Icarus, and Bicho's snowman. The 2014 dialogue comes from Quest.wz (Say.img); the parts the 2014
 * scripts did (sock knitting, Bicho's snowman, the tree's present) are rewritten.
 * <p>
 * Rewards: the Quest.wz rewards, some EXP (a percent of the level's EXP), Swordie Coins for the side stories, the
 * tree's present (2014 prize list) and Gift Filled Christmas Socks (a random Christmas prize when opened) at the end.
 * Every day of the event: a sock from Santa in Santa's House (once your sock hangs on the tree) and one from the
 * Snow Blower in the Extra Frosty Snow Zone (Roodolph takes you there). On Christmas Day, Hot Time lasts all day and
 * its gift has 3 more socks.
 */
public final class ChristmasEvent {

    private static final Logger log = LogManager.getLogger(ChristmasEvent.class);

    public static final int MIN_LEVEL = 25;
    public static final int ROONEY = 1022101;
    public static final int PAPERBOY = 9250052;
    public static final int HAPPYVILLE = 209000000;
    public static final int SANTAS_HOUSE = 209080100;
    public static final int SNOW_ZONE = 209080000;
    public static final int SNOW_BLOWER = 9400714;

    // the story
    public static final int NEWS = 4477;          // [Missing Santa] News Special Report (Paperboy -> Rooney)
    public static final int ENCYCLOPEDIA = 4478;  // Disappeared Santa Encyclopedia (Rooney: all 6 volumes)
    public static final int MRS_CLAUS = 4479;     // Mrs. Claus Finding
    public static final int QUIZ = 4480;          // Common Sense Of Santa
    public static final int RECIPE = 4481;        // Socks Creation Recipes
    public static final int KNITTING = 4482;      // Socks Actual Creation Action
    public static final int HANGING = 4483;       // Socks Hanging (Christmas Tree: 4 recommendations)
    public static final int CLIFF = 4484;         // Cliff's Request (a picture of Torr)
    public static final int TORR = 4485;          // Torr's Whereabouts
    public static final int SANTA_CAT = 4486;     // Santa Cat's request (Maple Claws -> Rina)
    public static final int CHIMNEY = 4487;       // Delivery of Rina cleaning device (Rina -> Maple Claws)
    public static final int MILK = 4488;          // Warm Milk Delivery (Roodolph -> Icarus)
    public static final int ICARUS = 4489;        // To sleep Icarus (Icarus -> Roodolph)
    public static final int SNOWMAN = 4490;       // Bicho's snowman

    // items
    public static final int VOL_1 = 4161043, VOL_2 = 4161038, VOL_3 = 4161039;
    public static final int VOL_4 = 4161040, VOL_5 = 4161041, VOL_6 = 4161042;
    /** The volumes monsters have, in the order the story needs them (No. 6 comes from the Christmas Tree). */
    public static final int[] DROPPED_VOLUMES = {VOL_2, VOL_1, VOL_3, VOL_4, VOL_5};
    public static final int[] ALL_VOLUMES = {VOL_1, VOL_2, VOL_3, VOL_4, VOL_5, VOL_6};
    public static final int PHOTO = 4031881;          // Picture of Rudolph
    public static final int CHIMNEY_BROOM = 4031882;
    public static final int FLASHLIGHT = 4031883;
    public static final int WARM_MILK = 4031884;
    public static final int RECOMMENDATION = 4031885; // Mapler Recommendation
    public static final int COMPLETED_SOCKS = 4031886;
    public static final int SOCKS_FRAME = 4220022;
    public static final int CHRISTMAS_SOCKS = 2430607; // Gift Filled Christmas Socks (opens into a prize)

    /** Knitting progress (quest 4482's record) needed: Quest.wz's 3000. Monsters near your level give the yarn. */
    public static final int KNIT_GOAL = 3000;
    private static final int KNIT_PER_KILL = 60;
    /** Server-only record: town to go back to from Happyville, Bicho's snowman parts, the daily socks, reminders. */
    public static final int CHRISTMAS_QR = 89999987;

    private ChristmasEvent() {
    }

    public static boolean isActive() {
        return SeasonalEvents.isActive(SeasonalEvents.CHRISTMAS);
    }

    public static boolean isEventQuest(int questId) {
        return questId >= NEWS && questId <= SNOWMAN;
    }

    // ------------------------------------------------------------------------------------------------- the story

    private record Step(int quest, int map, String what) {
    }

    private static final String HV = "in #bHappyville#k (Rooney can take you there)";
    private static final List<Step> STEPS = List.of(
            new Step(NEWS, FieldConstants.HENESYS_FIELD_ID, "Talk to #bRooney#k in any major town."),
            new Step(MRS_CLAUS, HAPPYVILLE, "Talk to #bMrs. Claus#k " + HV + ". She'll want #bSanta Encyclopedia "
                    + "No.2#k first."),
            new Step(QUIZ, HAPPYVILLE, "Take #bMrs. Claus#k's quiz " + HV + ". Bring #bSanta Encyclopedia No.1#k."),
            new Step(RECIPE, HAPPYVILLE, "Bring #bSanta Encyclopedia No.3#k (the sock recipe) to #bMrs. Claus#k " + HV
                    + "."),
            new Step(KNITTING, HAPPYVILLE, "Knit the sock: hunt monsters near your level for #bColorful Yarn#k "
                    + "until the #bSocks Frame#k is full, then go back to #bMrs. Claus#k " + HV + "."),
            new Step(HANGING, HAPPYVILLE, "Hang your #bCompleted Socks#k on the #bChristmas Tree#k " + HV + " "
                    + "(bring #bSanta Encyclopedia No.4#k), then help Happyville's people for #b4 Mapler "
                    + "Recommendations#k."),
            new Step(ENCYCLOPEDIA, FieldConstants.HENESYS_FIELD_ID, "Bring all six #bSanta Encyclopedia#k volumes to #bRooney#k "
                    + "(No.1-5 from monsters near your level, No.6 from the Christmas Tree).")
    );

    private static final Map<Integer, String> SIDE = new LinkedHashMap<>();

    static {
        SIDE.put(CLIFF, "#bCliff#k (Happyville, upstairs) wants a picture of Torr: use the #btripod#k on the "
                + "town's highest roof.");
        SIDE.put(TORR, "Find #bTorr#k crying in the corner of Happyville and tell him Cliff is waiting.");
        SIDE.put(SANTA_CAT, "Get the cleaning tools from #bRina#k (Henesys, Rina's house) for #bMaple Claws#k.");
        SIDE.put(CHIMNEY, "Bring the #bChimney Broom#k and #bFlashlight#k to #bMaple Claws#k in Happyville.");
        SIDE.put(MILK, "Buy a #bWarm Milk#k at the #bKerning City Pharmacy#k and give it to #bIcarus#k in "
                + "Kerning City.");
        SIDE.put(ICARUS, "Tell #bRoodolph#k in Happyville that Icarus is asleep.");
        SIDE.put(SNOWMAN, "Ask the three #bsnowmen#k of Happyville (Branch, Metal Bucket, Straw Hat) how a snowman "
                + "should look, then go back to #bBicho#k.");
    }

    /** What Rooney's "Where am I in the story?" says, and the map he can take the player to (0: none). */
    public record Guide(String text, int map, boolean done) {
    }

    public static Guide guide(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (qm.hasQuestCompleted(ENCYCLOPEDIA)) {
            return new Guide("You found Santa Claus' faith again: the whole story is done. Merry Christmas!", 0, true);
        }
        if (!qm.hasQuestInProgress(NEWS) && !qm.hasQuestCompleted(NEWS)) {
            return new Guide("The #bPaperboy#k in the major towns has the morning's big news.", 0, false);
        }
        StringBuilder sb = new StringBuilder();
        int map = 0;
        for (Step s : STEPS) {
            if (s.quest() == ENCYCLOPEDIA) {
                continue;
            }
            if (!qm.hasQuestCompleted(s.quest())) {
                sb.append("#b").append(net.swordie.ms.loaders.StringData.getQuestStringById(s.quest()))
                        .append("#k\r\n").append(s.what());
                map = s.map();
                break;
            }
        }
        if (qm.hasQuestInProgress(HANGING)) {
            for (Map.Entry<Integer, String> e : SIDE.entrySet()) {
                int q = e.getKey();
                boolean open = qm.hasQuestInProgress(q)
                        || (!qm.hasQuestCompleted(q) && (q == CLIFF || q == SANTA_CAT || q == MILK || q == SNOWMAN));
                if (open) {
                    sb.append("\r\n- ").append(e.getValue());
                }
            }
            sb.append(String.format("\r\n#bMapler Recommendations:#k %d/4", count(chr, RECOMMENDATION)));
        }
        if (qm.hasQuestInProgress(ENCYCLOPEDIA)) {
            int have = 0;
            for (int v : ALL_VOLUMES) {
                if (chr.hasItem(v)) {
                    have++;
                }
            }
            sb.append(sb.length() == 0 ? "" : "\r\n\r\n").append(String.format("#bSanta Encyclopedia:#k %d/6 "
                    + "volumes. ", have)).append(have == 6 ? "Bring them to #bRooney#k!" : "No.1-5 come from "
                    + "monsters near your level; No.6 from the Christmas Tree.");
        }
        return new Guide(sb.toString(), map, false);
    }

    /** A record of how far the knitting is (quest 4482's record, 0-3000). */
    public static int knitProgress(Char chr) {
        try {
            String v = chr.getScriptManager().getQRValue(KNITTING);
            return v == null || v.isBlank() ? 0 : Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void setKnitProgress(Char chr, int value) {
        chr.getScriptManager().setQRValue(chr, KNITTING, String.valueOf(value), false);
    }

    /** Mrs. Claus starts the knitting: the Socks Frame, and the record at 0. */
    public static boolean startKnitting(Char chr) {
        if (!chr.canHold(SOCKS_FRAME, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Etc inventory first.");
            return false;
        }
        chr.getScriptManager().startQuestNoCheck(KNITTING);
        if (!chr.hasItem(SOCKS_FRAME)) {
            chr.addItemToInventory(SOCKS_FRAME, 1);
        }
        setKnitProgress(chr, 0);
        return true;
    }

    /**
     * Completes a Missing Santa quest with its Quest.wz rewards and the event's.
     *
     * @return whether the quest got completed (false if it was done already or there's no room)
     */
    public static boolean finish(Char chr, int quest) {
        QuestManager qm = chr.getQuestManager();
        if (qm.hasQuestCompleted(quest)) {
            return false;
        }
        if (quest == KNITTING && !chr.canHold(COMPLETED_SOCKS, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Etc inventory first.");
            return false;
        }
        if (quest == HANGING && !roomForPrize(chr)) {
            return false;
        }
        if (quest == ENCYCLOPEDIA && !chr.canHold(CHRISTMAS_SOCKS, 3)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Use inventory first.");
            return false;
        }
        if (!qm.hasQuestInProgress(quest)) {
            chr.getScriptManager().startQuestNoCheck(quest);
        }
        if (!qm.completeQuest(quest)) {
            return false;
        }
        try {
            double pct = switch (quest) {
                case ENCYCLOPEDIA -> 10;
                case KNITTING, HANGING -> 5;
                default -> 2;
            };
            chr.addExp(SeasonalEvents.expPercent(chr, pct));
            switch (quest) {
                case KNITTING -> {
                    if (chr.hasItem(SOCKS_FRAME)) {
                        chr.consumeItem(SOCKS_FRAME, count(chr, SOCKS_FRAME));
                    }
                    chr.addItemToInventory(COMPLETED_SOCKS, 1);
                }
                case HANGING -> {
                    chr.consumeItem(RECOMMENDATION, Math.min(4, count(chr, RECOMMENDATION)));
                    if (!chr.hasItem(VOL_6)) {
                        chr.addItemToInventory(VOL_6, 1);
                    }
                    chr.addMoney(10_000);
                    Prize p = roll();
                    SeasonalEvents.giveItem(chr, p.item(), p.count());
                    chr.chatMessage(ChatType.Notice2, String.format("[Christmas] There was something in your sock: "
                            + "%s%s!", itemName(p.item()), p.count() > 1 ? " x" + p.count() : ""));
                    SeasonalEvents.giveCoins(chr, 10, "the Christmas Tree's present");
                }
                case ENCYCLOPEDIA -> {
                    chr.addItemToInventory(CHRISTMAS_SOCKS, 3);
                    SeasonalEvents.giveCoins(chr, 15, "Santa Claus is coming to town");
                    chr.chatMessage(ChatType.Notice2, "[Christmas] You got 3 Gift Filled Christmas Socks.");
                }
                case TORR, CHIMNEY, ICARUS, SNOWMAN -> SeasonalEvents.giveCoins(chr, 3, "a good deed in Happyville");
                default -> {
                }
            }
        } catch (Exception e) {
            log.error("Christmas: rewards of quest " + quest + " for " + chr.getName(), e);
        }
        return true;
    }

    /** @return how many of an item the character has */
    public static int count(Char chr, int itemId) {
        return chr.getScriptManager().getQuantityOfItem(itemId);
    }

    private static String itemName(int itemId) {
        String n = net.swordie.ms.loaders.StringData.getItemStringById(itemId);
        return n == null ? String.valueOf(itemId) : n;
    }

    // ------------------------------------------------------------------------------------------ prizes and socks

    private record Prize(int item, int count, int weight) {
    }

    /** The Christmas Tree's present (christmas-prizes.tsv) */
    private static final Supplier<List<Prize>> PRIZES = DataTable.cached("christmas-prizes", t -> {
        List<Prize> list = new ArrayList<>();
        for (DataTable.Row r : t.rows()) {
            list.add(new Prize(r.i("item"), r.i("count"), r.i("weight")));
        }
        return List.copyOf(list);
    });

    private static Prize roll() {
        List<Prize> prizes = PRIZES.get();
        int total = prizes.stream().mapToInt(Prize::weight).sum();
        int r = Util.getRandom(total - 1);
        for (Prize p : prizes) {
            r -= p.weight();
            if (r < 0) {
                return p;
            }
        }
        return prizes.get(0);
    }

    private static boolean roomForPrize(Char chr) {
        for (Prize p : PRIZES.get()) {
            if (!chr.canHold(p.item(), p.count())) {
                chr.chatMessage(ChatType.Notice2, "Make sure your Equip, Use and Set-up inventories have a free slot "
                        + "first.");
                return false;
            }
        }
        return true;
    }

    /**
     * Opens a Gift Filled Christmas Socks (item script): a prize from the Christmas list and 2 Swordie Coins.
     *
     * @return what was inside, for the chat; null if there was no room
     */
    public static String openSocks(Char chr) {
        if (!chr.hasItem(CHRISTMAS_SOCKS) || !roomForPrize(chr)) {
            return null;
        }
        Prize p = roll();
        chr.consumeItem(CHRISTMAS_SOCKS, 1);
        SeasonalEvents.giveItem(chr, p.item(), p.count());
        SeasonalEvents.giveCoins(chr, 2, "a Christmas sock");
        return itemName(p.item()) + (p.count() > 1 ? " x" + p.count() : "");
    }

    private static String today() {
        return net.swordie.ms.util.ResetClock.today().toString();
    }

    /**
     * Santa in Santa's House fills your sock once a day, once it hangs on the Christmas Tree (quest 4483 started).
     *
     * @return 1 given, 0 already today, -1 sock not hung yet, -2 no room
     */
    public static int santaPresent(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (!qm.hasQuestInProgress(HANGING) && !qm.hasQuestCompleted(HANGING)) {
            return -1;
        }
        Map<String, String> rec = record(chr);
        if (today().equals(rec.get("santa"))) {
            return 0;
        }
        if (!chr.canHold(CHRISTMAS_SOCKS, 1)) {
            return -2;
        }
        rec.put("santa", today());
        save(chr, rec);
        chr.addItemToInventory(CHRISTMAS_SOCKS, 1);
        return 1;
    }

    // ------------------------------------------------------------------------------------------ Bicho's snowman

    /** The parts of a snowman the three snowmen of Happyville tell you about (bits 1, 2, 4). */
    public static int snowmanParts(Char chr) {
        try {
            return Integer.parseInt(record(chr).getOrDefault("snow", "0"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * A snowman told the player its secret (part 1: Branch, 2: Metal Bucket, 3: Straw Hat).
     *
     * @return whether that was new
     */
    public static boolean learnSnowmanPart(Char chr, int part) {
        if (!chr.getQuestManager().hasQuestInProgress(SNOWMAN) || part < 1 || part > 3) {
            return false;
        }
        int bits = snowmanParts(chr);
        int bit = 1 << (part - 1);
        if ((bits & bit) != 0) {
            return false;
        }
        bits |= bit;
        Map<String, String> rec = record(chr);
        rec.put("snow", String.valueOf(bits));
        save(chr, rec);
        if (bits == 7) {
            chr.getScriptManager().setQRValue(chr, SNOWMAN, "Done", false);
            chr.chatMessage(ChatType.Notice2, "[Christmas] You know everything about snowmen now. Go back to Bicho!");
        }
        return true;
    }

    // ---------------------------------------------------------------------------------------------- the world

    /** Login during Christmas (Lv. 25+): a reminder once a day. */
    public static void onLogin(Char chr) {
        if (chr.getLevel() < MIN_LEVEL) {
            return;
        }
        Map<String, String> rec = record(chr);
        if (today().equals(rec.get("notice"))) {
            return;
        }
        rec.put("notice", today());
        save(chr, rec);
        QuestManager qm = chr.getQuestManager();
        String until = SeasonalEvents.windowText(SeasonalEvents.CHRISTMAS).replaceAll(".* - ", "");
        String msg = qm.hasQuestCompleted(ENCYCLOPEDIA)
                ? "[Christmas] Merry Christmas! Santa (Santa's House) and the Snow Blower (Extra Frosty Snow Zone) "
                + "have a sock for you every day until " + until + "."
                : !qm.hasQuestInProgress(NEWS) && !qm.hasQuestCompleted(NEWS)
                ? "[Christmas] Happyville is open (" + SeasonalEvents.windowText(SeasonalEvents.CHRISTMAS) + ")! "
                + "Have you seen today's news? Talk to the Paperboy in any major town."
                : "[Christmas] Happyville is open until " + until + ". Rooney (major towns) can take you there.";
        net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            if (chr.isOnline()) {
                chr.chatMessage(ChatType.Notice2, msg);
            }
        }, 8_000);
    }

    static void onMobKilled(Mob mob) {
        if (mob.getTemplateId() == SNOW_BLOWER) {
            snowBlower(mob);
            return;
        }
        Char killer = mob.getMostDamageChar();
        if (killer == null || killer.getLevel() < MIN_LEVEL || mob.isBoss() || mob.isBossFightAdd()
                || mob.getField().getInfo().isTown() || killer.getField() != mob.getField()
                || Math.abs(mob.getForcedMobStat().getLevel() - killer.getLevel()) > 20) {
            return;
        }
        QuestManager qm = killer.getQuestManager();
        if (qm.hasQuestInProgress(ENCYCLOPEDIA) && Util.succeedProp(GameConstants.CHRISTMAS_VOLUME_CHANCE, 10000)) {
            for (int v : DROPPED_VOLUMES) {
                if (!killer.hasItem(v) && !usedUp(qm, v)) {
                    SeasonalEvents.grantFind(killer, v, 1);
                    break;
                }
            }
        }
        if (qm.hasQuestInProgress(KNITTING) && killer.hasItem(SOCKS_FRAME)) {
            int before = knitProgress(killer);
            if (before < KNIT_GOAL) {
                int after = Math.min(KNIT_GOAL, before + KNIT_PER_KILL);
                setKnitProgress(killer, after);
                if (after == KNIT_GOAL) {
                    killer.chatMessage(ChatType.Notice2, "[Christmas] The Socks Frame is full of Colorful Yarn! "
                            + "Take it to Mrs. Claus in Happyville.");
                } else if (after / 600 != before / 600) {
                    killer.chatMessage(ChatType.Notice2, String.format("[Christmas] Colorful Yarn: %d%%",
                            after * 100 / KNIT_GOAL));
                }
            }
        }
    }

    /** A volume the story already took (none do until the end, when Rooney takes all six). */
    private static boolean usedUp(QuestManager qm, int volume) {
        return qm.hasQuestCompleted(ENCYCLOPEDIA);
    }

    /** The Snow Blower of the Extra Frosty Snow Zone: a sock for everyone who hit it, once a day each. */
    private static void snowBlower(Mob mob) {
        for (Char chr : mob.getDamageDone().keySet()) {
            if (chr == null || chr.getField() != mob.getField() || chr.getLevel() < MIN_LEVEL) {
                continue;
            }
            Map<String, String> rec = record(chr);
            if (today().equals(rec.get("blower"))) {
                chr.chatMessage(ChatType.Notice2, "[Christmas] The Snow Blower has nothing more for you today. "
                        + "Come back tomorrow!");
                continue;
            }
            if (!chr.canHold(CHRISTMAS_SOCKS, 1)) {
                chr.chatMessage(ChatType.Notice2, "[Christmas] Make room in your Use inventory for the Snow Blower's "
                        + "sock.");
                continue;
            }
            rec.put("blower", today());
            save(chr, rec);
            SeasonalEvents.grantFind(chr, CHRISTMAS_SOCKS, 1);
            chr.chatMessage(ChatType.Notice2, "[Christmas] Snow! The Snow Blower left you a Gift Filled Christmas "
                    + "Sock. (Once a day.)");
        }
    }

    // ------------------------------------------------------------------------------------------- the record

    public static Map<String, String> record(Char chr) {
        Map<String, String> m = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(CHRISTMAS_QR);
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
        chr.getQuestManager().setHiddenRecord(CHRISTMAS_QR, sb.toString());
    }

    /** Remembers the town the player came from (Rupi takes them back there). */
    public static void rememberTown(Char chr) {
        if (chr.getField() != null && chr.getField().getInfo().isTown() && FieldConstants.regionOf(chr.getFieldID()) != FieldConstants.regionOf(HAPPYVILLE)) {
            Map<String, String> m = record(chr);
            m.put("town", String.valueOf(chr.getFieldID()));
            save(chr, m);
        }
    }

    /** @return the town to go back to from Happyville (Henesys if none) */
    public static int returnTown(Char chr) {
        try {
            return Integer.parseInt(record(chr).getOrDefault("town", String.valueOf(FieldConstants.HENESYS_FIELD_ID)));
        } catch (NumberFormatException e) {
            return FieldConstants.HENESYS_FIELD_ID;
        }
    }
}
