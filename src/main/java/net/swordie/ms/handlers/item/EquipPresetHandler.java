package net.swordie.ms.handlers.item;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.EquipPresetModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

public class EquipPresetHandler {

    @Handler(op = InHeader.USER_EQUIPMENT_PRESET_CHANGE_REQUEST)
    public static void handleEquipmentPresetChange(Char chr, InPacket inPacket) {
        EquipPresetModule.handleChange(chr, inPacket);
    }

    @Handler(op = InHeader.USER_OUTFIT_PRESET_CHANGE_REQUEST)
    public static void handleOutfitPresetChange(Char chr, InPacket inPacket) {
        EquipPresetModule.handleOutfit(chr, inPacket, true);
    }

    @Handler(op = InHeader.USER_OUTFIT_PRESET_TOGGLE_REQUEST)
    public static void handleOutfitPresetToggle(Char chr, InPacket inPacket) {
        EquipPresetModule.handleOutfit(chr, inPacket, false);
    }
}
