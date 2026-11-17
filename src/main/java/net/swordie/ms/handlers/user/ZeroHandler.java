package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.ZeroPool;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.EgoEquipItemUpgradeType;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.scripts.ScriptType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created on 2/15/2020.
 *
 * @author Asura
 */
public class ZeroHandler {
    private static final int GENESIS_LAZULI = 1572010;
    private static final int ABSOLAB_ESSENCE = 4310216;
    private static final int ARCANE_UMBRA_ESSENCE = 4310217;
    private static final int GENESIS_ESSENCE = 4310260;

    private static final Logger log = LogManager.getLogger();

    // Zero Weapon Related Handlers
    public static int getReqItemByNextRank(int nextRank) {
        int itemId = 0;

        switch (nextRank) {
            case 8: // Abso Lab
                itemId = ABSOLAB_ESSENCE;
                break;
            case 9: // Arcane Umbra
                itemId = ARCANE_UMBRA_ESSENCE;
                break;
            case 10: // Genesis
                itemId = GENESIS_ESSENCE;
                break;
        }

        return itemId;
    }

    public static int getReqItemCountByNextRank(int nextRank) {
        int itemCount = 0;

        switch (nextRank) {
            case 8: // Abso Lab
                itemCount = 1;
                break;
            case 9: // Arcane Umbra
                itemCount = 1;
                break;
            case 10: // Genesis
                itemCount = 1;
                break;
        }

        return itemCount;
    }

    public static int getReqLvByNextRank(int nextRank) {
        int curRank = nextRank - 1;

        if (nextRank < 7) {
            return 100 + (curRank * 10);
        } else if (nextRank < 9) {
            return 100 + (nextRank * 10);
        } else {
            return 200;
        }
    }

    @Handler(op = InHeader.EGO_EQUIP_CHECK_UPDATE_ITEM_REQUEST)
    public static void handleEgoEquipCheckUpdateItemRequest(Char chr, InPacket inPacket) {
        int invTypeUpgradeItemVal = inPacket.decodeInt();
        int invSlotUpgradeItem = inPacket.decodeInt();
        int invTypeEquipVal = inPacket.decodeInt();
        int invSlotEquip = inPacket.decodeInt();
        int zeroInvSlot = inPacket.decodeInt();

        InvType invTypeUpgradeItem = InvType.getInvTypeByVal(invTypeUpgradeItemVal);
        InvType invTypeEquip = InvType.getInvTypeByVal(invTypeEquipVal);
        if (invTypeEquip.equals(InvType.EQUIP) && invSlotEquip < 0) {
            invTypeEquip = InvType.EQUIPPED;
        }

        Equip equip = (Equip) chr.getInventoryByType(invTypeEquip).getItemBySlot(invSlotEquip);
        Item item = chr.getInventoryByType(invTypeUpgradeItem).getItemBySlot(invSlotUpgradeItem);
        if (item == null || equip == null) {
            return;
        }

        // Anti-Cheat checks & Logic Checks

        chr.write(ZeroPool.egoEquipCheckUpdateItemResult(invTypeUpgradeItem, "You cannot use that item on this equipment", zeroInvSlot));
    }

    @Handler(op = InHeader.EGO_EQUIP_GAUGE_COMPLETE_RETURN)
    public static void handleEgoEquipGaugeCompleteReturn(Char chr, InPacket inPacket) {
        chr.write(ZeroPool.egoEquipUpgradeItemClear());
    }

    @Handler(op = InHeader.EGO_EQUIP_TALK_REQUEST)
    public static void handleEgoEquipTalkRequest(Char chr, InPacket inPacket) {
        int rand = new Random().nextInt(20);
        Quest q = chr.getQuestManager().getOrCreateQuestById(QuestConstants.EGO_EQUIP_TALK_REQUEST);
        q.setProperty("number", rand);
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));

        chr.getScriptManager().startScript(0, "ego_equip_talk_req", ScriptType.Npc);
    }

    @Handler(op = InHeader.EGO_EQUIP_CREATE_UPGRADE_ITEM_COST_REQUEST)
    public static void handleEgoEquipCreateUpgradeItemCostRequest(Char chr, InPacket inPacket) { // Updates Add Bonus Stat & Change Potential buttons
        int egoEquipItemUpgradeTypeVal = inPacket.decodeInt();
        int unk = inPacket.decodeByte(); // 0 ?

        EgoEquipItemUpgradeType type = EgoEquipItemUpgradeType.getByVal(egoEquipItemUpgradeTypeVal);
        if (type == null) {
            return;
        }

        chr.write(ZeroPool.egoEquipCreateUpgradeItemCostResult(type, type.getMesoCost(), type.getWpCost()));
    }



    @Handler(op = InHeader.INHERITANCE_INFO_REQUEST)
    public static void handleInheritanceInfoRequest(Char chr, InPacket inPacket) {
        Equip equip = (Equip) chr.getEquippedItemByBodyPart(BodyPart.Weapon);
        if (equip == null || equip.getItemId() < ItemConstants.SHARP_SHADOW || equip.getItemId() > GENESIS_LAZULI) {
            chr.write(ZeroPool.inheritanceFailed());
            return;
        }
        int curRank = equip.getItemId() - ItemConstants.SHARP_SHADOW;
        int nextRank = curRank + 1;
        int reqLv = getReqLvByNextRank(nextRank);

        int itemId = getReqItemByNextRank(nextRank);
        int itemCount = getReqItemCountByNextRank(nextRank);

        if (curRank < 10) {
            chr.write(ZeroPool.inheritanceInfo(curRank, reqLv, ItemConstants.SHARP_SHADOW + nextRank, ItemConstants.MASSIVE_SHADOW + nextRank, nextRank, itemId, itemCount));
        } else {
            chr.chatMessage("Your Weapon is already at the highest level it can be.");
        }
    }

    @Handler(op = InHeader.INHERITANCE_UPGRADE_REQUEST)
    public static void handleInheritanceUpgradeRequest(Char chr, InPacket inPacket) {
        int curLazuliItemId = inPacket.decodeInt();
        boolean transferSecondaryStats = inPacket.decodeByte() != 0;
        // some more shit, but mostly unknowns or unused

        var equipInv = chr.getEquipInventory();
        Equip oldLapis = (Equip) chr.getEquippedItemByBodyPart(BodyPart.Weapon);
        Equip oldLazuli = (Equip) chr.getEquippedItemByBodyPart(BodyPart.Shield);
        if (oldLapis == null || oldLazuli == null || oldLapis.getItemId() < ItemConstants.SHARP_SHADOW || oldLapis.getItemId() > GENESIS_LAZULI) {
            chr.write(ZeroPool.inheritanceFailed());
            return;
        }
        int curRank = oldLapis.getItemId() - ItemConstants.SHARP_SHADOW;
        int nextRank = curRank + 1;
        int reqLv = getReqLvByNextRank(nextRank);

        if (chr.getLevel() < reqLv) {
            chr.chatMessage("You need to be at least Lv. " + reqLv + ".");
            chr.write(ZeroPool.inheritanceFailed());
            return;
        }

        if (!(curRank < 10)) {
            chr.chatMessage("Your Weapon is already at the highest level it can be.");
            chr.write(ZeroPool.inheritanceFailed());
            return;
        }

        if (equipInv.getEmptySlots() < 2) {
            chr.chatMessage("Please make some space in your Equip inventory (2 empty slots are needed).");
            chr.write(ZeroPool.inheritanceFailed());
            return;
        }

        int itemId = getReqItemByNextRank(nextRank);
        int itemCount = getReqItemCountByNextRank(nextRank);
        if (itemId > 0) {
            if (!chr.hasItemCount(itemId, itemCount)) {
                chr.chatMessage("You do not have the required materials.");
                chr.write(ZeroPool.inheritanceFailed());
                return;
            }
            chr.consumeItem(itemId, itemCount);
        }

        Equip newLapis = ItemData.getEquipDeepCopy(ItemConstants.SHARP_SHADOW + nextRank, false);
        Equip newLazuli = ItemData.getEquipDeepCopy(ItemConstants.MASSIVE_SHADOW + nextRank, false);

        if (oldLapis.getItemId() != ItemConstants.SHARP_SHADOW && oldLazuli.getItemId() != ItemConstants.MASSIVE_SHADOW) {
            if (transferSecondaryStats) {
                newLapis.copySecondaryStatsFrom(oldLapis);
                newLazuli.copySecondaryStatsFrom(oldLazuli);
            } else {
                newLapis.copySecondaryStatsFrom(oldLapis, true, true, true, false, false, false, false);
                newLazuli.copySecondaryStatsFrom(oldLazuli, true, true, true, false, false, false, false);
            }
        }

        int bodyPartLapis = ItemConstants.getBodyPartFromItem(newLapis.getItemId(), chr.getAvatarData().getAvatarLook().getGender());
        int bodyPartLazuli = ItemConstants.getBodyPartFromItem(newLazuli.getItemId(), chr.getAvatarData().getAvatarLook().getGender());

        newLapis.setBagIndex(bodyPartLapis);
        newLazuli.setBagIndex(bodyPartLazuli);

        chr.unequip(oldLapis);
        chr.unequip(oldLazuli);

        oldLapis.setBagIndex(0);
        oldLazuli.setBagIndex(0);

        equipInv.removeItem(oldLapis, true);
        equipInv.removeItem(oldLazuli, true);

        chr.equip(newLapis, bodyPartLapis);
        chr.equip(newLazuli, bodyPartLazuli);

        newLapis.updateToChar(chr);
        newLazuli.updateToChar(chr);

        chr.write(ZeroPool.inheritanceComplete(true));
    }
}