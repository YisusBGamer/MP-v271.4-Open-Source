package net.swordie.ms.handlers.life;

import net.swordie.ms.client.anticheat.Offense;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.ShopDlg;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.CustomConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.DailyEntry;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.InventoryOperation;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.EquipItemInfo;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.shop.NpcShopDlg;
import net.swordie.ms.world.shop.NpcShopItem;
import net.swordie.ms.world.shop.RechargePrices;
import net.swordie.ms.world.shop.result.ShopResult;
import net.swordie.ms.world.shop.result.ShopResultType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShopModule {

    private static final Logger log = LogManager.getLogger();

    /** The price a regular shop's Collector pays for an Intense Power Crystal. */
    public static long getPowerCrystalCollectorPrice(Item item) {
        if (item == null || item.getPowerCrystalInfo() == null) {
            return 0;
        }
        long price = item.getPowerCrystalInfo().getPrice();
        if (price <= 0) { // made before its boss had a price (Normal/Hard Lucid)
            price = net.swordie.ms.constants.BossLootConstants.getPowerCrystalMesoByCrystalBossId(
                    item.getPowerCrystalInfo().getBossId());
        }
        return net.swordie.ms.constants.BossLootConstants.getPowerCrystalSellPrice(price);
    }

    /** GMS: 180 crystal sales a week per account, and 14 of them per character */
    public static final int POWER_CRYSTAL_CHAR_WEEKLY = 14;
    private static final int POWER_CRYSTAL_CHAR_QR = 89999998; // server-only record: week, sold

    /** Remaining weekly crystal sales, shared by NPC shops and @sell. */
    public static int getPowerCrystalSaleLimit(Char chr) {
        return Math.max(0, Math.min(chr.getAccount().getRemainingEntries(DailyEntry.PowerCrystal),
                POWER_CRYSTAL_CHAR_WEEKLY - charCrystalsSold(chr)));
    }

    private static String crystalWeek() {
        return net.swordie.ms.util.ResetClock.weekStart(java.time.DayOfWeek.THURSDAY).toString();
    }

    private static int charCrystalsSold(Char chr) {
        var qm = chr.getQuestManager();
        if (qm.getHiddenRecord(POWER_CRYSTAL_CHAR_QR) == null) {
            return 0;
        }
        var q = qm.getQuestById(POWER_CRYSTAL_CHAR_QR);
        if (!crystalWeek().equals(q.getProperty("week"))) {
            return 0;
        }
        String n = q.getProperty("sold");
        return Util.isNumber(n) ? Integer.parseInt(n) : 0;
    }

    private static void countCharCrystalSale(Char chr) {
        int sold = charCrystalsSold(chr) + 1;
        chr.getQuestManager().setHiddenRecord(POWER_CRYSTAL_CHAR_QR, "week=" + crystalWeek() + ";sold=" + sold);
    }

    /** Sells one unstacked Intense Power Crystal at the same price and account limit as the Collector. */
    public static boolean sellPowerCrystalToCollector(Char chr, Item item) {
        if (item == null || item.getPowerCrystalInfo() == null || item.getQuantity() != 1
                || !chr.hasItemCount(item.getItemId(), 1) || getPowerCrystalSaleLimit(chr) <= 0) {
            return false;
        }
        long price = getPowerCrystalCollectorPrice(item);
        if (price <= 0 || !chr.canAddMoney(price)) {
            return false;
        }
        var quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.POWER_CRYSTAL_QUEST);
        chr.getAccount().reduceDailyEntry(DailyEntry.PowerCrystal);
        countCharCrystalSale(chr);
        quest.setProperty("count", chr.getAccount().getRemainingEntries(DailyEntry.PowerCrystal));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        chr.consumeItem(item, 1);
        chr.addMoney(price);
        return true;
    }

    /** Past the meso cap the rest goes into Golden Maple Leaves; with no room for them the sale is refused. */
    private static boolean canTakeMesos(Char chr, long mesos) {
        if (chr.canAddMoney(mesos)) {
            return true;
        }
        chr.chatMessage("You can't carry that many mesos. Make room in your Etc inventory for Golden Maple Leaves.");
        chr.dispose();
        return false;
    }

    public static void handleShopSell(Char chr, InPacket inPacket, NpcShopDlg nsd) {
        var slot = inPacket.decodeShort();
        var itemId = inPacket.decodeInt();
        var quantity = (short) Math.min(Short.MAX_VALUE, inPacket.decodeInt()); // v271: 4 bytes
        InvType it = ItemConstants.getInvTypeByItemID(itemId);
        var item = chr.getInventoryByType(it).getItemBySlot(slot);

        if (item == null || item.getItemId() != itemId || item.getQuantity() < quantity) {
            chr.chatMessage("Could not find that item.");
            return;
        }

        if (quantity <= 0) {
            // 0 would pay the full price of an equip while consuming nothing
            chr.getOffenseManager().addOffense(Offense.Type.Editing,
                    "User tried selling a zero or negative quantity to NPC shop");
            chr.dispose();
            return;
        }
        if (ItemConstants.isEquip(itemId)) {
            quantity = 1;
        }
        var sellInfo = ItemConstants.isEquip(itemId) ? null : ItemData.getItemInfoByID(itemId);
        if (sellInfo != null && sellInfo.isNotSale()) {
            // Item.wz notSale: the client doesn't offer these for sale
            chr.getOffenseManager().addOffense(Offense.Type.Editing,
                    String.format("Possible hack: User tried to sell item %d that can't be sold", itemId));
            chr.dispose();
            return;
        }

        if (!chr.hasItemCount(itemId, quantity)) {
            chr.getOffenseManager().addOffense(Offense.Type.Editing,
                    String.format("Possible hack: User tried to sell %d amount of item %d while owning less",
                            quantity, itemId));
            chr.dispose();
            return;
        }

        boolean isBundle = ItemConstants.isRechargable(itemId);

        long cost;
        boolean equip = ItemConstants.isEquip(itemId);
        EquipItemInfo equipInfo = equip ? ((Equip) item).getInfo() : null;
        ItemInfo info = equip ? null : ItemData.getItemInfoByID(itemId);
        if (equipInfo == null && info == null) {
            chr.dispose(); // no item data to price it
            return;
        }
        if (equip) {
            cost = equipInfo.getPrice();
        } else if (isBundle) {
            quantity = (short) item.getQuantity();
            cost = info.getPrice();
        } else {
            cost = info.getPrice() * quantity;
        }
        if (item.getPowerCrystalInfo() != null) {
            cost = getPowerCrystalCollectorPrice(item);
            if (!canTakeMesos(chr, cost)) {
                return;
            }

            //Reduce available Power Crystals
            // created when missing
            var quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.POWER_CRYSTAL_QUEST);

            // The client also enforces the weekly limit, but reject over-limit sales server-side too.
            if (getPowerCrystalSaleLimit(chr) <= 0) {
                chr.getOffenseManager().addOffense(Offense.Type.Editing,
                        "Possible hack: User tried to sell an Intense Power Crystal past the weekly limit");
                chr.dispose();
                return;
            }
            chr.getAccount().reduceDailyEntry(DailyEntry.PowerCrystal);
        countCharCrystalSale(chr);
            quest.setProperty("count", chr.getAccount().getRemainingEntries(DailyEntry.PowerCrystal));
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        }

        if (item.getPowerCrystalInfo() == null) {
            // The guild's Merchandising (not for what a shop here sells back cheaply)
            var shopItem = nsd.getItems().stream().filter(i -> i.getItemID() == itemId && i.getTokenItemID() == 0
                    && i.getPointQuestID() == 0).findFirst().orElse(null);
            long shopUnit = shopItem == null ? 0 : shopItem.getPrice() / (long) Math.max(1, shopItem.getQuantity());
            long resellUnit = net.swordie.ms.handlers.social.module.GuildModule.getResellPrice(itemId);
            cost = net.swordie.ms.handlers.social.module.GuildModule.applyMerchandising(chr, cost, shopUnit, resellUnit,
                    false);
        }
        if (!canTakeMesos(chr, cost)) {
            return;
        }

        listForBuyBack(chr, item, quantity, cost); // copied before consumeItem changes the stack
        chr.consumeItem(item, quantity);
        chr.addMoney(cost);
        chr.write(ShopDlg.shopResult(ShopResult.update(chr, nsd)));
    }

    // Contributed by Arixal (from GitLab).
    /** A copy of what was sold, for buy-back at what the sale paid (the guild's Merchandising raises a sale). */
    static void listForBuyBack(Char chr, Item sold, short quantity, long paid) {
        Item buyBackItem = sold.deepCopy();
        buyBackItem.setQuantity(quantity);
        chr.addItemToBuyBack(buyBackItem, paid);
    }

    public static void handleShopRecharge(Char chr, InPacket inPacket) {
        short slot = inPacket.decodeShort();
        var item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || !ItemConstants.isRechargable(item.getItemId())) {
            chr.chatMessage(String.format("Was not able to find a rechargable item at position %d.", slot));
            return;
        }
        ItemInfo ii = ItemData.getItemInfoByID(item.getItemId());
        if (ii == null || item.getQuantity() >= ii.getSlotMax()) {
            // already full: the cost would be 0 or negative (which would give mesos)
            chr.write(ShopDlg.shopResult(ShopResult.msg(ShopResultType.Success)));
            return;
        }
        long cost = RechargePrices.cost(item.getItemId(), ii.getSlotMax() - item.getQuantity());
        if (chr.getMoney() < cost) {
            chr.write(ShopDlg.shopResult(ShopResult.msg(ShopResultType.NotEnoughMesosMsg)));
            return;
        }
        chr.deductMoney(cost, ProgressionLog.Sink.SHOPS);
        item.setQuantity(ii.getSlotMax());
        chr.write(WvsContext.inventoryOperation(true, false,
                InventoryOperation.UpdateQuantity, slot, (short) 0, 0, item));
        chr.write(ShopDlg.shopResult(ShopResult.msg(ShopResultType.Success)));
    }

    public static void handleShopBuy(Char chr, InPacket inPacket, NpcShopDlg nsd) {
        short itemIndex = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        short quantity = (short) Math.min(Short.MAX_VALUE, inPacket.decodeInt()); // v271: 4 bytes
        NpcShopItem nsi = nsd.getItemByIndex(itemIndex, chr.getBuyBack());

        if (nsi == null) {
            itemIndex -= nsd.getItems().size();
            nsi = chr.getBuyBackItemBySlot(itemIndex);
        }

        if (nsi == null || nsi.getItemID() != itemId) {
            chr.chatMessage("The server's item at that position was different than the client's.");
            log.warn(String.format("Possible hack: expected shop itemID %d, got %d (chr %d)",
                    nsi == null ? -1 : nsi.getItemID(), itemId, chr.getId()));
            return;
        }
        int nsiQuant = nsi.getQuantity();

        if ((nsi.isBuyBack() || ItemConstants.isEquip(itemId)) && quantity != 1) {
            // equips can't stack, and a buyback entry is the whole stack that was sold: both are bought one at a time
            log.warn(String.format("Possible hack: shop buy of %d x%d (buyback %b), chr %d", itemId, quantity,
                    nsi.isBuyBack(), chr.getId()));
            chr.dispose();
            return;
        }

        if (quantity <= 0 || nsiQuant < 0 || !chr.canHold(itemId, quantity * Math.max(1, nsiQuant))) {
            // a quantity of 0 would give the item (or a full stack of stars) for free
            chr.write(ShopDlg.shopResult(ShopResult.msg(ShopResultType.FullInvMsg)));
            return;
        }

        // made before paying: a shop row for an item missing from the data is refused, not charged
        Item fresh = nsi.isBuyBack() ? null : ItemData.getItemDeepCopy(itemId);
        if (!nsi.isBuyBack() && fresh == null) {
            log.warn("Shop {} sells item {} that has no item data", nsd.getShopID(), itemId);
            chr.dispose();
            return;
        }

        if (nsi.getTokenItemID() != 0) {
            long cost = (long) nsi.getTokenPrice() * quantity;
            if (cost <= Integer.MAX_VALUE && chr.hasItemCount(nsi.getTokenItemID(), (int) cost)) {
                chr.consumeItem(nsi.getTokenItemID(), (int) cost);
            } else {
                chr.write(ShopDlg.shopResult(ShopResult.msg(ShopResultType.NotEnoughMesosMsg)));
                return;
            }
        } else if (nsi.getPointQuestID() != 0) {
            var qm = chr.getQuestManager();
            var quest = nsi.getPointQuestID() == QuestConstants.UNION_COIN && chr.getUnion() != null
                    ? qm.getOrCreateQuestById(QuestConstants.UNION_COIN) // the coins live on the Legion
                    : qm.getQuestById(nsi.getPointQuestID());
            if (quest == null) {
                chr.chatMessage("Not enough points to buy this item.");
                return;
            }
            var pointsStr = quest.getProperty("point");
            int points = 0;
            if (Util.isNumber(pointsStr)) {
                points = Integer.parseInt(pointsStr);
            }
            // Legion Coins live on the Legion (shared by the account's characters); the quest is only the client's
            // display copy and can be out of date on another character, so it must not decide the price or the rest
            if (quest.getQRKey() == QuestConstants.UNION_COIN && chr.getUnion() != null) {
                points = chr.getUnion().getUnionCoin();
            }
            long pointCost = (long) nsi.getPointPrice() * quantity;
            if (points >= pointCost) {
                int left = (int) Math.max(0, points - pointCost);
                quest.setProperty("point", left);

                if (quest.getQRKey() == QuestConstants.UNION_COIN) {
                    chr.getUnion().setUnionCoin(left);
                }
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
            } else {
                chr.chatMessage("Not enough points to buy this item.");
                return;
            }
        } else {
            long cost = nsi.getPrice() * (long)quantity;
            if (!nsi.isBuyBack()) { // the guild's Merchandising
                long unit = nsi.getPrice() / (long) Math.max(1, nsi.getQuantity());
                cost = net.swordie.ms.handlers.social.module.GuildModule.applyMerchandising(chr, cost, unit,
                        net.swordie.ms.handlers.social.module.GuildModule.getResellPrice(itemId), true);
            }
            if (chr.getMoney() < cost) {
                chr.write(ShopDlg.shopResult(ShopResult.msg(ShopResultType.NotEnoughMesosMsg)));
                return;
            }
            chr.deductMoney(cost, ProgressionLog.Sink.SHOPS);
            net.swordie.ms.client.character.achievement.MapleAchievements.onShopBuy(chr, cost);
        }

        Item item;
        if (nsi.isBuyBack()) {
            item = nsi.getItem(); // exactly what was sold (its quantity, and a part-used stack of stars stays so)
            chr.removeBuyBackItem(nsi);
        } else {
            item = fresh;
            item.setQuantity(quantity);
            if (nsi.getQuantity() > 1) {
                item.setQuantity(nsi.getQuantity() * quantity);
            }
            if (ItemConstants.isRechargable(itemId)) {
                var itemInfo = ItemData.getItemInfoByID(itemId);
                item.setQuantity(itemInfo.getSlotMax());
            }
        }
        chr.addItemToInventory(item);

        if (nsi.isBuyBack()) {
            chr.write(ShopDlg.shopResult(ShopResult.update(chr, nsd)));
        } else {
            // GMS answers a purchase with the Buy result; the full refresh makes the client jump to the buyback tab
            int stock = nsi.getBuyLimit() == 0 ? -1 : nsi.getBuyLimit();
            chr.write(ShopDlg.shopResult(ShopResult.buy(0, itemId, stock, 0)));
        }
    }
}
