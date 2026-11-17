package net.swordie.ms.handlers.user;

import net.swordie.ms.ServerSettings;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.MakingSkillType;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Inventory Alchemy window, Disassemble tab (Alchemy extraction, recipe 92049000).
 * v271 request (MAKING_SKILL_REQUEST, mode 1): int extractor owner, int n, n x {int recipe, int item id,
 * long serial, int, int, int} (one of the three trailing ints is the Equip slot).
 */
public class EquipExtraction {

    public static final int EXTRACT_RECIPE = 92049000;
    private static final int MAX_ITEMS = 30;

    public static void handle(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // extractor owner (0 = own skill)
        int n = inPacket.decodeInt();
        List<MakingSkillItem> done = new ArrayList<>();
        Set<Integer> usedSlots = new HashSet<>();
        int traces = 0;
        for (int i = 0; i < n && i < MAX_ITEMS && inPacket.getUnreadAmount() >= 28; i++) {
            int recipe = inPacket.decodeInt();
            int itemId = inPacket.decodeInt();
            inPacket.decodeLong(); // serial
            int a = inPacket.decodeInt();
            int b = inPacket.decodeInt();
            int c = inPacket.decodeInt();
            Equip equip = findEquip(chr, itemId, usedSlots, b, a, c);
            if (equip == null) {
                continue;
            }
            int got = extract(chr, equip);
            if (got < 0) {
                continue;
            }
            traces += got;
            usedSlots.add(equip.getBagIndex());
            MakingSkillItem msi = new MakingSkillItem();
            msi.setRecipeId(recipe);
            msi.setItemId(itemId);
            msi.setIdk1(a);
            msi.setIdk2(b);
            msi.setIdk3(c);
            msi.setItemPos(equip.getBagIndex());
            done.add(msi);
        }
        if (traces > 0) {
            chr.addItemToInventory(ItemConstants.SPELL_TRACE_ID, traces);
            chr.chatMessage(String.format("Disassembly: %,d Spell Traces.", traces));
        }
        chr.write(FieldPacket.makingSkillResult(chr.getId(), EXTRACT_RECIPE, MakingSkillType.Extraction,
                done.isEmpty() ? MakingSkillType.Fail_Unknown : MakingSkillType.Success_Cool, done, null, 0));
    }

    /** The equip in one of the given slots with this item id, not already taken by this request. */
    private static Equip findEquip(Char chr, int itemId, Set<Integer> used, int... slots) {
        var inv = chr.getEquipInventory();
        for (int s : slots) {
            if (s <= 0 || used.contains(s)) {
                continue;
            }
            Item it = inv.getItemBySlot(s);
            if (it instanceof Equip e && it.getItemId() == itemId) {
                return e;
            }
        }
        return null;
    }

    /** Extracts one equip; the Spell Trace count it adds, or -1 when refused. */
    private static int extract(Char chr, Equip equip) {
        int itemId = equip.getItemId();
        String name = StringData.getItemStringById(itemId);
        var info = ItemData.getItemInfoByID(itemId);
        if (!ItemConstants.isEquip(itemId) || equip.isCash()
                || (info != null && info.isQuest())) {
            chr.chatMessage(String.format("%s cannot be disassembled.", name));
            return -1;
        }
        int reqLevel = equip.getReqLevel();
        int need = GameConstants.getRequiredMakingSkillForExtraction(reqLevel);
        if (chr.getMakingSkillLevel(SkillConstants.ALCHEMY_CRAFT_SKILL) < need) {
            chr.chatMessage(String.format("Your Alchemy mastery isn't high enough to disassemble %s (required: %d).",
                    name, need));
            return -1;
        }
        if (chr.getEtcInventory().isFull()) {
            chr.chatMessage("Please make some more space in your ETC inventory.");
            return -1;
        }
        // Magic Powder by level (Recipe_9204 92049000 targets)
        chr.addItemToInventory(GameConstants.getExtractionReward(reqLevel), Util.getRandom(1, Math.max(1, reqLevel / 20)));
        chr.consumeItemFull(equip);
        net.swordie.ms.client.character.achievement.MapleAchievements.onExtract(chr);
        return net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.EXTRACT_SPELL_TRACES) && ServerSettings.getBoolean("extract.spellTraces", true) ? spellTraces(reqLevel, equip.getBaseGrade()) : 0;
    }

    /** Spell Traces for one extracted equip: level / 10 (min 1) times potential tier + 1 (house rule). */
    public static int spellTraces(int reqLevel, int baseGrade) {
        int tier = baseGrade >= 17 ? baseGrade - 16 : (baseGrade >= 1 && baseGrade <= 4 ? baseGrade : 0);
        return Math.max(1, reqLevel / 10) * (tier + 1);
    }
}
