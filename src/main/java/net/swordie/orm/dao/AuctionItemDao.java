package net.swordie.orm.dao;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.db.converters.FileTimeConverter;
import net.swordie.ms.enums.AuctionState;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.auction.AuctionItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AuctionItemDao implements SworDao<AuctionItem> {

    private static final Logger log = LogManager.getLogger();

    public static final String TABLE = "auction_items";
    private static final ItemDao itemDao = (ItemDao) SworDaoFactory.getByClass(Item.class);

    private static final String[] COLUMNS = {
            "type", "accountID", "charID", "state", "itemType", "charName", "price", "secondPrice", "directPrice",
            "endDate", "bidUserID", "bidUsername", "idk", "bidWorld", "oid", "regDate", "deposit", "ssType", "idk2",
            "idk3", "unkDate", "item", "itemName", "soldQuantity", "selleruserid", "bidaccountid", "buyeruserid",
            "sellercollected", "buyercollected", "solddate", "itemid"
    };

    private static final String INSERT;
    private static final String UPDATE;

    static {
        StringBuilder ins = new StringBuilder("INSERT INTO auction_items(");
        StringBuilder vals = new StringBuilder(" VALUES (");
        StringBuilder upd = new StringBuilder("UPDATE auction_items SET ");
        for (int i = 0; i < COLUMNS.length; i++) {
            ins.append(COLUMNS[i]).append(", ");
            vals.append("?, ");
            upd.append(COLUMNS[i]).append(" = ?").append(i + 1 < COLUMNS.length ? ", " : "");
        }
        INSERT = ins.append("id)").append(vals).append("?)").toString();
        UPDATE = upd.append(" WHERE id = ?").toString();
    }

    /** The row's write as {query, args}: an insert the first time. */
    public Object[] statement(AuctionItem ai) {
        String query = ai.isInit() ? INSERT : UPDATE;
        Object[] args = new Object[]{
                ai.getType(),
                ai.getAccountID(),
                ai.getCharID(),
                ai.getState().ordinal(),
                ai.getItemType(),
                ai.getCharName(),
                ai.getPrice(),
                ai.getSecondPrice(),
                ai.getDirectPrice(),
                FileTimeConverter.toDbColumn(ai.getEndDate()),
                ai.getBidUserID(),
                ai.getBidUsername(),
                ai.getIdk(),
                ai.getBidWorld(),
                ai.getOid(),
                FileTimeConverter.toDbColumn(ai.getRegDate()),
                ai.getDeposit(),
                ai.getSsType(),
                ai.getIdk2(),
                ai.getIdk3(),
                FileTimeConverter.toDbColumn(ai.getUnkDate()),
                ai.getItem() == null ? null : ai.getItem().getId(),
                ai.getItemName(),
                ai.getSoldQuantity(),
                ai.getSellerUserId(),
                ai.getBuyerAccountId(),
                ai.getBuyerUserId(),
                ai.isSellerCollected(),
                ai.isBuyerCollected(),
                FileTimeConverter.toDbColumn(ai.getSoldDate()),
                ai.getItemId(),
                ai.getId()
        };
        return new Object[]{query, args};
    }

    /** Marks the row written once its statement was committed. */
    public void markSaved(AuctionItem ai) {
        ai.setInit(false);
        ai.setLastSavedHash();
    }

    public void saveOrUpdate(AuctionItem auctionItem) {
        if (auctionItem == null || auctionItem.isClean()) {
            return;
        }
        if (auctionItem.getItem() != null) {
            itemDao.saveOrUpdate(auctionItem.getItem());
        }
        Object[] st = statement(auctionItem);
        DatabaseManager.executeQuery((String) st[0], (Object[]) st[1]);
        markSaved(auctionItem);
    }

    public void delete(AuctionItem auctionItem) {
        if (auctionItem == null || auctionItem.getId() == 0) {
            log.debug("Trying to delete object " + auctionItem + " that is null or has id 0");
            return;
        }
        DatabaseManager.executeQuery("DELETE FROM auction_items WHERE ID = ?", auctionItem.getId());
    }


    @Override
    public AuctionItem fromResultSet(ResultSet resultSet, String alias) {
        AuctionItem ai;

        try {
            ai = new AuctionItem(false);

            ai.setId(resultSet.getLong(alias + ".id"));
            ai.setType(resultSet.getInt(alias + ".type"));
            ai.setCharID(resultSet.getInt(alias + ".charid"));
            ai.setAccountID(resultSet.getInt(alias + ".accountid"));
            ai.setState(AuctionState.values()[resultSet.getInt(alias + ".state")]);
            ai.setItemType(resultSet.getInt(alias + ".itemType"));
            ai.setCharName(resultSet.getString(alias + ".charName"));
            ai.setPrice(resultSet.getLong(alias + ".price"));
            ai.setSecondPrice(resultSet.getLong(alias + ".secondprice"));
            ai.setDirectPrice(resultSet.getLong(alias + ".directprice"));
            ai.setEndDate(FileTimeConverter.fromDbColumn(resultSet.getTimestamp(alias + ".endDate")));
            ai.setBidUserID(resultSet.getInt(alias + ".biduserid"));
            ai.setBidUsername(resultSet.getString(alias + ".bidusername"));
            ai.setIdk(resultSet.getInt(alias + ".idk"));
            ai.setBidWorld(resultSet.getInt(alias + ".bidworld"));
            ai.setOid(resultSet.getInt(alias + ".oid"));
            ai.setRegDate(FileTimeConverter.fromDbColumn(resultSet.getTimestamp(alias + ".regdate")));
            ai.setDeposit(resultSet.getLong(alias + ".deposit"));
            ai.setSsType(resultSet.getInt(alias + ".sstype"));
            ai.setIdk2(resultSet.getInt(alias + ".idk2"));
            ai.setIdk3(resultSet.getInt(alias + ".idk3"));
            ai.setUnkDate(FileTimeConverter.fromDbColumn(resultSet.getTimestamp(alias + ".unkdate")));
            ai.setSoldQuantity(resultSet.getInt(alias + ".soldquantity"));
            ai.setSellerUserId(resultSet.getInt(alias + ".selleruserid"));
            ai.setBuyerAccountId(resultSet.getInt(alias + ".bidaccountid"));
            ai.setBuyerUserId(resultSet.getInt(alias + ".buyeruserid"));
            ai.setSellerCollected(resultSet.getBoolean(alias + ".sellercollected"));
            ai.setBuyerCollected(resultSet.getBoolean(alias + ".buyercollected"));
            ai.setSoldDate(FileTimeConverter.fromDbColumn(resultSet.getTimestamp(alias + ".solddate")));
            ai.setItemId(resultSet.getInt(alias + ".itemid"));

            resultSet.getLong(alias + ".item");
            if (!resultSet.wasNull()) {
                ai.setItem(itemDao.fromResultSet(resultSet, "i"));
            }
            ai.setItemName(resultSet.getString(alias + ".itemname"));

            ai.setLastSavedHash();
        } catch (SQLException e) {
            log.error("Could not read an auction row", e);
            return null;
        }

        return ai;
    }

    /** Every open listing and anything still waiting to be collected, plus two weeks of finished sales (market prices). */
    public List<AuctionItem> list() {
        var twoWeeksAgo = Timestamp.valueOf(FileTime.currentTime().toLocalDateTime().minusWeeks(2));
        var objs = DatabaseManager.executeSelect(this,
                "SELECT * " +
                        "FROM auction_items ai " +
                        "LEFT JOIN items i ON i.id = ai.item " +
                        "WHERE ai.state IN (?, ?, ?) OR ai.enddate > ? OR ai.solddate > ?",
                "ai", AuctionState.Init.ordinal(), AuctionState.Sold.ordinal(), AuctionState.Expire.ordinal(),
                twoWeeksAgo.toString(), twoWeeksAgo.toString());
        List<AuctionItem> auctionItems = new ArrayList<>();
        for (var obj : objs) {
            if (obj != null) {
                auctionItems.add((AuctionItem) obj);
            }
        }
        return auctionItems;
    }

}
