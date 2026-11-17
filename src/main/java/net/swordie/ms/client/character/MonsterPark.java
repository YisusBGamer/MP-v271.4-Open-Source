package net.swordie.ms.client.character;

import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.DailyEntry;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.world.event.SunnySunday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Asura on 27-7-2018.
 */
public class MonsterPark {

    // the tables: resources/data/monster-park-*.tsv (mob-exp, day-boxes, dungeons, box-gifts)
    private static final Supplier<Map<Integer, Integer>> MOB_EXP = DataTable.cached("monster-park-mob-exp", t -> {
        Map<Integer, Integer> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("mob"), r.i("exp"));
        }
        return m;
    });
    /** reward box by day of the week (monday..sunday) */
    private static final Supplier<Map<String, Integer>> DAY_BOXES = DataTable.cached("monster-park-day-boxes", t -> {
        Map<String, Integer> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.str("day").trim().toLowerCase(Locale.ROOT), r.i("box"));
        }
        return m;
    });

    private static Map<Integer, Integer> mobExp() {
        return MOB_EXP.get();
    }

    public static int getExpByMobId(int templateId) {
        return mobExp().getOrDefault(templateId, 0);
    }

    private static final int SUNDAY_BOX = 2434745; // Growth Box, when the table has no row for the day

    public static int getRewardByDay() {
        String day = net.swordie.ms.util.ResetClock.today().getDayOfWeek().name().toLowerCase(Locale.ROOT);
        return DAY_BOXES.get().getOrDefault(day, SUNDAY_BOX);    }



    /** Character's daily record, as GMS keeps it: count=clears today;date=yy/MM/dd. */
    public static final int ENTRY_QUEST = 18805;
    /** GMS daily limits: 7 clears per character; per world 2 free, 14 in all (the rest take an entry ticket each). */
    public static final int CHAR_CLEARS = 7;
    public static final int WORLD_FREE_CLEARS = 2;
    public static final int WORLD_CLEARS = 14;
    public static final int FIRST_CLEAR_COINS = 20;
    public static final int COIN = 4310020;
    public static final int HUB = 951000000;
    public static final int FREE_TICKET = 4001949; // Monster Park ticket items the window names
    public static final int CASH_TICKET = 5252030;
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yy/MM/dd");

    /** One dungeon. gate 1 = hub portal in00 (Lv. 105-199), 2 = in01 (Arcane River), 3 = in02 (Grandis); mapMark = the window icon. */
    public record Dungeon(int gate, String name, int minLevel, int maxLevel, int quest, int mapId, String mapMark) {
        public String label() {
            return String.format("%s (Lv. %d-%d)", name, minLevel, maxLevel);
        }
    }

    private static final Supplier<List<Dungeon>> DUNGEONS = DataTable.cached("monster-park-dungeons", t ->
            t.rows().stream().map(r -> new Dungeon(r.i("gate"), r.str("name"), r.i("minLevel"), r.i("maxLevel"),
                    r.i("quest"), r.i("map"), r.str("mark"))).toList());

    private static List<Dungeon> dungeons() {
        return DUNGEONS.get();
    }

    public static List<Dungeon> getDungeons(int gate) {
        List<Dungeon> res = new ArrayList<>();
        for (Dungeon d : dungeons()) {
            if (d.gate() == gate) {
                res.add(d);
            }
        }
        return res;
    }

    public static Dungeon getDungeon(int gate, int index) {
        List<Dungeon> list = getDungeons(gate);
        return index >= 0 && index < list.size() ? list.get(index) : null;
    }

    /** The dungeon whose stages contain this map (954101300 -> Morass), or null. */
    public static Dungeon getDungeonOfMap(int mapId) {
        for (Dungeon d : dungeons()) {
            if (mapId / 1000 == d.mapId() / 1000) {
                return d;
            }
        }
        return null;
    }

    private static String today() {
        return net.swordie.ms.util.ResetClock.today().format(DATE);
    }

    /** Clears this character made today (the record resets with the date). */
    public static int getClearsToday(Char chr) {
        Quest q = chr.getQuestManager().getQuestById(ENTRY_QUEST);
        if (q == null || !today().equals(q.getProperty("date"))) {
            return 0;
        }
        try {
            return Integer.parseInt(q.getProperty("count"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isFirstClearToday(Char chr) {
        return getClearsToday(chr) == 0;
    }

    private static void addClear(Char chr) {
        ScriptManagerImpl sm = chr.getScriptManager();
        int count = getClearsToday(chr) + 1;
        sm.setQRValue(ENTRY_QUEST, "date", today());
        sm.setQRValue(ENTRY_QUEST, "count", String.valueOf(count));
    }

    /** setting monster_park_unlimited: no daily limits or tickets */
    public static boolean unlimited() {
        return net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.MONSTER_PARK_UNLIMITED);
    }

    /** Clears the world (account) has left today, free and ticket ones together. */
    public static int getWorldClearsLeft(Char chr) {
        if (chr.getAccount() == null || unlimited()) {
            return WORLD_CLEARS;
        }
        int left = chr.getAccount().getRemainingEntries(DailyEntry.MonsterPark);
        return Math.max(0, Math.min(WORLD_CLEARS, left));
    }

    /** Free clears the world has left today. */
    public static int getFreeClearsLeft(Char chr) {
        int left = getWorldClearsLeft(chr);
        return Math.max(0, Math.min(left, WORLD_FREE_CLEARS - (WORLD_CLEARS - left)));
    }

    /** Ticket clears still possible today (world and character limits, tickets held). */
    public static int getTicketClearsLeft(Char chr) {
        int room = Math.min(getWorldClearsLeft(chr) - getFreeClearsLeft(chr), CHAR_CLEARS - getClearsToday(chr));
        return Math.max(0, Math.min(room, getTickets(chr)));
    }

    private static int getTickets(Char chr) {
        var etc = chr.getInventoryByType(InvType.ETC);
        var cash = chr.getInventoryByType(InvType.CASH);
        return (etc == null ? 0 : etc.getQuantity(FREE_TICKET)) + (cash == null ? 0 : cash.getQuantity(CASH_TICKET));
    }

    /** Why a clear can't be counted for this character now (daily limits), or null. */
    public static String getClearBlock(Char chr) {
        if (unlimited()) {
            return null;
        }
        if (getClearsToday(chr) >= CHAR_CLEARS) {
            return String.format("This character has cleared Monster Park %d times today. Come back tomorrow!",
                    CHAR_CLEARS);
        }
        if (getWorldClearsLeft(chr) <= 0) {
            return String.format("Your world has cleared Monster Park %d times today. Come back tomorrow!",
                    WORLD_CLEARS);
        }
        if (getFreeClearsLeft(chr) <= 0 && getTickets(chr) <= 0) {
            return String.format("Your world has used today's %d free clears. Each further clear takes a "
                    + "#b#t%d##k or a #b#t%d##k.", WORLD_FREE_CLEARS, FREE_TICKET, CASH_TICKET);
        }
        return null;
    }

    /** Why this character can't enter now, or null when it can. */
    public static String getEntryBlock(Char chr, Dungeon d) {
        if (d == null) {
            return "That dungeon isn't open.";
        }
        if (chr.getParty() != null) {
            return "Monster Park is solo only. Please leave your party first.";
        }
        if (chr.getLevel() < d.minLevel()) {
            return String.format("You need to be at least Level %d to enter #b%s#k.", d.minLevel(), d.name());
        }
        if (!net.swordie.ms.client.character.ContentAccess.questGateOpen(chr, d.quest())) {
            return String.format("Complete #b#y%d##k first to enter #b%s#k.", d.quest(), d.name());
        }
        return getClearBlock(chr);
    }

    /** Enters the first stage. A clear is counted only at the final stage's exit (leaving early costs nothing). */
    public static boolean enter(Char chr, Dungeon d) {
        String block = getEntryBlock(chr, d);
        if (block != null) {
            chr.chatMessage(plain(block));
            return false;
        }
        ScriptManagerImpl sm = chr.getScriptManager();
        sm.warpInstanceIn(d.mapId());
        sm.createQuestWithQRValue(GameConstants.MONSTER_PARK_EXP_QUEST, "0");
        sm.setInstanceTime(GameConstants.MONSTER_PARK_TIME, HUB);
        return true;
    }

    /** EXP for a clear: the day's Sunny Sunday bonus, else the plain Sunday +50% (GMS). */
    public static long getClearExp(long exp) {
        return exp * (100 + SunnySunday.monsterParkExpBonus()) / 100;
    }

    /** A dialog text as a chat line. */
    public static String plain(String text) {
        return text.replaceAll("#y([0-9]+)#", "quest $1").replaceAll("#t([0-9]+)#", "item $1").replaceAll("#[a-z]", "");
    }

    /**
     * Counts a clear at the final exit: the character's count, the world's, and a ticket once the free ones are used.
     *
     * @return why it can't be counted (no reward then), or null when it was
     */
    public static String recordClear(Char chr) {
        String block = getClearBlock(chr);
        if (block != null) {
            return block;
        }
        if (getFreeClearsLeft(chr) <= 0) {
            var etc = chr.getInventoryByType(InvType.ETC);
            chr.getScriptManager().consumeItem(etc != null && etc.getQuantity(FREE_TICKET) > 0 ? FREE_TICKET
                    : CASH_TICKET, 1);
        }
        if (chr.getAccount() != null && !unlimited()) {
            // (a record from before the limit can hold more than the day's clears)
            chr.getAccount().getDailyEntries().setRemainingEntries(DailyEntry.MonsterPark, getWorldClearsLeft(chr));
            chr.getAccount().reduceDailyEntry(DailyEntry.MonsterPark);
        }
        addClear(chr);
        return null;
    }

    /** One day-of-the-week box gift: item (MESOS / HONOR for those), quantity range, expiry in days (0 = none). */
    public record BoxGift(int item, int min, int max, int days) {
    }

    public static final int MESOS = -1;
    public static final int HONOR = -2;
    public static final int BOX_COINS_MIN = 15;
    public static final int BOX_COINS_MAX = 30;

    /** the day boxes' gifts: 15-30 coins plus one of these at random */
    private static final Supplier<Map<Integer, List<BoxGift>>> BOX_GIFTS = DataTable.cached("monster-park-box-gifts", t -> {
        Map<Integer, List<BoxGift>> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            String item = r.str("item").trim();
            int id = item.equals("mesos") ? MESOS : item.equals("honor") ? HONOR : Integer.parseInt(item);
            m.computeIfAbsent(r.i("box"), k -> new ArrayList<>()).add(new BoxGift(id, r.i("min"), r.i("max"), r.i("days")));
        }
        m.replaceAll((k, v) -> List.copyOf(v));
        return m;
    });

    private static Map<Integer, List<BoxGift>> boxGifts() {
        return BOX_GIFTS.get();
    }

    public static List<BoxGift> getBoxGifts(int boxId) {
        return boxGifts().getOrDefault(boxId, List.of());
    }

    /** "#v..# name x a-b" lines for the box's dialog. */
    public static String describeBox(int boxId) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#v%d# #t%d# x %d-%d\r\n\r\n#eAnd one of these at random:#n\r\n", COIN, COIN,
                BOX_COINS_MIN, BOX_COINS_MAX));
        for (BoxGift g : getBoxGifts(boxId)) {
            if (g.item() == MESOS) {
                sb.append("Mesos");
            } else if (g.item() == HONOR) {
                sb.append("Honor EXP");
            } else {
                sb.append(String.format("#v%d# #t%d#", g.item(), g.item()));
            }
            sb.append(g.min() == g.max() ? String.format(" x %,d", g.min()) : String.format(" x %,d-%,d", g.min(), g.max()));
            if (g.days() > 0) {
                sb.append(String.format(" (%d days)", g.days()));
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }

    /** Opens a day box: coins and one random gift. Returns an error to show, or null when it was opened. */
    public static String openBox(Char chr, int boxId) {
        List<BoxGift> gifts = getBoxGifts(boxId);
        if (gifts.isEmpty() || !chr.hasItem(boxId)) {
            return "This box can't be opened.";
        }
        int coins = net.swordie.ms.util.Util.getRandom(BOX_COINS_MIN, BOX_COINS_MAX + 1);
        BoxGift g = gifts.get(net.swordie.ms.util.Util.getRandom(gifts.size() - 1));
        int qty = g.min() >= g.max() ? g.min() : net.swordie.ms.util.Util.getRandom(g.min(), g.max() + 1);
        if (!chr.canHold(COIN, coins) || (g.item() > 0 && !chr.canHold(g.item(), qty))) {
            return "Please make room in your inventory first.";
        }
        ScriptManagerImpl sm = chr.getScriptManager();
        sm.consumeItem(boxId, 1);
        sm.giveItem(COIN, coins);
        if (g.item() == MESOS) {
            chr.addMoney(qty);
        } else if (g.item() == HONOR) {
            chr.addHonorExp(qty);
        } else if (g.days() > 0) {
            sm.giveItemWithExpiry(g.item(), g.days() * 24);
        } else {
            sm.giveItem(g.item(), qty);
        }
        return null;
    }
}
