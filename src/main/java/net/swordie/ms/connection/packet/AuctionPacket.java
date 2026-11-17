package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.AuctionState;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.auction.AuctionItem;

import java.util.Collection;

/**
 * Auction House stage packets (client dispatcher 0x142228080).
 * AUCTION_MESSAGE: request type + result code (0 done; it also unlocks the window after a request).
 * AUCTION_RESULT: type + body; item record = 0x1404a63a0.
 */
public class AuctionPacket {

    // AUCTION_RESULT types
    public static final int ON = 0;
    public static final int SEARCH = 15;
    public static final int MARKET_PRICE = 16;
    public static final int WISH_ADD = 25;
    public static final int WISH_REMOVE = 26;
    public static final int SELLING_LIST = 50;
    public static final int SELLING_ADD = 51;
    public static final int COMPLETE_LIST = 52;
    public static final int COMPLETE_UPDATE = 53;
    public static final int WISH_LIST = 54;
    public static final int SEARCH_REMOVE = 55;
    public static final int LISTING_UPDATE = 56;
    // previous-auction claims (Complete tab "oldUI" button; 60-byte records, collected with 40-42)
    public static final int OLD_CLAIM_LIST = 57;
    public static final int OLD_CLAIM_UPDATE = 58;

    // client listing states (record +0x18): the Complete tab holds 2..4
    public static final int ST_SELLING = 1;
    public static final int ST_SOLD = 3;
    public static final int ST_UNSOLD = 4;
    public static final int ST_DONE = 9;

    // side record type (0x1404a5ef0 last byte): seller 2 sold / 3 unsold, buyer 1 bought
    private static final int SIDE_LISTED = 0;
    private static final int SIDE_BOUGHT = 1;
    private static final int SIDE_SOLD = 2;
    private static final int SIDE_UNSOLD = 3;

    private static final int SEARCH_OK = 200;

    public static OutPacket message(int type, int code) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_MESSAGE);

        outPacket.encodeByte(type);
        outPacket.encodeByte(code);

        return outPacket;
    }

    public static OutPacket on() {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(ON);

        return outPacket;
    }

    // 0x1404b1010: 1 code, 1 1 1, 4 n items, 4 m extra records
    public static OutPacket searchResult(int type, Char chr, Collection<AuctionItem> items) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(type);
        outPacket.encodeByte(SEARCH_OK);
        outPacket.encodeByte(1);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(items.size());
        for (AuctionItem ai : items) {
            encodeItem(outPacket, ai, chr);
        }
        outPacket.encodeInt(0);

        return outPacket;
    }

    // 50 / 52 / 54: 4 n items (replaces the list)
    public static OutPacket itemList(int type, Char chr, Collection<AuctionItem> items) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(type);
        outPacket.encodeInt(items.size());
        for (AuctionItem ai : items) {
            encodeItem(outPacket, ai, chr);
        }

        return outPacket;
    }

    /**
     * 51 adds to the selling list. 53 moves a record: out of the selling list, into the Complete tab while its state
     * is 2..4 (out of it otherwise); 2/4 also drop it from the search results.
     */
    public static OutPacket itemUpdate(int type, Char chr, AuctionItem ai) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(type);
        encodeItem(outPacket, ai, chr);
        outPacket.encodeByte(0);

        return outPacket;
    }

    // 56: the listing as it is now (updates the search entry), then 1 + 4 world to update the wish list entry too
    public static OutPacket listingUpdate(Char chr, AuctionItem ai) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(LISTING_UPDATE);
        encodeItem(outPacket, ai, chr);
        outPacket.encodeByte(1);
        outPacket.encodeInt(worldId(chr));

        return outPacket;
    }

    /**
     * 57 (0x142124b00): 4 n, n x {60-byte record 0x140648300, 1 0, 1 has item, [item]}; 58 = one record, replaced
     * by id. The window only opens while the list is non-empty. This server has no pre-rework listings, so it is
     * always sent empty.
     * Record: 8 id, 4, 4 user id, 4 char id, 4 item id, 4 state (1/3/5 mesos -> 40, 2 item -> 41, 4 bound item ->
     * 41/42), 8 price, 8 time, 8 deposit, 4 quantity, 4 world. The client only collects its own user id and world.
     */
    public static OutPacket oldClaimList() {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(OLD_CLAIM_LIST);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket wishAdd(Char chr, AuctionItem ai) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(WISH_ADD);
        encodeItem(outPacket, ai, chr);

        return outPacket;
    }

    public static OutPacket wishRemove(Char chr, long auctionId) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(WISH_REMOVE);
        outPacket.encodeLong(auctionId);
        outPacket.encodeInt(worldId(chr));

        return outPacket;
    }

    public static OutPacket searchRemove(long auctionId) {
        OutPacket outPacket = new OutPacket(OutHeader.AUCTION_RESULT);

        outPacket.encodeByte(SEARCH_REMOVE);
        outPacket.encodeLong(auctionId);

        return outPacket;
    }

    private static int worldId(Char chr) {
        return chr.getWorld().getWorldId().getVal();
    }

    public static boolean isSeller(AuctionItem ai, Char chr) {
        return ai.getAccountID() == chr.getAccount().getId();
    }

    public static boolean isBuyer(AuctionItem ai, Char chr) {
        return ai.getBuyerAccountId() != 0 && ai.getBuyerAccountId() == chr.getAccount().getId();
    }

    /** The state this viewer sees: a side that was already collected shows as done. */
    public static int clientState(AuctionItem ai, Char chr) {
        AuctionState s = ai.getState();
        if (s == null) {
            return ST_DONE;
        }
        return switch (s) {
            case Init -> ST_SELLING;
            case Sold, SoldDone -> {
                if (isSeller(ai, chr) && (ai.isSellerCollected() || s == AuctionState.SoldDone)) {
                    yield ST_DONE;
                }
                if (isBuyer(ai, chr) && (ai.isBuyerCollected() || s == AuctionState.SoldDone)) {
                    yield ST_DONE;
                }
                yield ST_SOLD;
            }
            case Expire -> ST_UNSOLD;
            default -> ST_DONE;
        };
    }

    // 0x1404a6060 fields, then the seller and buyer side records (0x1404a5ef0, or a lone world byte), then the item
    public static void encodeItem(OutPacket outPacket, AuctionItem ai, Char chr) {
        int world = worldId(chr);
        int state = clientState(ai, chr);
        boolean sold = ai.getState() == AuctionState.Sold || ai.getState() == AuctionState.SoldDone;
        int quantity = ai.getQuantity();
        long unit = ai.getDirectPrice();
        long total = sold && ai.getPrice() > 0 ? ai.getPrice() : unit * quantity;
        boolean seller = isSeller(ai, chr);
        FileTime reg = ai.getRegDate() != null ? ai.getRegDate() : FileTime.currentTime();
        FileTime end = ai.getEndDate() != null ? ai.getEndDate() : FileTime.MAX_TIME();
        FileTime soldAt = sold && ai.getSoldDate() != null ? ai.getSoldDate() : null;

        outPacket.encodeLong(ai.getId());
        outPacket.encodeInt(world);
        outPacket.encodeByte(1);
        outPacket.encodeInt(ai.getItemId());
        outPacket.encodeInt(quantity);
        outPacket.encodeByte(state);
        outPacket.encodeLong(total);
        outPacket.encodeLong(seller ? ai.getDeposit() : 0); // refunded with the sale (client payout = price + this - fee)
        outPacket.encodeFT(reg);
        outPacket.encodeFT(end);
        outPacket.encodeFT(soldAt != null ? soldAt : FileTime.MIN_TIME());
        outPacket.encodeFT(soldAt != null ? FileTime.fromDate(soldAt.toLocalDateTime().plusYears(1)) : FileTime.MAX_TIME());
        outPacket.encodeLong(unit);
        outPacket.encodeDouble(unit);
        outPacket.encodeInt(ai.getItemId());
        outPacket.encodeInt(-100);

        // the lone byte is the world too: the client treats a different one as a cross-world listing
        int sellerSide = !seller ? -1 : switch (state) {
            case ST_SELLING -> SIDE_LISTED;
            case ST_SOLD -> SIDE_SOLD;
            case ST_UNSOLD -> SIDE_UNSOLD;
            default -> -1;
        };
        outPacket.encodeByte(sellerSide >= 0);
        if (sellerSide >= 0) {
            outPacket.encodeLong(ai.getSellerUserId()); // non-zero: the owner can't buy it
            encodeSide(outPacket, world, ai.getSellerUserId(), ai.getCharID(), soldAt, sellerSide);
        } else {
            outPacket.encodeByte(world);
        }
        boolean buyerSide = state == ST_SOLD && isBuyer(ai, chr);
        outPacket.encodeByte(buyerSide);
        if (buyerSide) {
            encodeSide(outPacket, world, ai.getBuyerUserId(), ai.getBidUserID(), soldAt, SIDE_BOUGHT);
        } else {
            outPacket.encodeByte(world);
        }
        Item item = ai.getItem();
        if (item == null) {
            item = ItemData.getItemDeepCopy(ai.getItemId());
            if (item == null) {
                item = new Item(ai.getItemId(), 0, 0, FileTime.MAX_TIME(),
                        net.swordie.ms.enums.InvType.ETC, false, Item.Type.ITEM);
            }
            item.setQuantity(Math.max(1, quantity));
        }
        item.encode(outPacket);
    }

    // 0x1404a5ef0: 1 world, 4 user id, 4 character id (only that character may collect some items), 4, 8 time, 1 type
    private static void encodeSide(OutPacket outPacket, int world, int userId, int charId, FileTime time, int type) {
        outPacket.encodeByte(world);
        outPacket.encodeInt(userId);
        outPacket.encodeInt(charId);
        outPacket.encodeInt(charId);
        outPacket.encodeFT(time != null ? time : FileTime.MIN_TIME());
        outPacket.encodeByte(type);
    }
}
