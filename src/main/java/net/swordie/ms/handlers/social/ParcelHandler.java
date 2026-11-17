package net.swordie.ms.handlers.social;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.db.DatabaseException;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.packet.ParcelPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.AutoSave;
import net.swordie.ms.world.World;
import net.swordie.orm.dao.ItemDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Duey (USER_PARCEL_REQUEST 307): 0 / 1 open, 3 send (1 inventory, 2 slot, 4 quantity, 4 mesos, S receiver, 1 quick,
 * S memo, [4 quick delivery item]), 5 claim (4 id), 6 delete (4 id), 8 close. Packages are stored in the parcels table.
 */
public class ParcelHandler {

    private static final Logger log = LogManager.getLogger();
    private static final ItemDao itemDao = (ItemDao) SworDaoFactory.getByClass(Item.class);

    private static final int REQ_OPEN = 0;
    private static final int REQ_OPEN_QUICK = 1;
    private static final int REQ_SEND = 3;
    private static final int REQ_CLAIM = 5;
    private static final int REQ_DELETE = 6;

    private static final long SEND_FEE = 5000;
    private static final int MAX_PARCELS = 50;
    private static final int MAX_MEMO = 100;

    public static class Parcel {
        public long id;
        public int receiverId;
        public int senderId;
        public String senderName = "";
        public long meso;
        public String memo = "";
        public boolean quick;
        public long sentTime;
        public Item item;
    }

    @Handler(op = InHeader.USER_PARCEL_REQUEST)
    public static void handleParcelRequest(Char chr, InPacket inPacket) {
        int type = inPacket.decodeByte();
        switch (type) {
            case REQ_OPEN, REQ_OPEN_QUICK -> chr.write(ParcelPacket.open(load(chr.getId())));
            case REQ_SEND -> handleSend(chr, inPacket);
            case REQ_CLAIM -> handleClaim(chr, inPacket.decodeInt());
            case REQ_DELETE -> handleDelete(chr, inPacket.decodeInt());
            default -> {
            }
        }
    }

    private static void handleSend(Char chr, InPacket inPacket) {
        int inv = inPacket.decodeByte();
        short slot = inPacket.decodeShort();
        int quantity = inPacket.decodeInt();
        long meso = inPacket.decodeInt() & 0xFFFFFFFFL;
        String receiverName = inPacket.decodeString();
        boolean quick = inPacket.decodeByte() != 0;
        String memo = inPacket.decodeString();
        if (memo.length() > MAX_MEMO) {
            memo = memo.substring(0, MAX_MEMO);
        }

        var rows = DatabaseManager.queryRows("SELECT c.id AS id, c.accid AS accid FROM characters c "
                + "INNER JOIN avatardata ad ON ad.id = c.avatardata "
                + "INNER JOIN characterstats cs ON cs.id = ad.characterstat WHERE cs.name = ?", receiverName);
        if (rows.isEmpty()) {
            chr.write(ParcelPacket.result(ParcelPacket.CHECK_NAME));
            return;
        }
        int receiverId = num(rows.get(0).get("id"));
        int receiverAcc = num(rows.get(0).get("accid"));
        if (receiverId == chr.getId() || receiverAcc == chr.getAccount().getId()) {
            chr.write(ParcelPacket.result(ParcelPacket.SAME_ACCOUNT));
            return;
        }
        if (count(receiverId) >= MAX_PARCELS) {
            chr.write(ParcelPacket.result(ParcelPacket.RECEIVER_FULL));
            return;
        }

        Item item = null;
        if (inv > 0 && slot > 0) {
            InvType invType = InvType.getInvTypeByVal(inv);
            item = invType == null ? null : chr.getInventoryByType(invType).getItemBySlot(slot);
            if (item == null || !item.isTradable() || quantity <= 0 || quantity > item.getQuantity()) {
                chr.write(ParcelPacket.result(ParcelPacket.BAD_REQUEST));
                return;
            }
        }
        if (item == null && meso <= 0) {
            chr.write(ParcelPacket.result(ParcelPacket.BAD_REQUEST));
            return;
        }
        long cost = meso + SEND_FEE;
        if (chr.getMoney() < cost) {
            chr.write(ParcelPacket.result(ParcelPacket.NO_MESOS));
            return;
        }

        // stored first, taken after: a failed write leaves the sender with everything
        int amount = item == null ? 0 : ItemConstants.isEquip(item.getItemId()) ? 1 : sendAmount(item, quantity);
        Item sent = null;
        long id = 0;
        try {
            if (item != null) {
                sent = item.deepCopy();
                sent.setQuantity(amount);
                sent.assignId();
                itemDao.saveOrUpdate(sent);
            }
            id = DatabaseManager.executeInsert("INSERT INTO parcels (receiverid, senderid, sendername, meso, memo, "
                            + "quick, senttime, itemid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", receiverId, chr.getId(),
                    chr.getName(), meso, memo, quick ? 1 : 0, System.currentTimeMillis(), sent == null ? 0 : sent.getId());
        } catch (DatabaseException e) {
            id = 0;
        }
        if (id <= 0) {
            log.error("Parcel from character {} to {} not stored; nothing taken", chr.getId(), receiverId);
            if (sent != null) {
                deleteLooseItem(sent.getId());
            }
            chr.write(ParcelPacket.result(ParcelPacket.UNKNOWN_ERROR));
            return;
        }
        if (item != null) {
            chr.consumeItem(item, amount);
        }
        chr.deductMoney(SEND_FEE, ProgressionLog.Sink.FEES);
        if (meso > 0) {
            chr.deductMoney(meso, ProgressionLog.Sink.TRANSFER);
        }
        AutoSave.saveNow(chr); // the parcel row is already written
        chr.write(ParcelPacket.result(ParcelPacket.SENT));

        World world = chr.getWorld();
        Char receiver = world.getCharByID(receiverId);
        if (receiver != null && receiver.isOnline()) {
            receiver.write(ParcelPacket.notice(chr.getName(), quick));
        }
    }

    private static void handleClaim(Char chr, int id) {
        Parcel p = byId(chr.getId(), id);
        if (p == null) {
            chr.write(ParcelPacket.result(ParcelPacket.UNKNOWN_ERROR));
            return;
        }
        if (p.item != null && !chr.canHold(p.item)) {
            chr.write(ParcelPacket.result(ParcelPacket.NO_SLOTS));
            return;
        }
        if (p.meso > 0 && !chr.canAddMoney(p.meso)) {
            chr.write(ParcelPacket.result(ParcelPacket.MESO_LIMIT));
            return;
        }
        // the delete decides who gets it: a second claim of the same parcel removes nothing and gets nothing
        if (!removeParcel(chr, p)) {
            chr.write(ParcelPacket.result(ParcelPacket.UNKNOWN_ERROR));
            return;
        }
        if (p.item != null) {
            chr.addItemToInventory(p.item);
        }
        if (p.meso > 0) {
            chr.addMoney(p.meso);
        }
        AutoSave.saveNow(chr); // the parcel row is already gone
        chr.write(ParcelPacket.removed(p.id, true));
    }

    private static void handleDelete(Char chr, int id) {
        Parcel p = byId(chr.getId(), id);
        if (p == null) {
            chr.write(ParcelPacket.result(ParcelPacket.UNKNOWN_ERROR));
            return;
        }
        if (!removeParcel(chr, p)) {
            chr.write(ParcelPacket.result(ParcelPacket.UNKNOWN_ERROR));
            return;
        }
        if (p.item != null) {
            deleteLooseItem(p.item.getId());
        }
        chr.write(ParcelPacket.removed(p.id, false));
    }

    /** True when this call removed the parcel row (and so owns its contents). */
    private static boolean removeParcel(Char chr, Parcel p) {
        try {
            return DatabaseManager.executeUpdate("DELETE FROM parcels WHERE id = ? AND receiverid = ?", p.id,
                    chr.getId()) == 1;
        } catch (DatabaseException e) {
            return false;
        }
    }

    /** An item row that belongs to no inventory or storage (a parcel's item). */
    private static void deleteLooseItem(long itemId) {
        try {
            DatabaseManager.executeUpdate("DELETE FROM items WHERE id = ? AND inventoryid IS NULL AND trunkid IS NULL",
                    itemId);
        } catch (DatabaseException e) {
            log.error("Parcel item {} left in the items table", itemId);
        }
    }

    // Contributed by Arixal (from GitLab).
    /** What a parcel takes from a stack: a throwing-star or bullet set goes whole (part of one would be a second set). */
    static int sendAmount(Item item, int quantity) {
        return ItemConstants.isRechargable(item.getItemId()) ? item.getQuantity() : quantity;
    }

    private static int count(int receiverId) {
        var rows = DatabaseManager.queryRows("SELECT COUNT(*) AS n FROM parcels WHERE receiverid = ?", receiverId);
        return rows.isEmpty() ? 0 : num(rows.get(0).get("n"));
    }

    private static Parcel byId(int receiverId, long id) {
        for (Parcel p : load(receiverId)) {
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }

    public static List<Parcel> load(int receiverId) {
        List<Parcel> parcels = new ArrayList<>();
        for (Map<String, Object> row : DatabaseManager.queryRows("SELECT id, receiverid, senderid, sendername, meso, "
                + "memo, quick, senttime, itemid FROM parcels WHERE receiverid = ? ORDER BY id", receiverId)) {
            Parcel p = new Parcel();
            p.id = lng(row.get("id"));
            p.receiverId = num(row.get("receiverid"));
            p.senderId = num(row.get("senderid"));
            p.senderName = row.get("sendername") == null ? "" : row.get("sendername").toString();
            p.meso = lng(row.get("meso"));
            p.memo = row.get("memo") == null ? "" : row.get("memo").toString();
            p.quick = num(row.get("quick")) != 0;
            p.sentTime = lng(row.get("senttime"));
            long itemId = lng(row.get("itemid"));
            if (itemId != 0) {
                var items = DatabaseManager.executeSelect(itemDao, "SELECT * FROM items i WHERE i.id = ?", "i", itemId);
                p.item = items.isEmpty() ? null : (Item) items.get(0);
                if (p.item == null && p.meso <= 0) {
                    continue;
                }
            }
            parcels.add(p);
        }
        return parcels;
    }

    private static int num(Object o) {
        return o instanceof Number n ? n.intValue() : 0;
    }

    private static long lng(Object o) {
        return o instanceof Number n ? n.longValue() : 0;
    }
}
