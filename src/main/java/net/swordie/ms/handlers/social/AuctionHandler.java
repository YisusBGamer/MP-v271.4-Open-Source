package net.swordie.ms.handlers.social;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipAttribute;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.AuctionPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.AuctionState;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.user.MigrationHandler;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.World;
import net.swordie.ms.world.auction.AuctionItem;
import net.swordie.ms.world.auction.AuctionTx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Auction House requests (v271 316, v265 310). The first byte is the request type; the bodies are the client's
 * senders (v271 0x142666be0..0x142b0c400, as v265 0x1421243f0..0x142554e70). Every request that locks the window
 * (5, 6, 7, 10, 11, 20-22, 25, 26) must get an AUCTION_MESSAGE back; 65 doesn't lock it and gets no reply.
 * <p>
 * The client's own ids: CWvsContext+0x22e4 = the int after the long in CHECK_PASSWORD_RESULT (our user id),
 * +0x2314 = world. Side records and claim records must carry both or the collect buttons stay off.
 * <p>
 * Trades run one at a time under {@link #LOCK}: check, take, give, then one database transaction for the moved items,
 * the listing rows, the mesos and the log row. Items move as the same object (same serial, stats and flags).
 * A sale record is collected once per side (seller: mesos, buyer: the item); an unsold one once by the seller.
 */
public class AuctionHandler {

    private static final Logger log = LogManager.getLogger();

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);

    private static final int REQ_INIT = 0;
    private static final int REQ_SELL = 5;
    private static final int REQ_RELIST = 6;
    private static final int REQ_CANCEL = 7;
    private static final int REQ_BUY = 10;
    private static final int REQ_BUY_BY_ITEM = 11;
    private static final int REQ_SEARCH = 15;
    private static final int REQ_MARKET_PRICE = 16;
    private static final int REQ_COLLECT_MESOS = 20;
    private static final int REQ_COLLECT_ITEM = 21;
    private static final int REQ_COLLECT_OTHER = 22;
    private static final int REQ_WISH_ADD = 25;
    private static final int REQ_WISH_REMOVE = 26;
    private static final int REQ_HISTORY_MESOS = 40;
    private static final int REQ_HISTORY_ITEM = 41;
    private static final int REQ_HISTORY_OTHER = 42;
    private static final int REQ_SELLING_LIST = 50;
    private static final int REQ_COMPLETE_LIST = 52;
    private static final int REQ_WISH_LIST = 54;
    // v271: the old-claim window is gone (40-42 no longer sent), v265 types 59+ are 2 lower
    private static final int REQ_EXIT = 57;
    private static final int REQ_LOW_PRICE_REPORT = 65;

    private static final int OK = 0;
    private static final int FAIL = 99; // no ErrorMsg entry: the client shows "An unknown error has occurred."

    // Etc/AuctionData.img (Pay, Register, Slot) and GMS rules
    public static final long DEPOSIT = 2_000;
    public static final long MIN_PRICE = 50;
    public static final long MAX_TOTAL = 1_999_999_999_999L;
    // the client uses PremiumCommissionRate (3) for MVP Silver..Red or the select-character auth byte bit 2
    // (0x1404b5170); this server has neither and sends that byte as 0, so client and server both use 5
    public static final int COMMISSION_PERCENT = 5;
    public static final int SELL_SLOTS = 30;
    public static final int BUY_SLOTS = 120;
    private static final int HOURS_SHORT = 24;
    private static final int HOURS_LONG = 48;
    private static final int SPELL_TRACE = 4001832;
    private static final int SPELL_TRACE_BUNDLE = 1000;

    private static final int LIST_MAX = 100;
    private static final int WISH_QR = 991245;
    private static final int WISH_SLOTS = 10;

    @Handler(op = InHeader.AUCTION_REQUEST)
    public static void handleAuctionRequest(Char chr, InPacket inPacket) {
        if (!chr.isInAuctionHouse()) {
            return;
        }
        int type = inPacket.decodeByte() & 0xFF;
        if (type == REQ_EXIT) {
            MigrationHandler.handleAuctionLeaveRequest(chr, inPacket);
            return;
        }
        if (type == REQ_INIT || unlocks(type)) {
            saveChar(chr);
        }
        World world = chr.getWorld();
        LOCK.lock();
        try {
            expireDue(world);
            switch (type) {
                case REQ_INIT -> handleInit(chr, world);
                case REQ_SELL -> handleSell(chr, inPacket, world);
                case REQ_RELIST -> handleRelist(chr, inPacket, world);
                case REQ_CANCEL -> handleCancel(chr, inPacket, world);
                case REQ_BUY -> handleBuy(chr, inPacket, world);
                case REQ_BUY_BY_ITEM -> handleBuyAllUnderPrice(chr, inPacket, world);
                case REQ_SEARCH, REQ_MARKET_PRICE -> handleSearch(chr, inPacket, type, world);
                case REQ_COLLECT_MESOS, REQ_COLLECT_ITEM, REQ_COLLECT_OTHER, REQ_HISTORY_MESOS, REQ_HISTORY_ITEM,
                        REQ_HISTORY_OTHER -> handleCollect(chr, inPacket.decodeLong(), type, world);
                case REQ_WISH_ADD -> handleWishAdd(chr, inPacket.decodeLong(), world);
                case REQ_WISH_REMOVE -> handleWishRemove(chr, inPacket.decodeLong());
                case REQ_SELLING_LIST -> chr.write(AuctionPacket.itemList(AuctionPacket.SELLING_LIST, chr, selling(chr, world)));
                case REQ_COMPLETE_LIST -> chr.write(AuctionPacket.itemList(AuctionPacket.COMPLETE_LIST, chr, complete(chr, world)));
                case REQ_WISH_LIST -> chr.write(AuctionPacket.itemList(AuctionPacket.WISH_LIST, chr, wishItems(chr, world)));
                case REQ_LOW_PRICE_REPORT -> handleLowPriceReport(chr, inPacket);
                default -> log.debug("Auction request type {} not handled ({} bytes)", type, inPacket.getUnreadAmount());
            }
        } catch (Exception e) {
            log.error("Auction request " + type + " failed for " + chr.getName(), e);
            if (unlocks(type)) {
                chr.write(AuctionPacket.message(type, FAIL));
            }
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Everything the character did before entering is saved first, so a trade's own writes (mesos, moved items) can
     * never pair with older unsaved state after a crash.
     */
    private static void saveChar(Char chr) {
        chr.flushRemovedItems();
        charDao.saveOrUpdate(chr);
    }

    private static boolean unlocks(int type) {
        return switch (type) {
            case REQ_SELL, REQ_RELIST, REQ_CANCEL, REQ_BUY, REQ_BUY_BY_ITEM, REQ_COLLECT_MESOS, REQ_COLLECT_ITEM,
                    REQ_COLLECT_OTHER, REQ_WISH_ADD, REQ_WISH_REMOVE, REQ_HISTORY_MESOS, REQ_HISTORY_ITEM,
                    REQ_HISTORY_OTHER -> true;
            default -> false;
        };
    }

    private static void handleInit(Char chr, World world) {
        chr.write(AuctionPacket.searchResult(AuctionPacket.SEARCH, chr, listings(world, a -> true)));
        chr.write(AuctionPacket.searchResult(AuctionPacket.MARKET_PRICE, chr, recentSales(world, a -> true)));
        chr.write(AuctionPacket.itemList(AuctionPacket.SELLING_LIST, chr, selling(chr, world)));
        chr.write(AuctionPacket.itemList(AuctionPacket.COMPLETE_LIST, chr, complete(chr, world)));
        chr.write(AuctionPacket.itemList(AuctionPacket.WISH_LIST, chr, wishItems(chr, world)));
        chr.write(AuctionPacket.on());
    }

    /**
     * 65 (v265 67): 4 item id, 8 (item vfunc 0xa8), 8 unit price, 8 estimated value. Sent instead of a listing when an equip worth over
     * 100M is priced under 1/5 of its value and the player backs out of the alert_price2 warning (0x1421479f0).
     * Report only: the client sends it without locking the window and the message dispatcher ignores it.
     */
    private static void handleLowPriceReport(Char chr, InPacket inPacket) {
        int itemId = inPacket.decodeInt();
        inPacket.decodeLong();
        long unitPrice = inPacket.decodeLong();
        long value = inPacket.decodeLong();
        log.info("Auction low-price warning declined by {}: item {} at {} mesos, value {}", chr.getName(), itemId,
                unitPrice, value);
    }

    private static void fail(Char chr, int type, String reason) {
        if (reason != null) {
            chr.chatMessage(ChatType.GameDesc, reason);
        }
        chr.write(AuctionPacket.message(type, FAIL));
    }

    private static void dbFail(Char chr, int type) {
        fail(chr, type, "The Auction House couldn't save that just now. Please try again.");
    }

    /** Price checks shared by listing and relisting; null when fine. */
    private static String priceProblem(long unitPrice, int quantity) {
        if (unitPrice < MIN_PRICE) {
            return "The price must be at least " + MIN_PRICE + " mesos.";
        }
        if (quantity <= 0 || unitPrice > MAX_TOTAL / quantity) {
            return "The total price can't be over 1,999,999,999,999 mesos.";
        }
        return null;
    }

    private static int hours(int requested) {
        return requested == HOURS_LONG ? HOURS_LONG : HOURS_SHORT;
    }

    private static boolean isEquipLike(Item item) {
        return item instanceof Equip || !item.getInvType().isStackable(item.getItemId());
    }

    // 5: 1 inventory, 4 slot, 4 item id, 4 quantity, 8 unit price, 4 hours
    private static void handleSell(Char chr, InPacket inPacket, World world) {
        InvType invType = InvType.getInvTypeByVal(inPacket.decodeByte());
        int slot = inPacket.decodeInt();
        int itemId = inPacket.decodeInt();
        int quantity = inPacket.decodeInt();
        long unitPrice = inPacket.decodeLong();
        int hours = hours(inPacket.decodeInt());
        Inventory inv = invType == null || invType == InvType.EQUIPPED ? null : chr.getInventoryByType(invType);
        Item item = inv == null || slot <= 0 ? null : inv.getItemBySlot(slot);
        if (item == null || item.getItemId() != itemId || !item.isTradable() || item.isInBag()
                || (item.getDateExpire() != null && item.getDateExpire().isExpired())) {
            fail(chr, REQ_SELL, "This item cannot be listed.");
            return;
        }
        if (chr.getPets().stream().anyMatch(p -> p.getItem() == item)) {
            fail(chr, REQ_SELL, "Put the pet away before listing it.");
            return;
        }
        int n = isEquipLike(item) ? item.getQuantity() : quantity;
        if (n <= 0 || n > item.getQuantity() || (itemId == SPELL_TRACE && n % SPELL_TRACE_BUNDLE != 0)) {
            fail(chr, REQ_SELL, "That quantity can't be listed.");
            return;
        }
        String priceProblem = priceProblem(unitPrice, n);
        if (priceProblem != null) {
            fail(chr, REQ_SELL, priceProblem);
            return;
        }
        if (chr.getMoney() < DEPOSIT) {
            fail(chr, REQ_SELL, "You need " + DEPOSIT + " mesos for the listing fee.");
            return;
        }
        if (selling(chr, world).size() >= SELL_SLOTS) {
            fail(chr, REQ_SELL, "All " + SELL_SLOTS + " of your sales slots are in use.");
            return;
        }

        chr.deductMoney(DEPOSIT, ProgressionLog.Sink.AUCTION);
        Item listed;
        if (n == item.getQuantity()) {
            int q = item.getQuantity();
            chr.consumeItemFull(item, false, true); // the same item moves to the listing
            item.setQuantity(q);
            listed = item;
        } else {
            chr.consumeItem(item, n);
            listed = item.deepCopy();
            listed.setQuantity(n);
            listed.assignId();
        }
        AuctionItem ai = new AuctionItem();
        ai.setItem(listed);
        ai.setAccountID(chr.getAccount().getId());
        ai.setSellerUserId(chr.getUser().getId());
        ai.setCharID(chr.getId());
        ai.setCharName(chr.getName());
        ai.setBidWorld(world.getWorldId().getVal());
        ai.setDirectPrice(unitPrice);
        ai.setDeposit(DEPOSIT);
        ai.setRegDate(FileTime.currentTime());
        ai.setEndDate(FileTime.fromDate(FileTime.currentTime().toLocalDateTime().plusHours(hours)));
        ai.setState(AuctionState.Init);
        world.addAuction(ai, false);

        AuctionTx tx = new AuctionTx().detachItem(listed);
        if (listed != item) {
            tx.item(item);
        }
        boolean saved = tx.auction(ai).money(chr).log("list", ai, chr, 0, listed, n, DEPOSIT).commit();
        if (!saved) {
            log.error("Auction listing {} kept in memory only (database write failed)", ai.getId());
        }
        chr.write(AuctionPacket.message(REQ_SELL, OK));
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.SELLING_ADD, chr, ai));
    }

    // 6: 8 auction id, 4 world, 4 item id, 4 quantity, 8 unit price, 4 hours
    private static void handleRelist(Char chr, InPacket inPacket, World world) {
        long id = inPacket.decodeLong();
        inPacket.decodeInt();
        inPacket.decodeInt();
        inPacket.decodeInt();
        long unitPrice = inPacket.decodeLong();
        int hours = hours(inPacket.decodeInt());
        AuctionItem ai = world.getAuctionById(id);
        if (ai == null || ai.getState() != AuctionState.Expire || !AuctionPacket.isSeller(ai, chr) || ai.getItem() == null) {
            fail(chr, REQ_RELIST, "That item can't be listed again.");
            return;
        }
        if (ai.getItem().isCash() && ai.getCharID() != chr.getId()) {
            fail(chr, REQ_RELIST, "Only " + ai.getCharName() + " can list this item again.");
            return;
        }
        String priceProblem = priceProblem(unitPrice, ai.getItem().getQuantity());
        if (priceProblem != null) {
            fail(chr, REQ_RELIST, priceProblem);
            return;
        }
        if (chr.getMoney() < DEPOSIT) {
            fail(chr, REQ_RELIST, "You need " + DEPOSIT + " mesos for the listing fee.");
            return;
        }
        if (selling(chr, world).size() >= SELL_SLOTS) {
            fail(chr, REQ_RELIST, "All " + SELL_SLOTS + " of your sales slots are in use.");
            return;
        }
        chr.deductMoney(DEPOSIT, ProgressionLog.Sink.AUCTION);
        ai.setState(AuctionState.Init);
        ai.setDirectPrice(unitPrice);
        ai.setDeposit(DEPOSIT);
        ai.setCharID(chr.getId());
        ai.setCharName(chr.getName());
        ai.setRegDate(FileTime.currentTime());
        ai.setEndDate(FileTime.fromDate(FileTime.currentTime().toLocalDateTime().plusHours(hours)));
        if (!new AuctionTx().auction(ai).money(chr).log("relist", ai, chr, 0, ai.getItem(), ai.getQuantity(), DEPOSIT).commit()) {
            log.error("Auction relist {} kept in memory only (database write failed)", ai.getId());
        }
        chr.write(AuctionPacket.message(REQ_RELIST, OK));
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, chr, ai));
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.SELLING_ADD, chr, ai));
    }

    // 7: 8 auction id, 4 world
    private static void handleCancel(Char chr, InPacket inPacket, World world) {
        long id = inPacket.decodeLong();
        AuctionItem ai = world.getAuctionById(id);
        if (ai == null || ai.getState() != AuctionState.Init || !AuctionPacket.isSeller(ai, chr)) {
            fail(chr, REQ_CANCEL, "That listing can't be cancelled.");
            return;
        }
        ai.setEndDate(FileTime.currentTime());
        ai.setState(AuctionState.Expire);
        if (!new AuctionTx().auction(ai).log("cancel", ai, chr, 0, ai.getItem(), ai.getQuantity(), 0).commit()) {
            dbFail(chr, REQ_CANCEL);
            return;
        }
        chr.write(AuctionPacket.message(REQ_CANCEL, OK));
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, chr, ai));
    }

    // 10: 8 auction id, 4 world, 4 item id, 4 quantity, 8 total price, 4
    private static void handleBuy(Char chr, InPacket inPacket, World world) {
        long id = inPacket.decodeLong();
        inPacket.decodeInt();
        int itemId = inPacket.decodeInt();
        int quantity = inPacket.decodeInt();
        long clientTotal = inPacket.decodeLong();
        AuctionItem listing = world.getAuctionById(id);
        Item listedItem = listing == null ? null : listing.getItem();
        if (listedItem == null || listing.getState() != AuctionState.Init || listing.getEndDate().isExpired()
                || listing.getQuantity() <= 0 || listedItem.getItemId() != itemId) {
            fail(chr, REQ_BUY, "That item is no longer for sale.");
            if (listing != null && listing.getState() != AuctionState.Init) {
                chr.write(AuctionPacket.searchRemove(id));
            }
            return;
        }
        if (listing.getAccountID() == chr.getAccount().getId()) {
            fail(chr, REQ_BUY, "You can't buy items listed from your own account.");
            return;
        }
        int n = isEquipLike(listedItem) ? listing.getQuantity() : quantity;
        if (n <= 0 || n > listing.getQuantity() || (itemId == SPELL_TRACE && n % SPELL_TRACE_BUNDLE != 0)) {
            fail(chr, REQ_BUY, "That quantity isn't available.");
            return;
        }
        long cost = listing.getDirectPrice() * n;
        if (clientTotal != cost) {
            fail(chr, REQ_BUY, "The price of that listing is different. Search again.");
            return;
        }
        if (chr.getMoney() < cost) {
            fail(chr, REQ_BUY, "You don't have enough mesos.");
            return;
        }
        if (waitingPurchases(chr, world) >= BUY_SLOTS) {
            fail(chr, REQ_BUY, "Collect your purchases in the Complete tab first (" + BUY_SLOTS + " at most).");
            return;
        }

        chr.deductMoney(cost);
        AuctionTx tx = new AuctionTx();
        AuctionItem sale = takeFromListing(chr, world, listing, n, FileTime.currentTime(), tx);
        if (!tx.money(chr).commit()) {
            log.error("Auction sale {} kept in memory only (database write failed)", sale.getId());
        }

        chr.write(AuctionPacket.message(REQ_BUY, OK));
        sendBought(chr, world, listing, sale);
    }

    /**
     * 11 (0x142554e70, buyAllUnderPrice popup): 4 item id, 8 highest unit price, 4 count, 4 (-100, or the entry's
     * quantity for items in an AuctionData id range). Buys up to count units, cheapest listings first; the client
     * already checked that count is a whole number of bundles.
     */
    private static void handleBuyAllUnderPrice(Char chr, InPacket inPacket, World world) {
        int itemId = inPacket.decodeInt();
        long maxUnit = inPacket.decodeLong();
        int count = inPacket.decodeInt();
        inPacket.decodeInt();
        int bundle = itemId == SPELL_TRACE ? SPELL_TRACE_BUNDLE : 1;
        if (count <= 0 || maxUnit < MIN_PRICE || count % bundle != 0) {
            fail(chr, REQ_BUY_BY_ITEM, "Enter a price and a quantity to buy.");
            return;
        }
        int acc = chr.getAccount().getId();
        List<AuctionItem> offers = world.getAuctionHouse().stream()
                .filter(a -> a.getItem() != null && a.getItemId() == itemId && a.getState() == AuctionState.Init
                        && !a.getEndDate().isExpired() && a.getQuantity() > 0 && a.getAccountID() != acc
                        && a.getDirectPrice() <= maxUnit)
                .sorted(Comparator.comparingLong(AuctionItem::getDirectPrice).thenComparingLong(AuctionItem::getId))
                .collect(Collectors.toList());
        long free = BUY_SLOTS - waitingPurchases(chr, world);
        List<AuctionItem> picked = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        int left = count;
        long cost = 0;
        for (AuctionItem a : offers) {
            if (left <= 0 || picked.size() >= free) {
                break;
            }
            int n = isEquipLike(a.getItem()) ? a.getQuantity() : Math.min(left, a.getQuantity());
            if (!isEquipLike(a.getItem())) {
                n = (int) Math.min(n, (chr.getMoney() - cost) / a.getDirectPrice());
            }
            n -= n % bundle;
            if (n <= 0 || n > left) {
                continue;
            }
            long price = a.getDirectPrice() * n;
            if (price > chr.getMoney() - cost) {
                continue;
            }
            picked.add(a);
            amounts.add(n);
            cost += price;
            left -= n;
        }
        if (picked.isEmpty()) {
            fail(chr, REQ_BUY_BY_ITEM, free <= 0
                    ? "Collect your purchases in the Complete tab first (" + BUY_SLOTS + " at most)."
                    : offers.isEmpty() ? "None are for sale at or under that price."
                    : "You don't have enough mesos.");
            return;
        }

        chr.deductMoney(cost);
        FileTime now = FileTime.currentTime();
        AuctionTx tx = new AuctionTx();
        List<AuctionItem> sales = new ArrayList<>();
        for (int i = 0; i < picked.size(); i++) {
            sales.add(takeFromListing(chr, world, picked.get(i), amounts.get(i), now, tx));
        }
        if (!tx.money(chr).commit()) {
            log.error("Auction bulk buy of {} kept in memory only (database write failed)", itemId);
        }

        chr.write(AuctionPacket.message(REQ_BUY_BY_ITEM, OK));
        for (int i = 0; i < picked.size(); i++) {
            sendBought(chr, world, picked.get(i), sales.get(i));
        }
        if (left > 0) {
            chr.chatMessage(ChatType.GameDesc, "Bought %d of %d. No more were for sale at or under that price.",
                    count - left, count);
        }
    }

    /** Moves n units of a listing into a sale record (the listing itself when it sells out); adds the rows to tx. */
    private static AuctionItem takeFromListing(Char chr, World world, AuctionItem listing, int n, FileTime now,
                                               AuctionTx tx) {
        Item listedItem = listing.getItem();
        long cost = listing.getDirectPrice() * n;
        AuctionItem sale;
        if (n == listing.getQuantity()) {
            sale = listing;
        } else {
            Item part = listedItem.deepCopy();
            part.setQuantity(n);
            part.assignId();
            listedItem.setQuantity(listedItem.getQuantity() - n);
            sale = new AuctionItem();
            sale.setItem(part);
            sale.setAccountID(listing.getAccountID());
            sale.setSellerUserId(listing.getSellerUserId());
            sale.setCharID(listing.getCharID());
            sale.setCharName(listing.getCharName());
            sale.setBidWorld(listing.getBidWorld());
            sale.setDirectPrice(listing.getDirectPrice());
            sale.setRegDate(listing.getRegDate());
            sale.setEndDate(listing.getEndDate());
            sale.setDeposit(0); // the listing keeps its fee; it comes back with the last sale
            world.addAuction(sale, false);
            tx.item(listedItem).auction(listing).detachItem(part);
        }
        sale.setState(AuctionState.Sold);
        sale.setPrice(cost);
        sale.setSoldQuantity(n);
        sale.setSoldDate(now);
        sale.setBuyerAccountId(chr.getAccount().getId());
        sale.setBuyerUserId(chr.getUser().getId());
        sale.setBidUserID(chr.getId());
        sale.setBidUsername(chr.getName());
        tx.auction(sale).log("buy", sale, chr, sale.getAccountID(), sale.getItem(), n, cost);
        return sale;
    }

    private static void sendBought(Char chr, World world, AuctionItem listing, AuctionItem sale) {
        chr.write(AuctionPacket.listingUpdate(chr, listing));
        if (sale == listing) {
            chr.write(AuctionPacket.searchRemove(listing.getId()));
        }
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, chr, sale));
        notifySeller(world, sale, sale.getSoldQuantity(), sale == listing ? 0 : listing.getQuantity());
    }

    // 20-22 (Complete tab) and 40-42 (previous-auction claim record): both start with the entry id
    private static void handleCollect(Char chr, long id, int type, World world) {
        AuctionItem ai = world.getAuctionById(id);
        if (ai == null) {
            fail(chr, type, "There's nothing to collect for that entry.");
            return;
        }
        boolean seller = AuctionPacket.isSeller(ai, chr);
        boolean buyer = AuctionPacket.isBuyer(ai, chr);
        if (seller && ai.getState() == AuctionState.Sold && !ai.isSellerCollected()) {
            collectMesos(chr, ai, type);
        } else if (buyer && ai.getState() == AuctionState.Sold && !ai.isBuyerCollected()) {
            collectItem(chr, ai, type, true);
        } else if (seller && ai.getState() == AuctionState.Expire) {
            collectItem(chr, ai, type, false);
        } else {
            fail(chr, type, "That entry was already collected.");
            chr.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, chr, ai));
        }
    }

    /** The client's payout (0x1404a6520): commission per unit, rounded down, at least 1. */
    public static long commission(AuctionItem ai) {
        long total = ai.getPrice();
        long unit = ai.getDirectPrice();
        long count = 1;
        long base = total;
        if (unit != total && unit > 0) {
            count = total / unit;
            base = unit;
        }
        return Math.max(1, base * COMMISSION_PERCENT / 100 * count);
    }

    private static void collectMesos(Char chr, AuctionItem ai, int type) {
        long payout = ai.getPrice() + ai.getDeposit() - commission(ai);
        if (payout <= 0 || !chr.canAddMoney(payout)) {
            fail(chr, type, "You can't hold that many mesos.");
            return;
        }
        chr.addMoney(payout);
        ai.setSellerCollected(true);
        if (ai.isBuyerCollected()) {
            ai.setState(AuctionState.SoldDone);
        }
        if (!new AuctionTx().auction(ai).money(chr).log("collect_mesos", ai, chr, ai.getBuyerAccountId(), null,
                ai.getSoldQuantity(), payout).commit()) {
            log.error("Auction payout {} kept in memory only (database write failed)", ai.getId());
        }
        chr.write(AuctionPacket.message(type, OK));
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, chr, ai));
    }

    private static void collectItem(Char chr, AuctionItem ai, int type, boolean bought) {
        Item item = ai.getItem();
        if (item == null) {
            fail(chr, type, "That entry was already collected.");
            return;
        }
        if (!bought && item.isCash() && ai.getCharID() != chr.getId()) {
            fail(chr, type, "Only " + ai.getCharName() + " can take this item back.");
            return;
        }
        if (!chr.canHold(item, item.getQuantity())) {
            fail(chr, type, "Make room in your inventory first.");
            return;
        }
        if (bought && item instanceof Equip equip) {
            equip.removeScissor();
            if (equip.isCash()) {
                equip.addAttribute(EquipAttribute.Untradable);
            }
        }
        InvType invType = item.getInvType();
        int quantity = item.getQuantity();
        chr.addItemToInventory(item);
        ai.releaseItem();
        if (bought) {
            ai.setBuyerCollected(true);
            if (ai.isSellerCollected()) {
                ai.setState(AuctionState.SoldDone);
            }
        } else {
            ai.setState(AuctionState.Done);
        }
        AuctionTx tx = new AuctionTx().auction(ai);
        for (Item it : new ArrayList<>(chr.getInventoryByType(invType).getItems())) {
            tx.item(it);
        }
        if (item.getInventory() == null) {
            // merged into a stack the character already had
            tx.sql("DELETE FROM items WHERE id = ? AND inventoryid IS NULL AND trunkid IS NULL", item.getId());
        }
        tx.log(bought ? "collect_item" : "reclaim", ai, chr, bought ? ai.getAccountID() : 0, item, quantity, 0);
        if (!tx.commit()) {
            log.error("Auction collect {} kept in memory only (database write failed)", ai.getId());
        }
        chr.write(AuctionPacket.message(type, OK));
        chr.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, chr, ai));
    }

    // 15/16: 1 a; a == 0: S, S, 1 tab, 4, 4, 1, 4, (4 4) level, (8 8) unit price, (4 4) star force, lists...
    private static void handleSearch(Char chr, InPacket inPacket, int type, World world) {
        Predicate<AuctionItem> filter = a -> true;
        if (inPacket.decodeByte() == 0) {
            String name1 = inPacket.decodeString().toLowerCase();
            String name2 = inPacket.decodeString().toLowerCase();
            int tab = inPacket.decodeByte();
            inPacket.decodeInt();
            inPacket.decodeInt();
            inPacket.decodeByte();
            inPacket.decodeInt();
            int minLv = inPacket.decodeInt();
            int maxLv = inPacket.decodeInt();
            long minPrice = inPacket.decodeLong();
            long maxPrice = inPacket.decodeLong();
            filter = a -> matchesName(a, name1, name2) && matchesTab(a, tab)
                    && inRange(a.getDirectPrice(), minPrice, maxPrice) && inRange(level(a), minLv, maxLv);
        }
        List<AuctionItem> items = type == REQ_SEARCH ? listings(world, filter) : recentSales(world, filter);
        chr.write(AuctionPacket.searchResult(type, chr, items));
    }

    private static boolean inRange(long v, long min, long max) {
        // 0 or negative bounds mean "no limit"
        return (min <= 0 || v >= min) && (max <= 0 || v <= max);
    }

    private static int level(AuctionItem a) {
        return a.getItem() instanceof Equip e ? e.getReqLevel() : 0;
    }

    private static boolean matchesName(AuctionItem a, String name1, String name2) {
        if (name1.isEmpty() && name2.isEmpty()) {
            return true;
        }
        String name = StringData.getItemStringById(a.getItemId());
        if (name == null) {
            return false;
        }
        name = name.toLowerCase();
        String bare = name.replace(" ", "");
        return (!name1.isEmpty() && (name.contains(name1) || bare.contains(name1.replace(" ", ""))))
                || (!name2.isEmpty() && (name.contains(name2) || bare.contains(name2.replace(" ", ""))));
    }

    // tabs follow Etc/AuctionData.img ItemDetailCategory_*: 0 armor, 1 weapon, 2 consume, 3 cash, 4 etc; 5+ all
    private static boolean matchesTab(AuctionItem a, int tab) {
        int id = a.getItemId();
        boolean cash = a.getItem() != null && a.getItem().isCash();
        return switch (tab) {
            case 0 -> ItemConstants.isEquip(id) && !ItemConstants.isWeapon(id) && !cash;
            case 1 -> ItemConstants.isWeapon(id) && !cash;
            case 2 -> id / 1000000 == 2 && !cash; // hardcode-ok
            case 3 -> cash;
            case 4 -> (id / 1000000 == 3 || id / 1000000 == 4) && !cash; // hardcode-ok
            default -> true;
        };
    }

    private static List<AuctionItem> listings(World world, Predicate<AuctionItem> filter) {
        return world.getAuctionHouse().stream()
                .filter(a -> a.getItem() != null && a.getState() == AuctionState.Init && !a.getEndDate().isExpired())
                .filter(filter)
                .sorted(Comparator.comparingLong(AuctionItem::getDirectPrice))
                .limit(LIST_MAX)
                .collect(Collectors.toList());
    }

    private static List<AuctionItem> recentSales(World world, Predicate<AuctionItem> filter) {
        return world.getAuctionHouse().stream()
                .filter(a -> (a.getState() == AuctionState.Sold || a.getState() == AuctionState.SoldDone)
                        && a.getSoldDate() != null)
                .filter(filter)
                .sorted(Comparator.comparing((AuctionItem a) -> a.getSoldDate().toMillis()).reversed())
                .limit(LIST_MAX)
                .collect(Collectors.toList());
    }

    private static List<AuctionItem> selling(Char chr, World world) {
        int acc = chr.getAccount().getId();
        return world.getAuctionHouse().stream()
                .filter(a -> a.getAccountID() == acc && a.getItem() != null && a.getState() == AuctionState.Init)
                .collect(Collectors.toList());
    }

    private static boolean waiting(AuctionItem a, Char chr) {
        if (a.getState() == AuctionState.Sold) {
            return (AuctionPacket.isSeller(a, chr) && !a.isSellerCollected())
                    || (AuctionPacket.isBuyer(a, chr) && !a.isBuyerCollected());
        }
        return a.getState() == AuctionState.Expire && AuctionPacket.isSeller(a, chr);
    }

    private static List<AuctionItem> complete(Char chr, World world) {
        return world.getAuctionHouse().stream()
                .filter(a -> waiting(a, chr))
                .sorted(Comparator.comparingLong(AuctionItem::getId))
                .collect(Collectors.toList());
    }

    private static long waitingPurchases(Char chr, World world) {
        return world.getAuctionHouse().stream()
                .filter(a -> a.getState() == AuctionState.Sold && AuctionPacket.isBuyer(a, chr) && !a.isBuyerCollected())
                .count();
    }

    /** Listings past their end time become unsold; the seller takes the item back from the Complete tab. */
    private static void expireDue(World world) {
        List<AuctionItem> due = world.getAuctionHouse().stream()
                .filter(a -> a.getState() == AuctionState.Init && a.getEndDate() != null && a.getEndDate().isExpired())
                .collect(Collectors.toList());
        if (due.isEmpty()) {
            return;
        }
        AuctionTx tx = new AuctionTx();
        for (AuctionItem a : due) {
            a.setState(AuctionState.Expire);
            tx.auction(a);
        }
        if (!tx.commit()) {
            log.error("Could not save {} expired auction listings", due.size());
        }
    }

    private static Quest wishQuest(Char chr) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(WISH_QR);
        if (quest == null) {
            quest = QuestData.createQuestFromId(WISH_QR);
            qm.addQuest(quest);
        }
        return quest;
    }

    private static List<Long> wishIds(Char chr) {
        Quest quest = wishQuest(chr);
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < WISH_SLOTS; i++) {
            try {
                String v = quest.getProperty(String.valueOf(i));
                ids.add(v == null || v.isEmpty() ? 0L : Long.parseLong(v));
            } catch (NumberFormatException e) {
                ids.add(0L);
            }
        }
        return ids;
    }

    private static List<AuctionItem> wishItems(Char chr, World world) {
        List<AuctionItem> items = new ArrayList<>();
        for (long id : wishIds(chr)) {
            AuctionItem ai = id == 0 ? null : world.getAuctionById(id);
            if (ai != null && ai.getItem() != null && ai.getState() == AuctionState.Init) {
                items.add(ai);
            }
        }
        return items;
    }

    private static void handleWishAdd(Char chr, long id, World world) {
        AuctionItem ai = world.getAuctionById(id);
        List<Long> ids = wishIds(chr);
        int free = ids.indexOf(0L);
        if (ai == null || ai.getState() != AuctionState.Init || ids.contains(id) || free < 0) {
            fail(chr, REQ_WISH_ADD, free < 0 ? "Your wish list is full." : null);
            return;
        }
        wishQuest(chr).setProperty(String.valueOf(free), String.valueOf(id));
        chr.write(AuctionPacket.message(REQ_WISH_ADD, OK));
        chr.write(AuctionPacket.wishAdd(chr, ai));
    }

    private static void handleWishRemove(Char chr, long id) {
        int slot = wishIds(chr).indexOf(id);
        if (slot >= 0) {
            wishQuest(chr).setProperty(String.valueOf(slot), "0");
        }
        chr.write(AuctionPacket.message(REQ_WISH_REMOVE, OK));
        chr.write(AuctionPacket.wishRemove(chr, id));
    }

    private static void notifySeller(World world, AuctionItem sale, int bought, int remaining) {
        Account sellerAccount = world.getAccountById(sale.getAccountID());
        Char seller = sellerAccount == null ? null : sellerAccount.getCurrentChr();
        if (seller == null) {
            return;
        }
        seller.chatMessage(ChatType.GameDesc, "One of your items sold at the Auction House: %s (%d). Remaining: %d",
                sale.getItemName(), bought, remaining);
        if (seller.isInAuctionHouse()) {
            seller.write(AuctionPacket.itemList(AuctionPacket.SELLING_LIST, seller, selling(seller, world)));
            seller.write(AuctionPacket.itemUpdate(AuctionPacket.COMPLETE_UPDATE, seller, sale));
        }
    }
}
