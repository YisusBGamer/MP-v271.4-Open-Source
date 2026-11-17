package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipSpecialAttribute;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.enums.EquipBaseStat;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.ScrollStat;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.EquipItemInfo;
import net.swordie.ms.loaders.containerclasses.ItemInfo;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Exceptional Enhancement (Enchant window, Exceptional tab; Wandering Blacksmith). Rules from
 * Etc/ExceptionalEnchant.img (stage odds and costs), the items' Etuc (stage cap) and the hammers' inc* stats.
 */
public class ExceptionalEnchantModule {

    // EQUIPMENT_ENCHANT sub-type the Exceptional tab reads: the v271 dispatcher (0x1459ead40) hands only 107 to tab 5
    public static final int RESULT_TYPE = 107;

    // client result codes (0 = done; others pick the popup text, unknown ones show "cannot take")
    public static final int OK = 0;
    public static final int ERR_CANNOT = 1;
    public static final int ERR_TRACE = 7;
    public static final int ERR_TRADABLE = 9;
    public static final int ERR_STAGE = 0x65;
    public static final int ERR_LIMIT = 0x66;
    public static final int ERR_MESO = 0x67;
    public static final int ERR_HAMMER = 0x68;
    public static final int ERR_EXTRACT_MESO = 0xc9;
    public static final int ERR_USE_SLOTS = 0xca;
    public static final int ERR_NOTHING = 0xce;

    public static final long EXTRACT_COST = 10_000_000_000L;

    public static final int HAMMER_BELT = 2644204;
    public static final int HAMMER_FACE_ACC = 2644205;
    public static final int HAMMER_EYE_ACC = 2644206;
    public static final int HAMMER_EARRINGS = 2644207;
    public static final int HAMMER_MEDAL = 2644208;
    // the equips each hammer fits are its Item.wz req list
    private static final List<Integer> HAMMERS = List.of(HAMMER_BELT, HAMMER_FACE_ACC, HAMMER_EYE_ACC, HAMMER_EARRINGS,
            HAMMER_MEDAL);
    private static final Map<Integer, Integer> HAMMER_BY_EQUIP = new ConcurrentHashMap<>();

    // per stage: {base %, base mesos, extra-hammer %, extra-hammer mesos}; one extra hammer at most
    private static final long[][] STAGES = {
            {100, 0, 0, 0},
            {50, 2_500_000_000L, 50, 2_500_000_000L},
            {25, 5_000_000_000L, 25, 5_000_000_000L},
    };

    // Extreme reward chests and their hammer (Etc/ContentsGuide.img extendReward). Extreme Black Mage's belt hammer
    // is added by field in BossLootConstants (both modes share the Genesis Crux).
    public static final int HAMMER_DROP_CHANCE = net.swordie.ms.constants.GameConstants.MAX_DROP_CHANCE; // every Extreme clear drops its hammer
    public static final int BELT_HAMMER = HAMMER_BELT;
    private static final Map<Integer, Integer> EXTREME_CHEST_HAMMERS = Map.of(
            BossConstants.serenMob(2, BossConstants.SEREN_TREASURE_CHEST), HAMMER_FACE_ACC,
            BossConstants.KALOS_CHEST[3], HAMMER_EYE_ACC,
            BossConstants.KALING_BASE[3] + BossConstants.KALING_CHEST, HAMMER_EARRINGS,
            BossConstants.ADVERSARY_BASE[3] + 15, HAMMER_MEDAL
    );

    public static DropInfo hammerDrop(int templateId) {
        Integer hammer = EXTREME_CHEST_HAMMERS.get(templateId);
        return hammer == null ? null : new DropInfo(hammer, HAMMER_DROP_CHANCE);
    }

    /** An equip with a stage cap and a hammer that lists it. */
    public static boolean isTarget(int itemId) {
        return hammerFor(itemId) != 0;
    }

    /** Character.wz info/Etuc. */
    public static int maxStages(int itemId) {
        EquipItemInfo info = ItemData.getEquipInfoById(itemId);
        return info == null ? 0 : info.getEtuc();
    }

    public static int hammerFor(int itemId) {
        if (maxStages(itemId) <= 0) {
            return 0;
        }
        return HAMMER_BY_EQUIP.computeIfAbsent(itemId, id -> HAMMERS.stream()
                .filter(h -> fitsReq(h, id)).findFirst().orElse(0));
    }

    /** The hammer's inc* stat for each stage. */
    public static int statPerStage(int itemId, EquipBaseStat stat) {
        int hammer = hammerFor(itemId);
        ScrollStat inc = switch (stat) {
            case iStr -> ScrollStat.incSTR;
            case iDex -> ScrollStat.incDEX;
            case iInt -> ScrollStat.incINT;
            case iLuk -> ScrollStat.incLUK;
            case iPAD -> ScrollStat.incPAD;
            case iMAD -> ScrollStat.incMAD;
            case iMaxHP -> ScrollStat.incMHP;
            case iMaxMP -> ScrollStat.incMMP;
            default -> null;
        };
        ItemInfo info = hammer == 0 || inc == null ? null : ItemData.getItemInfoByID(hammer);
        return info == null ? 0 : info.getScrollStats().getOrDefault(inc, 0);
    }

    /** True if this item (a hammer or a legacy Exceptional Part: Item.wz req lists the equip) can enhance that equip. */
    public static boolean itemFits(int useItemId, int equipItemId) {
        return isTarget(equipItemId) && fitsReq(useItemId, equipItemId);
    }

    // A part fits the equips its req lists; a hammer's req names only its first targets, so it fits any equip of the
    // same body part (the Enchant window's own reinforce groups: belt, face, eye, earrings, medal).
    private static boolean fitsReq(int useItemId, int equipItemId) {
        ItemInfo info = ItemData.getItemInfoByID(useItemId);
        if (info == null || !info.getScrollStats().containsKey(ScrollStat.incSTR)) {
            return false;
        }
        if (info.getReqs().contains(equipItemId)) {
            return true;
        }
        int part = ItemConstants.getBodyPartFromItem(equipItemId, 2);
        return HAMMERS.contains(useItemId) && part != 0
                && info.getReqs().stream().anyMatch(r -> ItemConstants.getBodyPartFromItem(r, 2) == part);
    }

    public static int successRate(int stage, int extraHammers) {
        long[] s = STAGES[stage];
        return net.swordie.ms.constants.GameConstants.boosted((int) Math.min(100, s[0] + (extraHammers > 0 ? s[2] : 0)));
    }

    public static long mesoCost(int stage, int extraHammers) {
        long[] s = STAGES[stage];
        return s[1] + (extraHammers > 0 ? s[3] : 0);
    }

    public static int maxExtraHammers(int stage) {
        return STAGES[stage][2] > 0 ? 1 : 0;
    }

    private static int checkEquip(Equip equip) {
        if (equip == null || !isTarget(equip.getItemId())) {
            return ERR_CANNOT;
        }
        if (equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            return ERR_TRACE;
        }
        if (equip.isTradable()) {
            return ERR_TRADABLE;
        }
        return OK;
    }

    private static Equip findEquip(Char chr, int pos) {
        var inv = pos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
        return inv.getItemBySlot(pos) instanceof Equip e ? e : null;
    }

    /** Enchant window enhance: one hammer (plus one extra for better odds) and the stage's mesos. */
    public static void enhance(Char chr, int equipPos, int extraHammers) {
        Equip equip = findEquip(chr, equipPos);
        int err = checkEquip(equip);
        int stage = equip == null ? 0 : equip.getExceptionalCount();
        if (err == OK && stage >= maxStages(equip.getItemId())) {
            err = ERR_LIMIT;
        }
        if (err == OK && stage >= STAGES.length) {
            err = ERR_STAGE;
        }
        if (err == OK && (extraHammers < 0 || extraHammers > maxExtraHammers(stage))) {
            err = ERR_CANNOT;
        }
        int hammer = err == OK ? hammerFor(equip.getItemId()) : 0;
        long cost = err == OK ? mesoCost(stage, extraHammers) : 0;
        if (err == OK && !chr.hasItemCount(hammer, extraHammers + 1)) {
            err = ERR_HAMMER;
        }
        if (err == OK && chr.getMoney() < cost) {
            err = ERR_MESO;
            chr.chatMessage("Mesos required: " + NumberFormat.getNumberInstance(Locale.US).format(cost));
        }
        if (err != OK) {
            chr.write(result(err, 0, false));
            chr.dispose();
            return;
        }
        chr.consumeItem(hammer, extraHammers + 1);
        if (cost > 0) {
            chr.deductMoney(cost, ProgressionLog.Sink.HAMMERS);
        }
        boolean success = net.swordie.ms.util.Util.succeedProp(successRate(stage, extraHammers));
        if (success) {
            equip.setExceptionalCount(stage + 1);
            equip.updateToChar(chr);
        }
        chr.write(result(OK, 0, success));
        chr.dispose();
    }

    /** Enchant window extract: 10b mesos, the stage count drops by one and a hammer comes back. */
    public static void extract(Char chr, int equipPos) {
        Equip equip = findEquip(chr, equipPos);
        int err = checkEquip(equip);
        if (err == OK && equip.getExceptionalCount() <= 0) {
            err = ERR_NOTHING;
        }
        int hammer = err == OK ? hammerFor(equip.getItemId()) : 0;
        if (err == OK && !chr.canHold(hammer, 1)) {
            err = ERR_USE_SLOTS;
        }
        if (err == OK && chr.getMoney() < EXTRACT_COST) {
            err = ERR_EXTRACT_MESO;
        }
        if (err != OK) {
            chr.write(result(err, 1, false));
            chr.dispose();
            return;
        }
        chr.deductMoney(EXTRACT_COST, ProgressionLog.Sink.HAMMERS);
        equip.setExceptionalCount(equip.getExceptionalCount() - 1);
        equip.updateToChar(chr);
        chr.addItemToInventory(hammer, 1);
        chr.write(result(OK, 1, true));
        chr.dispose();
    }

    /** A hammer or Exceptional Part dragged onto the equip: the free first stage only (100%, no mesos). */
    public static void useOnEquip(Char chr, int srcInv, int srcPos, int equipPos) {
        var useInv = chr.getInventoryByType(srcInv == InvType.CASH.getVal() ? InvType.CASH : InvType.CONSUME);
        var useItem = useInv.getItemBySlot(srcPos);
        Equip equip = findEquip(chr, equipPos);
        int err = checkEquip(equip);
        if (err == OK && (useItem == null || !itemFits(useItem.getItemId(), equip.getItemId()))) {
            err = ERR_CANNOT;
        }
        if (err == OK && equip.getExceptionalCount() >= maxStages(equip.getItemId())) {
            err = ERR_LIMIT;
        }
        if (err == OK && equip.getExceptionalCount() > 0) {
            chr.chatMessage("Further Exceptional Enhancements are made in the Enhance window (Exceptional tab).");
            err = ERR_CANNOT;
        }
        if (err != OK) {
            if (err == ERR_TRADABLE) {
                chr.chatMessage("Only untradable items can be enhanced.");
            } else if (err == ERR_LIMIT) {
                chr.chatMessage("You have exceeded the Exceptional Enhancement limit.");
            }
            chr.dispose();
            return;
        }
        chr.consumeItem(useItem, 1);
        equip.setExceptionalCount(1);
        equip.updateToChar(chr);
        chr.chatMessage("Exceptional Enhancement applied.");
        chr.dispose();
    }

    public static OutPacket result(int code, int type, boolean success) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);
        outPacket.encodeByte(RESULT_TYPE);
        outPacket.encodeInt(code);
        outPacket.encodeInt(type); // 0 enhance, 1 extract
        outPacket.encodeInt(success ? 1 : 0);
        return outPacket;
    }
}
