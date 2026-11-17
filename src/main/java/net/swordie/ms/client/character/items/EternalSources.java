package net.swordie.ms.client.character.items;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The Grandis bosses' Eternal set tokens and armor boxes (v265 Consume items, GMS rules): 10 of a token or one box
 * for an Eternal piece, 2 fragments for a token. Armor tokens give a hat, top, bottom or shoulder; Limbo's and
 * Baldrix's and Jupiter's give shoes, gloves or a cape. Used by the item script (eternal_source) and by the journey bots.
 * Tables: resources/data/eternal-sources.tsv, eternal-pieces.tsv, eternal-fragments.tsv.
 */
public final class EternalSources {

    /** item -> what it gives (resources/data/eternal-sources.tsv) */
    private static final Supplier<Map<Integer, Source>> SOURCES = DataTable.cached("eternal-sources", t -> {
        Map<Integer, Source> m = new LinkedHashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("item"), new Source(r.i("cost"), r.str("set").trim(), r.b("ownClass")));
        }
        return m;
    });
    /** set -> its pieces in choice order (resources/data/eternal-pieces.tsv) */
    private static final Supplier<Map<String, List<Integer>>> PIECES = DataTable.cached("eternal-pieces", t -> {
        Map<String, List<Integer>> m = new LinkedHashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(r.str("set").trim(), k -> new ArrayList<>()).add(r.i("item"));
        }
        return m;
    });
    /** fragment -> token (resources/data/eternal-fragments.tsv) */
    private static final Supplier<Map<Integer, Integer>> FRAGMENTS = DataTable.cached("eternal-fragments", t -> {
        Map<Integer, Integer> m = new LinkedHashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("fragment"), r.i("token"));
        }
        return m;
    });
    public static final int FRAGMENT_COST = 2;

    /** cost: how many for one piece; set: an eternal-pieces.tsv set; ownClass: only the user's class's pieces */
    private record Source(int cost, String set, boolean ownClass) {
    }

    private EternalSources() {
    }

    public static boolean handles(int itemId) {
        return SOURCES.get().containsKey(itemId) || FRAGMENTS.get().containsKey(itemId);
    }

    public static boolean isFragment(int itemId) {
        return FRAGMENTS.get().containsKey(itemId);
    }

    public static int fragmentToken(int itemId) {
        return FRAGMENTS.get().getOrDefault(itemId, 0);
    }

    public static int cost(int itemId) {
        Source s = SOURCES.get().get(itemId);
        return s == null ? FRAGMENT_COST : s.cost();
    }

    public static Collection<Integer> sourceIds() {
        List<Integer> ids = new ArrayList<>(SOURCES.get().keySet());
        ids.addAll(FRAGMENTS.get().keySet());
        return ids;
    }

    /** Every piece the item gives (all classes unless it's a job box). */
    public static List<Integer> pieces(int itemId) {
        Source s = SOURCES.get().get(itemId);
        return s == null ? new ArrayList<>() : new ArrayList<>(PIECES.get().getOrDefault(s.set(), List.of()));
    }

    /** The pieces the character's class wears. */
    public static List<Integer> piecesFor(Char chr, int itemId) {
        List<Integer> res = new ArrayList<>();
        for (int id : pieces(itemId)) {
            if (EquipDropFilter.isUsableBy(id, chr)) {
                res.add(id);
            }
        }
        return res;
    }

    /** What the player may pick: a job box only its class's pieces. */
    public static List<Integer> choices(Char chr, int itemId) {
        Source s = SOURCES.get().get(itemId);
        return s != null && s.ownClass() ? piecesFor(chr, itemId) : pieces(itemId);
    }

    /** @return null if done, else why not */
    public static synchronized String exchange(Char chr, int itemId, int pieceId) {
        if (!SOURCES.get().containsKey(itemId) || !choices(chr, itemId).contains(pieceId)) {
            return "That isn't one of the choices.";
        }
        int cost = cost(itemId);
        if (!chr.hasItemCount(itemId, cost)) {
            return "You need " + cost + " of these for an Eternal piece.";
        }
        Item piece = ItemData.getItemDeepCopy(pieceId);
        if (piece == null) {
            return "This can't be exchanged right now.";
        }
        if (!chr.canHold(piece)) {
            return "Please make room in your Equip inventory.";
        }
        chr.consumeItem(itemId, cost);
        chr.addItemToInventory(piece);
        return null;
    }

    /** Fragments into tokens, as many pairs as there are. @return tokens made (0: none), -1 no room */
    public static synchronized int combine(Char chr, int fragmentId) {
        int token = fragmentToken(fragmentId);
        if (token == 0) {
            return 0;
        }
        int n = chr.getConsumeInventory().getQuantity(fragmentId) / FRAGMENT_COST;
        if (n <= 0) {
            return 0;
        }
        if (!chr.canHold(token, n)) {
            return -1;
        }
        chr.consumeItem(fragmentId, n * FRAGMENT_COST);
        chr.addItemToInventory(token, n);
        return n;
    }

    /**
     * What a player does with the tokens and boxes held: fragments into tokens, then the wanted pieces it doesn't
     * hold yet, boxes before tokens. @return the pieces taken
     */
    public static List<Integer> redeemFor(Char chr, Collection<Integer> wanted) {
        for (int frag : FRAGMENTS.get().keySet()) {
            combine(chr, frag);
        }
        List<Integer> got = new ArrayList<>();
        List<Integer> order = new ArrayList<>(SOURCES.get().keySet());
        order.sort((a, b) -> Integer.compare(cost(a), cost(b)));
        for (int src : order) {
            while (chr.hasItemCount(src, cost(src))) {
                Integer pick = null;
                for (int id : choices(chr, src)) {
                    if (wanted.contains(id) && !got.contains(id) && !holds(chr, id)) {
                        pick = id;
                        break;
                    }
                }
                if (pick == null || exchange(chr, src, pick) != null) {
                    break;
                }
                got.add(pick);
            }
        }
        return got;
    }

    private static boolean holds(Char chr, int id) {
        return chr.getEquippedInventory().getItemByItemID(id) != null
                || chr.getEquipInventory().getItemByItemID(id) != null;
    }
}
