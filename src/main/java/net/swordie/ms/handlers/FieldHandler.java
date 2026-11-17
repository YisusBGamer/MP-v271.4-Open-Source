package net.swordie.ms.handlers;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.handlers.header.InHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FieldHandler {
    private static final int GOO_RUINS_EXIT_FIELD = 867155800;

    private static final Logger log = LogManager.getLogger();

    @Handler(op = InHeader.BROADCAST_EFFECT_TO_SPLIT)
    public static void handleBroadcastEffectToSplit(Char chr, InPacket inPacket) {
        String effectPath = inPacket.decodeString();
        int duration = inPacket.decodeInt();
        int option = inPacket.decodeInt();
    }

    @Handler(op = InHeader.BROADCAST_ONE_TIME_ACTION_TO_SPLIT)
    public static void handleBroadcastOneTimeActionToSplit(Char chr, InPacket inPacket) {
        int action = inPacket.decodeInt();
        int duration = inPacket.decodeInt();
    }

    @Handler(op = InHeader.BROADCAST_AFFECTED_EFFECT_TO_SPLIT)
    public static void handleBroadcastAffectedEffectToSplit(Char chr, InPacket inPacket) {
        int skillID = inPacket.decodeInt();
        byte slv = inPacket.decodeByte();
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillAffected(skillID, slv, 0)), chr);
    }

    @Handler(op = InHeader.LEAVE_BUTTON_PRESSED)
    public static void leaveFieldButtonPressed(Char chr, InPacket inPacket){
        if(chr == null) return; //logged out
        if(FieldConstants.isGooExplorationField(chr.getFieldID())){
            chr.getScriptManager().warpInstanceOut(GOO_RUINS_EXIT_FIELD);
        }
    }
}
