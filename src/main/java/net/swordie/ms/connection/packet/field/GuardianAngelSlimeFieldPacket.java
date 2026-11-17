package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.slime.GuardianSlimeResult;
import net.swordie.ms.handlers.header.OutHeader;

/** CField_GuardianAngelSlime (fieldType 279, client 0x142d49510): GUARDIAN_ANGEL_SLIME_FIELD. */
public class GuardianAngelSlimeFieldPacket {

    public static OutPacket result(GuardianSlimeResult result) {
        OutPacket outPacket = new OutPacket(OutHeader.GUARDIAN_ANGEL_SLIME_FIELD);
        result.encode(outPacket);
        return outPacket;
    }
}
