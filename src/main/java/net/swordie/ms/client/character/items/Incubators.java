package net.swordie.ms.client.character.items;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Incubators: cash 506xxxx items listed in Etc/incubatorInfo.img (Philosopher's Book, Lucky Jewel Box, Golden Apple,
 * Atelier Reliquary, the Pigmy Egg Incubator...). Some hatch an Etc "egg" 4170000 + eggType (usingEggType min-max).
 * The data has no rewards: they come from resources/incubator-rewards.tsv, one reward from each pool per use.
 * <p>
 * Client (v265 RE): USER_CONSUME_CASH_ITEM_USE_REQUEST 331 = 4 0, 4 tick, 2 slot, 4 item, then 4 egg item, 4 egg slot
 * (both 0 unless the Pigmy Egg window picked one). INCUBATOR_RESULT 225 = 1 ok; 0 -> 4 code (0x10 can't use now,
 * 0x5a busy, else inventory full); 1 -> 4 item, 2 count, 4 extra, 4 incubator, 4 incubator slot (for "use again"),
 * 4 item 2, 4 count 2, 1 has item (+ typed item).
 */
public class Incubators {
    private static final Logger log = LogManager.getLogger();
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/incubator-rewards.tsv";
    /** Server-side fever counts per incubator, "itemId=uses;..." */
    public static final int QR = 500786;
    public static final int EGG_BASE = 4170000;
    private static final int CANT_USE = 0x10, INVENTORY_FULL = 1;
    /** Incubators whose missing-egg message the client shows: these need their egg. */
    private static final Set<Integer> NEED_EGG = Set.of(5060002, 5060005, 5060019, 5060023, 5060024, 5060026);

    public record Info(int itemId, List<int[]> eggTypes, int[] slots, int feverStack, int feverMultiplier) {
        boolean takesEgg(int eggId) {
            if (eggId / 10000 != EGG_BASE / 10000) {
                return false;
            }
            int type = eggId % 10000;
            return eggTypes.stream().anyMatch(r -> type >= r[0] && type <= r[1]);
        }
    }

    private static volatile Map<Integer, Info> infos;
    private static volatile Map<Integer, Map<String, List<ItemBoxRewards.Reward>>> pools;

    private Incubators() {
    }

    private static synchronized void ensureLoaded() {
        if (infos != null) {
            return;
        }
        Map<Integer, Info> map = new HashMap<>();
        try {
            WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("incubatorInfo.img");
            if (root == null) {
                log.warn("Etc.wz has no incubatorInfo.img: incubators are off");
            } else {
                for (WzObject<?, ?> node : root) {
                    int id = Integer.parseInt(node.getName());
                    List<int[]> eggs = new ArrayList<>();
                    WzObject<?, ?> types = node.getChild("usingEggType");
                    if (types != null) {
                        for (WzObject<?, ?> t : types) {
                            eggs.add(new int[]{intOf(t.getChild("min")), intOf(t.getChild("max"))});
                        }
                    }
                    int[] slots = new int[4]; // equip, consume, install, etc
                    WzObject<?, ?> confirm = node.getChildByPath("msg/finalConfirmNum");
                    if (confirm != null) {
                        slots = new int[]{intOf(confirm.getChild("equip")), intOf(confirm.getChild("consume")),
                                intOf(confirm.getChild("install")), intOf(confirm.getChild("etc"))};
                    }
                    map.put(id, new Info(id, eggs, slots, intOf(node.getChild("feverStack")),
                            Math.max(1, intOf(node.getChild("feverMultiplier")))));
                }
            }
        } catch (RuntimeException e) {
            log.error("Could not read incubatorInfo.img", e);
        }
        pools = loadPools();
        infos = map;
    }

    private static Map<Integer, Map<String, List<ItemBoxRewards.Reward>>> loadPools() {
        Map<Integer, Map<String, List<ItemBoxRewards.Reward>>> map = new HashMap<>();
        try {
            for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] cols = line.split("\t");
                if (cols.length < 3) {
                    continue;
                }
                List<ItemBoxRewards.Reward> rewards = new ArrayList<>();
                for (String r : cols[2].split(";")) {
                    String[] p = r.split("\\*");
                    int itemId = Integer.parseInt(p[0]);
                    if (ItemData.getItemDeepCopy(itemId) == null) {
                        log.warn("incubator-rewards.tsv: item " + itemId + " is not in the server data, skipped");
                        continue;
                    }
                    rewards.add(new ItemBoxRewards.Reward(itemId, Integer.parseInt(p[1]), Integer.parseInt(p[2]),
                            "item", p.length > 3 ? Double.parseDouble(p[3]) : 1, 0, ""));
                }
                if (!rewards.isEmpty()) {
                    map.computeIfAbsent(Integer.parseInt(cols[0]), k -> new LinkedHashMap<>()).put(cols[1], rewards);
                }
            }
        } catch (IOException | RuntimeException e) {
            log.error("Could not read " + FILE, e);
        }
        return map;
    }

    private static int intOf(Object node) {
        if (node instanceof WzProperty<?> p && p.getValue() != null) {
            Object v = p.getValue();
            return v instanceof Number n ? n.intValue() : Integer.parseInt(v.toString().trim());
        }
        return 0;
    }

    public static boolean isIncubator(int itemId) {
        ensureLoaded();
        return infos.containsKey(itemId);
    }

    /** Cash item use of an incubator; the rest of the request is the egg item and slot. */
    public static void use(Char chr, Item incubator, short slot, InPacket inPacket) {
        ensureLoaded();
        int incubatorId = incubator.getItemId();
        Info info = infos.get(incubatorId);
        int eggId = inPacket.getUnreadAmount() >= 8 ? inPacket.decodeInt() : 0;
        int eggSlot = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        Map<String, List<ItemBoxRewards.Reward>> table = pools.get(incubatorId);
        if (info == null || table == null) {
            fail(chr, CANT_USE);
            return;
        }
        Item egg = findEgg(chr, info, eggId, eggSlot);
        if (egg == null && NEED_EGG.contains(incubatorId)) {
            fail(chr, CANT_USE);
            return;
        }
        if (!hasSlots(chr, info.slots())) {
            fail(chr, INVENTORY_FULL);
            return;
        }
        int uses = feverUses(chr, incubatorId) + 1;
        boolean fever = info.feverStack() > 0 && uses % info.feverStack() == 0 && table.containsKey("fever");
        List<Item> items = new ArrayList<>();
        for (Map.Entry<String, List<ItemBoxRewards.Reward>> e : table.entrySet()) {
            String pool = e.getKey();
            if (pool.equals("fever") || (fever && pool.equals("1"))) {
                continue;
            }
            items.add(toItem(ItemBoxRewards.pick(e.getValue()), 1));
        }
        if (fever) {
            items.add(0, toItem(ItemBoxRewards.pick(table.get("fever")), info.feverMultiplier()));
        }
        items.removeIf(Objects::isNull);
        if (items.isEmpty()) {
            fail(chr, CANT_USE);
            return;
        }
        if (!chr.canHold(new ArrayList<>(items))) {
            fail(chr, INVENTORY_FULL);
            return;
        }
        chr.consumeItem(incubator, 1);
        if (egg != null) {
            chr.consumeItem(egg, 1);
        }
        if (info.feverStack() > 0) {
            setFeverUses(chr, incubatorId, uses % info.feverStack());
        }
        Item first = items.get(0);
        Item second = items.size() > 1 ? items.get(1) : null;
        int firstCount = ItemConstants.isEquip(first.getItemId()) ? 1 : first.getQuantity();
        int secondCount = second == null ? 0 : ItemConstants.isEquip(second.getItemId()) ? 1 : second.getQuantity();
        for (Item item : items) {
            chr.addItemToInventory(item);
        }
        chr.write(result(first.getItemId(), firstCount, incubatorId, slot,
                second == null ? 0 : second.getItemId(), secondCount));
        chr.dispose();
    }

    private static Item findEgg(Char chr, Info info, int eggId, int eggSlot) {
        Inventory etc = chr.getInventoryByType(InvType.ETC);
        if (eggId > 0 && info.takesEgg(eggId)) {
            Item item = etc.getItemBySlot(eggSlot);
            if (item != null && item.getItemId() == eggId && item.getQuantity() > 0) {
                return item;
            }
            item = etc.getItemByItemID(eggId);
            if (item != null) {
                return item;
            }
        }
        if (info.eggTypes().isEmpty()) {
            return null;
        }
        if (!NEED_EGG.contains(info.itemId())) {
            return null;
        }
        for (Item item : etc.getItems()) {
            if (info.takesEgg(item.getItemId()) && item.getQuantity() > 0) {
                return item;
            }
        }
        return null;
    }

    private static boolean hasSlots(Char chr, int[] slots) {
        InvType[] types = {InvType.EQUIP, InvType.CONSUME, InvType.INSTALL, InvType.ETC};
        for (int i = 0; i < types.length; i++) {
            if (slots[i] > 0 && chr.getInventoryByType(types[i]).getEmptySlots() < slots[i]) {
                return false;
            }
        }
        return true;
    }

    private static Item toItem(ItemBoxRewards.Reward r, int multiplier) {
        Item item = ItemData.getItemDeepCopy(r.itemId());
        if (item == null) {
            return null;
        }
        if (!ItemConstants.isEquip(r.itemId())) {
            item.setQuantity(Math.max(1, r.count()) * multiplier);
        }
        if (r.days() > 0) {
            item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusDays(r.days())));
        }
        return item;
    }

    private static Map<String, String> feverMap(Char chr) {
        Map<String, String> m = new HashMap<>();
        Quest q = chr.getQuestManager() == null ? null : chr.getQuestManager().getQuestById(QR);
        if (q != null && q.getQRValue() != null) {
            for (String kv : q.getQRValue().split(";")) {
                String[] p = kv.split("=", 2);
                if (p.length == 2) {
                    m.put(p[0], p[1]);
                }
            }
        }
        return m;
    }

    private static int feverUses(Char chr, int incubatorId) {
        try {
            return Integer.parseInt(feverMap(chr).getOrDefault(String.valueOf(incubatorId), "0"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void setFeverUses(Char chr, int incubatorId, int uses) {
        if (chr.getQuestManager() == null) {
            return;
        }
        Map<String, String> m = feverMap(chr);
        m.put(String.valueOf(incubatorId), String.valueOf(uses));
        Quest q = chr.getQuestManager().getQuestById(QR);
        if (q == null) {
            q = QuestData.createQuestFromId(QR);
            chr.getQuestManager().addQuest(q);
        }
        StringJoiner sj = new StringJoiner(";");
        m.forEach((k, v) -> sj.add(k + "=" + v));
        q.setQrValue(sj.toString());
    }

    private static void fail(Char chr, int code) {
        OutPacket out = new OutPacket(OutHeader.INCUBATOR_RESULT);
        out.encodeByte(0);
        out.encodeInt(code);
        chr.write(out);
        chr.dispose();
    }

    public static OutPacket result(int itemId, int count, int incubatorId, int incubatorSlot, int item2, int count2) {
        OutPacket out = new OutPacket(OutHeader.INCUBATOR_RESULT);
        out.encodeByte(1);
        out.encodeInt(itemId);
        out.encodeShort(count);
        out.encodeInt(0);
        out.encodeInt(incubatorId);
        out.encodeInt(incubatorSlot);
        out.encodeInt(item2);
        out.encodeInt(count2);
        out.encodeByte(0); // no item: the client makes one from the id
        return out;
    }
}
