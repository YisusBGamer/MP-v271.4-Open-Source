package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * The Swordie Coin Shop (npc/swordieCoinShop.py, @shop): cosmetics, bags, a pocket slot shortcut and capped
 * time-savers, priced in Swordie Coins. The script shows {@link #lines(String)} and buys
 * with {@link #buy(Char, String, int)}; caps are per account (coin_rewards, kind "cap"): weekly ones reset on Thursday
 * (UTC), lifetime ones never.
 */
public final class SwordieCoinShop {

    private static final Logger log = LogManager.getLogger(SwordieCoinShop.class);

    public static final int SPEAKER = 9062231; // Smithy, the old coin shop NPC

    public enum Kind {
        /** gives the items of the chosen option */
        ITEMS,
        /** gives the chosen symbol, which must be worn and below its max level */
        SYMBOL,
        /** Charm EXP up to the level that opens the pocket slot */
        POCKET
    }

    /** One thing the shop sells: an option is a list of {item, quantity}. cap 0 = no cap; weekly or for good. */
    public record Line(String id, String section, String name, int price, int cap, boolean weekly, Kind kind,
                       List<String> optionNames, List<int[][]> options, String note) {
    }

    public static final String[][] SECTIONS = {
            {"chairs", "Chairs"},
            {"mounts", "Mounts"},
            {"skins", "Damage skins"},
            {"outfits", "Outfit sets"},
            {"bags", "Bags"},
            {"services", "Services"},
            {"time", "Time-savers (weekly caps)"},
    };

    /**
     * resources/data/swordie-coin-shop.tsv: type (item, outfit, symbols, hammer, pocket, items), id, section, name,
     * price, cap, weekly, count, items, note. Built on first use, when the item names (string data) are loaded.
     */
    private static final Supplier<List<Line>> LINES = DataTable.cached("swordie-coin-shop", t -> {
        List<Line> res = new ArrayList<>();
        for (DataTable.Row r : t.rows()) {
            String type = r.str("type").trim();
            res.add(switch (type) {
                case "item" -> item(r.str("section").trim(), r.i("items"), r.i("price"));
                case "outfit" -> outfit(r.str("name").trim(), r.i("price"), versions(r.str("items")));
                case "symbols" -> symbols(r.str("id").trim(), r.str("name").trim(), r.i("price"), r.i("cap"),
                        r.i("count"), r.str("note").trim(), r.ints("items"));
                case "hammer" -> hammer(r.str("id").trim(), r.i("items"), r.i("price"), r.i("cap"));
                case "pocket" -> new Line(r.str("id").trim(), r.str("section").trim(), r.str("name").trim(),
                        r.i("price"), r.i("cap"), r.b("weekly"), Kind.POCKET, List.of("Open it"),
                        Collections.singletonList(new int[0][]),
                        r.str("note").trim().replace("{charm}", String.valueOf(Char.POCKET_CHARM_LEVEL)));
                case "items" -> items(r);
                default -> throw new IllegalArgumentException("swordie-coin-shop.tsv line " + r.line()
                        + ": unknown type " + type);
            });
        }
        return List.copyOf(res);
    });

    private static List<Line> lines() {
        return LINES.get();
    }

    private static String itemName(int id) {
        String n = net.swordie.ms.loaders.StringData.getItemStringById(id);
        return n == null ? String.valueOf(id) : n.trim();
    }

    private static Line item(String section, int itemId, int price) {
        return new Line(String.valueOf(itemId), section, itemName(itemId), price, 0, false, Kind.ITEMS,
                List.of(itemName(itemId)), Collections.singletonList(new int[][]{{itemId, 1}}), null);
    }

    /** outfit versions: comma-separated item lists split by | */
    private static int[][] versions(String cell) {
        return Arrays.stream(cell.trim().split(java.util.regex.Pattern.quote("|")))
                .map(v -> Arrays.stream(v.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
    }

    private static Line outfit(String name, int price, int[]... versions) {
        List<String> names = new ArrayList<>();
        List<int[][]> options = new ArrayList<>();
        for (int[] v : versions) {
            int[][] items = new int[v.length][];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < v.length; i++) {
                items[i] = new int[]{v[i], 1};
                sb.append(i == 0 ? "" : ", ").append(itemName(v[i]));
            }
            names.add(sb.toString());
            options.add(items);
        }
        return new Line("set_" + name.replaceAll("[^A-Za-z]", ""), "outfits", name + " set", price, 0, false,
                Kind.ITEMS, names, options, null);
    }

    private static Line symbols(String id, String name, int price, int cap, int count, String note,
                                int... symbolIds) {
        List<String> names = new ArrayList<>();
        List<int[][]> options = new ArrayList<>();
        for (int s : symbolIds) {
            names.add(itemName(s));
            options.add(new int[][]{{s, count}});
        }
        return new Line(id, "time", name, price, cap, true, Kind.SYMBOL, names, options, note);
    }

    private static Line hammer(String id, int itemId, int price, int cap) {
        return new Line(id, "time", itemName(itemId), price, cap, false, Kind.ITEMS, List.of(itemName(itemId)),
                Collections.singletonList(new int[][]{{itemId, 1}}), cap == 1 ? "One per account." : cap + " per account.");
    }

    /** one option: item:quantity pairs (comma-separated), shown as x&lt;quantity&gt; */
    private static Line items(DataTable.Row r) {
        String[] pairs = r.str("items").trim().split(",");
        int[][] items = new int[pairs.length][];
        for (int i = 0; i < pairs.length; i++) {
            String[] p = pairs[i].trim().split(":");
            items[i] = new int[]{Integer.parseInt(p[0]), Integer.parseInt(p[1])};
        }
        return new Line(r.str("id").trim(), r.str("section").trim(), r.str("name").trim(), r.i("price"), r.i("cap"),
                r.b("weekly"), Kind.ITEMS, List.of("x" + items[0][1]), Collections.singletonList(items),
                r.str("note").trim());
    }

    // ------------------------------------------------------------------------------------------------------- lookups

    public static List<Line> all() {
        return Collections.unmodifiableList(lines());
    }

    public static List<Line> lines(String section) {
        List<Line> res = new ArrayList<>();
        for (Line l : lines()) {
            if (l.section().equals(section)) {
                res.add(l);
            }
        }
        return res;
    }

    public static Line line(String id) {
        for (Line l : lines()) {
            if (l.id().equals(id)) {
                return l;
            }
        }
        return null;
    }

    public static boolean isOpen() {
        return SwordieCoinRewards.on();
    }

    public static int coins(Char chr) {
        return chr.getInventoryByType(net.swordie.ms.enums.InvType.ETC).getQuantity(ItemConstants.SWORDIE_COIN);
    }

    private static String period(Line l) {
        return l.weekly() ? SwordieCoinRewards.week() : SwordieCoinRewards.EVER;
    }

    /** @return how many more the account can buy (this week for weekly caps), or -1 for no cap */
    public static int capLeft(Char chr, Line l) {
        if (l.cap() <= 0) {
            return -1;
        }
        int bought = SwordieCoinRewards.stored(chr.getAccount().getId(), "cap", l.id(), period(l));
        return Math.max(0, l.cap() - Math.max(0, bought));
    }

    private static Equip wornSymbol(Char chr, int symbolId) {
        for (Item i : chr.getEquippedInventory().getItems()) {
            if (i instanceof Equip e && (i.getItemId() == symbolId || SymbolAreas.key(i.getItemId()) == symbolId)) {
                return e;
            }
        }
        return null;
    }

    /** @return why this option can't be bought by this character (null: it can, coins and caps aside) */
    public static String optionProblem(Char chr, Line l, int option) {
        if (l == null || option < 0 || option >= l.options().size()) {
            return "That isn't for sale.";
        }
        switch (l.kind()) {
            case SYMBOL -> {
                int symbolId = l.options().get(option)[0][0];
                Equip worn = wornSymbol(chr, symbolId);
                if (worn == null) {
                    return "Wear " + itemName(symbolId) + " first (it comes from that region's story).";
                }
                if (worn.getSymbolLevel() >= ItemConstants.getMaxSymbolLevel(symbolId)) {
                    return "Your " + itemName(symbolId) + " is already at its max level.";
                }
            }
            case POCKET -> {
                if (chr.isPocketSlotOpen()) {
                    return "Your pocket slot is open already.";
                }
            }
            default -> {
            }
        }
        return null;
    }

    /** The Charm EXP the character still needs for the level that opens the pocket slot. */
    public static int charmNeededForPocket(Char chr) {
        int total = 0;
        for (int i = 0; i < Char.POCKET_CHARM_LEVEL && i < GameConstants.traitExp.length; i++) {
            total += GameConstants.traitExp[i];
        }
        return Math.max(0, total - chr.getStat(Stat.charmEXP));
    }

    /**
     * Buys one of a line: checks the option, the cap, the coins and the room, then takes the coins and gives it.
     *
     * @return null when bought, else what's wrong (for the player)
     */
    public static synchronized String buy(Char chr, String lineId, int option) {
        if (!SwordieCoinRewards.earnsCoins(chr)) {
            return "The shop can't serve you.";
        }
        if (!isOpen()) {
            return "The Swordie Coin Shop is closed right now.";
        }
        Line l = line(lineId);
        String problem = optionProblem(chr, l, option);
        if (problem != null) {
            return problem;
        }
        int left = capLeft(chr, l);
        if (left == 0) {
            return l.weekly() ? "Your account has bought this week's limit of " + l.name() + " (" + l.cap()
                    + "). It resets on Thursday." : "Your account already has all of these it can buy.";
        }
        if (coins(chr) < l.price()) {
            return "You need " + l.price() + " Swordie Coins (you have " + coins(chr) + ").";
        }
        int[][] items = l.options().get(option);
        List<Item> check = new ArrayList<>();
        for (int[] it : items) {
            for (int n = 0; n < (ItemConstants.isEquip(it[0]) ? it[1] : 1); n++) {
                Item copy = ItemData.getItemDeepCopy(it[0]);
                if (copy == null) {
                    log.error("Coin shop line " + l.id() + " has an unknown item " + it[0]);
                    return "That item isn't available. Please report this.";
                }
                if (!ItemConstants.isEquip(it[0])) {
                    copy.setQuantity(it[1]);
                }
                check.add(copy);
            }
        }
        if (!check.isEmpty() && !chr.canHold(check)) {
            return "Make room in your inventory first.";
        }
        if (l.cap() > 0) {
            int bought = l.cap() - left;
            if (!SwordieCoinRewards.store(chr.getAccount().getId(), "cap", l.id(), period(l), bought + 1)) {
                return "The shop couldn't record the purchase. Please try again later.";
            }
        }
        chr.consumeItem(ItemConstants.SWORDIE_COIN, l.price());
        if (l.kind() == Kind.POCKET) {
            chr.addTraitExp(Stat.charmEXP, charmNeededForPocket(chr));
            chr.chatMessage("Your pocket slot is open. @upgrades can put a pocket item on now.");
        }
        for (int[] it : items) {
            if (ItemConstants.isEquip(it[0])) {
                for (int n = 0; n < it[1]; n++) {
                    chr.addItemToInventory(it[0], 1);
                }
            } else {
                chr.addItemToInventory(it[0], it[1]);
            }
        }
        log.info(String.format("Coin shop: %s (account %d) bought %s option %d for %d coins.", chr.getName(),
                chr.getAccount().getId(), l.id(), option, l.price()));
        return null;
    }

    public static List<String> sectionIds() {
        List<String> res = new ArrayList<>();
        Arrays.stream(SECTIONS).forEach(s -> res.add(s[0]));
        return res;
    }
}
