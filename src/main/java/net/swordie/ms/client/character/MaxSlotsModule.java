package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.familiar.FamiliarCodexManager;
import net.swordie.ms.client.character.familiar.FamiliarCodexUpdateMask;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ServerFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The max_slots setting: every slot the Cash Shop sells is open (character slots, inventory tabs, storage, Beauty
 * Album rooms, familiar storage, the second pendant slot). New accounts and characters start with them and older
 * ones are raised when they log in. Nothing is ever lowered, also with the setting off.
 */
public final class MaxSlotsModule {

    private static final Logger log = LogManager.getLogger(MaxSlotsModule.class);

    /** Character slots of a new account with the setting off (the users.characterslots column default). */
    public static final int START_CHARACTERS = 4;

    private MaxSlotsModule() {
    }

    public static boolean enabled() {
        return ServerFeatures.on(ServerFeatures.Feature.MAX_SLOTS);
    }

    public static int newAccountCharacterSlots() {
        return enabled() ? ServerConstants.MAX_CHARACTERS : START_CHARACTERS;
    }

    public static int newInventorySlots() {
        return enabled() ? Inventory.MAX_SLOTS : Inventory.START_SLOTS;
    }

    public static int newTrunkSlots() {
        return enabled() ? GameConstants.MAX_TRUNK_SIZE : GameConstants.DEFAULT_TRUNK_SIZE;
    }

    /** At the login screen, before the character list is sent. */
    public static void onUserLogin(User user) {
        if (enabled() && user.getCharacterSlots() < ServerConstants.MAX_CHARACTERS) {
            log.info("[Slots] login id {}: character slots {} -> {}", user.getId(), user.getCharacterSlots(),
                    ServerConstants.MAX_CHARACTERS);
            user.setCharacterSlots(ServerConstants.MAX_CHARACTERS);
        }
    }

    /** On entering the game: storage, Beauty Album, familiar storage and the pendant slot (inventories: on load). */
    public static void onLogin(Char chr) {
        if (!enabled()) {
            return;
        }
        try {
            Trunk trunk = chr.getAccount() == null ? null : chr.getAccount().getTrunk();
            if (trunk != null && trunk.getSlotCount() < GameConstants.MAX_TRUNK_SIZE) {
                trunk.setSlotCount(GameConstants.MAX_TRUNK_SIZE);
            }
            BeautyAlbum album = chr.getBeautyAlbum();
            if (album.openAll()) {
                BeautyModule.ALBUM_DAO.save(album);
            }
            FamiliarCodexManager fcm = chr.getFamiliarCodexManager();
            if (fcm != null && fcm.getFamiliarSlots() < GameConstants.MAX_FAMILIAR_SLOTS) {
                fcm.setFamiliarSlots(GameConstants.MAX_FAMILIAR_SLOTS);
                fcm.updateForClient(FamiliarCodexUpdateMask.FamiliarSlots.getVal());
            }
        } catch (Exception e) {
            log.error("[Slots] could not open the slots of character " + chr.getId(), e);
        }
        PendantSlotModule.onLogin(chr);
    }

    /** Inventories loaded from the database. */
    static void raise(Inventory inventory) {
        if (enabled() && inventory.getSlots() < Inventory.MAX_SLOTS) {
            inventory.setSlots(Inventory.MAX_SLOTS);
        }
    }
}
