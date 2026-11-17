package net.swordie.ms.handlers.item;

import net.swordie.ms.Server;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.avatar.EarStyle;
import net.swordie.ms.client.character.familiar.FamiliarCodexManager;
import net.swordie.ms.client.character.familiar.FamiliarCodexUpdateMask;
import net.swordie.ms.client.character.familiar.FamiliarModifiedMask;
import net.swordie.ms.client.character.familiar.FamiliarModifiedType;
import net.swordie.ms.client.character.items.*;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.soulcollection.BossSoulType;
import net.swordie.ms.client.soulcollection.SoulCollectionModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.life.pet.PetSkill;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.FieldData;
import net.swordie.ms.loaders.ForbiddenWordsData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.loaders.containerclasses.ItemRewardInfo;
import net.swordie.ms.util.FileTime;
import java.time.LocalDateTime;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.World;
import net.swordie.ms.world.event.SunnySunday;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.swordie.ms.enums.AccountType.Player;
import static net.swordie.ms.enums.ChatType.SystemNotice;
import static net.swordie.ms.enums.EquipBaseStat.tuc;
import static net.swordie.ms.enums.InvType.EQUIP;
import static net.swordie.ms.enums.InvType.EQUIPPED;

public class ItemHandlerModule {
    private static final Logger log = LogManager.getLogger();

    protected static void handleRewardItem(Char chr, ItemInfo itemInfo, Item item) {
        // Reward items
        ItemRewardInfo iri = itemInfo == null ? null : itemInfo.getRandomRewardInfo();
        if (iri == null) {
            chr.chatMessage("This item has no rewards.");
            chr.dispose();
            return;
        }
        if (iri.getItemID() == 0) {
            // a meso prize
            long meso = Math.max(0, iri.getCount());
            if (!chr.canAddMoney(meso)) {
                chr.chatMessage("You can't hold any more mesos.");
                chr.dispose();
                return;
            }
            chr.consumeItem(item);
            chr.addMoney(meso);
            chr.dispose();
            return;
        }
        Item reward = ItemData.getItemDeepCopy(iri.getItemID());
        if (reward == null) {
            log.error(String.format("Reward item %d of item %d doesn't exist.", iri.getItemID(), item.getItemId()));
            chr.chatMessage("This item's reward could not be found. Please report it to a GM.");
            chr.dispose();
            return;
        }
        reward.setQuantity(Math.max(1, iri.getCount()));
        if (iri.getPeriod() > 0) {
            // period is in minutes
            reward.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusMinutes(iri.getPeriod())));
        }
        if (!chr.canHold(reward.deepCopy())) {
            // check space before consuming the box
            chr.chatMessage("Make sure you have enough space in your inventory.");
            chr.dispose();
            return;
        }
        chr.consumeItem(item);
        chr.addItemToInventory(reward);
    }

    /**
     * Uses an item whose effect is in its Item.wz data: cosmetic coupons (change the hair, face or skin) and EXP items.
     *
     * @return whether the item was one of those (handled, including telling the player why it can't be used)
     */
    protected static boolean handleUseDataItem(Char chr, ItemInfo ii, Item item) {
        if (ii == null || item == null) {
            return false;
        }
        int look = ii.getCosmeticLook();
        if (look > 0) {
            // skin coupons have the skin number (2..27), hair and face coupons the hair/face id
            boolean valid = look < 100 ? ItemConstants.isSkin(look)
                    : (ItemConstants.isHair(look) || ItemConstants.isFace(look)) && ItemData.getEquipInfoById(look) != null;
            if (!valid) {
                log.warn(String.format("Coupon %d has an unknown look %d.", item.getItemId(), look));
                chr.chatMessage("This coupon can't be used.");
                chr.dispose();
                return true;
            }
            // styling is free: the coupon is kept (and so gives no charm EXP)
            chr.getScriptManager().changeCharacterLook(look);
            chr.chatMessage("Your new look is on. Coupons aren't used up here: styling is free (@style).");
            chr.dispose();
            return true;
        }
        if (ii.getUseExp() > 0) {
            if (ii.getUseMaxLevel() > 0 && chr.getLevel() > ii.getUseMaxLevel()) {
                chr.chatMessage(String.format("This item can only be used up to level %d.", ii.getUseMaxLevel()));
                chr.dispose();
                return true;
            }
            chr.consumeItem(item);
            chr.addExp(ii.getUseExp());
            chr.giveUseCharmExp(item.getItemId(), 1);
            chr.dispose();
            return true;
        }
        return false;
    }

    private static final java.util.regex.Pattern MAPLE_POINT_COUPON =
            java.util.regex.Pattern.compile("^([\\d,]+)(-Million)?-Maple-Point (Coupon|Chip)$");

    /** The Maple Points a "3,000-Maple-Point Coupon" / "1-Million-Maple-Point Chip" gives (from its name), else 0. */
    protected static long maplePointCouponAmount(int itemId) {
        String name = StringData.getItemStringById(itemId);
        if (name == null) {
            return 0;
        }
        var m = MAPLE_POINT_COUPON.matcher(name.trim());
        if (!m.matches()) {
            return 0;
        }
        long amount = Long.parseLong(m.group(1).replace(",", ""));
        return m.group(2) != null ? amount * 1_000_000 : amount;
    }

    /** Energy drinks and revitalizers: profession fatigue is gone in v265, so they do nothing (as in GMS) */
    private static final Set<Integer> FATIGUE_ITEMS = Set.of(2430212, 2430213, 2430214, 2430220, 2430227, 2430231,
            2431940); // hardcode-ok

    /**
     * Script items the server has no script for, whose effect is in Item.wz info: mount coupons (ridingSkillID),
     * damage skins (damageSkinID), Honor EXP items, inventory slot coupons, Maple Point coupons.
     *
     * @return whether the item was one of those (used, or the player was told why not)
     */
    protected static boolean handleScriptlessItem(Char chr, ItemInfo ii, Item item) {
        if (ii == null || item == null) {
            return false;
        }
        int itemId = item.getItemId();
        if (ItemConstants.isRetiredGoldenHammer(itemId)) {
            // GMS v271: "This item is no longer in use ... Use to obtain Spell Trace x..." (one hammer per use)
            int traces = ItemConstants.getRetiredGoldenHammerTraces(itemId);
            if (!chr.canHold(ItemConstants.SPELL_TRACE_ID, traces)) {
                chr.chatMessage("Make room in your Etc tab for the Spell Traces first.");
            } else {
                chr.consumeItem(item, 1);
                chr.addItemToInventory(ItemConstants.SPELL_TRACE_ID, traces);
                chr.chatMessage(String.format("You received %,d Spell Traces.", traces));
            }
            chr.dispose();
            return true;
        }
        if (FATIGUE_ITEMS.contains(itemId)) {
            chr.chatMessage("Professions no longer cause fatigue, so this item has no effect.");
            chr.dispose();
            return true;
        }
        if (net.swordie.ms.client.character.skills.vmatrix.NodestoneModule.isFixedNodestone(itemId)) {
            String why = net.swordie.ms.client.character.skills.vmatrix.NodestoneModule.openExperience(chr, item);
            if (why != null) {
                chr.chatMessage(why);
            }
            chr.dispose();
            return true;
        }
        if (GrowthPotions.isGrowthPotion(itemId)) {
            String why = GrowthPotions.use(chr, item);
            if (why != null) {
                chr.chatMessage(why);
            }
            chr.dispose();
            return true;
        }
        if (ii.getRidingSkillId() > 0) {
            int skillId = ii.getRidingSkillId();
            String mount = StringData.getSkillStringById(skillId) != null
                    ? StringData.getSkillStringById(skillId).getName() : StringData.getItemStringById(itemId);
            if (SkillData.getSkillInfoById(skillId) == null) {
                log.warn(String.format("Mount coupon %d names an unknown skill %d.", itemId, skillId));
                chr.chatMessage("This mount can't be added right now.");
            } else if (chr.hasSkill(skillId)) {
                chr.chatMessage(String.format("You already have the '%s' mount.", mount));
            } else {
                // skills don't expire on this server, so timed mount coupons give the mount for good
                chr.consumeItem(item, 1);
                chr.addSkill(skillId, 1, 1);
                chr.chatMessage(String.format("Successfully added the '%s' mount.", mount));
            }
            chr.dispose();
            return true;
        }
        if (ii.getIncHonorExp() > 0) {
            chr.consumeItem(item, 1);
            chr.addHonorExp(ii.getIncHonorExp());
            chr.chatMessage(String.format("You gained %,d Honor EXP.", ii.getIncHonorExp()));
            chr.dispose();
            return true;
        }
        if (itemId / 1000 == 5689) {
            // Water of Life: pets here never run out of life (kept)
            chr.chatMessage("Your pets never run out of life here, so the Water of Life isn't needed.");
            chr.dispose();
            return true;
        }
        if (itemId == ItemConstants.CONTINENTAL_RETURN_SCROLL) {
            // Continental Return Scroll: back to the nearest town, from anywhere
            Field field = chr.getField();
            int to = field == null ? 0 : field.getInfo().getReturnMap();
            if (to <= 0 || to == GameConstants.NO_MAP_ID || !field.isChannelField()) {
                chr.chatMessage("You can't use a return scroll here.");
            } else {
                Field town = chr.getOrCreateFieldByCurrentInstanceType(to);
                if (town != null) {
                    chr.consumeItem(item, 1);
                    chr.warp(town, town.getInfo().getDefaultPortal());
                }
            }
            chr.dispose();
            return true;
        }
        String itemName = StringData.getItemStringById(itemId);
        if (itemName != null && itemName.matches(".*[0-9]+-slot Coupon.*") && !itemName.contains("Character")) {
            // Selective 8-slot coupons and the like: every inventory tab is at its maximum already
            chr.chatMessage("Your inventory tabs already have the most slots they can have.");
            chr.dispose();
            return true;
        }
        if (ii.getSlotExpand() > 0) {
            // every character's inventory tabs are at the maximum already (Char.raiseToMaxSlots)
            chr.chatMessage("That inventory tab already has the most slots it can have.");
            chr.dispose();
            return true;
        }
        return useMaplePointCoupon(chr, ii, item);
    }

    private static boolean useMaplePointCoupon(Char chr, ItemInfo ii, Item item) {
        long points = ii.getMaplePoint() > 0 ? ii.getMaplePoint() : maplePointCouponAmount(item.getItemId());
        if (points <= 0 || points > Integer.MAX_VALUE) {
            return false;
        }
        chr.consumeItem(item, 1);
        chr.addNx((int) points);
        chr.chatMessage(String.format("You received %,d Maple Points.", points));
        chr.dispose();
        return true;
    }

    /**
     * Cash items used from the Cash tab whose effect is in their item data or needs no target: meso sacks, Maple
     * Point coupons, megaphones, Scissors of Karma, the travelling merchant and portable storage.
     *
     * @return whether the item was one of those
     */
    protected static boolean handleDataCashItem(Char chr, InPacket inPacket, ItemInfo ii, Item item) {
        if (ii == null) {
            return false;
        }
        int itemId = item.getItemId();
        if (ii.getMesoBag() > 0) {
            if (!chr.canAddMoney(ii.getMesoBag())) {
                chr.chatMessage("You can't hold any more mesos.");
            } else {
                chr.consumeItem(item, 1);
                chr.addMoney(ii.getMesoBag());
                chr.chatMessage(String.format("You received %,d mesos.", ii.getMesoBag()));
            }
            chr.dispose();
            return true;
        }
        if (useMaplePointCoupon(chr, ii, item)) {
            return true;
        }
        switch (itemId) {
            case ItemConstants.CHEAP_MEGAPHONE:
            case ItemConstants.MEGAPHONE:
            case ItemConstants.HEART_MEGAPHONE:
            case ItemConstants.SKULL_MEGAPHONE:
            case ItemConstants.CAKE_MEGAPHONE:
            case ItemConstants.PIE_MEGAPHONE:
            case ItemConstants.ECHO_MEGAPHONE:
                handleChannelMegaphone(inPacket, chr, item);
                chr.dispose();
                return true;
            case ItemConstants.SCISSORS_OF_KARMA:
            case ItemConstants.OSWELLS_SCISSORS_OF_KARMA:
                inPacket.decodeInt();
                handleScissorsOfKarma(chr, inPacket.decodeInt(), item);
                chr.dispose();
                return true;
            case ItemConstants.PET_NAME_TAG: // long pet SN, S name
                renamePet(chr, inPacket.decodeLong(), inPacket.decodeString(), item);
                chr.dispose();
                return true;
            case ItemConstants.ANDROID_NAMING_COUPON: // long android SN, S name
                inPacket.decodeLong();
                renameAndroid(chr, inPacket.decodeString(), item);
                chr.dispose();
                return true;
            case WonderPets.WONDERBERRY: // confirm dialog, then nothing more
            case WonderPets.WONDERBERRY_2: {
                String why = WonderPets.openWonderberry(chr, item);
                if (why != null) {
                    chr.chatMessage(why);
                }
                chr.dispose();
                return true;
            }
            case WonderPets.LUNA_CRYSTAL: // 4, 2, B8 base pet; 4, 2, B8 material pet (client 0x14426c710)
            case WonderPets.SPECIAL_LUNA_CRYSTAL: {
                inPacket.decodeInt();
                inPacket.decodeShort();
                long baseSn = inPacket.decodeLong();
                inPacket.decodeInt();
                inPacket.decodeShort();
                long materialSn = inPacket.decodeLong();
                String why = WonderPets.useLunaCrystal(chr, item, baseSn, materialSn);
                if (why != null) {
                    chr.chatMessage(why);
                }
                chr.dispose();
                return true;
            }
            case ItemConstants.PASSED_GAS, ItemConstants.FLORAL_SCENT, ItemConstants.STINKBOMB,
                    ItemConstants.FLORAL_SCENT_2: // a 60 s area around the user
                useMapAreaItem(chr, item);
                chr.dispose();
                return true;
            case ItemConstants.ITEM_GUARD, ItemConstants.ITEM_GUARD_7, ItemConstants.ITEM_GUARD_30,
                    ItemConstants.ITEM_GUARD_90, ItemConstants.ITEM_GUARD_365: // int inv type, int equip slot, int tick
                useItemGuard(chr, inPacket.decodeInt(), inPacket.decodeInt(), item);
                chr.dispose();
                return true;
            case ItemConstants.BEAUTY_FACE_SLOT_COUPON, ItemConstants.BEAUTY_HAIR_SLOT_COUPON,
                    ItemConstants.BEAUTY_SKIN_SLOT_COUPON: // kept: nothing to unlock
                chr.chatMessage("Your Beauty Album rooms grow by themselves when they fill up, so this coupon isn't needed.");
                chr.dispose();
                return true;
            case ItemConstants.SHARING_TAG: // kept: nothing to unlock
                chr.chatMessage("Storage already shares every item with your other characters here, so the Sharing Tag isn't needed.");
                chr.dispose();
                return true;
        }
        if (itemId >= ItemConstants.MAGICAL_HOURGLASS_FIRST && itemId <= ItemConstants.MAGICAL_HOURGLASS_LAST) {
            // Magical Hourglass: pets here never run out of life (kept)
            chr.chatMessage("Your pets never run out of life here, so the Magical Hourglass isn't needed.");
            chr.dispose();
            return true;
        }
        if (itemId / 10000 == 512 && ii.getPath() != null && ii.getPath().startsWith("Map/MapHelper.img/weather")) {
            useWeatherItem(chr, inPacket, item);
            chr.dispose();
            return true;
        }
        if (itemId / 10000 == 524 || itemId == ItemConstants.PET_SNACK) {
            // cash pet food (for the pets its data lists, or any) / Pet Snack: fills the summoned pets, +100 closeness
            var forPets = ii.getPetFoodPets();
            var pets = chr.getPets().stream()
                    .filter(p -> p.getItem() != null && (forPets.isEmpty() || forPets.contains(p.getItem().getItemId())))
                    .toList();
            if (pets.isEmpty()) {
                chr.chatMessage(forPets.isEmpty() ? "Summon a pet first." : "None of your summoned pets eat this food.");
                chr.dispose();
                return true;
            }
            chr.consumeItem(item, 1);
            for (var pet : pets) {
                var pi = pet.getItem();
                pi.setRepleteness((byte) 100);
                net.swordie.ms.handlers.life.PetHandler.addCloseness(chr, pet, 100);
                pi.updateToChar(chr);
                chr.getField().broadcastPacket(PetPacket.foodResult(chr.getId(), pet.getIdx(), true, 0));
            }
            chr.dispose();
            return true;
        }
        if (itemId / 10000 == 545 && ii.getNpcID() > 0) {
            // Miu Miu the Traveling Merchant (a shop) or a Portable Storage (Mr. Wang's storage); not used up
            var sm = chr.getScriptManager();
            if (ii.getNpcID() == GameConstants.STORAGE_NPC) {
                sm.openTrunk(ii.getNpcID());
            } else if (net.swordie.ms.loaders.NpcData.getShopById(ii.getNpcID()) != null) {
                sm.openShop(ii.getNpcID());
            } else {
                chr.chatMessage("This merchant has nothing for sale right now.");
            }
            chr.dispose();
            return true;
        }
        return false;
    }

    private static final int WEATHER_SECONDS = 30;
    private static final Map<Field, Long> weatherUntil = Collections.synchronizedMap(new WeakHashMap<>());

    /** Map weather items (Soap Bubbles, Sprinkled Presents...): the effect and the message for the whole map. */
    private static void useWeatherItem(Char chr, InPacket inPacket, Item item) {
        Field field = chr.getField();
        long now = System.currentTimeMillis();
        Long until = weatherUntil.get(field);
        if (until != null && until > now) {
            chr.chatMessage("Another weather effect is already running in this map.");
            return;
        }
        String text = inPacket.getUnreadAmount() >= 2 ? inPacket.decodeString() : "";
        if (ForbiddenWordsData.isForbiddenChat(text)) {
            chr.chatMessage("An error occurred. Try again later.");
            return;
        }
        weatherUntil.put(field, now + WEATHER_SECONDS * 1000L);
        field.broadcastPacket(FieldPacket.blowWeather(item.getItemId(),
                String.format("%s : %s", chr.getName(), text), WEATHER_SECONDS));
        chr.consumeItem(item, 1);
    }

    /** Pet and android names: 1-12 letters, digits and spaces, no forbidden words. */
    private static String checkName(String name) {
        if (name == null || name.isBlank() || name.getBytes().length > 12 || !name.matches("[A-Za-z0-9 ]+")) {
            return "Names can have 1 to 12 letters, digits and spaces.";
        }
        if (ForbiddenWordsData.isForbiddenChat(name)) {
            return "You can't use that name.";
        }
        return null;
    }

    private static void renamePet(Char chr, long sn, String name, Item tag) {
        Item pi = chr.getCashInventory().getItemBySN(sn);
        if (!(pi instanceof PetItem petItem)) {
            chr.chatMessage("Could not find that pet.");
            return;
        }
        String why = checkName(name.trim());
        if (why != null) {
            chr.chatMessage(why);
            return;
        }
        petItem.setName(name.trim());
        petItem.updateToChar(chr);
        for (var pet : chr.getPets()) {
            if (pet.getItem() == petItem) {
                pet.setName(name.trim());
                chr.getField().broadcastPacket(PetPacket.nameChanged(chr.getId(), pet.getIdx(), name.trim()));
            }
        }
        chr.consumeItem(tag, 1);
    }

    private static void renameAndroid(Char chr, String name, Item coupon) {
        var android = chr.getAndroid();
        if (android == null) {
            chr.chatMessage("Equip your android first.");
            return;
        }
        String why = checkName(name.trim());
        if (why != null) {
            chr.chatMessage(why);
            return;
        }
        android.rename(name.trim());
        chr.consumeItem(coupon, 1);
    }

    /**
     * Passed Gas / Floral Scent (Item/Cash/0528.img): AFFECTED_AREA type 2 with the negative item id (client reader
     * 0x141605970 draws Item/Cash/0528.img/<id>/tile and takes the time from info/time). Only these ids: the client
     * looks a type-2 area's item up without checking it, so any other id would crash everyone in the map.
     */
    private static final Set<Integer> MAP_AREA_ITEMS = Set.of(ItemConstants.PASSED_GAS, ItemConstants.FLORAL_SCENT,
            ItemConstants.STINKBOMB, ItemConstants.FLORAL_SCENT_2);
    private static final int MAP_AREA_MS = 60_000;

    private static void useMapAreaItem(Char chr, Item item) {
        if (!MAP_AREA_ITEMS.contains(item.getItemId())) {
            return;
        }
        Field field = chr.getField();
        if (field == null) {
            return;
        }
        var aa = new net.swordie.ms.life.AffectedArea(-1);
        aa.setObjectId(field.getNewObjectID());
        aa.setAaType((byte) 2);
        aa.setOwner(chr);
        aa.setSkillID(-item.getItemId());
        aa.setPosition(chr.getPosition().deepCopy());
        aa.setRect(chr.getPosition().getRectAround(new net.swordie.ms.util.Rect(new Position(-110, -82),
                new Position(110, 83))));
        aa.setDuration(MAP_AREA_MS);
        chr.consumeItem(item, 1);
        field.broadcastPacket(FieldPacket.affectedAreaCreated(aa));
        // some carry a buff (info/stateChangeItem): everyone in the area when it's made gets it
        ItemInfo ii = ItemData.getItemInfoByID(item.getItemId());
        int buff = ii == null ? 0 : ii.getStateChangeItem();
        // (once each, also when walking in while the cloud lasts: checked every second)
        if (buff != 0) {
            Item buffItem = ItemData.getItemDeepCopy(buff);
            Set<Integer> given = java.util.concurrent.ConcurrentHashMap.newKeySet();
            Runnable tick = () -> {
                if (buffItem == null) {
                    return;
                }
                for (Char other : field.getCharsReadOnly()) {
                    if (!given.contains(other.getId()) && aa.getRect().hasPositionInside(other.getPosition())) {
                        given.add(other.getId());
                        other.useStatChangeItem(buffItem, false);
                    }
                }
            };
            tick.run();
            net.swordie.ms.handlers.executors.EventManager.addFixedRateEvent(tick, 1000, 1000,
                    MAP_AREA_MS / 1000 - 1);
        }
        net.swordie.ms.handlers.executors.EventManager.addEvent(
                () -> field.broadcastPacket(FieldPacket.affectedAreaRemoved(aa, false)), MAP_AREA_MS);
    }

    /**
     * Item Guard: seals an equip (EquipAttribute.Locked) so it can't be dropped, traded or destroyed. Without
     * info/protectTime it's permanent; otherwise it seals for that many days, the end kept in the equip's expiry (at
     * most info/maxDays out).
     */
    private static void useItemGuard(Char chr, int invType, int slot, Item guard) {
        var inv = chr.getInventoryByType(invType == -1 || slot < 0 ? EQUIPPED : InvType.getInvTypeByVal(invType));
        Item target = inv == null ? null : inv.getItemBySlot(slot);
        if (!(target instanceof Equip equip)) {
            chr.chatMessage("Choose an equip to seal.");
            return;
        }
        if (equip.isCash()) {
            chr.chatMessage("Cash items can't be sealed.");
            return;
        }
        ItemInfo guardInfo = ItemData.getItemInfoByID(guard.getItemId());
        int days = guardInfo == null ? 0 : guardInfo.getProtectTime();
        int maxDays = guardInfo == null ? 0 : guardInfo.getMaxDays();
        boolean timed = equip.getDateExpire() != null && !equip.getDateExpire().isMaxTime()
                && equip.getDateExpire().toMillis() > 0;
        if (equip.hasAttribute(EquipAttribute.Locked) && !timed) {
            chr.chatMessage("This item is already sealed.");
            return;
        }
        if (days == 0) {
            if (timed && !equip.hasAttribute(EquipAttribute.Locked)) {
                chr.chatMessage("Time-limited items can't be sealed for good.");
                return;
            }
            equip.setDateExpire(FileTime.fromType(FileTime.Type.MAX_TIME));
        } else {
            LocalDateTime from = LocalDateTime.now();
            if (equip.hasAttribute(EquipAttribute.Locked) && timed) {
                from = equip.getDateExpire().toLocalDateTime();
            } else if (timed) {
                chr.chatMessage("Time-limited items can't be sealed.");
                return;
            }
            LocalDateTime end = from.plusDays(days);
            if (maxDays > 0 && end.isAfter(LocalDateTime.now().plusDays(maxDays))) {
                chr.chatMessage(String.format("An item can't be sealed for more than %d days.", maxDays));
                return;
            }
            equip.setDateExpire(FileTime.fromDate(end));
        }
        equip.addAttribute(EquipAttribute.Locked);
        equip.updateToChar(chr);
        chr.consumeItem(guard, 1);
        chr.chatMessage("The item has been sealed.");
    }

    /** Megaphones that reach the player's channel (the Super Megaphone reaches the world). */
    private static void handleChannelMegaphone(InPacket inPacket, Char chr, Item item) {
        if (item.getItemId() == ItemConstants.MEGAPHONE && chr.getLevel() < ItemConstants.MEGAPHONE_MIN_LEVEL) {
            chr.chatMessage(String.format("You must be at least level %d to use a Megaphone.", ItemConstants.MEGAPHONE_MIN_LEVEL));
            return;
        }
        if (chr.isOnMegaphoneCooldown()) {
            long remaining = chr.getMegaphoneRemainingCooldown() / 1000;
            chr.chatMessage(String.format("You are on cooldown! Remaining time: %d minutes and %d seconds",
                    remaining / 60, remaining % 60));
            return;
        }
        String text = inPacket.decodeString();
        if (text.isEmpty() || ForbiddenWordsData.isForbiddenChat(text)) {
            chr.chatMessage("An error occurred. Try again later.");
            return;
        }
        boolean whisperIcon = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
        var channel = chr.getClient().getChannelInstance();
        BroadcastMsg mega = BroadcastMsg.megaphone(String.format("%s : %s", chr.getName(), text),
                (byte) channel.getChannelId(), whisperIcon, chr);
        channel.broadcastPacketByChr(chr, WvsContext.broadcastMsg(mega));
        chr.consumeItem(item, 1);
        chr.setMegaphoneCooldown();
    }

    /**
     * Scissors of Karma: makes an equip that can be traded once with scissors (Item.wz tradeAvailable) tradable
     * once more.
     */
    protected static void handleScissorsOfKarma(Char chr, int equipPos, Item scissors) {
        var invItem = chr.getEquipInventory().getItemBySlot(equipPos);
        if (!(invItem instanceof Equip equip)) {
            chr.chatMessage("Could not find that item.");
            return;
        }
        var info = ItemData.getEquipInfoById(equip.getItemId());
        if (equip.isCash() || info == null || info.getTradeAvailable() == 0) {
            chr.chatMessage("The Scissors of Karma can't be used on that item.");
            return;
        }
        if (equip.isTradable()) {
            chr.chatMessage("This item is already tradable.");
            return;
        }
        equip.removeAttribute(EquipAttribute.Untradable);
        equip.addAttribute(EquipAttribute.UntradableAfterTransaction);
        equip.updateToChar(chr);
        chr.consumeItem(scissors, 1);
    }

    /**
     * @return whether the item has a reward list in the item data and no script of its own: such items are boxes
     * that are opened through {@link #handleRewardItem}, whichever way the client uses them
     */
    protected static boolean isRewardBox(ItemInfo ii) {
        return ii != null && !ii.getItemRewardInfos().isEmpty() && (ii.getScript() == null || ii.getScript().isEmpty());
    }

    protected static void handleFusionAnvil(InPacket inPacket, Char chr, Item item) {
        int appearancePos = inPacket.decodeInt();
        int functionPos = inPacket.decodeInt();
        Inventory inv = chr.getEquipInventory();
        Equip appearance = (Equip) inv.getItemBySlot(appearancePos);
        Equip function = (Equip) inv.getItemBySlot(functionPos);
        if (appearance != null && function != null && appearance.getItemId() / 10000 == function.getItemId() / 10000) {
            function.getOptions().set(6, appearance.getItemId() % 10000);
            function.updateToChar(chr);
        }
        chr.consumeItem(item);
    }

    protected static void handleTripleMegaphone(InPacket inPacket, Char chr, String medalString, Item item) {
        if (chr.isOnMegaphoneCooldown()) {
            long remainingCooldown = chr.getMegaphoneRemainingCooldown() / 1000;
            var minutes = remainingCooldown / 60;
            var seconds = remainingCooldown % 60;
            chr.chatMessage(String.format("You are on cooldown! Remaining time: %d minutes and %d seconds", minutes, seconds));
            chr.dispose();
            return;
        }

        boolean whisperIcon;
        World world;
        BroadcastMsg smega;
        byte stringListSize = inPacket.decodeByte();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < stringListSize; i++) {
            stringList.add(String.format("%s%s : %s", medalString, chr.getName(), inPacket.decodeString()));
        }

        // Forbidden Words Check
        var isForbidden = false;
        for (var chat : stringList) {
            isForbidden = ForbiddenWordsData.isForbiddenChat(chat);
            if (isForbidden) {
                break;
            }
        }
        if (isForbidden) {
            chr.chatMessage("An error occurred. Try again later.");
            chr.dispose();
            return;
        }
        // Forbidden Words Check

        whisperIcon = inPacket.decodeByte() != 0;

        world = chr.getClient().getWorld();
        smega = BroadcastMsg.tripleMegaphone(stringList,
                (byte) chr.getClient().getChannelInstance().getChannelId(), whisperIcon, chr);
        world.broadcastPacketByChr(chr, WvsContext.broadcastMsg(smega));
        chr.consumeItem(item);
        chr.setTripleMegaphoneCooldown();
    }

    protected static void handleItemMegaphone(InPacket inPacket, Char chr, String medalString, Item item) {
        if (chr.isOnMegaphoneCooldown()) {
            long remainingCooldown = chr.getMegaphoneRemainingCooldown() / 1000;
            var minutes = remainingCooldown / 60;
            var seconds = remainingCooldown % 60;
            chr.chatMessage(String.format("You are on cooldown! Remaining time: %d minutes and %d seconds", minutes, seconds));
            chr.dispose();
            return;
        }

        String text;
        boolean whisperIcon;
        InvType invType;
        World world;
        BroadcastMsg smega;
        text = inPacket.decodeString();

        // Forbidden Words Check
        if (ForbiddenWordsData.isForbiddenChat(text)) {
            chr.chatMessage("An error occurred. Try again later.");
            chr.dispose();
            return;
        }
        // Forbidden Words Check

        whisperIcon = inPacket.decodeByte() != 0;
        var subType = inPacket.decodeInt();
        if (subType == 0 || subType == 1) {
            var equipSelected = subType == 1;
            invType = EQUIP;
            int itemPosition = 0;
            if (equipSelected) {
                invType = InvType.getInvTypeByVal(inPacket.decodeInt());
                itemPosition = inPacket.decodeInt();
                if (invType.isEquipType() && itemPosition < 0) {
                    invType = EQUIPPED;
                }
            }
            Item broadcastedItem = chr.getInventoryByType(invType).getItemBySlot(itemPosition);

            world = chr.getClient().getWorld();
            smega = BroadcastMsg.itemMegaphone(String.format("%s%s : %s", medalString, chr.getName(), text),
                    (byte) chr.getClient().getChannelInstance().getChannelId(), whisperIcon, equipSelected,
                    broadcastedItem, chr);
            world.broadcastPacketByChr(chr, WvsContext.broadcastMsg(smega));
            chr.consumeItem(item);
            chr.setMegaphoneCooldown();
        } else {
            chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("This is not implemented yet.")));
        }
    }

    protected static void handleSuperMegaphone(InPacket inPacket, Char chr, String medalString, Item item) {
        if (chr.isOnMegaphoneCooldown()) {
            long remainingCooldown = chr.getMegaphoneRemainingCooldown() / 1000;
            var minutes = remainingCooldown / 60;
            var seconds = remainingCooldown % 60;
            chr.chatMessage(String.format("You are on cooldown! Remaining time: %d minutes and %d seconds", minutes, seconds));
            chr.dispose();
            return;
        }

        String text = inPacket.decodeString();

        // Forbidden Words Check
        if (ForbiddenWordsData.isForbiddenChat(text)) {
            chr.chatMessage("An error occurred. Try again later.");
            chr.dispose();
            return;
        }
        // Forbidden Words Check

        boolean whisperIcon = inPacket.decodeByte() != 0;
        World world = chr.getClient().getWorld();
        BroadcastMsg smega = BroadcastMsg.megaphone(
                String.format("%s%s : %s", medalString, chr.getName(), text),
                (byte) chr.getClient().getChannelInstance().getChannelId(), whisperIcon, chr);
        world.broadcastPacketByChr(chr, WvsContext.broadcastMsg(smega));
        chr.consumeItem(item);
        chr.setMegaphoneCooldown();
    }

    protected static boolean handleNebuliteDiffuser(InPacket inPacket, Char chr, Item item) {
        short ePos;
        Equip equip;
        ePos = inPacket.decodeShort();
        InvType invType = ePos < 0 ? EQUIPPED : EQUIP;
        equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (equip == null || equip.getSocket(0) == 0 || equip.getSocket(0) == ItemConstants.EMPTY_SOCKET_ID) {
            chr.chatMessage("That item currently does not have an active socket.");
            chr.dispose();
            return true;
        }
        equip.setSocket(0, ItemConstants.EMPTY_SOCKET_ID);
        equip.updateToChar(chr);

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(ePos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copySocketsFrom(equip);
            otherEquip.updateToChar(chr);

            chr.write(ZeroPool.egoEquipComplete(true, true));
        }

        chr.consumeItem(item);
        return false;
    }

    protected static boolean handleBonusCube(Client c, InPacket inPacket, Char chr, short pos, int itemId, Item item) {
        short ePos = (short) inPacket.decodeInt();
        return handleBonusCube(c, chr, pos, itemId, item, ePos, readCubeCount(inPacket));
    }

    /**
     * The cash cube tail after the equip slot (client 0x1433df8a0): int, [byte count (1 / 3) for the cube types that
     * allow Reset x3], byte, byte, string. Whether the count is there is told by the string's length.
     */
    private static int readCubeCount(InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 4) {
            return 1;
        }
        inPacket.decodeInt();
        int left = inPacket.getUnreadAmount();
        byte[] rest = inPacket.decodeArr(left);
        if (left >= 5 && 5 + strLen(rest, 3) == left) {
            return rest[0] == 3 ? 3 : 1;
        }
        return 1;
    }

    /** A little-endian short at off (a client string's length), or -1 when it isn't there. */
    private static int strLen(byte[] b, int off) {
        return off + 1 < b.length ? (b[off] & 0xFF) | ((b[off + 1] & 0xFF) << 8) : -1;
    }

    protected static boolean handleBonusCube(Client c, Char chr, short pos, int itemId, Item item, short ePos,
                                             int count) {
        InvType invType;
        Equip equip;
        if (c.getWorld().isReboot()) {
            chr.getOffenseManager().addOffense(String.format("Character %d attempted to use a bonus potential cube in reboot world.", chr.getId()));
            chr.dispose();
            return true;
        }
        invType = ePos < 0 ? EQUIPPED : EQUIP;
        equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (equip == null) {
            chr.chatMessage(SystemNotice, "Could not find equip.");
            return true;
        } else if (equip.getBonusGrade() < ItemGrade.Rare.getVal() || isFixedPotential(equip)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to use cube (id %d) an equip without a potential (id %d)", chr.getId(), itemId, equip.getItemId()));
            chr.dispose();
            return true;
        }

        if (!ItemConstants.canCubeGrade(itemId, ItemGrade.getHiddenGradeByVal(equip.getBonusGrade()).getVal())) {
            chr.chatMessage(SystemNotice, "This cube can't be used on an item of this rank.");
            chr.dispose();
            return true;
        }
        // v265 has no bonus cube result packet (v232 708 is gone): every bonus cube uses the before/after popup
        openPotentialChoice(c, chr, equip, itemId, item, ePos, pos, true, count);
        return false;
    }

    protected static boolean handleVioletCube(InPacket inPacket, Char chr, int itemId) {
        short ePos;
        InvType invType;
        Equip equip;
        Equip oldEquip;
        ePos = (short) inPacket.decodeInt();
        invType = ePos < 0 ? EQUIPPED : EQUIP;
        equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (equip == null) {
            chr.chatMessage(SystemNotice, "Could not find equip.");
            chr.dispose();
            return true;
        } else if (equip.getBaseGrade() < ItemGrade.Rare.getVal() || isFixedPotential(equip)) {
            String msg = String.format("Character %d tried to use cube (id %d) an equip without a potential (id %d)", chr.getId(), itemId, equip.getItemId());
            chr.getOffenseManager().addOffense(msg);
            chr.dispose();
            return true;
        }
        oldEquip = equip.deepCopy();

        OutPacket outPacket = new OutPacket(OutHeader.UNK_690);
        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);

        chr.write(outPacket);
        return false;
    }

    /** Equips with a fixed potential (Character.wz info/fixedPotential) can't be cubed. */
    private static boolean isFixedPotential(Equip equip) {
        var info = equip.getInfo();
        return info != null && info.isFixedPotential();
    }

    protected static boolean handleCube(Client c, InPacket inPacket, Char chr, short pos, int itemID, Item item) {
        short ePos = (short) inPacket.decodeInt();
        return handleCube(c, chr, pos, itemID, item, ePos, readCubeCount(inPacket));
    }

    protected static boolean handleCube(Client c, Char chr, short pos, int itemID, Item item, short ePos, int count) {
        InvType invType = ePos < 0 ? EQUIPPED : EQUIP;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(ePos);
        if (equip == null) {
            chr.chatMessage(SystemNotice, "Could not find equip.");
            chr.dispose();
            return true;
        } else if (equip.getBaseGrade() < ItemGrade.Rare.getVal() || isFixedPotential(equip)) {
            String msg = String.format("Character %d tried to use cube (id %d) an equip without a potential (id %d)", chr.getId(), itemID, equip.getItemId());
            chr.getOffenseManager().addOffense(msg);
            chr.dispose();
            return true;
        }

        if (!ItemConstants.canCubeGrade(itemID, ItemGrade.getHiddenGradeByVal(equip.getBaseGrade()).getVal())) {
            chr.chatMessage(SystemNotice, "This cube can't be used on an item of this rank.");
            chr.dispose();
            return true;
        }
        // v265 has no red/in-game cube result packet (v232 707/742 are gone; 981 is the Chaos Ring window):
        // every potential cube uses the Enhance window's before/after popup
        openPotentialChoice(c, chr, equip, itemID, item, ePos, pos, false, count);
        return false;
    }

    /**
     * Rolls copies of the equip and opens the before/after popup (342; window 1 = potential, 2 = bonus potential).
     * The equip keeps its lines until the pick (382); rolling again replaces the pending pick, which keeps BEFORE.
     * Reset x3 (count 3) takes three cubes and three fees and shows three AFTER cards.
     */
    private static void openPotentialChoice(Client c, Char chr, Equip equip, int cubeId, Item cube, short ePos,
                                            short cubePos, boolean bonus, int count) {
        count = count == 3 ? 3 : 1;
        if (cube.getQuantity() < count) {
            chr.chatMessage(SystemNotice, "Reset x3 needs 3 cubes.");
            chr.dispose();
            return;
        }
        long fee = cubeMesoFee(chr, equip);
        Item glass = fee > 0 ? feeWaiver(chr, equip) : null;
        int waived = glass == null ? 0 : Math.min(count, glass.getQuantity());
        long total = fee * (count - waived);
        if (chr.getMoney() < total) {
            chr.chatMessage(SystemNotice, String.format("You need %,d mesos to use a cube on this item.", total));
            chr.dispose();
            return;
        }
        if (total > 0) {
            chr.deductMoney(total, ProgressionLog.Sink.CUBES);
        }
        if (waived > 0) {
            chr.consumeItem(glass, waived);
        }
        int[] ceiling = ceilingFamily(cubeId);
        List<Equip> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(rollWithCeiling(equip, cubeId, bonus, ceiling));
        }
        MemorialCubeInfo mci = new MemorialCubeInfo(equip, cubeId, results, (short) cube.getBagIndex());
        chr.setMemorialCubeInfo(mci);
        chr.getField().broadcastPacket(UserPacket.showItemMemorialEffect(chr.getId(), true, cubeId, ePos, cubePos));
        c.write(WvsContext.memorialCubeWindow(mci, bonus ? 2 : 1));
        chr.consumeItem(cube, count);
        chr.write(net.swordie.ms.connection.packet.UserLocal.cubeResultNotice(cubeId, equip.getItemId()));
    }

    // Etc/CubeCeiling.img: cubes at Rare / Epic / Unique until a guaranteed rank-up, by cube family (it has no Heroic
    // cube rows: Glowing / Bright count as Red / Black, the bonus ones as additional)
    private static final int[] CEILING_RED = {25, 83, 500};
    private static final int[] CEILING_BLACK = {10, 42, 107};
    private static final int[] CEILING_ADDITIONAL = {31, 76, 214}; // additional and whiteAdditional
    private static final int[] CEILING_MESO_MAIN = {10, 42, 107}; // potentialReset
    private static final int[] CEILING_MESO_BONUS = {62, 152, 214}; // additionalPotentialReset

    /** @return the ceiling for this cube (-1 / -2 = meso reset), or null for cubes without one (in-game cubes) */
    private static int[] ceilingFamily(int cubeId) {
        return switch (cubeId) {
            case ItemConstants.RED_CUBE, ItemConstants.GLOWING_CUBE -> CEILING_RED;
            case ItemConstants.BLACK_CUBE, ItemConstants.BRIGHT_CUBE -> CEILING_BLACK;
            case ItemConstants.BONUS_POT_CUBE, ItemConstants.SPECIAL_BONUS_POT_CUBE,
                    ItemConstants.WHITE_BONUS_POT_CUBE, ItemConstants.BONUS_GLOWING_CUBE,
                    ItemConstants.BONUS_BRIGHT_CUBE -> CEILING_ADDITIONAL;
            case -1 -> CEILING_MESO_MAIN;
            case -2 -> CEILING_MESO_BONUS;
            default -> null;
        };
    }

    /**
     * Rolls a copy; the ceiling's last cube ranks up for sure. The item counts each roll that didn't rank up;
     * the 382 pick clears the count when a higher grade is kept.
     */
    private static Equip rollWithCeiling(Equip equip, int cubeId, boolean bonus, int[] ceiling) {
        if (cubeId == PotentialCubes.EQUALITY_CUBE) {
            return PotentialCubes.rollEquality(equip);
        }
        int grade = bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        int idx = grade - ItemGrade.Rare.getVal();
        boolean counted = ceiling != null && idx >= 0 && idx < ceiling.length;
        boolean force = counted && equip.getCubeCeiling(bonus) + 1 >= ceiling[idx];
        Equip result = equip.deepCopy();
        boolean tierUp = result.rollCube(cubeId, bonus, force);
        if (counted && !tierUp) {
            equip.setCubeCeiling(bonus, equip.getCubeCeiling(bonus) + 1);
        }
        return result;
    }

    // Use-tab appraisal glasses that are used up instead of the cube meso fee (info/autoUse, levelMin..levelMax)
    private static final int[] FEE_GLASSES = {2460007, 2460008, 2460010};

    /** @return a glass stack covering this item's level (client 0x1453de950 checks Use 2460000..2469999), or null */
    public static Item feeWaiver(Char chr, Equip equip) {
        int level = equip.getReqLevel();
        if (level < 130 || level > 200) {
            return null;
        }
        for (int id : FEE_GLASSES) {
            Item glass = chr.getConsumeInventory().getItemByItemID(id);
            if (glass != null) {
                return glass;
            }
        }
        return null;
    }

    // Etc/potentialCostTable.img by grade (rare..legendary); columns: item level /10 below 16, 16..19, 20..24, 25
    private static final long[][] MESO_RESET_MAIN = {
            {4_000_000, 4_250_000, 4_500_000, 5_000_000}, {16_000_000, 17_000_000, 18_000_000, 20_000_000},
            {34_000_000, 36_125_000, 38_250_000, 42_500_000}, {40_000_000, 42_500_000, 45_000_000, 50_000_000}};
    private static final long[][] MESO_RESET_BONUS = {
            {9_750_000, 10_375_000, 11_000_000, 12_250_000}, {27_300_000, 29_050_000, 30_800_000, 34_300_000},
            {66_300_000, 70_550_000, 74_800_000, 83_300_000}, {78_000_000, 83_000_000, 88_000_000, 98_000_000}};

    /**
     * Enhance window meso-only reset (697): type -1 main / -2 bonus potential, equip position, count (1 or 3).
     * Costs the table price (0x1404fa650); shows the same before/after popup as a cube.
     */
    protected static void handlePotentialMesoReset(Client c, Char chr, int type, short ePos, int count) {
        // a Heroic-world convenience on this Interactive server
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.HEROIC_MESO_POTENTIAL_RESET)
                || !net.swordie.ms.ServerSettings.getBoolean("enhance.heroic.mesoPotentialReset", true)) {
            chr.dispose();
            return;
        }
        boolean bonus = type == -2;
        Equip equip = chr.getInventoryByType(ePos < 0 ? EQUIPPED : EQUIP).getItemBySlot(ePos) instanceof Equip e ? e : null;
        int grade = equip == null ? 0 : bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        if (equip == null || (type != -1 && type != -2) || grade < ItemGrade.Rare.getVal()
                || grade > ItemGrade.Legendary.getVal() || isFixedPotential(equip)) {
            chr.dispose();
            return;
        }
        count = count == 3 ? 3 : 1;
        long cost = mesoResetPrice(equip, bonus) * count;
        if (chr.getMoney() < cost) {
            chr.chatMessage(SystemNotice, String.format("You need %,d mesos to reset this Potential.", cost));
            chr.dispose();
            return;
        }
        chr.deductMoney(cost, ProgressionLog.Sink.CUBES);
        int cubeId = bonus ? -2 : -1;
        int[] ceiling = ceilingFamily(cubeId);
        List<Equip> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(rollWithCeiling(equip, cubeId, bonus, ceiling));
        }
        MemorialCubeInfo mci = new MemorialCubeInfo(equip, cubeId, results, (short) 0);
        chr.setMemorialCubeInfo(mci);
        c.write(WvsContext.memorialCubeWindow(mci, bonus ? 2 : 1));
    }

    /** The table price of one meso reset for the item's current grade (Rare..Legendary; 0 outside them). */
    private static long mesoResetPrice(Equip equip, boolean bonus) {
        int grade = bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        if (grade < ItemGrade.Rare.getVal() || grade > ItemGrade.Legendary.getVal()) {
            return 0;
        }
        int col = Math.min(equip.getReqLevel() / 10, 25);
        col = col >= 25 ? 3 : col >= 20 ? 2 : col >= 16 ? 1 : 0;
        return (bonus ? MESO_RESET_BONUS : MESO_RESET_MAIN)[grade - ItemGrade.Rare.getVal()][col];
    }

    /**
     * @return what one meso-only reset of this item's (bonus) potential costs in the Enhance window, or -1 when the
     * window wouldn't offer it (setting off, no Rare..Legendary potential, fixed potential)
     */
    public static long potentialMesoResetCost(Equip equip, boolean bonus) {
        if (equip == null || isFixedPotential(equip)
                || !net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.HEROIC_MESO_POTENTIAL_RESET)
                || !net.swordie.ms.ServerSettings.getBoolean("enhance.heroic.mesoPotentialReset", true)) {
            return -1;
        }
        long price = mesoResetPrice(equip, bonus);
        return price > 0 ? price : -1;
    }

    /**
     * Rolls the item's own (bonus) potential with no before/after window, as a cube (or a meso reset: -1 main, -2
     * bonus) does by hand, with its cube ceiling: the roll is kept, so a rank-up starts the count over.
     *
     * @return whether the rank went up
     */
    public static boolean rollPotentialInPlace(Equip equip, int cubeId, boolean bonus) {
        int[] ceiling = ceilingFamily(cubeId);
        int grade = bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        int idx = grade - ItemGrade.Rare.getVal();
        boolean counted = ceiling != null && idx >= 0 && idx < ceiling.length;
        boolean force = counted && equip.getCubeCeiling(bonus) + 1 >= ceiling[idx];
        boolean tierUp = equip.rollCube(cubeId, bonus, force);
        if (tierUp) {
            equip.setCubeCeiling(bonus, 0);
        } else if (counted) {
            equip.setCubeCeiling(bonus, equip.getCubeCeiling(bonus) + 1);
        }
        return tierUp;
    }

    /**
     * Meso fee per cube use, as the Enhance window shows it (client 0x1453de950 / 0x1407f5720): item level L
     * squared times 0.5 (31..70), 2.5 (71..120) or 20 (121+); free up to level 30, up to 70 for characters
     * 60..89 and up to 120 for 90..100; Zero weapons are free; rounded down to hundreds. The client also takes
     * an event discount % from its event list (0x1453e7800); the game data defines none, so there is none here.
     */
    public static long cubeMesoFee(Char chr, Equip equip) {
        int type = (equip.getItemId() / 10000) % 100;
        if (type == 56 || type == 57) {
            return 0;
        }
        int level = equip.getReqLevel();
        int charLevel = chr.getLevel();
        int freeUpTo = charLevel >= 60 && charLevel < 90 ? 70 : charLevel >= 90 && charLevel <= 100 ? 120 : 30;
        if (level <= freeUpTo) {
            return 0;
        }
        double rate = level > 120 ? 20 : level > 70 ? 2.5 : 0.5;
        long fee = (long) ((double) level * level * rate);
        return fee / 100 * 100;
    }

    private static final Map<Integer, Long> warpDriveUsed = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * World Map Warp Drives and the 5044xxx Hyper Teleport Rocks (client cash type 106: 1 mode, then the teleport
     * rock target). Those with a cooldown (info/limitMin) aren't used up; the ones without (Alliance Teleport and
     * Teleport World Scrolls) are.
     */
    protected static void useWarpDrive(Client c, InPacket inPacket, Char chr, ItemInfo ii, Item item) {
        int itemId = item.getItemId();
        int limitMin = ii == null ? 0 : ii.getLimitMin();
        boolean usedUp = limitMin <= 0; // the scrolls without a cooldown are used up
        long cooldown = limitMin * 60_000L;
        long now = System.currentTimeMillis();
        Long last = warpDriveUsed.get(chr.getId());
        if (!usedUp && last != null && now - last < cooldown) {
            chr.chatMessage(String.format("You can use this again in %d seconds.", (cooldown - (now - last) + 999) / 1000));
            chr.dispose();
            return;
        }
        if (teleportRockTo(c, inPacket, chr)) {
            if (usedUp) {
                chr.consumeItem(item, 1);
            } else {
                warpDriveUsed.put(chr.getId(), now);
            }
        }
    }

    protected static boolean handleTeleportRock(Client c, InPacket inPacket, Char chr) {
        //can't teleport with hyper tele rock while in instance.
        if(chr.getField().getInstance() != null) {
            chr.chatMessage("You can't use a hyper teleport rock inside an instance");
            return false;
        }
        inPacket.decodeByte(); // unk
        teleportRockTo(c, inPacket, chr);
        return false;
    }

    /**
     * The teleport rock target (client 0x1453caf50): 1 by name, then [1: S name | 0: 4 field id, ...].
     * @return whether the character was moved
     */
    /** Moves to a world map field by teleport rock; the instance check is the caller's. */
    protected static boolean teleportRockToField(Char chr, int fieldId) {
        Field field = chr.getOrCreateFieldByCurrentInstanceType(fieldId);
        if (field == null || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, chr.getField()) || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(null, field) ||
                !FieldData.getWorldMapFields().contains(fieldId)) {
            chr.chatMessage("You may not warp to that map, as you cannot teleport from your current map.");
            chr.dispose();
            return false;
        }

        if (!chr.canWarpTo(field)) {
            chr.boxMessage("You cannot teleport to that map. Check that your level is high enough and that you have all required quests completed.");
            chr.dispose();
            return false;
        }

        chr.setInstance(null);
        chr.warp(field);
        return true;
    }

    protected static boolean teleportRockTo(Client c, InPacket inPacket, Char chr) {
        if (chr.getField().getInstance() != null) {
            chr.chatMessage("You can't use a teleport rock inside an instance.");
            chr.dispose();
            return false;
        }
        short type = inPacket.decodeByte();
        if (type == 0) {
            return teleportRockToField(chr, inPacket.decodeInt());
        } else {
            String targetName = inPacket.decodeString();
            int worldID = chr.getClient().getChannelInstance().getWorldId().getVal();
            World world = Server.getInstance().getWorldById(worldID);
            Char targetChr = world.getCharByName(targetName);

            // Target doesn't exist (checked first: an offline name has no account to look at)
            if (targetChr == null) {
                chr.chatMessage(String.format("%s is not online.", targetName));
                chr.dispose();
                return false;
            }

            if (targetChr.getUser() == null || targetChr.getUser().getAccountType() != Player) {
                chr.boxMessage("You can't warp to admins");
                chr.dispose();
                return false;
            }

            Position targetPosition = targetChr.getPosition();

            Field targetField = targetChr.getField();
            if (targetField == null || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(null, targetField)
                    || net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, chr.getField())) {
                chr.chatMessage("You may not warp to that map, as the targeted map cannot be teleported to.");
                chr.dispose();
                return false;
            }

            if (!chr.canWarpTo(targetField)) {
                chr.boxMessage("You cannot teleport to that map. Check that your level is high enough and that you have all required quests completed.");
                chr.dispose();
                return false;
            }

            if (targetChr.getInstance() != null) {
                // Target is in an instanced Map
                chr.chatMessage(String.format("%s is currently in an instance.", targetName));
                chr.dispose();
                return false;
            } else if (targetChr.isInCashShop() || targetChr.isInAuctionHouse()) {
                chr.chatMessage("%s is currently in the Cash Shop or Auction House.", targetName);
                chr.dispose();
                return false;
            } else if (targetChr.getChannel() != c.getChannel()) {
                // Change channels & warp & teleport
                int fieldId = targetChr.getFieldID();
                chr.setInstance(null);
                chr.changeChannelAndWarp(targetChr.getChannel(), fieldId);
            } else if (targetChr.getFieldID() != chr.getFieldID()) {
                // warp & teleport
                chr.setInstance(null);
                chr.warp(targetField);
                chr.write(FieldPacket.teleport(targetPosition, chr));
            } else {
                // teleport
                chr.write(FieldPacket.teleport(targetPosition, chr));
            }
        }
        return true;
    }

    protected static boolean handlePetSkillItem(InPacket inPacket, Char chr, int itemID, Item item) {
        // Pet Skill Items
        long sn = inPacket.decodeLong();
        PetSkill ps = ItemConstants.getPetSkillFromID(itemID);
        if (ps == null) {
            chr.chatMessage(String.format("Unhandled pet skill item %d", itemID));
            return true;
        }
        Item pi = chr.getCashInventory().getItemBySN(sn);
        if (!(pi instanceof PetItem)) {
            chr.chatMessage("Could not find that pet.");
            return true;
        }
        boolean add = itemID < ItemConstants.PET_SKILL_REMOVE_FIRST; // add property doesn't include the "Slimming Medicine"
        PetItem petItem = (PetItem) pi;
        if (add) {
            petItem.addPetSkill(ps);
        } else {
            petItem.removePetSkill(ps);
        }
        petItem.updateToChar(chr);
        chr.consumeItem(item);
        return false;
    }

    protected static void handleAvatarMegaphone(Client c, InPacket inPacket, Char chr, int itemID, Item item) {
        // Avatar Megaphones
        if (chr.isOnAvatarMegaphoneCooldown()) {
            long remainingCooldown = chr.getAvatarMegaphoneRemainingCooldown() / 1000;
            var minutes = remainingCooldown / 60;
            var seconds = remainingCooldown % 60;
            chr.chatMessage(String.format("You are on cooldown! Remaining time: %d minutes and %d seconds", minutes, seconds));
            chr.dispose();
            return;
        }

        List<String> lineList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String line = inPacket.decodeString();
            lineList.add(line);
        }

        // Forbidden Words Check
        var isForbidden = false;
        for (var chat : lineList) {
            isForbidden = ForbiddenWordsData.isForbiddenChat(chat);
            if (isForbidden) {
                break;
            }
        }
        if (isForbidden) {
            chr.chatMessage("An error occurred. Try again later.");
            chr.dispose();
            return;
        }
        // Forbidden Words Check

        boolean whisperIcon = inPacket.decodeByte() != 0;
        World world = c.getWorld();
        world.broadcastPacketByChr(chr, WvsContext.setAvatarMegaphone(chr, itemID, lineList, whisperIcon));
        chr.consumeItem(item);
        chr.setAvatarMegaphoneCooldown();
    }

    /**
     * @return true if the skill's levels are bought with the job's normal SP, so an SP reset may return or move them.
     * Fixed-level and hidden skills aren't, and neither are hyper skills or hyper stats, which have their own points.
     */
    private static boolean isSpResettable(SkillInfo si) {
        return si != null && si.getFixLevel() == 0 && !si.isInvisible() && si.getHyper() == 0
                && si.getHyperStat() == 0 && !SkillConstants.isHyperstatSkill(si.getSkillId())
                && !SkillData.isSpLocked(si.getSkillId());
    }

    protected static void handleFullSpReset(Char chr, Item item) {
        var cs = chr.getAvatarData().getCharacterStat();
        var isExtendSpJob = JobConstants.isExtendSpJob(chr.getJob());
        var extendSp = cs.getExtendSP();

        List<Skill> skillsToReset = new ArrayList<>();
        var totalReturnedSp = 0;
        for (var spSet : extendSp.getSpSet()) {
            int returnedSp = 0;
            var skills = chr.getSkillsByJobLevel(spSet.getJobLevel());
            for (var skill : skills) {
                var si = SkillData.getSkillInfoById(skill.getSkillId());
                if (isSpResettable(si)) {
                    returnedSp += skill.getCurrentLevel();
                    skill.setCurrentLevel(0);
                    skillsToReset.add(skill);
                }
            }
            if (isExtendSpJob) {
                spSet.setSp(spSet.getSp() + returnedSp);
            } else {
                totalReturnedSp += returnedSp;
            }
        }

        if (skillsToReset.isEmpty()) {
            // the client also refuses when no skill has SP in it (0x1453b98c0)
            chr.chatMessage("You have no skills to reset.");
            return;
        }
        if (!isExtendSpJob) {
            cs.addSp(totalReturnedSp);
        }

        skillsToReset.forEach(chr::addSkill);
        chr.write(WvsContext.changeSkillRecordResult(skillsToReset, true, false, false, false));
        chr.trimUnusableSp();

        Map<Stat, Object> stats = new HashMap<>();
        if (isExtendSpJob) {
            stats.put(Stat.sp, extendSp);
        } else {
            stats.put(Stat.sp, cs.getSp());
        }
        chr.write(WvsContext.statChanged(stats));
        chr.consumeItem(item);
    }

    protected static void handleSingleSpReset(Char chr, InPacket inPacket, int jobLevel, Item item) {
        int toSkillId = inPacket.decodeInt();
        int fromSkillId = inPacket.decodeInt();

        if (!SkillConstants.isValidSkillForJobAndJobLevel(toSkillId, chr.getJob(), jobLevel)
        || !SkillConstants.isValidSkillForJobAndJobLevel(fromSkillId, chr.getJob(), jobLevel)) {
            chr.chatMessage("Invalid skill to reset from/to.");
            return;
        }

        var toSkill = chr.getSkill(toSkillId);
        var fromSkill = chr.getSkill(fromSkillId);

        var toSi = SkillData.getSkillInfoById(toSkillId);
        var fromSi = SkillData.getSkillInfoById(fromSkillId);

        if (fromSkill == null || !isSpResettable(fromSi) || fromSkill.getCurrentLevel() < 1) {
            chr.chatMessage("You don't have enough SP to reset from that skill.");
            return;
        }

        if (!isSpResettable(toSi) || (toSkill != null && toSkill.getCurrentLevel() >= toSkill.getMaxLevel())
                || toSkillId == fromSkillId) {
            chr.chatMessage("That skill is already at its maximum level.");
            return;
        }
        if (toSkill == null) {
            toSkill = SkillData.getSkillDeepCopyById(toSkillId);
            if (toSkill == null) {
                chr.chatMessage("Invalid skill to reset from/to.");
                return;
            }
            toSkill.setCurrentLevel(0);
        }

        toSkill.incCurrentLevel(1);
        fromSkill.incCurrentLevel(-1);

        chr.addSkill(toSkill);
        chr.addSkill(fromSkill);

        toSkill.updateToChar(chr);
        fromSkill.updateToChar(chr);
        chr.consumeItem(item);
    }

    protected static void handlePlatinumScissorsOfKarma(InPacket inPacket, Char chr, Item item) {
        inPacket.decodeInt();
        var slot = inPacket.decodeInt();

        var psokItem = chr.getEquipInventory().getItemBySlot(slot);
        if (psokItem == null) {
            chr.chatMessage("Could not find that item.");
            chr.dispose();
            return;
        }

        if (psokItem.applyPsok(chr)) {
            chr.consumeItem(item);
        }
    }

    protected static void displayEnchantHyperUpgrade(Client c, InPacket inPacket, Char chr) {
        var ePos = inPacket.decodeInt();
        var safeGuard = inPacket.decodeByte() != 0;
        // set by the client for items on its special-item list; the reply then carries one more int
        var extraCount = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
        // v271: the Star Force mode picked in the window (0..3); the reply echoes it, or the client asks again
        int mode = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
        var inv = ePos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
        ePos = Math.abs(ePos);
        var equip = (Equip) inv.getItemBySlot(ePos);
        if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige) || !ItemConstants.isUpgradable(equip.getItemId())) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to enchant a non-enchantable equip (pos %d, itemid %d).",
                    chr.getId(), ePos, equip == null ? 0 : equip.getItemId()));
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }
        safeGuard &= equip.canSafeguardHyperUpgrade();
        boolean alwaysSucceeds = ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS);
        // shown with its real cost; the enhance request itself refuses an item with upgrade slots left
        if (equip.getBaseStat(tuc) != 0 && !c.getWorld().isReboot()) {
            chr.chatMessage("Use up this item's upgrade slots (scrolls or Spell Traces) before Star Force.");
        }
        c.write(FieldPacket.hyperUpgradeDisplay(equip,
                GameConstants.canStarForceDrop(equip),
                GameConstants.starForceCost(equip, mode, safeGuard),
                0,
                0,
                GameConstants.starForceChances(equip, mode, safeGuard, alwaysSucceeds),
                equip.getDropStreak() >= 2,
                extraCount,
                mode)
        );
    }

    protected static void displayEnchantScrollUpgrade(Client c, InPacket inPacket, Char chr) {
        int ePos = inPacket.decodeInt();
        var inv = ePos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
        ePos = Math.abs(ePos);
        var equip = (Equip) inv.getItemBySlot(ePos);
        if (c.getWorld().isReboot()) {
            chr.getOffenseManager().addOffense(String.format("Character %d attempted to scroll in reboot world (pos %d, itemid %d).",
                    chr.getId(), ePos, equip == null ? 0 : equip.getItemId()));
            chr.dispose();
            return;
        }
        if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige) || !ItemConstants.isUpgradable(equip.getItemId())) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to scroll a non-scrollable equip (pos %d, itemid %d).",
                    chr.getId(), ePos, equip == null ? 0 : equip.getItemId()));
            chr.dispose();
            return;
        }
        boolean fever = SunnySunday.scrollFeverTime();
        var suis = ItemConstants.getScrollUpgradeInfosByEquip(equip, fever);
        c.write(FieldPacket.scrollUpgradeDisplay(fever, suis));
    }

    protected static void doEnchantTransmission(Client c, InPacket inPacket, Char chr) {
        inPacket.decodeInt(); // tick
        short toPos = inPacket.decodeShort();
        short fromPos = inPacket.decodeShort();

        var fromInv = fromPos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
        var toInv = toPos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();

        Equip fromEq = (Equip) fromInv.getItemBySlot(fromPos);
        Equip toEq = (Equip) toInv.getItemBySlot(toPos);

        // the trace and a different, intact copy of the same item (the copy is used up)
        if (fromEq == null || toEq == null || fromEq == toEq || fromEq.getItemId() != toEq.getItemId()
                || !fromEq.hasSpecialAttribute(EquipSpecialAttribute.Vestige)
                || toEq.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            log.error(String.format("Equip transmission failed: from = %s, to = %s", fromEq, toEq));
            c.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }
        fromEq.removeVestige(); // keeps the stars set when it was destroyed
        chr.consumeItem(toEq);
        fromEq.updateToChar(chr);
        c.write(FieldPacket.showTraceRestorationResult(fromPos));
    }

    // Etc/GL_StarforceRecovery.img: the set items each Star Core restores
    private static final Set<Integer> STAR_CORE_SETS = Set.of(462, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451,
            457, 458, 459, 460, 461, 287, 289, 310, 119, 120, 25, 26, 27, 28, 29, 247, 248, 249, 250, 251, 504, 505, 506,
            507, 508, 617, 618, 619, 620, 621, 315, 316, 317, 318, 328, 127, 128, 129, 130, 131, 473, 782);
    private static final int STAR_CORE = 4037448;
    private static final int DAWN_STAR_CORE = 4037449;
    private static final int PITCHED_STAR_CORE = 4037450;

    private static boolean starCoreRestores(int coreId, Equip trace) {
        int setId = trace.getInfo() == null ? 0 : trace.getInfo().getSetItemID();
        return switch (coreId) {
            case STAR_CORE -> STAR_CORE_SETS.contains(setId);
            case DAWN_STAR_CORE -> setId == 869;
            case PITCHED_STAR_CORE -> setId == 677;
            default -> false;
        };
    }

    /** @return an Etc stack of a Star Core that restores this trace, or null */
    public static Item restoringStarCore(Char chr, Equip trace) {
        for (int id : new int[]{STAR_CORE, DAWN_STAR_CORE, PITCHED_STAR_CORE}) {
            Item core = chr.getEtcInventory().getItemByItemID(id);
            if (core != null && starCoreRestores(id, trace)) {
                return core;
            }
        }
        return null;
    }

    /** @return an intact copy of the trace's item in the Equip inventory (fewest stars first), or null */
    public static Equip identicalIntactItem(Char chr, Equip trace) {
        Equip best = null;
        for (Item item : chr.getEquipInventory().getItems()) {
            if (item instanceof Equip e && e != trace && e.getItemId() == trace.getItemId()
                    && !e.hasSpecialAttribute(EquipSpecialAttribute.Vestige)
                    && (best == null || e.getChuc() < best.getChuc())) {
                best = e;
            }
        }
        return best;
    }

    /** Trace Restoration with a Star Core instead of an identical item (set equipment only). */
    protected static void doStarCoreTraceRestoration(Client c, InPacket inPacket, Char chr) {
        inPacket.decodeInt(); // tick
        short corePos = inPacket.decodeShort();
        short tracePos = inPacket.decodeShort();

        var traceInv = tracePos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
        Item core = chr.getInventoryByType(InvType.ETC).getItemBySlot(corePos);
        Item traceItem = traceInv.getItemBySlot(tracePos);
        Equip trace = traceItem instanceof Equip e ? e : null;
        if (core == null || trace == null || !trace.hasSpecialAttribute(EquipSpecialAttribute.Vestige)
                || !starCoreRestores(core.getItemId(), trace)) {
            log.error(String.format("Star Core trace restoration failed: core = %s, trace = %s", core, trace));
            c.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }
        trace.removeVestige();
        chr.consumeItem(core);
        trace.updateToChar(chr);
        c.write(FieldPacket.showTraceRestorationResult(tracePos));
    }

    protected static void doEnchantHyperUpgrade(Client c, InPacket inPacket, Char chr, boolean extraInts) {
        // v271 (client 0x1421f4b50): type 1 / 7, int tick, int pos, int, int, byte safeguard, byte, byte, byte,
        // string, [type 7: int, int], int mode (the destroy-rate slider, 0..3)
        inPacket.decodeInt(); //tick
        int eqpPos = inPacket.decodeInt();
        inPacket.decodeInt();
        inPacket.decodeInt();

        boolean equippedInv = eqpPos < 0;
        var inv = equippedInv ? chr.getEquippedInventory() : chr.getEquipInventory();
        var equip = (Equip) inv.getItemBySlot(Math.abs(eqpPos));

        boolean safeGuard = inPacket.decodeByte() != 0 && equip != null && equip.canSafeguardHyperUpgrade();
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeByte();
        inPacket.decodeString();
        if (extraInts && inPacket.getUnreadAmount() >= 8) {
            inPacket.decodeInt();
            inPacket.decodeInt();
        }
        int mode = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;

        if (equip == null) {
            chr.chatMessage("Could not find the given equip.");
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        if (ItemConstants.isUpgradable(equip.getItemId()) && equip.getBaseStat(tuc) != 0 && !c.getWorld().isReboot()) {
            chr.chatMessage("Use up this item's upgrade slots (scrolls or Spell Traces) before Star Force.");
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        if (!ItemConstants.isUpgradable(equip.getItemId())
                || equip.getChuc() >= GameConstants.getMaxStars(equip)
                || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            chr.chatMessage("Equipment cannot be enhanced.");
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        long cost = GameConstants.starForceCost(equip, mode, safeGuard);

        if (chr.getMoney() < cost) {
            chr.chatMessage("Mesos required: " + NumberFormat.getNumberInstance(Locale.US).format(cost));
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        Equip oldEquip = equip.deepCopy();
        boolean alwaysSucceeds = ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS);
        int[] chances = GameConstants.starForceChances(equip, mode, safeGuard, alwaysSucceeds);
        if (equippedInv && chances[1] > 0 && chr.getEquipInventory().getEmptySlots() == 0) {
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You do not have enough space in your "
                    + "equip inventory in case your item gets destroyed.")));
            return;
        }

        int roll = GameConstants.rollStarForce(chances, true);
        var success = roll == 1;
        boolean boom = roll == 2;
        boolean canDegrade = GameConstants.canStarForceDrop(equip);
        if (success) {
            equip.setChuc((short) (equip.getChuc() + 1));
            equip.setDropStreak(0);
        } else if (boom) {
            // the trace keeps the stars it comes back with (GMS: 15-19 -> 12, 20 -> 15, 21-22 -> 17, ...)
            equip.setChuc((short) GameConstants.getStarForceRecoveryStars(equip, equip.getChuc()));
            equip.makeVestige();
            boom = true;

            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                chr.deductMoney(cost, ProgressionLog.Sink.STAR_FORCE);
                c.write(FieldPacket.showUpgradeResult(oldEquip, equip, success, boom, canDegrade));
                chr.dispose();
                chr.getScriptManager().startScript(0, "ego_equip_boom", ScriptType.Npc);
                return;
            }

        } else if (canDegrade) {
            equip.setChuc((short) (equip.getChuc() - 1));
            equip.setDropStreak(equip.getDropStreak() + 1);
        }
        net.swordie.ms.client.character.achievement.MapleAchievements.onStarForce(chr, success ? "success" : boom ? "destroy" : "fail", equip.getChuc());
        chr.deductMoney(cost, ProgressionLog.Sink.STAR_FORCE);
        equip.recalcEnchantmentStats();
        oldEquip.recalcEnchantmentStats();
        equip.updateToChar(chr);

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(eqpPos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copyChucFrom(equip);
            otherEquip.updateToChar(chr);
        }

        c.write(FieldPacket.showUpgradeResult(oldEquip, equip, success, boom, canDegrade));
        chr.dispose();
    }

    public static boolean enhanceItem(Char chr, Equip equip, boolean advanced, boolean safeguard) {
        if (equip == null) {
            chr.chatMessage("Could not find the given equip.");
            return false;
        }

        if (equip.cannotBeEnhanced(chr)) {
            chr.chatMessage("Equipment cannot be enhanced.");
            return false;
        }

        long cost = GameConstants.getEnchantmentMesoCost(equip);
        long nxCost = 0;

        if (chr.getMoney() < cost) {
            chr.chatMessage("Mesos required: " + NumberFormat.getNumberInstance(Locale.US).format(cost));
            return false;
        }

        Equip oldEquip = equip.deepCopy();
        boolean alwaysSucceeds = ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS);
        // the window's standard odds (slider 1), destroy as a share of the failures
        int[] chances = GameConstants.starForceChances(equip, 0, false, alwaysSucceeds);
        if (advanced) {
            chances[0] = Math.min(1000, chances[0] + 100);
            nxCost = GameConstants.getEnchantmentNxCost(equip, cost);
        }
        if (safeguard) {
            chances[1] = 0; // the no-boom scroll never destroys
        }

        if (chr.getNX() < nxCost) {
            chr.chatMessage("NX required: " + NumberFormat.getNumberInstance(Locale.US).format(nxCost));
            return false;
        }

        if (equip.getInvType() == EQUIPPED && chances[1] > 0 && chr.getEquipInventory().getEmptySlots() == 0) {
            chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You do not have enough space in your "
                    + "equip inventory in case your item gets destroyed.")));
            return false;
        }

        int roll = GameConstants.rollStarForce(chances, false); // Enhancement Scrolls: the base rate
        var success = roll == 1;
        boolean boom = roll == 2;
        boolean canDegrade = GameConstants.canStarForceDrop(equip);
        if (success) {
            equip.setChuc((short) (equip.getChuc() + 1));
            equip.setDropStreak(0);
        } else if (boom) {
            equip.setChuc((short) GameConstants.getStarForceRecoveryStars(equip, equip.getChuc()));
            if (ItemConstants.isLongOrBigSword(equip.getItemId())) {
            } else {
                equip.makeVestige();
            }
            boom = true;

/*
            if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
                chr.deductMoney(cost);
                chr.write(FieldPacket.showUpgradeResult(oldEquip, equip, success, boom, canDegrade));
                chr.dispose();
                chr.getScriptManager().startScript(0, "ego_equip_boom", ScriptType.Npc);
                return false;
            }*/

        } else if (canDegrade) {
            equip.setChuc((short) (equip.getChuc() - 1));
            equip.setDropStreak(equip.getDropStreak() + 1);
        }
        chr.deductMoney(cost, ProgressionLog.Sink.STAR_FORCE);
        chr.deductNX((int) nxCost);
        equip.recalcEnchantmentStats();
        oldEquip.recalcEnchantmentStats();
        equip.updateToChar(chr);

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(equip.getBagIndex()) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copyChucFrom(equip);
            otherEquip.updateToChar(chr);
        }

        return true;
    }

    protected static void doEnchantScrollUpgrade(Client c, InPacket inPacket, Char chr) {
        inPacket.decodeInt();// tick
        short pos = inPacket.decodeShort();
        int scrollIndex = inPacket.decodeInt();
        Inventory inv = pos < 0 ? chr.getEquippedInventory() : chr.getEquipInventory();
        pos = (short) Math.abs(pos);
        Equip equip = (Equip) inv.getItemBySlot(pos);

        if (equip == null || equip.hasSpecialAttribute(EquipSpecialAttribute.Vestige)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to enchant a non-scrollable equip (pos %d, itemid %d).",
                    chr.getId(), pos, equip == null ? 0 : equip.getItemId()));
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        boolean fever = SunnySunday.scrollFeverTime();
        List<ScrollUpgradeInfo> suis = ItemConstants.getScrollUpgradeInfosByEquip(equip, fever);
        if (scrollIndex < 0 || scrollIndex >= suis.size()) {
            chr.getOffenseManager().addOffense(String.format("Characer %d tried to spell trace scroll with an invalid scoll ID (%d, "
                    + "itemID %d).", chr.getId(), scrollIndex, equip.getItemId()));
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }

        ScrollUpgradeInfo sui = suis.get(scrollIndex);
        if (!chr.hasItemCount(ItemConstants.SPELL_TRACE_ID, sui.getCost())) {
            chr.write(FieldPacket.showUnknownEnchantFailResult((byte) 0));
            return;
        }
        Equip prevEquip = equip.deepCopy();
        chr.consumeItem(ItemConstants.SPELL_TRACE_ID, sui.getCost());
        boolean success = sui.applyTo(equip, chr);
        net.swordie.ms.client.character.achievement.MapleAchievements.onSpellTrace(chr, sui.getCost(), success, sui.getChance(),
                sui.getType() == SpellTraceScrollType.Innocence ? "innocent" : sui.getType() == SpellTraceScrollType.CleanSlate ? "white" : "");
        equip.recalcEnchantmentStats();
        String desc = success ? "Your item has been upgraded." : "Your upgrade has failed.";
        chr.write(FieldPacket.showScrollUpgradeResult(fever, success ? 1 : 0, desc, prevEquip, equip));

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(pos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copyScrollStatsFrom(equip);
            otherEquip.copyAttributesFrom(equip);
            otherEquip.updateToChar(chr);
        }

        equip.updateToChar(chr);
        suis = ItemConstants.getScrollUpgradeInfosByEquip(equip, fever);
        c.write(FieldPacket.scrollUpgradeDisplay(fever, suis));

        var props = new HashMap<String, Object>();
        props.put("equip", equip);

        chr.getScriptManager().startScript(0, "custom_reapply_helper_scrolls", ScriptType.Npc, props);
    }

    public static void handleBeastTamerSpReset(Char chr, InPacket inPacket, Item item) {
        if (!JobConstants.isBeastTamer(chr.getJob())) {
            chr.chatMessage("Only Beast Tamers can use this item.");
            chr.dispose();
            return;
        }

        int rootId;

        switch (inPacket.decodeInt()) {
            case 1: // Bear
                rootId = 11200;
                break;
            case 2: // Leopard
                rootId = 11210;
                break;
            case 3: // Hawk
                rootId = 11211;
                break;
            case 4: // Cat
                rootId = 11212;
                break;
            default:
                chr.chatMessage("Unknown BT SP reset request.");
                return;
        }

        var sp = 0;
        Set<Skill> resetSkills = new HashSet<>();

        for (var skill : chr.getSkills()) {
            if (skill.getRootId() == rootId) {
                sp += skill.getCurrentLevel();
                skill.setCurrentLevel(0);
                resetSkills.add(skill);
            }
        }

        chr.addStatAndSendPacket(Stat.sp, sp);
        chr.write(WvsContext.changeSkillRecordResult(resetSkills));

        chr.consumeItem(item);
    }

    public static void handleFamiliarSlotExpansionCoupon(InPacket inPacket, Char chr, Item item) {
        var slotInc = 5; // item increases familiar slots by 5 slots.

        FamiliarCodexManager fcm = chr.getFamiliarCodexManager();
        fcm.incFamiliarSlots(slotInc);
        fcm.updateForClient(FamiliarCodexUpdateMask.FamiliarSlots.getVal());
        chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.FamiliarSlotExpanded, true));
        chr.consumeItem(item);
    }

    public static void handleRedFamiliarCard(InPacket inPacket, Char chr, Item item) {
        int familiarId = inPacket.decodeInt();
        Familiar f = chr.getFamiliarByID(familiarId);
        if (f == null) {
            chr.dispose();
            return;
        }
        f.rerollPotentialLines(chr);
        if (Util.succeedProp(GameConstants.boosted(GameConstants.RANK_UP_CHANCE))) {
            f.doGradeUp(chr);
        }
        chr.write(UserLocal.familiarAddResult(f, FamiliarModifiedMask.PotLines0.getVal() + FamiliarModifiedMask.PotLines1.getVal()));
        chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.ReleaseOption, true));
        chr.consumeItem(item);
    }

    public static void handleFamiliarBreakthroughCard(InPacket inPacket, Char chr, Item item) {
        byte unk = inPacket.decodeByte();
        int familiarId = inPacket.decodeInt();
        Familiar f = chr.getFamiliarByID(familiarId);
        if (f == null) {
            chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.MaxLvIncreased, false));
            return;
        }
        var curCap = f.getCapLevel();
        f.incCapLevel();
        var newCap = f.getCapLevel();
        boolean success = newCap > curCap;
        if (success && f.levelToCap()) { // and level up to the new cap at once
            chr.write(UserLocal.familiarAddResult(f, FamiliarModifiedMask.Level.getVal()
                    + FamiliarModifiedMask.LvExp.getVal() + FamiliarModifiedMask.LvMaxExp.getVal()
                    + FamiliarModifiedMask.Attack.getVal() + FamiliarModifiedMask.Defense.getVal()));
        }
        chr.write(UserLocal.familiarModifyResult(FamiliarModifiedType.MaxLvIncreased, success));
        if (success) {
            chr.consumeItem(item);
        }
    }

    public static void handleKaiserChangeColorCoupon(Char chr, Item item) {
        var itemId = item.getItemId();

        var extern = chr.getKaiserColourExtern();
        var inner = chr.getKaiserColourInner();
        var premium = chr.getKaiserColourPremium();
        switch (itemId) {
            case ItemConstants.FINAL_FORM_MAIN_COLOR_CHANGE_COUPON:
                extern = Util.getRandom(0, 75) * 10;
                break;
            case ItemConstants.FINAL_FORM_SUB_COLOR_CHANGE_COUPON:
                inner = Util.getRandom(0, 75) * 10;
                break;
            case ItemConstants.FINAL_FORM_PREMIUM_BLACK_COUPON:
                extern = 842;
                inner = 0;
                premium = true;
                break;
            case ItemConstants.FINAL_FORM_RESET_COUPON:
                extern = 0;
                inner = 0;
                premium = false;
                break;
        }
        chr.changeKaiserColour(extern, inner, premium);
        chr.consumeItem(item);
    }

    public static void handleItemTag(Char chr, InPacket inPacket, Item item) {
        var equipPos = inPacket.decodeShort();
        Equip equip = (Equip) chr.getEquippedInventory().getItemBySlot(equipPos);
        if (item.getItemId() == ItemConstants.ITEM_TAG_CLEAR) {
            if (equip.getOwner().length() == 0) {
                chr.chatMessage("This item does not have an owner.");
                return;
            }
            equip.setOwner("");
        } else {
            if (equip.getOwner().length() > 0) {
                chr.chatMessage("This item already has an owner.");
                return;
            }
            equip.setOwner(chr.getName());

        }
        equip.updateToChar(chr);
        chr.consumeItem(item);
    }

    public static void displayAdBoard(Char chr, String msg) {
        if (msg.length() == 0) {
            chr.chatMessage("You cannot put up an empty message.");
            return;
        }

        if (msg.length() > GameConstants.MAX_AD_BOARD_MSG_LEN) {
            chr.chatMessage("This messsage is too long.");
            return;
        }
        chr.setADBoardRemoteMsg(msg);
        chr.getField().broadcastPacket(UserPacket.adBoard(chr));
    }

    public static void handleUserLotteryItemUseRequest(Char chr, Item item, int unk1, int unk2, int quantity) {

        var itemId = item.getItemId();

        if (ItemConstants.isLotteryItem(itemId)) {
            List<DropInfo> rewardPool = new ArrayList<>(LotteryItemConstants.getRewardPoolFromLotteryItem(itemId));
            Collections.shuffle(rewardPool);

            if (rewardPool.size() == 0) {
                chr.chatMessage("This Lottery Item does not have any rewards");
                return;
            }

            if (chr.getEquipInventory().isFull() || chr.getConsumeInventory().isFull() || chr.getEtcInventory().isFull() || chr.getInstallInventory().isFull()) {
                chr.chatMessage("Make sure you have enough space in your inventory");
                return;
            }

            if (unk1 == 0 && unk2 == 0) {
                // Click to Spin
                chr.write(UserLocal.doLotteryUI(item, rewardPool.stream().map(DropInfo::getItemID).collect(Collectors.toList())));
            }

            if (unk1 == 1 && unk2 == 1) {
                // Spinning
            }

            if (unk1 == 1 && unk2 == 0) {
                // Finished Spinning
                var inv = chr.getInventoryByType(item.getInvType());
                if (item.getQuantity() <= 0 || inv == null || inv.getItemBySlot(item.getBagIndex()) != item) {
                    // the box must still be in that slot (the quantity is never taken from the client)
                    chr.dispose();
                    return;
                }
                var rewardedItem = LotteryItemConstants.getRandomItemFromRewardPool(itemId);
                chr.consumeItem(item, 1); // consume one lottery box
                chr.addItemToInventory(rewardedItem.getItemID(), rewardedItem.getQuantity()); // give reward
                net.swordie.ms.client.character.achievement.MapleAchievements.onLottery(chr, itemId, rewardedItem.getItemID());
                chr.write(UserPacket.effect(Effect.lotteryUIResult(rewardedItem.getItemID(), rewardedItem.getQuantity()))); // effects that go with the lottery UI
                chr.write(UserPacket.effect(Effect.lotteryUse(itemId))); // effects that go with the lottery UI

                // Ensure next reward is not the same quantity
                rewardedItem.generateNextDrop();
            }
        }

        switch (itemId) {
            case ItemConstants.RANDOM_CHAIR_BOX:
                var script = "consume_" + itemId;
                chr.getScriptManager().startScript(itemId, script, ScriptType.Item);
                break;
        }
    }

    public static void handleCartasPearl(InPacket inPacket, Char chr, Item item) {
        var newEarVal = inPacket.decodeInt();

        if (newEarVal >= EarStyle.values().length) {
            chr.chatMessage("Unknown selected value.");
            return;
        }

        var avatarLook = chr.getAvatarData().getAvatarLook();

        if (newEarVal == avatarLook.getEarStyle().ordinal()) {
            chr.chatMessage("You already have this ear shape.");
            return;
        }

        var newStyle = EarStyle.values()[newEarVal];
        avatarLook.setEarStyle(newStyle);
        if (chr.getAvatarData().getZeroAvatarLook() != null) {
            chr.getAvatarData().getZeroAvatarLook().setEarStyle(newStyle);
        }

        chr.consumeItem(item);

        chr.getField().broadcastPacket(UserRemote.avatarModified(chr, AvatarModifiedMask.AvatarLook.getVal(), (byte) 0), chr);
    }

    public static void handleSoulCollection(Char chr, Item item, BossSoulType bossSoulType) {

        if (SoulCollectionModule.addSoulToCollection(chr, chr.getAccount(), bossSoulType, item)) {
            chr.consumeItem(item); // a soul that's already in the collection (or isn't a collection soul) is kept
        } else {
            chr.dispose();
        }
    }

    /** what an assist scroll puts on an equip (resources/data/assist-scrolls.tsv) */
    private record AssistScroll(List<EquipAttribute> attributes, boolean lite, boolean superiorOnly, boolean reset,
                                boolean cash) {
    }

    /** Lite assist scrolls only work on equips up to this level */
    private static final int LITE_ASSIST_MAX_LEVEL = 105;

    private static final Supplier<Map<Integer, AssistScroll>> ASSIST_SCROLLS = DataTable.cached("assist-scrolls", t -> {
        Map<Integer, AssistScroll> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            List<EquipAttribute> attributes = new ArrayList<>();
            for (String a : r.str("attributes").split(",")) {
                if (!a.isBlank()) {
                    attributes.add(EquipAttribute.valueOf(a.trim()));
                }
            }
            m.put(r.i("item"), new AssistScroll(List.copyOf(attributes), r.b("lite"), r.b("superiorOnly"), r.b("reset"),
                    r.b("cash")));
        }
        return m;
    });

    /** Cash-tab assist scrolls (Shielding Ward, Shield, Guardian, Innocence, Luck's Key, pet ones). */
    public static boolean isCashAssistScroll(int itemId) {
        AssistScroll scroll = assistScroll(itemId);
        return scroll != null && scroll.cash();
    }

    private static AssistScroll assistScroll(int scrollId) {
        return ASSIST_SCROLLS.get().get(scrollId);
    }

    /**
     * Puts an assist scroll's protection on an equip: Lucky Day (+10% on the next scroll), Shielding Ward (not
     * destroyed), Shield (upgrade slot kept), Guardian (the next failed scroll isn't used up); Innocence resets the
     * equip's scroll stats. Lite versions only work on equips up to level 105.
     *
     * @return whether it was applied (the player is told why not)
     */
    protected static boolean applyAssistScroll(Char chr, int scrollId, Equip equip) {
        AssistScroll scroll = assistScroll(scrollId);
        if (scroll == null) {
            chr.chatMessage("This scroll can't be used yet.");
            return false;
        }
        if (scroll.lite() && equip.getReqLevel() > LITE_ASSIST_MAX_LEVEL) {
            chr.chatMessage("This scroll only works on equipment up to level 105.");
            return false;
        }
        var info = ItemData.getEquipInfoById(equip.getItemId());
        if (scroll.superiorOnly() && (info == null || !info.isSuperiorEqp())) {
            chr.chatMessage("This scroll only works on Superior equipment.");
            return false;
        }
        if (scroll.reset()) {
            equip.resetStats();
        }
        for (EquipAttribute attribute : scroll.attributes()) {
            equip.addAttribute(attribute);
        }
        return true;
    }

    public static void handleAddEquipAttributeScroll(InPacket inPacket, Char chr, Item scroll) {
        var invTypeNum = inPacket.decodeShort();
        var equipPos = inPacket.decodeShort();

        var invType = equipPos < 0 ? EQUIPPED : EQUIP;
        Equip equip = (Equip) chr.getInventoryByType(invType).getItemBySlot(equipPos);
        if (scroll == null || equip == null) {
            chr.chatMessage(SystemNotice, "Could not find scroll or equip.");
            return;
        }

        if (!applyAssistScroll(chr, scroll.getItemId(), equip)) {
            return;
        }
        if (ItemConstants.isLongOrBigSword(equip.getItemId()) && JobConstants.isZero(chr.getJob())
                && scroll.getItemId() / 100 == 50642) {
            int otherEquipPos = Math.abs(equipPos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            if (otherEquip != null) {
                otherEquip.resetStats();
            }
        }

        chr.write(FieldPacket.showItemUpgradeEffect(chr.getId(), true, false, scroll.getItemId(), equip.getItemId(), false));

        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())) {
            int otherEquipPos = Math.abs(equipPos) == 10 ? 11 : 10;
            Equip otherEquip = (Equip) chr.getEquippedInventory().getItemBySlot(otherEquipPos);
            otherEquip.copyAttributesFrom(equip);
            otherEquip.updateToChar(chr);

            chr.write(ZeroPool.egoEquipComplete(true, true));
        }

        equip.updateToChar(chr);
        chr.consumeItem(scroll);
    }
}
