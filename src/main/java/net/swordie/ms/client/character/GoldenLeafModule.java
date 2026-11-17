package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.hexa.HexaModule;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipDropFilter;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.constants.DamageSkinConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.HexaCoreData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.loaders.containerclasses.SetItemInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Golden Maple Leaves as the meso store above {@link #KEEP_MESOS}: a player's mesos past it turn into Leaves
 * (1 Leaf = {@link #LEAF_MESOS}), Leaves go back to mesos at {@link #LEAF_SELL_MESOS}, and the Leaf Shop
 * (@leaves, scripts/npc/leafShop.py) sells for Leaves. Bots bank and buy by the same rules.
 * <p>
 * Gear packages unlock per account: the first time one of its characters wears a whole set of a tier at once
 * ({@link #onSetsWorn}), saved in golden_leaf_sets (sql/migration V198).
 */
public final class GoldenLeafModule {

    private static final Logger log = LogManager.getLogger(GoldenLeafModule.class);

    public static final int LEAF = ItemConstants.GOLDEN_MAPLE_LEAF;
    /** Default of the setting: mesos above this become Leaves; the wallet stays between this and one Leaf more. */
    public static final long KEEP_MESOS = 80_000_000_000L;
    public static final long LEAF_MESOS = 1_000_000_000L;
    /** What a Leaf gives back in mesos. */
    public static final long LEAF_SELL_MESOS = 800_000_000L;
    /** What a Leaf costs in mesos (10% over its worth). */
    public static final long LEAF_BUY_MESOS = 1_100_000_000L;
    private static final long FULL_NOTICE_MS = 60_000;

    // ------------------------------------------------------------------------------------------------- prices

    public static final int DAMAGE_SKIN_LEAVES = 50;

    /** One thing the shop sells for Leaves. {@code quantity} items each time, {@code many}: several at once. */
    public record Offer(String id, String tab, int itemId, int quantity, int leaves, boolean many, String note) {
    }

    /** The shop (golden-leaf-offers.tsv); the nx offer's quantity is the NX credit per Leaf. */
    private static final Supplier<List<Offer>> OFFERS = DataTable.cached("golden-leaf-offers", t -> {
        List<Offer> list = new ArrayList<>();
        for (DataTable.Row r : t.rows()) {
            list.add(new Offer(r.str("id").trim(), r.str("tab").trim(), r.i("item"), r.i("quantity"), r.i("leaves"),
                    r.b("many"), r.str("note").trim()));
        }
        return List.copyOf(list);
    });

    /** Damage skins that shorten big numbers (golden-leaf-damage-skins.tsv), one permanent item each. */
    private static final Supplier<int[]> UNIT_DAMAGE_SKINS = DataTable.cached("golden-leaf-damage-skins",
            t -> t.rows().stream().mapToInt(r -> r.i("item")).toArray());

    /**
     * A gear package: one set per class (Etc.wz SetItemInfo), unlocked by wearing {@code pieces} of one at once.
     * Without {@code weapon} the package is armor only.
     */
    public record GearSet(int key, String name, int level, int leaves, int pieces, boolean weapon, int[] setIds) {
    }

    public static final List<GearSet> GEAR_SETS = List.of(
            new GearSet(1, "Root Abyss", 150, 20, 4, true, new int[]{247, 248, 249, 250, 251}),
            new GearSet(2, "AbsoLab", 160, 40, 7, true, new int[]{504, 505, 506, 507, 508}),
            new GearSet(3, "Arcane Umbra", 200, 80, 7, true, new int[]{617, 618, 619, 620, 621}),
            // the set's weapons are Genesis and Destiny ones: armor only
            new GearSet(4, "Eternal", 250, 200, 8, false, new int[]{886, 887, 888, 889, 890})
    );

    private static final Map<Integer, Long> fullNoticeAt = new ConcurrentHashMap<>();
    /** Unlocked set ids by account, loaded on first use */
    private static final Map<Integer, Set<Integer>> unlocked = new ConcurrentHashMap<>();

    private GoldenLeafModule() {
    }

    // ------------------------------------------------------------------------------------------------- banking

    /** @return the mesos kept before Leaves, by the setting */
    public static long keepMesos() {
        return ServerFeatures.getInt(ServerFeatures.Feature.GOLDEN_LEAF_KEEP) * 1_000_000_000L;
    }

    public static int getLeaves(Char chr) {
        return chr.getEtcInventory().getQuantity(LEAF);
    }

    /**
     * The wallet after mesos above {@link #KEEP_MESOS} went into Leaves (as many as the Etc inventory takes).
     *
     * @param money the wallet after a gain, possibly over {@link GameConstants#MAX_MONEY}
     */
    static long bank(Char chr, long money) {
        long keep = keepMesos();
        if (!ServerFeatures.on(ServerFeatures.Feature.GOLDEN_LEAVES) || money - keep < LEAF_MESOS) {
            return money;
        }
        int want = (int) Math.min(Integer.MAX_VALUE, (money - keep) / LEAF_MESOS);
        int n = fit(chr, want);
        if (n > 0) {
            Item leaves = ItemData.getItemDeepCopy(LEAF);
            leaves.setQuantity(n);
            try {
                chr.addItemToInventory(leaves);
            } catch (IllegalStateException e) {
                n = 0;
            }
        }
        if (n > 0) {
            money -= n * LEAF_MESOS;
            chr.chatMessage(ChatType.SystemNotice, String.format("Your mesos went over %s: %,d mesos became %,d "
                    + "Golden Maple Lea%s. Spend or exchange them with @leaves.", billions(keep),
                    n * LEAF_MESOS, n, n == 1 ? "f" : "ves"));
        }
        if (n < want) {
            long now = System.currentTimeMillis();
            Long last = fullNoticeAt.get(chr.getId());
            if (last == null || now - last >= FULL_NOTICE_MS) {
                fullNoticeAt.put(chr.getId(), now);
                chr.chatMessage(ChatType.SystemNotice, String.format("Your Etc inventory is full, so your mesos "
                        + "over %s stay mesos. Make room for Golden Maple Leaves.", billions(keep)));
            }
        }
        return money;
    }

    /** Whether a gain past {@link GameConstants#MAX_MONEY} can go into Leaves instead. */
    static boolean canBank(Char chr, long total) {
        if (!ServerFeatures.on(ServerFeatures.Feature.GOLDEN_LEAVES) || total - GameConstants.MAX_MONEY <= 0) {
            return false;
        }
        long need = (total - GameConstants.MAX_MONEY + LEAF_MESOS - 1) / LEAF_MESOS;
        return need <= Integer.MAX_VALUE && chr.canHold(LEAF, (int) need);
    }

    /** The most of {@code want} Leaves the Etc inventory takes. */
    private static int fit(Char chr, int want) {
        if (chr.canHold(LEAF, want)) {
            return want;
        }
        int lo = 0, hi = want - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            if (chr.canHold(LEAF, mid)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    private static String billions(long mesos) {
        return mesos % 1_000_000_000L == 0 ? (mesos / 1_000_000_000L) + "B" : String.format("%,d", mesos);
    }

    /** Leaves that can go back to mesos now: the wallet must stay below the next Leaf. */
    public static int maxExchange(Char chr) {
        long room = Math.min(keepMesos() + LEAF_MESOS - 1, GameConstants.MAX_MONEY) - chr.getMoney();
        if (room < LEAF_SELL_MESOS) {
            return 0;
        }
        return (int) Math.min(getLeaves(chr), room / LEAF_SELL_MESOS);
    }

    public static synchronized String exchangeToMesos(Char chr, int n) {
        if (n <= 0) {
            return "Nothing exchanged.";
        }
        if (n > maxExchange(chr)) {
            return maxExchange(chr) == 0 && getLeaves(chr) > 0
                    ? "Your wallet is full: spend some mesos first (Leaves go back to mesos while you carry less "
                    + "than " + billions(keepMesos() + LEAF_MESOS) + ")."
                    : "You can exchange at most " + maxExchange(chr) + " now.";
        }
        chr.consumeItem(LEAF, n);
        chr.addMoney(n * LEAF_SELL_MESOS);
        return String.format("Exchanged %,d Golden Maple Lea%s for %,d mesos.", n, n == 1 ? "f" : "ves",
                n * LEAF_SELL_MESOS);
    }

    /** Leaves the character can buy now: limited by mesos and Etc space. */
    public static int maxBuyLeaves(Char chr) {
        int want = (int) Math.min(9999, chr.getMoney() / LEAF_BUY_MESOS);
        return want > 0 ? fit(chr, want) : 0;
    }

    public static synchronized String buyLeaves(Char chr, int n) {
        if (n <= 0) {
            return "Nothing bought.";
        }
        if (n > maxBuyLeaves(chr)) {
            return chr.getMoney() < LEAF_BUY_MESOS ? "A Leaf costs " + billions(LEAF_BUY_MESOS) + " mesos."
                    : "You can buy at most " + maxBuyLeaves(chr) + " now (mesos or Etc space).";
        }
        chr.deductMoney(n * LEAF_BUY_MESOS, net.swordie.ms.client.character.ProgressionLog.Sink.OTHER);
        Item leaves = ItemData.getItemDeepCopy(LEAF);
        leaves.setQuantity(n);
        chr.addItemToInventory(leaves);
        return String.format("Bought %,d Golden Maple Lea%s for %,d mesos.", n, n == 1 ? "f" : "ves",
                n * LEAF_BUY_MESOS);
    }

    // ------------------------------------------------------------------------------------------------- shop

    public static List<Offer> getOffers(String tab) {
        List<Offer> res = new ArrayList<>();
        for (Offer o : OFFERS.get()) {
            if (o.tab().equals(tab) && (o.itemId() == 0 || (ItemConstants.isEquip(o.itemId())
                    ? ItemData.getEquipInfoById(o.itemId()) != null : ItemData.getItemInfoByID(o.itemId()) != null))) {
                res.add(o);
            }
        }
        return res;
    }

    public static Offer getOffer(String id) {
        return OFFERS.get().stream().filter(o -> o.id().equals(id)).findFirst().orElse(null);
    }

    /** The unit damage skins the server can give, without the ones the account has. */
    public static List<Integer> getDamageSkins(Char chr) {
        List<Integer> res = new ArrayList<>();
        Account acc = chr.getAccount();
        for (int id : UNIT_DAMAGE_SKINS.get()) {
            if (ItemData.getItemInfoByID(id) != null && DamageSkinConstants.isHandledDamageSkinItemId(id)
                    && (acc == null || acc.getDamageSkinByItemID(id) == null)) {
                res.add(id);
            }
        }
        return res;
    }

    /** How many of an offer the character could buy now (Leaves, room and limits). */
    public static int maxBuy(Char chr, Offer o) {
        int leaves = getLeaves(chr) / Math.max(1, o.leaves());
        int most = o.many() ? leaves : Math.min(1, leaves);
        switch (o.id()) {
            case "solErda" -> {
                if (!chr.getQuestManager().hasQuestCompleted(HexaConstants.SIXTH_JOB_QUEST)) {
                    return 0;
                }
                most = Math.min(most, HexaCoreData.getSolErdaSlotMax() - HexaModule.getSolErda(chr));
            }
            case "nx" -> most = Math.min(most, (Integer.MAX_VALUE - chr.getAccount().getNxCredit()) / Math.max(1, o.quantity()));
            case "charSlot" -> {
                User user = chr.getUser();
                if (user == null || user.getCharacterSlots() >= ServerConstants.MAX_CHARACTERS) {
                    return 0;
                }
            }
            default -> {
            }
        }
        return Math.max(0, most);
    }

    /** Why an offer can't be bought, or null. */
    public static String whyNot(Char chr, Offer o) {
        if (getLeaves(chr) < o.leaves()) {
            return "You need " + o.leaves() + " Golden Maple Leaves for that.";
        }
        return switch (o.id()) {
            case "solErda" -> !chr.getQuestManager().hasQuestCompleted(HexaConstants.SIXTH_JOB_QUEST)
                    ? "Sol Erda is for characters with the 6th job." : maxBuy(chr, o) <= 0
                    ? "Your HEXA Matrix holds as much Sol Erda as it can." : null;
            case "nx" -> maxBuy(chr, o) <= 0 ? "Your account holds as much NX credit as it can." : null;
            case "charSlot" -> maxBuy(chr, o) <= 0 ? "Your account has the most character slots there are." : null;
            default -> null;
        };
    }

    public static synchronized String buy(Char chr, String offerId, int count) {
        Offer o = getOffer(offerId);
        if (o == null || count <= 0) {
            return "That isn't sold here.";
        }
        String why = whyNot(chr, o);
        if (why != null) {
            return why;
        }
        if (count > maxBuy(chr, o)) {
            return "You can buy at most " + maxBuy(chr, o) + " now.";
        }
        int price = o.leaves() * count;
        switch (o.id()) {
            case "solErda" -> {
                chr.consumeItem(LEAF, price);
                HexaModule.addSolErda(chr, count);
                return String.format("Added %d Sol Erda to your HEXA Matrix for %d Leaves.", count, price);
            }
            case "nx" -> {
                chr.consumeItem(LEAF, price);
                chr.getAccount().addNXCredit(count * o.quantity());
                return String.format("Added %,d NX credit for %d Lea%s (Cash Shop: NX Credit).",
                        (long) count * o.quantity(), price, price == 1 ? "f" : "ves");
            }
            case "charSlot" -> {
                User user = chr.getUser();
                chr.consumeItem(LEAF, price);
                user.setCharacterSlots(user.getCharacterSlots() + 1);
                return "Your account now has " + user.getCharacterSlots() + " character slots.";
            }
            default -> {
                return giveItem(chr, o.itemId(), o.quantity() * count, price);
            }
        }
    }

    public static synchronized String buyDamageSkin(Char chr, int itemId) {
        if (!getDamageSkins(chr).contains(itemId)) {
            return "That damage skin isn't sold here (or your account has it).";
        }
        if (getLeaves(chr) < DAMAGE_SKIN_LEAVES) {
            return "You need " + DAMAGE_SKIN_LEAVES + " Golden Maple Leaves for that.";
        }
        return giveItem(chr, itemId, 1, DAMAGE_SKIN_LEAVES) + " Use it to add the skin to your collection.";
    }

    private static String giveItem(Char chr, int itemId, int quantity, int price) {
        if (!chr.canHold(itemId, quantity)) {
            return "Please make room in your inventory first.";
        }
        Item item = ItemConstants.isEquip(itemId) ? ItemData.getEquipDeepCopy(itemId, false)
                : ItemData.getItemDeepCopy(itemId);
        if (item == null) {
            return "That item can't be given right now.";
        }
        chr.consumeItem(LEAF, price);
        if (!ItemConstants.isEquip(itemId)) {
            item.setQuantity(quantity);
        }
        chr.addItemToInventory(item);
        return String.format("You got %s%s for %d Leaves.", quantity > 1 ? quantity + " " : "", name(itemId), price);
    }

    private static String name(int itemId) {
        String n = StringData.getItemStringById(itemId);
        return n == null ? String.valueOf(itemId) : n;
    }

    // ------------------------------------------------------------------------------------------------- gear sets

    public static GearSet getGearSet(int key) {
        return GEAR_SETS.stream().filter(s -> s.key() == key).findFirst().orElse(null);
    }

    private static Set<Integer> unlockedSets(int accountId) {
        return unlocked.computeIfAbsent(accountId, id -> {
            Set<Integer> res = ConcurrentHashMap.newKeySet();
            for (var row : DatabaseManager.queryRows("SELECT setid AS setid FROM golden_leaf_sets WHERE accountid = ?",
                    id)) {
                if (row.get("setid") instanceof Number n) {
                    res.add(n.intValue());
                }
            }
            return res;
        });
    }

    public static boolean isUnlocked(Char chr, GearSet set) {
        if (chr.getAccount() == null) {
            return false;
        }
        Set<Integer> have = unlockedSets(chr.getAccount().getId());
        return Arrays.stream(set.setIds()).anyMatch(have::contains);
    }

    /**
     * From the equipped items' set counts (Inventory.recalcBaseStats): records a whole set of a package tier the
     * first time the account wears one. The database work runs off the caller's thread.
     */
    public static void onSetsWorn(Char chr, Map<Integer, Integer> setCount) {
        if (chr == null || chr.getAccount() == null || setCount.isEmpty()) {
            return;
        }
        for (GearSet set : GEAR_SETS) {
            for (int setId : set.setIds()) {
                if (setCount.getOrDefault(setId, 0) >= set.pieces()) {
                    int accountId = chr.getAccount().getId();
                    Set<Integer> have = unlocked.get(accountId);
                    if (have != null && have.contains(setId)) {
                        continue;
                    }
                    EventManager.addEvent(() -> record(chr, accountId, set, setId), 0);
                }
            }
        }
    }

    private static synchronized void record(Char chr, int accountId, GearSet set, int setId) {
        try {
            Set<Integer> have = unlockedSets(accountId);
            if (have.contains(setId)) {
                return;
            }
            boolean first = Arrays.stream(set.setIds()).noneMatch(have::contains);
            DatabaseManager.executeQuery("INSERT IGNORE INTO golden_leaf_sets (accountid, setid) VALUES (?, ?)",
                    accountId, setId);
            have.add(setId);
            if (first) {
                chr.chatMessage(ChatType.SystemNotice, "You wore a full " + set.name() + " set: its gear package "
                        + "is now open to your whole account in the Leaf Shop (@leaves).");
            }
        } catch (Exception e) {
            log.error("Could not record the " + set.name() + " set for account " + accountId, e);
        }
    }

    /** The package's items for this character's class and weapon: one piece per armor slot, one weapon. */
    public static List<Integer> getPackageItems(Char chr, GearSet set) {
        Map<Integer, Integer> armor = new TreeMap<>(); // by item type
        List<Integer> weapons = new ArrayList<>();
        for (int setId : set.setIds()) {
            SetItemInfo info = EtcData.getSetItemInfoById(setId);
            if (info == null) {
                continue;
            }
            for (int id : info.getItemIds()) {
                if (ItemData.getEquipInfoById(id) == null || !EquipDropFilter.isUsableBy(id, chr)) {
                    continue;
                }
                if (ItemConstants.isWeapon(id)) {
                    if (set.weapon()) {
                        weapons.add(id);
                    }
                } else {
                    armor.merge(id / 10000, id, Math::min);
                }
            }
        }
        List<Integer> res = new ArrayList<>(armor.values());
        if (!weapons.isEmpty()) {
            Collections.sort(weapons);
            Item worn = chr.getEquippedItemByBodyPart(BodyPart.Weapon);
            int type = worn == null ? -1 : worn.getItemId() / 10000;
            res.add(weapons.stream().filter(id -> id / 10000 == type).findFirst().orElse(weapons.get(0)));
        }
        return res;
    }

    /** Why a package can't be bought now, or null. */
    public static String whyNotSet(Char chr, GearSet set) {
        if (!isUnlocked(chr, set)) {
            return "Locked: wear a full " + set.name() + " set (" + set.pieces() + " pieces) on any character of "
                    + "your account once to open it.";
        }
        if (chr.getLevel() < set.level()) {
            return "You need level " + set.level() + " for the " + set.name() + " package.";
        }
        if (getLeaves(chr) < set.leaves()) {
            return "You need " + set.leaves() + " Golden Maple Leaves for that.";
        }
        if (getPackageItems(chr, set).isEmpty()) {
            return "The " + set.name() + " set has nothing for your class.";
        }
        return null;
    }

    public static synchronized String buySet(Char chr, int key) {
        GearSet set = getGearSet(key);
        if (set == null) {
            return "That package isn't sold here.";
        }
        String why = whyNotSet(chr, set);
        if (why != null) {
            return why;
        }
        List<Integer> ids = getPackageItems(chr, set);
        if (chr.getEquipInventory().getEmptySlots() < ids.size()) {
            return "Please make " + ids.size() + " free slots in your Equip inventory first.";
        }
        chr.consumeItem(LEAF, set.leaves());
        for (int id : ids) {
            Equip e = ItemData.getEquipDeepCopy(id, false);
            if (e != null) {
                chr.addItemToInventory(e);
            }
        }
        return String.format("You got the %s package (%d pieces) for %d Leaves.", set.name(), ids.size(),
                set.leaves());
    }
}
