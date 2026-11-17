package net.swordie.ms.handlers;

import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.BossMileage;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.cashshop.IterativeBuyInfo;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.CCashShop;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SSBConstants;
import net.swordie.ms.enums.CashItemType;
import net.swordie.ms.enums.CashShopActionType;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.CommodityItem;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.shop.cashshop.CashItemInfo;
import net.swordie.ms.world.shop.cashshop.CashShop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 4/23/2018.
 */
public class CashShopHandler {
    private static final Logger log = LogManager.getLogger();
    /** payment method values the client sends (purchase dialog 0x142039a90) */
    private static final int PAY_NX_CREDIT = 1;
    private static final int PAY_MAPLE_POINTS = 2;
    private static final int PAY_NX_PREPAID = 0x20;

    @Handler(op = InHeader.CASH_SHOP_QUERY_CASH_REQUEST)
    public static void handleCashShopQueryCashRequest(Client c, InPacket inPacket) {
        BossMileage.expire(c.getChr());
        c.write(CCashShop.queryCashResult(c.getChr()));
    }

    /** v265 coupon box (client 0x141c41220 / 0x142c6e0e0): 1 0, S code, 1 from another window. */
    @Handler(op = InHeader.CASH_SHOP_CHECK_COUPON_REQUEST)
    public static void handleCashShopCheckCouponRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr == null) {
            return;
        }
        inPacket.decodeByte();
        String code = inPacket.decodeString();
        net.swordie.ms.client.character.cashshop.CouponModule.useCoupon(chr, code);
    }

    @Handler(op = InHeader.CASH_SHOP_CASH_ITEM_REQUEST)
    public static void handleCashShopCashItemRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        User user = chr.getUser();
        Account account = chr.getAccount();
        Trunk trunk = account.getTrunk();
        byte type = inPacket.decodeByte();
        CashItemType cit = CashItemType.getRequestTypeByVal(type);
        if (cit == null) {
            log.warn("Unknown cash shop cash item request {}", type);
            c.write(CCashShop.error());
            return;
        }

        switch (cit) {
            case Req_Buy -> handleBuy(c, inPacket, chr, user, account, trunk);
            case Req_Gift -> handleGift(c, inPacket, chr, user, account);
            case Req_MoveLtoS -> handleMoveLtoS(c, inPacket, chr, trunk);
            case Req_MoveStoL -> handleMoveStoL(c, inPacket, chr, trunk);
            case Req_SetWish -> handleSetWish(chr, inPacket);
            case Req_IncSlotCount -> handleIncSlotCount(c, inPacket, chr, user, account, trunk);
            case Req_IncTrunkCount -> handleIncTrunkCount(c, inPacket, user, account, trunk);
            case Req_IncCharSlotCount -> handleIncCharSlotCount(c, inPacket, user, account);
            case Req_IncBuyCharCount -> handleBuyCharCount(c, inPacket, user, account, chr);
            case Req_Rebate -> handleRebate(chr, inPacket, trunk);
            case Req_BuyPackage, Req_GiftPackage -> handleBuyPackage(c, inPacket, user, account, chr);
            case Req_UseLockerItems -> handleUseLockerItems(c, inPacket, chr, trunk);
            case Req_ExtraInfo -> {
                // the client asks for every item it shows (4 sn, 4 3); GMS answers only items with a sale period,
                // with Out 362 (4 sn, 4 3, 4 1, FT end). This shop has none, so no answer, as GMS for most items
                inPacket.decodeInt();
            }
            default -> {
                c.write(CCashShop.error());
                log.warn("Unhandled cash shop cash item request {}", cit);
                chr.dispose();
            }
        }
    }

    /**
     * Packages (several items for one price) aren't sold on this server: the Cash Shop is for cosmetics. Nothing is
     * charged; the player is told.
     */
    private static void handleBuyPackage(Client c, InPacket inPacket, User user, Account account, Char chr) {
        c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("Packages aren't sold on this server. "
                + "Please buy the items one by one.")));
        c.write(CCashShop.error());
    }

    private static void handleBuyCharCount(Client c, InPacket inPacket, User user, Account account, Char chr) {
        var slotType = inPacket.decodeByte();
        var paymentMethod = inPacket.decodeInt();
        var commoditySn = inPacket.decodeInt();

        var comItem = EtcData.getCommodityItem(commoditySn);

        if (comItem == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }
        if (!validCommodity(c, comItem, id -> id / 10000 == 543)) {
            return;
        }

        var itemId = comItem.getItemId();
        var itemInfo = ItemData.getItemInfoByID(itemId);

        if (itemInfo == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }

        // the limit is checked before charging (the NX used to be taken anyway)
        if (user.getCharacterSlots() >= ServerConstants.MAX_CHARACTERS) {
            c.write(CCashShop.incCharSlotCountFailed(CashItemType.FailReason_LimitOverCharacter));
            return;
        }

        if (!applyCost(c, user, account, paymentMethod, comItem, 1)) return;

        user.setCharacterSlots(user.getCharacterSlots() + 1);

        c.write(CCashShop.incCharSlotCountDone(user.getCharacterSlots()));
    }

    private static void handleIncCharSlotCount(Client c, InPacket inPacket, User user, Account account) {
        var slotType = inPacket.decodeByte();
        var paymentMethod = inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        var commoditySn = inPacket.decodeInt();

        var comItem = EtcData.getCommodityItem(commoditySn);

        if (comItem == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }
        if (!validCommodity(c, comItem, id -> id / 10000 == 543)) {
            return;
        }

        var itemId = comItem.getItemId();
        var itemInfo = ItemData.getItemInfoByID(itemId);

        if (itemInfo == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }

        if (user.getCharacterSlots() >= ServerConstants.MAX_CHARACTERS) {
            c.write(CCashShop.incCharSlotCountFailed(CashItemType.FailReason_LimitOverCharacter));
            return;
        }

        if (!applyCost(c, user, account, paymentMethod, comItem, 1)) return;

        // this used to charge and give nothing
        user.setCharacterSlots(user.getCharacterSlots() + 1);
        c.write(CCashShop.incCharSlotCountDone(user.getCharacterSlots()));
    }

    /**
     * Gift (client: S second password, 1 0, 4 commodity sn, S recipient, S message, 1). Only to a character online in
     * this world on another account; paid like a buy with NX.
     */
    private static void handleGift(Client c, InPacket inPacket, Char chr, User user, Account account) {
        inPacket.decodeString();
        inPacket.decodeByte();
        int commoditySn = inPacket.decodeInt();
        String name = inPacket.decodeString();
        inPacket.decodeString();

        CommodityItem comItem = EtcData.getCommodityItem(commoditySn);
        if (comItem == null || !validCommodity(c, comItem, id -> id / 1000000 != 9)) { // hardcode-ok
            if (comItem == null) {
                c.write(CCashShop.error());
            }
            return;
        }
        Char other = chr.getWorld() == null ? null : chr.getWorld().getCharByName(name);
        if (other == null || other.getAccount() == null) {
            c.write(CCashShop.giftFailed(CashItemType.FailReason_GiftUnknownRecipient));
            return;
        }
        if (other.getAccount().getId() == account.getId()) {
            c.write(CCashShop.giftFailed(CashItemType.FailReason_GiftSameAccount));
            return;
        }
        Trunk otherTrunk = other.getAccount().getTrunk();
        if (otherTrunk.isFullLocker()) {
            c.write(CCashShop.giftFailed(CashItemType.FailReason_GiftRecipientLockerFull));
            return;
        }
        if (!applyCost(c, user, account, 0x20, comItem, 1)) return;

        CashItemInfo cii = comItem.toCashItemInfo(other.getAccount(), 1);
        cii.setBuyCharacterID(chr.getName());
        otherTrunk.addCashItem(cii);
        log.info(String.format("Cash shop: %s gifted commodity %d (item %d) to %s.", chr.getName(), commoditySn,
                comItem.getItemId(), other.getName()));
        c.write(CCashShop.giftDone(other.getName(), comItem.getItemId(), cii.getQuantity()));
        c.write(CCashShop.queryCashResult(chr));
        if (other.isInCashShop()) {
            other.write(CCashShop.loadLockerDone(other.getAccount()));
        }
    }

    /**
     * @return how many bytes of a delete (rebate) request come before the item sn (its last 8), or -1 if too short.
     * Contributed by Dapsy (from the Discord).
     */
    static int rebateSnOffset(int length) {
        return length < 3 + 8 ? -1 : length - 8;
    }

    private static void handleRebate(Char chr, InPacket inPacket, Trunk trunk) {
        long itemSn;
        CashItemInfo cii;
        // v271 puts a short string before the sn (v265: 3 bytes, then the sn); the sn is always the last 8 bytes
        int before = rebateSnOffset(inPacket.getUnreadAmount());
        if (before < 0) {
            chr.write(CCashShop.error());
            return;
        }
        inPacket.decodeArr(before);

        itemSn = inPacket.decodeLong();
        cii = trunk.getLockerItemBySn(itemSn);

        if (cii == null) {
            chr.write(CCashShop.fullInventoryMsg());
            return;
        }

        trunk.removeCashItem(cii);
        chr.write(CCashShop.rebateDone(cii));
        chr.write(CCashShop.queryCashResult(chr));
    }

    private static void handleIncTrunkCount(Client c, InPacket inPacket, User user, Account account, Trunk trunk) {
        var slotType = inPacket.decodeByte();
        var paymentMethod = inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        var commoditySn = inPacket.decodeInt();

        var comItem = EtcData.getCommodityItem(commoditySn);

        if (comItem == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }
        if (!validCommodity(c, comItem, id -> id / 1000 == 9110)) {
            return;
        }

        var itemId = comItem.getItemId();
        var itemInfo = ItemData.getItemInfoByID(itemId);

        if (itemInfo == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }

        if (trunk.getSlotCount() >= GameConstants.MAX_TRUNK_SIZE) {
            // already at the maximum: don't take the NX for nothing
            c.write(CCashShop.error());
            return;
        }

        if (!applyCost(c, user, account, paymentMethod, comItem, 1)) return;

        int delta = itemInfo.getDelta();
        trunk.setSlotCount(Math.min(GameConstants.MAX_TRUNK_SIZE, trunk.getSlotCount() + delta));

        c.write(CCashShop.incTrunkCountDone(trunk));
        c.write(CCashShop.queryCashResult(c.getChr()));
    }

    private static void handleIncSlotCount(Client c, InPacket inPacket, Char chr, User user, Account account, Trunk trunk) {
        byte paymentMethod;
        int commoditySn;
        CommodityItem comItem;
        var slotType = inPacket.decodeByte();
        paymentMethod = inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        commoditySn = inPacket.decodeInt();

        comItem = EtcData.getCommodityItem(commoditySn);

        if (comItem == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }
        if (!validCommodity(c, comItem, id -> id / 10000 == 911)) {
            return;
        }

        var itemId = comItem.getItemId();
        var itemInfo = ItemData.getItemInfoByID(itemId);

        if (itemInfo == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }

        int invTypeVal = (itemId / 1000) % 10;
        int delta = itemInfo.getDelta();
        int slots = 0;
        Map<InvType, Integer> toExpand = new HashMap<>();

        boolean expandTrunk = false;
        switch (invTypeVal) {
            case 0 -> {
                // storage: only expanded once paid (the slots used to be added before the cost check)
                if (trunk.getSlotCount() >= GameConstants.MAX_TRUNK_SIZE) {
                    c.write(CCashShop.error());
                    return;
                }
                expandTrunk = true;
            }
            case 6 -> {
                // "all tabs to the maximum"
                for (InvType t : new InvType[]{InvType.EQUIP, InvType.CONSUME, InvType.INSTALL, InvType.ETC}) {
                    toExpand.put(t, Inventory.MAX_SLOTS);
                }
                slots = Inventory.MAX_SLOTS;
            }
            default -> toExpand.put(InvType.getInvTypeByVal(invTypeVal), delta);
        }

        // inventories already at the maximum can't grow: refuse before charging (the NX/points used to be lost)
        if (!toExpand.isEmpty()) {
            boolean canExpand = false;
            for (var invType : toExpand.keySet()) {
                var inventory = invType == null ? null : chr.getInventoryByType(invType);
                if (inventory != null && inventory.getSlots() < Inventory.MAX_SLOTS) {
                    canExpand = true;
                }
            }
            if (!canExpand) {
                c.write(CCashShop.error());
                return;
            }
        }

        if (!applyCost(c, user, account, paymentMethod, comItem, 1)) return;

        if (expandTrunk) {
            trunk.addSlotCount(delta);
            slots = trunk.getSlotCount();
        }

        boolean showMessage = true;

        for (var entry : toExpand.entrySet()) {
            var invType = entry.getKey();
            var increaseCount = entry.getValue();
            var inventory = chr.getInventoryByType(invType);

            if (inventory.getSlots() == Inventory.MAX_SLOTS) {
                showMessage = false;
                continue;
            }

            inventory.increaseSlots(increaseCount);
            slots = inventory.getSlots();
        }

        if (showMessage) {
            c.write(CCashShop.incSlotCountDone(invTypeVal, slots));
        }
        c.write(CCashShop.queryCashResult(chr));
    }

    private static void handleMoveStoL(Client c, InPacket inPacket, Char chr, Trunk trunk) {
        var itemSn = inPacket.decodeLong();
        var item = chr.getItemBySn(itemSn);

        if (item == null || trunk.isFullLocker()) {
            c.write(CCashShop.fullInventoryMsg());
            return;
        }

        var quant = item.getQuantity();

        var cii = CashItemInfo.fromItem(chr, item);
        c.write(CCashShop.resMoveStoLDone(cii, null));
        chr.consumeItemFull(item, false, true);

        item.setQuantity(quant);
        item.setInventory(null);
        item.setTrunk(null);
        trunk.addCashItem(cii);
        c.write(CCashShop.queryCashResult(chr));
    }

    private static void handleMoveLtoS(Client c, InPacket inPacket, Char chr, Trunk trunk) {
        CashItemInfo cii;
        long itemSn = inPacket.decodeLong();
        cii = trunk.getLockerItemBySn(itemSn);
        if (cii == null) {
            c.write(CCashShop.fullInventoryMsg());
            return;
        }
        Item item = cii.getItem();

        if (!chr.canHold(item)) {
            c.write(CCashShop.fullInventoryMsg());
            return;
        }

        Inventory inv = chr.getInventoryByType(item.getInvType());
        Item stack = inv == null ? null : inv.getItemByItemIDAndStackable(item.getItemId());
        trunk.removeCashItem(cii);
        chr.addItemToInventory(item);
        // the client puts the result item into its slot: when the item went onto an existing stack, that's the stack
        boolean ownSlot = inv == null || inv.getItems().stream().anyMatch(i -> i == item);
        c.write(CCashShop.resMoveLtoSDone(ownSlot || stack == null ? item : stack));
    }

    private static void handleBuy(Client c, InPacket inPacket, Char chr, User user, Account account, Trunk trunk) {
        // v271 (cart send 0x14203a0d0): 1 Maple Points, 4 payment, 1 use Reward Points, 1, 4 sn, 4 batch count
        inPacket.decodeByte();
        int paymentMethod = inPacket.decodeInt();
        boolean useRewardPoints = inPacket.decodeByte() != 0;
        inPacket.decodeByte();
        int commoditySn = inPacket.decodeInt();
        var totalBuyCount = inPacket.decodeInt();

        // save iterative buy info if we get a new batch (a count of 0 with no batch going is a single buy)
        if (totalBuyCount > 0 || chr.getIterativeBuyInfo() == null) {
            if (chr.getIterativeBuyInfo() != null) {
                chr.getIterativeBuyInfo().encodeItems(chr);
            }

            chr.setIterativeBuyInfo(new IterativeBuyInfo(Math.max(1, totalBuyCount)));
        }

        CommodityItem comItem = EtcData.getCommodityItem(commoditySn);

        if (comItem == null) {
            c.write(CCashShop.error());
            log.warn("Requested item could not be found.");
            return;
        }
        if (!validCommodity(c, comItem, id -> id / 1000000 != 9)) { // hardcode-ok
            return;
        }

        if (!applyCostWithRewardPoints(c, chr, user, account, paymentMethod, comItem, useRewardPoints)) return;

        CashItemInfo cii = comItem.toCashItemInfo(account, 1);

        var similarItem = trunk.getSimilarEndDateItem(cii);
        if (similarItem != null) {
            similarItem.addQuantity(cii.getQuantity());
            cii = similarItem;
        } else {
            trunk.addCashItem(cii);
        }

        var iterativeBuyInfo = chr.getIterativeBuyInfo();
        if (iterativeBuyInfo != null) {
            iterativeBuyInfo.addCashItemInfo(cii);

            // write packet only when our iterative buy info is done
            if (iterativeBuyInfo.isDone()) {
                iterativeBuyInfo.encodeItems(chr);
                chr.setIterativeBuyInfo(null);
            }
            chr.write(CCashShop.queryCashResult(chr));
        }
    }

    /**
     * Only commodities that are on sale (Etc.wz Commodity.img OnSale), cost something, and are the kind of item the
     * request is for can be bought: the client only offers those, but a crafted packet could name any SN (thousands
     * are off sale with price 0).
     */
    /** On sale in the client's data but not working here yet (plans/features/pulled-items.tsv) */
    private static final java.util.Set<Integer> PULLED_ITEMS = java.util.Set.of(
            ItemConstants.VIOLET_CUBE
    );

    private static boolean validCommodity(Client c, CommodityItem ci, java.util.function.IntPredicate itemIdOk) {
        if (ci == null || !ci.isOnSale() || ci.getPrice() <= 0 || !itemIdOk.test(ci.getItemId())
                || PULLED_ITEMS.contains(ci.getItemId())) {
            log.warn(String.format("Cash shop: refused commodity %s (item %d, on sale %b, price %d) for chr %s.",
                    ci == null ? "?" : String.valueOf(ci.getSn()), ci == null ? 0 : ci.getItemId(),
                    ci != null && ci.isOnSale(), ci == null ? 0 : ci.getPrice(),
                    c.getChr() == null ? "?" : String.valueOf(c.getChr().getId())));
            c.write(CCashShop.error());
            return false;
        }
        return true;
    }

    /**
     * NX purchases with the client's Reward Points box ticked: the points pay up to the item's mileageRate percent
     * (the client offers NX when NX plus those points cover the price), NX the rest.
     */
    private static boolean applyCostWithRewardPoints(Client c, Char chr, User user, Account account, int paymentMethod,
                                                     CommodityItem comItem, boolean useRewardPoints) {
        boolean nx = paymentMethod == PAY_NX_CREDIT || paymentMethod == PAY_NX_PREPAID;
        BossMileage.expire(chr);
        int points = useRewardPoints && nx ? BossMileage.usable(chr, comItem, comItem.getPrice()) : 0;
        if (!BossMileage.spend(chr, points)) {
            c.write(CCashShop.message(CashItemType.FailReason_NoRemainCashMileage));
            return false;
        }
        if (!applyCost(c, user, account, paymentMethod, comItem, 1, points)) {
            BossMileage.refund(chr, points);
            return false;
        }
        if (points > 0) {
            log.info("Character {} paid {} Reward Points for commodity {}", chr.getId(), points, comItem.getSn());
        }
        return true;
    }

    private static boolean applyCost(Client c, User user, Account account, int paymentMethod, CommodityItem comItem, int quantity) {
        return applyCost(c, user, account, paymentMethod, comItem, quantity, 0);
    }

    private static boolean applyCost(Client c, User user, Account account, int paymentMethod, CommodityItem comItem,
                                     int quantity, int paidWithPoints) {
        long cost = comItem.getPrice();
        cost *= quantity;
        cost -= paidWithPoints;

        if (cost > Integer.MAX_VALUE) {
            c.write(CCashShop.message(CashItemType.FailReason_NotEnoughMaplePoint));
            log.warn("Character does not have enough to pay for this item (Paying with " + paymentMethod + ")");
            return false;
        }

        boolean notEnoughMoney = true;
        // NX Credit and NX Prepaid are both "NX": whichever one the client has selected, spend NX Prepaid first
        // (that's what monsters drop) and only use NX Credit when Prepaid can't cover the item.
        if (net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.CASH_SHOP_PREFER_PREPAID) && (paymentMethod == PAY_NX_CREDIT || paymentMethod == PAY_NX_PREPAID)) {
            if (account.getNxPrepaid() >= cost) {
                account.deductNXPrepaid((int) cost);
                return true;
            }
            if (account.getNxCredit() >= cost) {
                account.deductNXCredit((int) cost);
                return true;
            }
        }
        switch (paymentMethod) {
            case PAY_NX_CREDIT -> {
                if (account.getNxCredit() >= cost) {
                    account.deductNXCredit((int) cost);
                    notEnoughMoney = false;
                }
            }
            case PAY_MAPLE_POINTS -> {
                if (user.getMaplePoints() >= cost) {
                    user.deductMaplePoints((int) cost);
                    notEnoughMoney = false;
                }
            }
            case PAY_NX_PREPAID -> {
                if (account.getNxPrepaid() >= cost) {
                    account.deductNXPrepaid((int) cost);
                    notEnoughMoney = false;
                }
            }
        }
        if (notEnoughMoney) {
            c.write(CCashShop.message(CashItemType.FailReason_NotEnoughMaplePoint));
            log.warn("Character does not have enough to pay for this item (Paying with " + paymentMethod + ")");
            return false;
        }
        return true;
    }

    @Handler(op = InHeader.CASH_SHOP_ACTION)
    public static void handleCashShopAction(Char chr, InPacket inPacket) {
        CashShop cashShop = Server.getInstance().getCashShop();
        byte type = inPacket.decodeByte();
        CashShopActionType csat = CashShopActionType.getByVal(type);
        if (csat == null) {
            log.warn("Unhandled cash shop cash action request {}", type);
            chr.write(CCashShop.error());
            return;
        }
        switch (csat) {
            case Req_OpenCategory -> {
                int categoryIdx = inPacket.decodeInt();
//                chr.write(CCashShop.openCategoryResult(cashShop, categoryIdx));
            }
            case Req_Favorite, Req_Leave -> {
            }
            default -> {
                chr.write(CCashShop.error());
                log.warn("Unhandled cash shop cash action request {}", csat);
                chr.dispose();
            }
        }
    }

    @Handler(op = InHeader.CASH_SHOP_TUTORIAL_DONE)
    public static void handleCashShopTutorialDone(Char chr, InPacket inPacket) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getOrCreateQuestById(QuestConstants.CASH_SHOP_TUTORIAL);
        quest.setProperty("c", 1);
        quest.setStatus(QuestStatus.Completed);
        qm.addQuest(quest);
    }

    public static final int WISH_QR = 89999961; // hidden record: the 12 wish-list SNs, comma separated

    public static int[] wishList(Char chr) {
        int[] sns = new int[12];
        String raw = chr.getQuestManager().getHiddenRecord(WISH_QR);
        if (raw != null && !raw.isEmpty()) {
            String[] parts = raw.split(",");
            for (int i = 0; i < parts.length && i < sns.length; i++) {
                try {
                    sns[i] = Integer.parseInt(parts[i].trim());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return sns;
    }

    /** v271 senders 0x1420385c0 / 0x142038980: 1 add (0) / remove (1), 4 sn, then the whole list (12 SNs). */
    private static void handleSetWish(Char chr, InPacket inPacket) {
        inPacket.decodeByte();
        inPacket.decodeInt();
        int[] sns = new int[12];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sns.length && inPacket.getUnreadAmount() >= 4; i++) {
            int sn = inPacket.decodeInt();
            sns[i] = sn != 0 && EtcData.getCommodityItem(sn) != null ? sn : 0;
            sb.append(i == 0 ? "" : ",").append(sns[i]);
        }
        chr.getQuestManager().setHiddenRecord(WISH_QR, sb.toString());
        chr.write(CCashShop.wishList(CCashShop.WISH_SET_DONE, sns));
    }

    /** Not served (meaning unknown): the balance refresh clears the window's request lock. */
    @Handler(op = InHeader.CASH_SHOP_SN_TEXT_REQUEST)
    public static void handleCashShopSnTextRequest(Char chr, InPacket inPacket) {
        log.info("[Cash] request 1872 not served: {}", inPacket);
        chr.write(CCashShop.queryCashResult(chr));
    }

    @Handler(op = InHeader.CASH_SHOP_LOOK_REQUEST)
    public static void handleCashShopLookRequest(Char chr, InPacket inPacket) {
        log.info("[Cash] request 1874 not served: {}", inPacket);
        chr.write(CCashShop.queryCashResult(chr));
    }

    @Handler(op = InHeader.CASH_SHOP_SURPRISE_ITEM_OPEN)
    public static void handleCashShopSurpriseItemOpen(Char chr, InPacket inPacket) {
        var id = inPacket.decodeLong();

        var trunk = chr.getAccount().getTrunk();
        var item = trunk.getLockerItemBySn(id);

        if (item == null) {
            chr.boxMessage("Could not find that item.");
            chr.write(CCashShop.queryCashResult(chr));
            return;
        }
        CashItemInfo got = rollSurpriseItem(chr, item);
        if (got == null) {
            chr.write(CCashShop.surpriseItemFailed());
            return;
        }
        trunk.addCashItem(got);
        int left = item.getQuantity() - 1;
        trunk.removeQuantity(item, 1);
        chr.write(CCashShop.surpriseItemOpened(item, got, left));
    }

    /** A new item from the current Surprise Style Box pool, or null for other items or an empty pool. */
    private static CashItemInfo rollSurpriseItem(Char chr, CashItemInfo box) {
        if (box.getItemId() != ItemConstants.SURPRISE_STYLE_BOX) {
            return null;
        }
        Set<DropInfo> dropInfos = SSBConstants.getCurrentSSBInfo();
        var drop = dropInfos == null ? null : Util.getRandomDropInfoByChance(dropInfos);
        return drop == null ? null : CashItemInfo.fromNewItem(chr, drop.getItemID(), 1);
    }

    /** Use 1 / Use All on a Cash Inventory box: opens up to the asked count from each locker stack. */
    private static void handleUseLockerItems(Client c, InPacket inPacket, Char chr, Trunk trunk) {
        boolean fromCashInventory = inPacket.decodeByte() != 0;
        inPacket.decodeInt(); // item id
        int n = inPacket.decodeInt();
        var mode = fromCashInventory ? WvsContext.GachaponWindowMode.CASH_INVENTORY
                : WvsContext.GachaponWindowMode.CASH_SHOP;
        for (int i = 0; i < n && inPacket.getUnreadAmount() >= 16; i++) {
            long sn = inPacket.decodeLong();
            inPacket.decodeInt();
            int count = inPacket.decodeInt();
            CashItemInfo box = trunk.getLockerItemBySn(sn);
            if (box == null) {
                continue;
            }
            List<CashItemInfo> got = new ArrayList<>();
            int open = Math.min(Math.max(1, count), box.getQuantity());
            for (int j = 0; j < open; j++) {
                CashItemInfo item = rollSurpriseItem(chr, box);
                if (item == null) {
                    break;
                }
                trunk.addCashItem(item);
                got.add(item);
            }
            if (got.isEmpty()) {
                break;
            }
            trunk.removeQuantity(box, got.size());
            // locker and request lock before the result, which opens the reveal window (modal) with its own Use buttons
            c.write(CCashShop.loadLockerDone(chr.getAccount()));
            c.write(CCashShop.releaseRequestLock());
            c.write(WvsContext.cashGachaponResult(mode, box.getItemId(),
                    got.stream().map(CashItemInfo::getItem).toList()));
            return;
        }
        c.write(CCashShop.surpriseItemFailed());
        c.write(CCashShop.releaseRequestLock());
        chr.dispose();
    }

}
