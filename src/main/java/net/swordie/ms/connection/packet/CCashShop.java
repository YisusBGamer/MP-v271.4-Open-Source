package net.swordie.ms.connection.packet;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.CashItemType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.shop.cashshop.CashItemInfo;

import java.util.List;

/**
 * Created on 4/23/2018.
 */
public class CCashShop {
    public static OutPacket queryCashResult(Char chr) {
        User user = chr.getUser();
        Account account = chr.getAccount();

        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_QUERY_CASH_RESULT);

        // v271 (0x142058710): NX (one balance: Prepaid + Credit, both spendable), Maple Points, Reward Points;
        // a negative value makes the client ignore the whole packet
        long nx = (long) Math.max(0, account.getNxPrepaid()) + Math.max(0, account.getNxCredit());
        outPacket.encodeInt((int) Math.min(Integer.MAX_VALUE, nx));
        outPacket.encodeInt(Math.max(0, user.getMaplePoints()));
        outPacket.encodeInt(Math.max(0, chr.getRewardPoints()));

        return outPacket;
    }

    public static OutPacket cashItemResBuyDone(CashItemInfo cashItemInfo, FileTime registerDate, CashItemInfo receiveBonus) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_Buy_Done.getVal());

        cashItemInfo.encode(outPacket);

        boolean hasRegisterDate = registerDate != null;
        outPacket.encodeInt(hasRegisterDate ? 1 : 0);
        if (hasRegisterDate) {
            outPacket.encodeFT(registerDate);
        }

        boolean hasReceiveBonus = receiveBonus != null;
        outPacket.encodeByte(hasReceiveBonus);
        if (receiveBonus != null) {
            receiveBonus.encode(outPacket);
        }

        return outPacket;
    }

    public static OutPacket error() {
        return message(CashItemType.FailReason_DBQueryFailed);
    }

    public static OutPacket resMoveLtoSDone(Item item) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_MoveLtoS_Done.getVal());

        outPacket.encodeByte(true); // bExclRequestSent
        outPacket.encodeShort(item.getBagIndex());
        item.encode(outPacket);

        int size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeLong(0); // sn
        }

        boolean bool = false;
        outPacket.encodeByte(bool);
        if (bool) {
            new CashItemInfo().encode(outPacket); // bonus cash item
        }

        return outPacket;
    }

    public static OutPacket resMoveStoLDone(CashItemInfo cii, Item extraMovedItem) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_MoveStoL_Done.getVal());

        boolean extraItem = extraMovedItem != null;
        outPacket.encodeByte(extraItem);
        if (extraItem) {
            outPacket.encodeLong(extraMovedItem.getId());
            outPacket.encodeInt(extraMovedItem.getItemId());
            outPacket.encodeInt(extraMovedItem.getBagIndex());
        }
        outPacket.encode(cii);

        return outPacket;
    }

    public static OutPacket loadLockerDone(Account account) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_LoadLocker_Done.getVal());

        // v265: trunk slots, character slots, int, then the locker list (no over-max part, no trailing shorts)
        Trunk trunk = account.getTrunk();
        List<CashItemInfo> locker = trunk.getLocker();
        int lockerSize = Math.min(locker.size(), GameConstants.MAX_LOCKER_SIZE);

        outPacket.encodeShort(trunk.getSlotCount());
        outPacket.encodeShort(account.getUser().getCharacterSlots());
        outPacket.encodeInt(0);
        outPacket.encodeShort(lockerSize);
        for (int i = 0; i < lockerSize; i++) {
            locker.get(i).encode(outPacket);
        }

        return outPacket;
    }

    public static OutPacket rebateDone(CashItemInfo deletedItem) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_Rebate_Done.getVal());
        outPacket.encodeLong(deletedItem.getId());

        // either extra deleted items or overflow items that are now seeable
        int size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeLong(0); // liSN
        }

        return outPacket;
    }

    /**
     * Failure notice as Buy_Failed (15, 0x142059f50): unlocks the window and shows the reason's text. Sub-type 5 (the
     * old choice) shows the text and then sends the player out of the Cash Shop (empty 252), on v265 too.
     */
    public static OutPacket message(CashItemType failReason) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_Buy_Failed.getVal());
        outPacket.encodeByte(failReason.getVal());

        return outPacket;
    }

    /** v265 coupon done (sub-type 16, client 0x141c639c0): 1 n, n x locker item, 4 Maple Points, 4 m, m x (4 item,
     *  4 count), 4 mesos. */
    public static OutPacket couponDone(List<CashItemInfo> lockerItems, int maplePoints, List<int[]> items, long mesos) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(16);
        outPacket.encodeByte(lockerItems.size());
        for (CashItemInfo cii : lockerItems) {
            cii.encode(outPacket);
        }
        outPacket.encodeInt(maplePoints);
        outPacket.encodeInt(items.size());
        for (int[] item : items) {
            outPacket.encodeInt(item[0]);
            outPacket.encodeInt(item[1]);
        }
        outPacket.encodeInt((int) Math.min(Integer.MAX_VALUE, mesos));

        return outPacket;
    }

    /** v265 coupon failed (sub-type 18): 1 reason (14 wrong number, 16 not now, 17 expired, 18 already used). */
    public static OutPacket couponFailed(int reason) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(18);
        outPacket.encodeByte(reason);

        return outPacket;
    }

    /** Gift done (sub-type 23, v271 0x14205c410): S recipient, 4 item, 4 count (v265: 2 count). */
    public static OutPacket giftDone(String recipient, int itemId, int count) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_Gift_Done.getVal());
        outPacket.encodeString(recipient);
        outPacket.encodeInt(itemId);
        outPacket.encodeInt(count);

        return outPacket;
    }

    /** Gift failed (sub-type 24, 0x14205c5a0): 1 reason (an int follows only for reasons 0x1f / 0x20; reasons below 3
     *  send the player out of the Cash Shop). */
    public static OutPacket giftFailed(CashItemType reason) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_Gift_Failed.getVal());
        outPacket.encodeByte(reason.getVal());

        return outPacket;
    }

    public static OutPacket fullInventoryMsg() {
        return message(CashItemType.FailReason_ItemLockerIsFull);
    }

    public static OutPacket extraInfoResult(int sn, int info) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(107);
        outPacket.encodeInt(sn);
        outPacket.encodeInt(info);

        return outPacket;
    }

    public static OutPacket unk3() {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket unk6() {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        int size = 0;
        outPacket.encodeShort(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeArr(new byte[70]); // unsure what this struct is
        }

        return outPacket;
    }

    public static OutPacket incSlotCountDone(int invType, int slotCount) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_IncSlotCount_Done.getVal());

        outPacket.encodeByte(invType);
        outPacket.encodeShort(slotCount);

        return outPacket;
    }

    public static OutPacket incSlotCountFailed(CashItemType failReason) {
        return message(failReason);
    }

    public static OutPacket incTrunkCountDone(Trunk trunk) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_IncTrunkCount_Done.getVal());
        outPacket.encodeShort(trunk.getSlotCount());

        return outPacket;
    }

    public static OutPacket incCharSlotCountDone(int newCharCount) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_IncCharSlotCount_Done.getVal());
        outPacket.encodeShort(newCharCount);

        return outPacket;
    }

    public static OutPacket incCharSlotCountFailed(CashItemType reason) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);

        outPacket.encodeByte(CashItemType.Res_IncCharSlotCount_Failed.getVal());
        outPacket.encodeByte(reason.getVal()); // the client reads one byte

        return outPacket;
    }

    public static final int WISH_LOAD_DONE = 8, WISH_SET_DONE = 12;

    /** v271 2703 sub-types 8 (load) / 12 (set done, unlocks the window): 12 commodity SNs (0 = empty slot). */
    public static OutPacket wishList(int type, int[] sns) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_CASH_ITEM_RESULT);
        outPacket.encodeByte(type);
        for (int i = 0; i < 12; i++) {
            outPacket.encodeInt(sns != null && i < sns.length ? sns[i] : 0);
        }
        return outPacket;
    }

    private static final int REQUEST_LOCK_ONLY = 4; // outside the sub-window modes 1-3
    // CASH_SHOP_SURPRISE_ITEM_RESULT sub types (client 0x14203f340)
    private static final int SURPRISE_DONE = 0xb8, SURPRISE_FAILED = 0xb9;

    /** Releases the Cash Shop window's request lock (set by requests such as Use 1 / Use All) without other effects. */
    public static OutPacket releaseRequestLock() {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_REQUEST_LOCK_RESULT);
        outPacket.encodeByte(REQUEST_LOCK_ONLY);
        return outPacket;
    }

    /** One box opened through the old open request: updates the box stack and shows the reveal. */
    public static OutPacket surpriseItemOpened(CashItemInfo box, CashItemInfo got, int boxesLeft) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_SURPRISE_ITEM_RESULT);
        outPacket.encodeByte(SURPRISE_DONE);
        outPacket.encodeLong(box.getCashItemSN());
        outPacket.encodeInt(boxesLeft); // 0 drops the box from the locker
        outPacket.encodeInt(box.getItemId()); // matched only when the SN is -1
        outPacket.encode(got);
        outPacket.encodeInt(got.getItemId());
        outPacket.encodeByte(got.getQuantity());
        outPacket.encodeByte(0); // 0 = red text
        return outPacket;
    }

    public static OutPacket surpriseItemFailed() {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_SURPRISE_ITEM_RESULT);
        outPacket.encodeByte(SURPRISE_FAILED);
        outPacket.encodeByte(0); // 63 = too many cash items, else unknown error
        return outPacket;
    }
}
