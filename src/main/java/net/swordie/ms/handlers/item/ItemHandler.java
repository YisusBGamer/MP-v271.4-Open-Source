package net.swordie.ms.handlers.item;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.BeautyMix;
import net.swordie.ms.client.character.BeautyModule;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.*;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.partyquests.towerofoz.OzTowerModule;
import net.swordie.ms.client.soulcollection.BossSoulType;
import net.swordie.ms.client.soulcollection.SoulCollectionModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.mob.boss.will.WillModule;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.loaders.containerclasses.MakingSkillRecipe;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static net.swordie.ms.enums.ChatType.*;
import static net.swordie.ms.enums.InvType.*;

public class ItemHandler {

    private static final Logger log = LogManager.getLogger();

    @Handler(op = InHeader.USER_PORTAL_SCROLL_USE_REQUEST)
    public static void handleUserPortalScrollUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.PortalScrollLimit.getVal()) > 0 || !field.isChannelField()) {
            chr.chatMessage("You may not use a return scroll in this map.");
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); //tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item scroll = chr.getConsumeInventory().getItemBySlot(slot);
        ItemInfo ii = ItemData.getItemInfoByID(itemID);
        if (scroll == null || scroll.getItemId() != itemID || ii == null || chr.getHP() <= 0) {
            // the scroll has to be in that slot
            chr.dispose();
            return;
        }
        Field toField;

        if (itemID != ItemConstants.RETURN_SCROLL_NEAREST_TOWN) {
            toField = chr.getOrCreateFieldByCurrentInstanceType(ii.getMoveTo());
        } else {
            toField = chr.getOrCreateFieldByCurrentInstanceType(field.getInfo().getReturnMap());
        }
        if (toField == null) {
            chr.dispose();
            return;
        }
        chr.consumeItem(scroll);
        Portal portal = toField.getInfo().getDefaultPortal();
        chr.warp(toField, portal);
    }


    @Handler(op = InHeader.USER_STAT_CHANGE_ITEM_CANCEL_REQUEST)
    public static void handleUserStatChangeItemCancelRequest(Char chr, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int itemID = inPacket.decodeInt();
        tsm.removeStatsBySkill(itemID);
        tsm.sendResetStatPacket();
    }


    @Handler(op = InHeader.USER_CONSUME_CASH_ITEM_USE_REQUEST)
    public static void handleUserConsumeCashItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Inventory cashInv = chr.getInventoryByType(InvType.CASH);
        inPacket.decodeInt(); // 0
        inPacket.decodeInt(); // tick
        short pos = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = cashInv.getItemBySlot(pos);
        ItemInfo itemInfo = ItemData.getItemInfoByID(itemId);
        if (item == null || item.getItemId() != itemId) {
            chr.dispose();
            return;
        }
        if (BeautyMix.isMixItem(itemId)) {
            BeautyMix.handle(chr, item, inPacket);
            return;
        }
        if (openChoiceBeautyCoupon(chr, item)) {
            return;
        }
        if (itemId == net.swordie.ms.client.character.DeathPenaltyModule.SAFETY_CHARM) {
            net.swordie.ms.client.character.DeathPenaltyModule.useSafetyCharm(chr, item);
            return;
        }
        if (ItemConstants.isAPResetItem(itemId)) { // 5050100: no more fields
            resetAbility(chr, item);
            return;
        }
        if (ItemConstants.isAPChangeItem(itemId)) { // 5050000: B8 up, B8 down
            changeAbility(chr, inPacket, item);
            return;
        }
        if (ItemConstants.isCirculatorItem(itemId)) {
            useCirculator(chr, item, inPacket.decodeByte()); // v265 RE: then byte preset
            return;
        }
        if (Incubators.isIncubator(itemId)) { // Etc/incubatorInfo.img: then 4 egg item, 4 egg slot
            Incubators.use(chr, item, pos, inPacket);
            return;
        }
        int prefix = itemId / 10000;
        if (prefix == 553) {
            ItemHandlerModule.handleRewardItem(chr, itemInfo, item);
        } else if (prefix == 539) {
            ItemHandlerModule.handleAvatarMegaphone(c, inPacket, chr, itemId, item);
        } else if (prefix == 519) {
            ItemHandlerModule.handlePetSkillItem(inPacket, chr, itemId, item);
        } else if (prefix == 504) {
            if (itemId == ItemConstants.HYPER_TELEPORT_ROCK) {
                ItemHandlerModule.handleTeleportRock(c, inPacket, chr);
            } else if (itemId / 1000 == 5044) {
                ItemHandlerModule.useWarpDrive(c, inPacket, chr, itemInfo, item);
            } else {
                chr.dispose();
            }
        } else if (prefix == 538) { // Rock of Evolution: pets are evolved by Mar the Fairy (Ellinia) or @menu > Shops > Pets
            chr.chatMessage("Talk to Mar the Fairy in Ellinia, or @menu > Shops > Pets, to evolve your pet with this.");
            chr.dispose();
        } else if (prefix == 537) {
            ItemHandlerModule.displayAdBoard(chr, inPacket.decodeString());
        }
        else if (itemId == ItemConstants.ITEM_TAG || itemId == ItemConstants.ITEM_TAG_CLEAR) {
            ItemHandlerModule.handleItemTag(chr, inPacket, item);
        } else {
            Equip medal = (Equip) chr.getEquippedInventory().getFirstItemByBodyPart(BodyPart.Medal);
            int medalInt = 0;
            if (medal != null) {
                medalInt = (medal.getAnvilId() == 0 ? medal.getItemId() : medal.getAnvilId()); // Check for Anvilled medal
            }
            String medalString = (medalInt == 0 ? "" : String.format("<%s> ", StringData.getItemStringById(medalInt)));

            switch (itemId) {
                case ItemConstants.SP_RESET_1ST_JOB:
                case ItemConstants.SP_RESET_2ND_JOB:
                case ItemConstants.SP_RESET_3RD_JOB:
                case ItemConstants.SP_RESET_4TH_JOB:
                    ItemHandlerModule.handleSingleSpReset(chr, inPacket, itemId % 10, item);
                    break;
                case ItemConstants.SP_RESET_SCROLL:
                    ItemHandlerModule.handleFullSpReset(chr, item);
                    break;
                case ItemConstants.BEAST_TAMER_ANIMAL_SP_RESET:
                    ItemHandlerModule.handleBeastTamerSpReset(chr, inPacket, item);
                    break;
                case ItemConstants.RED_CUBE: // Red Cube
                case ItemConstants.BLACK_CUBE: // Black cube
                case ItemConstants.GLOWING_CUBE:
                case ItemConstants.BRIGHT_CUBE:
                case PotentialCubes.EQUALITY_CUBE:
                    ItemHandlerModule.handleCube(c, inPacket, chr, pos, itemId, item);
                    break;
                case ItemConstants.VIOLET_CUBE:
                    ItemHandlerModule.handleVioletCube(inPacket, chr, itemId);
                    break;
                case ItemConstants.BONUS_POT_CUBE: // Bonus Potential Cube
                case ItemConstants.SPECIAL_BONUS_POT_CUBE: // [Special] Bonus Potential Cube
                case ItemConstants.WHITE_BONUS_POT_CUBE: // White Bonus Potential Cube
                case ItemConstants.BONUS_GLOWING_CUBE:
                case ItemConstants.BONUS_BRIGHT_CUBE:
                    ItemHandlerModule.handleBonusCube(c, inPacket, chr, pos, itemId, item);
                    break;
                case ItemConstants.NEBULITE_DIFFUSER:
                    ItemHandlerModule.handleNebuliteDiffuser(inPacket, chr, item);
                    break;
                case ItemConstants.SUPER_MEGAPHONE:
                    ItemHandlerModule.handleSuperMegaphone(inPacket, chr, medalString, item);
                    break;
                case ItemConstants.ITEM_MEGAPHONE:
                    ItemHandlerModule.handleItemMegaphone(inPacket, chr, medalString, item);
                    break;
                case ItemConstants.TRIPLE_MEGAPHONE:
                    ItemHandlerModule.handleTripleMegaphone(inPacket, chr, medalString, item);
                    break;
                case ItemConstants.FUSION_ANVIL:
                case ItemConstants.FUSION_ANVIL_2:
                    ItemHandlerModule.handleFusionAnvil(inPacket, chr, item);
                    break;
                case ItemConstants.PLATINUM_SCISSORS_OF_KARMA:
                    ItemHandlerModule.handlePlatinumScissorsOfKarma(inPacket, chr, item);
                    break;
                case ItemConstants.FAMILIAR_SLOT_COUPON:
                case ItemConstants.FAMILIAR_SLOT_COUPON_2:
                    ItemHandlerModule.handleFamiliarSlotExpansionCoupon(inPacket, chr, item);
                    break;
                case ItemConstants.RED_FAMILIAR_CARD:
                case ItemConstants.RED_FAMILIAR_CARD_2:
                    ItemHandlerModule.handleRedFamiliarCard(inPacket, chr, item);
                    break;
                case ItemConstants.FAMILIAR_BREAKTHROUGH_CARD:
                case ItemConstants.FAMILIAR_BREAKTHROUGH_CARD_2:
                    ItemHandlerModule.handleFamiliarBreakthroughCard(inPacket, chr, item);
                    break;
                case ItemConstants.CARTA_INDIGO_PEARL:
                case ItemConstants.CARTA_TEAL_PEARL:
                case ItemConstants.CARTA_SCARLET_PEARL:
                    ItemHandlerModule.handleCartasPearl(inPacket, chr, item);
                    break;
                default:
                    if (ItemHandlerModule.isCashAssistScroll(itemId)) {
                        ItemHandlerModule.handleAddEquipAttributeScroll(inPacket, chr, item);
                        break;
                    }
                    if (ItemHandlerModule.handleDataCashItem(chr, inPacket, itemInfo, item)) {
                        return;
                    }
                    if (ItemHandlerModule.isRewardBox(itemInfo)) {
                        // cash boxes with a reward list (e.g. 5222074)
                        ItemHandlerModule.handleRewardItem(chr, itemInfo, item);
                        return;
                    }
                    chr.chatMessage(Mob, String.format("Cash item %d is not implemented, notify Sjonnie pls.", itemId));
                    log.debug(String.format("Cash item %d is not implemented, rest packet: %s.", itemId, inPacket));
                    chr.dispose();
                    return;
            }
        }
        chr.dispose();
    }

    // client: int tick, short slot, short equip, int, byte, byte, string (no Reset x3 count on this path)
    @Handler(op = InHeader.USER_FREE_MIRACLE_CUBE_ITEM_USE_REQUEST)
    public static void handleUserFreeMiracleCubeItemUseRequest(Client c, InPacket inPacket)
    {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        short pos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(pos);
        if (item != null && ItemConstants.isCube(item.getItemId())) {
            ItemHandlerModule.handleCube(c, chr, pos, item.getItemId(), item, ePos, 1);
        } else {
            chr.dispose();
        }
    }

    // client: int tick, short slot, int item, int equip, int
    @Handler(op = InHeader.USER_BONUS_MYSTICAL_CUBE_ITEM_USE_REQUEST)
    public static void handleUserBonusMysticalCubeItemUseRequest(Client c, InPacket inPacket)
    {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        short pos = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        short ePos = (short) inPacket.decodeInt();
        inPacket.decodeInt();
        useBonusCube(c, chr, pos, itemId, ePos, 1);
    }

    // client: int tick, int item, short slot, short equip, int, byte count (1 / 3 = Reset x3)
    @Handler(op = InHeader.USER_BONUS_BRIGHT_CUBE_ITEM_USE_REQUEST)
    public static void handleUserBonusBrightCubeItemUseRequest(Client c, InPacket inPacket)
    {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        int itemId = inPacket.decodeInt();
        short pos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        inPacket.decodeInt();
        int count = inPacket.decodeByte();
        useBonusCube(c, chr, pos, itemId, ePos, count);
    }

    // client: int type (-1 main, -2 bonus), int equip, byte count
    @Handler(op = InHeader.USER_POTENTIAL_MESO_RESET_REQUEST)
    public static void handleUserPotentialMesoResetRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int type = inPacket.decodeInt();
        short ePos = (short) inPacket.decodeInt();
        int count = inPacket.decodeByte();
        ItemHandlerModule.handlePotentialMesoReset(c, chr, type, ePos, count);
    }

    /**
     * The Enhance window's cube slot: an RPC call (one method per cube family) instead of the cube use requests.
     * Every method sends the cube slots and the equip first; the call id is the packet's last int. The reply clears
     * the client's request lock; result 0 plays the window's success effect (rank up = the second one), the
     * before/after popup comes on BLACK_CUBE_RESULT as for a cube used from the inventory.
     */
    @Handler(op = InHeader.POTENTIAL_RPC_REQUEST)
    public static void handlePotentialRpcRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr == null || inPacket.getUnreadAmount() < 11) {
            return;
        }
        int method = inPacket.decodeInt();
        inPacket.decodeShort();
        int mask = inPacket.decodeByte() & 0xFF;
        byte[] b = inPacket.decodeArr(inPacket.getUnreadAmount());
        int callId = b.length >= 4 ? readIntLE(b, b.length - 4) : 0;
        if (PotentialCubes.isOwnRpc(method)) {
            PotentialCubes.onRpc(chr, method, mask, b, callId);
            return;
        }
        if (!PotentialCubes.isFamilyUse(method) && PotentialCubes.hasVioletChoice(chr)) {
            PotentialCubes.onVioletPick(chr, method, mask, b, callId);
            return;
        }
        int[] off = {0};
        List<Integer> slots = new ArrayList<>();
        int ePos = 0;
        boolean ok = (mask & 3) == 3;
        if (ok) {
            int n = readVarint(b, off);
            for (int i = 0; i < n && off[0] + 4 <= b.length; i++, off[0] += 4) {
                slots.add(readIntLE(b, off[0]));
            }
            ok = n > 0 && slots.size() == n && off[0] + 4 <= b.length;
            if (ok) {
                ePos = readIntLE(b, off[0]);
            }
        }
        Item cube = ok ? chr.getInventoryByType(CONSUME).getItemBySlot(slots.get(0)) : null;
        if (ok && (cube == null || !ItemConstants.isCube(cube.getItemId()))) {
            cube = chr.getInventoryByType(CASH).getItemBySlot(slots.get(0));
        }
        if (cube == null || !ItemConstants.isCube(cube.getItemId())) {
            log.info(String.format("Enhance window cube call %08X refused (mask %d, slots %s): %s", method, mask, slots,
                    inPacket));
            // a call without the slot list: most likely a cube the window holds but won't apply at the item's rank
            chr.chatMessage(SystemNotice, ok ? "This item can't be used from the Enhance window; use it from your inventory."
                    : "This cube can't be used on an item of this rank.");
            potentialRpcReply(chr, callId, 1, false);
            chr.dispose();
            return;
        }
        int itemId = cube.getItemId();
        short pos = (short) cube.getBagIndex();
        int count = slots.size() >= 3 ? 3 : 1;
        Equip equip = (Equip) chr.getInventoryByType(ePos < 0 ? EQUIPPED : EQUIP).getItemBySlot(ePos);
        boolean bonus = isBonusCube(itemId);
        int grade = equip == null ? 0 : bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        MemorialCubeInfo before = chr.getMemorialCubeInfo();
        if (bonus) {
            ItemHandlerModule.handleBonusCube(c, chr, pos, itemId, cube, (short) ePos, count);
        } else {
            ItemHandlerModule.handleCube(c, chr, pos, itemId, cube, (short) ePos, count);
        }
        MemorialCubeInfo after = chr.getMemorialCubeInfo();
        if (after == null || after == before) {
            potentialRpcReply(chr, callId, 1, false);
            chr.dispose();
            return;
        }
        boolean rankUp = false;
        for (int i = 0; i < after.getResultCount(); i++) {
            Equip e = after.getResult(i);
            rankUp |= e != null && (bonus ? e.getBonusGrade() : e.getBaseGrade()) > grade;
        }
        potentialRpcReply(chr, callId, 0, rankUp);
    }

    private static boolean isBonusCube(int itemId) {
        if (itemId / 1000 == 2730 || itemId == ItemConstants.BONUS_POT_CUBE
                || itemId == ItemConstants.SPECIAL_BONUS_POT_CUBE || itemId == ItemConstants.WHITE_BONUS_POT_CUBE
                || itemId == ItemConstants.BONUS_GLOWING_CUBE || itemId == ItemConstants.BONUS_BRIGHT_CUBE) {
            return true;
        }
        String name = StringData.getItemStringById(itemId);
        return name != null && (name.contains("Bonus") || name.contains("Additional"));
    }

    private static void potentialRpcReply(Char chr, int callId, int result, boolean rankUp) {
        OutPacket out = new OutPacket(OutHeader.POTENTIAL_RPC_RESULT);
        out.encodeInt(callId);
        out.encodeInt(0);
        out.encodeByte(3);
        out.encodeInt(result);
        out.encodeInt(rankUp ? 1 : 0);
        chr.write(out);
    }

    private static int readIntLE(byte[] b, int o) {
        return (b[o] & 0xFF) | (b[o + 1] & 0xFF) << 8 | (b[o + 2] & 0xFF) << 16 | (b[o + 3] & 0xFF) << 24;
    }

    /** The client's zigzag varint (a vector's length). */
    private static int readVarint(byte[] b, int[] off) {
        int v = 0;
        for (int shift = 0; off[0] < b.length && shift < 35; shift += 7) {
            int x = b[off[0]++] & 0xFF;
            v |= (x & 0x7F) << shift;
            if ((x & 0x80) == 0) {
                break;
            }
        }
        return (v >>> 1) ^ -(v & 1);
    }

    private static void useBonusCube(Client c, Char chr, short pos, int itemId, short ePos, int count) {
        // Use-tab bonus cubes, or a cash one (Bonus Bright) sent on the same request
        InvType inv = itemId / 1000000 == 5 ? InvType.CASH : InvType.CONSUME; // hardcode-ok
        Item item = chr.getInventoryByType(inv).getItemBySlot(pos);
        if (item != null && item.getItemId() == itemId && ItemConstants.isCube(itemId)) {
            ItemHandlerModule.handleBonusCube(c, chr, pos, itemId, item, ePos, count);
        } else {
            chr.dispose();
        }
    }


    @Handler(op = InHeader.USER_ABILITY_RESET_ITEM_USE_REQUEST)
    public static void handleUserAbilityResetItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item item = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID || !ItemConstants.isAPResetItem(itemID)) {
            chr.dispose();
            return;
        }
        resetAbility(chr, item);
    }

    /** AP Reset Scroll (Use 2501xxx via 339, Cash 5050100 via 331): every STR/DEX/INT/LUK point above 4 back as AP. */
    static void resetAbility(Char chr, Item item) {
        if (chr.getLevel() <= 10 && JobConstants.isBeginnerJob(chr.getJob())) {
            chr.dispose();
            return;
        }
        int newAP = 0;
        Map<Stat, Object> stats = new HashMap<>();
        for (Stat s : new Stat[]{Stat.str, Stat.dex, Stat.inte, Stat.luk}) {
            int cur = chr.getStat(s);
            int excess = cur - 4;
            if (excess > 0) {
                newAP += excess;
                chr.setStat(s, 4);
                stats.put(s, (short) 4);
            }
        }
        chr.addStat(Stat.ap, (short) newAP);
        stats.put(Stat.ap, (short) chr.getStat(Stat.ap));
        chr.consumeItem(item, 1);
        chr.write(WvsContext.abilityResetItemResult(chr.getId()));
        chr.write(WvsContext.statChanged(stats));
    }

    @Handler(op = InHeader.USER_ABILITY_CHANGE_ITEM_USE_REQUEST)
    public static void handleUserAbilityChangeItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item item = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID || !ItemConstants.isAPChangeItem(itemID)) {
            chr.dispose();
            return;
        }
        changeAbility(chr, inPacket, item);
    }

    /** AP change (Use 2502000 via 340, Cash 5050000 via 331): B8 stat to raise, B8 stat to lower (int stat, int). */
    static void changeAbility(Char chr, InPacket inPacket, Item item) {
        int inc = inPacket.decodeInt();
        inPacket.decodeInt();
        int dec = inPacket.decodeInt();
        inPacket.decodeInt();
        Stat toStat = Stat.getByVal(inc);
        Stat fromStat = Stat.getByVal(dec);
        if (toStat == null || fromStat == null) {
            chr.dispose();
            return;
        }
        int[] hpMpToAdd = GameConstants.getHpMpPerLevel(chr.getJob());
        int hp = hpMpToAdd[0];
        int mp = hpMpToAdd[1];
        Map<Stat, Object> stats = new HashMap<>();
        if (fromStat == Stat.mhp) {
            chr.addStat(fromStat, -hp);
            stats.put(fromStat, chr.getStat(fromStat));
        } else if (fromStat == Stat.mmp && !JobConstants.isNoManaJob(chr.getJob())) {
            chr.addStat(fromStat, -mp);
            stats.put(fromStat, chr.getStat(fromStat));
        } else if (fromStat == Stat.str || fromStat == Stat.dex || fromStat == Stat.inte || fromStat == Stat.luk) {
            chr.addStat(fromStat, -1);
            stats.put(fromStat, (short) chr.getStat(fromStat));
        }
        if (toStat == Stat.mhp) {
            chr.addStat(toStat, hp);
            stats.put(toStat, chr.getStat(toStat));
        } else if (toStat == Stat.mmp && !JobConstants.isNoManaJob(chr.getJob())) {
            chr.addStat(toStat, mp);
            stats.put(toStat, chr.getStat(toStat));
        } else if (toStat == Stat.str || toStat == Stat.dex || toStat == Stat.inte || toStat == Stat.luk) {
            chr.addStat(toStat, 1);
            stats.put(toStat, (short) chr.getStat(toStat));
        }
        chr.consumeItem(item, 1);
        chr.write(WvsContext.statChanged(stats));
    }

    /** A Miracle Circulator from the Use inventory (the cash ones come through USER_CONSUME_CASH_ITEM_USE_REQUEST). */
    @Handler(op = InHeader.USER_REQUEST_CHARACTER_POTENTIAL_SKILL_RAND_SET_UI)
    public static void handleUserRequestCharacterPotentialSkillRandSetUi(Char chr, InPacket inPacket) {
        int preset = inPacket.decodeByte();
        int itemID = inPacket.decodeInt();
        int slot = inPacket.decodeInt();
        Item item = chr.getInventoryByType(InvType.CONSUME).getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID) {
            item = chr.getInventoryByType(InvType.CONSUME).getItemByItemID(itemID);
        }
        useCirculator(chr, item, preset);
    }

    /**
     * Rolls a Circulator on a preset and shows the before/after window; the result waits for the player's pick
     * (USER_MIRACLE_CIRCULATOR_OPTION_REQUEST). The Circulator is used up either way.
     */
    static void useCirculator(Char chr, Item item, int preset) {
        var cpm = chr.getPotentialMan();
        if (item == null || !ItemConstants.isCirculatorItem(item.getItemId())
                || !net.swordie.ms.client.character.potential.CharacterPotentialMan.isValidPreset(preset)) {
            chr.dispose();
            return;
        }
        if (cpm.getPotentials(preset).size() < 3) {
            chr.chatMessage("You need 3 Inner Ability lines to use a Circulator.");
            chr.dispose();
            return;
        }
        int itemID = item.getItemId();
        var lines = cpm.rollCirculator(preset, itemID);
        chr.consumeItem(item);
        chr.write(WvsContext.miracleCirculatorResult(lines, itemID, preset));
    }

    @Handler(ops = {InHeader.USER_ARCANE_SYMBOL_REQUEST, InHeader.USER_AUTHENTIC_SYMBOL_REQUEST,
            InHeader.USER_GRAND_AUTHENTIC_SYMBOL_REQUEST})
    public static void handleUserArcaneSymbolRequest(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        int fromPos = inPacket.decodeInt();
        int toPos = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0; // the worn symbol's slot
        switch (type) {
            case 0:
                // merge
                // the worn symbol by the slot the client names (by id only if that slot holds none), and the one to
                // merge from its slot, or else any unworn copy of the same symbol
                Item fromItem = chr.getEquipInventory().getItemBySlot(fromPos);
                Item toItem = toPos != 0 ? chr.getEquippedInventory().getItemBySlot(toPos) : null;
                if (!(toItem instanceof Equip) || !ItemConstants.hasSymbolBlock(toItem.getItemId())) {
                    toItem = fromItem == null ? null : chr.getEquippedInventory().getItemByItemID(fromItem.getItemId());
                }
                if (toItem != null && (!(fromItem instanceof Equip) || fromItem.getItemId() != toItem.getItemId())) {
                    int symbolId = toItem.getItemId();
                    fromItem = chr.getEquipInventory().getItems().stream()
                            .filter(i -> i instanceof Equip && i.getItemId() == symbolId)
                            .min(Comparator.comparingInt(i -> ((Equip) i).getSymbolLevel())).orElse(null);
                }
                Equip fromEq = fromItem instanceof Equip ? (Equip) fromItem : null;
                Equip toEq = toItem instanceof Equip ? (Equip) toItem : null;
                if (fromEq == null || toEq == null || fromEq.getItemId() != toEq.getItemId() || !ItemConstants.hasSymbolBlock(toEq.getItemId())) {
                    log.warn(String.format("[Symbol] %s merge failed: from slot %d = %s, to slot %d = %s", chr.getName(),
                            fromPos, fromItem == null ? "nothing" : fromItem.getItemId(), toPos,
                            toItem == null ? "nothing" : toItem.getItemId()));
                    chr.boxMessage("Could not find one of the symbols.");
                    return;
                }
                if (toEq.getSymbolExp() >= ItemConstants.getRequiredSymbolExp(toEq.getSymbolLevel(), toEq.getItemId())) {
                    chr.boxMessage("Your symbol already is at max exp.");
                    return;
                }
                if (fromEq.getSymbolLevel() > toEq.getSymbolLevel()) {
                    chr.boxMessage("Your merging symbol's level is higher than your equipped symbol's level.");
                    return;
                }
                chr.consumeItem(fromEq);
                toEq.setSymbolExp(Math.min(toEq.getSymbolExp() + fromEq.getSymbolExp(), ItemConstants.getRequiredSymbolExp(toEq.getSymbolLevel(), toEq.getItemId())));
                toEq.updateToChar(chr);
                break;
            case 1:
                // enhance
                // v265: 4 type, 4 worn slot (1600+ Arcane, 1700+ Sacred, 1800+ Grand Sacred)
                Item worn = chr.getEquippedInventory().getItemBySlot(fromPos);
                if (!(worn instanceof Equip symbol) || !ItemConstants.hasSymbolBlock(worn.getItemId())) {
                    chr.chatMessage("Could not find a valid symbol.");
                    chr.dispose();
                    return;
                }
                int reqSymbolExp = ItemConstants.getRequiredSymbolExp(symbol.getSymbolLevel(), symbol.getItemId());
                if (symbol.getSymbolLevel() >= ItemConstants.getMaxSymbolLevel(symbol.getItemId())
                        || symbol.getSymbolExp() < reqSymbolExp) {
                    chr.chatMessage("Your symbol does not have enough EXP to level up.");
                    chr.dispose();
                    return;
                }
                long cost = ItemConstants.getSymbolMoneyReqByLevel(symbol.getSymbolLevel(), symbol.getItemId());
                if (cost > chr.getMoney()) {
                    chr.chatMessage("You do not have enough mesos to level up your symbol.");
                    chr.dispose();
                    return;
                }
                chr.deductMoney(cost, ProgressionLog.Sink.FEES);
                symbol.setSymbolLevel((short) (symbol.getSymbolLevel() + 1));
                symbol.initSymbolStats(symbol.getSymbolLevel(), symbol.getSymbolExp() - reqSymbolExp, chr.getJob());
                symbol.updateToChar(chr);
                break;
            case 2:
                // mass merge
                int itemId = fromPos;
                toEq = (Equip) chr.getEquippedInventory().getItemByItemID(itemId);
                if (toEq == null || !ItemConstants.hasSymbolBlock(itemId)) {
                    chr.chatMessage("Could not find an arcane symbol to transfer to.");
                    return;
                }
                if (toEq.hasSymbolExpForLevelUp()) {
                    chr.chatMessage("First level your symbol before trying to add more symbols onto it.");
                    return;
                }
                Set<Equip> matchingSymbols = new HashSet<>();
                for (Item item : chr.getEquipInventory().getItems()) {
                    if (item.getItemId() == toEq.getItemId() && ((Equip) item).getSymbolLevel() == 1) {
                        matchingSymbols.add((Equip) item);
                    }
                }
                for (Equip eqSymbol : matchingSymbols) {
                    chr.consumeItem(eqSymbol);
                    toEq.addSymbolExp(eqSymbol.getTotalSymbolExp());
                    if (toEq.hasSymbolExpForLevelUp()) {
                        break;
                    }
                }
                toEq.updateToChar(chr);
                break;
        }
    }

    /** Use-tab Teleport Rocks (2320000-2329999), client 0x1453bb070: 2 slot, 4 item, target, 4 tick. Used up on a move. */
    @Handler(op = InHeader.USER_MAP_TRANSFER_ITEM_USE_REQUEST)
    public static void handleUserMapTransferItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (itemId / 10000 != 232 || item == null || item.getItemId() != itemId) {
            chr.dispose();
            return;
        }
        if (ItemHandlerModule.teleportRockTo(c, inPacket, chr)) {
            chr.consumeItem(item.getItemId(), 1);
        }
    }

    /** World map double-click: a Hyper Teleport Rock move to that field (the rock is not used up). */
    @Handler(op = InHeader.USER_WORLD_MAP_TELEPORT_REQUEST)
    public static void handleUserWorldMapTeleportRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int fieldId = inPacket.decodeInt();
        if (!chr.hasItem(ItemConstants.HYPER_TELEPORT_ROCK)) {
            chr.chatMessage("You need a Hyper Teleport Rock to move from the World Map.");
            chr.dispose();
            return;
        }
        if (chr.getField().getInstance() != null) {
            chr.chatMessage("You can't use a hyper teleport rock inside an instance.");
            chr.dispose();
            return;
        }
        if (fieldId == chr.getFieldID()) {
            chr.dispose();
            return;
        }
        ItemHandlerModule.teleportRockToField(chr, fieldId);
    }

    @Handler(op = InHeader.USER_STAT_CHANGE_ITEM_USE_REQUEST)
    public static void handleUserStatChangeItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr.getHP() <= 0) {
            chr.dispose();
            return;
        }
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.StatChangeItemConsumeLimit.getVal()) > 0) {
            chr.dispose();
            return;
        }

        var statChangeItemCooltimeSec = field.getInfo().getConsumeItemCoolTime();
        var consumeTimeDelta = chr.getNextAvailableConsumeItemTime() - System.currentTimeMillis();
        if (statChangeItemCooltimeSec > 0 && consumeTimeDelta > 0) {
            chr.chatMessage("You cannot use consumable items (%d second(s) left).", consumeTimeDelta / 1000);
            chr.dispose();
            return;
        }

        if (FieldConstants.isWillP2Field(chr.getField().getId()) && !WillModule.canUseConsumable(chr)) {
            chr.chatMessage("You will need the power of moonlight in order to use that here.");
            chr.dispose();
            return;
        }

        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        if (inPacket.getUnreadAmount() >= 4) {
            inPacket.decodeInt(); // 1
        }
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID) {
            chr.dispose();
            return;
        }
        ItemInfo rewardInfo = ItemData.getItemInfoByID(itemID);
        if (itemID == ItemConstants.FAMILIAR_ESSENCE) {
            // refills the Summon Gauge by 50% (spec FfatigueR 50)
            var fcm = chr.getFamiliarCodexManager();
            int max = net.swordie.ms.constants.GameConstants.MAX_SUMMON_GAUGE;
            if (fcm.getSummonGauge() >= max) {
                chr.chatMessage("Your Summon Gauge is already full.");
                chr.dispose();
                return;
            }
            fcm.setSummonGauge(Math.min(max, fcm.getSummonGauge() + max / 2));
            chr.write(UserLocal.codexUpdate(fcm,
                    net.swordie.ms.client.character.familiar.FamiliarCodexUpdateMask.SummonGauge.getVal()));
            chr.consumeItem(item.getItemId(), 1);
            chr.dispose();
            return;
        }
        if (ItemHandlerModule.handleUseDataItem(chr, rewardInfo, item)) {
            return; // cosmetic coupons, EXP items
        }
        if (ItemHandlerModule.isRewardBox(rewardInfo) && rewardInfo.getSpecStats().isEmpty()) {
            // boxes like 2022336 are used like potions, but only have a reward list
            ItemHandlerModule.handleRewardItem(chr, rewardInfo, item);
            chr.dispose();
            return;
        }
        if (statChangeItemCooltimeSec > 0) {
            chr.setNextAvailableConsumeItemTime(System.currentTimeMillis() + statChangeItemCooltimeSec * 1000);
            chr.write(UserLocal.userConsumeItemCooltime());
        }
        long hpBefore = chr.getHP();
        chr.useStatChangeItem(item, true);
        net.swordie.ms.client.character.achievement.MapleAchievements.onItemUse(chr, itemID, chr.getHP() - hpBefore);
    }


    /**
     * Beauty coupons (styling is free, so coupons are never used up): a coupon for one look (Item.wz spec/cosmetic,
     * e.g. 2540000, 5157000) applies it; the salon, choice, colour, lens and skin coupons (5150-5154xxx) open the
     * free style picker on their part.
     */
    private static boolean openChoiceBeautyCoupon(Char chr, Item item) {
        int id = item.getItemId();
        ItemInfo ii = ItemData.getItemInfoByID(id);
        if (ii != null && ii.getCosmeticLook() > 0) {
            return ItemHandlerModule.handleUseDataItem(chr, ii, item);
        }
        int prefix = id / 1000;
        if (prefix < 5150 || prefix > 5154) {
            return false;
        }
        if (chr.getScriptManager().getLastActiveScriptType() != ScriptType.None || chr.isTalkingToNpc()) {
            chr.chatMessage("Finish your current conversation before using a beauty coupon.");
            chr.dispose();
            return true;
        }
        String part = switch (prefix) {
            case 5151 -> "dye";
            case 5152 -> id % 1000 >= 100 && id % 1000 < 200 ? "lens" : "face";
            case 5153 -> "skin";
            default -> "hair";
        };
        chr.getScriptManager().startScript(BeautyModule.SPEAKER, BeautyModule.SCRIPT, ScriptType.Npc,
                Map.<String, Object>of("start_on", part));
        chr.dispose();
        return true;
    }

    @Handler(op = InHeader.USER_CONSUME_HAIR_ITEM_USE_REQUEST)
    public static void handleUserConsumeHairItemUseRequest(Char chr, InPacket inPacket) {
        // read as int tick, short slot, int item; the client map hints at an extra int first, so try that too
        InPacket other = inPacket.clone();
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = findCoupon(chr, slot, itemId);
        if (item == null && other.getUnreadAmount() >= 14) {
            other.decodeInt();
            other.decodeInt();
            slot = other.decodeShort();
            itemId = other.decodeInt();
            item = findCoupon(chr, slot, itemId);
        }
        if (item == null) {
            log.debug(String.format("Hair coupon use: no item %d in slot %d (chr %d), rest: %s", itemId, slot,
                    chr.getId(), inPacket));
            chr.dispose();
            return;
        }
        if (openChoiceBeautyCoupon(chr, item)) {
            return;
        }
        if (!ItemHandlerModule.handleUseDataItem(chr, ItemData.getItemInfoByID(itemId), item)) {
            chr.chatMessage("This coupon can't be used.");
            chr.dispose();
        }
    }

    /** Use-tab mix dye coupons: short slot, int item, then the mixing window's answer (see BeautyMix) */
    @Handler(op = InHeader.USER_MIX_DYE_ITEM_USE_REQUEST)
    public static void handleUserMixDyeItemUseRequest(Char chr, InPacket inPacket) {
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = findCoupon(chr, slot, itemId);
        if (item == null || !BeautyMix.isMixItem(itemId)) {
            chr.dispose();
            return;
        }
        BeautyMix.handle(chr, item, inPacket);
    }

    private static Item findCoupon(Char chr, short slot, int itemId) {
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || item.getItemId() != itemId) {
            item = chr.getCashInventory().getItemBySlot(slot);
        }
        return item != null && item.getItemId() == itemId ? item : null;
    }

    @Handler(op = InHeader.USER_SCRIPT_ITEM_USE_REQUEST)
    public static void handleUserScriptItemUseRequest(Client c, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        int quant = inPacket.decodeInt();
        Char chr = c.getChr();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || item.getItemId() != itemID) {
            item = chr.getCashInventory().getItemBySlot(slot);
        }
        if (item == null || item.getItemId() != itemID) {
            chr.dispose();
            return;
        }
        if (openChoiceBeautyCoupon(chr, item)) {
            return;
        }
        // Damage Skin Check
        if (DamageSkinConstants.isHandledDamageSkinItemId(itemID)) {
            var success = chr.addDamageSkin(itemID);
            if (success) {
                var itemName = StringData.getItemStringById(itemID);
                chr.chatMessage(String.format("the %s has been added to your account's damage skin collection.", itemName != null ? itemName : "Unknown Damage Skin"));
            }
            return;
        }


        // Oz Pill
        if (ItemConstants.isOzPill(itemID)) {
            if (OzTowerModule.usePill(chr, itemID)) {
                chr.consumeItem(item);
            }
            chr.dispose();
            return;
        }


        if (net.swordie.ms.client.character.BurningModule.isBurninator(itemID)) {
            String why = net.swordie.ms.client.character.BurningModule.useBurninator(chr, itemID);
            if (why == null) {
                chr.consumeItem(item);
            } else {
                chr.chatMessage(net.swordie.ms.enums.ChatType.Notice2, why);
            }
            chr.dispose();
            return;
        }

        if (net.swordie.ms.client.character.hexa.HexaModule.useEnergyItem(chr, itemID, 1)) { // Sol Erda energy
            chr.consumeItem(item);
            chr.dispose();
            return;
        }

        if (net.swordie.ms.client.character.items.EternalSources.handles(itemID)) {
            chr.getScriptManager().startScript(itemID, "eternal_source", ScriptType.Item);
            chr.dispose();
            return;
        }

        String script = String.valueOf(itemID);
        ItemInfo ii = ItemData.getItemInfoByID(itemID);
        if (ii == null) {
            chr.dispose();
            return;
        }
        if ((ii.getScript() == null || ii.getScript().isEmpty()) && ItemHandlerModule.handleUseDataItem(chr, ii, item)) {
            return; // cosmetic coupons, EXP items
        }
        if (ii.getScript() != null && !"".equals(ii.getScript())) {
            script = ii.getScript();
        } else if (!ii.getItemRewardInfos().isEmpty()) {
            // a box with a reward list and no script (e.g. Spiegelmann's Badge Chest): open it
            ItemHandlerModule.handleRewardItem(chr, ii, item);
            chr.dispose();
            return;
        }
        if (!itemScriptExists(script)) {
            if (ItemHandlerModule.handleScriptlessItem(chr, ii, item)) {
                return;
            }
            var setBox = SetBoxes.get(itemID);
            if (setBox != null) {
                if (setBox.isChoice()) {
                    chr.getScriptManager().startScript(itemID, "set_box_selector", ScriptType.Item);
                } else {
                    String why = SetBoxes.open(chr, item, setBox, 0);
                    if (why != null) {
                        chr.chatMessage(why);
                    }
                }
                chr.dispose();
                return;
            }
            int boxLevel = net.swordie.ms.client.character.PlusGiftBoxes.levelFor(itemID);
            if (boxLevel > chr.getLevel()) {
                chr.chatMessage("You must be level " + boxLevel + " to open this box.");
                chr.dispose();
                return;
            }
            var box = ItemBoxRewards.get(itemID);
            if (box != null) {
                // contents from the item's description: a choice opens the selection dialog
                if (box.isChoice()) {
                    chr.getScriptManager().startScript(itemID, "box_selector", ScriptType.Item);
                } else {
                    var rewards = ItemBoxRewards.rewardsFor(chr, box);
                    if (box.isRandom() && !rewards.isEmpty()) {
                        rewards = List.of(ItemBoxRewards.pick(rewards));
                    }
                    String why = ItemBoxRewards.open(chr, item, rewards);
                    if (why != null) {
                        chr.chatMessage(why);
                    }
                }
                chr.dispose();
                return;
            }
        }
        chr.getScriptManager().startScript(itemID, script, ScriptType.Item);
        chr.dispose();
    }

    /** The Return Scroll choice (client 0x1438e7a80): int tick, byte 1 = back to the old stats. */
    @Handler(op = InHeader.USER_RETURN_EFFECT_RESPONSE)
    public static void handleUserReturnEffectResponse(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        ReturnScrollModule.respond(chr, inPacket.decodeByte() != 0);
    }

    private static final Map<Integer, List<int[]>> summonSackMobs = new java.util.concurrent.ConcurrentHashMap<>();

    /** Item.wz Consume/0210.img/<item>/mob: {mob id, chance %} */
    private static List<int[]> summonSackMobs(int itemId) {
        return summonSackMobs.computeIfAbsent(itemId, id -> {
            List<int[]> list = new ArrayList<>();
            try {
                var img = new net.swordie.ms.jwzlib.WzFile(net.swordie.ms.ServerConstants.WZ_DIR + "/Item.wz")
                        .getChildByPath(String.format("Consume/%04d.img/%08d/mob", id / 10000, id));
                if (img != null) {
                    for (Object o : img) {
                        var m = (net.swordie.ms.jwzlib.WzObject<?, ?>) o;
                        var mid = m.getChild("id");
                        var prob = m.getChild("prob");
                        if (mid instanceof net.swordie.ms.jwzlib.WzProperty<?> pm) {
                            int chance = prob instanceof net.swordie.ms.jwzlib.WzProperty<?> pp ? pp.getIntValue() : 100;
                            list.add(new int[]{pm.getIntValue(), chance});
                        }
                    }
                }
            } catch (RuntimeException e) {
                log.error("Could not read the monsters of summon sack " + id, e);
            }
            return list;
        });
    }

    /** Summoning sacks (210xxxx, client 0x1453ab480): int tick, short slot, int item. */
    @Handler(op = InHeader.USER_MOB_SUMMON_ITEM_USE_REQUEST)
    public static void handleUserMobSummonItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        Field field = chr.getField();
        if (item == null || item.getItemId() != itemId || itemId / 10000 != 210) {
            chr.dispose();
            return;
        }
        if ((field.getInfo().getFieldLimit() & FieldOption.SummonLimit.getVal()) > 0 || field.getInfo().isTown()
                || !field.isChannelField()) {
            chr.chatMessage("You can't summon monsters here.");
            chr.dispose();
            return;
        }
        var mobs = summonSackMobs(itemId);
        if (mobs.isEmpty()) {
            chr.chatMessage("Nothing comes out of this sack.");
            chr.dispose();
            return;
        }
        chr.consumeItem(item, 1);
        for (int[] m : mobs) {
            if (Util.succeedProp(m[1])) {
                field.spawnMob(m[0], chr.getPosition());
            }
        }
        chr.dispose();
    }

    private static Item potItem(Char chr) {
        for (Item i : chr.getCashInventory().getItems()) {
            if (PotionPot.isPot(i.getItemId())) {
                return i;
            }
        }
        return null;
    }

    private static void sendPot(Char chr, PotionPot pot, int type, int code) {
        if (pot != null) {
            chr.write(pot.update(chr.getId()));
        }
        chr.write(PotionPot.result(type, code));
        chr.dispose();
    }

    /** Potion Pot drink (client 0x14540d5d0): int tick, short slot, int pot item. Fills HP and MP from the pot. */
    @Handler(op = InHeader.POTION_POT_USE_REQUEST)
    public static void handlePotionPotUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item potItem = potItem(chr);
        if (potItem == null || potItem.getItemId() != itemId) {
            sendPot(chr, null, 0, PotionPot.NO_ITEM);
            return;
        }
        PotionPot pot = PotionPot.getOrCreate(chr, potItem);
        if (pot.isRusty()) {
            sendPot(chr, pot, 0, PotionPot.RUSTY);
            return;
        }
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.StatChangeItemConsumeLimit.getVal()) > 0) {
            sendPot(chr, pot, 0, 8);
            return;
        }
        int needHp = pot.useHp ? Math.max(0, chr.getMaxHP() - chr.getHP()) : 0;
        int needMp = pot.useMp ? Math.max(0, chr.getMaxMP() - chr.getMP()) : 0;
        int hp = Math.min(needHp, pot.hp), mp = Math.min(needMp, pot.mp);
        if (hp <= 0 && mp <= 0) {
            sendPot(chr, pot, 0, PotionPot.EMPTY);
            return;
        }
        pot.hp -= hp;
        pot.mp -= mp;
        pot.save(chr);
        if (hp > 0) {
            chr.heal(hp);
        }
        if (mp > 0) {
            chr.healMP(mp);
        }
        chr.write(pot.update(chr.getId()));
        chr.dispose();
    }

    /** Potion Pot fill (client 0x14540d760): int tick, short slot, int potion item, int quantity. */
    @Handler(op = InHeader.POTION_POT_ADD_REQUEST)
    public static void handlePotionPotAddRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int potionId = inPacket.decodeInt();
        int quantity = inPacket.decodeInt();
        Item potItem = potItem(chr);
        Item potion = chr.getConsumeInventory().getItemBySlot(slot);
        if (potItem == null) {
            sendPot(chr, null, 0, PotionPot.NO_ITEM);
            return;
        }
        PotionPot pot = PotionPot.getOrCreate(chr, potItem);
        if (pot.isRusty()) {
            sendPot(chr, pot, 0, PotionPot.RUSTY);
            return;
        }
        int[] per = PotionPot.potionValue(chr, potionId);
        if (potion == null || potion.getItemId() != potionId || quantity <= 0 || (per[0] <= 0 && per[1] <= 0)) {
            sendPot(chr, pot, 0, PotionPot.NO_ITEM);
            return;
        }
        if (pot.hp >= pot.max && pot.mp >= pot.max) {
            sendPot(chr, pot, 0, PotionPot.FULL);
            return;
        }
        int used = 0;
        for (int i = 0; i < Math.min(quantity, potion.getQuantity()); i++) {
            if (pot.hp >= pot.max && pot.mp >= pot.max) {
                break;
            }
            pot.hp = (int) Math.min(pot.max, (long) pot.hp + per[0]);
            pot.mp = (int) Math.min(pot.max, (long) pot.mp + per[1]);
            used++;
        }
        pot.save(chr);
        chr.consumeItem(potion, used);
        chr.write(pot.update(chr.getId()));
        chr.write(PotionPot.result(2, 0));
        chr.dispose();
    }

    /** Potion Pot options (client 0x1443f8750): byte HP box, byte MP box. */
    @Handler(op = InHeader.POTION_POT_OPTION_REQUEST)
    public static void handlePotionPotOptionRequest(Char chr, InPacket inPacket) {
        boolean hp = inPacket.decodeByte() != 0;
        boolean mp = inPacket.decodeByte() != 0;
        Item potItem = potItem(chr);
        if (potItem != null) {
            PotionPot pot = PotionPot.getOrCreate(chr, potItem);
            pot.useHp = hp;
            pot.useMp = mp;
            pot.save(chr);
        }
        chr.dispose();
    }

    /** Pot Embiggener / Rust Remover (client 0x1443fd7a0): int tick, byte kind (0 / 1), int item, short slot. */
    @Handler(op = InHeader.POTION_POT_INCREASE_REQUEST)
    public static void handlePotionPotIncreaseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        int kind = inPacket.decodeByte();
        int itemId = inPacket.decodeInt();
        short slot = inPacket.decodeShort();
        Item potItem = potItem(chr);
        Item use = chr.getCashInventory().getItemBySlot(slot);
        if (use == null || use.getItemId() != itemId) {
            use = chr.getCashInventory().getItemByItemID(itemId);
        }
        if (potItem == null || use == null) {
            sendPot(chr, null, 0, PotionPot.NO_ITEM);
            return;
        }
        PotionPot pot = PotionPot.getOrCreate(chr, potItem);
        if (kind == 0) {
            if (itemId / 1000 != 5821) {
                chr.dispose();
                return;
            }
            if (pot.max >= PotionPot.MAX) {
                sendPot(chr, pot, 0, PotionPot.MAX_CAPACITY);
                return;
            }
            pot.max = Math.min(PotionPot.MAX, pot.max + PotionPot.STEP);
            pot.save(chr);
            chr.consumeItem(use, 1);
            sendPot(chr, pot, 3, 0);
        } else {
            var ii = ItemData.getItemInfoByID(itemId);
            if (itemId / 1000 != 5500 || itemId % 100 < 10 || itemId % 100 > 19 || ii == null || ii.getAddTime() <= 0) {
                chr.dispose();
                return;
            }
            if (!pot.isRusty()) {
                sendPot(chr, pot, 0, PotionPot.NOT_RUSTY);
                return;
            }
            pot.end = System.currentTimeMillis() + ii.getAddTime() * 1000L;
            pot.save(chr);
            chr.consumeItem(use, 1);
            sendPot(chr, pot, 4, 0);
        }
    }

    /** SP Reset Scroll from the Use tab (client 0x1453b98c0): int tick, short slot, int item. */
    @Handler(op = InHeader.USER_SKILL_RESET_ITEM_USE_REQUEST)
    public static void handleUserSkillResetItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        if (item == null || item.getItemId() != itemId || itemId / 1000 != 2500) {
            chr.dispose();
            return;
        }
        if (itemId == ItemConstants.BEAST_TAMER_SP_RESET_SCROLL && !JobConstants.isBeastTamer(chr.getJob())) {
            chr.chatMessage("Only Beast Tamers can use this scroll.");
            chr.dispose();
            return;
        }
        ItemHandlerModule.handleFullSpReset(chr, item);
        chr.write(WvsContext.skillResetItemResult(chr.getId()));
        chr.dispose();
    }

    // client: short slot, int item
    @Handler(op = InHeader.USER_CHAR_SLOT_INC_ITEM_USE_REQUEST)
    public static void handleUserCharSlotIncItemUseRequest(Char chr, InPacket inPacket) {
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Item item = findCoupon(chr, slot, itemId);
        var user = chr.getUser();
        if (item == null || user == null) {
            chr.dispose();
            return;
        }
        if (user.getCharacterSlots() >= net.swordie.ms.ServerConstants.MAX_CHARACTERS) {
            chr.chatMessage("Your account already has the most character slots it can have.");
            chr.dispose();
            return;
        }
        user.setCharacterSlots(user.getCharacterSlots() + 1);
        chr.consumeItem(item, 1);
        chr.chatMessage(String.format("Your account now has %d character slots.", user.getCharacterSlots()));
        chr.dispose();
    }

    // client: int tick, short slot, short, short equip slot
    @Handler(op = InHeader.USER_KARMA_CONSUME_ITEM_USE_REQUEST)
    public static void handleUserKarmaConsumeItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        inPacket.decodeShort();
        short equipPos = inPacket.decodeShort();
        Item scissors = chr.getConsumeInventory().getItemBySlot(slot);
        if (scissors == null || scissors.getItemId() / 10000 != 272) {
            chr.dispose();
            return;
        }
        ItemHandlerModule.handleScissorsOfKarma(chr, equipPos, scissors);
        chr.dispose();
    }

    private static boolean itemScriptExists(String script) {
        return new java.io.File(String.format("%s/item/%s.py", net.swordie.ms.ServerConstants.SCRIPT_DIR, script)).exists();
    }


    @Handler(op = InHeader.USER_EQUIPMENT_ENCHANT_WITH_SINGLE_UI_REQUEST)
    public static void handleUserEquipmentEnchantWithSingleUIRequest(Client c, InPacket inPacket) {
        byte equipmentEnchantType = inPacket.decodeByte();

        Char chr = c.getChr();
        EquipmentEnchantType eeType = EquipmentEnchantType.getByVal(equipmentEnchantType);

        if (eeType == null) {
            log.error(String.format("Unknown enchant UI request %d", equipmentEnchantType));
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        switch (eeType) {
            case ScrollUpgradeRequest:
                ItemHandlerModule.doEnchantScrollUpgrade(c, inPacket, chr);
                break;
            case ScrollUpgradeDisplay:
                ItemHandlerModule.displayEnchantScrollUpgrade(c, inPacket, chr);
                break;
            case HyperUpgradeResult:
            case HyperUpgradeExtra:
                ItemHandlerModule.doEnchantHyperUpgrade(c, inPacket, chr, eeType == EquipmentEnchantType.HyperUpgradeExtra);
                break;
            case TransmissionResult:
                ItemHandlerModule.doEnchantTransmission(c, inPacket, chr);
                break;
            case StarCoreTraceRestoration:
                ItemHandlerModule.doStarCoreTraceRestoration(c, inPacket, chr);
                break;
            case HyperUpgradeDisplay:
                ItemHandlerModule.displayEnchantHyperUpgrade(c, inPacket, chr);
                break;
            case MiniGameDisplay: // not sent by the v271 client
                break;
            case ScrollTimerEffective:
            case ShowHyperUpgradeResult:
                break;
            default:
                log.debug("Unhandled Equipment Enchant Type: " + eeType);
                chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
                break;
        }
    }

    @Handler(op = InHeader.USER_SKILL_LEARN_ITEM_USE_REQUEST)
    public static void handleUserLearnItemUseRequest(Client c, InPacket inPacket) {
        inPacket.decodeInt(); //tick
        short pos = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Char chr = c.getChr();

        ItemInfo ii = ItemData.getItemInfoByID(itemID);
        if (ii == null || !chr.hasItem(itemID)) {
            chr.chatMessage("Could not find that item.");
            return;
        }
        int masterLevel = ii.getMasterLv();
        int reqSkillLv = ii.getReqSkillLv();
        int skillid = 0;
        Map<ScrollStat, Integer> vals = ii.getScrollStats();
        int chance = vals.getOrDefault(ScrollStat.success, 100);

        for (int skill : ii.getSkills()) {
            if (chr.hasSkill(skill)) {
                skillid = skill;
                break;
            }
        }
        if (ii.getSkills().isEmpty() && ItemHandlerModule.isRewardBox(ii)) {
            // random mastery book boxes (2290xxx/2291xxx with a reward list): open them
            Item box = chr.getConsumeInventory().getItemBySlot(pos);
            if (box != null && box.getItemId() == itemID) {
                ItemHandlerModule.handleRewardItem(chr, ii, box);
            }
            chr.dispose();
            return;
        }
        Skill skill = skillid == 0 ? null : chr.getSkill(skillid);
        if (skill == null) {
            chr.chatMessage(SystemNotice, "You don't have the skill this Mastery Book is for.");
            chr.dispose();
            return;
        }
        if (skillid == 0 || (skill.getMasterLevel() >= masterLevel) || skill.getCurrentLevel() < reqSkillLv) {
            chr.chatMessage(SystemNotice, "You cannot use this Mastery Book.");
            chr.dispose();
            return;
        }

        if (skill.getCurrentLevel() >= reqSkillLv && skill.getMasterLevel() < masterLevel) {
            boolean always = ServerFeatures.on(ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED);
            chance = GameConstants.boosted(chance);
            chr.chatMessage(Mob, "Success Chance: " + (always ? 100 : chance) + "%.");
            chr.consumeItem(itemID, 1);
            if (always || Util.succeedProp(chance)) { // mastery books follow scrolls_always_succeed too
                skill.setMasterLevel(masterLevel);
                chr.addSkill(skill);
                chr.write(WvsContext.changeSkillRecordResult(skill));
                chr.chatMessage(Notice2, "[Mastery Book] Item id: " + itemID + "  set Skill id: " + skillid + "'s Master Level to: " + masterLevel + ".");
            } else {
                chr.chatMessage(Notice2, "[Mastery Book] Item id: " + itemID + " was used, however it was unsuccessful.");
            }
        }
        chr.dispose();
    }

    @Handler(op = InHeader.USER_ITEM_SKILL_SOCKET_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserItemSkillSocketUpdateItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);
        InvType invType = ePos < 0 ? EQUIPPED : EQUIP;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (item == null || equip == null || !ItemConstants.isWeapon(equip.getItemId())
                || !ItemConstants.isSoulEnchanter(item.getItemId()) || !ItemConstants.canEquipHavePotential(equip)
                || equip.getReqLevel() + equip.getiIncReq() < ItemConstants.MIN_LEVEL_FOR_SOUL_SOCKET) {
            chr.dispose();
            return;
        }
        if (equip.getSoulSocketId() != 0) {
            chr.chatMessage("This weapon is already a Soul Weapon.");
            chr.dispose();
            return;
        }
        ItemInfo enchanterInfo = ItemData.getItemInfoByID(item.getItemId());
        if (enchanterInfo == null) {
            chr.dispose();
            return;
        }
        int successProp = enchanterInfo.getScrollStats().getOrDefault(ScrollStat.success, 100);
        boolean success = GameConstants.rollScrollSuccess(successProp);
        if (success) {
            equip.setSoulSocketId((short) (item.getItemId() % ItemConstants.SOUL_ENCHANTER_BASE_ID));
            equip.updateToChar(chr);

            if (ePos < 0 && JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
                Item other = chr.getEquippedInventory().getItemBySlot(otherEquipPos);
                if (other instanceof Equip) {
                    Equip otherEquip = (Equip) other;
                    otherEquip.copySoulOptionsFrom(equip);
                    otherEquip.updateToChar(chr);
                }

                chr.write(ZeroPool.egoEquipComplete(true, true));
            }
            if (ePos < 0) {
                chr.initSoulMP(); // the gauge starts right away, without a re-equip or relog
            }
        }
        chr.consumeItem(item);
        chr.write(UserPacket.soulEnchanterResult(success));
    }

    @Handler(op = InHeader.USER_ITEM_SKILL_OPTION_UPGRADE_ITEM_USE_REQUEST)
    public static void handleUserItemSkillOptionUpdateItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        inPacket.decodeInt(); // tick
        short uPos = inPacket.decodeShort();
        short ePos = inPacket.decodeShort();
        Item item = chr.getConsumeInventory().getItemBySlot(uPos);

        var inv = ePos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();

        Equip equip = (Equip) inv.getItemBySlot(ePos);
        if (item == null || equip == null || !ItemConstants.isWeapon(equip.getItemId())
                || !ItemConstants.isSoul(item.getItemId()) || equip.getSoulSocketId() == 0) {
            chr.dispose();
            return;
        }

        int curSoulOptionId = equip.getSoulOptionId();
        equip.setSoulOptionId((short) (1 + item.getItemId() % ItemConstants.SOUL_ITEM_BASE_ID));

        if (ePos < 0) {
            if (curSoulOptionId > 0) {
                int soulSkillID = SoulCollectionConstants.getSoulSkillFromSoulID(curSoulOptionId);
                if (chr.hasSkill(soulSkillID)) {
                    chr.removeSkillAndSendPacket(soulSkillID);
                }
            }
            if (equip.getSoulOptionId() > 0) {
                var soulSkillID = SoulCollectionConstants.getSoulSkillFromSoulID(equip.getSoulOptionId());
                var soulType = SoulCollectionConstants.getBossSoulTypeBySoulId(equip.getSoulOptionId());
                var soulSkillLv = 1;
                if (soulType != null) {
                    soulSkillLv = SoulCollectionModule.getSoulSkillLevelBySoulType(chr, soulType);
                }
                chr.addSkill(soulSkillID, soulSkillLv, 2);
            }
        }
        short option = ItemConstants.getSoulOptionFromSoul(item.getItemId());
        if (option == 0) {
            option = (short) ItemConstants.getRandomSoulOption();
        }
        equip.setSoulOption(option);
        equip.updateToChar(chr);

        if (ePos < 0 && JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
            Item other = chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            if (other instanceof Equip) {
                Equip otherEquip = (Equip) other;
                otherEquip.copySoulOptionsFrom(equip);
                otherEquip.updateToChar(chr);
            }

            chr.write(ZeroPool.egoEquipComplete(true, true));
        }

        chr.consumeItem(item);
        chr.write(UserPacket.soulOptionResult(true, false, equip.getItemId(), item.getItemId()));

        if (ePos < 0) {
            // the new soul's stat line counts right away, and the gauge starts over
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.capHpMpToMax();
            chr.getTemporaryStatManager().removeStat(CharacterTemporaryStat.SoulMP);
            chr.getTemporaryStatManager().removeStat(CharacterTemporaryStat.FullSoulMP);
            chr.initSoulMP();
        }
    }

    @Handler(op = InHeader.USER_WEAPON_TEMP_ITEM_OPTION_REQUEST)
    public static void handleUserWeaponTempItemOptionRequest(Char chr, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(CharacterTemporaryStat.SoulMP)) {
            Option o = new Option();
            o.nOption = tsm.getOption(CharacterTemporaryStat.SoulMP).nOption;
            o.xOption = tsm.getOption(CharacterTemporaryStat.SoulMP).xOption;
            o.rOption = tsm.getOption(CharacterTemporaryStat.SoulMP).rOption;
            tsm.putCharacterStatValue(CharacterTemporaryStat.FullSoulMP, o);
            tsm.sendSetStatPacket();
        }
        chr.dispose();
    }

    @Handler(op = InHeader.USER_PROTECT_BUFF_DIE_ITEM_REQUEST)
    public static void handleUserProtectBuffDieItemRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        boolean used = inPacket.decodeByte() != 0;
        if (used) {
            // grabs the first one from the list of buffItems
            Item buffProtector = chr.getBuffProtectorItem();
            if (buffProtector != null) {
                chr.setBuffProtector(true);
                chr.consumeItem(buffProtector);
                chr.write(UserLocal.setBuffProtector(buffProtector.getItemId(), true));
            } else {
                chr.getOffenseManager().addOffense(String.format("Character id %d tried to use a buff without having the appropriate item.", chr.getId()));
            }
        }
    }

    @Handler(op = InHeader.USER_DEFAULT_WING_ITEM)
    public static void handleUserDefaultWingItem(Char chr, InPacket inPacket) {
        int wingItem = inPacket.decodeInt();
        if (wingItem == ItemConstants.ANGELIC_BUSTER_DEFAULT_WING) {
            chr.getAvatarData().getCharacterStat().setWingItem(wingItem);
            chr.getField().broadcastPacket(UserRemote.setDefaultWingItem(chr), chr);
        }
    }

    @Handler(op = InHeader.USER_RECIPE_OPEN_ITEM_USE_REQUEST)
    public static void handleUserRecipeOpenItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt();// tick
        short pos = inPacket.decodeShort();// // nPOS
        int itemId = inPacket.decodeInt();// nItemID

        Item item = chr.getInventoryByType(CONSUME).getItemBySlot(pos);
        if (item.getItemId() != itemId) {
            chr.dispose();
            return;
        }
        if (chr != null && chr.getHP() > 0 && ItemConstants.isRecipeOpenItem(itemId)) {
            ItemInfo recipe = ItemData.getItemInfoByID(itemId);
            if (recipe != null) {
                int recipeId = recipe.getSpecStats().getOrDefault(SpecStat.recipe, 0);
                int reqSkillLevel = recipe.getSpecStats().getOrDefault(SpecStat.reqSkillLevel, 0);
                MakingSkillRecipe msr = SkillData.getRecipeById(recipeId);
                if (msr != null && msr.isNeedOpenItem()) {
                    if (chr.getSkillLevel(msr.getReqSkillID()) < reqSkillLevel || chr.getSkillLevel(recipeId) > 0) {
                        chr.chatMessage("You need a higher Profession level to learn this recipe.");
                        return;
                    }
                    chr.consumeItem(itemId, 1);
                    chr.addSkill(recipeId, 1, 1);
                }
            }
        }
    }

    @Handler(op = InHeader.USER_ACTIVATE_NICK_ITEM)
    public static void handleUserActivateNickItem(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int nickItem = inPacket.decodeInt();

        if (nickItem != 0 && !chr.hasItem(nickItem)) {
            chr.getOffenseManager().addOffense("Tried to use nickitem " + nickItem + " without having it.");
            return;
        }

        chr.setNickItem(nickItem);
        chr.write(WvsContext.nickSkillExpired(nickItem == 0));
        chr.getField().broadcastPacket(UserRemote.setActiveNickItem(chr, null), chr);
    }

    @Handler(op = InHeader.USER_BAG_ITEM_USE_REQUEST)
    public static void handleUserBagItemUseRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        short pos = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        byte invType = inPacket.decodeByte();

        Item bagOwner = chr.getInventoryByType(InvType.getInvTypeByVal(invType)).getItemBySlot(pos);
        if (bagOwner == null || !ItemConstants.isBagItem(itemId)) {
            chr.dispose();
            return;
        }
        boolean firstTime = false;
        if (bagOwner.getBagItemIndex() == -1) { // Open BagItem for first time
            bagOwner.openBagItemFirstTime(chr);
            if (bagOwner.getBagItemIndex() == -1) {
                chr.chatMessage(String.format("You have reached the limit of bags in your %s tab", getInvTypeByVal(invType).toString()));
                chr.dispose();
                return;
            }
            chr.write(WvsContext.inventoryOperation(true, false, InventoryOperation.Add, pos, pos, pos, bagOwner));
            firstTime = true;
        }

        chr.write(UserLocal.bagItemUseResult(bagOwner.getBagItemIndex(), bagOwner.getItemId(), firstTime));
        chr.setLastBagItemIndexOpened(bagOwner.getBagItemIndex());
        chr.dispose();
    }

    @Handler(op = InHeader.USER_KAISER_COLOR_CHANGE_ITEM_USE_REQUEST)
    public static void handleUserKaiserColourChangeItemUseRequest(Char chr, InPacket inPacket) {
        int nPos = inPacket.decodeInt();
        int itemId = inPacket.decodeInt();
        inPacket.decodeByte();

        if (!chr.hasItem(itemId)) {
            chr.dispose();
            return;
        }

        Item item = chr.getInventoryByType(CONSUME).getItemBySlot(nPos);
        if (item == null) {
            chr.dispose();
            return;
        }

        ItemHandlerModule.handleKaiserChangeColorCoupon(chr, item);
        chr.dispose();
    }

    @Handler(op = InHeader.USER_LOTTERY_ITEM_USE_REQUEST)
    public static void handleUserLotteryItemUseRequest(Char chr, InPacket inPacket) {
        int nPos = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        // v265 (0x1453bbc20): short slot, int item, byte state, int
        int unk1 = inPacket.decodeByte();
        int unk2 = 0;
        int quantity = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;

        Item item = chr.getInventoryByType(CONSUME).getItemBySlot(nPos);
        if (item == null || item.getItemId() != itemId) {
            chr.dispose();
            return;
        }

        // boxes with a reward list in the item data (e.g. Spiegelmann's Badge Chest) that aren't spinning lottery
        // boxes: open them straight away
        ItemInfo ii = ItemData.getItemInfoByID(itemId);
        if (!ItemConstants.isLotteryItem(itemId) && ii != null && !ii.getItemRewardInfos().isEmpty()
                && (ii.getScript() == null || ii.getScript().isEmpty())) {
            if (unk1 == 0 && unk2 == 0) {
                ItemHandlerModule.handleRewardItem(chr, ii, item);
            }
            chr.dispose();
            return;
        }

        ItemHandlerModule.handleUserLotteryItemUseRequest(chr, item, unk1, unk2, quantity);
        chr.dispose();
    }

    /** The Soul Collection window's register button: every soul placed in it, by inventory slot. */
    @Handler(op = InHeader.USER_CHANGE_SOUL_COLLECTION_REQUEST)
    public static void handleUserChangeSoulCollectionRequest(Char chr, InPacket inPacket) {
        int n = inPacket.decodeInt();
        for (int i = 0; i < n && inPacket.getUnreadAmount() >= 8; i++) {
            int invType = inPacket.decodeInt();
            int slot = inPacket.decodeInt();
            var inv = chr.getInventoryByType(net.swordie.ms.enums.InvType.getInvTypeByVal(invType));
            Item item = inv == null ? null : inv.getItemBySlot(slot);
            if (item == null) {
                continue;
            }
            for (BossSoulType type : BossSoulType.values()) {
                if (ItemConstants.getBossSoulCollectionFlagByItemId(type, item.getItemId()) != null) {
                    ItemHandlerModule.handleSoulCollection(chr, item, type);
                    break;
                }
            }
        }
        chr.dispose();
    }

    @Handler(op = InHeader.USER_SELECT_SOUL_SKILL_UP_REQUEST)
    public static void handleUserSelectSoulSkillUpRequest(Char chr, InPacket inPacket) {
        // Packet sent when dragging Boss Soul into 'Soul Collection' UI.
        // v265 (0x1453a6a50): int tick, byte, then three ints (soul type, slot and one more; order not known), so
        // the pair that names a soul item of that type is used
        inPacket.decodeInt(); // tick
        inPacket.decodeByte();
        int[] v = new int[3];
        for (int i = 0; i < 3; i++) {
            v[i] = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : -1;
        }
        BossSoulType bossSoulType = null;
        Item item = null;
        for (int t = 0; t < 3 && item == null; t++) {
            for (int p = 0; p < 3 && item == null; p++) {
                var type = t == p ? null : BossSoulType.getByVal(v[t]);
                var it = type == null ? null : chr.getInventoryByType(CONSUME).getItemBySlot(v[p]);
                if (it != null && ItemConstants.getBossSoulCollectionFlagByItemId(type, it.getItemId()) != null) {
                    bossSoulType = type;
                    item = it;
                }
            }
        }
        if (item == null || bossSoulType == null) {
            chr.dispose();
            return;
        }

        ItemHandlerModule.handleSoulCollection(chr, item, bossSoulType);
        chr.dispose();
    }

    /**
     * Capture items (Item.wz/Consume/0227.img: Empty Bottle, Paper Toy Box, Lasso, Scorpion Catch Rope, ...): the
     * client asks to catch the monster in front of the character with one. maxHp: the mob's HP percent at most (0 =
     * any); left/right/top/bottom: the catch box; success: percent (0 = always).
     */
    private record Bridle(int item, int mob, int create, int maxHp, int left, int right, int top, int bottom,
                          int success) {
    }

    /** resources/data/bridle-items.tsv */
    private static final java.util.function.Supplier<Map<Integer, Bridle>> BRIDLE_ITEMS =
            net.swordie.ms.loaders.DataTable.cached("bridle-items", t -> {
                Map<Integer, Bridle> m = new HashMap<>();
                for (var r : t.rows()) {
                    m.put(r.i("item"), new Bridle(r.i("item"), r.i("mob"), r.i("create"), r.i("maxHp"), r.i("left"),
                            r.i("right"), r.i("top"), r.i("bottom"), r.i("success")));
                }
                return Map.copyOf(m);
            });
    private static final int BRIDLE_RANGE_SLACK = 60; // the server's mob positions lag a little behind the client's

    private static Bridle getBridleItem(int itemId) {
        return BRIDLE_ITEMS.get().get(itemId);
    }

    @Handler(op = InHeader.USER_BRIDLE_ITEM_USE_REQUEST)
    public static void handleUserBridleItemUseRequest(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 14) {
            log.warn("Bridle item use request too short ({} bytes) from {}", inPacket.getUnreadAmount(), chr.getName());
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); // update time
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        int mobObjectId = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        Bridle bridle = getBridleItem(itemId);
        if (item == null || item.getItemId() != itemId || bridle == null) {
            log.warn("Bridle item use request with item {} (slot {}) that {} doesn't have, or that can't catch",
                    itemId, slot, chr.getName());
            chr.dispose();
            return;
        }
        Field field = chr.getField();
        var life = field.getLifeByObjectID(mobObjectId);
        if (!(life instanceof net.swordie.ms.life.mob.Mob) || life.getTemplateId() != bridle.mob()) {
            chr.chatMessage("There's no monster here that can be caught with this.");
            chr.dispose();
            return;
        }
        var mob = (net.swordie.ms.life.mob.Mob) life;
        if (mob.getHp() <= 0) {
            chr.dispose();
            return;
        }
        int dx = mob.getPosition().getX() - chr.getPosition().getX();
        int dy = mob.getPosition().getY() - chr.getPosition().getY();
        int reachX = Math.max(Math.abs(bridle.left()), Math.abs(bridle.right())) + BRIDLE_RANGE_SLACK;
        if (Math.abs(dx) > reachX || dy < bridle.top() - BRIDLE_RANGE_SLACK || dy > bridle.bottom() + BRIDLE_RANGE_SLACK) {
            chr.chatMessage("The monster is too far away.");
            chr.dispose();
            return;
        }
        if (bridle.maxHp() > 0 && mob.getMaxHp() > 0 && mob.getHp() * 100 > (long) bridle.maxHp() * mob.getMaxHp()) {
            chr.chatMessage("The monster is still too strong. Weaken it first.");
            chr.dispose();
            return;
        }
        if (!chr.canHold(bridle.create(), 1)) {
            chr.chatMessage("Make some room in your inventory first.");
            chr.dispose();
            return;
        }
        if (bridle.success() > 0 && !Util.succeedProp(bridle.success())) {
            chr.chatMessage("It got away! Try again.");
            chr.dispose();
            return;
        }
        chr.consumeItem(item, 1);
        mob.die(false); // caught: no drops
        chr.addItemToInventory(bridle.create(), 1);
        chr.dispose();
    }

    // v271 sender 0x1453a6fd0 writes 0x129 (297): Encode4(5510000, Respawn Token) + x + y
    @Handler(op = InHeader.USER_UPGRADE_TOMB_EFFECT)
    public static void handleUserUpgradeTombEffect(Char chr, InPacket inPacket) {
        int itemId = inPacket.decodeInt();
        int posX = inPacket.decodeInt();
        int posY = inPacket.decodeInt();
        chr.getField().broadcastPacketByChr(chr, UserRemote.showUpgradeTombEffect(chr, itemId, new Position(posX, posY)));
    }
}
