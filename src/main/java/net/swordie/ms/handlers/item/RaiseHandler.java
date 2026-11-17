package net.swordie.ms.handlers.item;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * "Raise" items (Item.wz/Etc/0422, info/uiData = UI/UIWindow2/raise/N): a quest item that is filled by dragging
 * other items onto its window, such as the Small Bottle of [Elodin] Thirst Quenching (37169), which takes Pure Water.
 * <p>
 * The amount put in is kept as the whole quest record of the raise item's quest (its infoNumber), which is what the
 * quest's end check (Check.img infoex, e.g. "9") and the window read. Each dragged item adds its own info/exp, up to
 * info/grade x info/exp of the raise item.
 * <p>
 * Raise items are the ones whose Item.wz info has consumeItem, grade and exp (ItemInfo.getConsumeItems, getGrade,
 * getInfoExp); what an item adds is its own info/exp. Items that take puzzle pieces (RAISE_ADD_PIECE) or fill from
 * hunting EXP have no consumeItem list and are left alone.
 */
public class RaiseHandler {

    private static final Logger log = LogManager.getLogger(RaiseHandler.class);
    private static final AtomicBoolean loggedLayout = new AtomicBoolean(false);

    private record Raise(int itemId, int questId, int capacity, List<Integer> takes) {
    }

    /** the raise items the character holds, lowest id first */
    private static List<Raise> heldRaises(Char chr) {
        List<Raise> raises = new ArrayList<>();
        for (Item item : chr.getInventoryByType(InvType.ETC).getItems()) {
            ItemInfo ii = ItemData.getItemInfoByID(item.getItemId());
            if (ii == null || ii.getConsumeItems().isEmpty() || ii.getGrade() <= 0 || ii.getInfoExp() <= 0
                    || ii.getQuestIDs().isEmpty()
                    || raises.stream().anyMatch(r -> r.itemId() == item.getItemId())) {
                continue;
            }
            raises.add(new Raise(item.getItemId(), ii.getQuestIDs().iterator().next(),
                    ii.getGrade() * ii.getInfoExp(), ii.getConsumeItems()));
        }
        raises.sort(Comparator.comparingInt(Raise::itemId));
        return raises;
    }

    @Handler(op = InHeader.RAISE_INC_EXP)
    public static void handleRaiseIncExp(Char chr, InPacket inPacket) {
        byte[] raw = inPacket.getUnreadAmount() > 0 ? inPacket.decodeArr(inPacket.getUnreadAmount()) : new byte[0];
        if (loggedLayout.compareAndSet(false, true)) {
            log.info("RAISE_INC_EXP from {} (first this run): {}", chr.getName(), Util.readableByteArray(raw));
        } else {
            log.debug("RAISE_INC_EXP from {}: {}", chr.getName(), Util.readableByteArray(raw));
        }
        try {
            incExp(chr, raw);
        } finally {
            chr.dispose();
        }
    }

    private static void incExp(Char chr, byte[] raw) {
        List<Raise> raises = heldRaises(chr);
        Set<Integer> takes = new HashSet<>();
        raises.forEach(r -> takes.addAll(r.takes()));
        // older clients send nItemIT (1), nSlotPosition (2), nItemID (4); if that doesn't read as an item a raise
        // item takes, look for the item id anywhere in the packet and use the first stack of it
        int itemId = 0;
        int slot = -1;
        if (raw.length >= 7 && takes.contains(readInt(raw, 3))) {
            itemId = readInt(raw, 3);
            slot = (raw[1] & 0xFF) | ((raw[2] & 0xFF) << 8);
        } else {
            for (int i = 0; i + 4 <= raw.length; i++) {
                if (takes.contains(readInt(raw, i))) {
                    itemId = readInt(raw, i);
                    break;
                }
            }
        }
        if (itemId == 0) {
            log.warn("RAISE_INC_EXP from {}: no raise ingredient in {}", chr.getName(), Util.readableByteArray(raw));
            return;
        }
        ItemInfo ingredientInfo = ItemData.getItemInfoByID(itemId);
        int exp = ingredientInfo == null ? 0 : ingredientInfo.getInfoExp();
        QuestManager qm = chr.getQuestManager();
        Raise raise = null;
        for (Raise r : raises) {
            if (r.takes().contains(itemId) && qm.hasQuestInProgress(r.questId())) {
                raise = r;
                break;
            }
        }
        if (raise == null || exp <= 0) {
            return;
        }
        int recordId = raise.questId();
        QuestInfo qi = QuestData.getQuestInfoById(raise.questId());
        if (qi != null && qi.getInfoNumber() > 0) {
            recordId = qi.getInfoNumber();
        }
        Quest record = qm.getQuestById(recordId);
        if (record == null) {
            record = QuestData.createQuestFromId(recordId);
            if (record == null) {
                return;
            }
            qm.addQuest(record);
        }
        int filled = 0;
        try {
            String qr = record.getQRValue();
            filled = qr == null || qr.isEmpty() ? 0 : Integer.parseInt(qr.trim());
        } catch (NumberFormatException e) {
            filled = 0;
        }
        if (filled >= raise.capacity()) {
            return;
        }
        Item ingredient = null;
        if (slot > 0) {
            Item atSlot = chr.getInventoryByType(InvType.ETC).getItemBySlot(slot);
            if (atSlot == null || atSlot.getItemId() != itemId) {
                atSlot = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(slot);
            }
            if (atSlot != null && atSlot.getItemId() == itemId) {
                ingredient = atSlot;
            }
        }
        if (ingredient == null) {
            ingredient = chr.getInventoryByType(ingredientInfo.getInvType()).getItemByItemID(itemId);
        }
        if (ingredient == null) {
            return;
        }
        chr.consumeItem(ingredient, 1);
        record.setQrValue(String.valueOf(Math.min(raise.capacity(), filled + exp)));
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(record)));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(record)));
    }

    private static int readInt(byte[] b, int i) {
        return (b[i] & 0xFF) | ((b[i + 1] & 0xFF) << 8) | ((b[i + 2] & 0xFF) << 16) | ((b[i + 3] & 0xFF) << 24);
    }
}
