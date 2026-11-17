package net.swordie.ms.handlers.life.modules;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipAttribute;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.client.trunk.TrunkDlg;
import net.swordie.ms.client.trunk.TrunkType;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.loaders.ItemData;

public class NpcHandlerModule {
    public static void handleTrunkPutItem(InPacket inPacket, Char chr, Trunk trunk) {
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        int quantity = inPacket.decodeInt(); // v271: 4 bytes

        InvType invType = ItemConstants.getInvTypeByItemID(itemId);
        Item item = chr.getInventoryByType(invType).getItemBySlot(slot);
        if (item != null && ItemConstants.isEquip(itemId) && item.getQuantity() != 1) {
            // an equip is never a stack: storing part of one would copy it (stats, stars, potentials)
            chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_PutUnknown)));
            return;
        }
        if (item != null && quantity > 0 && item.getQuantity() >= quantity && item.getItemId() == itemId) {
            if (trunk.isFullItems()) {
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_PutNoSpace)));
                return;
            }

            var itemInfo = ItemData.getItemInfoByID(itemId);
            int slotMax = itemInfo == null ? 1 : itemInfo.getSlotMax();
            var toOverrideItem = trunk.getItemByItemID(itemId, false, item.getDateExpire());
            var overrideQuant = toOverrideItem == null ? 0 : toOverrideItem.getQuantity();

            if (quantity + overrideQuant > slotMax) {
                quantity = slotMax - overrideQuant;
            }
            if (ItemConstants.isRechargable(itemId)) {
                quantity = item.getQuantity();
            }
            if (quantity <= 0 && !ItemConstants.isRechargable(itemId)) {
                // the stack in storage is already full
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_PutNoSpace)));
                return;
            }

            chr.consumeItem(item, quantity);
            trunk.addItem(item, quantity);

            chr.write(FieldPacket.trunkDlg(TrunkDlg.update(TrunkType.TrunkRes_PutSuccess, trunk)));
        } else {
            chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_PutUnknown)));
        }
    }

    public static void handleTrunkGetItem(InPacket inPacket, Char chr, Trunk trunk) {
        byte invTypeNum = inPacket.decodeByte();
        short pos = inPacket.decodeByte();
        int quantity = inPacket.decodeInt(); // v271: 4 bytes

        if (pos >= 0 && pos < trunk.getItems().size()) {
            var trunkItem = trunk.getItemByInvIdAndPos(invTypeNum, pos);
            if (trunkItem == null || quantity <= 0) {
                // a quantity of 0 used to copy equips (nothing removed from storage, a copy added to the inventory)
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetUnknown)));
                return;
            }
            if (ItemConstants.isEquip(trunkItem.getItemId())) {
                quantity = 1;
            }

            if (trunkItem.getQuantity() < quantity) {
                chr.getOffenseManager().addOffense("Tried getting more items than they had from trunk",
                        trunkItem.getQuantity(), quantity);
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetUnknown)));
                return;
            }

            var itemId = trunkItem.getItemId();
            if (ItemConstants.isRechargable(itemId)) {
                quantity = trunkItem.getQuantity();
            }

            if (trunkItem != null && chr.canHold(trunkItem.getItemId(), quantity)) {
                trunk.removeItem(trunkItem, quantity);

                var copy = trunkItem.deepCopy();
                if (ItemConstants.isEquip(trunkItem.getItemId())) {
                    ((Equip) copy).removeAttribute(EquipAttribute.UntradableAfterTransaction);
                } else {
                    copy.setQuantity(quantity);
                }
                chr.addItemToInventory(copy);

                chr.write(FieldPacket.trunkDlg(TrunkDlg.update(TrunkType.TrunkRes_GetSuccess, trunk)));
            } else {
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetUnknown)));
            }
        } else {
            chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetUnknown)));
        }
    }

    public static void handleTrunkGetMoney(InPacket inPacket, Char chr, Trunk trunk) {
        long reqMoney = inPacket.decodeLong();
        if (reqMoney == 0 || reqMoney == Long.MIN_VALUE) {
            chr.dispose(); // MIN_VALUE can't be negated: it used to corrupt the storage's mesos
            return;
        }
        boolean put = reqMoney < 0;
        long curMoney = chr.getMoney();
        if (put) {
            reqMoney = -reqMoney;
            if (reqMoney > curMoney) {
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_PutNoMoney)));
                return;
            }

            if (!trunk.canAddMoney(reqMoney)) {
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_PutMoneyExceedLimit)));
                return;
            }

            chr.deductMoney(reqMoney, ProgressionLog.Sink.TRANSFER);
            trunk.addMoney(reqMoney);
            chr.write(FieldPacket.trunkDlg(TrunkDlg.update(TrunkType.TrunkRes_MoneySuccess, trunk)));
        } else {
            if (reqMoney > trunk.getMoney()) {
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetNoMoney)));
                return;
            }

            if (!chr.canAddMoney(reqMoney)) {
                chr.write(FieldPacket.trunkDlg(TrunkDlg.message(TrunkType.TrunkRes_GetMoneyExceedLimit)));
                return;
            }

            trunk.addMoney(-reqMoney);
            chr.addMoney(reqMoney);
            chr.write(FieldPacket.trunkDlg(TrunkDlg.update(TrunkType.TrunkRes_MoneySuccess, trunk)));
        }
    }
}
