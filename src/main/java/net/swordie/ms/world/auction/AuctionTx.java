package net.swordie.ms.world.auction;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.db.QueryHolder;
import net.swordie.orm.connection.HikariCPDataSource;
import net.swordie.orm.dao.AuctionItemDao;
import net.swordie.orm.dao.ItemDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * One Auction House trade written in a single database transaction: listing rows, the items that moved, the
 * characters' mesos and the log row commit together or not at all.
 */
public class AuctionTx {

    private static final Logger log = LogManager.getLogger();
    private static final ItemDao itemDao = (ItemDao) SworDaoFactory.getByClass(Item.class);
    private static final AuctionItemDao auctionItemDao = (AuctionItemDao) SworDaoFactory.getByClass(AuctionItem.class);

    private final List<String> queries = new ArrayList<>();
    private final List<Object[]> args = new ArrayList<>();
    private final List<QueryHolder<Item>> itemHolders = new ArrayList<>();
    private final List<AuctionItem> auctions = new ArrayList<>();

    public AuctionTx sql(String query, Object... a) {
        queries.add(query);
        args.add(a);
        return this;
    }

    public AuctionTx item(Item item) {
        if (item == null) {
            return this;
        }
        QueryHolder<Item> qh = itemDao.createQueryHolder(item);
        if (!qh.isClean()) {
            qh.captureHash();
            itemHolders.add(qh);
            sql(qh.getQuery(), qh.getArgs());
        }
        return this;
    }

    /** The row of an item that left every inventory (listed or merged away): no owner left. */
    public AuctionTx detachItem(Item item) {
        item(item);
        return sql("UPDATE items SET inventoryid = NULL, trunkid = NULL WHERE id = ?", item.getId());
    }

    public AuctionTx auction(AuctionItem ai) {
        Object[] st = auctionItemDao.statement(ai);
        auctions.add(ai);
        return sql((String) st[0], (Object[]) st[1]);
    }

    public AuctionTx money(Char chr) {
        return sql("UPDATE characterstats SET money = ? WHERE id = ?", chr.getMoney(),
                chr.getAvatarData().getCharacterStat().getId());
    }

    public AuctionTx log(String action, AuctionItem ai, Char chr, int otherAccountId, Item item, int quantity, long mesos) {
        return sql("INSERT INTO auction_log(time, action, auctionid, accountid, charid, otheraccountid, itemsn, itemid,"
                        + " quantity, mesos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new Timestamp(System.currentTimeMillis()), action, ai.getId(), chr.getAccount().getId(), chr.getId(),
                otherAccountId, item == null ? 0 : item.getId(), ai.getItemId(), quantity, mesos);
    }

    /** Commits everything; false (and nothing written) on any error. */
    public boolean commit() {
        try (Connection con = HikariCPDataSource.getConnection()) {
            try {
                for (int i = 0; i < queries.size(); i++) {
                    try (var stmt = con.prepareStatement(queries.get(i))) {
                        Object[] a = args.get(i);
                        for (int j = 0; j < a.length; j++) {
                            stmt.setObject(j + 1, a[j]);
                        }
                        stmt.executeUpdate();
                    }
                }
                con.commit();
            } catch (SQLException e) {
                log.error("Auction trade rolled back", e);
                try {
                    con.rollback();
                } catch (SQLException ignored) {
                }
                itemHolders.forEach(QueryHolder::failed);
                return false;
            }
        } catch (SQLException e) {
            log.error("Auction trade: no database connection", e);
            itemHolders.forEach(QueryHolder::failed);
            return false;
        }
        itemHolders.forEach(QueryHolder::setLastSavedHash);
        auctions.forEach(auctionItemDao::markSaved);
        return true;
    }
}
