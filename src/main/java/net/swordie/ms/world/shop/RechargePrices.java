package net.swordie.ms.world.shop;

import net.swordie.ms.loaders.DataTable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Meso price per unit when a shop recharges throwing stars, bullets and capsules (resources/data/recharge-prices.tsv,
 * matches GMS). The shop window shows it and the recharge request charges it.
 */
public final class RechargePrices {

    /** items missing from the table: one meso per unit */
    private static final double DEFAULT_UNIT_PRICE = 1.0;
    /** keeps 0.3 x 500 at 150 instead of 151 */
    private static final double ROUNDING_SLACK = 1e-9;

    private static final Supplier<Map<Integer, Double>> PRICES = DataTable.cached("recharge-prices", t -> {
        Map<Integer, Double> prices = new HashMap<>();
        for (DataTable.Row row : t.rows()) {
            prices.put(row.i("item"), row.d("unit_price"));
        }
        return Collections.unmodifiableMap(prices);
    });

    private RechargePrices() {
    }

    public static double unitPrice(int itemId) {
        return PRICES.get().getOrDefault(itemId, DEFAULT_UNIT_PRICE);
    }

    /** mesos to refill count units, rounded up like the client's shop window */
    public static long cost(int itemId, int count) {
        return count <= 0 ? 0 : (long) Math.ceil(unitPrice(itemId) * count - ROUNDING_SLACK);
    }
}
