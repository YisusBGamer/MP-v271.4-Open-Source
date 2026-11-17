package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

public class SpecialGameExitHandler {

    public static final int TYPE_LEGION_RAID = 2;
    public static final int TYPE_SPIRIT_SAVIOR = 3;

    /**
     * The exit button of special fields (client 0x1440710a0): int type (2 Legion Raid, 3 Spirit Savior), int map.
     * The Legion Raid keeps the dragon's HP and the coins earned; its maps return to the coin exit maps
     * (921172000 -> 921172200, 921172100 -> 921172201).
     */
    @Handler(op = InHeader.SPECIAL_GAME_EXIT)
    public static void handleSpecialGameExit(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        int fieldId = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : chr.getFieldID();
        // High Mountain's exit button (Etc.wz ExitButton 354-363; the client sends the field type here): out to the
        // save zone, the week's progress stays
        if (chr.getInstance() != null && chr.getInstance().getInstanceHelper()
                instanceof net.swordie.ms.world.field.instance.instancehelper.epic.HighMountainInstanceHelper) {
            chr.getScriptManager().warpInstanceOut(
                    net.swordie.ms.world.field.instance.instancehelper.epic.EpicDungeon.HM_SAVE_ZONE, 0);
            return;
        }
        // the Angler Company's (ExitButton 372-375, 384, 388): out to its exit, the week's progress stays
        if (chr.getInstance() != null && chr.getInstance().getInstanceHelper()
                instanceof net.swordie.ms.world.field.instance.instancehelper.epic.AnglerCompanyInstanceHelper) {
            chr.getScriptManager().warpInstanceOut(
                    net.swordie.ms.world.field.instance.instancehelper.epic.EpicDungeon.ANGLER_SAVE_ZONE, 0);
            return;
        }
        // the Legion Raid's Retreat sends its field type (155), not the map
        if (type == TYPE_LEGION_RAID && chr.getField() != null && FieldConstants.isUnionRaidField(chr.getFieldID())
                && chr.getUnionRaid() != null) {
            chr.getUnionRaid().retreat(chr);
            return;
        }
        if (chr.getField() == null || fieldId != chr.getFieldID()) {
            chr.dispose();
            return;
        }
        if (type == TYPE_LEGION_RAID && FieldConstants.isUnionRaidField(fieldId) && chr.getUnionRaid() != null) {
            chr.getUnionRaid().retreat(chr);
            return;
        }
        int ret = chr.getField().getInfo() == null ? 0 : chr.getField().getInfo().getReturnMap();
        if (type == TYPE_SPIRIT_SAVIOR && ret > 0 && ret != fieldId) {
            if (chr.getInstance() != null) {
                chr.getScriptManager().warpInstanceOut(ret, 0);
            } else {
                chr.warp(ret);
            }
            return;
        }
        chr.dispose();
    }
}
