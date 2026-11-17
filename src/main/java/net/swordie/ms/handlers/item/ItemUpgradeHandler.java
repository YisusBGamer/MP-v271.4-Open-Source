package net.swordie.ms.handlers.item;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.*;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.ZeroPool;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;

import static net.swordie.ms.enums.ChatType.Mob;
import static net.swordie.ms.enums.ChatType.SystemNotice;
import static net.swordie.ms.enums.EquipBaseStat.*;
import static net.swordie.ms.enums.InvType.*;

public class ItemUpgradeHandler {
    private static final int BLACK_REBIRTH_FLAME = 2048753;
    private static final int ADVANCED_EQUIP_ENHANCEMENT_SCROLL = 2049023;
    private static final int ADVANCED_EQUIP_ENHANCEMENT_SCROLL_2 = 2049300;
    private static final int EQUIPMENT_ENHANCEMENT_SCROLL = 2049301;
    private static final int ADVANCED_EQUIP_ENHANCEMENT_SCROLL_3 = 2049303;
    private static final int ADVANCED_EQUIP_ENHANCEMENT_SCROLL_4 = 2049306;
    private static final int EQUIPMENT_ENHANCEMENT_SCROLL_2 = 2049307;
    private static final int PURE_EQUIP_ENHANCEMENT_SCROLL = 2049323;
    private static final int ADVANCED_EQUIP_ENHANCEMENT_SCROLL_5 = 2049325;

    private static final Logger log = LogManager.getLogger();


    /** Flames that show the before/after window (as in GMS): Black and Abyssal Rebirth Flames. */
    public static boolean isChoiceFlame(int flameId) {
        String name = net.swordie.ms.loaders.StringData.getItemStringById(flameId);
        if (name == null) {
            return false;
        }
        name = name.toLowerCase();
        return name.contains("black") || name.contains("abyssal");
    }

    @Handler(op = InHeader.USER_EX_ITEM_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserExItemUpgradeItemUseRequest(Client c, InPacket inPacket) {
        inPacket.decodeInt(); //tick
        int count = inPacket.decodeInt();
        short usePosition = 0;
        for (int i = 0; i < count && i < 100; i++) {
            short pos = inPacket.decodeShort();
            if (i == 0) {
                usePosition = pos;
            }
        }
        int flameId = inPacket.decodeInt();
        var eqpPosition = inPacket.decodeShort();
        inPacket.decodeByte(); // 1 = from the result window's retry
        int flameCount = inPacket.decodeByte();

        Char chr = c.getChr();
        if (flameId < 0) {
            Item eq = chr.getInventoryByType(eqpPosition < 0 ? EQUIPPED : EQUIP).getItemBySlot(eqpPosition);
            mesoFlame(chr, eq instanceof Equip e ? e : null, flameId, flameCount);
            return;
        }
        Item flame = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(usePosition);
        var invType = eqpPosition < 0 ? EQUIPPED : EQUIP;
        Item eqItem = chr.getInventoryByType(invType).getItemBySlot(eqpPosition);
        Equip equip = eqItem instanceof Equip e ? e : null;

        if (flame == null || flame.getItemId() != flameId || equip == null || !ItemConstants.canEquipHaveFlame(equip)) {
            chr.chatMessage(SystemNotice, "Could not find flame or equip.");
            chr.write(WvsContext.flameApplied());
            chr.dispose();
            return;
        }

        if (!ItemConstants.isRebirthFlame(flame.getItemId())) {
            chr.chatMessage(SystemNotice, "This item is not a rebirth flame.");
            chr.write(WvsContext.flameApplied());
            chr.dispose();
            return;
        }

        ItemInfo flameInfo = ItemData.getItemInfoByID(flame.getItemId());
        if (flameInfo == null) {
            chr.write(WvsContext.flameApplied());
            chr.dispose();
            return;
        }
        Map<ScrollStat, Integer> vals = flameInfo.getScrollStats();
        if (vals.size() > 0) {
            int reqEquipLevelMax = vals.getOrDefault(ScrollStat.reqEquipLevelMax, 250);

            if (equip.getReqLevel() + equip.getiIncReq() > reqEquipLevelMax) {
                c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("Equipment level does not meet scroll requirements.")));
                chr.write(WvsContext.flameApplied());
                chr.dispose();
                return;
            }

            boolean success = GameConstants.rollScrollSuccess(vals.getOrDefault(ScrollStat.success, 100));

            boolean eternalFlame = vals.getOrDefault(ScrollStat.createType, 6) >= 7;
            if (success && isChoiceFlame(flame.getItemId())) {
                // the new stats wait on a copy until the player keeps them or the old ones
                Equip result = equip.deepCopy();
                result.randomizeFlameStats(true);
                chr.setMemorialFlameInfo(new MemorialFlameInfo(equip, result, flame.getItemId()));
                chr.consumeItem(flame);
                chr.write(WvsContext.memorialFlameResult(equip, result, flameId, maxFlameTier(result)));
                chr.dispose();
                return;
            }
            if (success) {
                equip.randomizeFlameStats(eternalFlame); // Generate high stats if it's an eternal/RED flame only.
                applyFlame(chr, equip, equip);
            }

            c.write(FieldPacket.showItemUpgradeEffect(chr.getId(), success, false, flame.getItemId(), equip.getItemId(), false));
            equip.updateToChar(chr);
            chr.consumeItem(flame);
        }
        // the Enhance window waits for this after a flame without a choice (REBIRTH_FLAME_RESULT is gone)
        chr.write(WvsContext.flameApplied());
        chr.dispose();
    }

    /** Mesos per bonus-stat reset (client 0x14053f8a0: count x 3,000,000). */
    public static final long MESO_FLAME_COST = 3_000_000L;

    /**
     * The flame a meso reset rolls like (MESO_FLAME_ITEM); 0 = no meso reset. A Heroic-world convenience on this
     * Interactive server: setting enhance.heroic.mesoFlame.
     */
    public static int mesoFlameItemId() {
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.HEROIC_MESO_FLAME)
                || !net.swordie.ms.ServerSettings.getBoolean("enhance.heroic.mesoFlame", true)) {
            return 0;
        }
        return net.swordie.ms.ServerSettings.getInt("enhance.mesoFlameItemId", BLACK_REBIRTH_FLAME);
    }

    /**
     * Bonus-stat reset for mesos (flame id = -MESO_FLAME_ITEM): rolls like that flame and opens the keep/replace
     * window; the window's retry sends the negative id again. The client sends count 1, or 3 for Reset x3 (client
     * 0x14120fa60): 3 results to pick from for 3x the mesos.
     */
    private static void mesoFlame(Char chr, Equip equip, int flameId, int count) {
        int itemId = -flameId;
        ItemInfo ii = ItemData.getItemInfoByID(itemId);
        if (itemId == 0 || itemId != mesoFlameItemId() || ii == null || equip == null || !ItemConstants.canEquipHaveFlame(equip)
                || (count != 1 && count != 3)) {
            chr.write(WvsContext.flameApplied());
            chr.dispose();
            return;
        }
        Map<ScrollStat, Integer> vals = ii.getScrollStats();
        if (equip.getReqLevel() + equip.getiIncReq() > vals.getOrDefault(ScrollStat.reqEquipLevelMax, 250)) {
            chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("Equipment level does not meet scroll requirements.")));
            chr.write(WvsContext.flameApplied());
            chr.dispose();
            return;
        }
        long cost = MESO_FLAME_COST * count;
        if (chr.getMoney() < cost) {
            chr.chatMessage(SystemNotice, String.format("You need %,d mesos to reset the bonus stats.", cost));
            chr.write(WvsContext.flameApplied());
            chr.dispose();
            return;
        }
        chr.deductMoney(cost, ProgressionLog.Sink.CUBES);
        boolean eternal = vals.getOrDefault(ScrollStat.createType, 6) >= 7;
        java.util.List<Equip> results = new java.util.ArrayList<>();
        int tier = 0;
        for (int i = 0; i < count; i++) {
            Equip result = equip.deepCopy();
            result.randomizeFlameStats(eternal);
            results.add(result);
            tier = Math.max(tier, maxFlameTier(result));
        }
        chr.setMemorialFlameInfo(new MemorialFlameInfo(equip, results, flameId));
        chr.write(WvsContext.memorialFlameResult(equip, results, flameId, tier));
        chr.dispose();
    }

    /** The highest line tier in a flame (exGradeOption: 3 decimal digits a line, the last one the tier). */
    private static int maxFlameTier(Equip equip) {
        int max = 0;
        for (long ex = equip.getExGradeOption(); ex > 0; ex /= 1000) {
            max = (int) Math.max(max, ex % 10);
        }
        return max;
    }

    /** Puts flame stats on an equip (and Zero's other weapon) and refreshes the stats it gives. */
    private static void applyFlame(Char chr, Equip equip, Equip from) {
        if (from != equip) {
            equip.copyFlameStatsFrom(from);
        }
        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())
                && chr.getEquippedInventory().getItems().contains(equip)) {
            int otherEquipPos = Math.abs(equip.getBagIndex()) == 10 ? 11 : 10;
            Item other = chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            if (other instanceof Equip otherEquip) {
                otherEquip.copyFlameStatsFrom(equip);
                otherEquip.updateToChar(chr);
                chr.write(ZeroPool.egoEquipComplete(true, true));
            }
        }
        equip.updateToChar(chr);
        if (chr.getEquippedInventory().getItems().contains(equip)) {
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
    }

    /** The Black Rebirth Flame window's pick: a result index keeps the new stats, -1 keeps the old ones. */
    @Handler(op = InHeader.USER_MEMORIAL_EX_UPGRADE_ITEM_OPTION_REQUEST)
    public static void handleUserMemorialExUpgradeItemOptionRequest(Char chr, InPacket inPacket) {
        int pick = inPacket.decodeInt();
        MemorialFlameInfo mfi = chr.getMemorialFlameInfo();
        chr.setMemorialFlameInfo(null);
        if (mfi != null && mfi.getResult(pick) != null) {
            Equip equip = mfi.getEquip();
            boolean stillThere = chr.getEquippedInventory().getItems().contains(equip)
                    || chr.getEquipInventory().getItems().contains(equip);
            if (stillThere) {
                applyFlame(chr, equip, mfi.getResult(pick));
                chr.write(FieldPacket.showItemUpgradeEffect(chr.getId(), true, false, Math.abs(mfi.getFlameId()), equip.getItemId(), false));
            }
        }
        chr.write(WvsContext.memorialFlameClear());
        chr.dispose();
    }

    /** v265: int tick, int pick; a result index keeps the new potential, -1 keeps the old one. */
    @Handler(op = InHeader.USER_MEMORIAL_CUBE_OPTION_REQUEST)
    public static void handleUserMemorialCubeOptionRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        int pick = inPacket.decodeInt();
        MemorialCubeInfo mci = chr.getMemorialCubeInfo();
        chr.setMemorialCubeInfo(null);
        if (mci == null) {
            chr.dispose();
            return;
        }
        Equip equip = mci.getEquip();
        boolean stillThere = chr.getEquippedInventory().getItems().contains(equip)
                || chr.getEquipInventory().getItems().contains(equip);
        int baseBefore = equip.getBaseGrade();
        int bonusBefore = equip.getBonusGrade();
        if (pick >= 0 && stillThere && mci.applyNewOptions(pick)) { // pick = index of the AFTER card (x3: 0..2)
            // keeping a higher grade starts the cube ceiling over
            if (equip.getBaseGrade() > baseBefore) {
                equip.setCubeCeiling(false, 0);
            }
            if (equip.getBonusGrade() > bonusBefore) {
                equip.setCubeCeiling(true, 0);
            }
            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())
                    && chr.getEquippedInventory().getItems().contains(equip)) {
                int otherEquipPos = Math.abs(equip.getBagIndex()) == 10 ? 11 : 10;
                if (chr.getEquippedInventory().getItemBySlot(otherEquipPos) instanceof Equip otherEquip) {
                    otherEquip.setOptions(new ArrayList<>(equip.getOptions()));
                    otherEquip.updateToChar(chr);
                }
            }
        }
        equip.updateToChar(chr);
        chr.write(WvsContext.memorialCubeClear());
    }

    /** The Circulator window's pick: keep the new lines (1) or the old ones (0). */
    @Handler(op = InHeader.USER_MIRACLE_CIRCULATOR_OPTION_REQUEST)
    public static void handleUserMiracleCirculatorOptionRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        boolean keepNew = inPacket.decodeByte() == 1;
        inPacket.decodeInt(); // item
        int preset = inPacket.decodeByte();
        if (!chr.getPotentialMan().resolveCirculator(keepNew, preset)) {
            chr.dispose();
        }
    }

    @Handler(op = InHeader.GOLD_HAMMER_REQUEST)
    public static void handleGoldHammerRequest(Char chr, InPacket inPacket) {
        if (chr.getClient().getWorld().isReboot()) {
            chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Error, 1, 0));
            chr.getOffenseManager().addOffense(String.format("Character %d attempted to hammer in reboot world.", chr.getId()));
            return;
        }

        inPacket.decodeInt(); // tick
        int iPos = inPacket.decodeInt(); // hammer slot
        int hammerID = inPacket.decodeInt(); // hammer item id
        int eInvType = inPacket.decodeInt(); // use hammer? useless though
        int ePos = inPacket.decodeInt(); // equip slot

        InvType invType = InvType.getInvTypeByVal(eInvType);
        if (invType.equals(EQUIP) && ePos < 0) {
            invType = EQUIPPED;
        }

        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot((short) ePos);
        Item hammer = chr.getInventoryByType(CONSUME).getItemBySlot((short) iPos);
        short maxHammers = ItemConstants.MAX_HAMMER_SLOTS;

        if (ItemConstants.isRetiredGoldenHammer(hammerID)) {
            // retired in GMS v271: every equip already has the hammer slot; double-clicking it gives Spell Traces
            chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Error, 1, 0));
            chr.chatMessage("Golden Hammers are no longer used: every item already has that slot. Double-click the "
                    + "hammer to get Spell Traces for it.");
            return;
        }

        if (equip != null) {
            var defaultEquip = ItemData.getEquipInfoById(equip.getItemId());
            if (defaultEquip.isHasIUCMax()) {
                maxHammers = defaultEquip.getIucMax();
            }
        }

        if (equip == null || !ItemConstants.canEquipGoldHammer(equip) ||
                hammer == null || !ItemConstants.isGoldHammer(hammer) || hammerID != hammer.getItemId()) {
            chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Error, 1, 0));
            chr.getOffenseManager().addOffense(String.format("Character %d tried to use hammer (id %d) on an invalid equip (id %d)",
                    chr.getId(), hammer == null ? 0 : hammer.getItemId(), equip == null ? 0 : equip.getItemId()));
            return;
        }

        ItemInfo hammerInfo = ItemData.getItemInfoByID(hammer.getItemId());
        if (hammerInfo == null) {
            chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Error, 1, 0));
            return;
        }
        Map<ScrollStat, Integer> vals = hammerInfo.getScrollStats();

        if (vals.size() > 0) {
            if (equip.getIuc() >= maxHammers) {
                chr.getOffenseManager().addOffense(String.format("Character %d tried to use hammer (id %d) an invalid equip (id %d)",
                        chr.getId(), hammerID, equip.getItemId()));
                chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Error, 2, 0));
                return;
            }

            boolean success = GameConstants.rollScrollSuccess(vals.getOrDefault(ScrollStat.success, 100));

            if (success) {
                equip.addStat(iuc, 1); // +1 hammer used
                equip.addStat(tuc, 1); // +1 upgrades available
                equip.updateToChar(chr);

                if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                    int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                    Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                    otherEquip.copySecondaryStatsFrom(equip);
                    otherEquip.updateToChar(chr);

                    chr.write(ZeroPool.egoEquipComplete(true, true));
                }

                chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Success, 0, equip.getIuc()));
            } else {
                chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Fail, 1, equip.getIuc()));
            }

            chr.consumeItem(hammer.getItemId(), 1);
        }
    }

    @Handler(op = InHeader.GOLD_HAMMER_COMPLETE)
    public static void handleGoldHammerComplete(Char chr, InPacket inPacket) {
        int returnResult = inPacket.decodeInt();
        int result = inPacket.decodeInt();
        if (returnResult == GoldHammerResult.Success.getVal() || returnResult == GoldHammerResult.Fail.getVal()) {
            //I think its ok to just send back the result given.
            chr.write(WvsContext.goldHammerItemUpgradeResult(GoldHammerResult.Done, result, 0));
        } else {
            chr.getOffenseManager().addOffense(String.format("Character %d have invalid gold hammer complete returnResult %d",
                    chr.getId(), returnResult));
        }
    }

    @Handler(op = InHeader.USER_UPGRADE_ASSIST_ITEM_USE_REQUEST)
    public static void handleUserUpgradeAssistItemUseRequest(Client c, InPacket inPacket) {

        Char chr = c.getChr();
        if (c.getWorld().isReboot()) {
            log.error(String.format("Character %d attempted to scroll in reboot world.", chr.getId()));
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); //tick
        short uPos = inPacket.decodeShort(); //Use Position
        var invType = InvType.getInvTypeByVal(inPacket.decodeShort());
        short ePos = inPacket.decodeShort(); //Eqp Position
        byte bEnchantSkill = inPacket.decodeByte(); //no clue what this means exactly
//        short idk = inPacket.decodeShort(); //No clue what this is, stayed  00 00  throughout different tests
        Item scroll = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(uPos);
        invType = ePos < 0 ? EQUIPPED : invType;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (scroll == null || equip == null) {
            chr.chatMessage(SystemNotice, "Could not find scroll or equip.");
            return;
        }
        int scrollID = scroll.getItemId();
        if (!ItemHandlerModule.applyAssistScroll(chr, scrollID, equip)) {
            chr.dispose();
            return;
        }
        c.write(FieldPacket.showItemUpgradeEffect(chr.getId(), true, false, scrollID, equip.getItemId(), false));

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copyAttributesFrom(equip);
            otherEquip.updateToChar(chr);

            chr.write(ZeroPool.egoEquipComplete(true, true));
        }

        equip.updateToChar(chr);
        chr.consumeItem(scroll);
    }

    @Handler(op = InHeader.USER_HYPER_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserHyperUpgradeItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        var usePos = inPacket.decodeShort();
        var equipPos = inPacket.decodeShort();

        var scroll = chr.getConsumeInventory().getItemBySlot(usePos);
        var equip = equipPos < 0
                ? chr.getEquippedInventory().getItemBySlot(Math.abs(equipPos))
                : chr.getEquipInventory().getItemBySlot(equipPos);

        if (scroll == null || !(equip instanceof Equip)) {
            chr.chatMessage("Could not find the scroll or the equip.");
            chr.dispose();
            return;
        }

        ItemInfo scrollInfo = ItemData.getItemInfoByID(scroll.getItemId());
        int forceStars = scrollInfo == null ? 0 : scrollInfo.getScrollStats().getOrDefault(ScrollStat.forceUpgrade, 0);
        if (forceStars > 0) {
            String why = forceUpgrade(chr, (Equip) equip, forceStars);
            if (why != null) {
                chr.chatMessage(SystemNotice, why);
            } else {
                chr.consumeItem(scroll);
            }
            chr.dispose();
            return;
        }

        boolean advanced = false;
        boolean safeguard = false;

        switch (scroll.getItemId()) {
            case EQUIPMENT_ENHANCEMENT_SCROLL: // Normal EE
            case EQUIPMENT_ENHANCEMENT_SCROLL_2:
                break;
            case ADVANCED_EQUIP_ENHANCEMENT_SCROLL: // AEE
            case ADVANCED_EQUIP_ENHANCEMENT_SCROLL_2:
            case ADVANCED_EQUIP_ENHANCEMENT_SCROLL_3:
            case ADVANCED_EQUIP_ENHANCEMENT_SCROLL_4:
            case PURE_EQUIP_ENHANCEMENT_SCROLL:
                advanced = true;
                break;
            case ADVANCED_EQUIP_ENHANCEMENT_SCROLL_5: // Noboom AEE
                advanced = true;
                safeguard = true;
                break;
            default:
                if (!ItemConstants.isEnhancementScroll(scroll.getItemId())) {
                    // only Equip Enhancement Scrolls
                    chr.dispose();
                    return;
                }
        }

        boolean success = ItemHandlerModule.enhanceItem(chr, (Equip) equip, advanced, safeguard);
        if (!success) {
            chr.dispose();
        } else {
            chr.consumeItem(scroll);
        }
    }

    /**
     * Star Force N-Star Enhancement (Item.wz info/forceUpgrade = N): sets the item to N stars, always succeeds, no
     * mesos. Only on items below N stars that can reach N; not on superior items (info/noSuperior), and like any
     * Star Force only once the upgrade slots are used up.
     * @return why it can't be used, or null when the stars were set
     */
    public static String forceUpgrade(Char chr, Equip equip, int stars) {
        if (!ItemConstants.isUpgradable(equip.getItemId()) || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)
                || equip.getInfo() == null) {
            return "This item can't be enhanced.";
        }
        if (equip.getInfo().isSuperiorEqp()) {
            return "This scroll can't be used on superior equipment.";
        }
        if (equip.getBaseStat(tuc) != 0 && !chr.getWorld().isReboot()) {
            return "Use up the item's upgrade slots first.";
        }
        int max = GameConstants.getMaxStars(equip);
        if (max < stars) {
            return String.format("This item can only reach %d stars.", max);
        }
        if (equip.getChuc() >= stars) {
            return String.format("This item already has %d stars.", equip.getChuc());
        }
        equip.setChuc((short) stars);
        equip.setDropStreak(0);
        equip.updateToChar(chr);
        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherPos = Math.abs(equip.getBagIndex()) == 10 ? 11 : 10;
            Equip other = (Equip) chr.getEquippedInventory().getItemBySlot(otherPos);
            if (other != null) {
                other.copyChucFrom(equip);
                other.updateToChar(chr);
            }
        }
        chr.chatMessage(String.format("Your item now has %d stars.", stars));
        return null;
    }

    @Handler(op = InHeader.USER_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserUpgradeItemUseRequest(Client c, InPacket inPacket) {
        try {
            useUpgradeScroll(c, inPacket);
        } catch (RuntimeException e) {
            // the client keeps its exclusive request until a reply comes, so a thrown handler freezes its UI
            log.error("Scroll use failed", e);
            c.getChr().dispose();
        }
    }

    private static void useUpgradeScroll(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (c.getWorld().isReboot()) {
            log.error(String.format("Character %d attempted to scroll in reboot world.", chr.getId()));
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); //tick
        short uPos = inPacket.decodeShort(); //Use Position
        short second = inPacket.decodeShort(); // the reference server reads a count here (1)
        var invType = second == InvType.DEC.getVal() ? InvType.DEC : InvType.EQUIP;
        short ePos = inPacket.decodeShort(); //Eqp Position
        inPacket.decodeShort();
        byte bEnchantSkill = inPacket.decodeByte(); //no clue what this means exactly

        Item scroll = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(uPos);
        var ii = scroll == null ? null : ItemData.getItemInfoByID(scroll.getItemId());
        if (ii == null) {
            chr.chatMessage(SystemNotice, "Could not find the scroll.");
            chr.dispose();
            return;
        }
        int scrollID = scroll.getItemId();

        invType = ePos < 0 ? EQUIPPED : invType;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);

        if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            chr.chatMessage(SystemNotice, "Could not find scroll or equip.");
            chr.dispose();
            return;
        }

        if (ItemConstants.isSymbol(equip.getItemId()) || !ii.canBeUsedOnItem(equip)) {
            chr.chatMessage("Invalid item to use this item on.");
            chr.dispose();
            return;
        }

        boolean success = true;
        boolean boom = false;
        boolean shielded = false; // a Protection Scroll kept the item from being destroyed
        Map<ScrollStat, Integer> vals = ii.getScrollStats();
        if (vals.size() > 0) {
            boolean recover = vals.getOrDefault(ScrollStat.recover, 0) != 0;
            boolean reset = vals.getOrDefault(ScrollStat.reset, 0) + vals.getOrDefault(ScrollStat.perfectReset, 0) != 0;
            // pet equips have no upgrade slots: their scrolls apply without using one
            boolean petEquip = ItemConstants.isPetWear(equip.getItemId());
            if (equip.getBaseStat(tuc) <= 0 && (!recover && !reset) && !petEquip) {
                chr.chatMessage("This item cannot be scrolled.");
                chr.dispose();
                return;
            }
            boolean useTuc = !recover && !reset && !petEquip;
            // the guild's Enhancement Mastery / Item Salvation / Upgrade Salvation (regular scrolls, not Chaos scrolls)
            boolean guildBonus = useTuc && !vals.containsKey(ScrollStat.randStat);
            int successChance = vals.getOrDefault(ScrollStat.success, 100)
                    + chr.getAvatarData().getCharacterStat().getExtraScrollChance(equip)
                    + (guildBonus ? net.swordie.ms.handlers.social.module.GuildModule.skillValue(chr,
                    net.swordie.ms.handlers.social.module.GuildModule.ENHANCEMENT_MASTERY,
                    net.swordie.ms.client.character.skills.SkillStat.itemUpgradeBonusR) : 0)
                    + (equip.hasAttribute(EquipAttribute.LuckyDay) ? 10 : 0); // Lucky Day Scroll: +10%
            int curse = vals.getOrDefault(ScrollStat.cursed, 0);

            success = GameConstants.rollScrollSuccess(successChance);
            // Return Scroll: the stats from before a successful stat scroll, for the keep/return choice
            Equip returnBefore = success && !recover && !reset && equip.hasAttribute(EquipAttribute.ReturnScroll)
                    ? equip.deepCopy() : null;
            if (success) {
                boolean chaos = vals.containsKey(ScrollStat.randStat);
                if (chaos) {
                    boolean noNegative = vals.containsKey(ScrollStat.noNegative);
                    int max = vals.containsKey(ScrollStat.incRandVol) ? ItemConstants.INC_RAND_CHAOS_MAX : ItemConstants.RAND_CHAOS_MAX;
                    for (EquipBaseStat ebs : ScrollStat.getRandStats()) {
                        int cur = (int) equip.getBaseStat(ebs);
                        if (cur == 0) {
                            continue;
                        }
                        int randStat = Util.getRandom(max);
                        randStat = !noNegative && Util.succeedProp(50) ? -randStat : randStat;
                        equip.addStat(ebs, randStat);
                    }
                }
                if (recover) {
                    Equip fullTucEquip = ItemData.getEquipDeepCopy(equip.getItemId(), false);
                    int maxTuc = fullTucEquip.getTuc() + equip.getIuc();
                    if (equip.getTuc() + equip.getCuc() >= maxTuc) {
                        chr.chatMessage("This item has no open slots to recover.");
                    } else {
                        equip.addStat(tuc, 1);
                    }
                } else if (reset) {
                    equip.resetStats();

                    if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                        int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                        Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                        otherEquip.resetStats();
                    }

                } else {
                    for (Map.Entry<ScrollStat, Integer> entry : vals.entrySet()) {
                        ScrollStat ss = entry.getKey();
                        int val = entry.getValue();
                        if (ss.getEquipStat() != null) {
                            equip.addStat(ss.getEquipStat(), val);
                        }
                    }
                }
                if (useTuc) {
                    equip.addStat(tuc, -1);
                    equip.addStat(cuc, 1);
                }

                if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                    chr.write(ZeroPool.egoEquipComplete(true, true));
                }
            } else {
                if (curse > 0) {
                    boom = Util.succeedProp(curse);
                    if (boom && guildBonus && Util.succeedProp(net.swordie.ms.handlers.social.module.GuildModule
                            .skillValue(chr, net.swordie.ms.handlers.social.module.GuildModule.ITEM_SALVATION,
                                    net.swordie.ms.client.character.skills.SkillStat.itemCursedProtectR))) {
                        boom = false;
                        chr.chatMessage("The guild's Item Salvation kept your item from being destroyed.");
                    }
                    if (boom && !equip.hasAttribute(EquipAttribute.ProtectionScroll)) {
                        chr.consumeItem(equip);
                    } else {
                        shielded = boom;
                        boom = false;
                    }
                }
                boolean tucSaved = guildBonus && !boom && Util.succeedProp(net.swordie.ms.handlers.social.module
                        .GuildModule.skillValue(chr, net.swordie.ms.handlers.social.module.GuildModule.UPGRADE_SALVATION,
                                net.swordie.ms.client.character.skills.SkillStat.itemTUCProtectR));
                if (tucSaved && !equip.hasAttribute(EquipAttribute.UpgradeCountProtection)) {
                    chr.chatMessage("The guild's Upgrade Salvation kept your upgrade slot.");
                }
                if (useTuc && !tucSaved && !equip.hasAttribute(EquipAttribute.UpgradeCountProtection)) {
                    equip.addStat(tuc, -1);
                }

                if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                    chr.write(ZeroPool.egoEquipComplete(false, true));
                }
            }
            // Guardian Scroll: a failed scroll isn't used up
            boolean scrollKept = !success && !boom && equip.hasAttribute(EquipAttribute.ScrollProtection);
            equip.removeAttribute(EquipAttribute.ProtectionScroll);
            equip.removeAttribute(EquipAttribute.LuckyDay);
            equip.removeAttribute(EquipAttribute.ScrollProtection);
            if (useTuc) {
                equip.removeAttribute(EquipAttribute.UpgradeCountProtection);
            }
            if (!success) {
                equip.removeAttribute(EquipAttribute.ReturnScroll); // a failed scroll ends it too
            }
            c.write(FieldPacket.showItemUpgradeEffect(chr.getId(), success, false, scrollID, equip.getItemId(), boom));
            if (!boom) {
                equip.recalcEnchantmentStats();
                equip.updateToChar(chr);

                if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                    int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                    Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                    otherEquip.copySecondaryStatsFrom(equip);
                    otherEquip.updateToChar(chr);
                }
                if (returnBefore != null) {
                    ReturnScrollModule.offer(chr, equip, returnBefore, scrollID);
                }
            }
            // the Enhance window's Scroll tab stays locked until this (ignored when the scroll wasn't used from it);
            // sent while the scroll is still in its slot
            c.write(FieldPacket.showScrollUseResult(uPos, scrollID, success ? 1 : boom ? 2 : 0,
                    shielded || scrollKept));
            if (scrollKept) {
                chr.chatMessage("The Guardian Scroll kept your scroll from being used up.");
                chr.dispose();
            } else {
                chr.consumeItem(scroll);
            }
        } else {
            chr.chatMessage("Could not find scroll data.");
            chr.dispose();
        }
    }

    /**
     * Potential scrolls by name: {1 = bonus potential, hidden grade given, highest hidden grade it accepts (0 = none),
     * third line chance}, or null (Potential Stamps are retired: their data only runs a script now).
     */
    static int[] potentialScrollInfo(int scrollId) {
        String name = net.swordie.ms.loaders.StringData.getItemStringById(scrollId);
        if (name == null || !name.contains("Potential Scroll")) {
            return null;
        }
        boolean bonus = name.contains("Bonus");
        int line = name.contains("Special") ? 100 : ItemConstants.getThirdLineChance();
        int b = bonus ? 1 : 0;
        if (name.contains("Legendary")) {
            return new int[]{b, ItemGrade.HiddenLegendary.getVal(), 2, line};
        } else if (name.contains("Unique")) {
            return new int[]{b, ItemGrade.HiddenUnique.getVal(), 2, line};
        } else if (name.contains("Epic")) {
            return new int[]{b, ItemGrade.HiddenEpic.getVal(), 1, line};
        }
        return new int[]{b, ItemGrade.HiddenRare.getVal(), 0, line};
    }

    /** 0 none, 1 rare .. 4 legendary, shown or hidden. */
    private static int currentHiddenGrade(Equip equip, boolean bonus) {
        int g = bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        if (g >= ItemGrade.Rare.getVal() && g <= ItemGrade.Legendary.getVal()) {
            return g - ItemGrade.Rare.getVal() + 1;
        }
        int hidden = -equip.getOption(0, bonus);
        return hidden >= 1 && hidden <= 4 ? hidden : 0;
    }

    @Handler(op = InHeader.USER_ITEM_OPTION_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserItemOptionUpgradeItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        inPacket.decodeInt(); //tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        byte bEnchantSkill = inPacket.decodeByte(); // bool or byte?
        Item scroll = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(uPos);
        var invType = ePos < 0 ? EQUIPPED : EQUIP;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (scroll == null || equip == null) {
            chr.chatMessage(SystemNotice, "Could not find scroll or equip.");
            chr.dispose();
            return;
        } else if (!ItemConstants.canEquipHavePotential(equip)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to add potential an ineligible item (id %d)", chr.getId(), equip.getItemId()));
            chr.dispose();
            return;
        }
        int scrollID = scroll.getItemId();
        int[] ps = potentialScrollInfo(scrollID);
        boolean bonusScroll = ps != null && ps[0] == 1;
        if (ps == null || currentHiddenGrade(equip, bonusScroll) > ps[2]
                || (bonusScroll && currentHiddenGrade(equip, false) == 0)) {
            chr.chatMessage(SystemNotice, "This scroll can't be used on that item.");
            chr.dispose();
            return;
        }
        ItemInfo scrollInfo = ItemData.getItemInfoByID(scrollID);
        if (scrollInfo == null) {
            chr.dispose();
            return;
        }
        Map<ScrollStat, Integer> vals = scrollInfo.getScrollStats();
        int chance = vals.getOrDefault(ScrollStat.success, 100);
        boolean success = GameConstants.rollScrollSuccess(chance);
        if (success) {
            if (bonusScroll) {
                equip.setHiddenOptionBonus((short) ps[1], ps[3]);
            } else {
                equip.setHiddenOptionBase((short) ps[1], ps[3]);
            }
            equip.releaseOptions(bonusScroll);

            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                otherEquip.copyItemOptionsFrom(equip);
                otherEquip.updateToChar(chr);

                chr.write(ZeroPool.egoEquipComplete(true, true));
            }
        }

        c.write(FieldPacket.showItemUpgradeEffect(chr.getId(), success, false, scrollID, equip.getItemId(), false));
        if (success && invType == EQUIPPED) {
            chr.getInventoryByType(invType).recalcBaseStats(chr);
        }
        equip.updateToChar(chr);
        chr.consumeItem(scroll);
        chr.write(net.swordie.ms.connection.packet.UserLocal.scrollResultNotice(success, scrollID, equip.getItemId()));
    }

    @Handler(op = InHeader.USER_ADDITIONAL_SLOT_EXTEND_ITEM_USE_REQUEST)
    public static void handleUserAdditionalSlotExtendItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);
        var invType = ePos < 0 ? EQUIPPED : EQUIP;
        Item equipItem = chr.getInventoryByType(invType).getItemBySlot(ePos);

        if (item == null || equipItem == null) {
            chr.chatMessage("Could not find either the use item or the equip.");
            return;
        }

        int itemID = item.getItemId();
        Equip equip = (Equip) equipItem;

        if (((Equip) equipItem).getOptionBonus(2) != 0) {
            chr.chatMessage("This item already has 3 bonus potentials.");
            chr.dispose();
            return;
        }
        ItemInfo scrollInfo = ItemData.getItemInfoByID(itemID);
        if (scrollInfo == null) {
            chr.dispose();
            return;
        }

        int successChance = scrollInfo.getScrollStats().getOrDefault(ScrollStat.success, 100);
        boolean success = GameConstants.rollScrollSuccess(successChance);
        if (success) {
            switch (itemID / 100) {
                case 20483: // Equipment bonus stamps
                    equip.setOption(2, equip.getRandomOption(true, 2), true);
                    break;
                default:
                    log.error("Unhandled slot extend item " + itemID);
                    chr.chatMessage("Unhandled slot extend item " + itemID);
                    return;
            }
            equip.updateToChar(chr);

            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                otherEquip.copyItemOptionsFrom(equip);
                otherEquip.updateToChar(chr);

                chr.write(ZeroPool.egoEquipComplete(true, true));
            }
        }
        chr.consumeItem(item);
        chr.write(FieldPacket.showItemUpgradeEffect(chr.getId(), success, false, itemID, equip.getItemId(), false));
    }

    @Handler(op = InHeader.USER_ITEM_SLOT_EXTEND_ITEM_USE_REQUEST)
    public static void handleUserItemSlotExtendItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);
        var invType = ePos < 0 ? EQUIPPED : EQUIP;
        Item equipItem = chr.getInventoryByType(invType).getItemBySlot(ePos);

        if (item == null || equipItem == null) {
            chr.chatMessage("Could not find either the use item or the equip.");
            chr.dispose();
            return;
        }

        int itemID = item.getItemId();
        Equip equip = (Equip) equipItem;

        if (((Equip) equipItem).getOptionBase(2) != 0) {
            chr.chatMessage("This item already has 3 potentials.");
            chr.dispose();
            return;
        }
        ItemInfo scrollInfo = ItemData.getItemInfoByID(itemID);
        if (scrollInfo == null) {
            chr.dispose();
            return;
        }

        int successChance = scrollInfo.getScrollStats().getOrDefault(ScrollStat.success, 100);
        boolean success = GameConstants.rollScrollSuccess(successChance);
        if (success) {
            switch (itemID / 100) {
                case 20495: // Equipment stamps
                    equip.setOption(2, equip.getRandomOption(false, 2), false);
                    break;
                default:
                    log.error("Unhandled slot extend item " + itemID);
                    chr.chatMessage("Unhandled slot extend item " + itemID);
                    return;
            }
            equip.updateToChar(chr);

            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                otherEquip.copyItemOptionsFrom(equip);
                otherEquip.updateToChar(chr);

                chr.write(ZeroPool.egoEquipComplete(true, true));
            }
        }
        chr.consumeItem(item);
        chr.write(FieldPacket.showItemUpgradeEffect(chr.getId(), success, false, itemID, equip.getItemId(), false));
    }

    @Handler(op = InHeader.USER_ADDITIONAL_OPT_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserAdditionalOptUpgradeItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (c.getWorld().isReboot()) {
            log.error(String.format("Character %d attempted to use bonus potential in reboot world.", chr.getId()));
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); //tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        byte bEnchantSkill = inPacket.decodeByte();
        Item scroll = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(uPos);
        var invType = ePos < 0 ? EQUIPPED : EQUIP;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (scroll == null || equip == null) {
            chr.chatMessage(SystemNotice, "Could not find scroll or equip.");
            return;
        }
        int scrollID = scroll.getItemId();
        int[] ps = potentialScrollInfo(scrollID);
        boolean bonusScroll = ps != null && ps[0] == 1;
        if (ps == null || currentHiddenGrade(equip, bonusScroll) > ps[2]
                || (bonusScroll && currentHiddenGrade(equip, false) == 0)) {
            chr.chatMessage(SystemNotice, "This scroll can't be used on that item.");
            chr.dispose();
            return;
        }
        ItemInfo scrollInfo = ItemData.getItemInfoByID(scrollID);
        if (scrollInfo == null) {
            chr.dispose();
            return;
        }
        Map<ScrollStat, Integer> vals = scrollInfo.getScrollStats();
        int chance = vals.getOrDefault(ScrollStat.success, 100);
        boolean success = GameConstants.rollScrollSuccess(chance);
        if (success) {
            if (bonusScroll) {
                equip.setHiddenOptionBonus((short) ps[1], ps[3]);
            } else {
                equip.setHiddenOptionBase((short) ps[1], ps[3]);
            }
            equip.releaseOptions(bonusScroll);

            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                otherEquip.copyItemOptionsFrom(equip);
                otherEquip.updateToChar(chr);

                chr.write(ZeroPool.egoEquipComplete(true, true));
            }
        }
        c.write(FieldPacket.showItemUpgradeEffect(chr.getId(), success, false, scrollID, equip.getItemId(), false));
        if (success && invType == EQUIPPED) {
            chr.getInventoryByType(invType).recalcBaseStats(chr);
        }
        equip.updateToChar(chr);
        chr.consumeItem(scroll);
    }

    @Handler(op = InHeader.USER_ITEM_RELEASE_REQUEST)
    public static void handleUserItemReleaseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        inPacket.decodeInt(); //tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(uPos); // old system with magnifying glasses
        var invType = ePos < 0 ? EQUIPPED : EQUIP;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (equip == null) {
            chr.chatMessage(SystemNotice, "Could not find equip.");
            return;
        }
        boolean base = equip.getOptionBase(0) < 0;
        boolean bonus = equip.getOptionBonus(0) < 0;
        if (base && bonus) {
            // reveal both the potential and the bonus potential
            equip.releaseOptions(false);
            equip.releaseOptions(true);
        } else {
            equip.releaseOptions(bonus);
        }

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copyItemOptionsFrom(equip);
            otherEquip.updateToChar(chr);

            chr.write(ZeroPool.egoEquipComplete(true, true));
        }

        c.write(FieldPacket.showItemReleaseEffect(chr.getId(), ePos, bonus));
        if (invType.equals(EQUIPPED)) {
            chr.getInventoryByType(invType).recalcBaseStats(chr);
        }
        equip.updateToChar(chr);
    }

    @Handler(op = InHeader.USER_TOADS_HAMMER_REQUEST)
    public static void handleUserToadsHammerRequest(Char chr, InPacket inPacket) {
        int type = inPacket.decodeShort();
        if (type != 1) {
            chr.dispose();
            return;
        }
        short fromPos = inPacket.decodeShort();
        short toPos = inPacket.decodeShort();
        Item fromRaw = chr.getEquipInventory().getItemBySlot(fromPos);
        if (fromRaw == null) fromRaw = chr.getEquippedInventory().getItemBySlot(fromPos);
        Item toRaw = chr.getEquipInventory().getItemBySlot(toPos);
        if (toRaw == null) toRaw = chr.getEquippedInventory().getItemBySlot(toPos);
        if (!(fromRaw instanceof Equip fromEquip) || !(toRaw instanceof Equip toEquip)) {
            chr.chatMessage("Could not find the equipment.");
            chr.dispose();
            return;
        }
        if (!ItemConstants.isSameBodyPart(fromEquip.getItemId(), toEquip.getItemId())) {
            chr.chatMessage("Both items must be the same equipment type.");
            chr.dispose();
            return;
        }
        if (fromEquip.getChuc() < 1) {
            chr.chatMessage("The source item needs at least 1 Star Force.");
            chr.dispose();
            return;
        }
        // Equipment Transfer rules: no traces or superior items, target at most 10 levels higher (20 from Lv.119 and below)
        if (fromEquip == toEquip
                || fromEquip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)
                || toEquip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)
                || fromEquip.getInfo() == null || toEquip.getInfo() == null
                || fromEquip.getInfo().isSuperiorEqp() || toEquip.getInfo().isSuperiorEqp()) {
            chr.chatMessage("These items cannot be used for Equipment Transfer.");
            chr.dispose();
            return;
        }
        int fromLevel = fromEquip.getReqLevel();
        int toLevel = toEquip.getReqLevel();
        int maxGap = fromLevel <= 119 ? 20 : 10;
        if (toLevel < fromLevel || toLevel - fromLevel > maxGap) {
            chr.chatMessage("The target item must be the same level or up to " + maxGap + " levels higher.");
            chr.dispose();
            return;
        }
        int stars = Math.min(fromEquip.getChuc() - 1, GameConstants.getMaxStars(toEquip));
        toEquip.setChuc((short) stars, true);
        toEquip.setOptions(new ArrayList<>(fromEquip.getOptions()));
        // potential above Epic comes over as Epic
        if (aboveEpic(toEquip.getBaseGrade())) {
            toEquip.setHiddenOptionBase(ItemGrade.HiddenEpic.getVal(), 100);
            toEquip.releaseOptions(false);
        }
        if (aboveEpic(toEquip.getBonusGrade())) {
            toEquip.setHiddenOptionBonus(ItemGrade.HiddenEpic.getVal(), 100);
            toEquip.releaseOptions(true);
        }
        toEquip.setSoulOptionId(fromEquip.getSoulOptionId());
        toEquip.setSoulSocketId(fromEquip.getSoulSocketId());
        toEquip.setSoulOption(fromEquip.getSoulOption());
        toEquip.updateToChar(chr);
        chr.consumeItem(fromRaw);
        chr.write(WvsContext.receiveToadsHammerRequestResult(type, fromRaw, toRaw));
    }

    private static boolean aboveEpic(int grade) {
        return grade == ItemGrade.Unique.getVal() || grade == ItemGrade.Legendary.getVal()
                || grade == ItemGrade.HiddenUnique.getVal() || grade == ItemGrade.HiddenLegendary.getVal();
    }

    @Handler(op = InHeader.USER_TOADS_HAMMER_HELP_REQUEST)
    public static void handleUserToadsHammerHelpRequest(Char chr, InPacket inPacket) {
        chr.dispose();
    }

    @Handler(op = InHeader.USER_LUCKY_ITEM_USE_REQUEST)
    public static void handleUserLuckyItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short uPos = inPacket.decodeShort();
        Item scroll = chr.getConsumeInventory().getItemBySlot(uPos);
        ItemInfo scrollInfo = scroll == null ? null : ItemData.getItemInfoByID(scroll.getItemId());
        if (scrollInfo == null) {
            chr.dispose();
            return;
        }
        // a set scroll: its setItemCategory is the set id the client shows, success rolls like any scroll
        int setId = scrollInfo.getScrollStats().getOrDefault(ScrollStat.setItemCategory, 0);
        boolean success = Util.succeedProp(scrollInfo.getScrollStats().getOrDefault(ScrollStat.success, 100));
        if (setId != 0 && success) {
            chr.getQuestManager().addQuestWithQr(chr, QuestConstants.ZERO_SET_QUEST, String.valueOf(setId));
        }
        chr.write(UserPacket.showItemLuckyItemEffect(success, scroll.getItemId()));
        chr.consumeItem(scroll.getItemId(), 1);
        chr.dispose();
    }
}
