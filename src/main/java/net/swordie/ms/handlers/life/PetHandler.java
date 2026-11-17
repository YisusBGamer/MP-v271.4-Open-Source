package net.swordie.ms.handlers.life;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.anticheat.Offense;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.PetItem;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.PetPacket;
import net.swordie.ms.connection.packet.ShopDlg;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.FieldOption;
import net.swordie.ms.enums.InventoryOperation;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.boss.will.WillModule;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.life.pet.PetSkill;
import net.swordie.ms.loaders.NpcData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PetHandler {

    private static final Logger log = LogManager.getLogger();
    // closeness needed for each pet level (level 1 at 0)
    private static final int[] CLOSENESS = {1, 3, 6, 14, 31, 60, 108, 181, 287, 434, 632, 891, 1224, 1642, 2161,
            2793, 3557, 4467, 5542, 6801, 8263, 9950, 11882, 14084, 16578, 19391, 22547, 26074, 30000};
    private static final int PET_SHOP_ID = 1012004;
    // Item Pouch (pet loot) on/off per character, for the answer sent on summon
    private static final Map<Integer, Boolean> pickUpOn = new ConcurrentHashMap<>();

    public static void addCloseness(Char chr, Pet pet, int amount) {
        PetItem pi = pet.getItem();
        int closeness = Math.min(CLOSENESS[CLOSENESS.length - 1], pi.getTameness() + amount);
        pi.setTameness((short) closeness);
        int level = 1;
        for (int need : CLOSENESS) {
            if (closeness >= need) {
                level++;
            }
        }
        level = Math.min(30, level);
        if (level > pi.getLevel()) {
            pi.setLevel((byte) level);
            chr.write(UserLocal.petActivateChange(pet, true, (byte) 0)); // shows the new level
        }
    }

    private static Pet findPet(Char chr, int key) {
        Pet pet = chr.getPetByIdx(key);
        if (pet != null) {
            return pet;
        }
        return chr.getPets().stream()
                .filter(p -> p.getItem() != null && (p.getItem().getItemId() == key || p.getItem().getId() == key))
                .findFirst().orElse(null);
    }


    @Handler(op = InHeader.USER_ACTIVATE_PET_REQUEST)
    public static void handleUserActivatePetRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.NoPet.getVal()) > 0) {
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        Item item = chr.getCashInventory().getItemBySlot(slot);
        if (!(item instanceof PetItem)) {
            item = chr.getConsumeInventory().getItemBySlot(slot);
        }
        // Two of the same condition, as item had to be re-assigned
        if (!(item instanceof PetItem)) {
            chr.chatMessage(String.format("Could not find a pet on that slot (slot %s).", slot));
            return;
        }
        PetItem petItem = (PetItem) item;
        if (petItem.getActiveState() == 0 && petItem.hatchIfEgg()) {
            petItem.updateToChar(chr); // the client gets the hatched pet before it's summoned
        }
        if (petItem.getActiveState() == 0) {
            if (chr.getPets().size() >= GameConstants.MAX_PET_AMOUNT) {
                chr.chatMessage("You already have 3 pets out!");
                chr.dispose();
                return;
            }
            Pet pet = petItem.createPet(chr);
            chr.addPet(pet);
            petItem.activatePet(chr, pet);
            chr.getField().broadcastPacket(UserLocal.petActivateChange(pet, true, (byte) 0));
            chr.write(WvsContext.cashPetPickUpOnOffResult(true, pickUpOn.getOrDefault(chr.getId(), true)));
            sendAutoBuffRecord(chr, pet);
        } else {
            Pet pet = chr.getPets()
                    .stream()
                    .filter(p -> p.getItem().getActiveState() == petItem.getActiveState())
                    .findFirst().orElse(null);

            petItem.deactivatePet(chr, pet);
            chr.removePet(pet);
            chr.getField().broadcastPacket(UserLocal.petActivateChange(pet, false, (byte) 0));
        }

        petItem.updateToChar(chr);
        chr.updatePetIgnoredItemList();
        chr.dispose();
    }

    @Handler(op = InHeader.USER_PET_FOOD_ITEM_USE_REQUEST)
    public static void handleUserPetFoodItemUseRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.NoPet.getVal()) > 0) {
            chr.dispose();
            return;
        }
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        // only pet food (212xxxx) from that slot, and only with a pet out
        if (item == null || item.getItemId() != itemID || itemID / 10000 != 212 || chr.getPets().isEmpty()) {
            chr.dispose();
            return;
        }
        chr.consumeItem(item);
        for (Pet pet : chr.getPets()) {
            PetItem pi = pet.getItem();
            // max repleteness is 100
            pi.setRepleteness((byte) (Math.min(100, pi.getRepleteness() + 30)));
            c.write(WvsContext.inventoryOperation(true, false,
                    InventoryOperation.Add, (short) pi.getBagIndex(), (short) 0, 0, pi));
        }
    }

    @Handler(op = InHeader.USER_CASH_PET_PICK_UP_ON_OFF_REQUEST)
    public static void handleUserCashPetPickUpOnOffRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // tick
        boolean on = inPacket.decodeByte() != 0;
        boolean channelChange = inPacket.decodeByte() != 0;
        int attribute = 0;
        if (channelChange) {
            attribute = inPacket.decodeInt();
        }
        pickUpOn.put(chr.getId(), on);
        chr.write(WvsContext.cashPetPickUpOnOffResult(true, on));

    }

    @Handler(op = InHeader.PET_MOVE)
    public static void handlePetMove(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.NoPet.getVal()) > 0) {
            chr.dispose();
            return;
        }
        int petID = inPacket.decodeInt();
        inPacket.decodeByte(); // ?
        MovementInfo movementInfo = new MovementInfo(inPacket);
        Pet pet = chr.getPetByIdx(petID);
        if (pet != null) {
            movementInfo.applyTo(pet);
            chr.getField().broadcastPacket(UserLocal.petMove(chr.getId(), petID, movementInfo), chr);
        }
    }

    @Handler(op = InHeader.PET_DROP_PICK_UP_REQUEST)
    public static void handlePetDropPickUpRequest(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        // Item Pouch turned off in the pet window
        if ((field.getInfo().getFieldLimit() & FieldOption.NoPet.getVal()) > 0 || chr.getMiniRoom() != null
                || !pickUpOn.getOrDefault(chr.getId(), true)) {
            return;
        }
        int petID = inPacket.decodeInt();
        byte fieldKey = inPacket.decodeByte();
        inPacket.decodeInt(); // tick
        inPacket.decodeInt(); // v265, as in the user's own pickup request
        Position pos = inPacket.decodePosition();
        int dropID = inPacket.decodeInt();
        inPacket.decodeInt(); // cliCrc
        Life life = field.getLifeByObjectID(dropID);
        if (life instanceof Drop) {
            Drop drop = (Drop) life;
            var pet = chr.getPetByIdx(petID);
            if (pet == null) {
                return;
            }
            var om = chr.getOffenseManager();
            if (om.doSampleTest()) {
                var dist = pet.getPosition().distanceTo(drop.getPosition());
                var str = String.format("Dist = %s, Pet loc: %s, drop loc: %s", dist, pet.getPosition(), drop.getPosition());

                if (dist > GameConstants.MAX_PET_DROP_PICKUP_DIST) {
                    chr.getOffenseManager().addOffense(Offense.Type.Editing, str, true);
                    chr.dispose();
                    return;
                }
//                om.increaseTrust();
            }

            if (!drop.tryClaim()) {
                return;
            }
            boolean success;
            try {
                success = drop.canBePickedUpByPet() && drop.canBePickedUpBy(chr) && chr.addDrop(drop, true);
            } catch (RuntimeException e) {
                drop.releaseClaim();
                throw e;
            }
            if (success) {
                field.removeDrop(dropID, chr.getId(), false, petID);
            } else {
                drop.releaseClaim();
            }
        }
    }

    @Handler(op = InHeader.PET_STAT_CHANGE_ITEM_USE_REQUEST)
    public static void handlePetStatChangeItemUseRequest(Char chr, InPacket inPacket) {
        if (chr.getHP() <= 0) { // a dead character's pet doesn't heal it back up behind the revive window
            chr.dispose();
            return;
        }
        var field = chr.getField();
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
        inPacket.decodeByte(); // check later
        short pos = inPacket.decodeShort();
        int itemID = inPacket.decodeInt();
        Item item = chr.getConsumeInventory().getItemBySlot(pos);
        if (item == null || itemID != item.getItemId()) {
            chr.chatMessage("Could not find the potion for your pet to use.");
            chr.dispose(); // the client waits for an answer, and its inventory stays locked without one
            return;
        }
        // already at full HP and MP by the server's count: the potion isn't used up, the request is just answered
        // (the client keeps asking when its own idea of max HP is higher)
        if (chr.getHP() >= chr.getMaxHP() && chr.getMP() >= chr.getMaxMP()) {
            chr.dispose();
            return;
        }
        chr.useStatChangeItem(item, true);
    }

    @Handler(op = InHeader.USER_REGISTER_PET_AUTO_BUFF_REQUEST)
    public static void handleUserRegisterPetAutoBuffRequest(Char chr, InPacket inPacket) {
        int petIdx = inPacket.decodeInt();
        int petSkillIdx = inPacket.decodeInt();
        int skillID = inPacket.decodeInt();
        inPacket.decodeByte(); // 1 for a few Phantom skills
        Pet pet = chr.getPetByIdx(petIdx);
        if (pet == null || pet.getItem() == null) {
            chr.dispose();
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        Skill skill = chr.getSkill(skillID);
        int coolTime = si == null ? 0 : Math.max(si.getValue(SkillStat.cooltime, 1), si.getValue(SkillStat.cooltimeMS, 1));
        if (skillID != 0 && (si == null || !pet.getItem().hasPetSkill(PetSkill.AUTO_BUFF)
                || skill == null || skill.getCurrentLevel() == 0 || (coolTime > 0 && !si.isPetAutoBuff()))) {
            chr.chatMessage("Something went wrong when adding the pet skill.");
            chr.getOffenseManager().addOffense(String.format("Character %d tried to illegally add a pet skill (skillID = %d, skill = %s, "
                    + "pet = %s, coolTime = %d)", chr.getId(), skillID, skill, pet, coolTime));
            chr.dispose();
            return;
        }
        if (petSkillIdx == 0) {
            pet.getItem().setAutoBuffSkill(skillID);
        } else {
            pet.getItem().setAutoBuffSkill2(skillID);
        }
        pet.getItem().updateToChar(chr);
        sendAutoBuffRecord(chr, pet);
    }

    // the client reads a pet's auto-buff slots from quest-ex 101080 + pet slot, keys "<10 * pet slot + buff slot>"
    private static final int PET_AUTO_BUFF_QUEST = 101080;

    static void sendAutoBuffRecord(Char chr, Pet pet) {
        if (pet == null || pet.getItem() == null || pet.getIdx() < 0 || pet.getIdx() > 2) {
            return;
        }
        int idx = pet.getIdx();
        var quest = chr.getQuestManager().getOrCreateQuestById(PET_AUTO_BUFF_QUEST + idx);
        if (quest == null) {
            return;
        }
        quest.setProperty(String.valueOf(10 * idx), pet.getItem().getAutoBuffSkill());
        quest.setProperty(String.valueOf(10 * idx + 1), pet.getItem().getAutoBuffSkill2());
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
    }

    @Handler(op = InHeader.PET_UPDATE_EXCEPTION_LIST_REQUEST)
    public static void handlePetUpdateExceptionListRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // pet idx, we ignore that anyway
        inPacket.decodeInt();
        inPacket.decodeInt();
        var size = inPacket.decodeByte();

        Set<Integer> ignoredItems = new HashSet<>();
        for (int i = 0; i < size; i++) {
            ignoredItems.add(inPacket.decodeInt());
        }

        chr.setIgnoredItems(ignoredItems);
        chr.updatePetIgnoredItemList();
    }

    @Handler(op = InHeader.PET_ACTION)
    public static void handlePetAction(Char chr, InPacket inPacket) {
        int petIdx = inPacket.decodeInt();
        inPacket.decodeInt(); // tick
        byte type = inPacket.decodeByte();
        byte action = inPacket.decodeByte();
        String msg = inPacket.decodeString();
        if (chr.getPetByIdx(petIdx) == null || msg.length() > 100) {
            return;
        }
        chr.getField().broadcastPacket(PetPacket.action(chr.getId(), petIdx, type, action, msg), chr);
    }

    @Handler(op = InHeader.PET_INTERACTION_REQUEST)
    public static void handlePetInteractionRequest(Char chr, InPacket inPacket) {
        int petIdx = inPacket.decodeInt();
        inPacket.decodeByte(); // 1 when typed in chat
        int command = inPacket.decodeByte() & 0xFF;
        inPacket.decodeByte();
        Pet pet = chr.getPetByIdx(petIdx);
        if (pet == null || pet.getItem() == null) {
            return;
        }
        boolean success = Util.succeedProp(50);
        if (success) {
            addCloseness(chr, pet, 1);
            pet.getItem().updateToChar(chr);
        }
        chr.getField().broadcastPacket(PetPacket.interactResult(chr.getId(), petIdx, command, success));
    }

    @Handler(op = InHeader.PET_FOOD_ITEM_USE_REQUEST)
    public static void handlePetFoodItemUseRequest(Char chr, InPacket inPacket) {
        int petKey = inPacket.decodeInt();
        inPacket.decodeInt(); // tick
        short slot = inPacket.decodeShort();
        int itemId = inPacket.decodeInt();
        Field field = chr.getField();
        if ((field.getInfo().getFieldLimit() & FieldOption.NoPet.getVal()) > 0) {
            chr.dispose();
            return;
        }
        Item item = chr.getConsumeInventory().getItemBySlot(slot);
        Pet pet = findPet(chr, petKey);
        if (item == null || item.getItemId() != itemId || itemId / 10000 != 212 || pet == null || pet.getItem() == null) {
            chr.dispose();
            return;
        }
        chr.consumeItem(item);
        PetItem pi = pet.getItem();
        boolean hungry = pi.getRepleteness() < 100;
        pi.setRepleteness((byte) Math.min(100, pi.getRepleteness() + 30));
        if (hungry) {
            addCloseness(chr, pet, 1);
        }
        pi.updateToChar(chr);
        chr.getField().broadcastPacket(PetPacket.foodResult(chr.getId(), pet.getIdx(), hungry, itemId));
        chr.dispose();
    }

    @Handler(op = InHeader.PET_OPEN_SHOP)
    public static void handlePetOpenShop(Char chr, InPacket inPacket) {
        int petKey = inPacket.decodeInt();
        Pet pet = findPet(chr, petKey);
        if (pet == null || pet.getItem() == null || chr.getShop() != null) {
            chr.dispose();
            return;
        }
        var nsd = NpcData.getShopById(PET_SHOP_ID);
        if (nsd == null) {
            chr.chatMessage("The pet shop isn't available.");
            chr.dispose();
            return;
        }
        chr.setShop(nsd);
        chr.write(ShopDlg.openShop(chr, pet.getItem().getItemId(), nsd));
    }

    // 334: 4 tick, 4 slot, 1 on/off; the client waits for CASH_PET_SKILL_SETTING_RESULT (no body)
    @Handler(op = InHeader.USER_CASH_PET_SKILL_SETTING_REQUEST)
    public static void handleUserCashPetSkillSettingRequest(Char chr, InPacket inPacket) {
        chr.write(new OutPacket(OutHeader.CASH_PET_SKILL_SETTING_RESULT));
        chr.dispose();
    }

    // 335: 4 tick, 8 pet item serial; only a pet that isn't out can be thrown away
    @Handler(op = InHeader.USER_DESTROY_PET_ITEM_REQUEST)
    public static void handleUserDestroyPetItemRequest(Char chr, InPacket inPacket) {
        inPacket.decodeInt();
        long sn = inPacket.decodeLong();
        Item target = null;
        for (Item item : chr.getCashInventory().getItems()) {
            if (item instanceof PetItem pi && (pi.getId() == sn || pi.getCashItemSerialNumber() == sn)) {
                target = item;
                break;
            }
        }
        if (target != null && ((PetItem) target).getActiveState() == 0) {
            chr.consumeItemFull(target);
        }
        chr.dispose();
    }
}
