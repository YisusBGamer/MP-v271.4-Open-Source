package net.swordie.ms.life.drop;

import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerFeatures.Feature;

import net.swordie.ms.client.character.SymbolAreas;
import net.swordie.ms.loaders.DataTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Drop settings applied at drop time on top of the mob_drops table, so the table (and its migrations) stays as it is:
 * chance and amount scales for the item groups our migrations changed, and fixed amounts for some boss rewards.
 */
public final class DropTuning {
    /** item and boss groups (drop-tuning-groups.tsv) */
    private static final Supplier<Map<String, int[]>> GROUPS = DataTable.cached("drop-tuning-groups", t -> {
        Map<String, int[]> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.str("group").trim(), r.ints("ids"));
        }
        return m;
    });
    private static final String NODESTONES = "nodestones", SOUL_SHARDS = "soulShards", ETERNAL_BOXES = "eternalBoxes",
            CRA_BOSSES = "craBosses", CRA_RUNES = "craRunes", PIECE_BOX_BOSSES = "pieceBoxBosses",
            PIECE_BOXES = "pieceBoxes", CORES_DROPLETS = "coresDroplets";
    private static final int GOLLUX_PENNY = 4310098;
    private static final int GOLLUX_COIN = 4310097;
    private static final int MAX_CHANCE = 1_000_000;

    private DropTuning() {
    }

    private static boolean in(String group, int id) {
        for (int i : GROUPS.get().getOrDefault(group, new int[0])) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }

    /** @return the symbol a Grandis symbol coupon gives, or 0 */
    public static int symbolOfCoupon(int itemId) {
        return SymbolAreas.symbolOfCoupon(itemId);
    }

    private static boolean isPieceBox(int itemId) {
        return in(PIECE_BOXES, itemId);
    }

    /** @return the chance scale in % for this item, or 100 */
    private static int chancePercent(int itemId) {
        if (ItemConstants.isArcaneSymbol(itemId)) {
            return ServerFeatures.getInt(Feature.ARCANE_SYMBOL_DROPS);
        }
        if (ItemConstants.isAuthenticForceSymbol(itemId) || ItemConstants.isGrandSymbol(itemId)
                || symbolOfCoupon(itemId) != 0) {
            return ServerFeatures.getInt(Feature.SACRED_SYMBOL_DROPS);
        }
        if (in(NODESTONES, itemId)) {
            return ServerFeatures.getInt(Feature.NODESTONE_DROPS);
        }
        if (in(SOUL_SHARDS, itemId)) {
            return ServerFeatures.getInt(Feature.SOUL_SHARD_DROPS);
        }
        if (in(ETERNAL_BOXES, itemId)) {
            return ServerFeatures.getInt(Feature.ETERNAL_BOX_DROPS);
        }
        if (itemId == ItemConstants.SPELL_TRACE_ID) {
            return ServerFeatures.getInt(Feature.SPELL_TRACE_DROP_CHANCE);
        }
        return 100;
    }

    /** @return the amount scale in % for this item, or 100 */
    private static int amountPercent(int itemId) {
        if (itemId == GOLLUX_PENNY) {
            return ServerFeatures.getInt(Feature.GOLLUX_PENNIES);
        }
        if (itemId == GOLLUX_COIN) {
            return ServerFeatures.getInt(Feature.GOLLUX_COINS);
        }
        if (in(CORES_DROPLETS, itemId)) {
            return ServerFeatures.getInt(Feature.BOSS_CORES_DROPLETS);
        }
        return 100;
    }

    /**
     * Applies the drop settings to one kill's drops. Works on copies: the DropInfos are shared with the mob's table.
     */
    public static void apply(Set<DropInfo> drops, int mobId) {
        List<DropInfo> changed = new ArrayList<>();
        for (Iterator<DropInfo> it = drops.iterator(); it.hasNext(); ) {
            DropInfo di = it.next();
            if (di.isMoney()) {
                continue;
            }
            int itemId = di.getItemID();
            int chance = di.getChance();
            int min = di.getMinQuant();
            int max = di.getMaxQuant();
            int pct = chancePercent(itemId);
            if (pct != 100) {
                chance = (int) Math.min(MAX_CHANCE, Math.round(chance * pct / 100D));
                if (pct > 0 && di.getChance() > 0) {
                    chance = Math.max(1, chance);
                }
            }
            int amountPct = amountPercent(itemId);
            if (amountPct != 100) {
                min = (int) Math.round(min * amountPct / 100D);
                max = (int) Math.round(max * amountPct / 100D);
                if (amountPct > 0) {
                    min = Math.max(1, min);
                    max = Math.max(min, max);
                }
            }
            if (in(CRA_BOSSES, mobId) && in(CRA_RUNES, itemId)) {
                min = max = ServerFeatures.getInt(Feature.CRA_RUNES);
            }
            if (in(PIECE_BOX_BOSSES, mobId) && isPieceBox(itemId)) {
                min = max = ServerFeatures.getInt(Feature.PIECE_BOXES);
                chance = ServerFeatures.getInt(Feature.PIECE_BOX_CHANCE) * 100;
            }
            if (chance == di.getChance() && min == di.getMinQuant() && max == di.getMaxQuant()) {
                continue;
            }
            it.remove();
            if (chance <= 0 || max <= 0) {
                continue; // set to none
            }
            DropInfo copy = di.deepCopy();
            copy.setChance(chance);
            copy.setMinQuant(min);
            copy.setMaxQuant(max);
            copy.generateNextDrop();
            changed.add(copy);
        }
        drops.addAll(changed);
    }
}
