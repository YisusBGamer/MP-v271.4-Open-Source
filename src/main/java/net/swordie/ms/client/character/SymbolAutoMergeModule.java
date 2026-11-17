package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.SymbolType;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.loaders.StringData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Arcane and Sacred Symbols ({@link GameConstants#AUTO_MERGE_SYMBOLS}): a symbol that comes into the inventory from any
 * source (looted by hand, by pet or by auto-loot, a quest reward, a shop, storage, ...) while the same symbol is
 * equipped is merged into the equipped one right away, which is then levelled up as far as its growth and the
 * player's mesos allow (the same meso cost as levelling it by hand). A copy of a symbol that is already equipped at
 * max level turns to dust. Items given back (a cancelled trade) are left alone.
 * <p>
 * If the equipped symbol is waiting for a level up the player can't pay for, the new symbol stays in the inventory
 * (merging it then would waste its growth, as a symbol's growth can't go past what its next level needs).
 */
public final class SymbolAutoMergeModule {

    /** When the "not enough mesos" message was last sent, per character, so auto-loot doesn't spam it */
    private static final Map<Integer, Long> lastMesoWarning = new ConcurrentHashMap<>();
    private static final long MESO_WARNING_INTERVAL_MS = 60_000;

    private SymbolAutoMergeModule() {
    }

    /** A symbol's older ids (symbol-areas.tsv alias) count as the same symbol. */
    private static int symbolKey(int itemId) {
        return SymbolAreas.key(itemId);
    }

    private static Equip findEquipped(Char chr, int itemId) {
        int key = symbolKey(itemId);
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item instanceof Equip && symbolKey(item.getItemId()) == key) {
                return (Equip) item;
            }
        }
        return null;
    }

    private static int maxLevel(int itemId) {
        return SymbolType.byItemId(itemId) == SymbolType.Arcane ? ItemConstants.MAX_ARCANE_SYMBOL_LEVEL
                : ItemConstants.MAX_AUTH_SYMBOL_LEVEL;
    }

    /** @return whether the item is a symbol that has just been merged or turned to dust (it left the inventory) */
    public static boolean isGone(Char chr, Item item) {
        return item instanceof Equip && ItemConstants.isSymbol(item.getItemId())
                && chr.getEquipInventory().getItemBySlot(item.getBagIndex()) != item;
    }

    private static String name(int itemId) {
        String n = StringData.getItemStringById(itemId);
        return n == null || n.isEmpty() ? "symbol" : n;
    }

    /**
     * Called right after an item went into the inventory (Char#addItemToInventory).
     *
     * @return whether the item is gone (merged or turned to dust)
     */
    public static boolean onObtained(Char chr, Item item) {
        if (item instanceof Equip) {
            noticeIfSlotShut(chr, item.getItemId());
        }
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.AUTO_MERGE_SYMBOLS) || !(item instanceof Equip)
                || !ItemConstants.isSymbol(item.getItemId())) {
            return false;
        }
        Equip looted = (Equip) item;
        if (chr.getEquipInventory().getItemBySlot(looted.getBagIndex()) != looted) {
            return false; // not (or no longer) in the inventory
        }
        Equip worn = findEquipped(chr, looted.getItemId());
        if (worn == null) {
            return false;
        }
        String symbolName = name(worn.getItemId());
        if (worn.getSymbolLevel() >= maxLevel(worn.getItemId())) {
            chr.consumeItem(looted);
            chr.chatMessage(ChatType.SystemNotice, "Your " + symbolName + " is already at max level, so the new one "
                    + "turned to dust.");
            return true;
        }
        int levels = levelUp(chr, worn); // growth it already had (e.g. merged by hand earlier)
        if (worn.getSymbolLevel() < maxLevel(worn.getItemId()) && worn.hasSymbolExpForLevelUp()) {
            // waiting for a level up the player can't pay for: keep the looted one for later
            finish(chr, worn, symbolName, levels);
            warnMesos(chr, worn, symbolName);
            return false;
        }
        if (worn.getSymbolLevel() >= maxLevel(worn.getItemId())) {
            chr.consumeItem(looted);
            finish(chr, worn, symbolName, levels);
            chr.chatMessage(ChatType.SystemNotice, "Your " + symbolName + " is now at max level, so the new one turned "
                    + "to dust.");
            return true;
        }
        chr.consumeItem(looted);
        worn.addSymbolExp(Math.max(1, looted.getTotalSymbolExp()));
        levels += levelUp(chr, worn);
        finish(chr, worn, symbolName, levels);
        if (worn.getSymbolLevel() < maxLevel(worn.getItemId()) && worn.hasSymbolExpForLevelUp()) {
            warnMesos(chr, worn, symbolName);
        }
        return true;
    }

    /**
     * Levels the symbol up while it has the growth for it and the player has the mesos (the same cost as by hand).
     *
     * @return the levels gained
     */
    private static int levelUp(Char chr, Equip symbol) {
        int levels = 0;
        while (symbol.getSymbolLevel() < maxLevel(symbol.getItemId()) && symbol.hasSymbolExpForLevelUp()) {
            int req = ItemConstants.getRequiredSymbolExp(symbol.getSymbolLevel(), symbol.getItemId());
            long cost = ItemConstants.getSymbolMoneyReqByLevel(symbol.getSymbolLevel(), symbol.getItemId());
            if (chr.getMoney() < cost) {
                break;
            }
            chr.deductMoney(cost, ProgressionLog.Sink.FEES);
            int level = symbol.getSymbolLevel() + 1;
            int left = level >= maxLevel(symbol.getItemId()) ? 0 : symbol.getSymbolExp() - req;
            symbol.initSymbolStats(level, left, chr.getJob());
            levels++;
        }
        return levels;
    }

    private static void finish(Char chr, Equip symbol, String symbolName, int levels) {
        symbol.updateToChar(chr);
        if (levels > 0) {
            chr.getEquippedInventory().recalcBaseStats(chr); // Arcane Power and the symbol's stats
            chr.initBaseStats();
            chr.chatMessage(ChatType.SystemNotice, String.format("Your %s absorbed the new symbol and grew "
                            + "to level %d%s.", symbolName, symbol.getSymbolLevel(),
                    symbol.getSymbolLevel() >= maxLevel(symbol.getItemId()) ? " (max level)" : ""));
        }
    }

    // Etc.wz/SymbolInfo.img <type>/addLine: the client opens symbol slot i only when quests 0..i of its list are all
    // complete, and won't equip into a shut slot (it asks the server nothing)
    private static final int[] ARCANE_SLOT_QUESTS = {1465, 34218, 34330, 34478, 34269, 37871};
    private static final String[] ARCANE_AREAS = {"5th job", "Chu Chu Island", "Lachelein", "Arcana", "Morass",
            "Esfera"};
    private static final int[] SACRED_SLOT_QUESTS = {1484, 38120, 38319, 38404, 38653, 38702};
    private static final String[] SACRED_AREAS = {"Cernium", "Hotel Arcus", "Odium", "Shangri-La", "Arteria",
            "Carcion"};
    private static final int[] GRAND_SLOT_QUESTS = {36996, 36873};

    /** Which symbols' shut-slot notice each character has had since the server started */
    private static final Map<Integer, java.util.Set<Integer>> slotNoticed = new ConcurrentHashMap<>();

    /** @return the index of the first quest in the list the character hasn't completed (= open slot count) */
    private static int openSlots(Char chr, int[] quests) {
        int n = 0;
        while (n < quests.length && chr.getQuestManager().hasQuestCompleted(quests[n])) {
            n++;
        }
        return n;
    }

    /** Tells the player once which story opens the slot of a symbol they got whose slot the client keeps shut. */
    private static void noticeIfSlotShut(Char chr, int itemId) {
        if (chr.getQuestManager() == null) {
            return;
        }
        int[] quests;
        String[] areas;
        int slot = SymbolAreas.indexInKind(itemId);
        if (ItemConstants.isArcaneSymbol(itemId)) {
            quests = ARCANE_SLOT_QUESTS;
            areas = ARCANE_AREAS;
        } else if (ItemConstants.isAuthenticForceSymbol(itemId)) {
            quests = SACRED_SLOT_QUESTS;
            areas = SACRED_AREAS;
        } else if (ItemConstants.isGrandSymbol(itemId)) {
            quests = GRAND_SLOT_QUESTS;
            areas = null;
        } else {
            return;
        }
        if (slot < 0 || slot >= quests.length) {
            return;
        }
        int open = openSlots(chr, quests);
        if (slot < open || !slotNoticed.computeIfAbsent(chr.getId(), k -> ConcurrentHashMap.newKeySet()).add(itemId)) {
            return;
        }
        String name = name(itemId);
        String msg;
        if (areas == null) {
            msg = "The " + name + " can't be equipped yet: its slot opens with the " + (slot == 0
                    ? "Tallahart quest The Battle to Come." : "Geardock quest In the Aftermath.");
        } else if (open == 0) {
            msg = "The " + name + " can't be equipped yet: symbol slots open with the " + (quests == ARCANE_SLOT_QUESTS
                    ? "5th job quest Record of Power" : "quest A Divine Power") + ".";
        } else {
            msg = "The " + name + " can't be equipped yet: its slot opens once you've finished the " + areas[open]
                    + " story" + (open < slot ? " and every story after it up to " + areas[slot] : "") + ".";
            if (quests == ARCANE_SLOT_QUESTS) {
                msg += " Spiegelmann in any Arcane River town can skip a story you don't want to play.";
            }
        }
        chr.chatMessage(ChatType.SystemNotice, msg);
    }

    private static void warnMesos(Char chr, Equip symbol, String symbolName) {
        long now = System.currentTimeMillis();
        Long last = lastMesoWarning.get(chr.getId());
        if (last != null && now - last < MESO_WARNING_INTERVAL_MS) {
            return;
        }
        lastMesoWarning.put(chr.getId(), now);
        long cost = ItemConstants.getSymbolMoneyReqByLevel(symbol.getSymbolLevel(), symbol.getItemId());
        chr.chatMessage(ChatType.SystemNotice, String.format("Your %s is ready for level %d, which costs %,d mesos. "
                        + "New symbols stay in your inventory until you have the mesos.", symbolName,
                symbol.getSymbolLevel() + 1, cost));
    }
}
