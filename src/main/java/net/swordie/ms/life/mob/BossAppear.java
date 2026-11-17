package net.swordie.ms.life.mob;

import net.swordie.ms.loaders.DataTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Boss entrance effects (resources/data/boss-appear.tsv): the appear type and option GMS sends the first time a boss
 * template enters a fight's map. The appear type is an Effect.wz Summon.img index.
 */
public class BossAppear {
    public record Appear(int type, int option) {
    }

    /** resources/data/boss-appear.tsv: template, appearType, option, tag */
    private static final Supplier<Map<Integer, Appear>> APPEARS = DataTable.cached("boss-appear", t -> {
        Map<Integer, Appear> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("template"), new Appear(r.i("appearType"), r.i("option")));
        }
        return m;
    });

    /** @return the GMS entrance for this template, or null */
    public static Appear get(int templateId) {
        return APPEARS.get().get(templateId);
    }
}
