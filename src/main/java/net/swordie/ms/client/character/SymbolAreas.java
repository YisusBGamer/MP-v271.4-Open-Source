package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.loaders.DataTable;

import java.util.*;
import java.util.function.Supplier;

/**
 * Arcane, Sacred and Grand Sacred symbols and their areas (resources/data/symbol-areas.tsv), in progression order.
 */
public final class SymbolAreas {

    /**
     * One symbol. alias: older ids of the same symbol; level 0 = no area step for it (name, town, force and the
     * daily NPC/map are then unset).
     */
    public record Area(int symbol, int[] alias, BodyPart slot, String name, int level, int town, int force,
                       int dailyNpc, int dailyMap, int coupon) {

        public boolean hasStep() {
            return level > 0;
        }
    }

    private record Tables(List<Area> all, List<Area> steps, Map<Integer, Area> byItem, Map<Integer, Integer> coupons) {
    }

    private static final Supplier<Tables> TABLES = DataTable.cached("symbol-areas", t -> {
        List<Area> all = new ArrayList<>();
        Map<Integer, Area> byItem = new HashMap<>();
        Map<Integer, Integer> coupons = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            Area a = new Area(r.i("symbol"), r.ints("alias"), BodyPart.valueOf(r.str("slot").trim()),
                    r.str("name").trim(), r.i("level", 0), r.i("town", 0), r.i("force", 0), r.i("dailyNpc", 0),
                    r.i("dailyMap", 0), r.i("coupon", 0));
            all.add(a);
            byItem.put(a.symbol(), a);
            for (int id : a.alias()) {
                byItem.put(id, a);
            }
            if (a.coupon() > 0) {
                coupons.put(a.coupon(), a.symbol());
            }
        }
        return new Tables(List.copyOf(all), all.stream().filter(Area::hasStep).toList(), byItem, coupons);
    });

    /** @return the symbol an untradable symbol coupon gives, or 0 */
    public static int symbolOfCoupon(int itemId) {
        return TABLES.get().coupons().getOrDefault(itemId, 0);
    }

    private SymbolAreas() {
    }

    /** @return every symbol, in order */
    public static List<Area> all() {
        return TABLES.get().all();
    }

    /** @return the areas with a progression step (level, town, dailies), in order */
    public static List<Area> steps() {
        return TABLES.get().steps();
    }

    /** @return the symbol (or an older id of it), or null */
    public static Area byItem(int itemId) {
        return TABLES.get().byItem().get(itemId);
    }

    /** @return the symbol's current id (an older id maps to it), or the id itself when it is no symbol */
    public static int key(int itemId) {
        Area a = byItem(itemId);
        return a == null ? itemId : a.symbol();
    }

    /** @return the symbol's index among the symbols of its kind (arcane, sacred, grand sacred), or -1 */
    public static int indexInKind(int itemId) {
        Area a = byItem(itemId);
        if (a == null) {
            return -1;
        }
        int i = 0;
        for (Area o : all()) {
            if (o == a) {
                return i;
            }
            if (sameKind(o.symbol(), a.symbol())) {
                i++;
            }
        }
        return -1;
    }

    private static boolean sameKind(int a, int b) {
        return a / KIND_BLOCK == b / KIND_BLOCK;
    }

    /** symbol ids of one kind share their first four digits (1712xxx arcane, 1713xxx sacred, 1714xxx grand) */
    private static final int KIND_BLOCK = 1000;

    /** @return whether the item is a symbol the client equips in this slot */
    public static boolean fitsSlot(int itemId, BodyPart slot) {
        Area a = byItem(itemId);
        return a != null && a.slot() == slot;
    }
}
