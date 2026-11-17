package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.kalos.KalosResult;
import net.swordie.ms.handlers.header.OutHeader;

/** CField_Kalos: KALOS_FIELD_RESULT. */
public class KalosFieldPacket {

    public static OutPacket result(KalosResult result) {
        OutPacket outPacket = new OutPacket(OutHeader.KALOS_FIELD_RESULT);
        result.encode(outPacket);
        return outPacket;
    }
}
