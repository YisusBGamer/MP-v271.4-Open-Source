package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.world.field.ClockPacket;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Spiegelmann's Adventure Express: Nihal Desert Trade Tycoon (Etc.wz/NihalTrade.img, maps 993000801-993000837).
 * Spiegelmann starts a timed trade run with some Gold Coins; the registered merchants each sell one set of goods and
 * buy others (the goods' descriptions), so buy low in one market and sell high in another. Gold Coins (QR 15324
 * count), the load (QR 15324 cWeight / mWeight) and the goods (NihalTrade.img item QR / QREx) are quest records the
 * client's trade window (TRADE_KING_SHOP_ITEM 472, In 640 / 641, TRADE_KING_SHOP_RES 473) reads. Porters from Hatsar
 * (or the Desert Fox) carry more; Desert Thieves on the desert roads steal part of the load. When time is up the
 * express takes you to the exit, where Spiegelmann turns the profit into EXP.
 * Prices, start coins, run time, porter fees, the thieves and the EXP are this server's numbers (GMS ones unknown).
 */
public class NihalTradeModule {

    public static final int EXPRESS = 993000801;
    public static final int CROSSROADS = 993000802;
    public static final int EXIT = 993000837;
    // the desert roads, where Desert Thieves wait
    private static final int DESERT_ROAD_FIRST = 993000803, DESERT_ROAD_LAST = 993000817;
    public static final int GOLD_QR = 15324;
    public static final int WORKER_QR = 15325;

    private static final int START_GOLD = 50;
    private static final int BASE_WEIGHT = 10;
    private static final int RUN_SECONDS = 20 * 60;
    private static final int MAX_COUNT = 999;
    private static final int[] TIER_PRICE = {8, 14, 22, 35, 55, 90};
    private static final double SELLER_MIN = 0.9, SELLER_MAX = 1.1;
    private static final double BUYER_MIN = 1.3, BUYER_MAX = 1.7;
    private static final int DISCOUNT_GOODS = 4;
    private static final int RARE_FROM = 4034834; // "This good may not always be in stock."
    private static final int THIEF_CHANCE = 15, THIEF_CHANCE_GUARDED = 5;
    private static final int THIEF_TAKE = 20, THIEF_TAKE_GUARDED = 10;
    private static final int OASIS_GOLD = 3;
    private static final double EXP_PER_GOLD = 0.00005; // of the level's EXP, per Gold Coin of profit
    private static final int EXP_PROFIT_CAP = 1000;

    public static final int MERCIFUL = 9001087;
    public static final int DISCOUNT = 9001088;
    public static final int DESERT_FOX = 9001097;

    /** a good: its record (QR / QREx key), price tier (TIER_PRICE), the merchant who sells it and those who buy it */
    private record Good(int item, int qr, int qrEx, int tier, int seller, int[] buyers) {
    }

    private record Worker(int npc, int weight, int fee) {
    }

    private static final Supplier<List<Good>> GOODS = DataTable.cached("nihal-trade-goods", t -> t.rows().stream()
            .map(r -> new Good(r.i("item"), r.i("qr"), r.i("qrEx"), r.i("tier"), r.i("seller"), r.ints("buyers")))
            .toList());
    /** by hire index (QR 15325 worker); the Sand Warrior also keeps thieves away */
    private static final Supplier<List<Worker>> WORKERS = DataTable.cached("nihal-trade-workers", t -> t.rows().stream()
            .map(r -> new Worker(r.i("npc"), r.i("weight"), r.i("fee"))).toList());
    public static final int SAND_WARRIOR = 3;
    public static final int FOX_WORKER = 4;

    private record Offer(int itemId, int buy, int sell) {
    }

    private static final Map<Integer, List<Offer>> openShops = new ConcurrentHashMap<>();
    private static final Map<Integer, Integer> openShopNpcs = new ConcurrentHashMap<>();

    // ---- records ----

    private static Quest quest(Char chr, int qr) {
        return chr.getQuestManager().getOrCreateQuestById(qr);
    }

    private static int getInt(Char chr, int qr, String key) {
        String v = quest(chr, qr).getProperty(key);
        if (v == null || v.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static long getLong(Char chr, int qr, String key) {
        String v = quest(chr, qr).getProperty(key);
        try {
            return v == null || v.isEmpty() ? 0 : Long.parseLong(v.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void set(Char chr, int qr, String key, Object value) {
        quest(chr, qr).setProperty(key, String.valueOf(value));
    }

    private static void send(Char chr, int qr) {
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest(chr, qr))));
    }

    private static Good good(int itemId) {
        for (Good g : GOODS.get()) {
            if (g.item() == itemId) {
                return g;
            }
        }
        return null;
    }

    /** the goods' quest records, in table order */
    private static Set<Integer> goodsQrs() {
        Set<Integer> qrs = new LinkedHashSet<>();
        for (Good g : GOODS.get()) {
            qrs.add(g.qr());
        }
        return qrs;
    }

    private static int held(Char chr, Good g) {
        return getInt(chr, g.qr(), String.valueOf(g.qrEx()));
    }

    public static int getGold(Char chr) {
        return getInt(chr, GOLD_QR, "count");
    }

    public static boolean inRun(Char chr) {
        return getInt(chr, GOLD_QR, "run") == 1;
    }

    public static boolean isTradeField(int fieldId) {
        return fieldId >= CROSSROADS && fieldId < EXIT;
    }

    private static int secondsLeft(Char chr) {
        return (int) Math.max(0, (getLong(chr, GOLD_QR, "end") - System.currentTimeMillis()) / 1000);
    }

    private static int worker(Char chr) {
        String v = quest(chr, WORKER_QR).getProperty("worker");
        if (v == null || v.isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ---- prices ----

    private static double vary(int npc, int itemId, double min, double max) {
        long seed = net.swordie.ms.util.ResetClock.today().toEpochDay() * 31 + npc * 7L + itemId;
        return min + new Random(seed).nextDouble() * (max - min);
    }

    private static boolean inStock(int npc, int itemId) {
        return itemId < RARE_FROM || new Random(net.swordie.ms.util.ResetClock.today().toEpochDay() * 13 + npc + itemId).nextBoolean();
    }

    private static List<Offer> offers(int npc) {
        List<Offer> res = new ArrayList<>();
        if (npc == MERCIFUL) {
            for (Good g : GOODS.get()) {
                res.add(new Offer(g.item(), 0, Math.max(1, (int) (TIER_PRICE[g.tier()] * GameConstants.NIHAL_MERCIFUL_BUY_RATE))));
            }
            return res;
        }
        if (npc == DISCOUNT) {
            List<Good> cheap = new ArrayList<>();
            for (Good g : GOODS.get()) {
                if (g.tier() <= 1) {
                    cheap.add(g);
                }
            }
            Collections.shuffle(cheap, new Random(net.swordie.ms.util.ResetClock.today().toEpochDay()));
            for (Good g : cheap.subList(0, Math.min(DISCOUNT_GOODS, cheap.size()))) {
                res.add(new Offer(g.item(), Math.max(1, (int) (TIER_PRICE[g.tier()] * GameConstants.NIHAL_DISCOUNT_SELL_RATE)), 0));
            }
            return res;
        }
        for (Good g : GOODS.get()) {
            if (g.seller() == npc && inStock(npc, g.item())) {
                res.add(new Offer(g.item(), (int) Math.round(TIER_PRICE[g.tier()] * vary(npc, g.item(), SELLER_MIN, SELLER_MAX)), 0));
            }
        }
        for (Good g : GOODS.get()) {
            for (int buyer : g.buyers()) {
                if (buyer == npc) {
                    res.add(new Offer(g.item(), 0, (int) Math.round(TIER_PRICE[g.tier()] * vary(npc, g.item(), BUYER_MIN, BUYER_MAX))));
                }
            }
        }
        return res;
    }

    // ---- packets ----

    /** Out 472: the merchant's window (npc, no offer expiry, {item, buy price, sell price}) */
    public static OutPacket shopItems(int npc, List<Offer> list) {
        OutPacket out = new OutPacket(OutHeader.TRADE_KING_SHOP_ITEM);
        out.encodeInt(npc);
        out.encodeInt(0);
        out.encodeString("");
        out.encodeInt(list.size());
        for (Offer o : list) {
            out.encodeInt(o.itemId());
            out.encodeInt(o.buy());
            out.encodeInt(o.sell());
        }
        return out;
    }

    /** Out 473: 0 done, 1 invalid, 2 expired, 3 not enough Gold Coins, 4 too heavy, 5 not enough goods */
    public static OutPacket shopResult(int result, boolean bought, int itemId, int price) {
        OutPacket out = new OutPacket(OutHeader.TRADE_KING_SHOP_RES);
        out.encodeInt(result);
        out.encodeByte(bought);
        out.encodeInt(itemId);
        out.encodeInt(price);
        return out;
    }

    // ---- the run ----

    /** Spiegelmann at the express: a new run. */
    public static void startRun(Char chr) {
        int from = chr.getPreviousFieldID();
        if (from > 0 && (from < EXPRESS - 1 || from > EXIT)) {
            set(chr, GOLD_QR, "from", from);
        }
        for (Good g : GOODS.get()) {
            set(chr, g.qr(), String.valueOf(g.qrEx()), 0);
        }
        set(chr, GOLD_QR, "count", START_GOLD);
        set(chr, GOLD_QR, "cWeight", 0);
        set(chr, GOLD_QR, "mWeight", BASE_WEIGHT);
        set(chr, GOLD_QR, "run", 1);
        set(chr, GOLD_QR, "oasis", 0);
        set(chr, GOLD_QR, "end", System.currentTimeMillis() + RUN_SECONDS * 1000L);
        set(chr, WORKER_QR, "worker", -1);
        Set<Integer> qrs = goodsQrs();
        qrs.add(WORKER_QR);
        qrs.add(GOLD_QR);
        qrs.forEach(qr -> send(chr, qr));
    }

    /** enter_NihalTrade: the run's clock, the time limit and the desert thieves. */
    public static void onEnterTradeField(Char chr) {
        if (!inRun(chr)) {
            chr.chatMessage(ChatType.Mob, "Board Spiegelmann's Adventure Express to trade in Nihal Desert.");
            return;
        }
        int left = secondsLeft(chr);
        if (left <= 0) {
            chr.warp(EXIT);
            return;
        }
        int fieldId = chr.getFieldID();
        chr.write(FieldPacket.clock(ClockPacket.secondsClock(left)));
        EventManager.addEvent(() -> {
            if (inRun(chr) && isTradeField(chr.getFieldID()) && secondsLeft(chr) <= 0 && chr.getFieldID() == fieldId) {
                chr.chatMessage(ChatType.Mob, "Time's up! The Adventure Express is leaving.");
                chr.warp(EXIT);
            }
        }, left * 1000L + 500);
        if (fieldId >= DESERT_ROAD_FIRST && fieldId <= DESERT_ROAD_LAST) {
            boolean guarded = worker(chr) == SAND_WARRIOR;
            if (new Random().nextInt(100) < (guarded ? THIEF_CHANCE_GUARDED : THIEF_CHANCE)) {
                thieves(chr, guarded ? THIEF_TAKE_GUARDED : THIEF_TAKE);
            }
        }
    }

    private static void thieves(Char chr, int percent) {
        int stolen = 0;
        Set<Integer> qrs = new HashSet<>();
        for (Good g : GOODS.get()) {
            int have = held(chr, g);
            int take = have * percent / 100;
            if (take > 0) {
                set(chr, g.qr(), String.valueOf(g.qrEx()), have - take);
                qrs.add(g.qr());
                stolen += take;
            }
        }
        if (stolen <= 0) {
            return;
        }
        set(chr, GOLD_QR, "cWeight", Math.max(0, getInt(chr, GOLD_QR, "cWeight") - stolen));
        qrs.add(GOLD_QR);
        qrs.forEach(q -> send(chr, q));
        chr.chatMessage(ChatType.Mob, String.format("Desert Thieves! They made off with %d of your goods.", stolen));
    }

    /** A merchant's window. */
    public static void openShop(Char chr, int npc) {
        if (!inRun(chr)) {
            chr.chatMessage(ChatType.Mob, "Only traders of Spiegelmann's Adventure Express can trade here.");
            return;
        }
        List<Offer> list = offers(npc);
        openShops.put(chr.getId(), list);
        openShopNpcs.put(chr.getId(), npc);
        chr.write(shopItems(npc, list));
    }

    /** In 640 */
    public static void handleRequest(Char chr, boolean buy, int index, int count) {
        List<Offer> list = openShops.get(chr.getId());
        if (list == null || index < 0 || index >= list.size() || count <= 0 || count > MAX_COUNT || !inRun(chr)) {
            chr.write(shopResult(1, false, 0, 0));
            return;
        }
        if (secondsLeft(chr) <= 0) {
            chr.write(shopResult(2, false, 0, 0));
            return;
        }
        Offer o = list.get(index);
        Good g = good(o.itemId());
        if (g == null) {
            chr.write(shopResult(1, false, 0, 0));
            return;
        }
        int gold = getGold(chr);
        int load = getInt(chr, GOLD_QR, "cWeight");
        int have = held(chr, g);
        if (buy) {
            if (o.buy() <= 0) {
                chr.write(shopResult(1, false, 0, 0));
                return;
            }
            if ((long) o.buy() * count > gold) {
                chr.write(shopResult(3, false, 0, 0));
                return;
            }
            if (load + count > getInt(chr, GOLD_QR, "mWeight")) {
                chr.write(shopResult(4, false, 0, 0));
                return;
            }
            set(chr, GOLD_QR, "count", gold - o.buy() * count);
            set(chr, GOLD_QR, "cWeight", load + count);
            set(chr, g.qr(), String.valueOf(g.qrEx()), have + count);
        } else {
            if (o.sell() <= 0) {
                chr.write(shopResult(1, false, 0, 0));
                return;
            }
            if (have < count) {
                chr.write(shopResult(5, false, 0, 0));
                return;
            }
            set(chr, GOLD_QR, "count", gold + o.sell() * count);
            set(chr, GOLD_QR, "cWeight", Math.max(0, load - count));
            set(chr, g.qr(), String.valueOf(g.qrEx()), have - count);
        }
        send(chr, g.qr());
        send(chr, GOLD_QR);
        chr.write(shopResult(0, buy, o.itemId(), buy ? o.buy() : o.sell()));
    }

    /** In 641: the window asks for a fresh offer. */
    public static void handleInfoRequest(Char chr) {
        Integer npc = openShopNpcs.get(chr.getId());
        if (npc != null && inRun(chr)) {
            openShop(chr, npc);
        }
    }

    public static void closeShop(Char chr) {
        openShops.remove(chr.getId());
        openShopNpcs.remove(chr.getId());
    }

    // ---- porters, oases ----

    private static Worker porter(int idx) {
        List<Worker> list = WORKERS.get();
        return idx >= 0 && idx < list.size() ? list.get(idx) : null;
    }

    /** @return the porter's fee, or -1 */
    public static int workerFee(int idx) {
        Worker w = porter(idx);
        return w != null ? w.fee() : -1;
    }

    public static int workerNpc(int idx) {
        Worker w = porter(idx);
        return w != null ? w.npc() : 0;
    }

    public static int workerWeight(int idx) {
        Worker w = porter(idx);
        return w != null ? w.weight() : 0;
    }

    public static int currentWorker(Char chr) {
        return worker(chr);
    }

    /** @return what happened, for the NPC to say */
    public static String hire(Char chr, int idx) {
        if (!inRun(chr)) {
            return "You're not on a trade run.";
        }
        int fee = workerFee(idx);
        if (fee < 0) {
            return "Who?";
        }
        if (worker(chr) == idx) {
            return "They're already working for you.";
        }
        int gold = getGold(chr);
        if (gold < fee) {
            return String.format("That costs %d Gold Coins. You have %d.", fee, gold);
        }
        set(chr, GOLD_QR, "count", gold - fee);
        set(chr, GOLD_QR, "mWeight", BASE_WEIGHT + workerWeight(idx));
        set(chr, WORKER_QR, "worker", idx);
        send(chr, GOLD_QR);
        send(chr, WORKER_QR);
        return String.format("Done. You can now carry %d goods.", BASE_WEIGHT + workerWeight(idx));
    }

    /** Refreshing Oasis: full HP and MP; the first drink at each oasis of a run pays a few Gold Coins. */
    public static String drinkOasis(Char chr) {
        chr.heal(chr.getMaxHP());
        chr.healMP(chr.getMaxMP());
        if (!inRun(chr)) {
            return "The cool water refreshes you.";
        }
        int bit = chr.getFieldID() - CROSSROADS;
        int found = getInt(chr, GOLD_QR, "oasis");
        if (bit < 0 || bit > 30 || (found & (1 << bit)) != 0) {
            return "The cool water refreshes you.";
        }
        set(chr, GOLD_QR, "oasis", found | (1 << bit));
        set(chr, GOLD_QR, "count", getGold(chr) + OASIS_GOLD);
        send(chr, GOLD_QR);
        return String.format("The cool water refreshes you. A traveller left %d Gold Coins by the oasis!", OASIS_GOLD);
    }

    // ---- the end ----

    /** Spiegelmann at the exit: the profit becomes EXP. @return what he says */
    public static String settle(Char chr) {
        if (!inRun(chr)) {
            return "Ho ho! Come trade with the Adventure Express again sometime.";
        }
        int gold = getGold(chr);
        int profit = gold - START_GOLD;
        int level = Math.min(chr.getLevel(), GameConstants.charExp.length - 1);
        long exp = profit <= 0 ? 0 : (long) (GameConstants.charExp[level] * EXP_PER_GOLD * Math.min(profit, EXP_PROFIT_CAP));
        int best = getInt(chr, GOLD_QR, "best");
        if (gold > best) {
            set(chr, GOLD_QR, "best", gold);
        }
        set(chr, GOLD_QR, "run", 0);
        set(chr, GOLD_QR, "end", 0);
        for (Good g : GOODS.get()) {
            set(chr, g.qr(), String.valueOf(g.qrEx()), 0);
        }
        set(chr, GOLD_QR, "cWeight", 0);
        Set<Integer> qrs = goodsQrs();
        qrs.add(GOLD_QR);
        qrs.forEach(qr -> send(chr, qr));
        closeShop(chr);
        chr.write(FieldPacket.clock(ClockPacket.removeClock()));
        if (exp > 0) {
            chr.addExp(exp);
        }
        return profit > 0
                ? String.format("You finished with %d Gold Coins, a profit of %d. A true Trade Tycoon!", gold, profit)
                : String.format("You finished with %d Gold Coins. Trade is tough, but you'll get the hang of it!", gold);
    }

    public static int returnField(Char chr) {
        int from = getInt(chr, GOLD_QR, "from");
        return from > 0 ? from : FieldConstants.HENESYS_FIELD_ID;
    }
}
