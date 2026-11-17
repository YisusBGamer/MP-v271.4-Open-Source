package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipSpecialAttribute;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Equipment presets (v265): three pages in the Equip window, switched with the Apply button.
 * <ul>
 *     <li>USER_EQUIPMENT_PRESET_CHANGE_REQUEST 1116 (0x1453a3eb0, Apply = button 3000 of 0x144011630): int tick, byte
 *     preset 0..2. The client checks 0x1404896f0 first and sets the exclusive flag.</li>
 *     <li>A preset's items that aren't worn sit at equip position -(3000 + 100 * preset + slot); slot 1..65 except the
 *     pet slots 14/30/38 and Zero's 10/11 (0x1406543d0, 0x140520440). CharacterData sends them as the 3000/3100/3200
 *     lists of the equip block (range table 0x146861be0), after the applied preset byte (inventory+0x30).</li>
 *     <li>Preset 1 is the base: a slot that is empty in Preset 2/3 keeps the Preset 1 item on (0x1404894f0). While
 *     another preset is applied such an item carries item state bit 20 (vtable +0x318 = 0x140674a00), which makes
 *     the window show it as "equipped in Preset 1" (0x144013950). Bits 21/22 would leave it off in Preset 2/3; the
 *     client never asks for them, so they stay 0.</li>
 *     <li>Answer: INVENTORY_OPERATION 144 with the exclusive byte: the moves, the items whose bit 20 changed again,
 *     then op 6 with the preset in the position short (0x14546a780 case 6 stores it at inventory+0x30).</li>
 * </ul>
 */
public class EquipPresetModule {

    private static final Logger log = LogManager.getLogger();

    public static final int PRESETS = 3;
    public static final int SLOTS = 65;
    public static final int CARRIED = 0x100000;

    private static final int OP_ADD = 0;
    private static final int OP_MOVE = 2;
    private static final int OP_PRESET = 6;

    private static final class State {
        volatile int applied;
        final Set<Long> carried = ConcurrentHashMap.newKeySet();
    }

    private record Op(int type, int pos, int newPos, Item item) {
    }

    private static final Map<Integer, State> states = new ConcurrentHashMap<>();

    public static int base(int preset) {
        return 3000 + 100 * preset;
    }

    /** Bag index (or client position) of a preset storage slot. */
    public static boolean isStoragePos(int pos) {
        pos = Math.abs(pos);
        if (pos <= 3000 || pos >= base(PRESETS)) {
            return false;
        }
        int slot = (pos - 3000) % 100;
        return slot >= 1 && slot <= SLOTS;
    }

    public static int presetOf(int pos) {
        return (Math.abs(pos) - 3000) / 100;
    }

    public static int slotOf(int pos) {
        return (Math.abs(pos) - 3000) % 100;
    }

    public static boolean isPresetSlot(Char chr, int slot) {
        if (slot < 1 || slot > SLOTS || slot == 14 || slot == 30 || slot == 38) {
            return false;
        }
        return !((slot == 10 || slot == 11) && JobConstants.isZero(chr.getJob()));
    }

    private static State state(Char chr) {
        return states.computeIfAbsent(chr.getId(), id -> {
            State s = new State();
            List<Map<String, Object>> rows;
            try {
                rows = DatabaseManager.queryRows(
                        "SELECT applied AS a, carried AS c FROM char_equip_presets WHERE charid = ?", id);
            } catch (Exception e) {
                log.error("Couldn't load the equipment presets of character " + id, e);
                rows = List.of();
            }
            for (Map<String, Object> row : rows) {
                s.applied = Math.max(0, Math.min(PRESETS - 1, ((Number) row.get("a")).intValue()));
                Object c = row.get("c");
                if (c != null) {
                    for (String part : c.toString().split(",")) {
                        if (!part.isBlank()) {
                            try {
                                s.carried.add(Long.parseLong(part.trim()));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                }
            }
            return s;
        });
    }

    private static void save(Char chr, State s) {
        StringBuilder sb = new StringBuilder();
        for (long id : s.carried) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(id);
        }
        DatabaseManager.executeQuery("INSERT INTO char_equip_presets (charid, applied, carried) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE applied = VALUES(applied), carried = VALUES(carried)",
                chr.getId(), s.applied, sb.toString());
    }

    public static int applied(Char chr) {
        return chr == null || chr.getId() == 0 ? 0 : state(chr).applied;
    }

    /** The CharacterData byte; also puts bit 20 on the worn Preset 1 items (items are new objects after a login). */
    public static int appliedForEncode(Char chr) {
        if (chr == null || chr.getId() == 0) {
            return 0;
        }
        try {
            State s = state(chr);
            refreshFlags(chr, s, null);
            return s.applied;
        } catch (Exception e) {
            log.error("Equipment presets of " + chr.getName() + " couldn't be prepared", e);
            return 0;
        }
    }

    private static Item worn(Inventory eqd, int slot) {
        return Util.findWithPred(eqd.getItems(), i -> i.getBagIndex() == slot);
    }

    private static boolean isCarried(State s, Item item) {
        return item != null && s.applied != 0 && s.carried.contains(item.getId());
    }

    /**
     * Sets bit 20 where it belongs and drops carried ids that aren't worn any more.
     * @param changed gets the items whose bit changed (may be null)
     */
    private static void refreshFlags(Char chr, State s, List<Item> changed) {
        Inventory eqd = chr.getEquippedInventory();
        Set<Long> wornIds = new HashSet<>();
        for (Item item : eqd.getItems()) {
            if (item instanceof Equip eq && item.getBagIndex() >= 1 && item.getBagIndex() <= SLOTS) {
                wornIds.add(item.getId());
                setFlag(eq, s.applied != 0 && s.carried.contains(item.getId()), changed);
            }
        }
        for (Item item : eqd.getPresetItems()) {
            if (item instanceof Equip eq) {
                setFlag(eq, false, changed);
            }
        }
        if (s.carried.retainAll(wornIds)) {
            save(chr, s);
        }
    }

    private static void setFlag(Equip eq, boolean carried, List<Item> changed) {
        int flags = carried ? CARRIED : 0;
        if (eq.getPresetFlags() != flags) {
            eq.setPresetFlags(flags);
            if (changed != null) {
                changed.add(eq);
            }
        }
    }

    private static OutPacket operations(List<Op> ops) {
        OutPacket out = new OutPacket(OutHeader.INVENTORY_OPERATION);
        out.encodeByte(true); // exclusive request answered
        out.encodeByte(0);
        out.encodeInt(ops.size());
        out.encodeByte(0);
        boolean equipMove = false;
        for (Op op : ops) {
            out.encodeByte(op.type());
            out.encodeByte(InvType.EQUIP.getVal());
            out.encodeShort(op.pos());
            if (op.type() == OP_MOVE) {
                out.encodeShort(op.newPos());
                equipMove |= op.pos() < 0 || op.newPos() < 0;
            } else if (op.type() == OP_ADD) {
                out.encode(op.item());
            }
        }
        if (equipMove) {
            out.encodeByte(1); // read when an equip slot changed (0x14546a780)
        }
        return out;
    }

    /** Releases the client's exclusive flag without changing anything. */
    public static OutPacket release() {
        return operations(List.of());
    }

    private static boolean fits(Char chr, Item item, int slot) {
        if (!(item instanceof Equip eq) || item.isCash() || !isPresetSlot(chr, slot)
                || eq.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            return false;
        }
        int gender = chr.getAvatarData().getCharacterStat().getGender();
        return ItemConstants.getBodyPartArrayFromItem(item.getItemId(), gender).contains(slot);
    }

    /** The overall/bottom and shield/weapon pairs a preset may not hold together. */
    private static String conflict(Inventory eqd, int preset, Item item, int slot) {
        int id = item.getItemId();
        if (slot == 5 && ItemConstants.isOverall(id)) {
            if (eqd.getPresetItemBySlot(base(preset) + 6) != null) {
                return "Take the bottom out of this preset first.";
            }
        } else if (slot == 6) {
            Item top = eqd.getPresetItemBySlot(base(preset) + 5);
            if (top != null && ItemConstants.isOverall(top.getItemId())) {
                return "Take the overall out of this preset first.";
            }
        } else if (slot == 10 && ItemConstants.isShield(id)) {
            Item wep = eqd.getPresetItemBySlot(base(preset) + 11);
            if (wep != null && !ItemConstants.canWearShield(ItemConstants.getWeaponType(wep.getItemId()))) {
                return "This preset's weapon can't be used with a shield.";
            }
        } else if (slot == 11) {
            Item shield = eqd.getPresetItemBySlot(base(preset) + 10);
            if (shield != null && ItemConstants.isShield(shield.getItemId())
                    && !ItemConstants.canWearShield(ItemConstants.getWeaponType(id))) {
                return "That weapon can't be used with this preset's shield.";
            }
        }
        return null;
    }

    private static boolean refuse(Char chr, String msg) {
        if (msg != null) {
            chr.chatMessage(msg);
        }
        chr.write(release());
        chr.dispose();
        return true;
    }

    // ------------------------------------------------------------------------------------------ dragging

    /**
     * USER_CHANGE_SLOT_POSITION_REQUEST with a preset storage position (dragging on a preset page that isn't
     * applied). @return false if neither position is preset storage (the normal move handles it)
     */
    public static boolean handleMove(Char chr, InvType invType, short oldPos, short newPos) {
        boolean fromStore = oldPos < 0 && isStoragePos(oldPos);
        boolean toStore = newPos < 0 && isStoragePos(newPos);
        if (!fromStore && !toStore) {
            return false;
        }
        synchronized (chr.getEquippedInventory()) {
            return move(chr, invType, oldPos, newPos, fromStore, toStore);
        }
    }

    private static boolean move(Char chr, InvType invType, short oldPos, short newPos, boolean fromStore,
                                boolean toStore) {
        State s = state(chr);
        Inventory eqd = chr.getEquippedInventory();
        Inventory inv = chr.getEquipInventory();
        if (invType != InvType.EQUIP) {
            return refuse(chr, null);
        }
        if ((fromStore && presetOf(oldPos) == s.applied) || (toStore && presetOf(newPos) == s.applied)) {
            return refuse(chr, "Switch to the Preset page applied to the character and try again.");
        }
        if (fromStore && toStore) {
            Item a = eqd.getPresetItemBySlot(oldPos);
            Item b = eqd.getPresetItemBySlot(newPos);
            if (a == null || !fits(chr, a, slotOf(newPos)) || (b != null && !fits(chr, b, slotOf(oldPos)))) {
                return refuse(chr, a == null ? null : "That item doesn't go in that slot.");
            }
            a.setBagIndex(-newPos);
            a.setForceSaveNextFlush(true);
            if (b != null) {
                b.setBagIndex(-oldPos);
                b.setForceSaveNextFlush(true);
            }
        } else if (toStore) {
            if (oldPos <= 0) {
                return refuse(chr, "Take the item off first.");
            }
            Item item = inv.getItemBySlot(oldPos);
            int slot = slotOf(newPos);
            if (item == null || item.isInBag() || !fits(chr, item, slot)) {
                return refuse(chr, item == null ? null : "That item doesn't go in that slot.");
            }
            String why = conflict(eqd, presetOf(newPos), item, slot);
            if (why != null) {
                return refuse(chr, why);
            }
            Item old = eqd.getPresetItemBySlot(newPos);
            inv.removeItem(item, false);
            item.setBagIndex(-newPos);
            eqd.addPresetItem(item);
            if (old != null) {
                eqd.removePresetItem(old);
                old.setBagIndex(oldPos);
                inv.addItem(old);
            }
        } else {
            Item item = eqd.getPresetItemBySlot(oldPos);
            if (item == null || newPos <= 0 || newPos > inv.getSlots()) {
                return refuse(chr, null);
            }
            Item swap = inv.getItemBySlot(newPos);
            int slot = slotOf(oldPos);
            if (swap != null) {
                if (swap.isInBag() || !fits(chr, swap, slot)) {
                    return refuse(chr, "That item doesn't go in that slot.");
                }
                eqd.removePresetItem(item);
                String why = conflict(eqd, presetOf(oldPos), swap, slot);
                if (why != null) {
                    eqd.addPresetItem(item);
                    return refuse(chr, why);
                }
                inv.removeItem(swap, false);
                swap.setBagIndex(-oldPos);
                eqd.addPresetItem(swap);
            } else {
                eqd.removePresetItem(item);
            }
            item.setBagIndex(newPos);
            inv.addItem(item);
        }
        chr.write(operations(List.of(new Op(OP_MOVE, oldPos, newPos, null))));
        return true;
    }

    /**
     * Before a normal move on the worn slots. A Preset 1 item worn under another preset stays in Preset 1 when
     * something else is put on over it (it goes to the Preset 1 page), and stops being one when taken off.
     * @return the item that will be swapped out of newPos (null if it went to the Preset 1 page)
     */
    public static Item beforeMove(Char chr, InvType invType, short oldPos, short newPos, Item item, Item swapItem) {
        if (chr.getId() == 0 || !invType.isEquipType()) {
            return swapItem;
        }
        State s = state(chr);
        if (s.applied == 0) {
            return swapItem;
        }
        if (oldPos < 0 && newPos > 0 && s.carried.remove(item.getId())) {
            if (item instanceof Equip eq) {
                eq.setPresetFlags(0); // the move re-sends the item
            }
            save(chr, s);
        }
        int slot = -newPos;
        if (newPos < 0 && oldPos > 0 && isCarried(s, swapItem) && isPresetSlot(chr, slot)
                && item instanceof Equip eq && !item.isCash()
                && chr.getEquipRequirementFailure(eq, swapItem) == null
                && ItemConstants.getBodyPartArrayFromItem(item.getItemId(),
                        chr.getAvatarData().getCharacterStat().getGender()).contains(slot)
                && chr.getEquippedInventory().getPresetItemBySlot(base(0) + slot) == null) {
            synchronized (chr.getEquippedInventory()) {
                s.carried.remove(swapItem.getId());
                ((Equip) swapItem).setPresetFlags(0);
                toStorage(chr, swapItem, base(0) + slot);
                save(chr, s);
            }
            chr.write(operations(List.of(new Op(OP_MOVE, -slot, -(base(0) + slot), null),
                    new Op(OP_ADD, -(base(0) + slot), 0, swapItem))));
            return null;
        }
        return swapItem;
    }

    // ------------------------------------------------------------------------------------------ applying

    private static void withRoom(Char chr, Runnable r) {
        Inventory inv = chr.getEquipInventory();
        int slots = inv.getSlots();
        inv.setSlots(slots + 1);
        try {
            r.run();
        } finally {
            inv.setSlots(slots);
        }
    }

    /** Takes a worn item off into preset storage (with everything unequipping does). */
    private static void toStorage(Char chr, Item item, int storagePos) {
        withRoom(chr, () -> {
            chr.unequip(item, true);
            chr.getEquipInventory().removeItem(item, false);
        });
        item.setBagIndex(storagePos);
        chr.getEquippedInventory().addPresetItem(item);
    }

    /** Puts a stored item on. @return false if it couldn't be (it stays stored) */
    private static boolean fromStorage(Char chr, Item item, int slot) {
        Inventory eqd = chr.getEquippedInventory();
        int storagePos = item.getBagIndex();
        eqd.removePresetItem(item);
        boolean[] ok = {false};
        withRoom(chr, () -> {
            chr.getEquipInventory().addItem(item);
            ok[0] = chr.equip(item, -slot);
            if (!ok[0]) {
                chr.getEquipInventory().removeItem(item, false);
            }
        });
        if (!ok[0]) {
            item.setBagIndex(storagePos);
            eqd.addPresetItem(item);
        }
        return ok[0];
    }

    /** USER_EQUIPMENT_PRESET_CHANGE_REQUEST 1116 */
    public static void handleChange(Char chr, InPacket in) {
        in.decodeInt(); // tick
        int target = in.decodeByte();
        State s = state(chr);
        if (target < 0 || target >= PRESETS || target == s.applied || chr.getHP() <= 0) {
            refuse(chr, null);
            return;
        }
        try {
            synchronized (chr.getEquippedInventory()) {
                apply(chr, s, target);
            }
        } catch (Exception e) {
            log.error("Equipment preset change failed for " + chr.getName(), e);
            refuse(chr, null);
        }
    }

    private static void apply(Char chr, State s, int t) {
        Inventory eqd = chr.getEquippedInventory();
        int cur = s.applied;
        Item[] w = new Item[SLOTS + 1];
        Item[] p1 = new Item[SLOTS + 1];
        Item[] next = new Item[SLOTS + 1];
        boolean[] carriedNext = new boolean[SLOTS + 1];
        for (int slot = 1; slot <= SLOTS; slot++) {
            if (!isPresetSlot(chr, slot)) {
                continue;
            }
            Item worn = worn(eqd, slot);
            boolean carried = isCarried(s, worn);
            w[slot] = worn;
            p1[slot] = cur == 0 || carried ? worn : eqd.getPresetItemBySlot(base(0) + slot);
            Item own = t == 0 ? null : eqd.getPresetItemBySlot(base(t) + slot);
            if (own instanceof Equip eq) {
                String failure = chr.getEquipRequirementFailure(eq, null);
                if (failure != null) {
                    refuse(chr, "Preset " + (t + 1) + " can't be applied: " + failure);
                    return;
                }
            }
            next[slot] = own != null ? own : p1[slot];
            carriedNext[slot] = t != 0 && next[slot] != null && next[slot] == p1[slot];
        }
        // pairs that can't be worn together: the Preset 1 item gives way, else the bottom/shield stays stored
        if (next[5] != null && next[6] != null && ItemConstants.isOverall(next[5].getItemId())) {
            if (carriedNext[5]) {
                next[5] = null;
            } else {
                next[6] = null;
            }
        }
        if (next[10] != null && next[11] != null && ItemConstants.isShield(next[10].getItemId())
                && !ItemConstants.canWearShield(ItemConstants.getWeaponType(next[11].getItemId()))) {
            if (carriedNext[11] && !carriedNext[10]) {
                next[11] = null;
            } else {
                next[10] = null;
            }
        }

        List<Op> ops = new ArrayList<>();
        for (int slot = 1; slot <= SLOTS; slot++) {
            Item worn = w[slot];
            if (worn != null && worn != next[slot]) {
                int home = worn == p1[slot] ? base(0) + slot : base(cur) + slot;
                if (eqd.getPresetItemBySlot(home) != null) {
                    // only a hand-made packet could have filled it; leave this slot alone
                    next[slot] = worn;
                    carriedNext[slot] = t != 0 && worn == p1[slot];
                    continue;
                }
                toStorage(chr, worn, home);
                ops.add(new Op(OP_MOVE, -slot, -home, null));
            }
            Item in = next[slot];
            if (in != null && in != worn) {
                int from = in.getBagIndex();
                if (fromStorage(chr, in, slot)) {
                    ops.add(new Op(OP_MOVE, -from, -slot, null));
                } else {
                    next[slot] = null;
                    carriedNext[slot] = false;
                }
            }
        }

        s.applied = t;
        for (int slot = 1; slot <= SLOTS; slot++) {
            if (next[slot] != null) {
                if (carriedNext[slot]) {
                    s.carried.add(next[slot].getId());
                } else {
                    s.carried.remove(next[slot].getId());
                }
            }
            if (p1[slot] != null && p1[slot] != next[slot]) {
                s.carried.remove(p1[slot].getId());
            }
        }
        List<Item> changed = new ArrayList<>();
        refreshFlags(chr, s, changed);
        save(chr, s);
        for (Item item : changed) {
            int id = item.getItemId() / 10000;
            if (id == 150 || id == 151) {
                continue; // an Add of these makes the client read the movement byte
            }
            ops.add(new Op(OP_ADD, -item.getBagIndex(), 0, item));
        }
        ops.add(new Op(OP_PRESET, t, 0, null));

        eqd.recalcBaseStats(chr);
        chr.capHpMpToMax();
        chr.setBulletIDForAttack(chr.calculateBulletIDForAttack());
        chr.write(operations(ops));
    }

    /** USER_OUTFIT_PRESET_CHANGE_REQUEST 1114 / USER_OUTFIT_PRESET_TOGGLE_REQUEST 1115 */
    public static void handleOutfit(Char chr, InPacket in, boolean change) {
        if (change) {
            in.decodeInt();
            chr.chatMessage("Outfit presets aren't available yet.");
        } else {
            in.decodeByte();
        }
        chr.write(release());
    }
}
