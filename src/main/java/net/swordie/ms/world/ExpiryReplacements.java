package net.swordie.ms.world;

import java.util.function.Supplier;
import java.util.HashMap;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.World;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Timed items that turn into another item when their time is up (Item.wz info/replace: itemid, msg, period in
 * minutes for the new item), needed by event quests such as MMF's Crumpled Flyer or the Moon Festival's Moonlight
 * Bottle. The table is every replace entry of the v232 Item.wz.
 * <p>
 * Checked every minute for the players online ({@link SeasonalEvents#tick}) and on the regular expiry check
 * (Char#checkAndRemoveExpiredItems).
 */
public final class ExpiryReplacements {

    private static final Logger log = LogManager.getLogger(ExpiryReplacements.class);

    public record Replacement(int itemId, int periodMinutes, String msg) {
    }

    /** resources/data/expiry-replacements.tsv: item, becomes, periodMinutes, msg */
    private static final Supplier<Map<Integer, Replacement>> TABLE = DataTable.cached("expiry-replacements", t -> {
        Map<Integer, Replacement> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("item"), new Replacement(r.i("becomes"), r.i("periodMinutes"), r.str("msg")));
        }
        return m;
    });

    private static Map<Integer, Replacement> table() {
        return TABLE.get();
    }

    private ExpiryReplacements() {
    }

    public static Replacement get(int itemId) {
        return table().get(itemId);
    }

    public static boolean hasReplacement(int itemId) {
        return table().containsKey(itemId);
    }

    /** An expired item was just removed: gives the item it turns into (if any). @return whether it was replaced */
    public static boolean onExpired(Char chr, Item item) {
        Replacement r = item == null ? null : table().get(item.getItemId());
        if (r == null) {
            return false;
        }
        try {
            Item n = ItemData.getItemDeepCopy(r.itemId());
            if (n == null) {
                return false;
            }
            if (r.periodMinutes() > 0) {
                n.setDateExpire(FileTime.fromLong(System.currentTimeMillis() + r.periodMinutes() * 60_000L));
            }
            chr.addItemToInventory(n);
            if (r.msg() != null && !r.msg().isEmpty()) {
                chr.chatMessage(ChatType.Notice2, r.msg());
            }
            return true;
        } catch (Exception e) {
            log.error("ExpiryReplacements: " + item.getItemId() + " -> " + r.itemId() + " for " + chr.getName(), e);
            return false;
        }
    }

    /** Replaces this player's timed items whose time is up (only items in the table). */
    public static void sweep(Char chr) {
        if (chr == null || chr.getInventories() == null || chr.getInventories().contains(null)) {
            return;
        }
        List<Item> due = new ArrayList<>();
        for (Inventory inv : chr.getInventories()) {
            for (Item item : inv.getItems()) {
                if (item != null && table().containsKey(item.getItemId()) && item.getDateExpire() != null
                        && item.getDateExpire().isExpired()) {
                    due.add(item);
                }
            }
        }
        if (due.isEmpty()) {
            return;
        }
        List<Integer> ids = new ArrayList<>();
        for (Item item : due) {
            ids.add(item.getItemId());
        }
        chr.write(WvsContext.message(MessagePacket.generalItemExpireMessage(ids)));
        for (Item item : due) {
            chr.consumeItemFull(item);
            onExpired(chr, item);
        }
    }

    /** Every minute: all players online. */
    public static void sweepOnline() {
        for (World world : net.swordie.ms.Server.getInstance().getWorlds()) {
            for (Channel channel : world.getChannels()) {
                for (Char chr : new ArrayList<>(channel.getChars().values())) {
                    try {
                        sweep(chr);
                    } catch (Exception e) {
                        log.error("ExpiryReplacements: sweep for " + chr.getName(), e);
                    }
                }
            }
        }
    }
}
