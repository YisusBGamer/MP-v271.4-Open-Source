package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/** Esfera Guardian fields: ESFERA_GUARDIAN_INFO and ESFERA_GUARDIAN_GAUGE. */
public class EsferaGuardianFieldPacket {

    /** max Erda, gates to clear, Erda a gauge slot, Erda a shot, 2 more ints, the Erda mobs */
    public static OutPacket info(int maxErda, int gatesToClear, int erdaPerSlot, int erdaPerShot, int info5, int info6,
                                 int[] erdaMobs) {
        OutPacket out = new OutPacket(OutHeader.ESFERA_GUARDIAN_INFO);
        out.encodeInt(maxErda);
        out.encodeInt(gatesToClear);
        out.encodeInt(erdaPerSlot);
        out.encodeInt(erdaPerShot);
        out.encodeInt(info5);
        out.encodeInt(info6);
        out.encodeInt(erdaMobs.length);
        for (int id : erdaMobs) {
            out.encodeInt(id);
        }
        return out;
    }

    public static OutPacket gauge(int erda, int gatesDown) {
        OutPacket out = new OutPacket(OutHeader.ESFERA_GUARDIAN_GAUGE);
        out.encodeInt(erda);
        out.encodeInt(gatesDown);
        return out;
    }
}
