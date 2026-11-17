package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Pet pool packets (user pool prefix: owner id, pet index).
 */
public class PetPacket {

    // 1030: 1 type, 1 action, S chat
    public static OutPacket action(int charId, int petIdx, byte type, byte action, String msg) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_ACTION);

        outPacket.encodeInt(charId);
        outPacket.encodeInt(petIdx);
        outPacket.encodeByte(type);
        outPacket.encodeByte(action);
        outPacket.encodeString(msg);

        return outPacket;
    }

    // 1037 type 1: interact/<command>, success picks the success or fail lines
    public static OutPacket interactResult(int charId, int petIdx, int command, boolean success) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_ACTION_COMMAND);

        outPacket.encodeInt(charId);
        outPacket.encodeInt(petIdx);
        outPacket.encodeByte(1);
        outPacket.encodeByte(command);
        outPacket.encodeByte(success);
        outPacket.encodeByte(0);

        return outPacket;
    }

    // 1037 type 2: food/<level band>, item id 2120000..2129999 or 0
    public static OutPacket foodResult(int charId, int petIdx, boolean success, int foodItemId) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_ACTION_COMMAND);

        outPacket.encodeInt(charId);
        outPacket.encodeInt(petIdx);
        outPacket.encodeByte(2);
        outPacket.encodeByte(success);
        outPacket.encodeInt(foodItemId);

        return outPacket;
    }

    // 1032: int char, int pet index, S name
    public static OutPacket nameChanged(int charId, int petIdx, String name) {
        OutPacket outPacket = new OutPacket(OutHeader.PET_NAME_CHANGED);

        outPacket.encodeInt(charId);
        outPacket.encodeInt(petIdx);
        outPacket.encodeString(name);

        return outPacket;
    }
}
