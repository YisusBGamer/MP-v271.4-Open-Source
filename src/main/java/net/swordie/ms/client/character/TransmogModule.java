package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.loaders.StringData;

import java.util.*;

/**
 * Transmogrification (@transmog, scripts/npc/transmog.py): an equipped item takes the look of another equip of the
 * same kind that the player owns (Equip inventory or storage), keeping its own stats. Uses the game's own Fusion
 * Anvil field (option 6 of an equip), so the client draws it and other players see it; it's saved with the item.
 * <p>
 * Same kind = the same item category (itemId / 10000): a hat with a hat, a one-handed sword with a one-handed sword,
 * a top with a top (overalls only with overalls). That's how the client draws Fusion Anvil looks.
 */
public class TransmogModule {

    /** Item categories (itemId / 10000) that are visible on the character. Weapons are added separately. */
    private static final Set<Integer> VISIBLE_CATEGORIES = Set.of(
            100, // hats
            101, // face accessories
            102, // eye accessories
            103, // earrings
            104, // tops
            105, // overalls
            106, // bottoms
            107, // shoes
            108, // gloves
            109, // shields
            110  // capes
    );

    /** A look the player can pick: the item it comes from and where it is. */
    public static class Look {
        private final int itemId;
        private final boolean inStorage;

        public Look(int itemId, boolean inStorage) {
            this.itemId = itemId;
            this.inStorage = inStorage;
        }

        public int getItemId() {
            return itemId;
        }

        public boolean isInStorage() {
            return inStorage;
        }

        public String getName() {
            String name = StringData.getItemStringById(itemId);
            return name == null ? String.valueOf(itemId) : name;
        }
    }

    private static int category(int itemId) {
        return itemId / 10000;
    }

    public static boolean canHaveLook(Equip equip) {
        if (equip == null) {
            return false;
        }
        int itemId = equip.getItemId();
        return VISIBLE_CATEGORIES.contains(category(itemId)) || ItemConstants.isWeapon(itemId);
    }

    /**
     * @return the item whose look the equip shows (its own id when it has no transmog)
     */
    public static int getLookItemId(Equip equip) {
        return equip.getAnvilId() <= 0 ? equip.getItemId()
                : ItemConstants.getItemIdFromAnvilIdAndOriginId(equip.getItemId(), equip.getAnvilId());
    }

    public static boolean hasLook(Equip equip) {
        return equip.getAnvilId() > 0;
    }

    /**
     * @return the equipped items that can be transmogged, by slot
     */
    public static List<Equip> getEquippedItems(Char chr) {
        List<Equip> res = new ArrayList<>();
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item instanceof Equip && !item.isCash() && canHaveLook((Equip) item)) {
                res.add((Equip) item);
            }
        }
        res.sort(Comparator.comparingInt(Item::getBagIndex));
        return res;
    }

    /**
     * @return the looks available for an equipped item: other equips of the same kind in the Equip inventory and in
     * storage (cash items too), one per item id, inventory first
     */
    public static List<Look> getLooks(Char chr, Equip target) {
        Map<Integer, Look> looks = new LinkedHashMap<>();
        int cat = category(target.getItemId());
        int current = getLookItemId(target);
        for (Item item : chr.getEquipInventory().getItems()) {
            addLook(looks, item, cat, current, false);
        }
        if (net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.TRANSMOG_FROM_STORAGE) && chr.getAccount() != null && chr.getAccount().getTrunk() != null) {
            for (Item item : chr.getAccount().getTrunk().getItems()) {
                addLook(looks, item, cat, current, true);
            }
        }
        List<Look> res = new ArrayList<>(looks.values());
        res.sort(Comparator.comparing(Look::isInStorage).thenComparing(Look::getName));
        return res;
    }

    private static void addLook(Map<Integer, Look> looks, Item item, int cat, int current, boolean storage) {
        if (!(item instanceof Equip)) {
            return;
        }
        int id = item.getItemId();
        if (category(id) != cat || id == current || looks.containsKey(id)) {
            return;
        }
        looks.put(id, new Look(id, storage));
    }

    /**
     * Gives an equipped item the look of another item of the same kind the player owns.
     *
     * @return a message for the player
     */
    public static String applyLook(Char chr, int bagIndex, int lookItemId) {
        Equip target = getEquipped(chr, bagIndex);
        if (target == null) {
            return "That item isn't equipped anymore.";
        }
        boolean owned = false;
        for (Look look : getLooks(chr, target)) {
            if (look.getItemId() == lookItemId) {
                owned = true;
                break;
            }
        }
        if (!owned) {
            return "You need that item in your Equip inventory or storage to copy its look.";
        }
        setAnvil(chr, target, lookItemId == target.getItemId() ? 0 : lookItemId % 10000);
        if (lookItemId != target.getItemId()) {
            net.swordie.ms.client.character.achievement.MapleAchievements.onFusion(chr);
        }
        return "Your item now looks like #b#t" + lookItemId + "##k.";
    }

    /**
     * Gives an equipped item its own look back.
     */
    public static String resetLook(Char chr, int bagIndex) {
        Equip target = getEquipped(chr, bagIndex);
        if (target == null) {
            return "That item isn't equipped anymore.";
        }
        if (!hasLook(target)) {
            return "That item already has its own look.";
        }
        setAnvil(chr, target, 0);
        return "#b#t" + target.getItemId() + "##k has its own look again.";
    }

    /**
     * Gives every equipped item its own look back.
     *
     * @return how many items changed
     */
    public static int resetAll(Char chr) {
        int count = 0;
        for (Equip equip : getEquippedItems(chr)) {
            if (hasLook(equip)) {
                setAnvil(chr, equip, 0);
                count++;
            }
        }
        return count;
    }

    private static Equip getEquipped(Char chr, int bagIndex) {
        Item item = chr.getEquippedInventory().getItemBySlot(bagIndex);
        return item instanceof Equip && !item.isCash() && canHaveLook((Equip) item) ? (Equip) item : null;
    }

    private static void setAnvil(Char chr, Equip equip, int anvilId) {
        int pos = -Math.abs(equip.getBagIndex());
        // take the old look off the character, change it, and put the new one on (also shown to other players)
        chr.unequipAvatarLookLogic(equip, pos);
        equip.getOptions().set(6, anvilId);
        chr.equipAvatarLookLogic(equip, pos);
        equip.updateToChar(chr);
    }
}
