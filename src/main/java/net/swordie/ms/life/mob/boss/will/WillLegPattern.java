package net.swordie.ms.life.mob.boss.will;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/** One packet's worth of Will's legs: each leg's delay and x, and the hit type, as in resources/data/will-leg-patterns.tsv. */
public final class WillLegPattern {

    public static final String STRIKE = "strike";
    public static final String REFLECTED = "reflected";

    private static final int LEG_Y = 0;

    private static final Supplier<Map<String, List<WillLegPattern>>> PATTERNS = DataTable.cached("will-leg-patterns", t -> {
        Map<String, Map<Integer, WillLegPattern>> byKey = new LinkedHashMap<>();
        for (DataTable.Row r : t.rows()) {
            var key = r.str("kind") + ":" + r.i("phase");
            var pattern = byKey.computeIfAbsent(key, k -> new LinkedHashMap<>())
                    .computeIfAbsent(r.i("pattern"), k -> new WillLegPattern(r.i("hit")));
            for (int x : r.ints("xs")) {
                pattern.legs.add(new int[]{r.i("delay"), x});
            }
        }
        Map<String, List<WillLegPattern>> m = new LinkedHashMap<>();
        byKey.forEach((k, v) -> m.put(k, List.copyOf(v.values())));
        return m;
    });

    private final int hitType;
    private final List<int[]> legs = new ArrayList<>();

    private WillLegPattern(int hitType) {
        this.hitType = hitType;
    }

    /** A random pattern of this kind in this phase, or null when the table has none. */
    public static WillLegPattern random(String kind, int phase) {
        var list = PATTERNS.get().get(kind + ":" + phase);
        return list == null || list.isEmpty() ? null : Util.getRandomFromCollection(list);
    }

    public int getHitType() {
        return hitType;
    }

    public int legCount() {
        return legs.size();
    }

    /** The legs with ids counted on from firstId. */
    public List<StrikeInfo> strikes(int firstId) {
        var list = new ArrayList<StrikeInfo>();
        int id = firstId;
        for (int[] leg : legs) {
            list.add(new StrikeInfo(id++, leg[0], new Position(leg[1], LEG_Y)));
        }
        return list;
    }
}
